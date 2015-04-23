package uk.ac.nhm.nhm_www.core.model;

import org.apache.sling.api.resource.ResourceResolver;

import uk.ac.nhm.nhm_www.core.utils.PageUtils;

import com.day.cq.wcm.api.Page;

public class ListElement {
	protected String title;
	protected String elementLink;

	public ListElement() {

	}

	public ListElement(Page page) {
		this.title = PageUtils.getPageTitle(page);
		this.elementLink = page.getPath();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getElementLink() {
		return elementLink;
	}

	public void setElementLink(String elementLink) {
		this.elementLink = elementLink;
	}

	public boolean isInitialised() {
		if (this.title != null && this.elementLink != null) {
			return true;
		} else {
			return false;
		}
	}

}
