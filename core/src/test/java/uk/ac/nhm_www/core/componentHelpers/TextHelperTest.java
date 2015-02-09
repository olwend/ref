package uk.ac.nhm_www.core.componentHelpers;

import uk.ac.nhm.nhm_www.core.componentHelpers.TextHelper;
import uk.ac.nhm_www.core.componentHelpers.*;

import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.wrappers.ValueMapDecorator;

import java.util.HashMap;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class TextHelperTest {

	public ValueMap properties;
	public TextHelper helper;
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		properties = new ValueMapDecorator(new HashMap());
		helper = new TextHelper(properties);
	}

	@After
	public void tearDown() throws Exception {
		helper = null;
	}


	@Test
	public void ComponentDefaultsToUninitialisedTest() {
		assertFalse(helper.isComponentInitialised());
	}
	
	@Test
	public void ComponentReturnsCorrectValues()
	{
		properties = new ValueMapDecorator(new HashMap());
		properties.put("text", "     ");
		helper = new TextHelper(properties);
		
		assertTrue(helper.getText() == null);
		
		properties.put("text", "hello world");
		helper = new TextHelper(properties);

		assertTrue(helper.getText() == "hello world");
	}
}
