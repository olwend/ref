package uk.ac.nhm.nhm_www.core.componentHelpers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;

import uk.ac.nhm.nhm_www.core.model.ListElement;

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
		if(landingPage != null) {
			Iterator<Page> children = rootPage.listChildren(new PageFilter(request));
			processChildren(children);
		}
		
	}


	protected void processChildren(Iterator<Page> children) {
		listElements = new ArrayList<Object>();
		List<ListElement> elements = new ArrayList<ListElement>();
		while (children.hasNext()) {
			Page childPage = children.next();
			elements.add(new ListElement(childPage));
		}
		
	}


	public Boolean isInitialised() {
		return initialised;
	}


	public void setInitialised(Boolean initialised) {
		this.initialised = initialised;
	}
	
	
	
	
	
}
