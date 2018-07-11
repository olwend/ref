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
	
	private boolean isConfigured = false;
	
	@PostConstruct
	protected void init() throws JSONException, PathNotFoundException, RepositoryException {
		
		String imagePathItemsPath1 = request.getResource().getPath() + "/image1";
		Resource image1Resource = resourceResolver.getResource(imagePathItemsPath1);
		
		String imagePathItemsPath2 = request.getResource().getPath() + "/image2";
		Resource image2Resource = resourceResolver.getResource(imagePathItemsPath2);
		
		if(image1Resource != null && image2Resource != null) {
			this.setConfigured(true);
		}
		
	}

	public boolean isConfigured() {
		return isConfigured;
	}

	public void setConfigured(boolean isConfigured) {
		this.isConfigured = isConfigured;
	}

}
