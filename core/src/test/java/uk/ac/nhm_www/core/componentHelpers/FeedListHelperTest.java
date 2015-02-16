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
		this.helper = new PressReleaseFeedListHelper(properties, mockPageManager, mockPage, mockRequest,mockResourceResolver);
		helper.setComponentTitle("alpha");
		
		this.aemContext.load().json("/json-import/pr1.json", "/content/nhmwww/en/home/testpage/pr1");
		this.aemContext.load().json("/json-import/pr2.json", "/content/nhmwww/en/home/testpage/pr2");
		
		Page pr1 = this.mockResourceResolver.getResource("/content/nhmwww/en/home/testpage/pr1").adaptTo(Page.class);
		Page pr2 = this.mockResourceResolver.getResource("/content/nhmwww/en/home/testpage/pr2").adaptTo(Page.class);
		
		PressReleaseFeedListElement listElement1 = new PressReleaseFeedListElement(this.mockResourceResolver, pr1);
		Calendar cal = new GregorianCalendar(1982,4,1);
        Date date =  cal.getTime();
		listElement1.setPressReleaseDate(date);
		this.helper.addListElement(listElement1);
		PressReleaseFeedListElement listElement2 = new PressReleaseFeedListElement(this.mockResourceResolver, pr2);
		Calendar cal2 = new GregorianCalendar(2015,02,11);
        Date date2 =  cal2.getTime();
		listElement2.setPressReleaseDate(date2);
		this.helper.addListElement(listElement2);
		
	}

	@After
	public void tearDown() throws Exception {
		aemContext.pageManager().delete(this.mockPage, false);
		helper = null;
	}
	
	

	@Test
	public void ComponentTitleTest() {
		//Requirement: Component title – text.
		assertEquals(String.class, helper.getComponentTitle().getClass());
		assertEquals(helper.getComponentTitle(), "alpha");
	}
	
	@Test
	public void ComponentTitleHyperlinkTest() {
		//Requirement: entry field. Validation on it being a hyperlink. Button to select the page if within Adobe.
		this.helper.setHyperlink("alpha");
		assertEquals(helper.getHyperlink(), "alpha");
		assertFalse(helper.validateHyperlink());
		helper.setHyperlink("http://www.example.com/");
		assertTrue(helper.validateHyperlink());
		assertEquals(String.class, helper.getHyperlink().getClass());
	}
	
	@Test
	public void getChildrenElementsTest() {
		List<Object> elements = this.helper.getChildrenElements(); 
		assertTrue(elements.size() == 2);
		PressReleaseFeedListElement element = (PressReleaseFeedListElement) elements.get(0);
		assertNotNull(element.getPressReleaseDate());
		assertNotNull(element.getTitle());
	
	}
	
	
	
	/*
	@Test
	public void GroupingTest() {
		//Requirement: default value = 1. Other options: 2, 3. Note: Grouping is ignored for mobile devices.
		assertEquals(helper.getGrouping(),1);
		helper.setGrouping(2);
		assertEquals(helper.getGrouping(),2);
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void GroupingTest2() {
		helper.setGrouping(4);
	}

	@Test(expected=IndexOutOfBoundsException.class)
	public void GroupingTest3() {
		helper.setGrouping(0);
	}
	
	@Test
	public void ThumbnailsTest() {
		//Requirement: Yes / No check box. When checked Thumbnails to show.
		assertFalse(helper.getHasThumbnails());
		assertFalse(helper.getHasThumbnails());
		helper.setHasThumbnails(true);
		assertTrue(helper.getHasThumbnails());
	}
	
	@Test
	public void AutoScrollTest() {
		//Requirement: Yes / No checkbox. When checked to enable a new field called autoscroll duration. This can only be checked when the Thumbnails field is unchecked.
		assertFalse(helper.getHasAutoscroll());
		helper.setHasAutoscroll(true);
		assertTrue(helper.getHasAutoscroll());
	}
	
	@Test
	public void AutoScrollDurationTest() {
		// Requirement: numeric field which represents seconds, default = 4
		assertEquals(helper.getAutoScrollDuration(), 4);
		helper.setAutoScrollDuration(10);
		assertEquals(helper.getAutoScrollDuration(), 10);
	}
	
	@Test
	public void AddElementTest() {
		// Requirement: the ability to add images and YouTube videos that are stored in the adobe libraries. Once selected the form will update the element listing
		helper.addElement(this.element1);
		helper.addElement(this.element2);
		assertEquals(helper.getElement(0),this.element1);
		assertEquals(helper.getElement(1),this.element2);
	}
	
	@Test
	public void ElementListingTest() {
		// Requirement: with this new element showing the file name (derived from the filename) which is to be editable, a hyperlink field (with link validation), caption, sort and delete buttons. Hyperlink field to only show if the Thumbnails checkbox unchecked.
		
		// No testing - UI testing only
		assertTrue(true);
	}
	
	@Test
	public void ElementCaptionTest() {
		// Requirement: allow rich text content

		// No testing - UI testing only
		assertTrue(true);
	}
	
	@Test
	public void ElementSwapTest()
	{
		// Requirement: to be able to drag the element up and down the list to change the sort order
		helper.addElement(this.element1);
		helper.addElement(this.element2);
		assertEquals(helper.getElement(0),this.element1);
		helper.swapElements(0,1);
		assertEquals(helper.getElement(1),this.element1);
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void ElementDeleteTest()
	{
		helper.addElement(this.element1);
		helper.addElement(this.element2);
		helper.removeElement(1);
		helper.getElement(1);
	}
	*/
	
	/* ====== OLD TESTS ======

	@Test
	public void ComponentDefaultsToUninitialisedTest() {
		assertFalse(helper.isComponentInitialised());
	}
	
	@Test
	public void FullWidthCarouselTest()
	{
		//TODO write test
		
		// Should not be initialised unless there are a minimum of two images set. 
		properties = new ValueMapDecorator(new HashMap());
		properties.put("carouselType","full-width-carousel");
		properties.put("images",array1);
		helper = new CarouselHelper(properties);
		assertFalse(helper.isComponentInitialised());

		// Should be initialised if there are two or more images set. 		
		properties = new ValueMapDecorator(new HashMap());
		properties.put("carouselType","full-width-carousel");
		properties.put("images",array2);
		helper = new CarouselHelper(properties);
		assertTrue(helper.isComponentInitialised());
		
		//Correct booleans should be set
		assertTrue(helper.isHasImages());
		assertFalse(helper.isHasPages());
		assertFalse(helper.isThumbnails());
		assertFalse(helper.isHasVideos());
	}
	
	@Test
	public void TwoColumnCarouselTest()
	{
		// Should not be initialised unless there are a minimum of three images set. 
		properties = new ValueMapDecorator(new HashMap());
		properties.put("carouselType","two-column-carousel");
		properties.put("images",array2);
		helper = new CarouselHelper(properties);
		assertFalse(helper.isComponentInitialised());

		// Should be initialised if there are three or more images set. 		
		properties = new ValueMapDecorator(new HashMap());
		properties.put("carouselType","two-column-carousel");
		properties.put("images",array3);
		helper = new CarouselHelper(properties);
		assertTrue(helper.isComponentInitialised());
		
		//Correct booleans should be set
		assertTrue(helper.isHasImages());
		assertFalse(helper.isHasPages());
		assertFalse(helper.isThumbnails());
		assertFalse(helper.isHasVideos());
	}
	
	@Test
	public void ThumbnailCarouselTest()
	{
		// Should not be initialised unless there are a minimum of two images set. 
		properties = new ValueMapDecorator(new HashMap());
		properties.put("carouselType","thumbnail-carousel");
		properties.put("images",array1);
		helper = new CarouselHelper(properties);
		assertFalse(helper.isComponentInitialised());

		// Should be initialised if there are two or more images set. 		
		properties = new ValueMapDecorator(new HashMap());
		properties.put("carouselType","thumbnail-carousel");
		properties.put("images",array2);
		helper = new CarouselHelper(properties);
		assertTrue(helper.isComponentInitialised());
		
		//Correct booleans should be set
		assertTrue(helper.isHasImages());
		assertFalse(helper.isHasPages());
		assertTrue(helper.isThumbnails());
		assertFalse(helper.isHasVideos());
	}
	
	@Test
	public void ThreeColumnCarouselTest()
	{
		// Should not be initialised unless there are a minimum of four images set. 
		properties = new ValueMapDecorator(new HashMap());
		properties.put("carouselType","three-column-carousel");
		properties.put("images",array3);
		helper = new CarouselHelper(properties);
		assertFalse(helper.isComponentInitialised());

		// Should be initialised if there are four or more images set. 		
		properties = new ValueMapDecorator(new HashMap());
		properties.put("carouselType","three-column-carousel");
		properties.put("images",array4);
		helper = new CarouselHelper(properties);
		assertTrue(helper.isComponentInitialised());
		
		//Correct booleans should be set
		assertTrue(helper.isHasImages());
		assertFalse(helper.isHasPages());
		assertFalse(helper.isThumbnails());
		assertFalse(helper.isHasVideos());
	}
	
	@Test
	public void VideoCarouselTest()
	{
		// Should not be initialised unless there are a minimum of one video set. 
		properties = new ValueMapDecorator(new HashMap());
		properties.put("carouselType","video-carousel");
		helper = new CarouselHelper(properties);
		assertFalse(helper.isComponentInitialised());

		// Should be initialised if thereis a video set. 		
		properties = new ValueMapDecorator(new HashMap());
		properties.put("carouselType","video-carousel");
		properties.put("videos",array1);
		helper = new CarouselHelper(properties);
		assertTrue(helper.isComponentInitialised());
		
		//Correct booleans should be set
		assertFalse(helper.isHasImages());
		assertFalse(helper.isHasPages());
		assertFalse(helper.isThumbnails());
		assertTrue(helper.isHasVideos());
	}

	@Test
	public void UpCarouselTest()
	{
		// Should not be initialised unless there are a minimum of three pages set. 
		properties = new ValueMapDecorator(new HashMap());
		properties.put("carouselType","up-carousel");
		properties.put("pages",array2);
		helper = new CarouselHelper(properties);
		assertFalse(helper.isComponentInitialised());

		// Should be initialised if there are three or more pages set. 		
		properties = new ValueMapDecorator(new HashMap());
		properties.put("carouselType","up-carousel");
		properties.put("pages",array3);
		helper = new CarouselHelper(properties);
		assertTrue(helper.isComponentInitialised());
		//Correct booleans should be set
		assertFalse(helper.isHasImages());
		assertTrue(helper.isHasPages());
		assertFalse(helper.isThumbnails());
		assertFalse(helper.isHasVideos());
	}
	*/

}
