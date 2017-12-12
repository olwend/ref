package uk.ac.nhm.core.componentHelpers;

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

import uk.ac.nhm.core.model.PressReleaseFeedListElement;
import uk.ac.nhm.core.model.PressReleaseFeedListElementImpl;

public class PressReleaseFeedListHelper extends FeedListHelper {

    protected static final Logger logger = LoggerFactory.getLogger(PressReleaseFeedListHelper.class);

    public PressReleaseFeedListHelper(final ValueMap properties, final PageManager pageManager, final Page currentPage, final HttpServletRequest request, final ResourceResolver resourceResolver) {
    	super(properties, pageManager, currentPage, request, resourceResolver);
    }
    
    @Override
    protected void processChildren(final Iterator<Page> children) {
		this.listElements = new ArrayList<Object>();
		final List<PressReleaseFeedListElementImpl> pinnedElements = new ArrayList<PressReleaseFeedListElementImpl>();
		final List<PressReleaseFeedListElementImpl> unpinnedElements = new ArrayList<PressReleaseFeedListElementImpl>();
	
		
		while (children.hasNext()) {
		    final Page child = children.next();
		    final PressReleaseFeedListElement feedListElement = new PressReleaseFeedListElementImpl(child);
		    this.listElements.add(feedListElement);
		}
    }
    
    public List<Object> getChildrenElements() {
    	List<PressReleaseFeedListElementImpl> sortableObjects = new ArrayList<PressReleaseFeedListElementImpl>();
    	for(Object object: this.listElements) {
    		PressReleaseFeedListElementImpl element = (PressReleaseFeedListElementImpl) object;
    		if(element.isInitialised()) {
    			sortableObjects.add(element);
    		}
    	}
    	Collections.sort(sortableObjects);
    	Collections.reverse(sortableObjects);
    	List<Object> sortedObjects = new ArrayList<Object>();
    	sortedObjects.addAll(sortableObjects);
    	return sortedObjects;
    }
    
    public List<PressReleaseFeedListElementImpl> getTilesElements() {
		
    	final List<PressReleaseFeedListElementImpl> pinnedElements = new ArrayList<PressReleaseFeedListElementImpl>();
		final List<PressReleaseFeedListElementImpl> unpinnedElements = new ArrayList<PressReleaseFeedListElementImpl>();
		final List<PressReleaseFeedListElementImpl> sortedListToReturn = new ArrayList<PressReleaseFeedListElementImpl>();
		
		//while (children.hasNext()) {
		for(Object object:this.listElements){
		    PressReleaseFeedListElementImpl prElement = (PressReleaseFeedListElementImpl) object;
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
		final Iterator<PressReleaseFeedListElementImpl> itrPinnedElements = pinnedElements.iterator();
		final Iterator<PressReleaseFeedListElementImpl> itrUnpinnedElements = unpinnedElements.iterator();
		int i = 0;
		while (itrPinnedElements.hasNext() && i < this.numberOfItems ) {
			i++;
			PressReleaseFeedListElementImpl element = itrPinnedElements.next();
			sortedListToReturn.add(element);
		}
		while (itrUnpinnedElements.hasNext() && i < this.numberOfItems) {
			i++;
			PressReleaseFeedListElementImpl element = itrUnpinnedElements.next();
			sortedListToReturn.add(element);
		}
    	
    	
    	
    	return sortedListToReturn;
    	
    }
}
