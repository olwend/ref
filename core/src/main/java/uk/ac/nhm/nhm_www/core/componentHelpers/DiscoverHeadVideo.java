package uk.ac.nhm.nhm_www.core.componentHelpers;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;

public class DiscoverHeadVideo {
	private static final String YOUTUBE_ATTRIBUTE_NAME = "youtube";
	
	private String youTubeVideoId;
	
	public DiscoverHeadVideo(final Resource resource) {
		final ValueMap properties = resource.adaptTo(ValueMap.class);
		if (properties == null) {
			this.youTubeVideoId = null;
			return;
		}
		
		this.youTubeVideoId = properties.get(YOUTUBE_ATTRIBUTE_NAME, String.class);
	}
	
	public String getYouTubeVideoId() {
		return this.youTubeVideoId;
	}
}
