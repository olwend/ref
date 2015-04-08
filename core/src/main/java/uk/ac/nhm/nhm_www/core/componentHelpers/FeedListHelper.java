package uk.ac.nhm.nhm_www.core.componentHelpers;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageFilter;
import com.day.cq.wcm.api.PageManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.nhm.nhm_www.core.model.FeedListElement;
import uk.ac.nhm.nhm_www.core.utils.LinkUtils;

public class FeedListHelper extends ListHelper {
	
	protected String rootPagePath;
	protected Integer numberOfItems;
	
	protected static final Logger logger = LoggerFactory.getLogger(FeedListHelper.class);
	
	public FeedListHelper(ValueMap properties, PageManager pageManager, Page currentPage, HttpServletRequest request, ResourceResolver resourceResolver) {
		super(properties, pageManager, currentPage, request, resourceResolver);
		init();
	}

    protected void init() {
		if (this.properties.get("title", String.class) != null) {
		    this.componentTitle = this.properties.get("title",String.class);
		}
		if (this.properties.get("hyperlink", String.class) != null) {
		    this.hyperLink = LinkUtils.getFormattedLink(this.properties.get("hyperlink",String.class));
		}
		if (this.properties.get("numberToDisplay", Integer.class) != null) {
		    this.numberOfItems = this.properties.get("numberToDisplay",6);
		} else {
		    this.numberOfItems = new Integer(6);
		}
		if (this.properties.get("newwindow") != null) {
		    this.newwindow = this.properties.get("newwindow",false);
		}
		this.rootPagePath = this.properties.get("rootPagePath",this.currentPage.getPath());
		this.rootPage = this.pageManager.getPage(this.rootPagePath);
		final Iterator<Page> children;
		if(this.rootPage != null) {
		    children = this.rootPage.listChildren(new PageFilter(this.request));
		    
		} else {
			children = currentPage.listChildren(new PageFilter(this.request));
		}
		processChildren(children);
		
		this.initialised=true;


    }
    
	protected void processChildren(Iterator<Page> children) {
			
			List<FeedListElement> pinnedElements = new ArrayList<FeedListElement>();
			List<FeedListElement> unpinnedElements = new ArrayList<FeedListElement>();
			while (children.hasNext()) {
				Page child = children.next();
				FeedListElement feedListElement = new FeedListElement(child);
				if(feedListElement.isPinned()) {
					pinnedElements.add(feedListElement);
				} else {
					unpinnedElements.add(feedListElement);
				}
				
			}
			
			int i =0;
			Iterator<FeedListElement> itrPinnedElements = pinnedElements.iterator();
			Iterator<FeedListElement> itrUnpinnedElements = unpinnedElements.iterator();
			while(itrPinnedElements.hasNext() && i< this.numberOfItems) {
				FeedListElement element = itrPinnedElements.next();
				if(element.isInitialised()) {
					listElements.add(element);
					i++;
				}
				
			}
			while(itrUnpinnedElements.hasNext() && i< this.numberOfItems) {
				
				FeedListElement element = itrUnpinnedElements.next();
				if(element.isInitialised()) {
					listElements.add(element);
					i++;
				}
				
			}
		}


	
	
    public Integer getNumberOfItems() {
		return numberOfItems;
	}

	public void setNumberOfItems(Integer numberOfItems) {
		this.numberOfItems = numberOfItems;
	}

	public String getRootPagePath() {
	return this.rootPagePath;
    }

    public void setRootPagePath(final String rootPagePath) {
    	this.rootPagePath = rootPagePath;
    }


}
