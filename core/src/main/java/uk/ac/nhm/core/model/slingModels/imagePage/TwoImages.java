package uk.ac.nhm.core.model.slingModels.imagePage;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Model;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Model(adaptables = SlingHttpServletRequest.class)
public class TwoImages {

	private static final Logger LOG = LoggerFactory.getLogger(TwoImages.class);

	@Inject
	ValueMap properties;
	
	@Inject
	ResourceResolver resourceResolver;
	
	@Inject
	SlingHttpServletRequest request;
	
	@PostConstruct
	protected void init() throws JSONException, PathNotFoundException, RepositoryException {
		
	}

	public boolean isConfigured() throws RepositoryException {
		String imagePathItemsPath1 = request.getResource().getPath() + "/image1";
		Resource image1Resource = resourceResolver.getResource(imagePathItemsPath1);
		
		String imagePathItemsPath2 = request.getResource().getPath() + "/image2";
		Resource image2Resource = resourceResolver.getResource(imagePathItemsPath2);
		
		if(image1Resource.adaptTo(Node.class).hasProperty("fileReference") != false
				&& image2Resource.adaptTo(Node.class).hasProperty("fileReference") != false) {
			return true;
		} else {
			return false;
		}
	}

}
