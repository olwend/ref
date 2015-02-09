package uk.ac.nhm.nhm_www.core.componentHelpers;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;

import uk.ac.nhm.nhm_www.core.model.FileReference;

import com.adobe.granite.xss.XSSAPI;
import com.day.cq.commons.ImageResource;
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
	private String imageExtension;
	private String imageSuffix;
	private String imageAlt;
	private boolean imageConfigured;
	
	/**
	 * Helper Class Constructor.
	 * @param resource {@link Resource Component Resource}.
	 */
	public DiscoverPublicationHelper(final Resource resource, final HttpServletRequest request, final XSSAPI xssAPI) {
		this.resource   = resource;
		this.properties = resource.adaptTo(ValueMap.class);
		
		FileReference fileReference = new FileReference.FileReferenceBuilder(properties.get("image/fileReference", ""), properties, resource, request)
		.image(new ImageResource(resource))
		.xssApi(xssAPI)
		.build();
		
		this.imageConfigured = fileReference.getHasImage();
		
		if (this.imageConfigured)
		{
			this.imagePath = fileReference.getPath();
			this.imageExtension = fileReference.getExtension();
			this.imageSuffix = fileReference.getExtension();
			this.imageAlt = fileReference.getAlt();
		}
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

	
}
