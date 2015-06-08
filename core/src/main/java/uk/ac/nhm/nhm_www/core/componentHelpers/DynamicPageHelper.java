package uk.ac.nhm.nhm_www.core.componentHelpers;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;

import com.day.cq.wcm.api.Page;

public class DynamicPageHelper {
	private Resource resource;
	private ValueMap properties;
	private String pageIntroduction;
	private String legacyApp;
	
	public DynamicPageHelper(Resource resource,ValueMap properties)
	{
//		this.image = getProperties().get("image", String.class);
		setResource(resource);
		setProperties(properties);
		init();
	}

	public DynamicPageHelper(Page page) {
		Resource pageResource = page.adaptTo(Resource.class);
		setResource(pageResource);
		setProperties(page.getProperties());;
		init();
	}
	private void init() {
		String pageIntroduction = "";
		if(getProperties().get("pageIntroduction") !=null) {
			pageIntroduction = getProperties().get("pageIntroduction", String.class);
		}
		setPageIntroduction(pageIntroduction);	
		String legacyApp = "";
		if(getProperties().get("legacy-app") !=null) {
			legacyApp = getProperties().get("legacy-app", String.class);
		}
		setLegacyApp(legacyApp);
		
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public ValueMap getProperties() {
		return properties;
	}

	public void setProperties(ValueMap properties) {
		this.properties = properties;
	}

	public String getPageIntroduction() {
		return pageIntroduction;
	}

	public void setPageIntroduction(String pageIntroduction) {
		this.pageIntroduction = pageIntroduction;
	}

	public String getLegacyApp() {
		return legacyApp;
	}

	public void setLegacyApp(String legacyApp) {
		this.legacyApp = legacyApp;
	}
	
	
	
	
	
}
