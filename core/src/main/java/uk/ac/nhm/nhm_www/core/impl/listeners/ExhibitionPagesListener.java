package uk.ac.nhm.nhm_www.core.impl.listeners;

import java.text.ParseException;

import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.observation.Event;
import javax.jcr.observation.EventIterator;
import javax.jcr.observation.EventListener;
import javax.jcr.observation.ObservationManager;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.jcr.api.SlingRepository;
import org.json.JSONException;
import org.osgi.service.component.ComponentContext;

import uk.ac.nhm.nhm_www.core.utils.EventPagesUtils;

/**
 * Exhibition Folder Listener needed to retrieve the 
 * changes in the repo and create the JSON and XML 
 * feed files
 */
@Component(immediate = true, metatype = false)
@Service
public class ExhibitionPagesListener implements EventListener {
	private static final String EVENTS_PATH = "content/nhmwww/en/home/events";
	private static final String EXHIBITIONS_PATH = "content/nhmwww/en/home/visit/exhibitions-and-attractions";
	
	private EventPagesUtils eventPagesUtils;
	
	private Session session;
	private ObservationManager exhibitionsObservationManager;
	
	@Reference
	private SlingRepository repository;
	
	/**
	 * Logic to define the Exhibition Custom Event Handler
	 * 
	 * @param ctx
	 */
	@SuppressWarnings("deprecation")
	protected void activate(ComponentContext ctx) {
		try {
			session = repository.loginAdministrative(null);
			exhibitionsObservationManager = session.getWorkspace().getObservationManager();
			exhibitionsObservationManager.addEventListener(this, Event.NODE_ADDED | Event.NODE_REMOVED | Event.PROPERTY_ADDED | Event.PROPERTY_CHANGED | Event.PROPERTY_REMOVED, "/"+ EXHIBITIONS_PATH, true, null, null, false);
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}
	
	protected void deactivate(ComponentContext componentContext) throws RepositoryException {
		if (exhibitionsObservationManager != null) {
			exhibitionsObservationManager.removeEventListener(this);
		}
		
		if (session != null) {
			session.logout();
			session = null;
		}
	}
	
	/**
	 * Triggers the onEvent Function
	 * 
	 * @param events
	 */
	@Override
	public void onEvent(EventIterator events) {		
		try {
			eventPagesUtils = new EventPagesUtils();
			eventPagesUtils.getEventsDetails(session, EVENTS_PATH, EXHIBITIONS_PATH);
		} catch (PathNotFoundException e) {
			System.out.println(e);
			e.printStackTrace();
		} catch (RepositoryException e) {
			System.out.println(e);
			e.printStackTrace();
		} catch (JSONException e) {
			System.out.println(e);
			e.printStackTrace();
		} catch (ParseException e) {
			System.out.println(e);
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			System.out.println(e);
			e.printStackTrace();
		} catch (TransformerException e) {
			System.out.println(e);
			e.printStackTrace();
		} 
	}
}
