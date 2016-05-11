package uk.ac.nhm.nhm_www.core.impl.services;

import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;

import javax.jcr.LoginException;
import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

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
	private ArrayList<Integer> dateIndex = new ArrayList<Integer>();
	private Session session;
	private Node root;
	
	@Reference
	private SlingRepository repository;
	@Reference
	private ResourceResolverFactory resolverFactory;
	
	@Override
	public void createXML() throws LoginException, RepositoryException, JSONException, ParseException, ParserConfigurationException, TransformerException {
		
		events = getJSON();
		
		if (events != null && events.length() > 0) {
			eventsParsed = getTodayEvents(events);
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
	 * @return ArrayList<EventPageDetail>
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
						String[] parts = dateString.split(" ");
						Date date = getDateParsed(parts, sdf);
						//Gets the index of the date and stored the event object
						if(date.compareTo(today) == 0) {
							dateIndex.add(getDateIndex(parts));
							eventsArray.add(getEventDetails(event));
							break;
						}
					}
				}
			}
		}
		return eventsArray;
	}
	
	/**
	 * Function to create the App Feed XML file
	 * 
	 * @param eventsParsed
	 * @throws ParserConfigurationException
	 * @throws PathNotFoundException
	 * @throws RepositoryException
	 * @throws TransformerException
	 */
	private void createXMLFromEvents(ArrayList<EventPageDetail> eventsParsed) throws ParserConfigurationException, PathNotFoundException, RepositoryException, TransformerException {
		final String visitorFeed = "visitorfeed.xml";
		
		Node content = root.getNode("content/nhmwww");
		Node events = null;
		Node node = null;
		
		DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder icBuilder;
        icBuilder = icFactory.newDocumentBuilder();
        Document doc = icBuilder.newDocument();
        //Feed element
        Element feed = doc.createElementNS(null, "feed");
        //Items element
        Element items = createItemsFromEvents(eventsParsed, doc);
        //Appends the items element to feed
        feed.appendChild(items);
        //Appends the feed to the document
        doc.appendChild(feed);
        StringWriter sw = new StringWriter();
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.transform(new DOMSource(doc), new StreamResult(sw));
		
		
		if (!content.hasNode(visitorFeed)) {
			events = content.addNode(visitorFeed, "nt:file");
			node = events.addNode("jcr:content","nt:resource");
			node.setProperty("jcr:mimeType", "application/xml");
		} else {
			events = content.getNode(visitorFeed);
			node = events.getNode("jcr:content");
		}
		node.setProperty("jcr:data", sw.toString());

		session.save();
	}
	
	private Element createItemsFromEvents(ArrayList<EventPageDetail> eventsParsed, Document doc) {
		Element items = doc.createElement("items");
		int eventsCounter = 0;
		
        for (EventPageDetail event : eventsParsed) {
        	createItemFromEventTime(event, eventsCounter, doc, items);
        	eventsCounter++;
        }
		return items;
	}
	
	private void createItemFromEventTime(EventPageDetail event, int eventsCounter, Document doc, Element items) {
		Element item = doc.createElement("item");
		
	}
	
	/**
	 * Helper function to populate the EventDetail object from a JSON
	 * 
	 * @param event
	 * @return EventPageDetail
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
		//eventDetail.setDates(createDatesArray(event.getJSONArray(dates)));
		//eventDetail.setAllDay(event.getJSONArray(allDay));
		eventDetail.setTimes(createTimesArray(event.getJSONArray(times)));
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
	 * Helper function to create the times Array
	 * 
	 * @param timesJson
	 * @return ArrayList<String>
	 * @throws JSONException
	 */
	private ArrayList<String> createTimesArray(JSONArray timesJson) throws JSONException {
		ArrayList<String> stringArray = new ArrayList<String>();
		
		System.out.println("TIMES: " + timesJson.toString());
		
		String[] parts = timesJson.toString().split("\"");
		for(int i = 1; i < parts.length - 1; i++) {
			switch (parts[i]) {
			case ",":
				break;
			case " ":
				stringArray.add("");
			default:
				stringArray.add(parts[i]);
				break;
			}
		}
		
		for (String part : stringArray) {
			System.out.println("PARTS: " + part);
		}
		
		return stringArray;
	}
	
	/**
	 * Helper function to parse the date retrieved from the JSON
	 * 
	 * @param parts
	 * @param sdf
	 * @return Date
	 * @throws ParseException
	 */
	private Date getDateParsed (String[] parts, SimpleDateFormat sdf) throws ParseException {		
		//parts[5].substring(0, parts[5].length() - 1) is needed because we attach the index to the date
		String stringDateParsed = parts[0] + " " + parts[1] + " " + parts[2] + " " + parts[5].substring(0, parts[5].length() - 1);

		Date dateParsed = sdf.parse(stringDateParsed);
		
		return dateParsed;
	}
	/**
	 * Helper function to get the dates index
	 * 
	 * @param parts
	 * @return Integer
	 */
	private Integer getDateIndex(String[] parts) {
		return Integer.parseInt(parts[5].substring(parts[5].length()));
	}
}
