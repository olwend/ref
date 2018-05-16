package uk.ac.nhm.core.model.slingModels.imageGallery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.Property;

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
	
	private String components = null;
	private List<Map<String, String>> imagePageItems = null;
	
	@PostConstruct
	protected void init() throws JSONException {
		List<Map<String, String>> itemsList = new ArrayList<Map<String, String>>();
		
		this.setComponents(properties.get("carouselItems", String.class));

		String[] carouselItems = properties.get("carouselItems", String[].class);
		String carouselItemsString = "[";
		
		for(int i=0; i<carouselItems.length; i++) {
			Map<String, String> imagePageItemMap = new HashMap<String, String>();
			
			JSONObject jsonObject = new JSONObject(carouselItems[i]);
			imagePageItemMap.put("type", "nhmwww/components/functional/imagepage/" + jsonObject.getString("components"));
			imagePageItemMap.put("value", jsonObject.getString("itemHeading"));
			String par = "carouselItems/par" + (i + 1);
			imagePageItemMap.put("par", par);
			itemsList.add(imagePageItemMap);
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
