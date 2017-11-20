package uk.ac.nhm.core.componentHelpers;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.wrappers.ValueMapDecorator;
import org.junit.Test;

public class AccordionHelperTest {

	public ValueMap properties;
	public AccordionHelper helper;
	
	@Test
	public void testPageTitle() {
		properties = initialiseProperties(false, "Test Title", "/content/nhmwww/en/home/press/press-releases");
		helper = new AccordionHelper(properties);
		assertEquals(helper.getPanelTitle(), "Test Title");
	}
	
	@Test
	public void testPageTitleNull() {
		properties = initialiseProperties(false, null, "/content/nhmwww/en/home/press/press-releases");
		helper = new AccordionHelper(properties);
		assertNotNull(helper.getPanelTitle());
		assertEquals("This component is not configured correctly", helper.getPanelTitle());
		assertEquals("InvalidComponent", helper.getPanelId());
	}
	
	@Test
	public void testPanelIdUtils() {
		properties = initialiseProperties(false, "#Test,Title", "/content/nhmwww/en/home/press/press-releases");
		helper = new AccordionHelper(properties);
		assertEquals("TestTitle", helper.getPanelId());
	}
	
	public ValueMap initialiseProperties(Boolean isOpen, String panelTitle, String feedRoot) {
		properties = new ValueMapDecorator(new HashMap<String, Object>());
		properties.put("isOpen", isOpen);
		properties.put("panelTitle", panelTitle);
		properties.put("feedRoot", feedRoot);
		return properties;
	}
}