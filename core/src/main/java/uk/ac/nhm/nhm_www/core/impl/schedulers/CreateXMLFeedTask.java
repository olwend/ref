package uk.ac.nhm.nhm_www.core.impl.schedulers;

import java.text.ParseException;

import javax.jcr.LoginException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.jcr.api.SlingRepository;
import org.json.JSONException;

import uk.ac.nhm.nhm_www.core.impl.services.CreateXMLFeedServiceImpl;
import uk.ac.nhm.nhm_www.core.utils.CreateXMLFeedUtils;
import uk.ac.nhm.nhm_www.core.utils.EventCalendarLoginUtils;

import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.Replicator;

@Component(metatype = true, label = "NHM scheduled task", 
    description = "Cron-job executed every day at midnight 00:30")
@Service(value = Runnable.class)
@Properties({
    // For your testing you can set this value to "0 * * * * ?"
    // it will set up the Cron Job to be executed every minute.
    @Property(name = "scheduler.expression", value = "30 00 * * * ?",  
        description = "NHM cron-job expression"),
})
public class CreateXMLFeedTask implements Runnable {
    
    @Reference
	private CreateXMLFeedServiceImpl createXMLFeed;
    @Reference
    private SlingRepository repository;
    @Reference
    private Replicator replicator;
    
    private Session session;
    private EventCalendarLoginUtils eventCalendarLoginUtils;


    @Override
    public void run() {
        System.out.println("*** NHM Cron Job executed, generating events feed ...");
		try {
			createXMLFeed.createXML();
            System.out.println("*** Replicating feed ...");
            eventCalendarLoginUtils = new EventCalendarLoginUtils();
            session = repository.login(new SimpleCredentials(eventCalendarLoginUtils.getUserID(), eventCalendarLoginUtils.getUserPassword().toCharArray()));
            replicator.replicate(session, ReplicationActionType.ACTIVATE, "/" + CreateXMLFeedUtils.contentUrl + CreateXMLFeedUtils.visitorFeed);
            System.out.println("*** Feed replicated successfully.");   
		} catch (LoginException e) {
			System.out.println("Login Exception: " + e);
			e.printStackTrace();
		} catch (RepositoryException e) {
			System.out.println("Repository Exception: " + e);
			e.printStackTrace();
		} catch (JSONException e) {
			System.out.println("JSON Exception: " + e);
			e.printStackTrace();
		} catch (ParseException e) {
			System.out.println("Parse Exception: " + e);
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			System.out.println("Parser Configuration Exception: " + e);
			e.printStackTrace();
		} catch (TransformerException e) {
			System.out.println("Transformer Exception: " + e);
			e.printStackTrace();
		} catch (Exception e) {
            System.out.println("General Exception: " + e);
            e.printStackTrace();
        }
    }
}
