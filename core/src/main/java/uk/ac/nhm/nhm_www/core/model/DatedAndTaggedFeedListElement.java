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

public class DatedAndTaggedFeedListElement extends PressReleaseFeedListElementImpl implements TaggedFeedListElement {

	public static final String PUBLISH_DATE_ATTRIBUTE_NAME  = "publishdate";
	public static final String TEMPLATE_ATTRIBUTE_VALUE = "/apps/nhmwww/templates/newscontentpage";
	
	protected static final Logger LOG = LoggerFactory.getLogger(DatedAndTaggedFeedListElement.class);
	
	protected String[] tags;
	protected String path;
	protected String shortIntroduction;

	public String getShortIntroduction() {
		return shortIntroduction;
	}

	public void setShortIntroduction(String shortIntroduction) {
		this.shortIntroduction = shortIntroduction;
	}

	public DatedAndTaggedFeedListElement() {
		super();
	}
	
	public DatedAndTaggedFeedListElement(Page page) {
		super(page);
		this.tags = convertTagsToStrings(page.getTags());
		this.path = page.getPath();
		if (page.getProperties().get(SHORT_INTRO_ATTRIBUTE_NAME) != null){
			this.shortIntroduction = page.getProperties().get(SHORT_INTRO_ATTRIBUTE_NAME).toString();
		}
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
	
	public String[] convertTagsToStrings(Tag[] pageTags) {
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
	
	@Override
	
	public DatedAndTaggedFeedListElement bruteForceConstructor(final Node node, final Page page, TaggedFeedListElement tElement) throws ValueFormatException, RepositoryException, PathNotFoundException {
		LOG.error("Inside the Brute Force Constructor");
		DatedAndTaggedFeedListElement element =  new DatedAndTaggedFeedListElement();
		try {
			element.setTitle(node.getProperty(DatedAndTaggedFeedListElement.TITLE_ATTRIBUTE_NAME).getString());
			element.setIntro(node.getProperty(DatedAndTaggedFeedListElement.SUMMARY_ATTRIBUTE_NAME).getString());
			LOG.error("Summary & Title are OK");
			if (node.hasProperty(DatedAndTaggedFeedListElement.SHORT_INTRO_ATTRIBUTE_NAME)){
				element.setShortIntroduction(node.getProperty(DatedAndTaggedFeedListElement.SHORT_INTRO_ATTRIBUTE_NAME).getString());
			} else {
				element.setShortIntroduction(node.getProperty(DatedAndTaggedFeedListElement.SUMMARY_ATTRIBUTE_NAME).getString());
			}
			LOG.error("Short Intro OK");
			element.setImagePath(node.getProperty(DatedAndTaggedFeedListElement.IMAGE_FILEREF_ATTRIBUTE_NAME).getString());
			element.setElementLink(page.getPath());
			element.setPage(page);
			LOG.error("ImagePath, url and Page OK");
			
			final Calendar calendarDate = node.getProperty(DatedAndTaggedFeedListElement.PUBLISH_DATE_ATTRIBUTE_NAME).getDate();
			element.setPressReleaseDate(new Date(calendarDate.getTimeInMillis()));
			LOG.error("PublishedDate OK");
			
			// Tags
			Value[] valueArray = node.getProperty(DatedAndTaggedFeedListElement.TAGS_ATTRIBUTE_NAME).getValues();
			String[] tagArray = new String[valueArray.length];
			for (int i = 0; i < valueArray.length; i++) {
				String tag = valueArray[i].getString();
				tagArray[i] = tag;
			}
			element.setTags(tagArray);
			LOG.error("Tags OK");
			
		} catch (PathNotFoundException e) {
			LOG.error("PathNotFoundException ", e);
		} catch (RepositoryException e) {
			LOG.error("RepositoryException ", e);
		} catch (Exception e) {
			LOG.error("Exception Something went awfully wrong! ", e);
		}
		return element;
	}

}