package uk.ac.nhm.core.componentHelpers;

import org.apache.sling.api.resource.ValueMap;

//import uk.ac.nhm.core.componentHelpers.CTAButtonHelper.Icon;
import uk.ac.nhm.core.model.SVGImage;

public class EventSummaryHelper extends HelperBase {

	private enum Icon {DATES,TIMES,AFTERHOURS,TICKETS,TIMEDENTRY,AUDIO,PHOTO,CARD,AUTHOR,BRANDING,GALLERY,LOCATION,NOFLASHPHOTOGRAPHY,NOPHOTOGRAPHY,SUBSCRIPTION}

	private Icon enumIcon;
	private ValueMap properties;
	private String iconClass;
	private String text;
	private String svgSection;
	private Boolean isInitialised;
	
	public EventSummaryHelper(ValueMap properties, String section) {
		this.properties = properties;
		this.svgSection = section.toLowerCase().trim();
		init();
	}

	private void init() {
		if(this.properties.get("text", String.class) != null) {
			this.text = this.properties.get("text", String.class);
			this.isInitialised = true;
		} else {
			this.isInitialised = false;
		}
		
		//Set Calendar as default
		this.iconClass = this.properties.get("icon",String.class) == null ? "dates" : this.properties.get("icon", String.class);

		this.setSvgColour();
		this.setEnumIcon();
	}
	
	private void setEnumIcon() {
		if (this.iconClass.equals("dates")) {this.enumIcon = Icon.DATES;}
		else if (this.iconClass.equals("times")) {this.enumIcon = Icon.TIMES;}
		else if (this.iconClass.equals("after-hours")) {this.enumIcon = Icon.AFTERHOURS;} 
		else if (this.iconClass.equals("tickets")) {this.enumIcon = Icon.TICKETS;} 
		else if (this.iconClass.equals("timed-entry")) {this.enumIcon = Icon.TIMEDENTRY;} 
		else if (this.iconClass.equals("audio")) {this.enumIcon = Icon.AUDIO;} 
		else if (this.iconClass.equals("photo")) {this.enumIcon = Icon.NOPHOTOGRAPHY;}
		else if (this.iconClass.equals("membership")) {this.enumIcon = Icon.CARD;} 
		else if (this.iconClass.equals("joinnow")) {this.enumIcon = Icon.CARD;}
		else if (this.iconClass.equals("author")) {this.enumIcon = Icon.AUTHOR;}
		else if (this.iconClass.equals("branding")) {this.enumIcon = Icon.BRANDING;}
		else if (this.iconClass.equals("gallery")) {this.enumIcon = Icon.GALLERY;}
		else if (this.iconClass.equals("location")) {this.enumIcon = Icon.LOCATION;}
		else if (this.iconClass.equals("noflashphotography")) {this.enumIcon = Icon.NOFLASHPHOTOGRAPHY;}
		else if (this.iconClass.equals("nophotography")) {this.enumIcon = Icon.NOPHOTOGRAPHY;}
		else if (this.iconClass.equals("subscription")) {this.enumIcon = Icon.SUBSCRIPTION;}
	}

	public String getIconClass() {
		return this.iconClass;
	}

	public String getText() {
		return this.text;
	}

	private enum Sections { HOME, VISIT, DISCOVER, TAKEPART, SUPPORTUS, SHOP, SCHOOLS, OURSCIENCE, ABOUTUS, NEWSPRESS,WORKINGWITHUS, BUSINESSSERVICES, CONTACTUS, GENERAL}
	private Sections section;

	public void setSvgColour() {
		if (this.svgSection.equals("home")) {this.section = Sections.HOME;}
		else if (this.svgSection.equals("visit")) {this.section = Sections.VISIT;}
		else if (this.svgSection.equals("discover")) {this.section = Sections.DISCOVER;}
		else if (this.svgSection.equals("take-part")) {this.section = Sections.TAKEPART;}
		else if (this.svgSection.equals("support-us")) {this.section = Sections.SUPPORTUS;}
		else if (this.svgSection.equals("shop")) {this.section = Sections.SHOP;}
		else if (this.svgSection.equals("schools")) {this.section = Sections.SCHOOLS;}
		else if (this.svgSection.equals("our-science")) {this.section = Sections.OURSCIENCE;}
		else if (this.svgSection.equals("about-us")) {this.section = Sections.ABOUTUS;}
		else if (this.svgSection.equals("news-press")) {this.section = Sections.NEWSPRESS;}
		else if (this.svgSection.equals("working-with-us")) {this.section = Sections.WORKINGWITHUS;}
		else if (this.svgSection.equals("business-services")) {this.section = Sections.BUSINESSSERVICES;}
		else if (this.svgSection.equals("contact-us")) {this.section = Sections.CONTACTUS;}
		else this.section = Sections.GENERAL;
	}

	public String getSvgColour() {
		switch (this.section)  {
			case HOME:  return "#565656";
			case VISIT:  return "#A1164E";
			case DISCOVER:  return "#808080";
			case TAKEPART:  return "#BF491F";
			case SUPPORTUS:  return "#7F206E";
			case SHOP:  return "#808080";
			case SCHOOLS:  return "#0983BB";
			case OURSCIENCE:  return "#357900";
			case ABOUTUS:  return "#0B5C7B";
			case NEWSPRESS:  return "#107B5E";
			case WORKINGWITHUS:  return "#956624";
			case BUSINESSSERVICES:  return "#253480";
			case CONTACTUS:  return "#808080";
			default: return "#000";            
		}
	}

	public String getSvgFallback() {
		switch (this.enumIcon) {
			case DATES:  return "icon_l_feature_calendar";
			case TIMES:  return "icon_l_feature_clock";
			case AFTERHOURS:  return "icon_l_feature_sunset";
			case TICKETS:  return "icon_l_feature_ticket";
			case TIMEDENTRY:  return "icon_l_feature_alarm_clock";
			case AUDIO: return "icon_l_feature_audioguide";
			case PHOTO:  return "icon_l_feature_nophotography";
			case CARD:  return "icon_l_feature_membership";
			case AUTHOR: return "icon_l_feature_auther";
			case BRANDING: return "icon_l_branding_n";
			case GALLERY: return "icon_l_media_gallery";
			case LOCATION: return "icon_l_feature_location";
			case NOFLASHPHOTOGRAPHY: return "icon_l_feature_noflashphotography";
			case NOPHOTOGRAPHY: return "icon_l_feature_nophotography";
			case SUBSCRIPTION: return "icon_l_media_subscription";
			default: return "";            
		}
	}

	public String getSvgAlt() {
		switch (this.enumIcon) {
			case DATES:  return "Calendar";
			case TIMES:  return "Clock";
			case AFTERHOURS:  return "After Hours";
			case TICKETS:  return "Close";
			case TIMEDENTRY:  return "Alarm Clock";
			case AUDIO: return "Headphones";
			case PHOTO:  return "No Photography";
			case CARD:  return "Membership Card";
			case AUTHOR: return "Author";
			case BRANDING: return "Branding";
			case GALLERY: return "Gallery";
			case LOCATION: return "Location";
			case NOFLASHPHOTOGRAPHY: return "No Flash photography";
			case NOPHOTOGRAPHY: return "No Photography";
			case SUBSCRIPTION: return "Subscription";
			default: return "";            
		}
	}

	public String getSvgTitle() {
		switch (this.enumIcon) {
			case DATES:  return "icon__sidebar_calendar";
			case TIMES:  return "icon__sidebar_clock";
			case AFTERHOURS:  return "icon__sidebar_sunset";
			case TICKETS:  return "icon__sidebar_ticket";
			case TIMEDENTRY:  return "icon__sidebar_alarm_clock";
			case AUDIO: return "icon__sidebar_headphones";
			case PHOTO:  return "icon__sidebar_nophotography";
			case CARD:  return "icon__sidebar_membership";
			case AUTHOR: return "icon__sidebar_auther";
			case BRANDING: return "icon__sidebar_branding";
			case GALLERY: return "icon__sidebar_gallery";
			case LOCATION: return "icon__sidebar_location";
			case NOFLASHPHOTOGRAPHY: return "icon__sidebar_noflashphotography";
			case NOPHOTOGRAPHY: return "icon__sidebar_nophotography";
			case SUBSCRIPTION: return "icon__sidebar_subscription";
			default: return "";             
		}
	}

	public String getSvg() {
		switch (this.enumIcon) {
			case DATES:  return "icon_l_feature_calendar.svg";
			case TIMES:  return "icon_l_feature_clock.svg";
			case AFTERHOURS:  return "icon_l_feature_sunset.svg";
			case TICKETS:  return "icon_l_feature_ticket.svg";
			case TIMEDENTRY:  return "icon_l_feature_alarm_clock.svg";
			case AUDIO: return "icon_l_feature_audioguide.svg";
			case PHOTO:  return "icon_l_feature_nophotography.svg";
			case CARD:  return "icon_l_feature_membership.svg";
			case AUTHOR: return "icon_l_feature_auther.svg";
			case BRANDING: return "icon_l_branding_n.svg";
			case GALLERY: return "icon_l_media_gallery.svg";
			case LOCATION: return "icon_l_feature_location.svg";
			case NOFLASHPHOTOGRAPHY: return "icon_l_feature_noflashphotography.svg";
			case NOPHOTOGRAPHY: return "icon_l_feature_nophotography.svg";
			case SUBSCRIPTION: return "icon_l_media_subscription.svg";
			default: return "";            
		} 
	}

	public String getStrokeWidth() {
		switch (this.enumIcon) {
			case TICKETS:  return "3";	
			case TIMES:  return "3";
			case LOCATION: return "3";
			default: return "2";
		}
	}

	public SVGImage getSVGImage() {
		SVGImage svgImage = new SVGImage();
		svgImage.setCssClass("ico svg-ico");
		svgImage.setSvgSrc(this.getSvg());
		svgImage.setTitle(this.getSvgTitle());
		svgImage.setAltText(this.getSvgAlt());
		svgImage.setStrokeWidth(this.getStrokeWidth());
		svgImage.setFallback(this.getSvgFallback() + "-" + svgSection +".png");
		svgImage.setBaseColour(this.getSvgColour());
		svgImage.setHoverColour(this.getSvgColour());
		svgImage.setHoverState("no");
		return svgImage;

	}

	public Boolean isInitialised() {
		return isInitialised;
	}

}
