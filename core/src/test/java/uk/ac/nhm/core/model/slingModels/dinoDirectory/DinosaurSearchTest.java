package uk.ac.nhm.core.model.slingModels.dinoDirectory;

import org.apache.sling.api.resource.Resource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import io.wcm.testing.mock.aem.junit.AemContext;

public class DinosaurSearchTest {

	@Rule
    public final AemContext context = new AemContext();
	 
    @Before
    public void setup(){
	 
		// load the resources for each object
		context.load().json("/sample_resource.json","/content/foo/path/sampleResource");
		context.load().json("/model_class.json","/content/foo/path/modelClass");
		 
		context.addModelsForClasses(DinosaurSearch.class);
    }
    
    @Test
    public void simpleTest() {
    	Resource resource = context.resourceResolver().getResource("/content/foo/path/sampleResource");
        Assert.assertNotNull(resource);
        DinosaurSearch sampleResource= resource.adaptTo(DinosaurSearch.class);
        Assert.assertNotNull(sampleResource);
    }
	
}
