package uk.ac.nhm.nhm_www.core.services;

import java.util.List;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;

import uk.ac.nhm.nhm_www.core.model.DynamicApp.PageResourceArray;

import com.day.cq.wcm.api.Page;

public interface DynamicAppPageRenderingService {
	public Page getPage(final SlingHttpServletRequest request, String itemID);

	public JSONObject getJSON(Page page, final ResourceResolver resolver, final SlingHttpServletRequest request) throws JSONException;
}
