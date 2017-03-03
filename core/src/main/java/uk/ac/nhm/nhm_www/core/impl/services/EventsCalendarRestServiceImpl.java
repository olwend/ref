package uk.ac.nhm.nhm_www.core.impl.services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.apache.sling.jcr.api.SlingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.nhm.nhm_www.core.impl.servlets.EventsCalendarServlet;
import uk.ac.nhm.nhm_www.core.services.EventsCalendarRestService;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.PageManagerFactory;

@Service(value = EventsCalendarRestServiceImpl.class)
@Component(metatype = true,
immediate = true)
@Path("/calendarservice")
public class EventsCalendarRestServiceImpl implements EventsCalendarRestService {

	private static final Logger LOG = LoggerFactory.getLogger(EventsCalendarRestServiceImpl.class);

	@Reference
	private SlingRepository repository;

	@Reference
	private ResourceResolverFactory resourceResolverFactory;

	@Reference
	private PageManagerFactory pageManagerFactory;

	private final static String QUERY = "SELECT * FROM [cq:Page] as E WHERE ISDESCENDANTNODE (E, '/content/nhmwww/en/home/events')";

	@GET
	@Path("/all")
	@Produces("application/json")
	@Override
	public Response getAll() throws RepositoryException, JSONException {
		ArrayList<Page> cache = getCache();
		JSONArray jsonArray = getJSON(cache, "all");

		String s = jsonArray.toString();

		return Response.ok(s, MediaType.APPLICATION_JSON).build();
	}

	@GET
	@Path("/week")
	@Produces("application/json")
	@Override
	public Response getWeek() throws RepositoryException, JSONException  {

		return Response.ok("testWeek", MediaType.APPLICATION_JSON).build();
	}

	@GET
	@Path("/day")
	@Produces("application/json")
	@Override
	public Response getDay() throws RepositoryException, JSONException  {
		ArrayList<Page> cache = getCache();
		JSONArray jsonArray = getJSON(cache, "day");

		String s = jsonArray.toString();

		return Response.ok(s, MediaType.APPLICATION_JSON).build();
	}

	private ArrayList<Page> getCache() throws RepositoryException {
		ArrayList<Page> list = new ArrayList<Page>();

		try {
			//Bad code - do this correctly!
			final Session session = repository.loginAdministrative(null);//loginService("searchService", null);
			LOG.error(repository.toString());
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

	public JSONArray getJSON(ArrayList<Page> events, String filter) throws JSONException {
		JSONArray array = new JSONArray();

		try {
			for(Page event : events) {
				JSONObject object = getJsonObject(event, filter);
				if(object.getJSONArray("dates").length() > 0) {
					array.put(object);
				}
			}
		}
		catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return array;
	}

	private JSONObject getJsonObject(Page event, String filter) throws LoginException, JSONException, ParseException {
		JSONObject jsonObject = new JSONObject();

		final ResourceResolver resolver = resourceResolverFactory.getAdministrativeResourceResolver(null);
		final Resource resource = resolver.getResource(event.getPath() + "/jcr:content");
		final ValueMap properties = resource.adaptTo(ValueMap.class);

		String[] dates = properties.get("jcr:datesRecurrence", String.class).split(",");
		String[] times = properties.get("jcr:timesRecurrence", String.class).split("\\],\\[");
		String[] durations = properties.get("jcr:durationsRecurrence", String.class).split(",");
		LOG.error(String.valueOf(durations.length));
		JSONArray dateArray = new JSONArray();
		for(int i=0; i<dates.length; i++) {
			JSONObject object = new JSONObject();

			//Convert date string into date object
			Pattern pattern = Pattern.compile("^[A-Za-z0-9 :+(]+\\)");
			Matcher matcher = pattern.matcher(dates[i]);

			DateFormat format = new SimpleDateFormat("E MMM d yyyy", Locale.ENGLISH);
			Date eventDate = null;

			if(matcher.find()) {
				eventDate = format.parse(matcher.group(0));

				switch(filter) {
				case "all": 
					/*object.put("date", matcher.group(0));

					int x = Integer.parseInt(dates[i].substring(dates[i].length()-1, dates[i].length()));
					String[] time = times[x].split(",");

					JSONArray timesArray = new JSONArray();
					for(int j=0; j<time.length; j++) {
						timesArray.put(time[j].replaceAll("\\[|\"|\\]|\\\\", ""));
					}

					object.put("times", (Object)timesArray);
					object.put("duration", Integer.valueOf(durations[x].replaceAll("\\[|\\]",  "")));*/

					object = processDates(matcher, i, dates, times, durations);
					
					dateArray.put(object);

					break;
				case "week":
					//TODO
					object.put("date", matcher.group(0));
					break;
				case "day":
					if(matcher.group(0).startsWith("Fri Mar 03 2017")) {
						object.put("date", matcher.group(0));

						int x = Integer.parseInt(dates[i].substring(dates[i].length()-1, dates[i].length()));
						String[] time = times[x].split(",");

						JSONArray timesArray = new JSONArray();
						for(int j=0; j<time.length; j++) {
							timesArray.put(time[j].replaceAll("\\[|\"|\\]|\\\\", ""));
						}

						object.put("times", (Object)timesArray);
						object.put("duration", Integer.valueOf(durations[x].replaceAll("\\[|\\]",  "")));

						dateArray.put(object);
					}
					break;
				}

			} else {
				object.put("date", dates[i]);
			}
		}

		jsonObject.put("title", properties.get("jcr:eventTitle", String.class));
		jsonObject.put("description", properties.get("jcr:eventDescription", String.class));
		jsonObject.put("dates", (Object)dateArray);

		return jsonObject;
	}


	private JSONObject processDates(Matcher matcher, int index, String[] dates, String[] times, String[] durations) throws JSONException {
		JSONObject object = new JSONObject();
		
		//Get date
		object.put("date", matcher.group(0));

		//Get times from times[] given index
		int x = Integer.parseInt(dates[index].substring(dates[index].length()-1, dates[index].length()));
		String[] time = times[x].split(",");

		JSONArray timesArray = new JSONArray();
		for(int j=0; j<time.length; j++) {
			timesArray.put(time[j].replaceAll("\\[|\"|\\]|\\\\", ""));
		}

		object.put("times", (Object)timesArray);
		
		//Get duration from durations[] given index
		object.put("duration", Integer.valueOf(durations[x].replaceAll("\\[|\\]",  "")));
		
		return object;
	}
}
