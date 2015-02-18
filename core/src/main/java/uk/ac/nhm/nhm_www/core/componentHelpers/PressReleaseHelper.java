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
		
		final String[] suffixes =
				  //    0     1     2     3     4     5     6     7     8     9
				     { "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th",
				  //    10    11    12    13    14    15    16    17    18    19
				       "th", "th", "th", "th", "th", "th", "th", "th", "th", "th",
				  //    20    21    22    23    24    25    26    27    28    29
				       "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th",
				  //    30    31
				       "th", "st" };

		 Date date = new Date();
		 SimpleDateFormat formatDayOfMonth  = new SimpleDateFormat("d");
		 int day = Integer.parseInt(formatDayOfMonth.format(date));
		 String dayStr = day + suffixes[day];
		 dateStr = dayStr + new SimpleDateFormat(" MMMM yyyy").format(this.getPublishDate());
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
