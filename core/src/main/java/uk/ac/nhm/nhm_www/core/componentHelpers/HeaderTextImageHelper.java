package uk.ac.nhm.nhm_www.core.componentHelpers;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;

import uk.ac.nhm.nhm_www.core.utils.LinkUtils;

import com.adobe.granite.xss.XSSAPI;
import com.day.cq.commons.ImageResource;

public class HeaderTextImageHelper {
	private Boolean isDarkGreyBackground;
	private String path;
	private String extension;
	private String suffix;
	private String alt;
	private String linkURL;
	private boolean linkNewWindow;
	private String text;
	private String heading;
	private String sectionOverride;
	private String callToActionTitle;
	private String callToActionLink;
	private boolean callToActionLinkNewWindow;
	private String imageSize;
	private String imagePosition;
	private String componentType;
	private String imageLinkURL;
	private boolean imageLinkNewWindow;
	private Boolean hasImage;
	private Boolean hasCTA;
	private Boolean activated;
	private String backgroundColor;
	private Object textPosition;
	private String iconClass;
	private Boolean hasCTAIcon;
	

	public HeaderTextImageHelper(ValueMap properties, Resource resource, HttpServletRequest request, XSSAPI xssAPI) {
		this.textPosition = "left";
		this.isDarkGreyBackground = false;
		this.hasImage = false;
		this.activated = false;
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
		if(this.imageLinkURL != null && !this.imageLinkURL.equals("")){
			this.imageLinkURL = LinkUtils.getFormattedLink(this.imageLinkURL);
		}
		if (properties.get("newwindow") != null) {
			this.imageLinkNewWindow = properties.get("newwindow",false);
		}
		this.heading = properties.get("text-heading", "");
		this.linkURL = properties.get("linkURL","");
		if(this.linkURL != null && !this.linkURL.equals("")){
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
		this.imageSize = properties.get("imageSize", "4");
		this.imagePosition = properties.get("imagePosition", "right");
		this.textPosition = setTextPosition(imagePosition);
		
		this.hasCTA = properties.get("addCallToAction", false);
		if(this.hasCTA){
			// CTA Title, Link & New Window
			this.callToActionTitle = properties.get("cta-title", "");
			this.callToActionLink = properties.get("cta-path", "");
			if(!this.callToActionTitle.equals("") && !this.callToActionLink.equals("")) {
				this.callToActionLink= LinkUtils.getFormattedLink(this.callToActionLink);
			}
			if (properties.get("callToActionNewWindow") != null) {
				this.callToActionLinkNewWindow = properties.get("cta-newwindow",false);
			}
			// Do we want the icon?
			this.hasCTAIcon = properties.get("cta-icon", false);
			
			// Icon Type
			this.iconClass = properties.get("calltoaction-type", "");
			
			// Section Override
			this.sectionOverride = properties.get("section-override", "");
			
		}
		
		if(!this.heading.equals("") && !this.text.equals("")) {
			this.activated = true;
		}

	}
	
	public Boolean hasCTAIcon() {
		return this.hasCTAIcon;
	}

	public void setCallToActionLinkNewWindow(boolean callToActionLinkNewWindow) {
		this.callToActionLinkNewWindow = callToActionLinkNewWindow;
	}
	
	public String getCallToActionLinkNewWindow() {
		if (this.callToActionLinkNewWindow) {	
			return " target=\"_blank\"";
		} else {
			return "";
		}
	}

	public Object getTextPosition() {
		return textPosition;
	}

	private String setTextPosition(String imagePosition) {
		String ret = StringUtils.EMPTY;
		switch (imagePosition) {
		case "right":
			ret = "left";
			break;
		case "left":
			ret = "right";
			break;
		case "top":
			ret = "bottom";
			break;
		default:
			ret = "left";
			break;
		}
		return ret;
	}

	public String getIconClass() {
		return iconClass;
	}

	public void setIconClass(String iconClass) {
		this.iconClass = iconClass;
	}

	public String getSectionOverride() {
		return sectionOverride;
	}

	public void setSectionOverride(String sectionOverride) {
		this.sectionOverride = sectionOverride;
	}

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

	public String getAlt() {
		return alt;
	}

	public void setAlt(String alt) {
		this.alt = alt;
	}

	public Boolean getHasImage() {
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

	public String getLinkURL() {
		return linkURL;
	}

	public void setLinkURL(String linkURL) {
		this.linkURL = linkURL;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getHeading() {
		return heading;
	}

	public void setHeading(String heading) {
		this.heading = heading;
	}

	public Boolean isDarkGreyBackground() {
		return isDarkGreyBackground;
	}
	
	public void setIsDarkGreyBackground(Boolean isDarkGreyBackground) {
		this.isDarkGreyBackground = isDarkGreyBackground;
	}
	
	public String setBackgroundColor(){
		if (this.isDarkGreyBackground){
			return "DarkGrey";
		} else {
			return "GreyBox";
		}
	}
	
	public String getBackgroundColor(){
		return this.backgroundColor;
	}

	public String getCallToActionTitle() {
		return callToActionTitle;
	}

	public void setCallToActionTitle(String callToActionTitle) {
		this.callToActionTitle = callToActionTitle;
	}

	public String getCallToActionLink() {
		return callToActionLink;
	}

	public void setCallToActionLink(String callToActionLink) {
		this.callToActionLink = callToActionLink;
	}

	public Boolean hasCTA() {
		return hasCTA;
	}

	public void setHasCTA(Boolean hasCTA) {
		this.hasCTA = hasCTA;
	}

	public String getImageSize() {
		return imageSize;
	}

	public void setImageSize(String imageSize) {
		this.imageSize = imageSize;
	}

	public String getImagePosition() {
		return imagePosition;
	}

	public void setImagePosition(String imagePosition) {
		this.imagePosition = imagePosition;
	}

	public String getComponentType() {
		return componentType;
	}

	public void setComponentType(String componentType) {
		this.componentType = componentType;
	}

	public String getImageLinkURL() {
		return imageLinkURL;
	}

	public void setImageLinkURL(String imageLinkURL) {
		this.imageLinkURL = imageLinkURL;
	}

	public String getNewWindowHtml(boolean opensInNewWindow) {
		if (opensInNewWindow)
		{	
			return " target=\"_blank\"";
		}
		else
		{
			return "";
		}
	}

	public boolean isLinkNewWindow() {
		return linkNewWindow;
	}

	public boolean isImageLinkNewWindow() {
		return imageLinkNewWindow;
	}
}
