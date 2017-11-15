package uk.ac.nhm.nhm_www.core.componentHelpers;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.servlet.http.HttpServletRequest;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.joda.time.DateTime;
import org.joda.time.MutableDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.granite.xss.XSSAPI;
import com.day.cq.commons.ImageResource;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;

import uk.ac.nhm.nhm_www.core.model.FileReference;

/**
 * Discover Publication Component Helper class.
 */
public class ArticleHelper {
	private static final Logger LOG = LoggerFactory.getLogger(ArticleHelper.class);

	private static final String IMAGE_HEAD_TYPE = "image";
	private static final String VIDEO_HEAD_TYPE = "video";

	/*
	 * Repository Attribute names.
	 */
	public static final String TITLE_ATTRIBUTE_NAME 		= "jcr:title";
	public static final String INTRODUCTION_ATTRIBUTE_NAME  = "introduction";
	public static final String SNIPPET_ATTRIBUTE_NAME		= "snippet";
	public static final String IMAGE_CAPTION_ATTRIBUTE_NAME = "imageCaption";
	public static final String PINNED_ATTRIBUTE_NAME 		= "pinned";
	public static final String PINNED_DATE_ATTRIBUTE_NAME   = "pinnedDate";
	public static final String TAGS_ATTRIBUTE_NAME 		 	= "cq:tags";
	public static final String TYPE_ATTRIBUTE_NAME 			= "type";
	public static final String HEAD_TYPE_ATTRIBUTE_NAME 	= "headType";
	public static final String CREATION_DATE_ATTRIBUTE_NAME = "jcr:created";
	public static final String IMAGE_ALT_ATTRIBUTE_NAME		= "image/alt";
	public static final String DATE_PUBLISHED				= "datepublished";
	public static final String DATE_LAST_UDPATED			= "datelastupdated";
	public static final String HUB_TAG						= "hubTag";
	public static final String OTHER_TAGS					= "otherTags";

	/*
	 * SubResource Names.
	 */
	private static final String IMAGE_RESOURCE_NAME = "image";
	private static final String VIDEO_RESOURCE_NAME = "video";

	/*
	 * Component Properties.
	 */
	private ValueMap properties;

	/*
	 * Component Resource.
	 */
	private Resource resource;

	private String imagePath;
	private String imageNodePath;
	private String imageExtension;
	private String imageSuffix;
	private String imageAlt;
	private boolean imageConfigured;

	private String ogTitle;
	private String ogDescription;
	private String ogImagePath;
	private String selectTab;

	private String author;	
	private String updatedDate;
	private String publishedDate;
	private String analyticsDate;
	
	private Map<String, String> hubTag;
	List<Map<String, String>> tagList;

	/**
	 * Helper Class Constructor.
	 * @param resource {@link Resource Component Resource}.
	 * @param request 
	 * @param xssAPI
	 * @throws RepositoryException 
	 */
	public ArticleHelper(final Resource resource, final HttpServletRequest request, final XSSAPI xssAPI, final SlingHttpServletRequest slingRequest) throws RepositoryException {
		this.resource = resource;
		this.properties = resource.adaptTo(ValueMap.class);
		
		ResourceResolver resourceResolver = slingRequest.getResourceResolver();
		TagManager tagMgr = resourceResolver.adaptTo(TagManager.class);
		
		if(this.properties!=null){
			if(this.properties.get("image/fileReference") != null) {
				FileReference fileReference = new FileReference.FileReferenceBuilder(properties.get("image/fileReference", ""), properties, resource, request)
					.image(new ImageResource(resource))
					.xssApi(xssAPI)
					.build();

				this.imageConfigured = fileReference.getHasImage();

				if (this.imageConfigured) {
					this.imagePath = fileReference.getPath();
					this.imageNodePath = fileReference.getNodePath();
					this.imageExtension = fileReference.getExtension();
					this.imageSuffix = fileReference.getExtension();
					this.imageAlt = fileReference.getAlt();
				}
			}

			//Get date property, return last updated if exists else published
			String datePublished = null;
			String dateLastUpdated = null;

			if(this.properties.get(DATE_PUBLISHED) != null) datePublished = properties.get(DATE_PUBLISHED, String.class);
			if(this.properties.get(DATE_LAST_UDPATED) != null) dateLastUpdated = properties.get(DATE_LAST_UDPATED, String.class);

			DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("yy/MM/dd");

			this.author = getProperties().get("author", String.class);

			//Set hub tag name
			if(this.properties.get(HUB_TAG) != null) {
				String[] hubTags = this.properties.get(HUB_TAG, String[].class);
				if(hubTags.length > 0) {
					Tag tag = tagMgr.resolve(hubTags[0]);
					Map<String, String> tagMap = new HashMap<String, String>();
					if(tag!=null) {
						tagMap.put("title", tag.getTitle().toUpperCase());
						tagMap.put("path", tag.getDescription());
						this.hubTag = tagMap;	
					}
				} 
			} 
			
			//Get other tags
			this.tagList = new ArrayList<Map<String, String>>();
			
			if(this.properties.get(OTHER_TAGS) != null) {
				String[] otherTags = this.properties.get(OTHER_TAGS, String[].class);
				if(otherTags.length > 0) {
					for(int i=0; i<otherTags.length; i++) {
						Tag tag = tagMgr.resolve(otherTags[i]);
						Map<String, String> tagMap = new HashMap<String, String>();
						if(tag!=null) {
							tagMap.put("title", tag.getTitle());
							tagMap.put("path", tag.getDescription());
							this.tagList.add(tagMap);	
						}

					}
				}
			}
			
			if(dateLastUpdated != null) {
				DateTime dt = dateFormatter.parseDateTime(dateLastUpdated);
				MutableDateTime mdt = dt.toMutableDateTime();
				this.updatedDate = mdt.getDayOfMonth() + " " + getMonth(mdt.getMonthOfYear()) + " " + mdt.getYear();
			} 

			if(datePublished != null) {
				DateTime dt = dateFormatter.parseDateTime(datePublished);
				MutableDateTime mdt = dt.toMutableDateTime();
				this.publishedDate = mdt.getDayOfMonth() + " " + getMonth(mdt.getMonthOfYear()) + " " + mdt.getYear();

				if(mdt.getMonthOfYear() < 10) {
					this.analyticsDate = mdt.getYear() + "-0" + mdt.getMonthOfYear() + "-" + mdt.getDayOfMonth();
				} else {
					this.analyticsDate = mdt.getYear() + "-" + mdt.getMonthOfYear() + "-" + mdt.getDayOfMonth();
				}
			}
			else {
				this.publishedDate = "Please set a published date in the dialog";
			}
		}
	}

	/**
	 * Helper Class Constructor.
	 * @param resource {@link Resource Component Resource}.
	 */
	public ArticleHelper(Resource resource) {
		setResource(resource);
		setProperties(resource.adaptTo(ValueMap.class));

		init();
	}

	private void init() {
		//Initialise variables from Facebook tab
		//Set title
		if(properties.get("article/ogtitle") != null) {
			setOgTitle(properties.get("article/ogtitle", String.class));
		} else if(properties.get("jcr:title") != null) {
			setOgTitle(properties.get("jcr:title", String.class));
		}

		//Set description
		if(properties.get("article/ogdescription") != null) {
			setOgDescription(properties.get("article/ogdescription", String.class));
		} else if(properties.get("jcr:description") != null) {
			setOgDescription(properties.get("jcr:description", String.class));
		} else {
			setOgDescription(properties.get("", String.class));
		}

		//Set image path - value is dependent on which radio button is selected
		if(properties.get("article/headType") != null) {
			if(properties.get("article/headType").equals("image")) {
				if(properties.get("article/ogimagepath") != null) {
					setOgImagePath(properties.get("article/ogimagepath", String.class));
				} else if(properties.get("article/image/fileReference") != null) {
					setOgImagePath(properties.get("article/image/fileReference", String.class));
				} else {
					setOgImagePath(properties.get("", String.class));
				}
			}
			else if(properties.get("article/headType").equals("video")) {
				if(properties.get("article/ogvideopath") != null) {
					setOgImagePath(properties.get("article/ogvideopath", String.class));
				} else if(properties.get("article/video/youtube") != null) {
					setOgImagePath(properties.get("article/video/youtube", String.class));
				} else {
					setOgImagePath(properties.get("", String.class));
				}
			}
		}

		//Set type - image or video
		if(getProperties().get("article/headType") != null) {
			setSelectTab(selectTab = getProperties().get("article/headType", String.class));
		}
		
		
	}

	public String getMonth(int month) {
		return new DateFormatSymbols().getMonths()[month-1];
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	/**
	 * Checks if the component has enough configuration to show its content.
	 * @return <code>true</code> if the component has configured at least Title, Introduction and Image or Video. <code>false</code> in otherwise.
	 */
	public boolean isConfigured() {
		/*return true;*/
		return this.properties !=null
				&& this.properties.get(TITLE_ATTRIBUTE_NAME, String.class) != null
				&& this.properties.get(INTRODUCTION_ATTRIBUTE_NAME, String.class) != null
				&& (this.isImageHeadType() && this.imageConfigured 
						|| this.isVideoHeadType() && this.getVideo() != null);
	}


	/**
	 * Gets the Title component.
	 * @return The title configured on the component or an empty string if is not configured.
	 */
	public String getTitle() {
		if (this.properties == null) {
			return "";
		}
		return this.properties.get(TITLE_ATTRIBUTE_NAME, "");
	}

	/**
	 * Gets the Introduction component.
	 * @return The introduction configured on the component or an empty string if is not configured.
	 */
	public String getIntroduction() {
		if (this.properties == null) {
			return "";
		}
		return this.properties.get(INTRODUCTION_ATTRIBUTE_NAME, "");
	}

	/**
	 * Checks if the component has the Snippet Text configured.
	 * @return <code>true</code> if the component has configured Snippet Text Attribute. <code>false</code> in otherwise.
	 */
	public boolean hasSnippet() {
		if (this.properties == null) {
			return false;
		}
		return this.properties.get(SNIPPET_ATTRIBUTE_NAME, String.class) != null;
	}

	/**
	 * Gets the Snippet Text configured on the component.
	 * @return The Snippet Text configured on the component or an empty string if is not configured.
	 */
	public String getSnippet() {
		if (this.properties == null) {
			return "";
		}
		return this.properties.get(SNIPPET_ATTRIBUTE_NAME, "");
	}

	/**
	 * Checks if the component has and Image Caption configured.
	 * @return <code>true</code> if the component has configured the Image Caption Attribute. <code>false</code> in otherwise.
	 */
	public boolean hasImageCaption() {
		if (this.properties == null) {
			return false;
		}
		return this.properties.get(IMAGE_CAPTION_ATTRIBUTE_NAME, String.class) != null;
	}

	/**
	 * Checks if the component has and Image Caption configured.
	 * @return <code>true</code> if the component has configured the Image Caption Attribute. <code>false</code> in otherwise.
	 */
	public boolean hasImageAltText() {
		if (this.properties == null) {
			return false;
		}
		return this.properties.get(IMAGE_ALT_ATTRIBUTE_NAME, String.class) != null;
	}

	/**
	 * Gets the Image Text Caption configured on the component.
	 * @return The Snippet Text configured on the component or an empty string if is not configured.
	 */
	public String getImageCaption() {
		if (this.properties == null) {
			return "";
		}
		return this.properties.get(IMAGE_CAPTION_ATTRIBUTE_NAME, "");
	}

	/**
	 * Gets the Image Text Caption configured on the component.
	 * @return The Snippet Text configured on the component or an empty string if is not configured.
	 */
	public String getImageAltText() {
		if (this.properties == null) {
			return "";
		}
		return this.properties.get(IMAGE_ALT_ATTRIBUTE_NAME, "");
	}

	/**
	 * Checks if the component has been Pinned.
	 * @return <code>true</code> if the component has the attribute Pinned set true. <code>false</code> in otherwise.
	 */
	public boolean isPinned() {
		if (this.properties == null) {
			return false;
		}
		return this.properties.get(PINNED_ATTRIBUTE_NAME, false);
	}

	/**
	 * Gets the Pinned Date configured on the component.
	 * @return The Pinned Date configured on the component or <code>null</code> if is not configured.
	 */
	public Date getPinnedDate() {
		if (!this.isPinned()) {
			return null;
		}

		return properties.get(PINNED_DATE_ATTRIBUTE_NAME, Calendar.class).getTime();
	}

	/**
	 * Gets the tags configured on the component.
	 * @return The tags configured.
	 */
	public String[] getTags() {
		if (this.properties == null) {
			return null;
		}
		return this.properties.get(TAGS_ATTRIBUTE_NAME, String[].class);
	}

	/**
	 * Gets the Head Type configured on the component. This can be image or video.
	 * @return The Head Type (Image or Video) configured on the component or &quot;image%quot; if is not configured.
	 */
	public String getHeadType() {
		if (this.properties == null) {
			return IMAGE_HEAD_TYPE;
		}
		return this.properties.get(HEAD_TYPE_ATTRIBUTE_NAME, IMAGE_HEAD_TYPE);
	}

	/**
	 * Checks if the component has configured the Head Type as Image.
	 * @return <code>true</code> if the Head Type is &quot;image&quot;.
	 */
	public boolean isImageHeadType() {
		return IMAGE_HEAD_TYPE.equals(this.getHeadType());
	}

	/**
	 * Checks if the component has configured the Head Type as Video.
	 * @return <code>true</code> if the Head Type is &quot;video&quot;.
	 */
	public boolean isVideoHeadType() {
		return VIDEO_HEAD_TYPE.equals(this.getHeadType());
	}

	/**
	 * Gets the Image configure as Head, if the Head Type is configured as Image if not will return <code>null</code>.
	 * @return The {@link Image Image object} configured on the component if is configured the Head Type as Image or <code>null</code> otherwise. 
	 */
	/*public Image getImage() {
		if (!this.isImageHeadType()) {
			return null;
		}

		final Resource imageResource = this.resource.getChild(IMAGE_RESOURCE_NAME);
		if (imageResource == null) {
			return null;
		}

		final DiscoverHeadImage image = new DiscoverHeadImage(imageResource);
		return image.getImage();
	}*/

	/**
	 * Gets the YouTube Video Id configure as Head, if the Head Type is configured as Video if not will return <code>null</code>.
	 * @return The Youtube Video Id configured on the component if is configured the Head Type as Video or <code>null</code> otherwise. 
	 */
	public String getVideo() {
		if (!this.isVideoHeadType()) {
			return null;
		}

		final Resource videoResource = this.resource.getChild(VIDEO_RESOURCE_NAME);
		if (videoResource == null) {
			return null;
		}

		final DiscoverHeadVideo video = new DiscoverHeadVideo(videoResource);
		return video.getYouTubeVideoId();
	}

	/**
	 * Gets the Publication Type configured on the component.
	 * @return The Publication Type configured on the component.
	 */
	public String getType() {
		if (this.properties == null) {
			return null;
		}
		return this.properties.get(TYPE_ATTRIBUTE_NAME, String.class);
	}

	/**
	 * Gets the Creation Date configured on the component.
	 * @return The Creation Date of the component.
	 */
	public Date getCreationDate() {
		final Calendar calendar = this.properties.get(CREATION_DATE_ATTRIBUTE_NAME, Calendar.class);

		if (calendar == null) {
			return null;
		}

		return calendar.getTime();
	}

	public String getImagePath() {
		return this.imagePath;
	}

	public String getImageNodePath() {
		return this.imageNodePath;
	}

	public String getImageExtension() {
		return this.imageExtension;
	}

	public String getImageSuffix() {
		return this.imageSuffix;
	}

	public String getImageAlt() {
		return this.imageAlt;
	}

	public boolean isImageConfigured() {
		return this.imageConfigured;
	}

	public ValueMap getProperties() {
		return properties;
	}

	public void setProperties(ValueMap properties) {
		this.properties = properties;
	}

	public String getOgTitle() {
		return ogTitle;
	}

	public void setOgTitle(String ogTitle) {
		this.ogTitle = ogTitle;
	}

	public String getOgDescription() {
		return ogDescription;
	}

	public void setOgDescription(String ogDescription) {
		this.ogDescription = ogDescription;
	}

	public String getOgImagePath() {
		return ogImagePath;
	}

	public void setOgImagePath(String ogImagePath) {
		this.ogImagePath = ogImagePath;
	}

	public String getSelectTab() {
		return selectTab;
	}

	public void setSelectTab(String selectTab) {
		this.selectTab = selectTab;
	}

	public void setPublishedDate(String date) {
		this.publishedDate = date;
	}

	public String getPublishedDate() {
		return publishedDate;
	}

	public void setUpdatedDate(String date) {
		this.updatedDate = date;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Map<String, String> getHubTag() {
		return hubTag;
	}

	public void setHubTag(Map<String, String> hubTag) {
		this.hubTag = hubTag;
	}

	public List<Map<String, String>> getTagList() {
		return tagList;
	}

	public void setTagList(List<Map<String, String>> tagList) {
		this.tagList = tagList;
	}

}
