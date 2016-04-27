package uk.ac.nhm.nhm_www.core.impl.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.jcr.LoginException;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.jackrabbit.oak.commons.json.JsonObject;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.jcr.api.SlingRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import uk.ac.nhm.nhm_www.core.services.CreateXMLFeed;

@Component(immediate = true, metatype = false)
@Service (value = CreateXMLFeed.class)
public class CreateXMLFeedService implements CreateXMLFeed {
	private static final String JSON_PATH = "content/nhmwww/eventscontent";

	private JSONArray events = new JSONArray();
	private Session session;
	private Node root;
	
	@Reference
	private SlingRepository repository;
	@Reference
	private ResourceResolverFactory resolverFactory;
	
	@Override
	public void createXML() throws LoginException, RepositoryException, JSONException, ParseException {
		events = getJSON();
		
		if (events != null && events.length() > 0) {
			checkTodayEvents(events);
		}
	}
	
	/**
	 * Function to get the JSON from CRX
	 *  
	 * @throws RepositoryException 
	 * @throws LoginException 
	 * @throws JSONException 
	 */
	@SuppressWarnings("deprecation")
	private JSONArray getJSON() throws LoginException, RepositoryException, JSONException {
		session = repository.loginAdministrative(null);
		root = session.getRootNode();
		
		if (root.hasNode(JSON_PATH)) {
			Node eventsNode = root.getNode(JSON_PATH);
			
			if (eventsNode.hasProperty("events")) {
				JSONObject json = new JSONObject(eventsNode.getProperty("events").getString());
				events = json.getJSONArray("Events");
			}
		}

		return events;
	}
	
	/**
	 * Function to check if the events have a date which
	 * matches with today
	 * 
	 * @param events
	 * @throws JSONException
	 * @throws ParseException 
	 */
	private void checkTodayEvents(JSONArray events) throws JSONException, ParseException {
		final SimpleDateFormat sdf = new SimpleDateFormat("E MMM dd yyyy");
		
		Date today = new Date();
		today = sdf.parse(sdf.format(today));
		System.out.println("TODAY ---------=-=>" + today.toString());
		for (int i = 0; i < events.length(); i++) {
			JSONObject event = events.getJSONObject(i);
			JSONArray dates = event.getJSONArray("dates");
			if (dates != null && dates.length() > 0) {
				for (int j = 0; j < dates.length(); j++) {
					String dateString = dates.getString(j);
					if (dateString.length() > 0) {
						Date date = getDateParsed(dateString, sdf);
						System.out.println("DATEPARSED ---------=-=>");
						System.out.println(date.toString());
					}
				}
			}
		}
	}
	
	/**
	 * Helper function to parse the date retrieved from the JSON
	 * 
	 * @param dateString
	 * @param sdf
	 * @return
	 * @throws ParseException
	 */
	private Date getDateParsed (String dateString, SimpleDateFormat sdf) throws ParseException {		
		String[] parts = dateString.split(" ");
		String stringDateParsed = parts[0] + " " + parts[1] + " " + parts[2] + " " + parts[3];

		Date dateParsed = sdf.parse(stringDateParsed);
		
		return dateParsed;
	}
}
