package uk.ac.nhm.core.componentHelpers;

import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.ValueFormatException;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.dam.cfm.ContentFragment;
import com.adobe.cq.sightly.WCMUsePojo;

public class ContentModelUsePojo extends WCMUsePojo {

    private static final Logger LOG = LoggerFactory.getLogger(ContentModelUsePojo.class);

    private String text;
    private String imagePath;
    private String link;
    
    private ContentFragment fragment;

    @Override
    public void activate() {
    	ResourceResolver resourceResolver = getResourceResolver();
        String cfReference = getProperties().get("fileReference", null);
        String variation = getProperties().get("variation", null);
        
        Resource fragmentResource = resourceResolver.getResource(cfReference);
        
        if(fragmentResource != null) fragment = fragmentResource.adaptTo(ContentFragment.class);
        
        if(fragment != null) {
        	if(variation != null && !variation.equals("master")) {
        		if(fragment.hasElement("text")) text = fragment.getElement("text").getVariation(variation).getContent();
		    	if(fragment.hasElement("imagepath")) imagePath = fragment.getElement("imagepath").getVariation(variation).getContent();
		    	if(fragment.hasElement("link")) link = fragment.getElement("link").getVariation(variation).getContent();
        	} else {
		        if(fragment.hasElement("text")) text = fragment.getElement("text").getContent();
		    	if(fragment.hasElement("imagepath")) imagePath = fragment.getElement("imagepath").getContent();
		    	if(fragment.hasElement("link")) link = fragment.getElement("link").getContent();
        	}
        }
    }

	public boolean isConfigured() throws ValueFormatException, PathNotFoundException, RepositoryException {
		if(getProperties().get("fileReference", null) != null) {
			return true;
		} else {
			return false;
		}
	}
    
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
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