package uk.ac.nhm.core.componentHelpers;

import java.util.HashMap;

import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.wrappers.ValueMapDecorator;
import org.junit.Before;

public class Foundation5ImageHelperTest {

	public ValueMap properties;
	public Foundation5ImageHelper helper;
	
	@Before
	public void setUp() throws Exception {
		properties = new ValueMapDecorator(new HashMap<String, Object>());
	}
	
}
