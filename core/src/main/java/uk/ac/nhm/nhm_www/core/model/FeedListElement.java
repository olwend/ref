package uk.ac.nhm.nhm_www.core.model;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;

import uk.ac.nhm.nhm_www.core.utils.*;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;


public class FeedListElement {
	protected String imageResourcePath;
	protected String intro;
	protected String title;
	protected Boolean pinned;
	protected ResourceResolver resourceResolver;
	
	public FeedListElement(ResourceResolver resourceResolver, Page page) {
		this.title = PageUtils.getPageTitle(page);
		this.intro = PageUtils.getPageDescription(page);
		this.pinned = page.getProperties().get("pinned", false);  
		Resource resource = page.adaptTo(Resource.class);
		this.imageResourcePath = resource.getPath()+"/jcr:content/image";
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


	public Boolean isPinned() {
		return pinned;
	}


	public void setPinned(Boolean pinned) {
		this.pinned = pinned;
	}
	
	

	
	
		
}
