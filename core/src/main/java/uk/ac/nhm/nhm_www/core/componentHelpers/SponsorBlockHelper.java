package uk.ac.nhm.nhm_www.core.componentHelpers;

import java.util.Date;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;

public class SponsorBlockHelper {

	private Resource resource;
	private ValueMap properties;
	
	private String sponsor1image;
	private String sponsor2image;
	private String sponsor3image;
	private String sponsor4image;
	
	
	public SponsorBlockHelper(Resource resource,ValueMap properties)
	{
		setResource(resource);
		setProperties(properties);
		setSponsor1image(getProperties().get("sponsor1", String.class));
		setSponsor2image(getProperties().get("sponsor2", String.class));
		setSponsor3image(getProperties().get("sponsor3", String.class));
		setSponsor4image(getProperties().get("sponsor4", String.class));
	}


	public Resource getResource() {
		return resource;
	}


	private void setResource(Resource resource) {
		this.resource = resource;
	}


	public ValueMap getProperties() {
		return properties;
	}


	private void setProperties(ValueMap properties) {
		this.properties = properties;
	}


	public String getSponsor1image() {
		return sponsor1image;
	}


	private void setSponsor1image(String sponsor1image) {
		this.sponsor1image = sponsor1image;
	}


	public String getSponsor2image() {
		return sponsor2image;
	}


	private void setSponsor2image(String sponsor2image) {
		this.sponsor2image = sponsor2image;
	}


	public String getSponsor3image() {
		return sponsor3image;
	}


	private void setSponsor3image(String sponsor3image) {
		this.sponsor3image = sponsor3image;
	}


	public String getSponsor4image() {
		return sponsor4image;
	}


	private void setSponsor4image(String sponsor4image) {
		this.sponsor4image = sponsor4image;
	}
	
}
