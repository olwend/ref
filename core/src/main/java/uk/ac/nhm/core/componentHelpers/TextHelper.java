package uk.ac.nhm.core.componentHelpers;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;

public class TextHelper extends HelperBase {

	private ValueMap properties;
	
	
	public TextHelper(ValueMap properties) {
		this.properties = properties;
	}
	public TextHelper(HelperFactory factory) {
		this.properties = factory.getProperties();
	}
	public TextHelper(Resource resource) {
		HelperFactory factory = new HelperFactory(resource);
		this.properties = factory.getProperties();
	}
	
	public String getText()
	{
		if (this.properties.get("text",String.class) != null && this.properties.get("text",String.class).trim().length() > 0)
		{
			return this.properties.get("text",String.class);
		}
		else
		{
			return null;
		}
	}

	public boolean isComponentInitialised() {
		if (this.getText() != null)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
