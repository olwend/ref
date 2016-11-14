package uk.ac.nhm.nhm_www.core.impl.schedulers;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@Service
@Properties({
	@Property(name="scheduler.expression", value="TODO - Add cron scheduler"),
})
public class XMLFeedDispatcherFlush implements Runnable {

	protected final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
