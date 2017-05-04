package uk.ac.nhm.nhm_www.core.componentHelpers;

import javax.servlet.http.HttpServletRequest;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;

public class DefaultPageHelper {
	
	private static final Logger LOG = LoggerFactory.getLogger(DynamicPageHelper.class);
	
	private Resource resource;
	private ValueMap properties;
	private HttpServletRequest request;
	private Boolean useBanner;
	
	public DefaultPageHelper(Resource resource,ValueMap properties, HttpServletRequest request)	{
		setResource(resource);
		setProperties(properties);
		setRequest(request);
		LOG.error("Scheme"  + request.getScheme());
		init();
	}

	public DefaultPageHelper(Page page) {
		Resource pageResource = page.adaptTo(Resource.class);
		setResource(pageResource);
		setProperties(page.getProperties());;
		init();
	}
	
	private void init() {
		if(getProperties().get("usebanner") != null) {
			this.setUseBanner(getProperties().get("usebanner", false));
		} else {
			this.setUseBanner(false);
		}
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
	
	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public Boolean getUseBanner() {
		return useBanner;
	}

	public void setUseBanner(Boolean useBanner) {
		this.useBanner = useBanner;
	}
}
