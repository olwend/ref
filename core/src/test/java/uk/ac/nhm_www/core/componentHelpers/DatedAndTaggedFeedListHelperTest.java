package uk.ac.nhm_www.core.componentHelpers;

import java.util.HashMap;

import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.wrappers.ValueMapDecorator;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import io.wcm.testing.mock.aem.junit.AemContext;

@RunWith(MockitoJUnitRunner.class)
public class DatedAndTaggedFeedListHelperTest {
	
	private ValueMap properties;
	
	@Rule
	public final AemContext aemContext = new AemContext();
	
	@Before
	public void setUp() throws Exception {
		properties = new ValueMapDecorator(new HashMap<String, Object>());
	}
	
	@Test
	public void shouldWriteBDDTest(){
		
	}
	
}
