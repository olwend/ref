package uk.ac.nhm.nhm_www.core.services;

import java.util.Set;
import java.util.TreeSet;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.SlingException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.commons.jcr.JcrConstants;

import uk.ac.nhm.nhm_www.core.componentHelpers.ScientistProfileHelper;
import uk.ac.nhm.nhm_www.core.model.science.Scientist;

@Component(label = "Natural History Museum Scientists Groups Service", description = "Service used to get information related with Scientists Groups.", metatype = true, immediate = true)
@Service(value = ScientistsGroupsService.class)
@Properties(value = {
	@Property(name = "service.pid", value = "uk.ac.nhm.nhm_www.core.delivery.service.ScientistsGroupsService", propertyPrivate = false)
})
public class ScientistsGroupsService {
	
	private static final Logger LOG = LoggerFactory.getLogger(ScientistsGroupsService.class);
	
	public static final String GROUP_NAME_PROPERTY_NAME = "groupName";
	public static final String SCIENTISTS_PROPERTY_NAME = "scientists";
	
    public String getGroupName(final Resource scientistResource) {
    	final ValueMap groupProperties = this.getGroupProperties(scientistResource);
    	
    	if (groupProperties == null) {
    		return "";
    	}
    	
    	return groupProperties.get(GROUP_NAME_PROPERTY_NAME, "");
    }
    
    public Set<Scientist> getGroupScientists(final Resource scientistResource) {
    	final Set<Scientist> result = new TreeSet<>();
    	
    	final ValueMap groupProperties = this.getGroupProperties(scientistResource);
    	
    	if (groupProperties == null) {
    		return result;
    	}
    	
    	final String[] scientistPaths = groupProperties.get(SCIENTISTS_PROPERTY_NAME, String[].class);
    	
    	if (scientistPaths == null) {
    		return result;
    	}
    	
    	final ResourceResolver resourceResolver = scientistResource.getResourceResolver();
    	
    	for (final String scientistPath : scientistPaths) {
    		try {
    			final Resource groupScientistResource = resourceResolver.getResource(scientistPath + "/" + JcrConstants.JCR_CONTENT);
    			
    			if (groupScientistResource == null) {
    				continue;
    			}
    			
    			final ScientistProfileHelper scientist = new ScientistProfileHelper(groupScientistResource);
    			
    			final Scientist scientistElement = new Scientist(scientist.getFirstName() + " " + scientist.getLastName(), scientistPath);
    			
    			result.add(scientistElement);
    		
    		} catch (final SlingException e) {
    			LOG.error("Error getting the Scientist " + scientistPath, e);
    		}
    	}
    	
    	return result;
    }
    
    private ValueMap getGroupProperties(final Resource scientistResource) {
    	final ScientistProfileHelper scientist = new ScientistProfileHelper(scientistResource);
    	
    	if (!scientist.hasGroup()) {
    		return null;
    	}
    	
    	final ResourceResolver resourceResolver = scientistResource.getResourceResolver();
    	
    	final Resource groupResource = resourceResolver.getResource(scientist.getGroupPath());
    	
    	if (groupResource == null) {
    		return null;
    	}
    	
    	return groupResource.adaptTo(ValueMap.class);
    }
	
}
