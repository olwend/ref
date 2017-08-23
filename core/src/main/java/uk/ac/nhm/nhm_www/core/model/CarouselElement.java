package uk.ac.nhm.nhm_www.core.model;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.nhm.nhm_www.core.componentHelpers.InformationalPageHeadHelper;
import uk.ac.nhm.nhm_www.core.utils.LinkUtils;
import uk.ac.nhm.nhm_www.core.utils.PageUtils;

import com.adobe.granite.ocs.api.Deployment.Properties;
import com.day.cq.commons.ImageHelper;
import com.day.cq.commons.ImageResource;
import com.day.cq.dam.api.Asset;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.components.DropTarget;
import com.day.cq.wcm.foundation.Image;

public class CarouselElement {

	private String filename;
	private String mobileFilename;
	private String hyperlink;
	private String heading;
	private String caption;
	private Boolean isVideo;
	private Boolean isContentSlide;
	private String description;
	private String type;
	private String alt;
	
	
	
	protected static final Logger logger = LoggerFactory.getLogger(CarouselElement.class);

	public CarouselElement(JSONObject item, PageManager pageManager, ResourceResolver resourceResolver) throws JSONException {
		this.type = item.getString("type");
		this.filename = item.getString("item");
		if(item.has("itemMobile")){
			this.mobileFilename = item.getString("itemMobile");
		} else {
			this.mobileFilename = "";
		}
		this.isVideo=false;
		this.isContentSlide=false;
		this.hyperlink =  item.getString("itemURL");
		if(this.hyperlink != null && !this.hyperlink.equals("")){
			this.hyperlink = LinkUtils.getFormattedLink(this.hyperlink);
		}
		this.heading = item.getString("itemHeading");
		this.caption = item.getString("itemCaption");
		
		if(this.type.equals("video")) {
			this.isVideo = true;
		}
		if(!this.type.equals("static") && !this.type.equals("video") && pageManager.getPage(this.filename) !=null) { 
			Page page = pageManager.getPage(this.filename);
			this.isContentSlide = true;
			this.caption = page.getTitle();
			this.description = page.getDescription();
			
		}
		
	    
		this.alt = "";
		try {
			if(this.filename.endsWith(".jpg") || this.filename.endsWith(".png") || this.filename.endsWith(".gif")) {
				Resource rs = resourceResolver.getResource(this.filename);
				Asset asset = rs.adaptTo(Asset.class);
				String assetTitle = asset.getMetadataValue("dc:title");
				if(assetTitle != null && !assetTitle.equals("")) {
					this.alt = assetTitle;
				} else if(this.heading != null || !this.heading.equals("")){
					this.alt = this.heading;
				} else if(this.caption != null || !this.caption.equals("")){
					this.alt = this.caption;
				}	
			}
		} catch(Exception e) {
			logger.error("An exception was thrown", e);
		}
				
	}

	public void setFilename(String string) {
		this.filename = string;
	}
	
	public String getFilename()
	{
		return this.filename;
	}
	
	public String getMobileFilename() {
		return mobileFilename;
	}

	public void setMobileFilename(String mobileFilename) {
		this.mobileFilename = mobileFilename;
	}

	public void setHyperlink(String string) {
		this.hyperlink = string;
	}
	
	public String getHyperlink()
	{
		return this.hyperlink;
	}

	public String getHeading() {
		return heading;
	}

	public void setHeading(String heading) {
		this.heading = heading;
	}

	public void setCaption(String string) {
		this.caption = string;
	}
	
	public String getCaption()
	{
		return this.caption;
	}

	public Boolean getIsVideo() {
		return isVideo;
	}

	public void setIsVideo(Boolean isVideo) {
		this.isVideo = isVideo;
	}

	public Boolean getIsContentSlide() {
		return isContentSlide;
	}
	
	
	public String getDescription()
	{
		return this.description;
	}

	public void setIsContentSlide(Boolean isContentSlide) {
		this.isContentSlide = isContentSlide;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAlt() {
		return alt;
	}

	public void setAlt(String alt) {
		this.alt = alt;
	}

}
