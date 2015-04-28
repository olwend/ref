package uk.ac.nhm.nhm_www.core.componentHelpers;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;

/**
 * Discover List Component Helper Class.
 */
public class NewsListHelper extends DiscoverListHelper{

	/*
	 * Component Properties.
	 */
	private ValueMap properties;
	
	/**
	 * Helper Class Constructor.
	 * @param resource {@link Resource Component Resource}.
	 */
	public NewsListHelper(Resource resource) {
		super(resource);
		this.properties = resource.adaptTo(ValueMap.class);
		// TODO Auto-generated constructor stub
	}

}
