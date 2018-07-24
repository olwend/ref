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

import com.day.cq.dam.api.Asset;
import com.day.cq.wcm.foundation.Image;

import uk.ac.nhm.core.utils.LinkUtils;

@Model(adaptables = SlingHttpServletRequest.class)
public class NHMImage {

	private static final Logger LOG = LoggerFactory.getLogger(NHMImage.class);

	@Inject
	ValueMap properties;
	
	@Inject
	ResourceResolver resourceResolver;
	
	private String defaultImagePath;
	private String smallImagePath;
	private String mediumImagePath;
	private String largeImagePath;
	
	private String alt = null;
	private boolean addMarginBottom = false;
	private String caption = null;
	private String imageLinkURL = null;
	private boolean newWindow = false;
	
	@PostConstruct
	protected void init() {
		String fileReference = properties.get("fileReference", "");
		Resource imageResource = resourceResolver.getResource(fileReference);
		Image image = null;
		
		try {
			image = new Image(imageResource);
			Asset asset = imageResource.adaptTo(Asset.class);
			
			int imageWidth = getImageWidth(image.getPath());
			
			//Default
			if(imageWidth > 1920) {
				this.setDefaultImagePath(fileReference + "/_jcr_content/renditions/cq5dam.web.1920.1920.jpeg");
			} else {
				this.setDefaultImagePath(fileReference);
			}
			
			//Small
			if(imageWidth > 768) {
				this.setSmallImagePath(fileReference + "/_jcr_content/renditions/cq5dam.web.768.768.jpeg");
			} else {
				this.setSmallImagePath(fileReference);
			}
			
			//Medium
			if(imageWidth > 1024) {
				this.setMediumImagePath(fileReference + "/_jcr_content/renditions/cq5dam.web.1160.1160.jpeg");
			} else {
				this.setMediumImagePath(fileReference);
			}
			
			//Large
			this.setLargeImagePath(fileReference);
			
			this.setAlt(properties.get("alt", asset.getMetadataValue("dc:title")));
		} catch(NullPointerException e) {
			LOG.error("Image not found in repo. Expected path=" + fileReference);
		}
		
		this.setAddMarginBottom(properties.get("addMarginBottom", false));
		
		if(properties.get("caption") != null) {
			this.setCaption(properties.get("caption", String.class));
		}
		
		if(properties.get("imagelink") != null) {
			this.setImageLinkURL(LinkUtils.getFormattedLink(properties.get("imagelink", String.class)));
		}
		
		if(properties.get("newwindow") != null) {
			this.setNewWindow(properties.get("newwindow", false));
		}
	}

	private int getImageWidth(String damAssetPath) {
		Resource imageMedataData = this.resourceResolver.getResource(damAssetPath+"/jcr:content/metadata");
		ValueMap imageProperies = imageMedataData.getValueMap();
		String imageWidth = imageProperies.get("tiff:ImageWidth", "0");
		return Integer.parseInt(imageWidth);
	}
	
	public String getDefaultImagePath() {
		return defaultImagePath;
	}

	public void setDefaultImagePath(String defaultImagePath) {
		this.defaultImagePath = defaultImagePath;
	}

	public String getSmallImagePath() {
		return smallImagePath;
	}

	public void setSmallImagePath(String smallImagePath) {
		this.smallImagePath = smallImagePath;
	}

	public String getMediumImagePath() {
		return mediumImagePath;
	}

	public void setMediumImagePath(String mediumImagePath) {
		this.mediumImagePath = mediumImagePath;
	}

	public String getLargeImagePath() {
		return largeImagePath;
	}

	public void setLargeImagePath(String largeImagePath) {
		this.largeImagePath = largeImagePath;
	}

	public String getAlt() {
		return alt;
	}

	public void setAlt(String alt) {
		this.alt = alt;
	}

	public boolean getAddMarginBottom() {
		return addMarginBottom;
	}

	public void setAddMarginBottom(boolean addMarginBottom) {
		this.addMarginBottom = addMarginBottom;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getImageLinkURL() {
		return imageLinkURL;
	}

	public void setImageLinkURL(String imageLinkURL) {
		this.imageLinkURL = imageLinkURL;
	}

	public String getNewWindow() {
		if (newWindow) {	
			return "_blank";
		} else {
			return "";
		}
	}

	public void setNewWindow(boolean newWindow) {
		this.newWindow = newWindow;
	}
	
}
