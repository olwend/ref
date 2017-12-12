package uk.ac.nhm.core.model;

import uk.ac.nhm.core.utils.PageUtils;

import com.day.cq.wcm.api.Page;

public class ListElementImpl implements ListElement {
	protected String title;
	protected String elementLink;

	public ListElementImpl() {

	}

	public ListElementImpl(Page page) {
		this.title = PageUtils.getPageTitle(page);
		this.elementLink = page.getPath();
	}

	/* (non-Javadoc)
	 * @see uk.ac.nhm.core.model.ListElement#getTitle()
	 */
	@Override
	public String getTitle() {
		return title;
	}

	/* (non-Javadoc)
	 * @see uk.ac.nhm.core.model.ListElement#setTitle(java.lang.String)
	 */
	@Override
	public void setTitle(String title) {
		this.title = title;
	}

	/* (non-Javadoc)
	 * @see uk.ac.nhm.core.model.ListElement#getElementLink()
	 */
	@Override
	public String getElementLink() {
		return elementLink;
	}

	/* (non-Javadoc)
	 * @see uk.ac.nhm.core.model.ListElement#setElementLink(java.lang.String)
	 */
	@Override
	public void setElementLink(String elementLink) {
		this.elementLink = elementLink;
	}

	/* (non-Javadoc)
	 * @see uk.ac.nhm.core.model.ListElement#isInitialised()
	 */
	@Override
	public boolean isInitialised() {
		if (this.title != null && this.elementLink != null) {
			return true;
		} else {
			return false;
		}
	}

}
