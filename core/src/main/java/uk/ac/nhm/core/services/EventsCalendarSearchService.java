package uk.ac.nhm.core.services;

import java.util.ArrayList;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;

import com.day.cq.wcm.api.Page;

public interface EventsCalendarSearchService {

	public ArrayList<Page> getPages();

	public JSONObject getJSON(Page page, final ResourceResolver resolver, final SlingHttpServletRequest request) throws JSONException;
}