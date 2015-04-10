package uk.ac.nhm.nhm_www.core.componentHelpers;

import com.day.cq.tagging.Tag;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageFilter;
import com.day.cq.wcm.api.PageManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.nhm.nhm_www.core.model.DatedAndTaggedFeedListElement;
import uk.ac.nhm.nhm_www.core.model.PressReleaseFeedListElement;
import uk.ac.nhm.nhm_www.core.utils.LinkUtils;

public class DatedAndTaggedFeedListHelper extends PressReleaseFeedListHelper {
	
	protected static final Logger logger = LoggerFactory.getLogger(DatedAndTaggedFeedListHelper.class);
	protected Integer noOfItems;
	//protected Integer noOfScienceItems;
	protected Tag[] tags;


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
		
		// Tags
		this.tags = this.properties.get("tags", this.currentPage.getTags());
		
		// Handle Children **WARNING** not handling Science children yet, TBImplemented
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
		
		while (children.hasNext()) {
			Page child = children.next();
		    final DatedAndTaggedFeedListElement feedListElement = new DatedAndTaggedFeedListElement(child);
		    this.listElements.add(feedListElement);
			
//			if (pageHasTags(child, this.tags)){
//				DatedAndTaggedFeedListElement taggedElement = new DatedAndTaggedFeedListElement(child);
//				this.listElements.add(taggedElement);
//			}
		}
	}
	
	protected boolean pageHasTags(Page page, Tag[] tags){
		boolean found = false;
		List<Tag> pageTags = Arrays.asList(page.getTags()); 
		Iterator<Tag> filterIterator = Arrays.asList(tags).iterator();
		
		while(!found && filterIterator.hasNext()){
			Tag tagFilter = filterIterator.next();
			found = pageTags.contains(tagFilter); 
		}
		return found;
		
	}
		
	public Integer getNoOfItems() {
		return noOfItems;
	}

	public void setNoOfItems(Integer noOfItems) {
		this.noOfItems = noOfItems;
	}
	
	
	
}
