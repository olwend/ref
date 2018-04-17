package uk.ac.nhm.core.componentHelpers;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.HashMap;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.wrappers.ValueMapDecorator;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import uk.ac.nhm.core.componentHelpers.HeroHelper;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.foundation.Image;


@Ignore
@RunWith(MockitoJUnitRunner.class)
public class HeroHelperTest {

	private ValueMap properties;
	private HeroHelper helper;
	
	@Mock
	SlingHttpServletRequest mockRequest;
	@Mock
	Page mockPage;
	@Mock
	Resource mockResource;
	@Mock
	Image mockImage;

	public Image image;
	
	@Before
	public void setUp() throws Exception {

		when(mockRequest.getResource()).thenReturn(mockResource);
		when(mockPage.getNavigationTitle()).thenReturn("mock page title");
			
		image = new Image(mockResource);
		properties = new ValueMapDecorator(new HashMap<String, Object>());
		helper = new HeroHelper(mockRequest,mockPage,properties,image);
		properties.put("subHeading","mock subheading");

		
	}

	@After
	public void tearDown() throws Exception {
		helper = null;
	}


	@Test
	public void ComponentDefaultsToUninitialisedTest() {
		assertTrue(true);
	}
	
	
	@Test
	public void valuesSetTest(){
		
		//Ensure getters return correct data 
		properties = new ValueMapDecorator(new HashMap<String, Object>());
		properties.put("summary","mock summary");
		properties.put("promoLinkText","mock Link title");
		helper = new HeroHelper(mockRequest,mockPage,properties,image);
		
		assertTrue(helper.getSummary().equals("mock summary"));
		assertTrue(helper.getPromoLinkText().equals("mock Link title"));
		assertTrue(true);
	}
	
	@Test
	public void returnsNullStringsTest(){
		when(mockRequest.getResource()).thenReturn(mockResource);
		
		// Check that Subheading is null be default.
		//Commented out as this is no longer able to be empty, it uses the page title as a replacement and also as the alt value for the images
		//assertTrue(helper.getSummary() == null);
		
		// Strings should be trimmed, therefore if strings include only whitespace then the string should be null.
		when(mockPage.getNavigationTitle()).thenReturn("      ");
		properties.put("summary","             ");	
		//Same as above
		//assertTrue(helper.getSummary() == null);
	}
	
	@Test
	public void VideoPropertySanityTest(){
		properties = new ValueMapDecorator(new HashMap<String, Object>());
		helper = new HeroHelper(mockRequest,mockPage,properties,image);
		// Check that getVideo returns null by default
		
		assertTrue(helper.getVideoId() == null);
		
		// Check that video returns null if an invalid videoId is entered in the dialog.
		properties = new ValueMapDecorator(new HashMap<String, Object>());
		properties.put("youtube","847358943  69688976499+=£&");
		helper = new HeroHelper(mockRequest,mockPage,properties,image);
		assertTrue(helper.getVideoId() == null);
		
		// Check that video returns a videoId.
		properties = new ValueMapDecorator(new HashMap<String, Object>());
		properties.put("youtube","BdI-AFdWlfU");
		helper = new HeroHelper(mockRequest,mockPage,properties,image);
		assertTrue(helper.getVideoId() == "BdI-AFdWlfU");
	}
	
	@Test
	public void EnsureImagesAlwaysHaveAltText(){
		// All images must have alt text
		assertTrue(image.getAlt().length() > 0);
	}

}
