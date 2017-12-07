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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.tagging.JcrTagManagerFactory;
import com.day.cq.tagging.TagManager;

import uk.ac.nhm.nhm_www.core.services.CreateXMLFeedService;
import uk.ac.nhm.nhm_www.core.utils.CreateXMLFeedUtils;


@Component(immediate = true, metatype = false)
@Service (value = CreateXMLFeedServiceImpl.class)
public class CreateXMLFeedServiceImpl implements CreateXMLFeedService { 
	private static final String JSON_PATH = "content/nhmwww/eventscontent";
	
	private static final Logger LOG = LoggerFactory.getLogger(CreateXMLFeedServiceImpl.class);
	
	private CreateXMLFeedUtils createXMLFeedUtils;
	
	private Session session;
	private Node root;
	
	@Reference
	private SlingRepository repository;
	
	@Reference
	JcrTagManagerFactory jcrTagManagerFactory;
	TagManager tagManager;
    

	@Override

	public void createXML() throws LoginException, RepositoryException, JSONException, ParseException, ParserConfigurationException, TransformerException{		
		
        session = repository.loginService("searchService", null);
		tagManager = jcrTagManagerFactory.getTagManager(session);
	
		LOG.info("Attempting refresh of XML feed for Events Calendar");

				
		createXMLFeedUtils = new CreateXMLFeedUtils();
		createXMLFeedUtils.storeXMLFromEvents(createXMLFeedUtils.getTodayEvents(getJSON(), tagManager), root, session);
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

		//eventCalendarLoginUtils = new EventCalendarLoginUtils();
        //session = repository.login(new SimpleCredentials(eventCalendarLoginUtils.getUserID(), eventCalendarLoginUtils.getUserPassword().toCharArray()));

		LOG.info("Admin session created with user " + session.getUserID());

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
