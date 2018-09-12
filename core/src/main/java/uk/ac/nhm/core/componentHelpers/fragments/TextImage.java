package uk.ac.nhm.core.componentHelpers.fragments;

import java.util.Iterator;
import java.util.Map;

import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.ValueFormatException;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.dam.cfm.ContentFragment;
import com.adobe.cq.dam.cfm.VariationDef;
import com.adobe.cq.sightly.WCMUsePojo;

public class TextImage extends WCMUsePojo {

    private static final Logger LOG = LoggerFactory.getLogger(TextImage.class);

    private String text;
    private String imagePath;
    private String link;
    private String showCta;
    private String ctaText;
    private String ctaIconPath;
    
    private ContentFragment fragment;

    @Override
    public void activate() {
    	//Get fragment resource
    	ResourceResolver resourceResolver = getResourceResolver();
        String cfReference = getProperties().get("fileReference", null);
        String variation = getProperties().get("variation", null);
        
        Resource fragmentResource = resourceResolver.getResource(cfReference);
        
        if(fragmentResource != null) fragment = fragmentResource.adaptTo(ContentFragment.class);
        
        //Check if variation exists
        boolean variationAvailable = false;
        Iterator<VariationDef> fragmentVariations = fragment.listAllVariations();
        
        while(fragmentVariations.hasNext()) {
        	VariationDef fragmentVariation = fragmentVariations.next();
        	String variationName = fragmentVariation.getName();
        	if(variationName.equals(variation)) variationAvailable = true;
        }
        
        //Set values for view
        if(fragment != null) {
        	if(variation != null && !variation.equals("master") && variationAvailable == true) {
        		if(fragment.hasElement("text")) text = fragment.getElement("text").getVariation(variation).getContent();
		    	if(fragment.hasElement("imagepath")) imagePath = fragment.getElement("imagepath").getVariation(variation).getContent();
		    	if(fragment.hasElement("link")) link = fragment.getElement("link").getVariation(variation).getContent();
		    	if(fragment.hasElement("cta")) setShowCta(fragment.getElement("cta").getVariation(variation).getContent());
		    	if(fragment.hasElement("ctatext")) ctaText = fragment.getElement("ctatext").getVariation(variation).getContent();
		    	if(fragment.hasElement("ctaicon")) ctaIconPath = fragment.getElement("ctaicon").getVariation(variation).getContent();
        	} else {
		        if(fragment.hasElement("text")) text = fragment.getElement("text").getContent();
		    	if(fragment.hasElement("imagepath")) imagePath = fragment.getElement("imagepath").getContent();
		    	if(fragment.hasElement("link")) link = fragment.getElement("link").getContent();
		    	if(fragment.hasElement("cta")) setShowCta(fragment.getElement("cta").getContent());
		    	if(fragment.hasElement("ctatext")) ctaText = fragment.getElement("ctatext").getContent();
		    	if(fragment.hasElement("ctaicon")) ctaIconPath = fragment.getElement("ctaicon").getContent();
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
    
	public boolean hasCta() {
		if(getProperties().get("cta", null) != null
			&& getProperties().get("cta", null).equals("true")) {
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
		if(link.contains(".html")) {
			return link;
		} else {
			return link + ".html";
		}
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getShowCta() {
		return showCta;
	}

	public void setShowCta(String showCta) {
		this.showCta = showCta;
	}

	public String getCtaText() {
		return ctaText;
	}

	public void setCtaText(String ctaText) {
		this.ctaText = ctaText;
	}

	public String getCtaIconPath() {
		return ctaIconPath;
	}

	public void setCtaIconPath(String ctaIconPath) {
		this.ctaIconPath = ctaIconPath;
	}

}