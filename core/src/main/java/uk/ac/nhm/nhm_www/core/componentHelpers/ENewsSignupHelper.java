package uk.ac.nhm.nhm_www.core.componentHelpers;

import org.apache.sling.api.resource.ValueMap;

public class ENewsSignupHelper extends HelperBase {
	
	private String title;
	/**
	 * Set a default campaign in case it does not get set 
	 */
	private String campaign = "www";
	
	public ENewsSignupHelper(ValueMap properties) {
		
		if (properties.get("title") != null) {
			this.title = properties.get("title", String.class);
		}
		if (properties.get("campaign") != null) {
			this.campaign = properties.get("campaign", String.class);
		}
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCampaign() {
		return campaign;
	}

	public void setCampaign(String campaign) {
		this.campaign = campaign;
	}

}
