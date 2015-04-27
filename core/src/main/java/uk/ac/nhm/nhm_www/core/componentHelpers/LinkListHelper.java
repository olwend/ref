package uk.ac.nhm.nhm_www.core.componentHelpers;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

public class LinkListHelper extends ListHelper {

	protected static final Logger LOG = LoggerFactory.getLogger(LinkListHelper.class);	
	
	public String description = StringUtils.EMPTY;
	
	public LinkListHelper(ValueMap properties, PageManager pageManager, Page currentPage, HttpServletRequest request, ResourceResolver resourceResolver) {
		super(properties, pageManager, currentPage, request, resourceResolver);
		
		if (properties.get("description", String.class) != null)
			this.description = properties.get("description", String.class);
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public boolean hasDescription() {
		boolean res = false;
		if (!getDescription().isEmpty()){
			res = true; 
		}
		return res;
	}
	
}
