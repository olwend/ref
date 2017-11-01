package uk.ac.nhm.core.componentHelpers;

import javax.servlet.http.HttpServletRequest;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.joda.time.DateTime;
import org.joda.time.MutableDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.granite.xss.XSSAPI;
import com.day.cq.commons.ImageResource;

import uk.ac.nhm.core.model.FileReference;

public class ArticleRelatedHelper {

	private static final Logger LOG = LoggerFactory.getLogger(ArticleRelatedHelper.class);
	
	private static final String IMAGE_HEAD_TYPE = "image";
	private static final String VIDEO_HEAD_TYPE = "video";
	
	public static final String HEAD_TYPE_ATTRIBUTE_NAME 	= "headType";
	public static final String SNIPPET_ATTRIBUTE_NAME		= "snippet";
	public static final String TITLE_ATTRIBUTE_NAME 		= "jcr:title";
	public static final String INTRODUCTION_ATTRIBUTE_NAME  = "introduction";
	
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
	
	public ArticleRelatedHelper(final Resource resource, final HttpServletRequest request, final XSSAPI xssAPI) {
		this.resource = resource;
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
	 * Gets the YouTube Video Id configure as Head, if the Head Type is configured as Video if not will return <code>null</code>.
	 * @return The Youtube Video Id configured on the component if is configured the Head Type as Video or <code>null</code> otherwise. 
	 */
	public String getVideo() {
		if (!this.isVideoHeadType()) {
			LOG.error("a");
			return null;
		}
		LOG.error("b");

		final Resource videoResource = this.resource.getChild(VIDEO_RESOURCE_NAME);
		LOG.error(this.resource.getPath());
		if (videoResource == null) {
			return null;
		}

		final DiscoverHeadVideo video = new DiscoverHeadVideo(videoResource);
		return video.getYouTubeVideoId();
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
	 * Gets the Snippet Text configured on the component.
	 * @return The Snippet Text configured on the component or an empty string if is not configured.
	 */
	public String getSnippet() {
		if (this.properties == null) {
			return "";
		}
		return this.properties.get(SNIPPET_ATTRIBUTE_NAME, "");
	}
	
	public boolean isImageConfigured() {
		return this.imageConfigured;
	}
}
