package uk.ac.nhm.nhm_www.core.impl.schedulers;

import java.text.ParseException;

import javax.jcr.LoginException;
import javax.jcr.RepositoryException;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.felix.scr.annotations.Property;
import org.json.JSONException;

import uk.ac.nhm.nhm_www.core.services.CreateXMLFeed;

@Component
@Service(value = Runnable.class)
@Property( name = "scheduler.expression", value = "0 * * * * ?")
public class CreateMXLFeedTask implements Runnable {
	   
	@Reference
	private CreateXMLFeed createXMLFeed;
    
	public void run() {
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
			System.out.println("Parser ConfigurationException: " + e);
			e.printStackTrace();
		}
    }
}