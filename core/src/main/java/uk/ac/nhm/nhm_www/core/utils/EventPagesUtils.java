package uk.ac.nhm.nhm_www.core.utils;

import java.util.ArrayList;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.jcr.ValueFormatException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import uk.ac.nhm.nhm_www.core.model.EventPageDetail;

public class EventPagesUtils {
	private Node root;

	// Gets the nodes under EVENTS_PATH and EXHIBITIONS_PATH
	public void getEventsDetails(Session session, String eventsPath, String exhibitionsPath) throws RepositoryException, JSONException  {		
		ArrayList<EventPageDetail> eventsArray = new ArrayList<EventPageDetail>();
		
		root = session.getRootNode();
		
		//Gets the events 
		Node eventsNode = root.getNode(eventsPath);
		eventsArray = populateEventsArray(eventsNode, eventsArray);

		//Gets the exhibitions 
		Node exhibitionsNode = root.getNode(exhibitionsPath);
		eventsArray = populateEventsArray(exhibitionsNode, eventsArray);

		createFeed(eventsArray, session);
	}
	
	private ArrayList<EventPageDetail> populateEventsArray (Node node, ArrayList<EventPageDetail> eventsArray) throws RepositoryException {
		final String primaryType = "jcr:primaryType";
		final String cqPage = "cq:Page";
		
		NodeIterator iterator = node.getNodes();
		
		while (iterator.hasNext()) {
			Node currentNode = iterator.nextNode();
			if (currentNode.getProperty(primaryType).getString().equals(cqPage)) {
				NodeIterator pageIterator = currentNode.getNodes();
				while (pageIterator.hasNext()) {
					Node iteratedNode = pageIterator.nextNode();
					eventsArray.add(populateEventDetail(iteratedNode));
				}
			}
		}
		
		return eventsArray;
	}

	// Retrieves the Page properties and populates the EventsPageDetail Object
	private EventPageDetail populateEventDetail(Node iteratedNode) throws ValueFormatException, PathNotFoundException, RepositoryException  {
		final String eventPagePath = "./jcr:eventPagePath";
		final String eventSelect = "eventSelect";
		final String eventTitle = "jcr:eventTitle";
		final String eventDescription = "jcr:eventDescription";
		final String eventVenue = "eventVenue";
		final String eventGroup = "eventGroup";
		final String eventTileLink = "eventTileLink";
		final String tags = "./cq:tags";
		final String keywords = "keywords";
		final String datesRecurrence = "jcr:datesRecurrence";
		final String allDayRecurrence = "jcr:allDayRecurrence";
		final String timesRecurrence = "jcr:timesRecurrence";
		final String adultPrice = "adultPrice";
		final String concessionPrice = "concessionPrice";
		final String memberPrice = "memberPrice";
		final String familyPrice = "familyPrice";
		final String customPrice = "customPrice";
		final String eventListingPrice = "eventListingPrice";
		final String fileReference = "fileReference";
		final String ctaLink = "ctaLink";
		final String subject = "./cq:subject";
		final String capacity = "./capacity";
		final String eventDuration = "./eventDuration";
		final String subjectScience = "./cq:subjectScience";
		final String speakerDetails = "./speakerDetails";
		
		EventPageDetail eventDetail = new EventPageDetail();
		
		// Common Event Values
		if (iteratedNode.hasProperty(eventPagePath)) {
			eventDetail.setEventPagePath(iteratedNode.getProperty(eventPagePath).getString());
		}
		if (iteratedNode.hasProperty(eventSelect)) {
			eventDetail.setEventType(iteratedNode.getProperty(eventSelect).getString());
		}
		if (iteratedNode.hasProperty(eventTitle)) {
			eventDetail.setTitle(iteratedNode.getProperty(eventTitle).getString());
		}
		if (iteratedNode.hasProperty(eventDescription)) {
			eventDetail.setDescription(iteratedNode.getProperty(eventDescription).getString());
		}
		if (iteratedNode.hasProperty(eventVenue)) {
			eventDetail.setEventVenue(iteratedNode.getProperty(eventVenue).getString());
		}
		if (iteratedNode.hasProperty(eventGroup)) {
			eventDetail.setEventGroup(iteratedNode.getProperty(eventGroup).getString());
		}
		if (iteratedNode.hasProperty(eventTileLink)) {
			eventDetail.setEventTileLink(iteratedNode.getProperty(eventTileLink).getString());
		}
		if (iteratedNode.hasProperty(tags)) {
			eventDetail.setTags(createArrayFromValues(iteratedNode.getProperty(tags).getValues()));
		}
		if (iteratedNode.hasProperty(keywords)) {
			eventDetail.setKeywords(iteratedNode.getProperty(keywords).getString());
		}
		if (iteratedNode.hasProperty(datesRecurrence)) {
			eventDetail.setDates(createDatesArray(iteratedNode.getProperty(datesRecurrence).getString()));
		}
		if (iteratedNode.hasProperty(allDayRecurrence)) {
			eventDetail.setAllDay(createAllDayArray(iteratedNode.getProperty(allDayRecurrence).getString()));
		}
		if (iteratedNode.hasProperty(timesRecurrence)) {
			eventDetail.setTimes(createTimesArray(iteratedNode.getProperty(timesRecurrence).getString()));
		}
		if (iteratedNode.hasProperty(adultPrice)) {
			eventDetail.setAdultPrice(iteratedNode.getProperty(adultPrice).getString());
		}
		if (iteratedNode.hasProperty(concessionPrice)) {
			eventDetail.setConcessionPrice(iteratedNode.getProperty(concessionPrice).getString());
		}
		if (iteratedNode.hasProperty(memberPrice)) {
			eventDetail.setMemberPrice(iteratedNode.getProperty(memberPrice).getString());
		}
		if (iteratedNode.hasProperty(familyPrice)) {
			eventDetail.setFamilyPrice(iteratedNode.getProperty(familyPrice).getString());
		}
		if (iteratedNode.hasProperty(customPrice)) {
			eventDetail.setCustomPrice(iteratedNode.getProperty(customPrice).getString());
		}
		if (iteratedNode.hasProperty(eventListingPrice)) {
			eventDetail.setEventListingPrice(iteratedNode.getProperty(eventListingPrice).getString());
		}
		if (iteratedNode.hasProperty(fileReference)) {
			eventDetail.setImageLink(iteratedNode.getProperty(fileReference).getString());
		}
		if (iteratedNode.hasProperty(ctaLink)) {
			eventDetail.setCtaLink(iteratedNode.getProperty(ctaLink).getString());
		}
		// School Event Values
		if (iteratedNode.hasProperty(subject)) {
			eventDetail.setSubject(createArrayFromValues(iteratedNode.getProperty(subject).getValues()));
		}
		if (iteratedNode.hasProperty(capacity)) {
			eventDetail.setCapacity(iteratedNode.getProperty(capacity).getString());
		}
		if (iteratedNode.hasProperty(eventDuration)) {
			eventDetail.setEventDuration(iteratedNode.getProperty(eventDuration).getString());
		}
		// Science Event Values
		if (iteratedNode.hasProperty(subjectScience)) {
			eventDetail.setScienceSubject(createArrayFromValues(iteratedNode.getProperty(subjectScience).getValues()));
		}
		if (iteratedNode.hasProperty(speakerDetails)) {
			eventDetail.setSpeakerDetails(iteratedNode.getProperty(speakerDetails).getString());
		}
		return eventDetail;
	}

	// Creates the JSON
	private void createFeed(ArrayList<EventPageDetail> eventsArray, Session session) throws JSONException, PathNotFoundException, RepositoryException  {
		final String eventscontent = "eventscontent";
		
		JSONObject eventsObject = new JSONObject();
		JSONArray eventsJSONArray = new JSONArray();

		for (EventPageDetail event : eventsArray) {
			JSONObject events = new JSONObject();

			// Common Event Values
			events.put("eventPagePath", event.getEventPagePath());
			events.put("eventType", event.getEventType());
			events.put("title", event.getTitle());
			events.put("description", event.getDescription());
			events.put("venue", event.getEventVenue());
			events.put("group", event.getEventGroup());
			events.put("tileLink", event.getEventTileLink());
			events.put("tags", event.getTags());
			events.put("keywords", event.getKeywords());
			events.put("dates", event.getDates());
			events.put("allDay", event.getAllDay());
			events.put("times", event.getTimes());
			events.put("adultPrice", event.getAdultPrice());
			events.put("concessionPrice", event.getConcessionPrice());
			events.put("memberPrice", event.getMemberPrice());
			events.put("familyPrice", event.getFamilyPrice());
			events.put("customPrice", event.getCustomPrice());
			events.put("eventListingPrice", event.getEventListingPrice());
			events.put("imageLink", event.getImageLink());
			events.put("ctaLink", event.getCtaLink());
			// School Event Values
			events.put("subject", event.getSubject());
			events.put("capacity", event.getCapacity());
			events.put("eventDuration", event.getEventDuration());
			// Science Event Values
			events.put("scienceSubject", event.getScienceSubject());
			events.put("speakerDetails", event.getSpeakerDetails());

			eventsJSONArray.put(events);
		}
		eventsObject.put("Events", eventsJSONArray);

		Node content = root.getNode("content/nhmwww");
		Node events = null;
		if (!content.hasNode(eventscontent)) {
			events = content.addNode(eventscontent, "nt:unstructured");
		} else {
			events = content.getNode(eventscontent);
		}
		events.setProperty("events", eventsObject.toString());

		session.save();
	}

	// Helper function to create the dates Array
	private ArrayList<String> createDatesArray(String stringValue) {
		ArrayList<String> stringArray = new ArrayList<String>();

		String[] values = stringValue.split(",");

		for (int i = 0; i < values.length; i++) {
			stringArray.add(values[i]);
		}
		return stringArray;
	}

	// Helper function to create the All Day Array
	private ArrayList<String> createAllDayArray(String stringValue) {
		ArrayList<String> stringArray = new ArrayList<String>();

		stringValue = stringValue.replaceAll("[^\\w\\s\\,]", "");

		String[] values = stringValue.split(",");

		for (int i = 0; i < values.length; i++) {
			stringArray.add(values[i]);
		}
		return stringArray;
	}

	// Helper function to create the Times Array
	private ArrayList<String> createTimesArray(String stringValue) {
		ArrayList<String> stringArray = new ArrayList<String>();

		stringValue = stringValue.replace("\"", "");
		stringValue = stringValue.substring(1, stringValue.length() - 1);

		String[] values = stringValue.split("],");

		for (int i = 0; i < values.length; i++) {
			values[i] = values[i].replaceAll("[^\\w\\s\\,\\:]", "");
			stringArray.add(values[i]);
		}
		return stringArray;
	}

	// Helper function to create the tags Array
	private ArrayList<String> createArrayFromValues(Value[] values) {
		ArrayList<String> stringArray = new ArrayList<String>();
		
		for (int i = 0; i < values.length; i++) {
			stringArray.add(values[i].toString());
		}
		return stringArray;
	}
}
