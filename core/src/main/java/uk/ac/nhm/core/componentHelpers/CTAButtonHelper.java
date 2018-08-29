package uk.ac.nhm.core.componentHelpers;

import javax.jcr.AccessDeniedException;
import javax.jcr.ItemNotFoundException;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.servlet.http.HttpServletRequest;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.nhm.core.model.AEMImage;
import uk.ac.nhm.core.model.FileReference;
import uk.ac.nhm.core.model.SVGImage;
import uk.ac.nhm.core.utils.LinkUtils;
import uk.ac.nhm.core.utils.NodeUtils;

import com.adobe.granite.xss.XSSAPI;
import com.day.cq.commons.ImageResource;
import com.day.cq.commons.jcr.JcrUtil;
import com.day.cq.wcm.api.Page;



/**
 * 
 * @author alext2
 *
 */
public class CTAButtonHelper extends HelperBase {

	private String path;
	private String extension;
	private String suffix;
	private String alt;
	private String iconClass;
	private String heading;
	private String text;
	private String title;
	private String linkURL;
	private boolean newwindow;
	private String sectionOverride;
	private AEMImage image;
	private Boolean initialised;
	private Boolean hasImage;
	private Page page;
	private Resource resource;
	private boolean isFullWidthCTA;
	private Node parentNode;
	private SVGImage svgImage;
	private String svgSection;
	private ValueMap properties;
	
	protected static final Logger logger = LoggerFactory.getLogger(CTAButtonHelper.class);
	
	public CTAButtonHelper(Resource resource) {
		this.helperFactory = new HelperFactory(resource);
		helperFactory.getProperties();
		init();
	}
	
	/**
	 * 
	 * @param properties
	 * @param resource
	 * @param request
	 * @param xssAPI
	 * @param section
	 * @throws AccessDeniedException
	 * @throws ItemNotFoundException
	 * @throws RepositoryException
	 */
	public CTAButtonHelper(ValueMap properties, Resource resource, HttpServletRequest request, XSSAPI xssAPI, String section) throws AccessDeniedException, ItemNotFoundException, RepositoryException {
		this.hasImage = false;
		this.initialised = false;
		this.heading = properties.get("heading", "");
		
		// Fix for the usage of this helper in HTI && Grand Summary
		if ( (this.heading == null || this.heading.equals("")) && (properties.get("cta-title") != null) ){
			this.heading = properties.get("cta-title", String.class);
		}
		
		this.title = properties.get("title", "");
		
		this.resource = resource;
		this.page = this.resource.adaptTo(Page.class);
		this.sectionOverride = properties.get("section-override", "");
		if(sectionOverride != null && !sectionOverride.equals("")) {
			this.svgSection = sectionOverride;
		} else {
			this.svgSection = section.toLowerCase().trim();
		}
		
		
		FileReference fileReference = new FileReference.FileReferenceBuilder(properties.get("fileReference", ""), properties, resource, request)
		.image(new ImageResource(resource))
		.xssApi(xssAPI)
		.build();
		
		if (fileReference.getHasImage())
		{
			this.path = fileReference.getPath();
			this.extension = fileReference.getExtension();
			this.suffix = fileReference.getSuffix();
			this.alt = fileReference.getAlt();
			this.hasImage = fileReference.getHasImage();
					
		}
		
		this.iconClass = properties.get("calltoaction-type", "");
		this.sectionOverride = properties.get("section-override", "");
		this.text= properties.get("text", "");
		this.linkURL = LinkUtils.getFormattedLink(properties.get("cta-path", ""));
		if (properties.get("newwindow") != null) {
			this.newwindow = properties.get("newwindow",false);
		}
				
		this.parentNode = this.resource.getParent().adaptTo(Node.class); // 1 = parent node
		if (NodeUtils.getRowType(parentNode) == NodeUtils.RowType.ROWFULLWIDTH)
		{
			this.isFullWidthCTA = true;
		}
		else
		{
			this.isFullWidthCTA = false;
		}
		
		if (this.isFullWidthCTA)
		{
			this.setCssOnGrandparentNode();
		}
		
		if(!this.iconClass.equals("") && (!this.text.equals("") || !this.heading.equals("") )) {
			init();
		}
		
	}
	
	private void setCssOnGrandparentNode() throws AccessDeniedException, ItemNotFoundException, RepositoryException {
		Resource parentResource = this.resource.getParent();
		Node ancestorNode = parentResource.getParent().adaptTo(Node.class);
		JcrUtil.setProperty(ancestorNode, "additionalCss", "bottom-banner");
		ancestorNode.getSession().save();
		
		//TODO make this future proof and recursive
		/*while(!isRowFound) {
			count++;
			
			if(parentNode.getPath().endsWith("rowfullwidth")) {
				
				isRowFound = true;
			} else {
				parentNode =  parentNode.getParent();
				logger.error("parentNode.getDepth():");
				if(parentNode.getDepth() < 1) {
					isRowFound = true;
				}
			}
			
			
		}*/
	}
	
	public CTAButtonHelper(String section) throws AccessDeniedException, ItemNotFoundException, RepositoryException {
		this.svgSection = section.toLowerCase().trim();
		this.setSvgColour();
		this.initialised = true;
		
	}
	
	public CTAButtonHelper(ValueMap properties)
	{
		this.properties = properties;
		init();
	}


	private void init() {
		this.setEnumIcon();
		this.setSvgColour();
		this.initialised = true;
		
	}
	
	


	public String getHeading() {
		return heading;
	}

	public void setHeading(String heading) {
		this.heading = heading;
	}

	public String getIconClass() {
		return iconClass;
	}


	public void setIconClass(String iconClass) {
		this.iconClass = iconClass;
	}


	public Boolean isInitialised() {
		return initialised;
	}


	public void setInitialised(Boolean initialised) {
		this.initialised = initialised;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getLinkURL() {
		return linkURL;
	}

	public void setLinkURL(String linkURL) {
		this.linkURL = linkURL;
	}

	public Boolean isHasImage() {
		return hasImage;
	}

	public void setHasImage(Boolean hasImage) {
		this.hasImage = hasImage;
	}

	public ValueMap getProperties() {
		return properties;
	}

	public void setProperties(ValueMap properties) {
		this.properties = properties;
	}

	public Boolean getInitialised() {
		return initialised;
	}

	public AEMImage getImage() {
		return image;
	}

	public void setImage(AEMImage image) {
		this.image = image;
	}
	
	public boolean getIsFullWidthCTA() {
		return this.isFullWidthCTA;		
	}

	public void setSVGImage(SVGImage svgImage)
	{
		this.svgImage = svgImage;
	}
	
	//TODO: tona: This is not DRY - I've mostly copied & pasted directly from eventsummaryhelper.java - Needs refactoring at some point
	
	private enum Icon {DATES,TIMES,AFTERHOURS,TICKETS,TIMEDENTRY,AUDIO,PHOTO,CARD,AUTHOR,BRANDING,GALLERY,LOCATION,NOFLASHPHOTOGRAPHY,NOPHOTOGRAPHY,SUBSCRIPTION}
	
	private Icon enumIcon;
	
	private void setEnumIcon()
	{
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
	
	private enum Sections { HOME, VISIT, DISCOVER, TAKEPART, SUPPORTUS, SHOP, SCHOOLS, OURSCIENCE, ABOUTUS, NEWSPRESS,WORKINGWITHUS, BUSINESSSERVICES, CONTACTUS, GENERAL}
	private Sections section;
	

	public void setSvgColour() {
		logger.error("Section passed: " + this.svgSection);
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
		switch (this.section) 
		 {
	            case HOME:  return "#565656";
	            case VISIT:  return "#A1164E";
	            case DISCOVER:  return "#808080";
	            case TAKEPART:  return "#BF491F";
	            case SUPPORTUS:  return "#7F206E";
	            case SHOP:  return "#808080";
	            case SCHOOLS:  return "#0983BB";
	            case OURSCIENCE:  return "#2C6100";
	            case ABOUTUS:  return "#0B5C7B";
	            case NEWSPRESS:  return "#107B5E";
	            case WORKINGWITHUS:  return "#956624";
	            case BUSINESSSERVICES:  return "#253480";
	            case CONTACTUS:  return "#808080";
	            default: return "#000";            
		 }
	}


	public String getSvgFallback()
	{
		 switch (this.enumIcon) 
		 {
	            case DATES:  return "icon_l_feature_calendar";
	            case TIMES:  return "icon_l_feature_clock";
	            case AFTERHOURS:  return "icon_l_feature_sunset";
	            case TICKETS:  return "icon_l_feature_ticket";
	            case TIMEDENTRY:  return "icon_l_feature_alarm_clock";
	            case AUDIO: return "icon_l_feature_audioguidepng";
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
	
	public String getSvgAlt()
	{
		 switch (this.enumIcon) 
		 {
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

	public String getSvgTitle()
	{
		 switch (this.enumIcon) 
		 {
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
	
	public String getSvg()
	{
		 switch (this.enumIcon) 
		 {
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

	public String getStrokeWidth()
	{
			switch (this.enumIcon)
			{
	            case TICKETS:  return "3";	
	            case TIMES:  return "3";
	            case LOCATION: return "3";
	            default: return "2";
			}
	}
	
	
	public SVGImage getSVGImage()
	{
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

	public Boolean getHasText() {
		if (this.text.trim().length() > 0) {return true;} else {return false;}
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSectionOverride() {
		return sectionOverride;
	}

	public void setSectionOverride(String sectionOverride) {
		this.sectionOverride = sectionOverride;
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
