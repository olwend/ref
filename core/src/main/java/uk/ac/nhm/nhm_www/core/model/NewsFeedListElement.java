package uk.ac.nhm.nhm_www.core.model;

import org.apache.sling.api.resource.ResourceResolver;

import com.day.cq.tagging.Tag;
import com.day.cq.wcm.api.Page;

public class NewsFeedListElement extends PressReleaseFeedListElement{
	
    protected Tag[] tags;

	public NewsFeedListElement(ResourceResolver resourceResolver, Page page) {
		super(page);
		this.tags = page.getTags();
		
	}

}