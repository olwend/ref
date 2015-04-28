package uk.ac.nhm.nhm_www.core.componentHelpers;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;

import com.day.cq.tagging.Tag;

public class NewsHelper extends PressReleaseHelper{

	private Tag[] tags;
	private Boolean isComponentInitialised;
	
	public NewsHelper(Resource resource, ValueMap properties, Tag[] tags) {
		super(resource, properties);
		this.tags = tags;
	}

	public Boolean getIsComponentInitialised() {
		//Tags will provide Year, Month, Type (Science or not), they are mandatory. 
		if (this.getTags() != null && this.getSummary() != null && this.getSummary().length() > 0)
		{
			this.isComponentInitialised = true;
		}
		else
		{
			this.isComponentInitialised = false;
		}
		
		return isComponentInitialised;
	}
	
	public Tag[] getTags() {
		return tags;
	}

	private void setTags(Tag[] tags) {
		this.tags = tags;
	}

}
