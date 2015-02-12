package uk.ac.nhm.nhm_www.core.model;

import org.apache.sling.api.resource.ResourceResolver;

import com.day.cq.wcm.api.PageManager;


public class FeedListElement {
	private String imagePath;
	private String intro;
	private String title;
	
	
	public FeedListElement(PageManager pageManager, ResourceResolver resourceResolver) {
		
	}


	public String getImagePath() {
		return imagePath;
	}


	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
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
