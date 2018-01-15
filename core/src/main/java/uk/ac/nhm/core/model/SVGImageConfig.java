package uk.ac.nhm.core.model;

public class SVGImageConfig {

	//TODO: tona: This is a failed attempt to refactor the various CTA / Event Summary pages to remove copypaste code. Leaving it in the codebase for future reference.
	private String iconClass;
	private String svgSection;
	
	public SVGImageConfig(String iconClass, String svgSection)
	{
		this.iconClass = iconClass;
		this.svgSection = svgSection;
	}
	public SVGImageConfig()
	{
		
	}

	private enum Icon {DATES,TIMES,AFTERHOURS,TICKETS,TIMEDENTRY,AUDIO,PHOTO,CARD}
	
	private Icon enumIcon;
	

	public void setEnumIcon()
	{
		if (this.iconClass.equals("dates")) {this.enumIcon = Icon.DATES;}
		else if (this.iconClass.equals("times")) {this.enumIcon = Icon.TIMES;}
		else if (this.iconClass.equals("after-hours")) {this.enumIcon = Icon.AFTERHOURS;} 
		else if (this.iconClass.equals("tickets")) {this.enumIcon = Icon.TICKETS;} 
		else if (this.iconClass.equals("timed-entry")) {this.enumIcon = Icon.TIMEDENTRY;} 
		else if (this.iconClass.equals("audio")) {this.enumIcon = Icon.AUDIO;} 
		else if (this.iconClass.equals("photo")) {this.enumIcon = Icon.PHOTO;}
		else if (this.iconClass.equals("membership")) {this.enumIcon = Icon.CARD;}
		// Note that joinnow is the same as membership; there used to be two dropdowns in the dialog that pointed to the same icon. Keep this in to maintain compatibility in case there's any old data in the JCR.
		else if (this.iconClass.equals("joinnow")) {this.enumIcon = Icon.CARD;}
		else if (this.iconClass.equals("joinnow")) {this.enumIcon = Icon.CARD;}
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
		switch (this.section) 
		 {
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


	public String getSvgFallback()
	{
		 switch (this.enumIcon) 
		 {
	            case DATES:  return "calendar-lg.png";
	            case TIMES:  return "clock-lg.png";
	            case AFTERHOURS:  return "sunset.png";
	            case TICKETS:  return "ticket.png";
	            case TIMEDENTRY:  return "alarm_clock.png";
	            case AUDIO: return "headphones.png";
	            case PHOTO:  return "nophotography.png";
	            case CARD:  return "card.png";
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
	            default: return "";            
		 }
	        
	}

	public String getStrokeWidth()
	{
			switch (this.enumIcon)
			{
	            case TICKETS:  return "3";	
	            case TIMES:  return "5";
	            default: return "2";
			}
	}
	
}
