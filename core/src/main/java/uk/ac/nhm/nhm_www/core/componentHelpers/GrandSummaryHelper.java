package uk.ac.nhm.nhm_www.core.componentHelpers;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ValueMap;

import uk.ac.nhm.nhm_www.core.utils.LinkUtils;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.components.DropTarget;
import com.day.cq.wcm.foundation.Image;
import com.day.cq.wcm.foundation.Placeholder;

public class GrandSummaryHelper {
	
	private boolean activated;
	private boolean componentInitialised;
	
	// Image & Advanced
	private Image image;
	private String id;
	private String divId;
	private String title;
	private String description;
	private Image mobileimage;
	private Object componentType;
	private Boolean hasCTA;
	
	// CTA Tab
	private String ctaTitle;
	private String ctaLink;
	private Boolean ctaLinkNewWindow;
	private Boolean hasCTAIcon;
	private String ctaIconClass;
	private String ctaSectionOverride;

	public GrandSummaryHelper(SlingHttpServletRequest request, Page page, ValueMap properties, Image image) {
		this.activated = false;
		this.hasCTA = false;
		
		// Title, Link and New Window
		this.title = properties.get("title", String.class);
		
		// Description
		this.description = properties.get("description", String.class);

		// Component Type
		this.componentType = properties.get("component-type", String.class);

		// Image Handling
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
		
		// Call to Action
		if (properties.get("addCallToAction") != null) {
			this.hasCTA = properties.get("addCallToAction",false);
		}
		
		if(this.hasCTA){
			ctaTitle = StringUtils.EMPTY;
			ctaLink = StringUtils.EMPTY;
			ctaIconClass = StringUtils.EMPTY;
			ctaSectionOverride = StringUtils.EMPTY;
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
			
			if (properties.get("section-override") != null) {
				this.ctaSectionOverride = properties.get("section-override", String.class);
			}
		}
	}

	public GrandSummaryHelper(SlingHttpServletRequest request, Page page, ValueMap properties) {
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

	public Object getComponentType() {
		return componentType;
	}

	public void setComponentType(Object componentType) {
		this.componentType = componentType;
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

	public boolean isComponentInitialised() {
		return componentInitialised;
	}

	public String getDivId() {
		return divId;
	}

	public void setDivId(String divId) {
		this.divId = divId;
	}

	public Boolean hasCTA() {
		return hasCTA;
	}
	
	/************
	 * CTA Tabs *
	 ************/
	
	public String getCTAIconClass() {
		return ctaIconClass;
	}

	public void setCTAIconClass(String iconClass) {
		this.ctaIconClass = iconClass;
	}

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

	public Boolean getCTALinkNewWindow() {
		return ctaLinkNewWindow;
	}

	public void setCTALinkNewWindow(Boolean callToActionLinkNewWindow) {
		this.ctaLinkNewWindow = callToActionLinkNewWindow;
	}

	public Boolean hasCTAIcon() {
		return hasCTAIcon;
	}

	public void setCTAIcon(Boolean hasCTAIcon) {
		this.hasCTAIcon = hasCTAIcon;
	}

	public String getCTASectionOverride() {
		return ctaSectionOverride;
	}

	public void setCTASectionOverride(String sectionOverride) {
		this.ctaSectionOverride = sectionOverride;
	}

}
