package uk.ac.nhm.nhm_www.core.utils;

import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import uk.ac.nhm.nhm_www.core.model.EventPageDetail;

public class CreateXMLFeedUtils {
	
	private ArrayList<Integer> dateIndex = new ArrayList<Integer>();
	private Date today = new Date();
	
	/**
	 * Function to check if the events have a date which
	 * matches with today and retrieve them
	 * 
	 * @param events
	 * @return ArrayList<EventPageDetail>
	 * @throws JSONException
	 * @throws ParseException 
	 */
	public ArrayList<EventPageDetail> getTodayEvents(JSONArray events) throws JSONException, ParseException {
		final SimpleDateFormat sdf = new SimpleDateFormat("E MMM dd yyyy");
		ArrayList<EventPageDetail> eventsArray = new ArrayList<EventPageDetail>();
		//Clears the dateIndex array
		dateIndex.clear();
		
		//Gets today's date and parse it
		Date todayParsed = sdf.parse(sdf.format(today));
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
						if(date.compareTo(todayParsed) == 0) {
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
	 * @throws ParseException 
	 */
	public void storeXMLFromEvents(ArrayList<EventPageDetail> eventsParsed, Node root, Session session) throws ParserConfigurationException, PathNotFoundException, RepositoryException, TransformerException, ParseException {
		final String visitorFeed = "visitorfeed.xml";
		final String contentUrl = "content/nhmwww";
		final String ntFile = "nt:file";
		final String jcrContent = "jcr:content";
		final String ntResource = "nt:resource";
		final String jcrMimeType = "jcr:mimeType";
		final String applicationXML = "application/xml";
		final String jcrData = "jcr:data";
		final String emptyFeed = "<feed/>";
		
		Node content = root.getNode(contentUrl);
		Node events = null;
		Node node = null;
				
		if (!content.hasNode(visitorFeed)) {
			events = content.addNode(visitorFeed, ntFile);
			node = events.addNode(jcrContent, ntResource);
			node.setProperty(jcrMimeType, applicationXML);
		} else {
			events = content.getNode(visitorFeed);
			node = events.getNode(jcrContent);
		}

		if (!eventsParsed.isEmpty()) {
			node.setProperty(jcrData, createXMLFromEvents(eventsParsed));
		} else {
			node.setProperty(jcrData, emptyFeed);
		}

		session.save();
		session.refresh(false);
	}
	
	/**
	 * Function to create the App Feed XML file
	 * 
	 * @param eventsParsed
	 * @throws ParserConfigurationException
	 * @throws PathNotFoundException
	 * @throws RepositoryException
	 * @throws TransformerException
	 * @throws ParseException 
	 */
	private String createXMLFromEvents(ArrayList<EventPageDetail> eventsParsed) throws ParserConfigurationException, PathNotFoundException, RepositoryException, TransformerException, ParseException {		
		DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder icBuilder;
        icBuilder = icFactory.newDocumentBuilder();
        Document doc = icBuilder.newDocument();
        //Feed element
        Element feed = doc.createElement("feed");
        //Items element
        Element items = createItemsFromEvents(eventsParsed, doc);
        //Appends the items element to feed
        feed.appendChild(items);
        //Appends the feed to the document
        doc.appendChild(feed);
        
        //Needed to transform to String
        StringWriter sw = new StringWriter();
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.transform(new DOMSource(doc), new StreamResult(sw));
		
		return sw.toString();
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
		return Integer.parseInt(parts[5].substring(parts[5].length() - 1));
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
		final String title = "title";
		final String description = "description";
		final String venue = "venue";
		final String tileLink = "tileLink";
		final String tags = "tags";
		final String keywords = "keywords";
		final String times = "times";
		final String eventListingPrice = "eventListingPrice";
		final String imageLink = "imageLink";
		
		EventPageDetail eventDetail = new EventPageDetail();
		
		//Needed Event Values for creating the XML
		eventDetail.setEventPagePath(event.getString(eventPagePath));
		eventDetail.setTitle(event.getString(title));
		eventDetail.setDescription(event.getString(description));
		eventDetail.setEventVenue(event.getString(venue));
		eventDetail.setEventTileLink(event.getString(tileLink));
		eventDetail.setTags(createTagsArray(event.getJSONArray(tags).toString()));
		eventDetail.setKeywords(event.getString(keywords));
		eventDetail.setTimes(createTimesArray(event.getJSONArray(times).toString()));
		eventDetail.setEventListingPrice(event.getString(eventListingPrice));
		eventDetail.setImageLink(event.getString(imageLink));
		
		return eventDetail;
	}
	
	/**
	 * Helper function to retrieve the tags form the JSON
	 * 
	 * @param tagsJson
	 * @return ArrayList<String>
	 * @throws JSONException
	 */
	private ArrayList<String> createTagsArray(String tagsJson) throws JSONException {
		ArrayList<String> stringArray = new ArrayList<String>();
		tagsJson = tagsJson.replaceAll("[^\\w\\s\\,\\/\\:\\-]", "");
		String[] tags = tagsJson.toString().split(",");		
		
		for (String tag : tags) {
			stringArray.add(tag);
		}
		
		return stringArray;
	}
	
	/**
	 * Helper function to create the times Array
	 * 
	 * @param timesJson
	 * @return ArrayList<String>
	 * @throws JSONException
	 */
	private ArrayList<String> createTimesArray(String timesJson) throws JSONException {
		ArrayList<String> stringArray = new ArrayList<String>();
		String[] parts = timesJson.split("\"");
		
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
		return stringArray;
	}
	
	/**
	 * Function to create the "items" label
	 * 
	 * @param eventsParsed
	 * @param doc
	 * @return Element
	 * @throws ParseException
	 */
	private Element createItemsFromEvents(ArrayList<EventPageDetail> eventsParsed, Document doc) throws ParseException {
		Element items = doc.createElement("items");
		int eventsCounter = 0;
		
        for (EventPageDetail event : eventsParsed) {
        	createItemFromEventTime(event, eventsCounter, doc, items);
        	eventsCounter++;
        }
		return items;
	}
	
	/**
	 * Function to populate the "items" label with "item"
	 * 
	 * @param event
	 * @param eventsCounter
	 * @param doc
	 * @param items
	 * @throws ParseException
	 */
	private void createItemFromEventTime(EventPageDetail event, int eventsCounter, Document doc, Element items) throws ParseException {
		final SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
		
		final String uid = "uid";
		final String name = "name";
		final String dtstart = "dtstart";
		final String dtend = "dtend";
		final String url = "url";
		final String image_url = "image_url";
		final String category = "category";
		final String description = "description";
		final String custom_1 = "custom_1";
		final String custom_2 = "custom_2";
		final String custom_3 = "custom_3";
		final String allday = "allday";
		
		ArrayList<String> eventTimes = event.getTimes();
		
		String timeString = eventTimes.get(dateIndex.get(eventsCounter));
		String [] times = timeString.split(",");

		String todayParsed = sdf.format(today);

		int uidCounter = 0;
		
		for (String singleTime : times) {
			Element item = doc.createElement("item");
			String uidValue = "" + String.valueOf(System.currentTimeMillis()) + eventsCounter + uidCounter;
			//Sets the uid
			item.appendChild(getEventElements(doc, item, uid, uidValue));
			//Sets the name
			item.appendChild(getEventElements(doc, item, name, event.getTitle()));
			//Sets the dtstart and dtend
			if (singleTime.equals("")) {
				item.appendChild(getEventElements(doc, item, dtstart, todayParsed));
				item.appendChild(getEventElements(doc, item, dtend, todayParsed));
			} else {
				item.appendChild(getEventElements(doc, item, dtstart, todayParsed +  " " + singleTime));
				item.appendChild(getEventElements(doc, item, dtend, todayParsed +  " " + setEventDuration(singleTime)));
			}
			//Sets the url
			if (event.getEventTileLink().equals("")) {
				item.appendChild(getEventElements(doc, item, url, event.getEventPagePath() + ".html"));
			} else {
				item.appendChild(getEventElements(doc, item, url, event.getEventTileLink()));
			}
			//Sets the image url
			item.appendChild(getEventElements(doc, item, image_url, event.getImageLink()));
			//Creates the placemarks
			Element placemarks = doc.createElement("placemarks");
			Element placemark = doc.createElement("placemark");
			placemark.appendChild(getEventElements(doc, placemark, name, event.getEventVenue()));
			placemarks.appendChild(placemark);
			item.appendChild(placemarks);
			//Creates the categories and sets the Event types
			Element categories = doc.createElement("categories");
			categories.appendChild(getEventElements(doc, categories, category, getTypeTags(event.getTags(), "Event")));
			item.appendChild(categories);
			//Sets the description
			item.appendChild(createCDATA(doc, item, description, event.getDescription()));
			//Sets the audience in custom_1
			item.appendChild(getEventElements(doc, item, custom_1,  getTypeTags(event.getTags(), "Audience")));
			//Sets the listing price in custom_2
			item.appendChild(getEventElements(doc, item, custom_2,  event.getEventListingPrice()));
			//Sets the time in custom_3
			if (singleTime.equals("")) {
				item.appendChild(getEventElements(doc, item, custom_3,  "All day"));
			} else {
				item.appendChild(getEventElements(doc, item, custom_3,  singleTime));
			}
			items.appendChild(item);
			if (singleTime.equals("")) {
				items.appendChild(getEventElements(doc, items, allday,  "true"));
			} 
			uidCounter++;
		}
	}
	
	/**
	 * Helper function to populate the "item" label with the event details
	 * 
	 * @param doc
	 * @param element
	 * @param name
	 * @param value
	 * @return Element
	 */
    private static Element getEventElements(Document doc, Element element, String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }
    
    
    /**
     * Helper function to create CDATA section (needed for the description field)
     * 
     * @param doc
     * @param element
     * @param name
     * @param value
     * @return Element
     */
    private Element createCDATA(Document doc, Element element, String name, String value) {
            Element node = doc.createElement(name);
            node.appendChild(doc.createCDATASection(value));
            return node;
    }
	 
    /**
     * Helper function to retrieve the tags for Event and Audience
     * 
     * @param tags
     * @param type
     * @return ArrayList<String>
     */
    private String getTypeTags(ArrayList<String> tags, String type) {
    	String types = "";
    	if (!tags.isEmpty()) {
        	for (String tag: tags) {
        		if (!tag.equals("")) {
            		String[] tokens = tag.split("/");
            		String[] headers = tokens[0].split(":");
                    
            		if (type.equals("Event") && headers[1].equals("events")) {
            			types = getEventTags(tokens[tokens.length - 1], types);
                    } else if (type.equals("Audience") && headers[1].equals("audience")) {
            			types = getEventTags(tokens[tokens.length - 1], types);
                    }
        		}
        	}
    	}
    	return types;
    }
    
    /**
     * Helper function to populate the Event and Audience tag String
     * 
     * @param token
     * @param tags
     * @return String
     */
    private String getEventTags(String token, String tags) {
        String lastToken = token.replace("-", " ");

        if (tags.equals("")) {
           tags = lastToken;                               
        } 
        else {
           tags = tags.concat(", " + lastToken);                            
        }
        return tags;
     }
    
    //TODO: Set the time properly related with NHMEC-58
	private String setEventDuration(String time) {
		return "90 mins";
	}
}
