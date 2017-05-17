package uk.ac.nhm.nhm_www.core.impl.services.rest;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
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
import javax.ws.rs.PathParam;
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
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.PageManagerFactory;

import uk.ac.nhm.nhm_www.core.services.EventsCalendarRestService;

@Service(value = EventsCalendarRestServiceImpl.class)
@Component(label = "Natural History Museum Events Calendar Rest API", metatype = true, immediate = true)
@Path("/calendar")
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
		LOG.info("- all - ");
		ArrayList<Page> cache = getCache();
		JSONArray jsonArray = getJSON(cache, "all", null);

		String s = jsonArray.toString();

		return Response.ok(s, MediaType.APPLICATION_JSON).build();
	}

	@GET
	@Path("/week")
	@Produces("application/json")
	@Override
	public Response getWeek() throws RepositoryException, JSONException  {
		LOG.info("- week - ");
		ArrayList<Page> cache = getCache();
		JSONArray jsonArray = getJSON(cache, "week", null);

		String s = jsonArray.toString();

		return Response.ok(s, MediaType.APPLICATION_JSON).build();
	}
	
	@GET
	@Path("/week/{year}/{month}/{day}")
	@Produces("application/json")
	@Override
	public Response getWeekByDate(
			@PathParam("year") int year,
			@PathParam("month") int month,
			@PathParam("day") int day) throws RepositoryException, JSONException  {
		
		DateTime dt = new DateTime(year, month, day, 0, 0);
		
		LOG.info("- week by date - ");
		
		ArrayList<Page> cache = getCache();
		JSONArray jsonArray = getJSON(cache, "weekByDate", dt);

		String s = jsonArray.toString();

		return Response.ok(s, MediaType.APPLICATION_JSON).build();
	}

	@GET
	@Path("/day")
	@Produces("application/json")
	@Override
	public Response getDay() throws RepositoryException, JSONException  {
		LOG.info("- day - ");
		ArrayList<Page> cache = getCache();
		JSONArray jsonArray = getJSON(cache, "day", null);

		String s = jsonArray.toString();

		return Response.ok(s, MediaType.APPLICATION_JSON).build();
	}
	
	@GET
	@Path("/day/{year}/{month}/{day}")
	@Produces("application/json")
	@Override
	public Response getDay(
			@PathParam("year") int year,
			@PathParam("month") int month,
			@PathParam("day") int day) throws RepositoryException, JSONException  {
		
		DateTime dt = new DateTime(year, month, day, 0, 0);
		
		LOG.info("- day by date - ");
		
		ArrayList<Page> cache = getCache();
		JSONArray jsonArray = getJSON(cache, "dayByDate", dt);

		String s = jsonArray.toString();

		return Response.ok(s, MediaType.APPLICATION_JSON).build();
	}

	private ArrayList<Page> getCache() throws RepositoryException {
		ArrayList<Page> list = new ArrayList<Page>();

		try {
			//Bad code - do this correctly!
			final Session session = repository.loginService("searchService", null);
			try {
				final QueryManager queryMgr = session.getWorkspace().getQueryManager();
				final Query query = queryMgr.createQuery(QUERY, Query.JCR_SQL2);
				final QueryResult queryResult = query.execute();

				NodeIterator nodeIterator = queryResult.getNodes();

				/* Get Resource resolver using resource resolver factory. */
				final Map<String, Object> param = new HashMap<String, Object>();
				param.put(ResourceResolverFactory.SUBSERVICE, "searchService");
				final ResourceResolver resolver = resourceResolverFactory.getServiceResourceResolver(param);
				
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
			LOG.error("test");
			return null;
		}

		return list;
	}

	public JSONArray getJSON(ArrayList<Page> events, String filter, DateTime dt) throws JSONException {
		JSONArray array = new JSONArray();

		try {
			for(Page event : events) {
				JSONObject object = getJsonObject(event, filter, dt);
				if(object.getJSONArray("dates").length() > 0) {
					LOG.error(object.getJSONArray("dates").toString());
					array.put(object);
				}
			}
		}
		catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return array;
	}

	private JSONObject getJsonObject(Page event, String filter, DateTime dt) throws LoginException, JSONException, ParseException {
		JSONObject jsonObject = new JSONObject();

		final Map<String, Object> param = new HashMap<String, Object>();
		param.put(ResourceResolverFactory.SUBSERVICE, "searchService");
		final ResourceResolver resolver = resourceResolverFactory.getServiceResourceResolver(param);
		
		final Resource resource = resolver.getResource(event.getPath() + "/jcr:content");
		final ValueMap properties = resource.adaptTo(ValueMap.class);

		String[] dates = properties.get("jcr:datesRecurrence", String.class).split(",");
		String[] times = properties.get("jcr:timesRecurrence", String.class).split("\\],\\[");
		String[] durations = properties.get("jcr:durationsRecurrence", String.class).split(",");

		JSONArray dateArray = new JSONArray();

		for(int i=0; i<dates.length; i++) {
			JSONObject object = new JSONObject();

			Pattern pattern = Pattern.compile("^[A-Za-z0-9 :]+00");
			Matcher matcher = pattern.matcher(dates[i]);

			//Get today's date as object for comparing with later...
			LocalDate currentDate = new LocalDate();

			//Get date for one week away
			LocalDate oneWeekDate = currentDate.plusDays(7);

			if(matcher.find()) {
				//Get date object for event
				DateTimeFormatter formatter = DateTimeFormat.forPattern("E MMM d yyyy HH:mm:ss");
				LocalDate eventDate = formatter.parseLocalDate((matcher.group(0)));				

				switch(filter) {
				case "all": 
					//All future events
					if((eventDate.toDateTimeAtStartOfDay().isAfter(currentDate.toDateTimeAtStartOfDay())
							|| eventDate.toDateTimeAtStartOfDay().isEqual(currentDate.toDateTimeAtStartOfDay()))) {
						object = processDates(matcher, i, dates, times, durations);
						dateArray.put(object);
					}
					break;

				case "week":
					//All events for the coming week including the current day
					if((eventDate.toDateTimeAtStartOfDay().isAfter(currentDate.toDateTimeAtStartOfDay()) 
							|| eventDate.toDateTimeAtStartOfDay().isEqual(currentDate.toDateTimeAtStartOfDay()))
							&& eventDate.toDateTimeAtStartOfDay().isBefore(oneWeekDate.toDateTimeAtStartOfDay())) {
						object = processDates(matcher, i, dates, times, durations);
						dateArray.put(object);
					}
					break;
					
				case "weekByDate":
					//All events for the coming week including the current day	
					DateTime dtOneWeekDate = dt.plusDays(7);
					
					if((eventDate.toDateTimeAtStartOfDay().isAfter(dt.withTimeAtStartOfDay()) 
							|| eventDate.toDateTimeAtStartOfDay().isEqual(dt.withTimeAtStartOfDay()))
							&& eventDate.toDateTimeAtStartOfDay().isBefore(dtOneWeekDate.withTimeAtStartOfDay())) {
						object = processDates(matcher, i, dates, times, durations);
						dateArray.put(object);
					}
					break;

				case "day":
					//All events for the current day
					if(eventDate.toDateTimeAtStartOfDay().equals(currentDate.toDateTimeAtStartOfDay())) {
						object = processDates(matcher, i, dates, times, durations);
						dateArray.put(object);
					}
					break;
					
				case "dayByDate":
					//All events for the current day					
					if(eventDate.toDateTimeAtStartOfDay().equals(dt.withTimeAtStartOfDay())) {
						object = processDates(matcher, i, dates, times, durations);
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
