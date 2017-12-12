package uk.ac.nhm.core.model;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;

public class PressReleaseFeedListElementImpl extends FeedListElementImpl implements PressReleaseFeedListElement {
	protected Date pressReleaseDate;
	
	protected static final Logger logger = LoggerFactory.getLogger(PressReleaseFeedListElementImpl.class);
	
	public PressReleaseFeedListElementImpl(Page page) {
		super(page);
		this.pressReleaseDate = page.getProperties().get("publishdate", Date.class);
	}
	
	public PressReleaseFeedListElementImpl() {
		super();
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see uk.ac.nhm.core.model.PressReleaseFeedListElement#isInitialised()
	 */
	@Override
	public boolean isInitialised() {
		if(this.title != null && this.elementLink !=null && this.imageResourcePath !=null && this.pressReleaseDate != null) {
			return true;
		} else {
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see uk.ac.nhm.core.model.PressReleaseFeedListElement#setPressReleaseDate(java.util.Date)
	 */
	@Override
	public void setPressReleaseDate(Date pressReleaseDate) {
		this.pressReleaseDate = pressReleaseDate;
	}

	/* (non-Javadoc)
	 * @see uk.ac.nhm.core.model.PressReleaseFeedListElement#getPressReleaseDate()
	 */
	@Override
	public Date getPressReleaseDate() {
		return pressReleaseDate;
	}

	/* (non-Javadoc)
	 * @see uk.ac.nhm.core.model.PressReleaseFeedListElement#compareTo(uk.ac.nhm.core.model.PressReleaseFeedListElementImpl)
	 */

	@Override
	public int compareTo(PressReleaseFeedListElement o) {
		return getPressReleaseDate().compareTo(o.getPressReleaseDate());
	}

}
