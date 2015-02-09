package uk.ac.nhm.nhm_www.core.componentHelpers;

import com.day.cq.wcm.api.components.DropTarget;
import com.day.cq.wcm.foundation.Image;

import org.apache.sling.api.resource.Resource;

/**
 * Head Type
 */
public class DiscoverHeadImage {
	
	private Image image;
	private String fileReference;
	
	public DiscoverHeadImage(final Resource resource) {
		this.image = new Image(resource);
		
		this.image.addCssClass(DropTarget.CSS_CLASS_PREFIX + "image");
		this.image.addCssClass("desktop tablet");
	}
	
	public Image getImage() {
		return this.image;
	}
	
}
