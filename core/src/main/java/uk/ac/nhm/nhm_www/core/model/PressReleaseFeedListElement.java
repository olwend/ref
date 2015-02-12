package uk.ac.nhm.nhm_www.core.model;

import java.util.Date;

import org.apache.sling.api.resource.ResourceResolver;

import com.day.cq.wcm.api.PageManager;

public class PressReleaseFeedListElement extends FeedListElement {
	private Date pressReleaseDate;
	
	public PressReleaseFeedListElement(PageManager pageManager, ResourceResolver resourceResolver) {
		super(pageManager, resourceResolver);
	}

	public void setPressReleaseDate(Date pressReleaseDate) {
		this.pressReleaseDate = pressReleaseDate;
	}

	public Date getPressReleaseDate() {
		return pressReleaseDate;
	}

	
}
