package uk.ac.nhm.nhm_www.core.model;

import org.apache.sling.api.resource.ResourceResolver;
import com.day.cq.tagging.Tag;
import com.day.cq.wcm.api.Page;

public class NewsFeedElement extends FeedListElement {
	
    protected Tag[] tags;				//Are these the tags I want? It looks like they are...

	public NewsFeedElement(ResourceResolver resourceResolver, Page page) {
		super(resourceResolver, page);
		this.tags = page.getTags();
		
	}

}