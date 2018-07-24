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

	public String fileReference1 = null;
	public String fileReference2 = null;
	
	private Resource imageResource1; 
	private Resource imageResource2;
	
	@PostConstruct
	protected void init() throws JSONException, PathNotFoundException, RepositoryException {
		//Set global imageResources
		String imagePathItemsPath1 = request.getResource().getPath() + "/image1";
		imageResource1 = resourceResolver.getResource(imagePathItemsPath1);
		
		String imagePathItemsPath2 = request.getResource().getPath() + "/image2";
		imageResource2 = resourceResolver.getResource(imagePathItemsPath2);
		
		//Set model variables to export to view
		ValueMap map1 = imageResource1.adaptTo(ValueMap.class);
		if(map1.containsKey("fileReference")) {
			fileReference1 = map1.get("fileReference", String.class);
		}
		
		ValueMap map2 = imageResource2.adaptTo(ValueMap.class);
		if(map2.containsKey("fileReference")) {
			fileReference2 = map2.get("fileReference", String.class);
		}
	}

	public boolean isConfigured() throws RepositoryException {
		if(imageResource1.adaptTo(Node.class).hasProperty("fileReference") != false
				&& imageResource2.adaptTo(Node.class).hasProperty("fileReference") != false) {
			return true;
		} else {
			return false;
		}
	}

	public String getFileReference1() {
		return fileReference1;
	}

	public void setFileReference1(String fileReference1) {
		this.fileReference1 = fileReference1;
	}

	public String getFileReference2() {
		return fileReference2;
	}

	public void setFileReference2(String fileReference2) {
		this.fileReference2 = fileReference2;
	}


}
