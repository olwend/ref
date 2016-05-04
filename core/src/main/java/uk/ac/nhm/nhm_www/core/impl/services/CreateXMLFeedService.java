package uk.ac.nhm.nhm_www.core.impl.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.jcr.LoginException;
import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.jcr.api.SlingRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import uk.ac.nhm.nhm_www.core.model.EventPageDetail;
import uk.ac.nhm.nhm_www.core.services.CreateXMLFeed;

@Component(immediate = true, metatype = false)
@Service (value = CreateXMLFeed.class)
public class CreateXMLFeedService implements CreateXMLFeed {
	private static final String JSON_PATH = "content/nhmwww/eventscontent";

	private JSONArray events = new JSONArray();
	private ArrayList<EventPageDetail> eventsParsed = new ArrayList<EventPageDetail>();
	private Session session;
	private Node root;
	
	@Reference
	private SlingRepository repository;
	@Reference
	private ResourceResolverFactory resolverFactory;
	
	@Override
	public void createXML() throws LoginException, RepositoryException, JSONException, ParseException, ParserConfigurationException {
		
		events = getJSON();
		
		if (events != null && events.length() > 0) {
			eventsParsed = getTodayEvents(events);
			System.out.println("JUAN TEST: "+eventsParsed.toString());
			if (!eventsParsed.isEmpty()) {
				createXMLFromEvents(eventsParsed);
			}
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
	 * matches with today and retrieve them
	 * 
	 * @param events
	 * @return 
	 * @throws JSONException
	 * @throws ParseException 
	 */
	private ArrayList<EventPageDetail> getTodayEvents(JSONArray events) throws JSONException, ParseException {
		final SimpleDateFormat sdf = new SimpleDateFormat("E MMM dd yyyy");
		ArrayList<EventPageDetail> eventsArray = new ArrayList<EventPageDetail>();
		
		//Gets today's date and parse it
		Date today = new Date();
		today = sdf.parse(sdf.format(today));
		//Checks if there is a date in the events which matches today
		for (int i = 0; i < events.length(); i++) {
			JSONObject event = events.getJSONObject(i);
			JSONArray dates = event.getJSONArray("dates");
			if (dates != null && dates.length() > 0) {
				for (int j = 0; j < dates.length(); j++) {
					String dateString = dates.getString(j);
					if (dateString.length() > 0) {
						Date date = getDateParsed(dateString, sdf);
						if(date.compareTo(today)==0){ 
							System.out.println("MATCHES: " + date.toString());
							eventsArray.add(getEventDetails(event));
						}
					}
				}
			}
		}
		return eventsArray;
	}
	
	private void createXMLFromEvents(ArrayList<EventPageDetail> eventsParsed) throws ParserConfigurationException, PathNotFoundException, RepositoryException {
		
		DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder icBuilder = icFactory.newDocumentBuilder();	    
	    Document doc = icBuilder.newDocument();
		Element feed = doc.createElementNS(null,"feed");
		doc.appendChild(feed);
		Element items = doc.createElement("items");
		items.setAttribute("test", "HELLO WORLD");
		feed.appendChild(items);
		DOMSource source = new DOMSource(doc);
		System.out.println("FEED "+feed.toString());
		
		final String visitorFeed = "visitorfeed.xml";
		
		Node content = root.getNode("content/nhmwww");
		Node events = null;
		Node node = null;
		
		if (!content.hasNode(visitorFeed)) {
			events = content.addNode(visitorFeed, "nt:file");
			node = events.addNode("jcr:content","nt:resource");
			node.setProperty("jcr:mimeType", "application/xml");
		} else {
			events = content.getNode(visitorFeed);
			node = events.getNode("jcr:content");
		}
		node.setProperty("jcr:data", source.toString());

		session.save();
	}
	
	/**
	 * Helper function to populate the EventDetail object from a JSON
	 * 
	 * @param event
	 * @return
	 * @throws JSONException
	 */
	private EventPageDetail getEventDetails(JSONObject event) throws JSONException {
		final String eventPagePath = "eventPagePath";
		final String eventType = "eventType";
		final String title = "title";
		final String description = "description";
		final String venue = "venue";
		final String group = "group";
		final String tileLink = "tileLink";
		final String tags = "tags";
		final String keywords = "keywords";
		final String dates = "dates";
		final String allDay = "allDay";
		final String times = "times";
		final String adultPrice = "adultPrice";
		final String concessionPrice = "concessionPrice";
		final String memberPrice = "memberPrice";
		final String familyPrice = "familyPrice";
		final String customPrice = "customPrice";
		final String imageLink = "imageLink";
		final String ctaLink = "ctaLink";
		final String subject = "subject";
		final String capacity = "capacity";
		final String eventDuration = "eventDuration";
		final String scienceSubject = "scienceSubject";
		final String speakerDetails = "speakerDetails";
		
		EventPageDetail eventDetail = new EventPageDetail();
		
		// Common Event Values
		eventDetail.setEventPagePath(event.getString(eventPagePath));
		eventDetail.setEventType(event.getString(eventType));
		eventDetail.setTitle(event.getString(title));
		eventDetail.setDescription(event.getString(description));
		eventDetail.setEventVenue(event.getString(venue));
		eventDetail.setEventGroup(event.getString(group));
		eventDetail.setEventTileLink(event.getString(tileLink));
		//eventDetail.setTags(event.getJSONArray(tags));
		eventDetail.setKeywords(event.getString(keywords));
		//eventDetail.setDates(event.getJSONArray(dates));
		//eventDetail.setAllDay(event.getJSONArray(allDay));
		//eventDetail.setTimes(event.getJSONArray(times));
		eventDetail.setAdultPrice(event.getString(adultPrice));
		eventDetail.setConcessionPrice(event.getString(concessionPrice));
		eventDetail.setMemberPrice(event.getString(memberPrice));
		eventDetail.setFamilyPrice(event.getString(familyPrice));
		eventDetail.setCustomPrice(event.getString(customPrice));
		eventDetail.setImageLink(event.getString(imageLink));
		eventDetail.setCtaLink(event.getString(ctaLink));
		
		// School Event Values
		//eventDetail.setSubject(event.getJSONArray(subject));
		eventDetail.setCapacity(event.getString(capacity));
		eventDetail.setEventDuration(event.getString(eventDuration));
		
		// Science Event Values
		//eventDetail.setScienceSubject(event.getJSONArray(scienceSubject));
		eventDetail.setSpeakerDetails(event.getString(speakerDetails));

		return eventDetail;
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
		//parts[5].substring(0, parts[5].length() - 1) is needed because we attach the index to the date
		String stringDateParsed = parts[0] + " " + parts[1] + " " + parts[2] + " " + parts[5].substring(0, parts[5].length() - 1);

		Date dateParsed = sdf.parse(stringDateParsed);
		
		return dateParsed;
	}
}
