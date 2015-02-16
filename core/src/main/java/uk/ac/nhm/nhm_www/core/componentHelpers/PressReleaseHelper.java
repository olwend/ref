package uk.ac.nhm.nhm_www.core.componentHelpers;

import java.util.Date;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

public class PressReleaseHelper {

	private Resource resource;
	private ValueMap properties;
	private Boolean pinned;
	private String summary;
	private Date publishDate;
	
	public PressReleaseHelper(Resource resource,ValueMap properties)
	{
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
