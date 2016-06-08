package uk.ac.nhm.nhm_www.core.componentHelpers;

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

import uk.ac.nhm.nhm_www.core.utils.LinkUtils;
import uk.ac.nhm.nhm_www.core.utils.NodeUtils;

public class TwentyTwentyImageHelper {

	private String originalImagePathBefore;
	private String originalImagePathAfter;

	private String alt;

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

	public String getAlt() {
		return alt;
	}

	public void setAlt(String alt) {
		this.alt = alt;
	}
		
}
