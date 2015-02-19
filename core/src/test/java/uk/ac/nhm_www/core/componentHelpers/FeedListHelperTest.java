package uk.ac.nhm_www.core.componentHelpers;

import uk.ac.nhm.nhm_www.core.componentHelpers.PressReleaseFeedListHelper;
import uk.ac.nhm.nhm_www.core.model.PressReleaseFeedListElement;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.wrappers.ValueMapDecorator;
import org.apache.sling.jcr.resource.JcrResourceResolverFactory;

import io.wcm.testing.mock.aem.junit.AemContext;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.jcr.Session;
import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.day.cq.workflow.WorkflowSession;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.foundation.Image;


@RunWith(MockitoJUnitRunner.class)
public class FeedListHelperTest {

	PageManager mockPageManager;
	ResourceResolver mockResourceResolver;
	Resource mockResource;
	Page mockPage;
	HttpServletRequest mockRequest;
	
	
	private ValueMap properties;
	private PressReleaseFeedListHelper helper;
	public Image image;
	

	 @Rule
     public final AemContext aemContext = new AemContext();

	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	
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
		
		
		
		this.helper = new PressReleaseFeedListHelper(properties, mockPageManager, mockPage, mockRequest,mockResourceResolver);
		
		helper.setComponentTitle("alpha");
		
	}

	@After
	public void tearDown() throws Exception {
		Page pr1 = this.mockResourceResolver.getResource("/content/nhmwww/en/home/testpage/pr1").adaptTo(Page.class);
		Page pr2 = this.mockResourceResolver.getResource("/content/nhmwww/en/home/testpage/pr2").adaptTo(Page.class);
		
		aemContext.pageManager().delete(this.mockPage, false);
		aemContext.pageManager().delete(pr1, false);
		aemContext.pageManager().delete(pr2, false);
		helper = null;
	}
	
	

	@Test
	public void ComponentTitleTest() {
		assertEquals(String.class, helper.getComponentTitle().getClass());
		assertEquals(helper.getComponentTitle(), "alpha");
	}
	
	@Test
	public void ComponentTitleHyperlinkTest() {
		helper.setHyperlink("alpha");
		assertEquals(helper.getHyperlink(), "alpha");
		assertFalse(helper.validateHyperlink());
		helper.setHyperlink("http://www.example.com/");
		assertTrue(helper.validateHyperlink());
		assertEquals(String.class, helper.getHyperlink().getClass());
	}
	
	@Test
	public void getChildrenElementsTest() {
		List<Object> elements = this.helper.getChildrenElements(); 
		System.out.println("Size:" + elements.size());
		assertTrue(elements.size() == 2);
		PressReleaseFeedListElement element = (PressReleaseFeedListElement) elements.get(0);
		assertNotNull(element.getPressReleaseDate());
		assertNotNull(element.getTitle());
		assertTrue(element.getTitle().equals("A frenchman is born"));
		assertTrue(element.isPinned());
		SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
		Calendar cal = new GregorianCalendar();
		cal.set(1982, 3, 1);
		Date date = cal.getTime();
		String strDate = sdf.format(date);
		String strPRDate = sdf.format(element.getPressReleaseDate());
		System.out.println("Toto pr date:" + strPRDate);
		System.out.println("Toto cal date:" + strDate);
		assertTrue(strPRDate.equals(strDate));
	}
}
