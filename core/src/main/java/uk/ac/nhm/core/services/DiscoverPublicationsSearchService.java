package uk.ac.nhm.core.services;

import java.util.List;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.commons.json.JSONObject;

import uk.ac.nhm.core.model.discover.ResourceComponent;

/**
 * Discover Publications Search Service.
 */
public interface DiscoverPublicationsSearchService {

    public List<ResourceComponent> searchCQ(final SlingHttpServletRequest request);
    
    public JSONObject getJSON(final List<ResourceComponent> listResources, final int startIndex, final int numElements) throws LoginException;
    
    public String getCqTags();
    
    public void setCqTags(final String cqTag);
    
    public JSONObject getNextPrevious(final String path);
}
