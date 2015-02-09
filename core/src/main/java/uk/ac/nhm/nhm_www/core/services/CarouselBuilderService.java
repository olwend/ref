package uk.ac.nhm.nhm_www.core.services;

import java.util.List;

import javax.jcr.AccessDeniedException;
import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.version.VersionException;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.jcr.resource.JcrResourceConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.nhm.nhm_www.core.componentHelpers.CarouselHelper;
import uk.ac.nhm.nhm_www.core.model.CarouselElement;

import com.day.cq.wcm.api.PageManager;

/**
 * Service to work with the Carousel Component on the repository. Building its structure.
 */
@Component(metatype = false, immediate = true)
@Service(value = CarouselBuilderService.class)
@Properties(value = {
	@Property(name = "service.pid", value = "uk.ac.nhm.nhm_www.core.delivery.service.CarouselBuilderService", propertyPrivate = false)
})
public class CarouselBuilderService {
	
	private static final Logger LOG = LoggerFactory.getLogger(CarouselBuilderService.class);
	
	public  static final String FOUNDATION_5_IMAGE_RESOURCE_TYPE = "nhmwww/components/functional/foundation5image";
	public  static final String IMAGE_NODE_NAME_PREFIX 			 = "image_";
	private static final String FILEREFERENCE_ATTRIBUTE 		 = "fileReference";
	private static final String ALT_ATTRIBUTE 				     = "alt";
	
	public void createStructure(final Resource carouselResource, final PageManager pageManager, final ResourceResolver resourceResolver) {
		try {
			final CarouselHelper helper = new CarouselHelper(carouselResource, pageManager, resourceResolver);
			
			final List<CarouselElement> elements = helper.getElements(); 
			
			final Session session = resourceResolver.adaptTo(Session.class);
			final Node carouselNode = carouselResource.adaptTo(Node.class);

			int imageNumber = 0;
			String currentNodeName = IMAGE_NODE_NAME_PREFIX + imageNumber;
			for(CarouselElement element: elements)
			{
				if (element.getIsContentSlide() || (!element.getIsVideo() &&  !element.getIsContentSlide())) {
					final String fileReference = element.getFilename();
					final String alt = element.getAlt();
					
					// TODO: Check if exist the node image and number of image.
					final Resource imageResource = carouselResource.getChild(currentNodeName);
					
					if (imageResource != null) {
						final ValueMap imageProperties = imageResource.adaptTo(ValueMap.class);
						final String resourceTypeProperty = imageProperties.get(JcrResourceConstants.SLING_RESOURCE_TYPE_PROPERTY, String.class);
						final String fileReferenceProperty = imageProperties.get(FILEREFERENCE_ATTRIBUTE, String.class);
						final String altProperty = imageProperties.get(ALT_ATTRIBUTE, String.class);
						
						if (FOUNDATION_5_IMAGE_RESOURCE_TYPE.equals(resourceTypeProperty) &&
								fileReference.equals(fileReferenceProperty) &&
								alt.equals(altProperty)) {
							imageNumber++;
							currentNodeName = IMAGE_NODE_NAME_PREFIX + imageNumber;
							continue;
						}
					}
					// TODO: Save the new information
					this.saveNodeInformation(carouselNode, currentNodeName, fileReference, alt);
					
					imageNumber++;
					currentNodeName = IMAGE_NODE_NAME_PREFIX + imageNumber;
				}
			}
			
			// TODO: Remove the rest of the images on this node because are not configured.
			while (carouselResource.getChild(currentNodeName) != null) {
				this.removeNode(carouselNode, currentNodeName);
				
				imageNumber++;
				currentNodeName = IMAGE_NODE_NAME_PREFIX + imageNumber;
			}
			
			session.save();
			
		} catch (final JSONException e) {
			LOG.error("JSON Exception: ", e);
		} catch (final RepositoryException e) {
			LOG.error("Repository Exception: ", e);
		}
	}
	
	private void saveNodeInformation(final Node carouselNode, final String imageNodeName, final String fileReference, final String alt) throws RepositoryException {
		final Node imageNode;
		if (carouselNode.hasNode(imageNodeName)) {
			imageNode = carouselNode.getNode(imageNodeName);
		} else {
			imageNode = carouselNode.addNode(imageNodeName);
		}
		
		imageNode.setProperty(JcrResourceConstants.SLING_RESOURCE_TYPE_PROPERTY, FOUNDATION_5_IMAGE_RESOURCE_TYPE);
		imageNode.setProperty(FILEREFERENCE_ATTRIBUTE, fileReference);
		imageNode.setProperty(ALT_ATTRIBUTE, alt);
	}
	
	private void removeNode(final Node carouselNode, final String nodeName) throws AccessDeniedException, PathNotFoundException, VersionException, LockException, ConstraintViolationException, RepositoryException {
		if (carouselNode.hasNode(nodeName)) {
			final Node imageNode = carouselNode.getNode(nodeName);
			imageNode.remove();
		}
	}
	
}
