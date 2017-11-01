package uk.ac.nhm.core.componentHelpers;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ValueMap;

import uk.ac.nhm.core.services.PathToResourceService;
import uk.ac.nhm.core.utils.LinkUtils;

import com.day.cq.dam.api.Asset;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.components.DropTarget;
import com.day.cq.wcm.foundation.Image;
import com.day.cq.wcm.foundation.Placeholder;

public class GrandSummaryHelper {
	
	private boolean activated;
	private boolean componentInitialised;
	
	// Image & Advanced
	private Image image;
	private Image mobileimage;
	
	private Asset fileImage;
	
	private String fileReference;
	private String mobileFileReference;
	private String alt;
	
	private String id;
	private String divId;
	private String title;
	private String link;
	private Boolean newWindow;
	private String description;
	private Boolean isExhibition;
	
	// Exhibition & CTA Tab
	private String ctaTitle;
	private String ctaLink;
	private Boolean ctaLinkNewWindow;
	private Boolean hasCTAIcon;
	private String ctaIconClass;
	private String ticketPrice;
	private String location;
	private String date;

	PathToResourceService ptrs = new PathToResourceService();
	
	public GrandSummaryHelper(SlingHttpServletRequest request, Page page, ValueMap properties, Image image) throws LoginException {
		this.activated = false;
		this.isExhibition = false;
		
		// **********************
		// ** Normal Component **
		// **********************
		
		this.title = properties.get("title", String.class);
		this.description = properties.get("description", String.class);
		this.link = properties.get("path", String.class);
		
		if(this.link !=null && !this.link.equals("")) {
			this.link= LinkUtils.getFormattedLink(this.link);
		}

		if (properties.get("newwindow") != null) {
			this.newWindow = properties.get("newwindow",false);
		}

		if(properties.get("fileReference")!=null) this.fileReference = properties.get("fileReference", String.class);
		if(properties.get("mobileFileReference")!=null) this.mobileFileReference = properties.get("mobileFileReference", String.class);
		
		//Get asset from @fileReference to get title of image
		this.fileImage = ptrs.getAsset(this.fileReference, request);
		if(this.fileImage.getMetadata().containsKey("dc:title")) {
			this.alt = this.fileImage.getMetadataValue("dc:title").toString();
		}
		else {
			this.alt = this.fileImage.getName();
		}
		
		this.mobileimage = new Image(request.getResource());
		this.mobileimage.setIsInUITouchMode(Placeholder.isAuthoringUIModeTouch(request));
		this.image = image;
		this.image.setIsInUITouchMode(Placeholder.isAuthoringUIModeTouch(request));
		// drop target css class = dd prefix + name of the drop target in the
		// edit config
		this.image.addCssClass(DropTarget.CSS_CLASS_PREFIX + "image");
		this.image.addCssClass("staticHero");
		this.image.setSelector(".img"); // use image script
		
		if (this.getAltText() != null) {
			this.image.setAlt(this.getAltText());
			this.image.setTitle(this.getAltText());
		}

		if (request.getResource().getPath() != null) {
			divId = request.getResource().getPath();
		}
		
		
		// **************************
		// ** Exhibition Component **
		// **************************
		
		if (properties.get("isExhibition") != null) {
			this.isExhibition = properties.get("isExhibition", false);
		}
		
		if (this.isExhibition) {
			this.ticketPrice = StringUtils.EMPTY;
			this.location = StringUtils.EMPTY;
			this.date = StringUtils.EMPTY;
			
			if (properties.get("ticketPrice") != null) {
				this.ticketPrice = properties.get("ticketPrice", String.class);
			}
			
			if (properties.get("location") != null) {
				this.location = properties.get("location", String.class);
			}
			
			if (properties.get("date") != null) {
				this.date = properties.get("date", String.class);
			}
			
			// ****************
			// ** CTA Button **
			// ****************
			ctaTitle = StringUtils.EMPTY;
			ctaLink = StringUtils.EMPTY;
			ctaIconClass = StringUtils.EMPTY;
			ctaLinkNewWindow = false;
			hasCTAIcon = false;

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
	}

	public GrandSummaryHelper(SlingHttpServletRequest request, Page page, ValueMap properties) throws LoginException {
		// Inject image class from Resource
		this(request, page, properties, new Image(request.getResource()));
	}
	
	/*************************
	 * Image & Advanced Tabs *
	 *************************/

	public Image getImage() {
		this.image.addCssClass(DropTarget.CSS_CLASS_PREFIX + "image");
		this.image.addCssClass("desktop tablet");
		if (this.getAltText() != null) {
			this.image.setAlt(this.getAltText());
			this.image.setTitle(this.getAltText());
		}
		return this.image;
	}

	public Image getMobileImage() {
		// drop target css class = dd prefix + name of the drop target in the
		// edit config
		this.mobileimage.addCssClass(DropTarget.CSS_CLASS_PREFIX + "image");
		this.mobileimage.addCssClass("mobile");
		this.mobileimage.setSelector(".img"); // use image script
		if (this.getAltText() != null) {
			this.mobileimage.setAlt(this.getAltText());
			this.mobileimage.setTitle(this.getAltText());
		}
		return this.mobileimage;
	}

	public boolean isActivated() {
		return activated;
	}

	public void setImage(Image image) {
		this.image = image;
	}
	
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public String getAltText() {
		return this.title;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Boolean getNewWindow() {
		return newWindow;
	}

	public void setNewWindow(Boolean newWindow) {
		this.newWindow = newWindow;
	}

	public boolean isComponentInitialised() {
		return componentInitialised;
	}

	public String getDivId() {
		return divId;
	}

	public void setDivId(String divId) {
		this.divId = divId;
	}

	public Boolean isExhibition() {
		return this.isExhibition;
	}
	
	
	/***************************
	 ** Exhibition & CTA Tabs **
	 ***************************/
	
	public String getTicketPrice() {
		return ticketPrice;
	}

	public void setTicketPrice(String ticketPrice) {
		this.ticketPrice = ticketPrice;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getCTATitle() {
		return ctaTitle;
	}

	public void setCTATitle(String ctaTitle) {
		this.ctaTitle = ctaTitle;
	}

	public String getCTALink() {
		return ctaLink;
	}

	public void setCTALink(String ctaLink) {
		this.ctaLink = ctaLink;
	}

	public Boolean getCTALinkNewWindow() {
		return ctaLinkNewWindow;
	}

	public void setCTALinkNewWindow(Boolean ctaLinkNewWindow) {
		this.ctaLinkNewWindow = ctaLinkNewWindow;
	}

	public Boolean hasCTAIcon() {
		return hasCTAIcon;
	}

	public void setHasCTAIcon(Boolean hasCTAIcon) {
		this.hasCTAIcon = hasCTAIcon;
	}

	public String getCTAIconClass() {
		return ctaIconClass;
	}

	public void setCTAIconClass(String ctaIconClass) {
		this.ctaIconClass = ctaIconClass;
	}

	public String getFileReference() {
		return fileReference;
	}

	public void setFileReference(String fileReference) {
		this.fileReference = fileReference;
	}

	public String getMobileFileReference() {
		return mobileFileReference;
	}

	public void setMobileFileReference(String mobileFileReference) {
		this.mobileFileReference = mobileFileReference;
	}

	public String getAlt() {
		return alt;
	}

	public void setAlt(String alt) {
		this.alt = alt;
	}

	public Asset getFileImage() {
		return fileImage;
	}

	public void setFileImage(Asset fileImage) {
		this.fileImage = fileImage;
	}

}
