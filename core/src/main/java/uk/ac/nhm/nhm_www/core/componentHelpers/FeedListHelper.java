package uk.ac.nhm.nhm_www.core.componentHelpers;

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

		
	}

	protected void processChildren(Iterator<Page> children) {
		int i =0;
		while (children.hasNext() && i< this.numberOfItems) {
			Page child = children.next();
			FeedListElement feedListElement = new FeedListElement(this.resourceResolver, child);
			feedListElements.add(feedListElement);
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
	

	public void addListElement(FeedListElement element) {
		// TODO Auto-generated method stub
		
	}
	
	public List<Object> getChildrenElements() {
		return  this.feedListElements;
	}

	
	

}
