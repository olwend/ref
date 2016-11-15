package uk.ac.nhm.nhm_www.core.impl.schedulers;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@Service
@Properties({
	//schedule the service to run every 300s
	@Property(name="scheduler.period", value="300"),
	@Property(name="scheduler.expression", value="*/5 * * * ?")
})
public class XMLFeedDispatcherFlush implements Runnable {

	private final String xmlFeedPath = "/content/nhmwww/visitorfeed.xml";
	private final String dispatcherFlushUrl = "/dispatcher/invalidate.cache";
	
	protected final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public void run() {
		try { 
            //retrieve the request parameters
            String handle = xmlFeedPath;
            String page = xmlFeedPath;
 
            //TODO - Need to create a session to get server path from
            String server = "aem-pblsh1-stg";
            String port = "5434";
 
            HttpClient client = new HttpClient();
 
            PostMethod post = new PostMethod("http://" + server + ":" + port + dispatcherFlushUrl);
            post.setRequestHeader("CQ-Action", "Activate");
            post.setRequestHeader("CQ-Handle", handle);

            //StringRequestEntity body = new StringRequestEntity(page, null, null);
            //post.setRequestEntity(body);
            //post.setRequestHeader("Content-length", String.valueOf(body.getContentLength()));
            client.executeMethod(post);
            post.releaseConnection();
            //log the results
            log.info("Dispatcher flush for page: " + post.getPath() + ", result: " + post.getResponseBodyAsString());
        } catch(Exception e){
            log.error("Flushcache servlet exception: " + e.getMessage());
        }
	}

}
