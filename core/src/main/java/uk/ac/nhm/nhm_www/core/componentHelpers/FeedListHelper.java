package uk.ac.nhm.nhm_www.core.componentHelpers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageFilter;
import com.day.cq.wcm.api.PageManager;

import uk.ac.nhm.nhm_www.core.model.FeedListElement;
import uk.ac.nhm.nhm_www.core.utils.LinkUtils;

public class FeedListHelper extends HelperBase {
	protected ValueMap properties;
	protected PageManager pageManager;
	protected Page currentPage;
	protected Page rootPage;
	protected HttpServletRequest request;
	protected ResourceResolver resourceResolver;
	protected String componentTitle;
	protected String hyperLink;
	protected String rootPagePath;
	protected Integer numberOfItems;
	protected Boolean newwindow;
	protected Boolean initialised;
	protected List<Object> feedListElements;
	
	
	public FeedListHelper(ValueMap properties, PageManager pageManager, Page currentPage, HttpServletRequest request, ResourceResolver resourceResolver) {
		this.properties = properties;
		this.pageManager = pageManager;
		this.currentPage = currentPage;
		
		this.request = request;
		this.resourceResolver = resourceResolver;
		this.feedListElements = new ArrayList<Object>();
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
		}
		if (this.properties.get("newwindow") != null) {
			this.newwindow = this.properties.get("newwindow",false);
		}
		this.rootPagePath = this.properties.get("rootPagePath",currentPage.getPath());
		this.rootPage = pageManager.getPage(rootPagePath);
		if(rootPage != null) {
			Iterator<Page> children = rootPage.listChildren(new PageFilter(request));
			processChildren(children);
		}
		this.initialised = true;

		
	}

	protected void processChildren(Iterator<Page> children) {
		
		List<FeedListElement> pinnedElements = new ArrayList<FeedListElement>();
		List<FeedListElement> unpinnedElements = new ArrayList<FeedListElement>();
		while (children.hasNext()) {
			Page child = children.next();
			FeedListElement feedListElement = new FeedListElement(this.resourceResolver, child);
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
			feedListElements.add(itrPinnedElements.next());
			i++;
		}
		while(itrPinnedElements.hasNext() && i< this.numberOfItems) {
			feedListElements.add(itrUnpinnedElements.next());
			i++;
		}
		
	}

	public void setComponentTitle(String title) {
		this.componentTitle = title; 
		
	}

	public Object getComponentTitle() {
		return this.componentTitle;
	}

	public void setHyperlink(String string) {
		this.hyperLink = string;
	}

	public String getHyperlink() {
		return this.hyperLink;
	}

	public boolean validateHyperlink() {
		return LinkUtils.validateUrl(this.hyperLink);
	}
	
	public String getRootPagePath() {
		return rootPagePath;
	}

	public void setRootPagePath(String rootPagePath) {
		this.rootPagePath = rootPagePath;
	}
	
	public Boolean isInitialised() {
		return initialised;
	}

	public void setInitialised(Boolean initialised) {
		this.initialised = initialised;
	}
	

	public void addListElement(Object element) {
		this.feedListElements.add(element);
		
	}
	
	public List<Object> getChildrenElements() {
		return  this.feedListElements;
	}

	public Boolean getNewwindow() {
		return newwindow;
	}

	public void setNewwindow(Boolean newwindow) {
		this.newwindow = newwindow;
	}

	
	
	

}
