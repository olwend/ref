package uk.ac.nhm.core.model.slingModels.imagePage;

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
import javax.jcr.RepositoryException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Model;
import org.joda.time.DateTime;
import org.joda.time.MutableDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;

@Model(adaptables = SlingHttpServletRequest.class)
public class ImagePage {

	private static final Logger LOG = LoggerFactory.getLogger(ImagePage.class);

	@Inject
	ValueMap properties;
	
	@Inject
	ResourceResolver resourceResolver;
	
	@Inject
	SlingHttpServletRequest request;
	
	public static final String HUB_TAG		= "hubTag";
	public static final String OTHER_TAGS	= "otherTags";
	
	private String author = null;
	private String datePublished = null;
	private String dateLastUpdated = null;
	private String introductionText = null;
	private String title = null;

	private Map<String, String> hubTag = null;
	private List<Map<String, String>> tagList = null;
//	private List<Map<String, String>> imagePageItems = null;
	
	@PostConstruct
	protected void init() throws JSONException, PathNotFoundException, RepositoryException {
		
		TagManager tagMgr = resourceResolver.adaptTo(TagManager.class);
		
		//Set hub tag name
		if(this.properties.get(HUB_TAG) != null) {
			String[] hubTags = this.properties.get(HUB_TAG, String[].class);
			if(hubTags.length > 0) {
				Tag tag = tagMgr.resolve(hubTags[0]);
				Map<String, String> tagMap = new HashMap<String, String>();
				if(tag!=null) {
					tagMap.put("title", tag.getTitle().toUpperCase());
					tagMap.put("path", tag.getDescription());
					this.setHubTag(tagMap);	
				}
			} 
		} 
		
		//Get other tags
		List<Map<String, String>> tagList = new ArrayList<Map<String, String>>();
		
		if(this.properties.get(OTHER_TAGS) != null) {
			String[] otherTags = this.properties.get(OTHER_TAGS, String[].class);
			if(otherTags.length > 0) {
				for(int i=0; i<otherTags.length; i++) {
					Tag tag = tagMgr.resolve(otherTags[i]);
					Map<String, String> tagMap = new HashMap<String, String>();
					if(tag!=null) {
						tagMap.put("title", tag.getTitle());
						tagMap.put("path", tag.getDescription());
						tagList.add(tagMap);	
					}
				}
			}
		}
		
		this.setTagList(tagList);
		
		if(properties.get("author") != null) this.setAuthor("By " + properties.get("author", String.class));
		if(properties.get("introduction") != null) this.setIntroductionText(properties.get("introduction", String.class));
		
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
		
//		List<Map<String, String>> itemsList = new ArrayList<Map<String, String>>();
//		
//		String imagePathItemsPath = request.getResource().getPath() + "/imagepageitems";
//		LOG.error(imagePathItemsPath);
//		Node itemsNode = resourceResolver.getResource(imagePathItemsPath).adaptTo(Node.class);
//		NodeIterator itemsNodeIterator = itemsNode.getNodes();
//
//		int i = 1;
//		
//		while(itemsNodeIterator.hasNext()) {
//			Node node = itemsNodeIterator.nextNode();
//			
//			Map<String, String> imagePageItemMap = new HashMap<String, String>();
//
//			String rowType = node.getProperty("components").getString();
//			LOG.error(rowType);
//			String row = "imagepageitems/row" + i;
//			imagePageItemMap.put("row", row);
//			imagePageItemMap.put("rowType", rowType);
//			
//			itemsList.add(imagePageItemMap);
//			i++;
//		}
//		
//		this.setImagePageItems(itemsList);
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

	public String getIntroductionText() {
		return introductionText;
	}

	public void setIntroductionText(String introductionText) {
		this.introductionText = introductionText;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Map<String, String> getHubTag() {
		return hubTag;
	}

	public void setHubTag(Map<String, String> hubTag) {
		this.hubTag = hubTag;
	}

	public List<Map<String, String>> getTagList() {
		return tagList;
	}

	public void setTagList(List<Map<String, String>> tagList) {
		this.tagList = tagList;
	}

//	public List<Map<String, String>> getImagePageItems() {
//		return imagePageItems;
//	}
//
//	public void setImagePageItems(List<Map<String, String>> imagePageItems) {
//		this.imagePageItems = imagePageItems;
//	}

}
