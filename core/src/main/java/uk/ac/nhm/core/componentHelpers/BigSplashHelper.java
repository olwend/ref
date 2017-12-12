package uk.ac.nhm.core.componentHelpers;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.commons.ImageResource;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.foundation.Image;

public class BigSplashHelper {
	
	private String videoId;
	private String imagePath;
	private String imageAlt;
	private String title;
	private String subtitle;
	private String caption;
	
	private String ctaText;
	private String ctaUrl;
	private Boolean applyLinkToTitle;
	
	private ResourceResolver resourceResolver;
	private ImageResource image;
	
	protected static final Logger LOG = LoggerFactory.getLogger(Foundation5ImageHelper.class);
	private static final String IMAGE_ALT_DEFAULT = "Default";
	
	public BigSplashHelper(SlingHttpServletRequest request, Page page, ValueMap properties) {
		this.videoId = properties.get("youtube", String.class);
		this.imagePath = properties.get("alternativeimage", String.class);
		this.title = properties.get("title", String.class);
		this.subtitle = properties.get("subtitle", String.class);
		this.caption = properties.get("caption", String.class);
		
		this.ctaText = properties.get("ctatext", String.class);
		this.ctaUrl = properties.get("ctaurl", String.class);
		this.applyLinkToTitle = properties.get("applyLinkToTitle",false);
		
		resourceResolver = request.getResourceResolver();
		Resource resource = resourceResolver.getResource(this.imagePath);
		
		this.imageAlt = IMAGE_ALT_DEFAULT;
		
		try {
			image = new Image(resource);
			this.imageAlt = image.getAlt();
		} catch(NullPointerException e) {
			LOG.error("Image not found in repository. Expected path=" + this.imagePath);
		}

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

	public String getImageAlt() {
		return imageAlt;
	}

	public void setImageAlt(String imageAlt) {
		this.imageAlt = imageAlt;
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
	
	public Boolean getapplyLinkToTitle() {
		return applyLinkToTitle;
	}

	public void setapplyLinkToTitle(Boolean applyLinkToTitle) {
		this.applyLinkToTitle = applyLinkToTitle;
	}
}