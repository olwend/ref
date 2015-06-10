//package uk.ac.nhm_www.core.componentHelpers;
//
//import static org.junit.Assert.assertTrue;
//import io.wcm.testing.mock.aem.junit.AemContext;
//
//import java.security.AccessControlException;
//import java.util.HashMap;
//import java.util.List;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.apache.sling.api.resource.Resource;
//import org.apache.sling.api.resource.ResourceResolver;
//import org.apache.sling.api.resource.ValueMap;
//import org.apache.sling.api.wrappers.ValueMapDecorator;
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.runners.MockitoJUnitRunner;
//
//import uk.ac.nhm.nhm_www.core.componentHelpers.DatedAndTaggedFeedListHelper;
//
//import com.day.cq.tagging.InvalidTagFormatException;
//import com.day.cq.tagging.Tag;
//import com.day.cq.tagging.TagManager;
//import com.day.cq.wcm.api.Page;
//import com.day.cq.wcm.api.PageManager;
//
//@RunWith(MockitoJUnitRunner.class)
//public class DatedAndTaggedFeedListHelperTest {
//	
//	Page mockPage;
//	PageManager mockPageManager;
//	Resource mockResource;
//	ResourceResolver mockResourceResolver;
//	TagManager tagManager;
//	HttpServletRequest mockRequest;
//	
//	private ValueMap properties;
//	private DatedAndTaggedFeedListHelper helper;
//	
//	@Rule
//	public final AemContext aemContext = new AemContext();
//	
//	@Before
//	public void setUp() throws Exception {
//		this.mockResourceResolver = aemContext.resourceResolver();
//		this.mockRequest = aemContext.request();
//		this.mockPageManager = aemContext.pageManager();
//		
//		aemContext.create().page("/content/nhmwww/en/home/about-us/daily-planet-news/1492", "sublandingpage");
//		
//		this.mockResource = aemContext.resourceResolver().getResource("/content/nhmwww/en/home/about-us/daily-planet-news/1492");
//		this.mockPage = this.mockResource.adaptTo(Page.class);
//		
//		this.tagManager = mockResourceResolver.adaptTo(TagManager.class);
//		
//		properties = new ValueMapDecorator(new HashMap<String, Object>());
//		properties.put("title", "Apples");
//		properties.put("hyperlink", "somehyperlink");												//tbchanged
//		properties.put("newwindow", false);
//		properties.put("rootPagepath", "/content/nhmwww/en/home/about-us/daily-planet-news/");
//		properties.put("numberToDisplay", 6);
//		
//		Tag[] pageTags = {
//				generateTag("fruit/apple/braeburn", "braeburn", "Best apple ever"), 
//				generateTag("fruit/apple/pinklady", "pinklady", "Decent apple")
//		};
//		properties.put("tags", pageTags);
//		
//		this.aemContext.load().json("/json-import/braeburn.json", "/content/nhmwww/en/home/about-us/1492/braeburn");
//		this.aemContext.load().json("/json-import/pinklady.json", "/content/nhmwww/en/home/about-us/1492/pinklady");
//		
//		this.helper = new DatedAndTaggedFeedListHelper(properties, mockPageManager, mockPage, mockRequest, mockResourceResolver);
//		
//		helper.setComponentTitle("Apples Component");
//		
//		helper.setInitialised(true);
//		
//	}
//
//	private Tag generateTag(String tagID, String title, String description) {
//		Tag tag = null;
//		
//		try {
//			tag = this.tagManager.createTag(tagID, title, description);									//nullpointer FFS
//		} catch (AccessControlException | InvalidTagFormatException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		return tag;
//		
//	}
//	
//	@Test
//	public void pageShouldHaveTags(){
//		
////		Adapt a resource instead to get a tagManager, this one might be null
////		tagManager.setTags(mockResource, tags);
//		List<Object> elements = this.helper.getChildrenElements();
//		assertTrue(elements.size() == 4);
//	}
//	
//	
//}
