package uk.ac.nhm.core.model.slingModels;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import io.wcm.testing.mock.aem.junit.AemContext;

public class BigSplashModelTest {

	@Rule
    public final AemContext context = new AemContext();

	@Before
	public void setUp() throws Exception {
//		context.load().json("/model/slingModels/imagePage/imageText/hasimage.json", "/content/sample/en/has-image");
	}
	
	@Test
	public void testEnvironmentService() {
		assertEquals("a", "a");
	}

	
}
