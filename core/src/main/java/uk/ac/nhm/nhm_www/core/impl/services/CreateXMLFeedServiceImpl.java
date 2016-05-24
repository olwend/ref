package uk.ac.nhm.nhm_www.core.impl.services;

import java.text.ParseException;

import javax.jcr.LoginException;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.SimpleCredentials;
import javax.jcr.Session;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.jcr.api.SlingRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import uk.ac.nhm.nhm_www.core.services.CreateXMLFeedService;
import uk.ac.nhm.nhm_www.core.utils.CreateXMLFeedUtils;
import uk.ac.nhm.nhm_www.core.utils.EventCalendarLoginUtils;

@Component(immediate = true, metatype = false)
@Service (value = CreateXMLFeedServiceImpl.class)
public class CreateXMLFeedServiceImpl implements CreateXMLFeedService { 
	private static final String JSON_PATH = "content/nhmwww/eventscontent";
	
	private CreateXMLFeedUtils createXMLFeedUtils;
	private EventCalendarLoginUtils eventCalendarLoginUtils;
	
	private Session session;
	private Node root;
	
	@Reference
	private SlingRepository repository;
	
	@Override
	public void createXML() throws LoginException, RepositoryException, JSONException, ParseException, ParserConfigurationException, TransformerException{		
		createXMLFeedUtils = new CreateXMLFeedUtils();
		createXMLFeedUtils.storeXMLFromEvents(createXMLFeedUtils.getTodayEvents(getJSON()), root, session);
	}
		
	/**
	 * Function to get the JSON from CRX
	 *  
	 * @throws RepositoryException 
	 * @throws LoginException 
	 * @throws JSONException 
	 */
	private JSONArray getJSON() throws LoginException, RepositoryException, JSONException {
		JSONArray events = new JSONArray();
		eventCalendarLoginUtils = new EventCalendarLoginUtils();
		session = repository.login(new SimpleCredentials(eventCalendarLoginUtils.getUserID(), eventCalendarLoginUtils.getUserPassword().toCharArray()));
		
		if (root.hasNode(JSON_PATH)) {
			Node eventsNode = root.getNode(JSON_PATH);
			
			if (eventsNode.hasProperty("events")) {
				JSONObject json = new JSONObject(eventsNode.getProperty("events").getString());
				events = json.getJSONArray("Events");
			}
		}
		return events;
	}
}
