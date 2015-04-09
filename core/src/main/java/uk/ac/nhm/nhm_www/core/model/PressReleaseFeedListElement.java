package uk.ac.nhm.nhm_www.core.model;

import java.util.Date;
import java.util.Iterator;

import uk.ac.nhm.nhm_www.core.componentHelpers.PressReleaseFeedListHelper;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PressReleaseFeedListElement extends FeedListElement implements Comparable<PressReleaseFeedListElement> {
	protected Date pressReleaseDate;
	
	protected static final Logger logger = LoggerFactory.getLogger(PressReleaseFeedListElement.class);
	
	public PressReleaseFeedListElement(Page page) {
		super(page);
		this.pressReleaseDate = page.getProperties().get("publishdate", Date.class);
	}
	
	public boolean isInitialised() {
		if(this.title != null && this.elementLink !=null && this.imageResourcePath !=null && this.pressReleaseDate != null) {
			return true;
		} else {
			return false;
		}
	}
	
	public void setPressReleaseDate(Date pressReleaseDate) {
		this.pressReleaseDate = pressReleaseDate;
	}

	public Date getPressReleaseDate() {
		return pressReleaseDate;
	}

	@Override
	public int compareTo(PressReleaseFeedListElement o) {
		return getPressReleaseDate().compareTo(o.getPressReleaseDate());
	}
	
	
}
