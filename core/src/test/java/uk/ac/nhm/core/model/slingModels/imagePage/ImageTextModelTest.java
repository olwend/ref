package uk.ac.nhm.core.model.slingModels.imagePage;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.wrappers.ValueMapDecorator;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import io.wcm.testing.mock.aem.junit.AemContext;
import junitx.util.PrivateAccessor;

@Ignore
public class ImageTextModelTest {
	
	@Rule
    public final AemContext aemContext = new AemContext();
	
	@Before
	public void setUp() throws Exception {
		aemContext.load().json("/model/slingModels/imagePage/imageText/hasimage.json", "/content/sample/en/hasimage");
		aemContext.load().json("/model/slingModels/imagePage/imageText/noimage.json", "/content/sample/en/noimage");
	}
	
	@Test
	public void testOnlyText() throws Exception {
		ImageText imageTextModel = new ImageText();
		
		ValueMap properties = new ValueMapDecorator(new HashMap());
		properties.put("text", "<p>Some test text<p>");
		PrivateAccessor.setField(imageTextModel, "properties", properties);

		Resource resource = aemContext.resourceResolver().getResource("/content/sample/en/noimage/jcr:content/image");
		
		assertFalse(imageTextModel.getConfigured(resource));
	}
	
	@Test
	public void testImageOnly() throws Exception {
		ImageText imageTextModel = new ImageText();
		
		ValueMap properties = new ValueMapDecorator(new HashMap());
		PrivateAccessor.setField(imageTextModel, "properties", properties);
		
		Resource resource = aemContext.resourceResolver().getResource("/content/sample/en/hasimage/jcr:content/image");

		assertFalse(imageTextModel.getConfigured(resource));
	}
	
	@Test
	public void testImageAndText() throws Exception {
		ImageText imageTextModel = new ImageText();
		
		ValueMap properties = new ValueMapDecorator(new HashMap());
		properties.put("text", "<p>Some test text<p>");
		PrivateAccessor.setField(imageTextModel, "properties", properties);
		
		Resource resource = aemContext.resourceResolver().getResource("/content/sample/en/hasimage/jcr:content/image");

		assertTrue(imageTextModel.getConfigured(resource));
	}
	
}
