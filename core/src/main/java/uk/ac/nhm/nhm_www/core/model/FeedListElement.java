package uk.ac.nhm.nhm_www.core.model;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;

import uk.ac.nhm.nhm_www.core.utils.PageUtils;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;


public class FeedListElement {
	protected String imageResourcePath;
	protected String intro;
	protected String title;
	protected ResourceResolver resourceResolver;
	
	public FeedListElement(ResourceResolver resourceResolver, Page page) {
		this.title = PageUtils.getPageTitle(page);
		this.intro = PageUtils.getPageDescription(page);
		this.imageResourcePath = this.resourceResolver.getResource(page.getPath()).getChild("image").getPath();
	}


	public String getImagePath() {
		return imageResourcePath;
	}


	public void setImagePath(String imageResourcePath) {
		this.imageResourcePath = imageResourcePath;
	}


	public String getIntro() {
		return intro;
	}


	public void setIntro(String intro) {
		this.intro = intro;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}

	
	
		
}
