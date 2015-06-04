package uk.ac.nhm.nhm_www.core.model;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;

import uk.ac.nhm.nhm_www.core.utils.PageUtils;

import com.day.cq.wcm.api.Page;

public class FeedListElementImpl extends ListElementImpl implements FeedListElement {
	protected String imageResourcePath;
	protected String intro;
	protected String title;
	protected Boolean pinned;
	protected ResourceResolver resourceResolver;
	protected Page page;

	public FeedListElementImpl(final Page page) {
		super(page);
		this.page = page;
		this.title = PageUtils.getPageTitle(page);
		this.intro = page.getProperties().get("summary", String.class);
		this.pinned = page.getProperties().get("pinned", false);
		final Resource resource = page.adaptTo(Resource.class);
		this.imageResourcePath = resource.getPath() + "/jcr:content/image";
	}

	public FeedListElementImpl() {
		super();
	}

	/* (non-Javadoc)
	 * @see uk.ac.nhm.nhm_www.core.model.FeedListElement#isInitialised()
	 */
	@Override
	public boolean isInitialised() {
		if (this.title != null && this.elementLink != null
				&& this.imageResourcePath != null) {
			return true;
		} else {
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see uk.ac.nhm.nhm_www.core.model.FeedListElement#getPage()
	 */
	@Override
	public Page getPage() {
		return this.page;
	}

	/* (non-Javadoc)
	 * @see uk.ac.nhm.nhm_www.core.model.FeedListElement#setPage(com.day.cq.wcm.api.Page)
	 */
	@Override
	public void setPage(Page page) {
		this.page = page;
	}

	/* (non-Javadoc)
	 * @see uk.ac.nhm.nhm_www.core.model.FeedListElement#getImagePath()
	 */
	@Override
	public String getImagePath() {
		return this.imageResourcePath;
	}

	/* (non-Javadoc)
	 * @see uk.ac.nhm.nhm_www.core.model.FeedListElement#setImagePath(java.lang.String)
	 */
	@Override
	public void setImagePath(final String imageResourcePath) {
		this.imageResourcePath = imageResourcePath;
	}

	/* (non-Javadoc)
	 * @see uk.ac.nhm.nhm_www.core.model.FeedListElement#getIntro()
	 */
	@Override
	public String getIntro() {
		return this.intro;
	}

	/* (non-Javadoc)
	 * @see uk.ac.nhm.nhm_www.core.model.FeedListElement#setIntro(java.lang.String)
	 */
	@Override
	public void setIntro(final String intro) {
		this.intro = intro;
	}

	/* (non-Javadoc)
	 * @see uk.ac.nhm.nhm_www.core.model.FeedListElement#isPinned()
	 */
	@Override
	public Boolean isPinned() {
		return this.pinned;
	}

	/* (non-Javadoc)
	 * @see uk.ac.nhm.nhm_www.core.model.FeedListElement#setPinned(java.lang.Boolean)
	 */
	@Override
	public void setPinned(final Boolean pinned) {
		this.pinned = pinned;
	}

}
