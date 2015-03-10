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
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.apache.sling.jcr.api.SlingRepository;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.PageManagerFactory;

import uk.ac.nhm.nhm_www.core.componentHelpers.DiscoverPublicationHelper;
import uk.ac.nhm.nhm_www.core.model.DynamicApp.PageResourceArray;
import uk.ac.nhm.nhm_www.core.model.discover.Image;
import uk.ac.nhm.nhm_www.core.model.discover.ResourceComponent;
import uk.ac.nhm.nhm_www.core.model.discover.ResourceComponentArray;
import uk.ac.nhm.nhm_www.core.model.discover.Video;
import uk.ac.nhm.nhm_www.core.services.DiscoverPublicationsSearchService;
import uk.ac.nhm.nhm_www.core.services.DynamicAppPageRenderingService;

@Component(label = "Natural History Museum Dynamic App Page Rendering", description = "Natural History Museum Dynamic App Page Rendering Service", metatype = true, immediate = true)
@Service(value = DynamicAppPageRenderingService.class)
@Properties(value = {
		@Property(name = "service.pid", value = "uk.ac.nhm.nhm_www.core.delivery.service.DynamicAppPathRenderingService", propertyPrivate = true),
		@Property(name = "service.description", value = "Natural History Museum Discover properties", propertyPrivate = true), 
		@Property(name = "queryLimit", intValue = 200, description = "Query Limit"),
		@Property(name = "cacheExpired", intValue = 1, description = "Minutes Cache to expire"),
		@Property(name = "concurrencyLevel" ,intValue = 16, description="Cache ConcurrentHashMap: estimated number of concurrently updating threads. The implementation performs internal sizing to try to accommodate this many threads."),
		@Property(name = "mappedPath", value="", description="Repository path mapped, this path is going to be hide on the final link to the publication")
})
public class DynamicAppPageRenderingServiceImpl implements DynamicAppPageRenderingService {

	
	private static final Logger LOG = LoggerFactory.getLogger(DynamicAppPageRenderingServiceImpl.class);
	
	@Reference
	private SlingRepository repository;
	
	@Reference
	private ResourceResolverFactory resourceResolverFactory;
	
	@Reference
	private PageManagerFactory pageManagerFactory;
	
	private int queryLimit;
	
	private int concurrencyLevel;
	
	private long cacheExpired;
	
	private final static int INITIAL_CAPACITY = 16;
	
	private final static float LOAD_FACTOR = 0.75f;
	
	/**
	 * HashMap to store the queries
	 */
	private ConcurrentHashMap<String, PageResourceArray> cache = new ConcurrentHashMap<String, PageResourceArray>();
	
	/**
	 * Automatically called as part of service activation
	 * 
	 * @param componentContext
	 * @throws Exception
	 */
	@Activate
	protected void activate(final ComponentContext componentContext) throws Exception {
		this.loadProperties(componentContext);
		this.cache = new ConcurrentHashMap<String, PageResourceArray>(INITIAL_CAPACITY, LOAD_FACTOR, concurrencyLevel);
	}
	
	@Override
	public Page getPage(SlingHttpServletRequest request, String itemID) {
		final String itemQuery = getItemQuery(itemID);
		if (!cache.containsKey(itemQuery)) {
			if (!updateCache(itemQuery)) {
				return null;
			}
			List<Page> pages = cache.get(itemQuery).getComplexObjectArray();
			if(pages.size() > 0){
				return pages.get(0);
			} else {
				return null;
			}
			
			
		}

		if (cache.containsKey(itemQuery)) {
			final PageResourceArray componentArray = cache.get(itemQuery);
			final Date actualDate = new Date();

			if ((actualDate.getTime() - componentArray.getCreatedAt().getTime()) > cacheExpired) {
				if (!updateCache(itemQuery))
					return null;
			}

			List<Page> pages = cache.get(itemQuery).getComplexObjectArray();
			if(pages.size() > 0){
				return pages.get(0);
			} else {
				return null;
			}
		}

		return null;

	}
	
	private String getItemQuery(String itemID) {
		return "SELECT * FROM [cq:Page] WHERE NAME() = '" + itemID + "'";
		
		
		
		
	}
	
	
	private void loadProperties(final ComponentContext componentContext) {

		try {
			this.queryLimit = this.getInteger(componentContext.getProperties().get("queryLimit"), 200);
			final int minutesCacheExpired = this.getInteger(componentContext.getProperties().get("cacheExpired"), 1);
			this.cacheExpired = TimeUnit.MINUTES.toMillis(minutesCacheExpired);
			this.concurrencyLevel = this.getInteger(componentContext.getProperties().get("concurrencyLevel"), 16);
		} catch (NumberFormatException e) {
			LOG.error("loadProperties NumberFormatException.", e);
		} catch (Exception e) {
			LOG.error("loadProperties Exception.", e);
		}

	}
	
	private boolean updateCache(final String keyQuery) {
		final PageResourceArray resourceComponentArray = new PageResourceArray();

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
					Page page = pmanager.getPage(node.getPath());
					resourceComponentArray.addResource(page);
					
				}
			} finally {
				if (session.isLive()) {
					session.logout();
				}
			}
		} catch (Exception e) {
			LOG.error("Exception ", e);
			return false;
		}

		cache.put(keyQuery, resourceComponentArray);
		return true;
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

	@Override
	public JSONObject getJSON(Page page) throws JSONException {
		final JSONObject object = new JSONObject();
		object.put("path", page.getPath());
		
		return object;
	}
	
	
}
