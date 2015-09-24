package uk.ac.nhm.nhm_www.core.componentHelpers;

import org.apache.sling.api.resource.ValueMap;

public class ENewsSignupHelper extends HelperBase {
	
	private String title;

	
	public ENewsSignupHelper(ValueMap properties) {
		
		if (properties.get("title") != null) {
			this.title = properties.get("title", String.class);
		}
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
