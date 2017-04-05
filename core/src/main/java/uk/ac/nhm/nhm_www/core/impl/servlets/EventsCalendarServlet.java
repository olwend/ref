package uk.ac.nhm.nhm_www.core.impl.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;
import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.apache.sling.jcr.api.SlingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.PageManagerFactory;

@Component(label = "Natural History Events Calendar API", description = "API for returning event details from the Natural History Museum events calendar", metatype = true, immediate = true)
@Service(value = Servlet.class)
@Properties({
	@Property(name = "sling.servlet.paths", value = { "/bin/api/calendar" }, propertyPrivate = true),
	@Property(name = "sling.servlet.extensions", value = {"json"}),
	@Property(name = "sling.servlet.methods", value = { "GET" }, propertyPrivate = true),
	@Property(name = "service.description", value = "Return **"),

	//Properties for parameters in URL
	//@Property(name = "pageId", value = "home", description = "The default item ID")
})
public class EventsCalendarServlet extends SlingAllMethodsServlet {

	private static final Logger LOG = LoggerFactory.getLogger(EventsCalendarServlet.class);

	private static final long serialVersionUID = 1L;

	@Reference
	private SlingRepository repository;

	@Reference
	private ResourceResolverFactory resourceResolverFactory;

	@Reference
	private PageManagerFactory pageManagerFactory;

	private final static String QUERY = "SELECT * FROM [cq:Page] as E WHERE ISDESCENDANTNODE (E, '/content/nhmwww/en/home/events')";

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException, NumberFormatException {

		ArrayList<Page> events = null;
		JSONArray jsonArray = null;
		
		try {
			events = getCache();
			jsonArray = getJSON(events);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		response.getWriter().write(jsonArray.toString());
	}
	
	private ArrayList<Page> getCache() throws RepositoryException {
		ArrayList<Page> list = new ArrayList<Page>();

		try {
			//Bad code - do this correctly!
			final Session session = repository.loginAdministrative(null);//loginService("searchService", null);
			try {
				final QueryManager queryMgr = session.getWorkspace().getQueryManager();
				final Query query = queryMgr.createQuery(QUERY, Query.JCR_SQL2);
				final QueryResult queryResult = query.execute();

				NodeIterator nodeIterator = queryResult.getNodes();

				/* Get Resource resolver using resource resolver factory. */
				final Map<String, Object> param = new HashMap<String, Object>();
				param.put(ResourceResolverFactory.SUBSERVICE, "searchService");
				final ResourceResolver resolver = resourceResolverFactory.getAdministrativeResourceResolver(null);//getServiceResourceResolver(param);
				/* Get page manager object using page manager Factory */
				final PageManager pmanager = pageManagerFactory.getPageManager(resolver);

				while(nodeIterator.hasNext()) {
					Node node = nodeIterator.nextNode();
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
	
	public JSONArray getJSON(ArrayList<Page> events) throws JSONException {
		JSONArray array = new JSONArray();
		
		try {
			for(Page event : events) {
				JSONObject object = getJsonObject(event);
				array.put(object);
			}
		}
		catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return array;
	}
	
	private JSONObject getJsonObject(Page event) throws LoginException, JSONException {
		JSONObject jsonObject = new JSONObject();
		
		final ResourceResolver resolver = resourceResolverFactory.getAdministrativeResourceResolver(null);
		final Resource resource = resolver.getResource(event.getPath() + "/jcr:content");
		final ValueMap properties = resource.adaptTo(ValueMap.class);
		
		String[] dates = properties.get("jcr:datesRecurrence", String.class).split(",");
		String[] times = properties.get("jcr:timesRecurrence", String.class).split("\\],\\[");
		
		JSONArray dateArray = new JSONArray();
		for(int i=0; i<dates.length; i++) {
			JSONObject object = new JSONObject();
			object.put("date", dates[i]);
			
			int x = Integer.parseInt(dates[i].substring(dates[i].length()-1, dates[i].length()));
			String[] time = times[x].split(",");
			
			JSONArray timesArray = new JSONArray();
			for(int j=0; j<time.length; j++) {
				timesArray.put(time[j].replaceAll("\\[|\"|\\]|\\\\", ""));
			}

			object.put("times", (Object)timesArray);
			dateArray.put(object);
		}
		
		jsonObject.put("title", event.getTitle());
		jsonObject.put("dates", (Object)dateArray);
		
		return jsonObject;
	}
	
}
