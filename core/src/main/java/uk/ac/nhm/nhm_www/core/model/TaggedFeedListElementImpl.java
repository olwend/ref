package uk.ac.nhm.nhm_www.core.model;

import java.util.Calendar;
import java.util.Date;

import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.Value;
import javax.jcr.ValueFormatException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

public class TaggedFeedListElementImpl extends FeedListElementImpl implements TaggedFeedListElement {
	
	public static final String TEMPLATE_ATTRIBUTE_VALUE = "/apps/nhmwww/templates/taggedcontentpage";
	
	protected static final Logger LOG = LoggerFactory.getLogger(TaggedFeedListElementImpl.class);
	
	protected String path;
	protected String shortIntroduction;
	protected String[] tags;
	
	public TaggedFeedListElementImpl() {
		super();
	}
	
	public TaggedFeedListElementImpl(Page page) {
		super(page);
		this.tags = convertTagsToStrings(page.getTags());
		this.path = page.getPath();
		if (page.getProperties().get(SHORT_INTRO_ATTRIBUTE_NAME) != null){
			this.shortIntroduction = page.getProperties().get(SHORT_INTRO_ATTRIBUTE_NAME).toString();
		}
	}
	
	/* (non-Javadoc)
	 * @see uk.ac.nhm.nhm_www.core.model.TaggedFeedListElement#isInitialised()
	 */
	@Override
	public boolean isInitialised() {
		if (this.tags != null && this.title != null && this.elementLink != null && this.imageResourcePath != null) {
			return true;
		} else {
			return false;
		}
	}
	
	// Short Introduction Handling
	
	/* (non-Javadoc)
	 * @see uk.ac.nhm.nhm_www.core.model.TaggedFeedListElement#getShortIntroduction()
	 */
	@Override
	public String getShortIntroduction() {
		return shortIntroduction;
	}

	/* (non-Javadoc)
	 * @see uk.ac.nhm.nhm_www.core.model.TaggedFeedListElement#setShortIntroduction(java.lang.String)
	 */
	@Override
	public void setShortIntroduction(String shortIntroduction) {
		this.shortIntroduction = shortIntroduction;
	}
	
	// Tag Handling

	public String[] convertTagsToStrings(Tag[] pageTags) {
		String[] stringTags = new String[pageTags.length];
		for (int i = 0; i < pageTags.length; i++) { 
			stringTags[i] = pageTags[i].toString();
		}
		return stringTags;
	}
	
	/* (non-Javadoc)
	 * @see uk.ac.nhm.nhm_www.core.model.TaggedFeedListElement#getTags()
	 */
	@Override
	public String[] getTags() {
		return tags;
	}
	
	/* (non-Javadoc)
	 * @see uk.ac.nhm.nhm_www.core.model.TaggedFeedListElement#setTags(java.lang.String[])
	 */
	@Override
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

	@Override
	public TaggedFeedListElement bruteForceConstructor(final Node node, final Page page, TaggedFeedListElement element) throws ValueFormatException, RepositoryException, PathNotFoundException {
		try {
			element.setTitle(node.getProperty(TaggedFeedListElementImpl.TITLE_ATTRIBUTE_NAME).getString());
			element.setIntro(node.getProperty(TaggedFeedListElementImpl.SUMMARY_ATTRIBUTE_NAME).getString());
			if (node.hasProperty(TaggedFeedListElementImpl.SHORT_INTRO_ATTRIBUTE_NAME)){
				element.setShortIntroduction(node.getProperty(TaggedFeedListElementImpl.SHORT_INTRO_ATTRIBUTE_NAME).getString());
			} else {
				element.setShortIntroduction(node.getProperty(TaggedFeedListElementImpl.SUMMARY_ATTRIBUTE_NAME).getString());
			}
			element.setImagePath(node.getProperty(TaggedFeedListElementImpl.IMAGE_FILEREF_ATTRIBUTE_NAME).getString());
			element.setElementLink(page.getPath());
			element.setPage(page);

			// Tags
			Value[] valueArray = node.getProperty(TaggedFeedListElementImpl.TAGS_ATTRIBUTE_NAME).getValues();
			String[] tagArray = new String[valueArray.length];
			for (int i = 0; i < valueArray.length; i++) {
				String tag = valueArray[i].getString();
				tagArray[i] = tag;
			}
			element.setTags(tagArray);
			
		} catch (PathNotFoundException e) {
			LOG.error("PathNotFoundException ", e);
		} catch (RepositoryException e) {
			LOG.error("RepositoryException ", e);
		} catch (Exception e) {
			LOG.error("Exception ", e);
		}
		return (TaggedFeedListElementImpl) element;
	}
}
