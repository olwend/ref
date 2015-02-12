package uk.ac.nhm_www.core.componentHelpers;

import uk.ac.nhm.nhm_www.core.componentHelpers.FeedListHelper;
import uk.ac.nhm.nhm_www.core.componentHelpers.PressReleaseFeedListHelper;
import uk.ac.nhm.nhm_www.core.model.FeedListElement;
import uk.ac.nhm.nhm_www.core.model.PressReleaseFeedListElement;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.wrappers.ValueMapDecorator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.foundation.Image;


@RunWith(MockitoJUnitRunner.class)
public class FeedListHelperTest {

	@Mock
	PageManager mockPageManager;
	@Mock
	ResourceResolver mockResourceResolver;
	@Mock
	Resource mockResource;
	@Mock
	Page mockPage;
	@Mock
	HttpServletRequest mockRequest;

	private ValueMap properties;
	private PressReleaseFeedListHelper helper;
	public Image image;
	

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}


	@Before
	public void setUp() throws Exception {
		properties = new ValueMapDecorator(new HashMap<String, Object>());
		properties.put("numberToDisplay", 6);
		properties.put("title", "Test Title");
		properties.put("feedRoot", "/content/nhmwww/en/home/press/press-releases/");
		helper = new PressReleaseFeedListHelper(properties, mockPageManager, mockPage, mockRequest,mockResourceResolver);
		
		PressReleaseFeedListElement listElement1 = new PressReleaseFeedListElement(mockResourceResolver, mockPage);
		listElement1.setTitle("A frenchman is born");
		listElement1.setIntro("Is this realy newsworthy?");
		listElement1.setImagePath("/dam/image/mock");
		Calendar cal = new GregorianCalendar(1982,4,1);
        Date date =  cal.getTime();
		listElement1.setPressReleaseDate(date);
		PressReleaseFeedListElement listElement2 = new PressReleaseFeedListElement(mockResourceResolver, mockPage);
		listElement2.setTitle("Something else happened");
		listElement2.setIntro("just watch the news");
		listElement2.setImagePath("/dam/image/mock");
		Calendar cal2 = new GregorianCalendar(2015,02,11);
        Date date2 =  cal2.getTime();
		listElement2.setPressReleaseDate(date2);
		helper.addListElement(listElement1);
		helper.addListElement(listElement2);
		
	}

	@After
	public void tearDown() throws Exception {
		helper = null;
	}
	
	

	@Test
	public void ComponentTitleTest() {
		//Requirement: Component title – text.

		helper.setComponentTitle("alpha");
		assertEquals(String.class, helper.getComponentTitle().getClass());
		assertEquals(helper.getComponentTitle(), "alpha");
	}
	
	@Test
	public void ComponentTitleHyperlinkTest() {
		//Requirement: entry field. Validation on it being a hyperlink. Button to select the page if within Adobe.
		helper.setHyperlink("alpha");
		assertEquals(helper.getHyperlink(), "alpha");
		assertFalse(helper.validateHyperlink());
		helper.setHyperlink("http://www.example.com/");
		assertTrue(helper.validateHyperlink());
		assertEquals(String.class, helper.getHyperlink().getClass());
	}
	
	@Test
	public void getChildrenElementsTest() {
		List<Object> elements =helper.getChildrenElements(); 
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
