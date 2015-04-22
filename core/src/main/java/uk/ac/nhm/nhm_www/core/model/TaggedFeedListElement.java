package uk.ac.nhm.nhm_www.core.model;

import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.ValueFormatException;

import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.day.cq.wcm.api.Page;

public interface TaggedFeedListElement extends FeedListElement {

	public static final String TITLE_ATTRIBUTE_NAME = "jcr:title";
	public static final String SUMMARY_ATTRIBUTE_NAME = "summary";
	public static final String SHORT_INTRO_ATTRIBUTE_NAME = "shortIntroduction";
	public static final String PINNED_ATTRIBUTE_NAME = "pinned";
	public static final String TAGS_ATTRIBUTE_NAME = "cq:tags";
	public static final String IMAGE_FILEREF_ATTRIBUTE_NAME = "image/fileReference";

	public abstract boolean isInitialised();

	public abstract String getShortIntroduction();

	public abstract void setShortIntroduction(String shortIntroduction);

	public abstract String[] getTags();

	public abstract void setTags(String[] tags);
	
	public abstract String[] convertTagsToStrings(Tag[] pageTags);
	
	public TaggedFeedListElement bruteForceConstructor(final Node node, final Page page, TaggedFeedListElement element) throws ValueFormatException, RepositoryException, PathNotFoundException ;

}