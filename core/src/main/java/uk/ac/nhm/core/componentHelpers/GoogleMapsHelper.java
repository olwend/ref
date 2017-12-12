package uk.ac.nhm.core.componentHelpers;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ValueMap;

public class GoogleMapsHelper extends HelperBase {
	private ValueMap properties;
	private String mapLocation;
	private boolean initialised = false;
	private String place;
	private String key;
	
	
	public GoogleMapsHelper(SlingHttpServletRequest request)
	{
		this.helperFactory = new HelperFactory(request);
		this.properties = helperFactory.getProperties();
		init();
	}
	
	private void init()
	{
		
		if (this.properties.get("mapLocation", String.class) != null)
		{
			this.mapLocation = this.properties.get("mapLocation",String.class);
		}
		if(mapLocation != null && !mapLocation.isEmpty()) {
			if(mapLocation.equals("southken")) {
				this.place = "Natural+History+Museum,+Cromwell+Road,+London,+United+Kingdom";
				this.key = "AIzaSyDQx5EO6WcwsOCDuozXsZbaY65NuFZ4mRg";
			} else if(mapLocation.equals("tring")) {
				this.place = "Natural%20History%20Museum%20at%20Tring";
				this.key = "AIzaSyC09eunz5fyve_HGt3DQGRQzEpQT1A1HbA";
			}
		}
		
		this.initialised = true;

	}

	public String getMapLocation() {
		return mapLocation;
	}

	public void setMapLocation(String mapLocation) {
		this.mapLocation = mapLocation;
	}

	public boolean isInitialised() {
		return initialised;
	}

	public void setInitialised(boolean initialised) {
		this.initialised = initialised;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	

}
