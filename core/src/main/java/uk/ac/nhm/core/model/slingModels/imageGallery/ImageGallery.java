package uk.ac.nhm.core.model.slingModels.imageGallery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.PathNotFoundException;
import javax.jcr.Property;
import javax.jcr.RepositoryException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Model;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Model(adaptables = SlingHttpServletRequest.class)
public class ImageGallery {

	private static final Logger LOG = LoggerFactory.getLogger(ImageGallery.class);

	@Inject
	ValueMap properties;
	
	@Inject
	ResourceResolver resourceResolver;
	
	@Inject
	SlingHttpServletRequest request;
	
	private String components = null;
	private List<Map<String, String>> imagePageItems = null;
	
	@PostConstruct
	protected void init() throws JSONException, PathNotFoundException, RepositoryException {
		List<Map<String, String>> itemsList = new ArrayList<Map<String, String>>();
		
		String imagePathItemsPath = request.getResource().getPath() + "/imagePageItems";
		Node itemsNode = resourceResolver.getResource(imagePathItemsPath).adaptTo(Node.class);
		NodeIterator itemsNodeIterator = itemsNode.getNodes();
		
		int i = 0;
		
		while(itemsNodeIterator.hasNext()) {
			Node childNode = itemsNodeIterator.nextNode();
			Map<String, String> imagePageItemMap = new HashMap<String, String>();
			
			String row = "imagePageItems/row" + (i + 1);
			imagePageItemMap.put("row", row);
						
			itemsList.add(imagePageItemMap);
			
			i++;
		}
		
		this.setImagePageItems(itemsList);
	}

	public String getComponents() {
		return components;
	}

	public void setComponents(String components) {
		this.components = components;
	}

	public List<Map<String, String>> getImagePageItems() {
		return imagePageItems;
	}

	public void setImagePageItems(List<Map<String, String>> imagePageItems) {
		this.imagePageItems = imagePageItems;
	}

}
