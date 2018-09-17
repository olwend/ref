package uk.ac.nhm.core.model.slingModels;

import static org.junit.Assert.assertEquals;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import com.day.cq.commons.ImageResource;
import com.day.cq.wcm.foundation.Image;

import io.wcm.testing.mock.aem.junit.AemContext;

public class BigSplashModelTest {

	private BigSplash bigSplashModel;
	
	@Rule
    public final AemContext context = new AemContext();

	@Before
	public void setUp() throws Exception {
		context.load().json("/model/slingModels/bigSplash/bigsplashwithalt.json", "/content/nhmwww/en/home/bigsplashwithalt");
		context.load().json("/model/slingModels/bigSplash/bigsplashwithoutalt.json", "/content/nhmwww/en/home/bigsplashwithoutalt");
		
		context.load().binaryFile("/model/slingModels/bigSplash/image.jpg", "/content/dam/nhmwww/images/image.jpg");
	}
	
	@Test
	public void testBigSplash() {
		bigSplashModel = new BigSplash();
		Resource resource = context.resourceResolver().getResource("/content/nhmwww/en/home/bigsplashwithalt");
		ValueMap properties = resource.adaptTo(ValueMap.class);
		bigSplashModel.setVariables(properties);
		assertEquals(bigSplashModel.getApplyLinkToTitle(), true);
		assertEquals(bigSplashModel.getCaption(), "Test caption");
		assertEquals(bigSplashModel.getCtaText(), "Test CTA text");
		assertEquals(bigSplashModel.getCtaUrl(), "/content/nhmwww/en/home");
		assertEquals(bigSplashModel.getImageAlt(), "Test image alt text");
		assertEquals(bigSplashModel.getImagePath(), "/content/dam/nhmwww/images/image.jpg");
		assertEquals(bigSplashModel.getSubtitle(), "Test subtitle");
		assertEquals(bigSplashModel.getTitle(), "Test title");
		assertEquals(bigSplashModel.getVideoId(), "youtubeid");
	}
	
	@Ignore
	@Test
	public void testImageTitle() {
		bigSplashModel = new BigSplash();
		Resource bigSplashResource = context.resourceResolver().getResource("/content/nhmwww/en/home/bigsplashwithoutalt");
		Resource imageResource = context.resourceResolver().getResource("/content/dam/nhmwww/images/image.jpg");
		
//		Resource imageResource = context.resourceResolver().getResource("/content/dam/nhmwww/images/image.jpg");
		ImageResource image = new Image(imageResource);
//		ValueMap imageProperties = imageResource.adaptTo(ValueMap.class);
//		bigSplashModel.setVariables(properties);
//		System.out.println(imageProperties.get("jcr:content/cq:name"));
		
	}

}
