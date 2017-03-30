package uk.ac.nhm.nhm_www.core.impl.services;

import io.wcm.testing.mock.aem.junit.AemContext;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.wrappers.ValueMapDecorator;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

public class EventsCalendarRestServiceImplTest {

	private EventsCalendarRestServiceImpl eventsCalendarRestService;
	private ArrayList<Page> events;
	
	private PageManager mockPageManager;
	private ResourceResolver mockResourceResolver;
	private HttpServletRequest mockRequest;
	
	@Rule
    public final AemContext aemContext = new AemContext();
	
	@Before
	public void setUp() throws Exception {
		events = createCache();
	}
	
	@Test
	public void testProcessDates() {
		
		
	}
	
	private ArrayList<Page> createCache() {
		ArrayList<Page> events = new ArrayList<Page>();
		
		this.mockResourceResolver = aemContext.resourceResolver();
		this.mockRequest = aemContext.request();
		
		for(int i=1; i<4; i++) {
			ValueMap properties = new ValueMapDecorator(new HashMap<String, Object>());
			properties.put("eventTitle", "Test title " + i);
			properties.put("eventDescription", "Test description " + i);
			properties.put("jcr:timesRecurrence", "");
			properties.put("jcr:timesRecurrence", "");
			properties.put("jcr:durationsRecurrence", "");
			
			aemContext.create().page("/content/nhmwww/en/home/events/testevent" + i);
			Resource mockResource = aemContext.resourceResolver().getResource("/content/nhmwww/en/home/events/testevent" + i);		
			
			Page mockPage = mockResource.adaptTo(Page.class);

			events.add(mockPage);
		}
		
		return events;
	}
}
