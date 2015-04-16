package uk.ac.nhm.nhm_www.core.model;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.sling.api.resource.ResourceResolver;

import uk.ac.nhm.nhm_www.core.componentHelpers.DatedAndTaggedFeedListHelper;

import com.day.cq.tagging.Tag;
import com.day.cq.wcm.api.Page;

public class DatedAndTaggedFeedListElement extends PressReleaseFeedListElement {

	public static final String TITLE_ATTRIBUTE_NAME 		= "jcr:title";
	public static final String SUMMARY_ATTRIBUTE_NAME  		= "summary";
	public static final String PINNED_ATTRIBUTE_NAME 		= "pinned";
	public static final String PUBLISH_DATE_ATTRIBUTE_NAME  = "publishdate";
	public static final String TAGS_ATTRIBUTE_NAME 		 	= "cq:tags";
	public static final String IMAGE_FILEREF_ATTRIBUTE_NAME	= "image/fileReference";
	
	protected String[] tags;
	protected String path;

	public DatedAndTaggedFeedListElement() {
		super();
	}
	
	public DatedAndTaggedFeedListElement(Page page) {
		super(page);
		this.tags = convertTagsToStrings(page.getTags());
		this.path = page.getPath();
	}

	public boolean isInitialised() {
		if (this.tags != null && this.title != null && this.elementLink != null && this.imageResourcePath != null && this.getPressReleaseDate() != null) {
			return true;
		} else {
			return false;
		}
	}

	public String[] getTags() {
		return tags;
	}
	
	public void setTags(String[] tags) {
		this.tags = tags;
	}
	
	private String[] convertTagsToStrings(Tag[] pageTags) {
		String[] stringTags = new String[pageTags.length];

		for (int i = 0; i < pageTags.length; i++) { 
			stringTags[i] = pageTags[i].toString();
		}
		return stringTags;
	}

//	public boolean hasTags(Tag[] tags) {
//		boolean found = false;
//		List<Tag> pageTags = Arrays.asList(this.tags);
//		Iterator<Tag> tagsToCheck = Arrays.asList(tags).iterator();
//
//		while (!found && tagsToCheck.hasNext()) {
//			Tag tag = tagsToCheck.next();
//			found = pageTags.contains(tag);
//		}
//		return found;
//	}
	
}