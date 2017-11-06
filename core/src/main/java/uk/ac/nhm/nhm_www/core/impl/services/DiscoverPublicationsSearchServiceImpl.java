package uk.ac.nhm.nhm_www.core.impl.services;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;

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

import uk.ac.nhm.nhm_www.core.model.discover.Image;
import uk.ac.nhm.nhm_www.core.model.discover.Video;
import uk.ac.nhm.nhm_www.core.componentHelpers.DiscoverPublicationHelper;
import uk.ac.nhm.nhm_www.core.model.discover.ResourceComponent;
import uk.ac.nhm.nhm_www.core.model.discover.ResourceComponentArray;
import uk.ac.nhm.nhm_www.core.services.DiscoverPublicationsSearchService;

import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.Rendition;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.PageManagerFactory;

@Component(label = "Natural History Museum Discover", description = "Natural History Museum Discover", metatype = true, immediate = true)
@Service(value = DiscoverPublicationsSearchService.class)
@Properties(value = {
		@Property(name = "service.pid", value = "uk.ac.nhm.nhm_www.core.delivery.service.DiscoverPublicationsSearchService", propertyPrivate = true),
		@Property(name = "service.description", value = "Natural History Museum Discover properties", propertyPrivate = true), 
		@Property(name = "queryLimit", intValue = 200, description = "Query Limit"),
		@Property(name = "cacheExpired", intValue = 1, description = "Minutes Cache to expire"),
		@Property(name = "concurrencyLevel", intValue = 16, description="Cache ConcurrentHashMap: estimated number of concurrently updating threads. The implementation performs internal sizing to try to accommodate this many threads."),
		@Property(name = "mappedPath", value="", description="Repository path mapped, this path is going to be hide on the final link to the publication")
})
public class DiscoverPublicationsSearchServiceImpl implements DiscoverPublicationsSearchService {

	private static final Logger LOG = LoggerFactory.getLogger(DiscoverPublicationsSearchServiceImpl.class);

	private static final String DISCOVER_IMAGE_RENDITION_NAME = "cq5dam.web.480.480.jpeg";
	
	@Reference
	private SlingRepository repository;

	@Reference
	private PageManagerFactory pageManagerFactory;

	@Reference
	private ResourceResolverFactory resourceResolverFactory;

	private String jcrPath = "/content/nhmwww/%";

	private String cqTags = "discoverpublication";

	private int queryLimit;

	private long cacheExpired;

	
	/**
	 * HashMap to store the queries
	 */
	private ConcurrentHashMap<String, ResourceComponentArray> cache = new ConcurrentHashMap<String, ResourceComponentArray>();
	
	private int concurrencyLevel;
	
	private final static int INITIAL_CAPACITY = 16;
	
	private final static float LOAD_FACTOR = 0.75f;
	
	private String mappedPath;

	
	/**
	 * Automatically called as part of service activation
	 * 
	 * @param componentContext
	 * @throws Exception
	 */
	@Activate
	protected void activate(final ComponentContext componentContext) throws Exception {
		this.loadProperties(componentContext);
		
		this.cache = new ConcurrentHashMap<String, ResourceComponentArray>(INITIAL_CAPACITY, LOAD_FACTOR, concurrencyLevel);
	}

	public List<ResourceComponent> searchCQ(final SlingHttpServletRequest request) {
		final String keyQuery = getKeyQuery();

		if (!cache.containsKey(keyQuery)) {
			if (!updateCache(keyQuery))
				return null;
			return cache.get(keyQuery).getComplexObjectArray();
		}

		if (cache.containsKey(keyQuery)) {
			final ResourceComponentArray componentArray = cache.get(keyQuery);
			final Date actualDate = new Date();

			if ((actualDate.getTime() - componentArray.getCreatedAt().getTime()) > cacheExpired) {
				if (!updateCache(keyQuery))
					return null;
			}

			return cache.get(keyQuery).getComplexObjectArray();
		}

		return null;
	}

	public JSONObject getJSON(final List<ResourceComponent> listResources, final int startIndex, final int numElements) throws LoginException {

		final JSONObject object = new JSONObject();

		try {
			final int maximumIndex;
			if (startIndex < 0) {
				maximumIndex = 0 + numElements;
			} else {
				maximumIndex = startIndex + numElements;
			}
			
			final int listSize = listResources.size();
			
			final int toIndex;
			if (maximumIndex > listSize) {
				toIndex = listSize;
			} else {
				toIndex = maximumIndex;
			}
			
			final JSONArray jsonArray = new JSONArray();
			
			for (ResourceComponent resource : listResources.subList(startIndex, toIndex)) {	
				final JSONObject jsonObject = new JSONObject();
				final JSONObject jsonImage = new JSONObject();
				final JSONObject jsonVideo = new JSONObject();
				jsonObject.put("title", resource.getTitle());
				
				jsonObject.put("href", getExternalRef(resource.getHref()));
				jsonObject.put("text", removeLinks(resource.getText()));
				if (resource.getImage() == null) {
					jsonObject.put("image", JSONObject.NULL);
				} else {
					/* TODO reenable to make responsiove)
					jsonImage.put("fileReference", getImageMobileRendering(resource.getImage().getFileReference()));*/
					final String fileReference = resource.getImage().getFileReference();
					final String mobileFileReference = this.getImageMobileRendering(fileReference);
					
					jsonImage.put("fileReference", mobileFileReference);
					
					jsonObject.put("image", jsonImage);
				}
				if (resource.getVideo() == null) {
					jsonObject.put("video", JSONObject.NULL);
				} else {
					jsonVideo.put("youtube", resource.getVideo().getYouTubeId());
					jsonObject.put("video", jsonVideo);
				}

				jsonObject.put("tag", resource.getTag());
				jsonObject.put("created", resource.getCreated());

				jsonArray.put(jsonObject);
			}
			object.put("discoverJSON", jsonArray);
		} catch (JSONException e) {
			LOG.error("JSONException.", e);
		} catch (IndexOutOfBoundsException e) {
			LOG.error("IndexOutOfBoundsException.", e);
		} catch (NullPointerException e) {
			LOG.error("NullPointerException.", e);
		}

		return object;
	}

	public JSONObject getNextPrevious(final String path) {
		final JSONObject object = new JSONObject();

		try {
			// Check if the query is in the HashMap (cache)
			final String keyQuery = getKeyQuery();
			
			if (!cache.containsKey(keyQuery)) {
				if (!updateCache(keyQuery))
					return new JSONObject();
			}
			
			if (cache.containsKey(keyQuery)) {
				final ResourceComponentArray componentArray = cache.get(keyQuery);
				final Date actualDate = new Date();

				if ((actualDate.getTime() - componentArray.getCreatedAt().getTime()) > cacheExpired) {
					if (!updateCache(keyQuery))
						return new JSONObject();
				}
				
				final HashMap<String, Integer> indexMap = cache.get(keyQuery).getIndexMap();
	
				if (indexMap.containsKey(path)) {
					final Integer value = indexMap.get(path);
	
					final List<ResourceComponent> listResources = cache.get(keyQuery).getComplexObjectArray();
	
					int previous = value - 1;
					int next = value + 1;
	
					if (value == 0) {
						previous = listResources.size() - 1;
					} else if (value == (listResources.size() - 1)) {
						next = 0;
					}
					object.put("previous", getExternalRef(listResources.get(previous).getHref()));
					object.put("next", getExternalRef(listResources.get(next).getHref()));
				} else {
					object.put("previous", JSONObject.NULL);
					object.put("next", JSONObject.NULL);
				}
			}
		} catch (JSONException e) {
			LOG.error(" SearchServiceImpl getJSON() getNextPrevious", e);
		}

		return object;
	}

	public String getJcrPath() {
		return jcrPath;
	}

	public void setJcrPath(String jcrPath) {
		this.jcrPath = jcrPath;
	}

	public String getCqTags() {
		return cqTags;
	}

	public void setCqTags(String cqTags) {
		this.cqTags = cqTags;
	}

	/**
	 * Private Functions
	 */

	/**
	 * Store Service properties
	 * 
	 * @param componentContext
	 */
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
	
	private String getImageMobileRendering(String fileReference) throws LoginException {
		/*TODO - actually make it responsive */
		/* Get Resource resolver using resource resolver factory. */
		final Map<String, Object> param = new HashMap<String, Object>();
		param.put(ResourceResolverFactory.SUBSERVICE, "searchService");
		ResourceResolver resolver = resourceResolverFactory.getServiceResourceResolver(param);
		Resource rs = resolver.getResource(fileReference);
		if (rs == null) {
			return null;
		}
		
		Asset asset = rs.adaptTo(Asset.class);
		Rendition rendition = asset.getRendition(DISCOVER_IMAGE_RENDITION_NAME);
		if (rendition == null) {
			return fileReference;
		}
		return rendition.getPath();		
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
		final ResourceComponentArray resourceComponentArray = new ResourceComponentArray();

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
	
					final ResourceComponent resourceComponent = new ResourceComponent();
					if (!node.hasProperty(DiscoverPublicationHelper.TITLE_ATTRIBUTE_NAME) ||
						!node.hasProperty(DiscoverPublicationHelper.SNIPPET_ATTRIBUTE_NAME) ||
						!node.hasProperty(DiscoverPublicationHelper.CREATION_DATE_ATTRIBUTE_NAME) ||
						!node.hasProperty(DiscoverPublicationHelper.HEAD_TYPE_ATTRIBUTE_NAME) ||
						!node.hasProperty(DiscoverPublicationHelper.TYPE_ATTRIBUTE_NAME)) {
						continue;
					}
					
					resourceComponent.setTitle(node.getProperty(DiscoverPublicationHelper.TITLE_ATTRIBUTE_NAME).getString());
					resourceComponent.setText(node.getProperty(DiscoverPublicationHelper.SNIPPET_ATTRIBUTE_NAME).getString());
	
					final Calendar calendarDate = node.getProperty("jcr:created").getDate();
					resourceComponent.setCreated(new Date(calendarDate.getTimeInMillis()));
	
					final String type = node.getProperty(DiscoverPublicationHelper.TYPE_ATTRIBUTE_NAME).getString(); 
					resourceComponent.setTag(type);
	
					final String headType = node.getProperty(DiscoverPublicationHelper.HEAD_TYPE_ATTRIBUTE_NAME).getString();
					if (headType.equals("image")) {
						if (!node.hasNode("image") || !node.hasProperty("image/fileReference")) {
							continue;
						}
						final Node image = node.getNode("image");
						resourceComponent.setImage(new Image(image.getProperty("fileReference").getString()));
						resourceComponent.setVideo(null);
					} else if (headType.equals("video")) {
						if (!node.hasNode("video") || !node.hasProperty("video/youtube")) {
							continue;
						}
						final Node video = node.getNode("video");
						resourceComponent.setVideo(new Video(video.getProperty("youtube").getString()));
						resourceComponent.setImage(null);
					}
	
					/* Get page object using PageManager */
					Page page = pmanager.getContainingPage(node.getPath());
					resourceComponent.setHref(page.getPath() + ".html");
	
					resourceComponent.setPath(node.getPath());
	
					resourceComponentArray.addResource(resourceComponent);
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

		cache.put(keyQuery, resourceComponentArray);
		return true;
	}

	/*
	 * Right now we are using just one query.
	 */
	private String getKeyQuery() {
		return "SELECT * FROM [nt:unstructured] as c WHERE  ([jcr:path] like '" + jcrPath+ "') AND "
				+ "(c.[sling:resourceType]='nhmwww/components/functional/d;iscoverpublication') AND "
				+ "(c.[cq:tags]='"+ cqTags + "') order by [pinnedDate] DESC,[jcr:created]  DESC ";
	}
	
	private String removeLinks(final String text) {
		return text.replaceAll("<a([^>]+)>(.+?)</a>", "");
	}
	
	private String getExternalRef(final String href) {
		if (href == null) {
			return href;
		}
		
		if (this.mappedPath == null) {
			return href;
		}
		
		if (href.startsWith(this.mappedPath)) {
			final String result = href.substring(this.mappedPath.length());
			
			if (result.startsWith("/")) {
				return result;
			} else {
				return "/" + result;
			}
		}
		
		return href;
	}
}
