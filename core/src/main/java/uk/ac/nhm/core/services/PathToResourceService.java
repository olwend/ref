package uk.ac.nhm.core.services;

import org.apache.felix.scr.annotations.Reference;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.felix.scr.annotations.Component;

import com.day.cq.dam.api.Asset;
import com.day.cq.wcm.foundation.Image;

@Component(metatype = false)

public class PathToResourceService {

	@Reference 
	ResourceResolverFactory resourceResolverFactory;
	
	public Asset getAsset(String path, SlingHttpServletRequest request) {
		ResourceResolver resourceResolver = request.getResourceResolver();
		Resource resource = resourceResolver.getResource(path);
		
		Image image = new Image(resource);
		
		Asset asset = image.adaptTo(Asset.class);
		
		return asset;
	}
	
	public Image getImage(String path, SlingHttpServletRequest request) {
		ResourceResolver resourceResolver = request.getResourceResolver();
		Resource resource = resourceResolver.getResource(path);
		
		Image image = new Image(resource);

		return image;
	}
	
	public Resource getResource(String path, SlingHttpServletRequest request) {
		ResourceResolver resourceResolver = request.getResourceResolver();
		Resource resource = resourceResolver.getResource(path);
		
		return resource;
	}
}