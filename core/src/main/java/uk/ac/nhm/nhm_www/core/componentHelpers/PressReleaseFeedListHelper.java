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

	int j = 0;
	while (children.hasNext()) {
	    j++;
	    System.out.println("j " + j);
	    final Page child = children.next();
	    final PressReleaseFeedListElement feedListElement = new PressReleaseFeedListElement(this.resourceResolver, child);
	    if(feedListElement.isPinned()) {
		System.out.println("in is pinned");
		pinnedElements.add(feedListElement);
	    } else {
		unpinnedElements.add(feedListElement);
	    }

	}
	Collections.sort(pinnedElements);
	Collections.sort(unpinnedElements);
	final Iterator<PressReleaseFeedListElement> itrPinnedElements = pinnedElements.iterator();
	final Iterator<PressReleaseFeedListElement> itrUnpinnedElements = unpinnedElements.iterator();

	while (itrPinnedElements.hasNext()/* && i < this.numberOfItems */) {
	    this.listElements.add(itrPinnedElements.next());
	}
	while (itrUnpinnedElements.hasNext()/* && i < this.numberOfItems */) {
	    this.listElements.add(itrUnpinnedElements.next());
	}

    }
}
