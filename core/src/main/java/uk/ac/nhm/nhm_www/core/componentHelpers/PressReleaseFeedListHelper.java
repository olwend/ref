package uk.ac.nhm.nhm_www.core.componentHelpers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

import uk.ac.nhm.nhm_www.core.model.FeedListElement;
import uk.ac.nhm.nhm_www.core.model.PressReleaseFeedListElement;

public class PressReleaseFeedListHelper extends FeedListHelper {
	
	protected static final Logger logger = LoggerFactory.getLogger(PressReleaseFeedListHelper.class);
	
	public PressReleaseFeedListHelper(ValueMap properties, PageManager pageManager, Page currentPage, HttpServletRequest request, ResourceResolver resourceResolver) {
		super(properties, pageManager, currentPage, request, resourceResolver);
	}
	
	protected void processChildren(Iterator<Page> children) {
		String test = "";
		if(children.hasNext()){
			test = "TOTO";
		}
		
		
		
		List<PressReleaseFeedListElement> pinnedElements = new ArrayList<PressReleaseFeedListElement>();
		List<PressReleaseFeedListElement> unpinnedElements = new ArrayList<PressReleaseFeedListElement>();
		
		while (children.hasNext()) {
			Page child = children.next();
			PressReleaseFeedListElement feedListElement = new PressReleaseFeedListElement(this.resourceResolver, child);
			if(feedListElement.isPinned()) {
				pinnedElements.add(feedListElement);
			} else {
				unpinnedElements.add(feedListElement);
			}
			
		}
		int i =0;
		Collections.sort(pinnedElements);
		Collections.sort(unpinnedElements);
		Iterator<PressReleaseFeedListElement> itrPinnedElements = pinnedElements.iterator();
		Iterator<PressReleaseFeedListElement> itrUnpinnedElements = unpinnedElements.iterator();
		
		while(itrPinnedElements.hasNext() && i< this.numberOfItems) {
			feedListElements.add(itrPinnedElements.next());
			i++;
		}
		while(itrUnpinnedElements.hasNext() && i< this.numberOfItems) {
			logger.error("AAAAAH: " );
			feedListElements.add(itrUnpinnedElements.next());
			i++;
		}
	}	
}
