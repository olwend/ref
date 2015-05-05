package uk.ac.nhm.nhm_www.core.componentHelpers;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ValueMap;

import uk.ac.nhm.nhm_www.core.utils.LinkUtils;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.components.DropTarget;
import com.day.cq.wcm.foundation.Image;
import com.day.cq.wcm.foundation.Placeholder;

public class GrandSummaryHelper {

	private Image image;
	private String id;
	private String divId;
	private String title;
	private String summary;
	private boolean componentInitialised;
	private Image mobileimage;
	private Object componentType;
	private Boolean hasCTA;
	private String callToActionTitle;
	private String callToActionLink;
	private Boolean callToActionLinkNewWindow;
	private Boolean hasCTAIcon;
	private String iconClass;
	private String sectionOverride;
	private boolean activated;

	public GrandSummaryHelper(SlingHttpServletRequest request, Page page, ValueMap properties, Image image) {
		// Title, Link and New Window
		this.title = properties.get("title", String.class);
		
		// Summary
		this.summary = properties.get("summary", String.class);

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
		
		if(this.title!=null && this.summary!=null){ 
			this.activated = true;
		}
	}

	public GrandSummaryHelper(SlingHttpServletRequest request, Page page, ValueMap properties) {
		// Inject image class from Resource
		this(request, page, properties, new Image(request.getResource()));
	}

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
	
	public String getSummary() {
		if (this.summary != null && this.summary.trim().length() > 0) {
			return this.summary;
		} else {
			return null;
		}
	}

	public void setSummary(String summary) {
		this.summary = summary;
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

	public String getIconClass() {
		return iconClass;
	}

	public void setIconClass(String iconClass) {
		this.iconClass = iconClass;
	}

	public Boolean hasCTA() {
		return hasCTA;
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

	public Boolean getCallToActionLinkNewWindow() {
		return callToActionLinkNewWindow;
	}

	public void setCallToActionLinkNewWindow(Boolean callToActionLinkNewWindow) {
		this.callToActionLinkNewWindow = callToActionLinkNewWindow;
	}

	public Boolean hasCTAIcon() {
		return hasCTAIcon;
	}

	public void setCTAIcon(Boolean hasCTAIcon) {
		this.hasCTAIcon = hasCTAIcon;
	}

	public String getSectionOverride() {
		return sectionOverride;
	}

	public void setSectionOverride(String sectionOverride) {
		this.sectionOverride = sectionOverride;
	}

}
