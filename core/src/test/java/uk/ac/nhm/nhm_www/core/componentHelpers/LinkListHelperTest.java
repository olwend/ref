package uk.ac.nhm.nhm_www.core.componentHelpers;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.wrappers.ValueMapDecorator;
import org.apache.sling.commons.json.JSONException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

import io.wcm.testing.mock.aem.junit.AemContext;

public class LinkListHelperTest {

	public ValueMap properties;
	public LinkListHelper helper;
	
	PageManager mockPageManager;
	ResourceResolver mockResourceResolver;
	Resource mockResource;
	Page mockPage;
	HttpServletRequest mockRequest;
	
	@Rule
    public final AemContext aemContext = new AemContext();
	
	@Before
	public void setUp() throws Exception {
		properties = new ValueMapDecorator(new HashMap<String, Object>());
		properties.put("numberToDisplay", 6);
		properties.put("title", "Test Title");
		properties.put("feedRoot", "/content/nhmwww/en/home/press/press-releases/");
		
		this.mockResourceResolver = aemContext.resourceResolver();
		this.mockRequest = aemContext.request();
		
		aemContext.create().page("/content/nhmwww/en/home/testpage", "test1");
		
		this.mockResource = aemContext.resourceResolver().getResource("/content/nhmwww/en/home/testpage");
		this.mockPageManager = aemContext.pageManager();
		
		this.mockPage = this.mockResource.adaptTo(Page.class);
		this.aemContext.load().json("/json-import/pr1.json", "/content/nhmwww/en/home/testpage/pr1");
		this.aemContext.load().json("/json-import/pr2.json", "/content/nhmwww/en/home/testpage/pr2");
	}
	
	@Test
	public void testAddListValidJson() throws JSONException {
		/**
		 * Test LinkListHelper returns correct HTML code when given valid JSON
		 */
		String codeOutput = addListUtil("{\"url\":\"/content/nhmwww/en/home/take-part\",\"text\":\"test\",\"openInNewWindow\":false}");
		String compare = "<li><div class=\"linklist--column \"><ul class=\"linklist--link-items\"><li><a href=\"/content/nhmwww/en/home/take-part\" target=\"_self\">test</a></li></ul></div></li>";
		
		assertEquals(codeOutput, compare);
	}
	
	@Test
	public void testAddListNonValidJson() throws JSONException {
		/**
		 * Test LinkListHelper returns warning when given non-valid JSON
		 */
		String codeOutput = addListUtil("url:/content/nhmwww/en/home/take-part");
		String compare="<li><div class=\"linklist--column \"><ul class=\"linklist--link-items\"><p>This component is not configured correctly</p></ul></div></li>";
		
		assertEquals(codeOutput, compare);
	}

	@Test
	public void testAddListNullJson() throws JSONException {
		/**
		 * Test LinkListHelper returns warning when given null value for the JSON
		 */
		String codeOutput = addListUtil(null);
		String compare="<li><div class=\"linklist--column \"><ul class=\"linklist--link-items\"><p>This component is not configured correctly</p></ul></div></li>";
		
		assertEquals(codeOutput, compare);
	}
	
	private String addListUtil(String json) throws JSONException {
		//Instantiate helper object
		helper = new LinkListHelper(properties, mockPageManager, mockPage, mockRequest, mockResourceResolver);
		helper.init();
		
		//Add JSON to array
		String[] s = new String[1];
		s[0] = json;
		
		//Add array to arraylist
		helper.columnItemsList.set(0, s);
		
		//Set @columnListItem used to inspect position in arraylist
		int columnListItem = 0;

		//Get code output from method
		String codeOutput = helper.addList(columnListItem).toString();
		
		return codeOutput;
	}
}