package uk.ac.nhm.nhm_www.core.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.commons.json.JSONObject;

import uk.ac.nhm.nhm_www.core.model.DatedAndTaggedFeedListElement;

public interface FeedListPaginationService {
	public List<Object> getJCRItems(HttpServletRequest request, String path,Integer pageNumber) throws LoginException;

	public JSONObject getJSON(List<Object> objects, Integer pageNumber,Integer pageSize, final ResourceResolver resolver,final SlingHttpServletRequest request);

	public List<DatedAndTaggedFeedListElement> searchCQ(final SlingHttpServletRequest request);
	
	public String[] getCqTags();

	public void setCqTags(String[] cqTags);

}
