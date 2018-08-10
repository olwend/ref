package uk.ac.nhm.core.model.slingModels.imagePage;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
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
	
	private String fileReference = null;
	
	private Resource imageResource; 

	@PostConstruct
	protected void init() throws JSONException, PathNotFoundException, RepositoryException {
		//Set global imageResource
		String imagePath = request.getResource().getPath() + "/image";
		imageResource = resourceResolver.getResource(imagePath);
		
		//Set model variables to export to view
		if(imageResource != null) {
			ValueMap map = imageResource.adaptTo(ValueMap.class);
			if(map.containsKey("fileReference")) {
				fileReference = map.get("fileReference", String.class);
			}
		}
	}

	protected boolean getConfigured(Resource imageResource) throws RepositoryException {
		ValueMap map = imageResource.adaptTo(ValueMap.class);
		
		if(properties != null
				&& map.containsKey("fileReference")) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isConfigured() throws RepositoryException {
		return getConfigured(imageResource);
	}
	
	public String getFileReference() {
		return this.fileReference;
	}
	
	public void setFileReference(String fileReference) {
		this.fileReference = fileReference;
	}

}
