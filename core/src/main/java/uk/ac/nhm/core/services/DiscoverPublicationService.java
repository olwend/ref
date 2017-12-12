package uk.ac.nhm.core.services;

import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.jcr.api.SlingRepository;
import org.apache.sling.settings.SlingSettingsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.designer.Designer;
import com.day.cq.wcm.api.designer.Style;

/**
 * Service to work with the Discover Publication Components on the repository. Ordering its tags and configuring the Creation Date of the component.
 */
@Component(metatype = false, immediate = true)
@Service(value = DiscoverPublicationService.class)
@Properties(value = {
	@Property(name = "service.pid", value = "uk.ac.nhm.core.delivery.service.DiscoverPublicationService", propertyPrivate = false)
})
public class DiscoverPublicationService {
	
	private static final Logger LOG = LoggerFactory.getLogger(DiscoverPublicationService.class);
	
	/** SlingSettingsService used to recover the configuration of the server and check for its {@link SlingSettingsService#getRunModes() run modes}. */
    @Reference
    private SlingSettingsService slingSettingsService;
    
    @Reference
    private SlingRepository repository;
	
    /**
     * Create the attribute &quot;<b>jcr:created</b>&quot; attribute on the Discover Publication Component node
     * if this attribute does not exist to added its created date.
     * @param resource {@link Resource Sling Resource} representing the Discover Publication Component. 
     */
    public void creationDate(final Resource resource) {
    	/* Do not continue if the server is not an author server. */
    	if (!this.isInAuthor()) {
			return;
		}
    	
    	/* Do not continue if the Resource has already a creation date. */
    	final ValueMap properties = resource.adaptTo(ValueMap.class);
    	if (properties != null && properties.get("jcr:created", Calendar.class) != null) {
    		return;
    	}
    	
    	try {
			final Session session = repository.loginService("searchService", null);
			try {
				final String nodePath = resource.getPath();
	
				if (!session.nodeExists(nodePath)) {
					createNode(nodePath, resource.getName(), session);
				} 
				final Node node = session.getNode(resource.getPath());
				
				node.setProperty("jcr:created", Calendar.getInstance());
				
				session.save();
			} finally {
				if (session.isLive()) {
					session.logout();
				}
			}
		} catch (RepositoryException e) {
			LOG.error("Error accessing to the repository.", e);
		}
    }
    
    /**
     * Order the Tags Attribute to put the Discover Tag (Configured on Design) the first tag.
     * @param resource {@link Resource Sling Resource} representing the Discover Publication Component.
     */
	public void orderTags(final Resource resource) {
		/* Do not continue if the server is not an author server. */
		if (!this.isInAuthor()) {
			return;
		}
		
		final Designer designer = resource.getResourceResolver().adaptTo(Designer.class);

    	final Style style = designer.getStyle(resource);
    	/* The Resource has to have a Design configuration. */
    	if (style == null) {
    		return;
    	}
    	
    	final String[] designTags = style.get("cq:tags", String[].class);
    	/* Do not continue if the design does not have the tags attribute configured. */
    	if (designTags == null) {
    		return;
    	}
		
    	final ValueMap properties = resource.adaptTo(ValueMap.class);
    	
    	final String[] componentOldTags = properties.get("cq:tags", String[].class);
    	final String[] componentTags;
    	if (componentOldTags == null || componentOldTags.length == 0) {
    		componentTags = designTags;
    	} else {
    		final List<String> componentOldTagsList = new LinkedList<String>(Arrays.asList(componentOldTags));
    		
    		for (final String tag : designTags) {
    			final int index = componentOldTagsList.indexOf(tag);
    			if (index > -1) {
    				componentOldTagsList.remove(index);
    			}
    		}
    		
    		componentTags = ArrayUtils.addAll(designTags, componentOldTagsList.toArray(new String[componentOldTagsList.size()]));
    	}
		
		try {
			final Session session = repository.loginService("searchService", null);
			try {
				final String nodePath = resource.getPath();
	
				if (!session.nodeExists(nodePath)) {
					createNode(nodePath, resource.getName(), session);
				} 
				final Node node = session.getNode(resource.getPath());
				
				node.setProperty("cq:tags", componentTags);
				
				session.save();
			} finally {
				if (session.isLive()) {
					session.logout();
				}
			}
		} catch (RepositoryException e) {
			LOG.error("Error accessing to the repository.", e);
		}
		
	}
	
	/**
     * Check if the system is in Author Server Mode.
     */
    private boolean isInAuthor() {
        /* Activate listener only in author environment. */
        return this.slingSettingsService.getRunModes().contains("author");
    }
    
    /**
	 * Created the path necessary to a Node indicated by a Path.
	 * @param path Path where the node will be located.
	 * @param node {@link Node Node} used as reference to create the Node, taking from it the resource type.
	 * @param session {@link Session Session} used to make the changes in the repository.
	 * @throws RepositoryException Occurs if there is some error acceding to the repository.
	 */
	private static synchronized void createNode(final String path, final String nodeName, final Session session) throws RepositoryException {
		final String parentPath 	= path.substring(0, path.lastIndexOf("/"));
		final String parentNodeName = path.substring(path.lastIndexOf("/") + 1);

		if  (!session.nodeExists(parentPath) && parentPath.contains("/") && !parentPath.endsWith("jcr:content")) {
			createNode(parentPath, parentNodeName, session);
		}

		if (!session.nodeExists(parentPath)) {
			throw new RepositoryException("Impossible to create node: " + parentPath);
		}

		final Node parentNode = session.getNode(parentPath);

		parentNode.addNode(nodeName, "nt:unstructured");
	}
	
}
