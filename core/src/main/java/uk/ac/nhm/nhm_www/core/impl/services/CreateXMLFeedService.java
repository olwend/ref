package uk.ac.nhm.nhm_www.core.impl.services;

import java.text.ParseException;

import javax.jcr.LoginException;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
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

import uk.ac.nhm.nhm_www.core.services.CreateXMLFeed;
import uk.ac.nhm.nhm_www.core.utils.CreateXMLFeedUtils;

@Component(immediate = true, metatype = false)
@Service (value = CreateXMLFeed.class)
public class CreateXMLFeedService implements CreateXMLFeed {
	private static final String JSON_PATH = "content/nhmwww/eventscontent";
	
	private CreateXMLFeedUtils createXMLFeedUtils;
	
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
	@SuppressWarnings("deprecation")
	private JSONArray getJSON() throws LoginException, RepositoryException, JSONException {
		JSONArray events = new JSONArray();
		
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
}
