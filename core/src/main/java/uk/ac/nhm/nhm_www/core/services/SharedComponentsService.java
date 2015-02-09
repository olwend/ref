package uk.ac.nhm.nhm_www.core.services;

import java.util.ArrayList;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.jcr.api.SlingRepository;
import org.apache.sling.jcr.resource.JcrResourceConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.nhm.nhm_www.core.model.SharedComponent;


@Component (label = "Youtube Collector Service", description = "Service to get information of the Videos on the Channels configured.", metatype = true, immediate = true)
@Service (value = SharedComponentsService.class)
@Properties(value = {
		@Property(name = "service.pid", value = "uk.ac.nhm.aem.training.sharedcomponents.services.SharedComponentsService", propertyPrivate = false),
        @Property(name = "service.description", value = "Service to look for information about shared components.", propertyPrivate = false)
})
public class SharedComponentsService {
	protected static final Logger LOG = LoggerFactory.getLogger(SharedComponentsService.class);

	private static final String SHARE_PROPERTY = "shareConfirmation";
	private static final String SHARE_TITLE_PROPERTY = "shareTitle";
	private static final String SHARE_CONTENT_SOURCE_PROPERTY = "contentSource";
	
	@Reference
	protected SlingRepository repository;
	
	public List<SharedComponent> getSharedComponents(final String path) {
		final List<SharedComponent> result = new ArrayList<SharedComponent>();
		
		if (!path.startsWith("/content/")) {
			return result;
		}
		
		final String rootPath = path.substring(0, path.indexOf("/", "/content/".length()));
		
		final String selectString = "SELECT * FROM [nt:unstructured] AS c WHERE ISDESCENDANTNODE([" + rootPath + "]) AND c." + SHARE_PROPERTY + " = 'true' AND c." + SHARE_TITLE_PROPERTY + " IS NOT NULL";
		
		try {
			final Session adminSession = repository.loginService("searchService", null);
			
			try {
				final QueryManager queryMgr = adminSession.getWorkspace().getQueryManager();
				final Query query = queryMgr.createQuery(selectString,Query.JCR_SQL2);
				final QueryResult queryResult = query.execute();
				
				final NodeIterator iterator = queryResult.getNodes();
				
				while (iterator.hasNext()) {
					final Node node = iterator.nextNode();
					
					result.add(new SharedComponent(node.getPath(), node.getProperty(SHARE_TITLE_PROPERTY).getString()));			
				}
			} finally {
				if (adminSession != null && adminSession.isLive()) {
					adminSession.logout();
				}
			}
		} catch (final RepositoryException e) {
			LOG.error("Error recovering shared components on the repository.", e);
			return new ArrayList<SharedComponent>();
		}
		
		return result;
	}
	
	/* Get all the components with a specific path configured as content Source*/
	public List<String> getSharedComponentConfigurations(final String path) {
		final List<String> result = new ArrayList<String>();
		
		if (!path.startsWith("/content/")) {
			return result;
		}
		
		final String rootPath = path.substring(0, path.indexOf("/", "/content/".length()));
		
		final String selectString = "SELECT * FROM [nt:unstructured] AS c WHERE ISDESCENDANTNODE([" + rootPath + "]) AND c." + SHARE_CONTENT_SOURCE_PROPERTY + " = '" + path + "'";
		
		try {
			final Session adminSession = repository.loginService("searchService", null);
			
			try {
				final QueryManager queryMgr = adminSession.getWorkspace().getQueryManager();
				final Query query = queryMgr.createQuery(selectString,Query.JCR_SQL2);
				final QueryResult queryResult = query.execute();
				
				final NodeIterator iterator = queryResult.getNodes();
				
				while (iterator.hasNext()) {
					final Node node = iterator.nextNode();
					
					result.add(node.getPath());			
				}
			} finally {
				if (adminSession != null && adminSession.isLive()) {
					adminSession.logout();
				}
			}
		} catch (final RepositoryException e) {
			LOG.error("Error recovering the references to a specific shared component.", e);
			return new ArrayList<String>();
		}
		
		return result;
	}
	
	
	/* Get the ResourceType of a component if the path is of a component and it is shared */
	public String getResourceType(final String path) {
		try {
			final Session adminSession = repository.loginService("searchService", null);
			
			try {
				if (!path.startsWith("/content/")) {
					return null;
				}
				
				if (!adminSession.nodeExists(path)) {
					return null;
				}
				
				final Node node = adminSession.getNode(path);
				if (!(node.hasProperty(SHARE_PROPERTY) && node.hasProperty(JcrResourceConstants.SLING_RESOURCE_TYPE_PROPERTY))) {
					return null;
				}
				
				return node.getProperty(JcrResourceConstants.SLING_RESOURCE_TYPE_PROPERTY).getString();
			
			} finally {
				if (adminSession != null && adminSession.isLive()) {
					adminSession.logout();
				}
			}
		} catch (final RepositoryException e) {
			LOG.error("Error recovering the resource type of a shared component.", e);
			return null;
		}
	}
	
}



	
	