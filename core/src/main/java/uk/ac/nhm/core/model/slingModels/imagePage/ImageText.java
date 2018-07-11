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
public class ImageText {

	private static final Logger LOG = LoggerFactory.getLogger(ImageText.class);

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
		String imagePath = request.getResource().getPath() + "/image";
		Resource imageResource = resourceResolver.getResource(imagePath);
		
		if(properties != null
				&& properties.get("text", String.class) != null
				&& imageResource.adaptTo(Node.class).hasProperty("fileReference") != false) {
			return true;
		} else {
			return false;
		}
	}

}
