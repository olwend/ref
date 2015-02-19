package uk.ac.nhm.nhm_www.core.componentHelpers;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.nhm.nhm_www.core.model.PressReleaseFeedListElement;

public class PressReleaseFeedListHelper extends FeedListHelper {

    protected static final Logger logger = LoggerFactory.getLogger(PressReleaseFeedListHelper.class);

    public PressReleaseFeedListHelper(final ValueMap properties, final PageManager pageManager, final Page currentPage, final HttpServletRequest request, final ResourceResolver resourceResolver) {
    	super(properties, pageManager, currentPage, request, resourceResolver);
    }
    
    @Override
    protected void processChildren(final Iterator<Page> children) {
		this.listElements = new ArrayList<Object>();
		final List<PressReleaseFeedListElement> pinnedElements = new ArrayList<PressReleaseFeedListElement>();
		final List<PressReleaseFeedListElement> unpinnedElements = new ArrayList<PressReleaseFeedListElement>();
	
		
		while (children.hasNext()) {
		    final Page child = children.next();
		    final PressReleaseFeedListElement feedListElement = new PressReleaseFeedListElement(this.resourceResolver, child);
		    this.listElements.add(feedListElement);
		}
    }
    
    public List<Object> getChildrenElements() {
    	List<PressReleaseFeedListElement> sortableObjects = new ArrayList<PressReleaseFeedListElement>();
    	for(Object object: this.listElements) {
    		sortableObjects.add((PressReleaseFeedListElement) object);
    	}
    	Collections.sort(sortableObjects);
    	Collections.reverse(sortableObjects);
    	List<Object> sortedObjects = new ArrayList<Object>();
    	sortedObjects.addAll(sortableObjects);
    	return sortedObjects;
    }
    
    public List<PressReleaseFeedListElement> getTilesElements() {
		
    	final List<PressReleaseFeedListElement> pinnedElements = new ArrayList<PressReleaseFeedListElement>();
		final List<PressReleaseFeedListElement> unpinnedElements = new ArrayList<PressReleaseFeedListElement>();
		final List<PressReleaseFeedListElement> sortedListToReturn = new ArrayList<PressReleaseFeedListElement>();
		
		//while (children.hasNext()) {
		for(Object object:this.listElements){
		    PressReleaseFeedListElement prElement = (PressReleaseFeedListElement) object;
		    if(prElement.isPinned()) {
		    	pinnedElements.add(prElement);
		    } else {
		    	unpinnedElements.add(prElement);
		    }
	
		}
		Collections.sort(pinnedElements);
		Collections.reverse(pinnedElements);
		Collections.sort(unpinnedElements);
		Collections.reverse(unpinnedElements);
		final Iterator<PressReleaseFeedListElement> itrPinnedElements = pinnedElements.iterator();
		final Iterator<PressReleaseFeedListElement> itrUnpinnedElements = unpinnedElements.iterator();
		int i = 0;
		while (itrPinnedElements.hasNext() && i < this.numberOfItems ) {
			i++;
			PressReleaseFeedListElement element = itrPinnedElements.next();
			sortedListToReturn.add(element);
		}
		while (itrUnpinnedElements.hasNext() && i < this.numberOfItems) {
			i++;
			PressReleaseFeedListElement element = itrUnpinnedElements.next();
			sortedListToReturn.add(element);
		}
    	
    	
    	
    	return sortedListToReturn;
    	
    }
}
