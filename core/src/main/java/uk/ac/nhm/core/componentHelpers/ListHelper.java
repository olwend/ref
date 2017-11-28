package uk.ac.nhm.core.componentHelpers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;

import uk.ac.nhm.core.model.DatedAndTaggedFeedListElement;
import uk.ac.nhm.core.model.ListElementImpl;
import uk.ac.nhm.core.utils.LinkUtils;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageFilter;
import com.day.cq.wcm.api.PageManager;

public class ListHelper extends HelperBase {
	protected ValueMap properties;
	protected PageManager pageManager;
	protected Page currentPage;
	protected Page rootPage;
	protected HttpServletRequest request;
	protected ResourceResolver resourceResolver;
	protected String componentTitle;
	protected List<Object> listElements;
	protected String hyperLink;
	protected Boolean newwindow;
	protected Boolean initialised;
	
	public ListHelper(ValueMap properties, PageManager pageManager, Page currentPage, HttpServletRequest request, ResourceResolver resourceResolver) {
		this.properties = properties;
		this.pageManager = pageManager;
		this.currentPage = currentPage;
		this.request = request;
		this.resourceResolver = resourceResolver;
		init();
	}

	protected void init() {
		Page landingPage = currentPage.getAbsoluteParent(4);
		
		if (this.properties.get("title", String.class) != null) {
		    this.componentTitle = this.properties.get("title",String.class);
		}
		
		if (this.properties.get("hyperlink", String.class) != null) {
		    this.hyperLink = LinkUtils.getFormattedLink(this.properties.get("hyperlink",String.class));
		}

		if(landingPage != null) {
			Iterator<Page> children = landingPage.listChildren(new PageFilter(request));
			processChildren(children);
		}
	}
	
	protected void processChildren(Iterator<Page> children) {
		listElements = new ArrayList<Object>();
		List<ListElementImpl> elements = new ArrayList<ListElementImpl>();
		while (children.hasNext()) {
			Page childPage = children.next();
			ListElementImpl element = new ListElementImpl(childPage);
			if(element.isInitialised()) {
				elements.add(element);
			}
		}
	}

	public Boolean isInitialised() {
		return initialised;
	}

	public void setInitialised(Boolean initialised) {
		this.initialised = initialised;
	}

	public String getComponentTitle() {
		return componentTitle;
	}

	public void setComponentTitle(String componentTitle) {
		this.componentTitle = componentTitle;
	}

	public String getHyperLink() {
		return hyperLink;
	}

	public void setHyperLink(String hyperLink) {
		this.hyperLink = hyperLink;
	}
	
    public Boolean getNewwindow() {
		return newwindow;
	}

	public void setNewwindow(Boolean newwindow) {
		this.newwindow = newwindow;
	}

	public boolean validateHyperlink() {
    	return LinkUtils.validateUrl(this.hyperLink);
    }
    
    public List<Object> getChildrenElements() {
    	return  this.listElements;
    }
    
    public void addListElement(final Object element) {
    	this.listElements.add(element);
    }
    
    public void addAllListElements(final List<Object> elements) {
    	this.listElements.addAll(elements);
    }
}
