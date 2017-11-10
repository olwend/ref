package uk.ac.nhm.core.componentHelpers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.nhm.core.utils.LinkUtils;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageFilter;
import com.day.cq.wcm.api.PageManager;

public class DatedAndTaggedFeedListHelper extends PressReleaseFeedListHelper {
	
	protected static final Logger logger = LoggerFactory.getLogger(DatedAndTaggedFeedListHelper.class);
	protected Integer noOfItems;
	protected Boolean hideMonths;
	protected String[] tags;
	protected String componentID;
	protected String shortIntroduction;

	public DatedAndTaggedFeedListHelper(ValueMap properties, PageManager pageManager, Page currentPage, HttpServletRequest request,	ResourceResolver resourceResolver) {
		super(properties, pageManager, currentPage, request, resourceResolver);
	}

    @Override
    protected void init() {
    	
    	// Component Title, HyperLink & New Window
		if (this.properties.get("title", String.class) != null) {
		    this.componentTitle = this.properties.get("title",String.class);
		}
		
		if (this.properties.get("hyperlink", String.class) != null) {
		    this.hyperLink = LinkUtils.getFormattedLink(this.properties.get("hyperlink",String.class));
		}
		
		if (this.properties.get("newwindow") != null) {
		    this.newwindow = this.properties.get("newwindow",false);
		}
		
		// Root Page & Root Page Path
		this.rootPagePath = this.properties.get("rootPagePath",this.currentPage.getPath());
		this.rootPage = this.pageManager.getPage(this.rootPagePath);
		
		// Number of Items to Display
		if (this.properties.get("noOfItems", Integer.class) != null) {
		    this.noOfItems = this.properties.get("noOfItems", 3);
		} else {
		    this.noOfItems = new Integer(3);
		}
		
		// Hide Months
		this.hideMonths = this.properties.get("hideMonths", false);
		
		// ComponentID
		this.componentID = this.properties.get("componentID", "");
		
		// Short Introduction
		this.shortIntroduction = this.properties.get("shortIntroduction", "");
		
		// Tags
		this.tags = this.properties.get("cqTags", String[].class);
		
		// Handle Children
		Iterator<Page> children;
		if(this.rootPage != null) {
		    children = this.rootPage.listChildren(new PageFilter(this.request));
		} else {
			children = currentPage.listChildren(new PageFilter(this.request));
		}
		
		processChildren(children);
		
		this.initialised=true;

    }
    
	@Override
	protected void processChildren (final Iterator<Page> children) {
		this.listElements = new ArrayList<Object>();
	}
	
	@Override
	public List<Object> getChildrenElements() {
		return this.listElements;
	}
		
	public Integer getNoOfItems() {
		return noOfItems;
	}

	public void setNoOfItems(Integer noOfItems) {
		this.noOfItems = noOfItems;
	}

	public Boolean getHideMonths() {
		return hideMonths;
	}

	public void setHideMonths(Boolean hideMonths) {
		this.hideMonths = hideMonths;
	}
	
	public String getComponentID() {
		return componentID;
	}

	public void setComponentID(String componentID) {
		this.componentID = componentID;
	}
	
    public String getIntroduction() {
		return shortIntroduction;
	}

	public void setIntroduction(String shortIntroduction) {
		this.shortIntroduction = shortIntroduction;
	}

	public String[] getTags() {
		return tags;
	}
	
	public void setTags(String[] tags) {
		this.tags = tags;
	}
	
	public String getTagsString() {
		String stringifiedTags = StringUtils.EMPTY;
		
		for(String tag : this.tags ) {
			stringifiedTags += tag + ",";
		}
		if(!stringifiedTags.isEmpty()) {
			stringifiedTags = stringifiedTags.substring(0, stringifiedTags.lastIndexOf(","));
		}
		return stringifiedTags;
	}
	public boolean hasTags(){
		return this.tags != null;
	}
	

}
