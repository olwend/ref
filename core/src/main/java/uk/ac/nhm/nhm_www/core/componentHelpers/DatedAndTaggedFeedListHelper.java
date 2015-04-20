package uk.ac.nhm.nhm_www.core.componentHelpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.jcr.Value;
import javax.servlet.http.HttpServletRequest;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.nhm.nhm_www.core.model.DatedAndTaggedFeedListElement;
import uk.ac.nhm.nhm_www.core.model.PressReleaseFeedListElement;
import uk.ac.nhm.nhm_www.core.utils.LinkUtils;

import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageFilter;
import com.day.cq.wcm.api.PageManager;

public class DatedAndTaggedFeedListHelper extends PressReleaseFeedListHelper {
	
	protected static final Logger logger = LoggerFactory.getLogger(DatedAndTaggedFeedListHelper.class);
	protected Integer noOfItems;
	protected Boolean hideMonths;
	protected String[] tags;


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
		if (this.properties.get("hideMonths") != null) {
		    this.hideMonths = this.properties.get("hideMonths",false);
		}
		
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
		
		/*while (children.hasNext()) {
			Page child = children.next();
		    final DatedAndTaggedFeedListElement feedListElement = new DatedAndTaggedFeedListElement(child);
		    this.listElements.add(feedListElement);
		}*/
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
	
	public String[] getTags() {
		for(String tag : tags ) {
			logger.error("tag ffrom helper: " + tag);
		}
		return tags;
	}

	public void setTags(String[] tags) {
		this.tags = tags;
	}
	
	public boolean hasTags(){
		return this.tags != null;
	}
	
	
	
//	public boolean hasTags(Tag[] tags) {
//		boolean found = false;
//		List<Tag> pageTags = Arrays.asList(this.tags);
//		Iterator<Tag> tagsToCheck = Arrays.asList(tags).iterator();
//
//		while (!found && tagsToCheck.hasNext()) {
//			Tag tag = tagsToCheck.next();
//			found = pageTags.contains(tag);
//		}
//		return found;
//	}
//	
//	protected boolean pageHasTags(Page page, Tag[] tags){
//		return new DatedAndTaggedFeedListElement(page).hasTags(tags);
//	}
	
}
