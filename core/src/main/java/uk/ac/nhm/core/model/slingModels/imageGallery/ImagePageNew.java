package uk.ac.nhm.core.model.slingModels.imageGallery;

import java.text.DateFormatSymbols;
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
import org.joda.time.DateTime;
import org.joda.time.MutableDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Model(adaptables = SlingHttpServletRequest.class)
public class ImagePageNew {

	private static final Logger LOG = LoggerFactory.getLogger(ImagePageNew.class);

	@Inject
	ValueMap properties;
	
	@Inject
	ResourceResolver resourceResolver;
	
	@Inject
	SlingHttpServletRequest request;
	
	private String author= null;
	private String components = null;
	private String datePublished = null;
	private String dateLastUpdated = null;
	private String title = null;
			
	private List<Map<String, String>> imagePageItems = null;
	
	@PostConstruct
	protected void init() throws JSONException, PathNotFoundException, RepositoryException {
		
		if(properties.get("author") != null) this.setAuthor("By " + properties.get("author", String.class));
		
		//Dates
		DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("yy/MM/dd");
		
		if(properties.get("datepublished") != null) {
			DateTime dt = dateFormatter.parseDateTime(properties.get("datepublished", String.class));
			MutableDateTime mdt = dt.toMutableDateTime();
			this.setDatePublished("First published " + mdt.getDayOfMonth() + " " + getMonth(mdt.getMonthOfYear()) + " " + mdt.getYear());
		} 
		
		if(properties.get("datelastupdated") != null && (!properties.get("datelastupdated", String.class).equals(properties.get("datepublished", String.class)))) {
			DateTime dt = dateFormatter.parseDateTime(properties.get("datelastupdated", String.class));
			MutableDateTime mdt = dt.toMutableDateTime();
			this.setDateLastUpdated("Last updated " + mdt.getDayOfMonth() + " " + getMonth(mdt.getMonthOfYear()) + " " + mdt.getYear());
		}
		
		this.setTitle(properties.get("jcr:title", String.class));
		
		List<Map<String, String>> itemsList = new ArrayList<Map<String, String>>();
		
		String imagePathItemsPath = request.getResource().getPath() + "/imagepageitems";
		LOG.error(imagePathItemsPath);
		Node itemsNode = resourceResolver.getResource(imagePathItemsPath).adaptTo(Node.class);
		NodeIterator itemsNodeIterator = itemsNode.getNodes();

		int i = 1;
		
		while(itemsNodeIterator.hasNext()) {
			Node childNode = itemsNodeIterator.nextNode();
			
			Map<String, String> imagePageItemMap = new HashMap<String, String>();

			String row = "imagepageitems/row" + i;
			imagePageItemMap.put("row", row);
			
			itemsList.add(imagePageItemMap);
			i++;
		}
		
		this.setImagePageItems(itemsList);
	}

	public String getMonth(int month) {
		return new DateFormatSymbols().getMonths()[month-1];
	}
	
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getComponents() {
		return components;
	}

	public void setComponents(String components) {
		this.components = components;
	}

	public String getDatePublished() {
		return datePublished;
	}

	public void setDatePublished(String datePublished) {
		this.datePublished = datePublished;
	}

	public String getDateLastUpdated() {
		return dateLastUpdated;
	}

	public void setDateLastUpdated(String dateLastUpdated) {
		this.dateLastUpdated = dateLastUpdated;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Map<String, String>> getImagePageItems() {
		return imagePageItems;
	}

	public void setImagePageItems(List<Map<String, String>> imagePageItems) {
		this.imagePageItems = imagePageItems;
	}

}
