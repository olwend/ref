package uk.ac.nhm.nhm_www.core.impl.schedulers;

import java.util.Map;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.felix.scr.annotations.Reference;

import java.text.ParseException;
import javax.jcr.LoginException;
import javax.jcr.RepositoryException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.json.JSONException;
import java.lang.Exception;

import uk.ac.nhm.nhm_www.core.impl.services.CreateXMLFeedServiceImpl;


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

    @Override
    public void run() {
        System.out.println("*** NHM Cron Job executed, generating events feed...");
		try {
			createXMLFeed.createXML();
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
