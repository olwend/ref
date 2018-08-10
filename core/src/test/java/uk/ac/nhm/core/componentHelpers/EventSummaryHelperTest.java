package uk.ac.nhm.core.componentHelpers;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.wrappers.ValueMapDecorator;
import org.junit.Test;

public class EventSummaryHelperTest {

	public ValueMap properties;
	public EventSummaryHelper helper;
	
	@Test
	public void testText() {
		properties = initialiseProperties("Test Title", "times");
		helper = new EventSummaryHelper(properties, "take-part");
		assertEquals(helper.getText(), "Test Title");
	}
	
	@Test
	public void testTextNull() {
		properties = initialiseProperties(null, "times");
		helper = new EventSummaryHelper(properties, "take-part");
		assertEquals(helper.getText(), null);
	}
	
	@Test
	public void testSectionColor() {
		properties = initialiseProperties("Test Title", "times");
		helper = new EventSummaryHelper(properties, "take-part");
		assertEquals(helper.getSvgColour(), "#BF491F");
	}
	
	@Test
	public void testDefaultIcon() {
		//Default is set as dates / calendar
		properties = initialiseProperties("Test Title", null);
		helper = new EventSummaryHelper(properties, "take-part");
		assertEquals(helper.getSvgFallback(), "icon_l_feature_calendar");
		assertEquals(helper.getSvgAlt(), "Calendar");
		assertEquals(helper.getSvgTitle(), "icon__sidebar_calendar");
		assertEquals(helper.getSvg(), "icon_l_feature_calendar.svg");
	}
	
	@Test
	public void testIconProperties() {
		properties = initialiseProperties("Test Title", "times");
		helper = new EventSummaryHelper(properties, "take-part");
		assertEquals(helper.getSvgFallback(), "icon_l_feature_clock");
		assertEquals(helper.getSvgAlt(), "Clock");
		assertEquals(helper.getSvgTitle(), "icon__sidebar_clock");
		assertEquals(helper.getSvg(), "icon_l_feature_clock.svg");
	}
	
	public ValueMap initialiseProperties(String text, String icon) {
		properties = new ValueMapDecorator(new HashMap<String, Object>());
		properties.put("text", text);
		properties.put("icon", icon);	
		return properties;
	}
}
