package uk.ac.nhm.core.impl.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Session;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;

import org.apache.felix.scr.annotations.Reference;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.apache.sling.jcr.api.SlingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.nhm.core.services.EventsCalendarSearchService;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.PageManagerFactory;

public class EventsCalendarSearchServiceImpl implements EventsCalendarSearchService {

	private static final Logger LOG = LoggerFactory.getLogger(EventsCalendarSearchServiceImpl.class);
	
	@Reference
	private SlingRepository repository;
	
	@Reference
	private ResourceResolverFactory resourceResolverFactory;
	
	@Reference
	private PageManagerFactory pageManagerFactory;
	
	private final static String QUERY = "SELECT * FROM [cq:Page] as E WHERE ([jcr:path] LIKE '/content/nhmwww/en/home/events/%')";
	private final int queryLimit = 200;

	private ArrayList<Page> getCache() {
		
		ArrayList<Page> list = new ArrayList<Page>();
		
		try {
			/* Create an Administrative Session */
			final Session session = repository.loginService("searchService", null);

			LOG.error(session.getUserID());
			LOG.error(repository.getDefaultWorkspace());
			LOG.error("test");
			
			try {
				final QueryManager queryMgr = session.getWorkspace().getQueryManager();
				final Query q = queryMgr.createQuery(QUERY,javax.jcr.query.Query.JCR_SQL2);
				q.setLimit(queryLimit);
				LOG.error(QUERY);
				final QueryResult queryResult = q.execute();
				LOG.error(queryResult.toString());
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
					list.add(page);
				}
			} finally {
				if (session.isLive()) {
					session.logout();
				}
			}
		} catch (Exception e) {
			LOG.error("Exception ", e);
			return null;
		}

		return list;
	}
	
	@Override
	public ArrayList<Page> getPages() {
		return getCache();
	}

	@Override
	public JSONObject getJSON(Page page, ResourceResolver resolver, SlingHttpServletRequest request) throws JSONException {
		
		return null;
	}

}
