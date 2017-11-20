package uk.ac.nhm.core.componentHelpers;

import org.apache.sling.api.resource.ValueMap;

public class AccordionHelper {
	
	private Boolean isOpen;
	private Boolean isInitialised;
	private String panelTitle;
	private String panelId;
	private String headingStyle;

	public AccordionHelper(ValueMap properties) {
		this.isOpen = properties.get("isOpen", false);
		
		if(properties.get("panelTitle", String.class) != null) {
			this.panelTitle = properties.get("panelTitle", String.class);
			this.panelId = properties.get("panelTitle", String.class).replaceAll(" ", "-");
		} else {
			this.panelTitle = null;
			this.panelId = "InvalidComponent";
		}
		
		this.panelId = this.panelId.replaceAll("\\(", "");
		this.panelId = this.panelId.replaceAll("\\)", "");
		this.panelId = this.panelId.replaceAll("#", "");
		this.panelId = this.panelId.replaceAll(",", "");
		this.headingStyle = properties.get("heading-style", "");
		init();
	}
	
	private void init() {
		if(this.panelTitle != null && !this.panelTitle.equals("")) {
			this.isInitialised = true;
		} else {
			this.isInitialised = false;
		}
	}

	public Boolean isOpen() {
		return isOpen;
	}

	public void setIsOpen(Boolean isOpen) {
		this.isOpen = isOpen;
	}
	
	public Boolean isInitialised() {
		return isInitialised;
	}

	public String getPanelTitle() {
		return panelTitle;
	}

	public void setPanelTitle(String panelTitle) {
		this.panelTitle = panelTitle;
	}

	public String getPanelId() {
		return panelId;
	}

	public void setPanelId(String panelId) {
		this.panelId = panelId;
	}

	public String getHeadingStyle() {
		return headingStyle;
	}

	public void setHeadingStyle(String headingStyle) {
		this.headingStyle = headingStyle;
	}
}
