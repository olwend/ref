package uk.ac.nhm_www.core.componentHelpers;

import java.security.AccessControlException;
import java.util.HashMap;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.wrappers.ValueMapDecorator;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.day.cq.tagging.InvalidTagFormatException;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.day.cq.wcm.api.Page;




import io.wcm.testing.mock.aem.junit.AemContext;

@RunWith(MockitoJUnitRunner.class)
public class DatedAndTaggedFeedListHelperTest {
	
	Resource mockResource;
	Page mockPage;
	TagManager tagManager;
	Tag braeburn, pinklady;
	
	private ValueMap properties;
	
	@Rule
	public final AemContext aemContext = new AemContext();
	
	@Before
	public void setUp() throws Exception {
		
		
		try {
			braeburn = tagManager.createTag("fruit/apple/braeburn", "braeburn", "Best apple ever");
		} catch (AccessControlException | InvalidTagFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			pinklady = tagManager.createTag("fruit/apple/pinklady", "pinklady", "Decent apple");
		} catch (AccessControlException | InvalidTagFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		properties = new ValueMapDecorator(new HashMap<String, Object>());
	}
	
	@Test
	public void shouldFindAPageWithCorrectTagToDisplay(){

		Tag[] tags = {braeburn, pinklady};
		//Adapt a resource to get a tagManager, this one might be null
		tagManager.setTags(mockResource, tags);
	}
	
}
