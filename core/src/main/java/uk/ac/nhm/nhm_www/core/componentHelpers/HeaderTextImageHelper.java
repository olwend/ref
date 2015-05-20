package uk.ac.nhm.nhm_www.core.componentHelpers;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;

import uk.ac.nhm.nhm_www.core.utils.LinkUtils;

import com.adobe.granite.xss.XSSAPI;
import com.day.cq.commons.ImageResource;

public class HeaderTextImageHelper {
	
	private Boolean activated;

	// Image Tab
	private String path;
	private String extension;
	private String suffix;
	private Boolean hasImage;
	
	// Advanced Tab
	private String alt;
	private String imageSize;
	private String imageLinkURL;
	private boolean imageLinkNewWindow;
	private String imagePosition;
	
	// Text Tab
	private String heading;
	private String linkURL;
	private boolean linkNewWindow;
	private Boolean hasCTA;
	private String backgroundColor;
	private Boolean isDarkGreyBackground;
	private String componentType;
	private String text;
	private Boolean addPadding;
	
	// CTA Tab
	private String ctaSectionOverride;
	private String ctaTitle;
	private String ctaLink;
	private String ctaIconClass;
	private boolean ctaLinkNewWindow;
	private boolean hasCTAIcon;	

	public HeaderTextImageHelper(ValueMap properties, Resource resource, HttpServletRequest request, XSSAPI xssAPI) {
		this.activated = false;
		
		// **********************************
		// ** Image & Advanced Tab Section **
		// **********************************
		this.isDarkGreyBackground = false;
		this.hasImage = false;
		
		this.addPadding = false;
		String fileReference = properties.get("fileReference", "");
		
		if (fileReference.length() != 0 || resource.getChild("file") != null) {
			String tempPath = request.getContextPath() + resource.getPath();
			ImageResource image = new ImageResource(resource);
			String tempAlt = xssAPI.encodeForHTMLAttr(properties.get("alt", image.getTitle()));
			// Handle extensions on both fileReference and file type images
			String tempExtension = "jpg";
			String tempSuffix = "";
			
			if (fileReference.length() != 0) {
				tempExtension = fileReference.substring(fileReference.lastIndexOf(".") + 1);
				tempSuffix = image.getSuffix();
				tempSuffix = tempSuffix.substring(0, tempSuffix.indexOf('.') + 1) + tempExtension;
			} else {
				Resource fileJcrContent = resource.getChild("file").getChild("jcr:content");
				if (fileJcrContent != null) {
					ValueMap fileProperties = fileJcrContent.adaptTo(ValueMap.class);
					String mimeType = fileProperties.get("jcr:mimeType", "jpg");
					tempExtension = mimeType.substring(mimeType.lastIndexOf("/") + 1);
				}
			}
			
			tempExtension = xssAPI.encodeForHTMLAttr(tempExtension);
			this.path= tempPath;
			this.extension = tempExtension;
			this.suffix = tempSuffix;
			this.alt = tempAlt;
			this.hasImage = true;
		}
		
		this.imageLinkURL = properties.get("image-path","");
		
		if(this.imageLinkURL != null && !this.imageLinkURL.equals("")) {
			this.imageLinkURL = LinkUtils.getFormattedLink(this.imageLinkURL);
		}
		
		if (properties.get("newwindow") != null) {
			this.imageLinkNewWindow = properties.get("newwindow",false);
		}
		this.addPadding = properties.get("addPadding",false);
		
		this.imageSize = properties.get("imageSize", "4");
		this.imagePosition = properties.get("imagePosition", "right");
		
		// **********************
		// ** Text Tab Section **
		// **********************
		this.hasCTA = false;
		this.heading = properties.get("text-heading", "");
		this.linkURL = properties.get("linkURL","");
		if(this.linkURL != null && !this.linkURL.equals("")) {
			this.linkURL = LinkUtils.getFormattedLink(this.linkURL);
		}
		
		if (properties.get("newwindowheading") != null) {
			this.linkNewWindow = properties.get("newwindowheading",false);
		}
		
		if (properties.get("darkgrey") != null) {
			this.isDarkGreyBackground = properties.get("darkgrey",false);
		}
		
		this.backgroundColor = setBackgroundColor();
		this.componentType = properties.get("component-type", "");
		this.text = properties.get("text", "");
		
		
		// *********************
		// ** CTA Tab Section **
		// *********************
		
		if(properties.get("addCallToAction") != null){
			this.hasCTA = properties.get("addCallToAction", false);
		}
		
		if(this.hasCTA){
			ctaSectionOverride = StringUtils.EMPTY;
			ctaTitle = StringUtils.EMPTY;
			ctaLink = StringUtils.EMPTY;
			ctaIconClass = StringUtils.EMPTY;
			ctaLinkNewWindow = false;
			hasCTAIcon = false;	
			
			if (properties.get("section-override") != null) {
				this.ctaSectionOverride = properties.get("section-override", String.class);
			}
			
			if (properties.get("cta-title") != null) {
				this.ctaTitle = properties.get("cta-title", String.class);
			}
			
			if (properties.get("cta-path") != null) {
				this.ctaLink = properties.get("cta-path", String.class);
			}
			
			if(!this.ctaTitle.equals("") && !this.ctaLink.equals("")) {
				this.ctaLink= LinkUtils.getFormattedLink(this.ctaLink);
			}
			
			if (properties.get("callToActionNewWindow") != null) {
				this.ctaLinkNewWindow = properties.get("cta-newwindow",false);
			}
			
			if(properties.get("cta-icon") != null) {
				this.hasCTAIcon = properties.get("cta-icon", false);
				this.ctaIconClass = properties.get("calltoaction-type", String.class);
			}
		}
		
		if(!this.heading.equals("") && !this.text.equals("")) {
			this.activated = true;
		}
	}
	
	
	/***************
	 ** Image Tab **
	 ***************/

	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public String getExtension() {
		return extension;
	}
	
	public void setExtension(String extension) {
		this.extension = extension;
	}
	
	public String getSuffix() {
		return suffix;
	}
	
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	
	public Boolean hasImage() {
		return hasImage;
	}
	
	public void setHasImage(Boolean hasImage) {
		this.hasImage = hasImage;
	}
	
	public Boolean isActivated() {
		return activated;
	}
	
	public void setActivated(Boolean activated) {
		this.activated = activated;
	}
	
	/****************
	 **Advanced Tab**
	 ****************/
	
	public String getAlt() {
		return alt;
	}
	
	public void setAlt(String alt) {
		this.alt = alt;
	}
	
	private String getTextPosition() {
		String ret = StringUtils.EMPTY;
		switch (this.imagePosition) {
		case "right":
			ret = "-pull";
			break;
		default:
			ret = "";
			break;
		}
		return ret;
	}
	
	private String getTextSize() {
		String ret = getImageSize();
		if (!this.hasImage) {
			ret = "12";
		} else {
			if (ret.equals("8")){
				ret = "4";
			} else {
				ret = "6";
			}
		}
		return ret;
	}

	public String getImageSize() {
		if (this.imagePosition.equals("top")){
			return "12";
		} else {
			return imageSize;			
		}
	}
	
	public void setImageSize(String imageSize) {
		this.imageSize = imageSize;
	}
	
	public String getImagePosition() {
		String ret = StringUtils.EMPTY;
		switch (imagePosition) {
		case "right":
			ret = "-push";
			break;
		default:
			ret = "";
			break;
		}
		return ret;
	}
	
	public void setImagePosition(String imagePosition) {
		this.imagePosition = imagePosition;
	}
	
	public String getImageLinkURL() {
		return imageLinkURL;
	}
	
	public void setImageLinkURL(String imageLinkURL) {
		this.imageLinkURL = imageLinkURL;
	}
	
	public boolean isImageLinkNewWindow() {
		return imageLinkNewWindow;
	}
	
	
	/**************
	 ** Text Tab **
	 **************/
	
	public String getHeading() {
		return heading;
	}
	
	public void setHeading(String heading) {
		this.heading = heading;
	}
	
	public String getLinkURL() {
		return linkURL;
	}
	
	public void setLinkURL(String linkURL) {
		this.linkURL = linkURL;
	}
	
	public boolean isLinkNewWindow() {
		return linkNewWindow;
	}
	
	public String getNewWindowHtml(boolean opensInNewWindow) {
		if (opensInNewWindow){	
			return " target=\"_blank\"";
		} else {
			return "";
		}
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public String getComponentType() {
		return componentType;
	}
	
	public void setComponentType(String componentType) {
		this.componentType = componentType;
	}
	
	public String setBackgroundColor() {
		if (this.isDarkGreyBackground){
			return "hti-box__dark-grey";
		} else {
			return "hti-box__light-grey";
		}
	}
	
	public Boolean hasCTA() {
		return hasCTA;
	}

	public void setHasCTA(Boolean hasCTA) {
		this.hasCTA = hasCTA;
	}
	
	public Boolean isDarkGreyBackground() {
		return isDarkGreyBackground;
	}
	
	public void setIsDarkGreyBackground(Boolean isDarkGreyBackground) {
		this.isDarkGreyBackground = isDarkGreyBackground;
	}
	
	public String getBackgroundColor() {
		return this.backgroundColor;
	}
	

	/*************
	 ** CTA Tab **
	 *************/
	
	public String getCTATitle() {
		return ctaTitle;
	}

	public void setCTATitle(String callToActionTitle) {
		this.ctaTitle = callToActionTitle;
	}

	public String getCTALink() {
		return ctaLink;
	}

	public void setCTALink(String callToActionLink) {
		this.ctaLink = callToActionLink;
	}

	public Boolean hasCTAIcon() {
		return this.hasCTAIcon;
	}
	
	public void setCTALinkNewWindow(boolean callToActionLinkNewWindow) {
		this.ctaLinkNewWindow = callToActionLinkNewWindow;
	}

	public String getCTALinkNewWindow() {
		if (this.ctaLinkNewWindow) {	
			return " target=\"_blank\"";
		} else {
			return "";
		}
	}

	public String getCTAIconClass() {
		return ctaIconClass;
	}

	public void setCTAIconClass(String iconClass) {
		this.ctaIconClass = iconClass;
	}

	public String getCTASectionOverride() {
		return ctaSectionOverride;
	}

	public Boolean getAddPadding() {
		return addPadding;
	}

	public void setAddPadding(Boolean addPadding) {
		this.addPadding = addPadding;
	}
	

	public void setCTASectionOverride(String sectionOverride) {
		this.ctaSectionOverride = sectionOverride;
	}
	
	
}
