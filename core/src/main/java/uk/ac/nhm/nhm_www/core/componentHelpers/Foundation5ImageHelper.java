package uk.ac.nhm.nhm_www.core.componentHelpers;

import javax.servlet.http.HttpServletRequest;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;

import com.adobe.granite.xss.XSSAPI;
import com.day.cq.commons.ImageResource;

import uk.ac.nhm.nhm_www.core.utils.LinkUtils;

public class Foundation5ImageHelper {
	private String path;
	private String origianlImagePath;
	private String extension;
	private String suffix;
	private String alt;
	private String caption;
	private String imageLinkURL;
	private boolean newwindow;
	private Boolean activated;
	

	public Foundation5ImageHelper(ValueMap properties, Resource resource, HttpServletRequest request, XSSAPI xssAPI) {
		
		this.activated = false;
		String fileReference = properties.get("fileReference", "");
		this.origianlImagePath = fileReference;
		if (fileReference.length() != 0 || resource.getChild("file") != null) {
			ImageResource image = new ImageResource(resource);
			String tempPath = request.getContextPath() + resource.getPath();
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
			this.caption = properties.get("caption","");
			this.imageLinkURL = properties.get("image-path","");
			if(this.imageLinkURL != null && !this.imageLinkURL.equals("")){
				this.imageLinkURL = LinkUtils.getFormattedLink(this.imageLinkURL);
			}
			if (properties.get("newwindow") != null) {
				this.newwindow = properties.get("newwindow",false);
			}
			this.path= tempPath;
			this.extension = tempExtension;
			this.suffix = tempSuffix;
			this.alt = tempAlt;
			this.activated = true;
		}
	}


	public String getPath() {
		return path;
	}


	public void setPath(String path) {
		this.path = path;
	}
	
	public String getOrigianlImagePath() {
		return origianlImagePath;
	}

	public void setOrigianlImagePath(String origianlImagePath) {
		this.origianlImagePath = origianlImagePath;
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


	public Boolean isActivated() {
		return activated;
	}


	public void setActivated(Boolean activated) {
		this.activated = activated;
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
	
	public String getNewWindowHtml() {
		if (this.newwindow)
		{	
			return " target=\"_blank\"";
		}
		else
		{
			return "";
		}
	}
	
		
}
