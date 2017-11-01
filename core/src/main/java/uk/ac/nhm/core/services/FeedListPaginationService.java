package uk.ac.nhm.core.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.commons.json.JSONObject;

import uk.ac.nhm.core.model.TaggedFeedListElement;

public interface FeedListPaginationService {
	public List<Object> getJCRItems(HttpServletRequest request, String path,Integer pageNumber) throws LoginException;

	public JSONObject getJSON(List<Object> objects, Integer pageNumber,Integer pageSize, final ResourceResolver resolver,final SlingHttpServletRequest request);

	public List<TaggedFeedListElement> searchCQ(final SlingHttpServletRequest request, String rootPath, String tags, String resourceType);
	
	public String[] getCqTags();

	public void setCqTags(String[] cqTags);

}
