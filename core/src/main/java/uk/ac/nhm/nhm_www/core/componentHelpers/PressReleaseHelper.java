package uk.ac.nhm.nhm_www.core.componentHelpers;

import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;

public class PressReleaseHelper {

	private Resource resource;
	private ValueMap properties;
	private Boolean pinned;
	private String summary;
	private Date publishDate;
	private Boolean isComponentInitialised;
	private String image;
	
	public PressReleaseHelper(Resource resource,ValueMap properties)
	{
//		this.image = getProperties().get("image", String.class);
		setResource(resource);
		setProperties(properties);
		setSummary(getProperties().get("summary", String.class));
		setPublishDate(getProperties().get("publishdate", Date.class));
		setPinned(getProperties().get("pinned", Boolean.class));
		
	}

	public ValueMap getProperties() {
		return properties;
	}

	public void setProperties(ValueMap properties) {
		this.properties = properties;
	}

	public Boolean getIsComponentInitialised() {
		if (getSummary() != null && getSummary().length() > 0)
		{
			isComponentInitialised = true;
		}
		else
		{
			isComponentInitialised = false;
		}
		
		return isComponentInitialised;
	}

	public Boolean getPinned() {
		return pinned;
	}

	public void setPinned(Boolean pinned) {
		this.pinned = pinned;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Date getPublishDate() {
		return publishDate;
	}
	
	@Deprecated
	public String getFormattedPublishDate()
	{
		String dateStr;
		 dateStr = new SimpleDateFormat("d MMMM, YYYY ").format(this.getPublishDate());
		 return dateStr;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}
}
