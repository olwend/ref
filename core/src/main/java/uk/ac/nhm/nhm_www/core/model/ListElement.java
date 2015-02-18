package uk.ac.nhm.nhm_www.core.model;

import org.apache.sling.api.resource.ResourceResolver;

import uk.ac.nhm.nhm_www.core.utils.PageUtils;

import com.day.cq.wcm.api.Page;

public class ListElement {
	protected String title;

	public ListElement(Page page) {
		this.title = PageUtils.getPageTitle(page);
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	
	
}
