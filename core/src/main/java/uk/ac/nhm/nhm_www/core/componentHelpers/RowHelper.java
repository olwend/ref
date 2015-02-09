package uk.ac.nhm.nhm_www.core.componentHelpers;

import org.apache.sling.api.resource.ValueMap;

public class RowHelper {
	private Boolean isEqualizedDisabled;

	public RowHelper(ValueMap properties) {
		
		this.isEqualizedDisabled = properties.get("isEqualizedDisabled", false);
	}

	public Boolean isEqualizedDisabled() {
		return isEqualizedDisabled;
	}

	
}
