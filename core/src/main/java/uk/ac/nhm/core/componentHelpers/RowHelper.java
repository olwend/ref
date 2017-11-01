package uk.ac.nhm.core.componentHelpers;

import org.apache.sling.api.resource.ValueMap;

public class RowHelper {
	private Boolean isEqualizedDisabled;
	private Boolean isEqualize3Col;

	public RowHelper(ValueMap properties) {
		
		this.isEqualizedDisabled = properties.get("isEqualizedDisabled", false);
		this.isEqualize3Col = properties.get("isEqualize3Col", false);
	}

	public Boolean isEqualizedDisabled() {
		return isEqualizedDisabled;
	}
	
	public Boolean isEqualize3Col() {
		return isEqualize3Col;
	}

	
}
