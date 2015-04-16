package uk.ac.nhm.nhm_www.core.model;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.sling.api.resource.ResourceResolver;

import com.day.cq.tagging.Tag;
import com.day.cq.wcm.api.Page;

public class DatedAndTaggedFeedListElement extends PressReleaseFeedListElement {

	public static final String TITLE_ATTRIBUTE_NAME 		= "jcr:title";
	public static final String SUMMARY_ATTRIBUTE_NAME  		= "summary";
	public static final String PINNED_ATTRIBUTE_NAME 		= "pinned";
	public static final String PUBLISH_DATE_ATTRIBUTE_NAME  = "publishdate";
	public static final String TAGS_ATTRIBUTE_NAME 		 	= "cq:tags";
	public static final String IMAGE_FILEREF_ATTRIBUTE_NAME	= "image/fileReference";
	
	protected Tag[] tags;
	protected String path;

	public DatedAndTaggedFeedListElement() {
		super();
	}
	
	public DatedAndTaggedFeedListElement(Page page) {
		super(page);
		this.tags = page.getTags();
		this.path = page.getPath();
	}

	public boolean isInitialised() {
		if (this.tags != null && this.title != null && this.elementLink != null && this.imageResourcePath != null && this.getPressReleaseDate() != null) {
			return true;
		} else {
			return false;
		}
	}

	public Tag[] getTags() {
		return tags;
	}

	public boolean hasTags(Tag[] tags) {
		boolean found = false;
		List<Tag> pageTags = Arrays.asList(this.tags);
		Iterator<Tag> filterIterator = Arrays.asList(tags).iterator();

		while (!found && filterIterator.hasNext()) {
			Tag tagFilter = filterIterator.next();
			found = pageTags.contains(tagFilter);
		}
		return found;
	}

	public void setTags(Tag[] tags) {
		this.tags = tags;
	}
	
}