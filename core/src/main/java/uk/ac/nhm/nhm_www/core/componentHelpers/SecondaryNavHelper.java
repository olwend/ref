package uk.ac.nhm.nhm_www.core.componentHelpers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.nhm.nhm_www.core.model.ListElement;
import uk.ac.nhm.nhm_www.core.utils.LinkUtils;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageFilter;
import com.day.cq.wcm.api.PageManager;

public class SecondaryNavHelper extends ListHelper{
	Page sectionLandingPage;
	protected static final Logger logger = LoggerFactory.getLogger(PressReleaseFeedListHelper.class);
	
	public SecondaryNavHelper(ValueMap properties, PageManager pageManager,
			Page currentPage, HttpServletRequest request,
			ResourceResolver resourceResolver) {
		super(properties, pageManager, currentPage, request, resourceResolver);
		init();
	}
	
	protected void init() {
		this.sectionLandingPage = currentPage.getAbsoluteParent(4);
		if (this.properties.get("title", String.class) != null) {
		    this.componentTitle = this.properties.get("title",String.class);
		}
		if (this.properties.get("hyperlink", String.class) != null) {
		    this.hyperLink = LinkUtils.getFormattedLink(this.properties.get("hyperlink",String.class));
		}

		if(sectionLandingPage != null) {
			Iterator<Page> children = sectionLandingPage.listChildren(new PageFilter(request));
			processChildren(children);
		}
		this.initialised = true;
	}
	
	protected void processChildren(Iterator<Page> children) {
		listElements = new ArrayList<Object>();
		//List<ListElement> elements = new ArrayList<ListElement>();
		while (children.hasNext()) {
		
			Page childPage = children.next();
			listElements.add(new ListElement(childPage));
		}
	}

	public Page getSectionLandingPage() {
		return sectionLandingPage;
	}

	public void setSectionLandingPage(Page sectionLandingPage) {
		this.sectionLandingPage = sectionLandingPage;
	}

}
