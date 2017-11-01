package uk.ac.nhm.core.componentHelpers;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;

/**
 * Discover List Component Helper Class.
 */
public class DiscoverListHelper {
	/*
	 * Repository Attribute names.
	 */
	private static final String TITLE_ATTRIBUTE_NAME = "jcr:title";
	private static final String LINK_ATTRIBUTE_NAME	 = "link";
	private static final String TAGS_ATTRIBUTE_NAME  = "cq:tags";
	
	/*
	 * Component Properties.
	 */
	private ValueMap properties;
	
	/**
	 * Helper Class Constructor.
	 * @param resource {@link Resource Component Resource}.
	 */
	public DiscoverListHelper(final Resource resource) {
		this.properties = resource.adaptTo(ValueMap.class);
	}
	
	/**
	 * Gets the Title component.
	 * @return The title configured on the component or <code>null</code> if is not configured.
	 */
	public String getTitle() {
		if (this.properties == null) {
			return null;
		}
		return this.properties.get(TITLE_ATTRIBUTE_NAME, String.class);
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
	 * Gets the title link. Adding &quot;.html&quot; if is a internal link or null if is not a valid external link.
	 * @return The link configured on the component.
	 */
	public String getTitleLink() {
		if (this.properties == null) {
			return null;
		}
		final String link = this.properties.get(LINK_ATTRIBUTE_NAME, String.class);
		if (link == null) {
			return null;
		}
		
		if (link.startsWith("http://") || link.startsWith("https://")) {
			return link;
		}
		
		if (link.startsWith("/content")) {
			return link + ".html";
		}
		
		return null;
	}

	/**
	 * Checks if the component has the Title configured.
	 * @return <code>true</code> if the component has configured the title attribute. <code>false</code> otherwise.
	 */
	public boolean hasTitle() {
		final String title = this.getTitle();
		return title != null && !title.isEmpty();
	}
	
	/**
	 * Checks if the component has the Title Link configured.
	 * @return <code>true</code> if the component has configured the title Link and it is a valid one. <code>false</code> otherwise.
	 */
	public boolean hasTitleLink() {
		return this.getTitleLink() != null;
	}
	
	/**
	 * Checks if the component has tags configured.
	 * @return <code>true</code> if the component has configured tags. <code>false</code> otherwise.
	 */
	public boolean hasTags() {
		final String[] tags = this.getTags();
		return tags != null && tags.length > 0;
	}
	
}
