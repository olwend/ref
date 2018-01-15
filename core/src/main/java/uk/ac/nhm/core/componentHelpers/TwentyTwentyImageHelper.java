package uk.ac.nhm.core.componentHelpers;

import javax.jcr.AccessDeniedException;
import javax.jcr.ItemNotFoundException;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.servlet.http.HttpServletRequest;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.granite.xss.XSSAPI;
import com.day.cq.commons.ImageResource;
import com.day.cq.dam.api.Asset;

import uk.ac.nhm.core.utils.LinkUtils;
import uk.ac.nhm.core.utils.NodeUtils;

public class TwentyTwentyImageHelper {

	private String originalImagePathBefore;
	private String originalImagePathAfter;

	private String altBefore;
	private String altAfter;
	
	private Boolean isInitialised;

	private ResourceResolver resourceResolver;
	
	protected static final Logger logger = LoggerFactory.getLogger(Foundation5ImageHelper.class);

	public TwentyTwentyImageHelper(ValueMap properties, Resource resource, ResourceResolver resourceResolver, HttpServletRequest request, XSSAPI xssAPI) {

		this.resourceResolver = resourceResolver;
		
		String fileReferenceBefore = properties.get("fileReferenceBefore", "");
		String fileReferenceAfter = properties.get("fileReferenceAfter", "");
		
		logger.error("File reference before: " + fileReferenceBefore);
		logger.error("File reference after: " + fileReferenceAfter);
		
		this.originalImagePathBefore = fileReferenceBefore;
		this.originalImagePathAfter = fileReferenceAfter;
		this.altBefore = properties.get("altBefore", "");
		this.altAfter = properties.get("altAfter", "");
		
		if(fileReferenceBefore.equals("") || fileReferenceAfter.equals("")) {
			this.isInitialised = false;
		} else {
			this.isInitialised = true;
		}
	}

	public String getOriginalImagePathBefore() {
		return originalImagePathBefore;
	}

	public String getOriginalImagePathAfter() {
		return originalImagePathAfter;
	}

	public void setOriginalImagePathBefore(String originalImagePathBefore) {
		this.originalImagePathBefore = originalImagePathBefore;
	}

	public void setOriginalImagePathAfter(String originalImagePathAfter) {
		this.originalImagePathAfter = originalImagePathAfter;
	}

	public String getAltBefore() {
		return altBefore;
	}

	public void setAltBefore(String altBefore) {
		this.altBefore = altBefore;
	}

	public String getAltAfter() {
		return altAfter;
	}

	public void setAltAfter(String altAfter) {
		this.altAfter = altAfter;
	}
	
	public Boolean isInitialised() {
		return isInitialised;
	}
}