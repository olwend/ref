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

		this.initialised = true;

    public FeedListHelper(final ValueMap properties, final PageManager pageManager, final Page currentPage, final HttpServletRequest request, final ResourceResolver resourceResolver) {
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
	} else {
	    this.numberOfItems = new Integer(6);
	}
	if (this.properties.get("newwindow") != null) {
	    this.newwindow = this.properties.get("newwindow",false);
	}
	this.rootPagePath = this.properties.get("rootPagePath",this.currentPage.getPath());
	this.rootPage = this.pageManager.getPage(this.rootPagePath);
	if(this.rootPage != null) {
	    final Iterator<Page> children = this.rootPage.listChildren(new PageFilter(this.request));
	    processChildren(children);
	}


    }

    protected void processChildren(final Iterator<Page> children) {

	final List<FeedListElement> pinnedElements = new ArrayList<FeedListElement>();
	final List<FeedListElement> unpinnedElements = new ArrayList<FeedListElement>();
	while (children.hasNext()) {
	    final Page child = children.next();
	    final FeedListElement feedListElement = new FeedListElement(this.resourceResolver, child);
	    if(feedListElement.isPinned()) {
		pinnedElements.add(feedListElement);
	    } else {
		unpinnedElements.add(feedListElement);
	    }

	}
	int i =0;
	final Iterator<FeedListElement> itrPinnedElements = pinnedElements.iterator();
	final Iterator<FeedListElement> itrUnpinnedElements = unpinnedElements.iterator();
	while(itrPinnedElements.hasNext() && i< this.numberOfItems) {
	    this.feedListElements.add(itrPinnedElements.next());
	    i++;
	}
	while(itrPinnedElements.hasNext() && i< this.numberOfItems) {
	    this.feedListElements.add(itrUnpinnedElements.next());
	    i++;
	}

	public Boolean getNewwindow() {
		return newwindow;
	}

	public void setNewwindow(Boolean newwindow) {
		this.newwindow = newwindow;
	}

	
	
	

    public void setComponentTitle(final String title) {
	this.componentTitle = title;

    }

    public Object getComponentTitle() {
	return this.componentTitle;
    }

    public void setHyperlink(final String string) {
	this.hyperLink = string;
    }

    public String getHyperlink() {
	return this.hyperLink;
    }

    public boolean validateHyperlink() {
	return LinkUtils.validateUrl(this.hyperLink);
    }

    public String getRootPagePath() {
	return this.rootPagePath;
    }

    public void setRootPagePath(final String rootPagePath) {
	this.rootPagePath = rootPagePath;
    }

    public Boolean isInitialised() {
	return this.initialised;
    }

    public void setInitialised(final Boolean initialised) {
	this.initialised = initialised;
    }


    public void addListElement(final Object element) {
	this.feedListElements.add(element);

    }

    public List<Object> getChildrenElements() {
	return  this.feedListElements;
    }




}
