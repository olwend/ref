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
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
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

	private static final String ADDRESS_LOCALITY = "London";
	private static final String ADDRESS_REGION = "United Kingdom";
	private static final String POSTAL_CODE = "SW7 5BD";
	private static final String STREET_ADDRESS = "Cromwell Road";
	private static final String LOCATION_NAME = "Natural History Museum";
	private static final String LOCATION_URL = "http://www.nhm.ac.uk";
	
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
		LOG.info("- all");
		ArrayList<Page> cache = getCache();
		JSONArray jsonArray = getJSON(cache, "all", null);

		String s = jsonArray.toString();

		return Response.ok(s, MediaType.APPLICATION_JSON).build();
	}
	
	@GET
	@Path("/month/{year}/{month}")
	@Produces("application/json")
	@Override
	public Response getMonth(
			@PathParam("year") int year,
			@PathParam("month") String month) throws RepositoryException, JSONException {
		LOG.info("- month - year=" + year + ", month=" + month);
		
		int monthInt = -1;
		ArrayList<Page> cache = getCache();
		JSONArray jsonArray;
		
		try {
			monthInt = Integer.parseInt(month);
		}
		catch (Exception e) {
			LOG.error("Exception", e);
		}
		
		if(monthInt > -1) {
			DateTime dt = new DateTime(year, monthInt, 1, 0, 0);
			jsonArray = getJSON(cache, "month", dt);
		} else {
			month = month.toLowerCase();
			switch(month) {
				case "january": monthInt = 1; break;
				case "february": monthInt = 2; break;
				case "march": monthInt = 3; break;
				case "april": monthInt = 4; break;
				case "may": monthInt = 5; break;
				case "june": monthInt = 6; break;
				case "july": monthInt = 7; break;
				case "august": monthInt = 8; break;
				case "september": monthInt = 9; break;
				case "october": monthInt = 10; break;
				case "november": monthInt = 11; break;
				case "december": monthInt = 12; break;
			}
			
			DateTime dt = new DateTime(year, monthInt, 1, 0, 0);
			jsonArray = getJSON(cache, "month", dt);
		}
		
		String s = jsonArray.toString();

		return Response.ok(s, MediaType.APPLICATION_JSON).build();
	}
	
	@GET
	@Path("/week")
	@Produces("application/json")
	@Override
	public Response getWeek() throws RepositoryException, JSONException  {
		LOG.info("- week");
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
		
		LOG.info("- week by date - year=" + year + ", month=" + month + ", day=" + day);
		
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
		LOG.info("- day");
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
		
		LOG.info("- day by date - year=" + year + ", month=" + month + ", day=" + day);
		
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
			return null;
		}

		return list;
	}

	public JSONArray getJSON(ArrayList<Page> events, String filter, DateTime dt) throws JSONException {
		JSONArray array = new JSONArray();

		try {
			for(Page event : events) {
				JSONArray array2 = getJsonObject(event, filter, dt);
				if(!array2.isNull(0)) {
					for(int i=0; i<array2.length(); i++) {
						array.put(array2.get(i));
					}
				}
			}
		}
		catch (Exception e) {
			//Fix this exception logging!
			//LOG.error("Exception", e);
		}
		return array;
	}

	private JSONArray getJsonObject(Page event, String filter, DateTime dt) throws LoginException, JSONException, ParseException {
		

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
			//JSONObject object = new JSONObject();
			

			Pattern pattern = Pattern.compile("^[A-Za-z0-9 :]+00");
			Matcher matcher = pattern.matcher(dates[i]);

			//Get today's date as object for comparing with later...
			LocalDate currentDate = new LocalDate();
//
//			//Get date for one week away
//			LocalDate oneWeekDate = currentDate.plusDays(7);
//
			if(matcher.find()) {
				//Get date object for event
				DateTimeFormatter formatter = DateTimeFormat.forPattern("E MMM d yyyy HH:mm:ss");
				LocalDate eventDate = formatter.parseLocalDate((matcher.group(0)));				

				switch(filter) {
//				case "all": 
//					//All future events
//					if((eventDate.toDateTimeAtStartOfDay().isAfter(currentDate.toDateTimeAtStartOfDay())
//							|| eventDate.toDateTimeAtStartOfDay().isEqual(currentDate.toDateTimeAtStartOfDay()))) {
//						object = processDates(matcher, i, dates, times, durations);
//						dateArray.put(object);
//					}
//					break;
//					
//				case "month":
//					//All events for a specified month
//					DateTime dtOneMonthDate = dt.plusMonths(1);
//					
//					if((eventDate.toDateTimeAtStartOfDay().isAfter(dt.withTimeAtStartOfDay()) 
//							|| eventDate.toDateTimeAtStartOfDay().isEqual(dt.withTimeAtStartOfDay()))
//							&& eventDate.toDateTimeAtStartOfDay().isBefore(dtOneMonthDate.withTimeAtStartOfDay())) {
//						object = processDates(matcher, i, dates, times, durations);
//						dateArray.put(object);
//					}
//					break;
//					
//				case "week":
//					//All events for the coming week including the current day
//					if((eventDate.toDateTimeAtStartOfDay().isAfter(currentDate.toDateTimeAtStartOfDay()) 
//							|| eventDate.toDateTimeAtStartOfDay().isEqual(currentDate.toDateTimeAtStartOfDay()))
//							&& eventDate.toDateTimeAtStartOfDay().isBefore(oneWeekDate.toDateTimeAtStartOfDay())) {
//						object = processDates(matcher, i, dates, times, durations);
//						dateArray.put(object);
//					}
//					break;
//					
//				case "weekByDate":
//					//All events for a week-long period with a specified start date
//					DateTime dtOneWeekDate = dt.plusDays(7);
//					
//					if((eventDate.toDateTimeAtStartOfDay().isAfter(dt.withTimeAtStartOfDay()) 
//							|| eventDate.toDateTimeAtStartOfDay().isEqual(dt.withTimeAtStartOfDay()))
//							&& eventDate.toDateTimeAtStartOfDay().isBefore(dtOneWeekDate.withTimeAtStartOfDay())) {
//						object = processDates(matcher, i, dates, times, durations);
//						dateArray.put(object);
//					}
//					break;
//
				case "day":
					//All events for the current day
					if(eventDate.toDateTimeAtStartOfDay().equals(currentDate.toDateTimeAtStartOfDay())) {
						int x = Integer.parseInt(dates[i].substring(dates[i].length()-1, dates[i].length()));
						String[] time = times[x].split(",");
						
						for(int j=0; j<time.length; j++) {
							JSONObject jsonObject = new JSONObject();
							DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("E MMM d yyyy HH:mm:ss");
							
							jsonObject.put("@context", "http://schema.org");
							jsonObject.put("@type", "Event");
							jsonObject.put("location", getLocationJsonObject());
							jsonObject.put("name", properties.get("jcr:eventTitle", String.class));
							jsonObject.put("description", properties.get("jcr:eventDescription", String.class));
							
							LocalDate dt1 = dateFormatter.parseLocalDate(matcher.group(0));
							
							String t = time[j].replaceAll("\\[|\"|\\]|\\\\", "");
							
							if(t.equals("")) {
								jsonObject.put("startDate", dt1.toString());
								jsonObject.put("endDate", dt1.toString());
							} else {
								jsonObject.put("startDate", dt1.toString() + "T" + t);
								LocalTime localTime = new LocalTime(Integer.valueOf(t.split(":")[0]), Integer.valueOf(t.split(":")[1]));
								LocalDate localDate = new LocalDate();
								localTime.plusMinutes(Integer.valueOf(durations[x].replaceAll("\\[|\\]",  "")));
							}
							
							dateArray.put(jsonObject);
						}
					}
					break;
					
//				case "dayByDate":
//					//All events for a specified day				
//					if(eventDate.toDateTimeAtStartOfDay().equals(dt.withTimeAtStartOfDay())) {
//						object = processDates(matcher, i, dates, times, durations);
//						dateArray.put(object);
//					}
//					break;
//				}
//			} else {
//				object.put("date", dates[i]);
//			}
		}}}

		

		return dateArray;
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
	
	private JSONObject getLocationJsonObject() throws JSONException {
		JSONObject locationObject = new JSONObject();
		
		JSONObject addressObject = new JSONObject();
		addressObject.put("@type", "PostalAddress");
		addressObject.put("addressLocality", ADDRESS_LOCALITY);
		addressObject.put("addressRegion", ADDRESS_REGION);
		addressObject.put("postalCode", POSTAL_CODE);
		addressObject.put("streetAddress", STREET_ADDRESS);

		locationObject.put("@type", "Place");
		locationObject.put("name", LOCATION_NAME);
		locationObject.put("url", LOCATION_URL);
		locationObject.put("address", addressObject);
		
		return locationObject;
	}
}
