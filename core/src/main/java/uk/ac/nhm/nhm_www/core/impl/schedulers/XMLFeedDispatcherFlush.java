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
	@Property(name="scheduler.expression", value="0 0 1 * * ? *")
})
public class XMLFeedDispatcherFlush implements Runnable {

	private final String xmlFeedPath = "/content/nhmwww/visitorfeed.xml";
	
	private final String server = "aem-disp1-stg";
	private final String dispatcherFlushUrl = "/dispatcher/invalidate.cache";
	
	private final String contentType = "text/plain";
	
	protected final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public void run() {
		try {
            String handle = xmlFeedPath;
            String page = xmlFeedPath;
 
            HttpClient client = new HttpClient();
 
            //clear the cache
            PostMethod post = new PostMethod("http://" + server + dispatcherFlushUrl);
            post.setRequestHeader("CQ-Action", "Activate");
            post.setRequestHeader("CQ-Handle", handle);
            post.setRequestHeader("Content-Type", contentType);

            //rebuild the cache
            StringRequestEntity body = new StringRequestEntity(page, null, null);
            post.setRequestEntity(body);
            post.setRequestHeader("Content-length", String.valueOf(body.getContentLength()));
            
            client.executeMethod(post);
            post.releaseConnection();
            
            log.error("Dispatcher flush for page: " + post.getURI() + ", result: " + post.getResponseBodyAsString());
        } catch(Exception e){
            log.error("Flushcache servlet exception: " + e.getMessage());
        }
	}
}