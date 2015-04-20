package uk.ac.nhm.nhm_www.core.impl.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;
import javax.servlet.http.HttpServletRequest;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.apache.sling.jcr.api.SlingRepository;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.nhm.nhm_www.core.model.DatedAndTaggedFeedListElement;
import uk.ac.nhm.nhm_www.core.model.DatedAndTaggedFeedListElementArray;
import uk.ac.nhm.nhm_www.core.model.FeedListElement;
import uk.ac.nhm.nhm_www.core.model.PressReleaseFeedListElement;
import uk.ac.nhm.nhm_www.core.services.FeedListPaginationService;

import com.day.cq.tagging.TagManager;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageFilter;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.PageManagerFactory;

@Component(label = "Natural History Museum Pagination", description = "Natural History Museum Pagination", metatype = true, immediate = true)
@Service(value = FeedListPaginationService.class)
@Properties(value = {
		@Property(name = "service.pid", value = "uk.ac.nhm.nhm_www.core.delivery.service.FeedListPaginationService", propertyPrivate = true),
		@Property(name = "service.description", value = "Natural History Museum Pagination properties", propertyPrivate = true),
		@Property(name = "queryLimit", intValue = 200, description = "Query Limit"),
		@Property(name = "cacheExpired", intValue = 1, description = "Minutes Cache to expire"),
		@Property(name = "concurrencyLevel", intValue = 16, description = "Cache ConcurrentHashMap: estimated number of concurrently updating threads. The implementation performs internal sizing to try to accommodate this many threads."),
		@Property(name = "mappedPath", value = "", description = "Repository path mapped, this path is going to be hide on the final link to the publication") 
})
public class FeedListPaginationServiceImpl implements FeedListPaginationService {

	@Reference
	private SlingRepository repository;
	
	@Reference
	private PageManagerFactory pageManagerFactory;
	
	@Reference
	private ResourceResolverFactory resourceResolverFactory;
	
	private String jcrPath = "/content/nhmwww/%";
	private String[] cqTags = null;
	private long cacheExpired;
	private int queryLimit;
	private static final Logger LOG = LoggerFactory.getLogger(FeedListPaginationServiceImpl.class);
	
	// HashMap to store the queries
	private int concurrencyLevel;
	private String mappedPath;
	private final static int INITIAL_CAPACITY = 16;
	private final static float LOAD_FACTOR = 0.75f;
	private ConcurrentHashMap<String, DatedAndTaggedFeedListElementArray> cache = new ConcurrentHashMap<String, DatedAndTaggedFeedListElementArray>();
	
	@Activate
	protected void activate(final ComponentContext componentContext) throws Exception {
		this.loadProperties(componentContext);
		
		this.cache = new ConcurrentHashMap<String, DatedAndTaggedFeedListElementArray>(INITIAL_CAPACITY, LOAD_FACTOR, concurrencyLevel);
	}
	
	public String[] getCqTags() {
		return cqTags;
	}

	public void setCqTags(String[] cqTags) {
		this.cqTags = cqTags;
	}
	
	private String returnTagString(String[] tags){
		String ret = "(c.[cq:tags]='" + tags[0] ;
		for (int i = 1; i < tags.length ; i++) {
			ret += "' AND c.[cq:tags]='" + tags[i];
		}
		return  ret;
	}
	
	private String getKeyQuery() {
		LOG.error("Tags" +  returnTagString(cqTags));
		return "SELECT * FROM [nt:unstructured] as c WHERE ([jcr:path] like '" + jcrPath + "') AND "
				+ "(c.[sling:resourceType]='nhmwww/components/page/newscontentpage') AND "
				+ returnTagString(cqTags) + "') "
				+ "ORDER BY [publishdate] DESC ";
	}

	private void loadProperties(final ComponentContext componentContext) {

		try {
			this.queryLimit = this.getInteger(componentContext.getProperties().get("queryLimit"), 200);
			final int minutesCacheExpired = this.getInteger(componentContext.getProperties().get("cacheExpired"), 1);
			this.cacheExpired = TimeUnit.MINUTES.toMillis(minutesCacheExpired);
			this.concurrencyLevel = this.getInteger(componentContext.getProperties().get("concurrencyLevel"), 16);
			this.mappedPath = componentContext.getProperties().get("mappedPath").toString();
		} catch (NumberFormatException e) {
			LOG.error("loadProperties NumberFormatException.", e);
		} catch (Exception e) {
			LOG.error("loadProperties Exception.", e);
		}

	}
	
	private int getInteger(final Object value, final int defaultValue) {
		if (value instanceof Integer) {
			return ((Integer)value).intValue();
		}
		
		try {
			if (value instanceof String) {
				return Integer.parseInt((String)value);
			}
			
			return Integer.parseInt(value.toString());
		} catch (final NumberFormatException e) {
			return defaultValue;
		}
	}
	
	private boolean updateCache(final String keyQuery) {
		final DatedAndTaggedFeedListElementArray elementArray = new DatedAndTaggedFeedListElementArray();

		try {
			/* Create an Administrative Session */
			final Session session = repository.loginService("searchService", null);

			try {
				final QueryManager queryMgr = session.getWorkspace().getQueryManager();
				final Query query = queryMgr.createQuery(keyQuery,javax.jcr.query.Query.JCR_SQL2);
				query.setLimit(queryLimit);
				final QueryResult queryResult = query.execute();
	
				final NodeIterator iterator = queryResult.getNodes();
	
				/* Get Resource resolver using resource resolver factory. */
				final Map<String, Object> param = new HashMap<String, Object>();
				param.put(ResourceResolverFactory.SUBSERVICE, "searchService");
				final ResourceResolver resolver = resourceResolverFactory.getServiceResourceResolver(param);
				/* Get page manager object using page manager Factory */
				final PageManager pmanager = pageManagerFactory.getPageManager(resolver);
				
				while (iterator.hasNext()) {
	
					final Node node = iterator.nextNode();
	
					final DatedAndTaggedFeedListElement element = new DatedAndTaggedFeedListElement();
					
					if (!node.hasProperty(DatedAndTaggedFeedListElement.TITLE_ATTRIBUTE_NAME) ||
						!node.hasProperty(DatedAndTaggedFeedListElement.SUMMARY_ATTRIBUTE_NAME) ||
						!node.hasProperty(DatedAndTaggedFeedListElement.PUBLISH_DATE_ATTRIBUTE_NAME) ||
						!node.hasProperty(DatedAndTaggedFeedListElement.TAGS_ATTRIBUTE_NAME) ||
						!node.hasProperty(DatedAndTaggedFeedListElement.IMAGE_FILEREF_ATTRIBUTE_NAME)) {
						continue;
					}
					
					element.setTitle(node.getProperty(DatedAndTaggedFeedListElement.TITLE_ATTRIBUTE_NAME).getString());
					element.setIntro(node.getProperty(DatedAndTaggedFeedListElement.SUMMARY_ATTRIBUTE_NAME).getString());
					element.setImagePath(node.getProperty(DatedAndTaggedFeedListElement.IMAGE_FILEREF_ATTRIBUTE_NAME).getString());
					Page page = pmanager.getContainingPage(node.getPath());
					element.setElementLink(page.getPath());
									
					// Date
					final Calendar calendarDate = node.getProperty(DatedAndTaggedFeedListElement.PUBLISH_DATE_ATTRIBUTE_NAME).getDate();
					element.setPressReleaseDate(new Date(calendarDate.getTimeInMillis()));
	
					// Tags
					TagManager tagManager = page.adaptTo(TagManager.class);
					Value[] valueArray = node.getProperty(DatedAndTaggedFeedListElement.TAGS_ATTRIBUTE_NAME).getValues();
					String[] tagArray = new String[valueArray.length];
					for (int i = 0; i < valueArray.length; i++) {
						String tag = valueArray[i].getString();
						tagArray[i] = tag;
					}
					element.setTags(tagArray);
					elementArray.addResource(element);
				}
			} finally {
				if (session.isLive()) {
					session.logout();
				}
			}
		} catch (PathNotFoundException e) {
			LOG.error("PathNotFoundException ", e);
			return false;
		} catch (RepositoryException e) {
			LOG.error("RepositoryException ", e);
			return false;
		} catch (Exception e) {
			LOG.error("Exception ", e);
			return false;
		}

		cache.put(keyQuery, elementArray);
		return true;
	}

	public List<DatedAndTaggedFeedListElement> searchCQ(final SlingHttpServletRequest request) {
		final String keyQuery = getKeyQuery();
		LOG.error("query built: " + keyQuery);
		if (!cache.containsKey(keyQuery)) {
			if (!updateCache(keyQuery))
				return null;
			return cache.get(keyQuery).getComplexObjectArray();
		}

		if (cache.containsKey(keyQuery)) {
			final DatedAndTaggedFeedListElementArray componentArray = cache.get(keyQuery);
			final Date actualDate = new Date();

			if ((actualDate.getTime() - componentArray.getCreatedAt().getTime()) > cacheExpired) {
				if (!updateCache(keyQuery))
					return null;
			}
			return cache.get(keyQuery).getComplexObjectArray();
		}
		return null;
	}

	
	@Override
	public List<Object> getJCRItems(final HttpServletRequest request, final String path, final Integer pageNumber) throws LoginException {
		final Map<String, Object> param = new HashMap<String, Object>();
		final List<Object> objectsFound = new ArrayList<Object>();
		param.put(ResourceResolverFactory.SUBSERVICE, "searchService");
		final ResourceResolver resolver = this.resourceResolverFactory.getServiceResourceResolver(param);
		final Resource res = resolver.getResource(path);
		final Page rootPage = res.adaptTo(Page.class);
		final Iterator<Page> itrChildren = rootPage.listChildren(new PageFilter(request));
		while (itrChildren.hasNext()) {
			objectsFound.add(itrChildren.next());
		}

		return objectsFound;
	}

	@Override
	public JSONObject getJSON(final List<Object> objects, final Integer pageNumber, final Integer pageSize, final ResourceResolver resolver, final SlingHttpServletRequest request) {
		final JSONObject jsonObject = new JSONObject();

		// final int listSize = objects.size();

		// final int numberOfPages = listSize / pageSize;
		final int indexFrom = (pageSize * pageNumber) - (pageSize - 1);
		final int indexTo;
		if (objects.size() - indexFrom < pageSize - 1) {
			indexTo = indexFrom + (objects.size() - indexFrom);
		} else {
			indexTo = indexFrom + pageSize - 1;
		}

		final JSONArray jsonArray = new JSONArray();
		try {
			final int pages = objects.size() / pageSize + (objects.size() % pageSize == 0 ? 0 : 1);
			jsonObject.put("pages", pages);
			for (int i = indexFrom - 1; i < indexTo; i++) {
				if (objects.get(i) instanceof PressReleaseFeedListElement) {
					final PressReleaseFeedListElement listElement = (PressReleaseFeedListElement) objects.get(i);
					jsonArray.put(addPressReleaseElement(listElement, resolver, request));
				} else {
					final FeedListElement listElement = (FeedListElement) objects.get(i);
					jsonArray.put(addElement(listElement));
				}
			}
			jsonObject.put("pageJson", jsonArray);
		} catch (final JSONException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}

	private JSONObject addPressReleaseElement(final PressReleaseFeedListElement listElement, final ResourceResolver resolver, final SlingHttpServletRequest request) throws JSONException {
		final JSONObject itemToAdd = new JSONObject();
		itemToAdd.put("title", listElement.getTitle());
		itemToAdd.put("intro", listElement.getIntro());
		itemToAdd.put("imagePath", listElement.getImagePath());
		final DateFormat df = new SimpleDateFormat("MMMMM d yyyy");
		itemToAdd.put("date", df.format(listElement.getPressReleaseDate()));
		itemToAdd.put("path", resolver.map(request, listElement.getPage().getPath()));
		return itemToAdd;
	}

	private JSONObject addElement(final FeedListElement listElement) throws JSONException {
		final JSONObject itemToAdd = new JSONObject();
		itemToAdd.put("title", listElement.getTitle());
		itemToAdd.put("intro", listElement.getIntro());
		itemToAdd.put("imagePath", listElement.getImagePath());
		return itemToAdd;
	}

}
