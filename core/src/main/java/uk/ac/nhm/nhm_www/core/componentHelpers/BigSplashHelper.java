package uk.ac.nhm.nhm_www.core.componentHelpers;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ValueMap;

import com.day.cq.wcm.api.Page;

public class BigSplashHelper {
	
	private String videoId;
	private String imagePath;
	private String title;
	private String subtitle;
	private String caption;
	
	private String ctaText;
	private String ctaUrl;
	
	public BigSplashHelper(SlingHttpServletRequest request, Page page, ValueMap properties) {
		this.videoId = properties.get("youtube", String.class);
		this.imagePath = properties.get("alternativeimage", String.class);
		this.title = properties.get("title", String.class);
		this.subtitle = properties.get("subtitle", String.class);
		this.caption = properties.get("caption", String.class);
		
		this.ctaText = properties.get("ctatext", String.class);
		this.ctaUrl = properties.get("ctaurl", String.class);
	}

	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getCtaText() {
		return ctaText;
	}

	public void setCtaText(String ctaText) {
		this.ctaText = ctaText;
	}

	public String getCtaUrl() {
		return ctaUrl;
	}

	public void setCtaUrl(String ctaUrl) {
		this.ctaUrl = ctaUrl;
	}

}