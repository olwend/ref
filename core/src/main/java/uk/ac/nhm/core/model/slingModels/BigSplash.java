package uk.ac.nhm.core.model.slingModels;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.commons.ImageResource;
import com.day.cq.wcm.foundation.Image;
@Model(adaptables = SlingHttpServletRequest.class)
public class BigSplash {
	
	private final static Logger LOG = LoggerFactory.getLogger(BigSplash.class);

	@Inject
	ValueMap properties;
	
	@Inject
	SlingHttpServletRequest request;
	
	@Inject
	ResourceResolver resourceResolver;
	
	private Boolean applyLinkToTitle = null;
	private String caption = null;
	private String ctaText = null;
	private String ctaUrl = null;
	private String imageAlt = null;
	private String imagePath = null;
	private String subtitle = null;
	private String title = null;
	private String videoId = null;
	
	@PostConstruct
	protected void init() {
		setVariables(properties);
	}

	private void setVariables(ValueMap properties) {
		this.applyLinkToTitle = properties.get("applyLinkToTitle",false);
		this.caption = properties.get("caption", String.class);
		this.ctaText = properties.get("ctatext", String.class);
		this.ctaUrl = properties.get("ctaurl", String.class);
		this.imagePath = properties.get("alternativeimage", String.class);
		this.subtitle = properties.get("subtitle", String.class);
		this.title = properties.get("title", String.class);
		this.videoId = properties.get("youtube", String.class);
		
		if(properties.containsKey("imagealttext")) {
			this.imageAlt = properties.get("imagealttext", String.class);
		} else {
			Resource resource = resourceResolver.getResource(this.imagePath);
			this.imageAlt = getImageTitle(resource);
		}
	}
	
	private String getImageTitle(Resource resource) {
		try {
			ImageResource image = new Image(resource);
			String imageTitle = image.getTitle();
			return imageTitle;
		} catch(NullPointerException e) {
			LOG.error("Image not found in repository. Expected path=" + this.imagePath);
			return null;
		}
	}
	
	public Boolean getApplyLinkToTitle() {
		return applyLinkToTitle;
	}

	public void setApplyLinkToTitle(Boolean applyLinkToTitle) {
		this.applyLinkToTitle = applyLinkToTitle;
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

	public String getImageAlt() {
		return imageAlt;
	}

	public void setImageAlt(String imageAlt) {
		this.imageAlt = imageAlt;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}
	
}
