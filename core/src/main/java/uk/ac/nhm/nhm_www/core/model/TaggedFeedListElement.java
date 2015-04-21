package uk.ac.nhm.nhm_www.core.model;

import java.util.Date;
import java.util.Iterator;

import uk.ac.nhm.nhm_www.core.componentHelpers.PressReleaseFeedListHelper;

import com.day.cq.tagging.Tag;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaggedFeedListElement extends FeedListElement {
	
	public static final String TITLE_ATTRIBUTE_NAME 		= "jcr:title";
	public static final String SUMMARY_ATTRIBUTE_NAME  		= "summary";
	public static final String SHORT_INTRO_ATTRIBUTE_NAME 	= "shortIntroduction";
	public static final String PINNED_ATTRIBUTE_NAME 		= "pinned";
	public static final String TAGS_ATTRIBUTE_NAME 		 	= "cq:tags";
	public static final String IMAGE_FILEREF_ATTRIBUTE_NAME	= "image/fileReference";
	
	protected static final Logger logger = LoggerFactory.getLogger(TaggedFeedListElement.class);
	
	protected String path;
	protected String shortIntroduction;
	protected String[] tags;
	
	public TaggedFeedListElement() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public TaggedFeedListElement(Page page) {
		super(page);
		this.tags = convertTagsToStrings(page.getTags());
		this.path = page.getPath();
		if (page.getProperties().get(SHORT_INTRO_ATTRIBUTE_NAME) != null){
			this.shortIntroduction = page.getProperties().get(SHORT_INTRO_ATTRIBUTE_NAME).toString();
		}
	}
	
	public boolean isInitialised() {
		if (this.tags != null && this.title != null && this.elementLink != null && this.imageResourcePath != null) {
			return true;
		} else {
			return false;
		}
	}
	
	// Short Introduction Handling
	
	public String getShortIntroduction() {
		return shortIntroduction;
	}

	public void setShortIntroduction(String shortIntroduction) {
		this.shortIntroduction = shortIntroduction;
	}
	
	// Tag Handling

	private String[] convertTagsToStrings(Tag[] pageTags) {
		String[] stringTags = new String[pageTags.length];
		for (int i = 0; i < pageTags.length; i++) { 
			stringTags[i] = pageTags[i].toString();
		}
		return stringTags;
	}
	
	public String[] getTags() {
		return tags;
	}
	
	public void setTags(String[] tags) {
		this.tags = tags;
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
