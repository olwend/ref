package uk.ac.nhm.core.impl.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import javax.jcr.PropertyIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
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

import uk.ac.nhm.core.model.DatedAndTaggedFeedListElement;
import uk.ac.nhm.core.model.FeedListElementImpl;
import uk.ac.nhm.core.model.PressReleaseFeedListElementImpl;
import uk.ac.nhm.core.model.TaggedFeedListElement;
import uk.ac.nhm.core.model.TaggedFeedListElementArray;
import uk.ac.nhm.core.model.TaggedFeedListElementArrayImpl;
import uk.ac.nhm.core.model.TaggedFeedListElementImpl;
import uk.ac.nhm.core.services.FeedListPaginationService;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageFilter;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.PageManagerFactory;

@Component(label = "Natural History Museum Pagination", description = "Natural History Museum Pagination", metatype = true, immediate = true)
@Service(value = FeedListPaginationService.class)
@Properties(value = {
		@Property(name = "service.pid", value = "uk.ac.nhm.core.delivery.service.FeedListPaginationService", propertyPrivate = true),
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
	private String resourceType = "nhmwww/components/page/newscontentpage";
	private String[] cqTags = null;
	private long cacheExpired;
	private int queryLimit;
	private static final Logger LOG = LoggerFactory.getLogger(FeedListPaginationServiceImpl.class);
	
	// HashMap to store the queries
	private int concurrencyLevel;
	private String mappedPath;
	private final static int INITIAL_CAPACITY = 16;
	private final static float LOAD_FACTOR = 0.75f;
	private ConcurrentHashMap<String, TaggedFeedListElementArray> cache = new ConcurrentHashMap<String, TaggedFeedListElementArray>();
	
	@Activate
	protected void activate(final ComponentContext componentContext) throws Exception {
		this.loadProperties(componentContext);
		
		this.cache = new ConcurrentHashMap<String, TaggedFeedListElementArray>(INITIAL_CAPACITY, LOAD_FACTOR, concurrencyLevel);
	}
	
	public String getJcrPath() {
		return jcrPath;
	}

	public void setJcrPath(String jcrPath) {
		this.jcrPath = jcrPath + "/%";
	}
	
	public String[] getCqTags() {
		return cqTags;
	}

	public void setCqTags(String[] cqTags) {
		this.cqTags = cqTags;
	}
	
	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	private String returnTagString(String[] tags){
		String ret = tags[0] ;
		for (int i = 1; i < tags.length ; i++) {
			ret += "' AND c.[cq:tags]='" + tags[i];
		}
		return  ret;
	}
	
	private String getKeyQuery() {
		return "SELECT * FROM [nt:unstructured] as c "
				+ "WHERE ([jcr:path] like '" + getJcrPath() + "') AND "
				+ "(c.[sling:resourceType]='" + getResourceType() + "') AND " 
				+ "(c.[cq:tags]='" + returnTagString(cqTags) + "') "
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
		TaggedFeedListElementArray elementArray = new TaggedFeedListElementArrayImpl();

		try {
			/* Create an Administrative Session */
			final Session session = repository.loginService("searchService", null);

			try {
				final QueryManager queryMgr = session.getWorkspace().getQueryManager();
				final Query query = queryMgr.createQuery(keyQuery,javax.jcr.query.Query.JCR_SQL2);
				query.setLimit(queryLimit);
				final QueryResult queryResult = query.execute();
	LOG.error(query.getStatement());
				final NodeIterator iterator = queryResult.getNodes();
	
				/* Get Resource resolver using resource resolver factory. */
				final Map<String, Object> param = new HashMap<String, Object>();
				param.put(ResourceResolverFactory.SUBSERVICE, "searchService");
				final ResourceResolver resolver = resourceResolverFactory.getServiceResourceResolver(param);
				/* Get page manager object using page manager Factory */
				final PageManager pmanager = pageManagerFactory.getPageManager(resolver);
				while (iterator.hasNext()) {
					
					final Node node = iterator.nextNode();
					TaggedFeedListElement element = new TaggedFeedListElementImpl();
	
					if (!node.hasProperty(TaggedFeedListElement.TITLE_ATTRIBUTE_NAME) ||
						!node.hasProperty(TaggedFeedListElement.SUMMARY_ATTRIBUTE_NAME) ||
						!node.hasProperty(TaggedFeedListElement.TAGS_ATTRIBUTE_NAME) ||
						!node.hasProperty(TaggedFeedListElement.IMAGE_FILEREF_ATTRIBUTE_NAME)) {
						
						if(!node.hasProperty(DatedAndTaggedFeedListElement.PUBLISH_DATE_ATTRIBUTE_NAME)){
							continue;
						}
					}
					
					Page page = pmanager.getContainingPage(node.getPath());
					LOG.error("node slingResourceType " + node.getProperty(TaggedFeedListElement.RENDERING_COMPONENT_ATTRIBUTE_NAME).toString());
					
					if (node.getProperty(TaggedFeedListElement.TEMPLATE_ATTRIBUTE_NAME).getString().equals(DatedAndTaggedFeedListElement.TEMPLATE_ATTRIBUTE_VALUE)){
						LOG.error("Is DatedAndTaggedFeedListElement");
						DatedAndTaggedFeedListElement dntElement = new DatedAndTaggedFeedListElement(page);
						elementArray.addResource(dntElement.bruteForceConstructor(node, page, element));
					} else {
						LOG.error("Its a TaggedFeedListElement");
						elementArray.addResource(element.bruteForceConstructor(node, page, element));
					}
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

	public List<TaggedFeedListElement> searchCQ(final SlingHttpServletRequest request, String rootPath, String tags, String resourceType) {
		this.setJcrPath(rootPath);
		this.cqTags = tags.split(",");
		this.setResourceType(resourceType);
		final String keyQuery = getKeyQuery();
		if (!cache.containsKey(keyQuery)) {
			if (!updateCache(keyQuery))
				return null;
			return cache.get(keyQuery).getComplexObjectArray();
		}

		if (cache.containsKey(keyQuery)) {
			final TaggedFeedListElementArray componentArray = cache.get(keyQuery);
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
		//LOG.error("# Objects: " + objects.size());
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
				LOG.error("object class:" + objects.get(i).toString());
				if(objects.get(i) instanceof DatedAndTaggedFeedListElement) {
					final DatedAndTaggedFeedListElement listElement = (DatedAndTaggedFeedListElement) objects.get(i);
					jsonArray.put(addDatedAndTaggedElement(listElement, resolver, request));
				} else if (objects.get(i) instanceof TaggedFeedListElementImpl) {
					final TaggedFeedListElementImpl listElement = (TaggedFeedListElementImpl) objects.get(i);
					jsonArray.put(addTaggedElement(listElement, resolver, request));
				} else if (objects.get(i) instanceof PressReleaseFeedListElementImpl) {
					final PressReleaseFeedListElementImpl listElement = (PressReleaseFeedListElementImpl) objects.get(i);
					jsonArray.put(addPressReleaseElement(listElement, resolver, request));
				} else {
					final FeedListElementImpl listElement = (FeedListElementImpl) objects.get(i);
					jsonArray.put(addElement(listElement));
				}
			}
			jsonObject.put("pageJson", jsonArray);
		} catch (final JSONException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}

	private JSONObject addElement(final FeedListElementImpl listElement) throws JSONException {
		final JSONObject itemToAdd = new JSONObject();
		itemToAdd.put("title", listElement.getTitle());
		itemToAdd.put("intro", listElement.getIntro());
		itemToAdd.put("imagePath", listElement.getImagePath());
		return itemToAdd;
	}
	
	private JSONObject addTaggedElement(final TaggedFeedListElement listElement, final ResourceResolver resolver, final SlingHttpServletRequest request) throws JSONException {
		final JSONObject itemToAdd = new JSONObject();
		itemToAdd.put("title", listElement.getTitle());
		itemToAdd.put("intro", listElement.getIntro());
		itemToAdd.put("imagePath", listElement.getImagePath());
		itemToAdd.put("path", resolver.map(request, listElement.getPage().getPath()));
		itemToAdd.put("group", listElement.getPage().getParent().getTitle());
		itemToAdd.put("shortIntro", listElement.getShortIntroduction());
		return itemToAdd;
	}
	
	private JSONObject addPressReleaseElement(final PressReleaseFeedListElementImpl listElement, final ResourceResolver resolver, final SlingHttpServletRequest request) throws JSONException {
		final JSONObject itemToAdd = new JSONObject();
		itemToAdd.put("title", listElement.getTitle());
		itemToAdd.put("intro", listElement.getIntro());
		itemToAdd.put("imagePath", listElement.getImagePath());
		final DateFormat df = new SimpleDateFormat("d MMMMM yyyy");
		itemToAdd.put("date", df.format(listElement.getPressReleaseDate()));
		itemToAdd.put("path", resolver.map(request, listElement.getPage().getPath()));
		return itemToAdd;
	}
	
	private JSONObject addDatedAndTaggedElement(final DatedAndTaggedFeedListElement listElement, final ResourceResolver resolver, final SlingHttpServletRequest request) throws JSONException {
		final JSONObject itemToAdd = new JSONObject();
		itemToAdd.put("title", listElement.getTitle());
		itemToAdd.put("intro", listElement.getIntro());
		itemToAdd.put("imagePath", listElement.getImagePath());
		final DateFormat df = new SimpleDateFormat("d MMMMM yyyy");
		itemToAdd.put("date", df.format(listElement.getPressReleaseDate()));
		itemToAdd.put("path", resolver.map(request, listElement.getPage().getPath()));
		itemToAdd.put("group", listElement.getPage().getParent().getTitle());
		itemToAdd.put("shortIntro", listElement.getShortIntroduction());
		return itemToAdd;
	}
}
