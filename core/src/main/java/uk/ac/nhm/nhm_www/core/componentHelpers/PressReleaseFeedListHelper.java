package uk.ac.nhm.nhm_www.core.componentHelpers;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

import uk.ac.nhm.nhm_www.core.model.PressReleaseFeedListElement;

public class PressReleaseFeedListHelper extends FeedListHelper {
	
	public PressReleaseFeedListHelper(ValueMap properties, PageManager pageManager, Page currentPage, HttpServletRequest request, ResourceResolver resourceResolver) {
		super(properties, pageManager, currentPage, request, resourceResolver);
		
		
	}
	
	protected void processChildren(Iterator<Page> children) {
		while (children.hasNext()) {
			Page child = children.next();
			PressReleaseFeedListElement prFeedListElement = new PressReleaseFeedListElement(resourceResolver, child);
			this.feedListElements.add(prFeedListElement);
		}
		
	}
	
	@Override
	public List<Object> getChildrenElements() {
		return feedListElements;
	}

}
