package uk.ac.nhm.nhm_www.core.componentHelpers;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.joda.time.DateTime;
import org.joda.time.MutableDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import uk.ac.nhm.nhm_www.core.model.FileReference;

import com.adobe.granite.xss.XSSAPI;
import com.day.cq.commons.ImageResource;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.foundation.Image;

/**
 * Discover Publication Component Helper class.
 */
public class DiscoverPublicationHelper {
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
	private String pageTitle;
	private String pageDescription;
	private String selectTab;
	
	private String date;
	
	/**
	 * Helper Class Constructor.
	 * @param resource {@link Resource Component Resource}.
	 * @param request 
	 * @param xssAPI
	 */
	public DiscoverPublicationHelper(final Resource resource, final HttpServletRequest request, final XSSAPI xssAPI) {
		this.resource   = resource;
		this.properties = resource.adaptTo(ValueMap.class);
		
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
		
		String datePublished = properties.get(DATE_PUBLISHED, String.class);
		String dateLastUpdated = properties.get(DATE_LAST_UDPATED, String.class);
		
		DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("MM/dd/yy");
		
		if(dateLastUpdated != null) {
			DateTime dt = dateFormatter.parseDateTime(dateLastUpdated);
			MutableDateTime mdt = dt.toMutableDateTime();
			this.date = "Last updated " + mdt.getDayOfMonth() + " " + getMonth(mdt.getMonthOfYear()) + " " + mdt.getYear();
		}
		else if(datePublished != null) {
			DateTime dt = dateFormatter.parseDateTime(datePublished);
			MutableDateTime mdt = dt.toMutableDateTime();
			this.date = mdt.getDayOfMonth() + " " + getMonth(mdt.getMonthOfYear()) + " " + mdt.getYear();
		}
		else {
			this.date = "Please set a published date in the dialog";
		}
	}
	
	/**
	 * Helper Class Constructor.
	 * @param resource {@link Resource Component Resource}.
	 */
	public DiscoverPublicationHelper(Resource resource) {
		setResource(resource);
		setProperties(resource.adaptTo(ValueMap.class));
		
		init();
	}
	
	private void init() {
		//Initialise variables from Facebook tab
		
		//Set title		
		String ogTitle = "";
		if(getProperties().get("ogtitle") != null) {
			ogTitle = getProperties().get("ogtitle", String.class);
			
		}
		setOgTitle(ogTitle);
		
		//Set description
		String ogDescription = "";
		if(getProperties().get("ogdescription") != null) {
			ogDescription = getProperties().get("ogdescription", String.class);
		}
		setOgDescription(ogDescription);

		//Set image path - value is dependent on which radio button is selected
		String ogImagePath = "";
		if(getProperties().get("selectTab") != null) {
			if(getProperties().get("selectTab").equals("radioImage")) {
				if(getProperties().get("ogimagepath") != null) {
					ogImagePath = getProperties().get("ogimagepath", String.class);
				}
			}
			else if(getProperties().get("selectTab").equals("radioVideo")) {
				if(getProperties().get("ogvideopath") != null) {
					ogImagePath = getProperties().get("ogvideopath", String.class);
				}
			}
		}
		setOgImagePath(ogImagePath);

		//Set title - default from 'Basic' tab
		String pageTitle = "";
		if(getProperties().get("jcr:title") != null) {
			pageTitle = getProperties().get("jcr:title", String.class);
		}
		setPageTitle(pageTitle);
		
		//Set description - default from 'Basic' tab
		String pageDescription = "";
		if(getProperties().get("jcr:description") != null) {
			pageDescription = getProperties().get("jcr:description", String.class);
		}
		setPageDescription(pageDescription);
		
		//Set value of radio buttons
		String selectTab = "";
		if(getProperties().get("selectTab") != null) {
			selectTab = getProperties().get("selectTab", String.class);
		}
		setSelectTab(selectTab);
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

	public String getPageTitle() {
		return pageTitle;
	}

	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}

	public String getPageDescription() {
		return pageDescription;
	}

	public void setPageDescription(String pageDescription) {
		this.pageDescription = pageDescription;
	}

	public String getSelectTab() {
		return selectTab;
	}

	public void setSelectTab(String selectTab) {
		this.selectTab = selectTab;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
}
