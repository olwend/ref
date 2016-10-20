package uk.ac.nhm.nhm_www.core.utils;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TextUtilsTest {

	TextUtils textUtils;
	
	@Before
	public void init() {
		textUtils = new TextUtils();
	}
	
	@Test
	public void testValidJson() {
		String validJson = "{\"employees\":[{\"firstName\":\"John\", \"lastName\":\"Doe\"},{\"firstName\":\"Anna\", \"lastName\":\"Smith\"}]}";
		assertTrue(textUtils.isJSONValid(validJson));
	}
	
	@Test
	public void testNonValidJson() {
		//null string
		String nonValidJson = null;
		assertFalse(textUtils.isJSONValid(nonValidJson));
		
		//Empty string
		nonValidJson = "";
		assertFalse(textUtils.isJSONValid(nonValidJson));
		
		//Mistyped JSON
		nonValidJson = "\"employees\":[{\"firstName\":\"John\", \"lastName\":\"Doe\"},{\"firstName\":\"Anna\", \"lastName\":\"Smith\"}]}";
		assertFalse(textUtils.isJSONValid(nonValidJson));
	}
	
}
