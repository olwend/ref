package uk.ac.nhm.core.componentHelpers;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.dam.cfm.ContentFragment;
import com.adobe.cq.sightly.WCMUsePojo;

public class ContentModelUsePojo extends WCMUsePojo {

    private static final Logger LOG = LoggerFactory.getLogger(ContentModelUsePojo.class);
	
    private String title;
    private String description;
    private String imagePath;
    private String link;

    @Override
    public void activate() {
    	ResourceResolver resourceResolver = getResourceResolver();
        String cfReference = getProperties().get("fileReference", null);
		
        Resource fragmentResource = resourceResolver.getResource(cfReference);
        
        if(fragmentResource != null) {
        	ContentFragment fragment = fragmentResource.adaptTo(ContentFragment.class);
        	
        	this.title = fragment.getElement("title").getContent();
        	this.description = fragment.getElement("description").getContent();
        	this.imagePath = fragment.getElement("imagepath").getContent();
        	this.link = fragment.getElement("link").getContent();
        }
    }

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}


}