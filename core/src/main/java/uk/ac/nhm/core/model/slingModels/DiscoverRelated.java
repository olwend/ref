package uk.ac.nhm.core.model.slingModels;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.Value;

import org.apache.felix.scr.annotations.Reference;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Model;
import org.joda.time.DateTime;
import org.joda.time.MutableDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.tagging.JcrTagManagerFactory;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;

@Model(adaptables = SlingHttpServletRequest.class)
public class DiscoverRelated {

	private final static Logger LOG = LoggerFactory.getLogger(DiscoverRelated.class);
	
	@Inject
	ValueMap properties;
	
	@Inject
	ResourceResolver resourceResolver;
	
	@Reference
	private JcrTagManagerFactory jcrTagManagerFactory;
	private TagManager tagManager;
	
	private List<Map<String, String>> pageList = null;

	@PostConstruct
	protected void init() {
		List<Map<String, String>> itemList = new ArrayList<Map<String, String>>();
		String[] posts = null;

		if(properties.get("posts") != null) {
			posts = properties.get("posts", String[].class);
		}
		
		try {
			tagManager = resourceResolver.adaptTo(TagManager.class);

			for(int i=0; i<posts.length; i++) {
				Map<String, String> itemMap = new HashMap<String, String>();
				
				String pagePath = posts[i];
				Resource resource = resourceResolver.getResource(pagePath);
				Node node = resource.adaptTo(Node.class);
				
				if(node.hasProperty("jcr:content/jcr:title")) {
		    		itemMap.put("title", node.getProperty("jcr:content/jcr:title").getString());
		    	} else {
		    		itemMap.put("title", node.getName());
		    	}
		    	
		    	if(node.hasProperty("jcr:content/article/snippet")) {
		    		//Snippet string likely to contain html tags - strip them out
		    		String snippet = node.getProperty("jcr:content/article/snippet").getString();
		    		itemMap.put("excerpt", snippet);
		    	} else if(node.hasProperty("jcr:content/jcr:description")) {
		    		itemMap.put("excerpt", node.getProperty("jcr:content/jcr:description").getString());
		    	}
	
		    	//Get image for Article template pages
		    	if(node.getProperty("jcr:content/cq:template").getString().equals("/apps/nhmwww/templates/articlepage")
		    			|| node.getProperty("jcr:content/cq:template").getString().equals("/apps/nhmwww/templates/imagepage")) {
			    	if(node.hasProperty("jcr:content/article/headType")) {
			    		if(node.getProperty("jcr:content/article/headType").getString().equals("image")) {
			    			if(node.hasProperty("jcr:content/article/altFileReference")) {
			    				String fileReference = node.getProperty("jcr:content/article/altFileReference").getString();
			    				String extension = "." + fileReference.substring(fileReference.lastIndexOf(".") + 1);
			    				
								itemMap.put("context", fileReference);
								itemMap.put("extension", extension);
								itemMap.put("imageType", "image");
			    			} else if(node.hasProperty("jcr:content/article/image/fileReference")) {
			    				String fileReference = node.getProperty("jcr:content/article/image/fileReference").getString();
			    				String extension = "." + fileReference.substring(fileReference.lastIndexOf(".") + 1);
			    				
								itemMap.put("context", fileReference);
								itemMap.put("extension", extension);
								itemMap.put("imageType", "image");
							}
			    		} else if(node.getProperty("jcr:content/article/headType").getString().equals("video")) {
			    			if(node.hasProperty("jcr:content/article/video/youtube")) {
			    				String youtubeImagePath = "http://img.youtube.com/vi/" + node.getProperty("jcr:content/article/video/youtube").getString() + "/mqdefault.jpg";
					    		itemMap.put("imagePath", youtubeImagePath);
					    		itemMap.put("imageType", "video");
					    	}
			    		}
			    	}
		    	}
	
		    	//Get publish date
		    	DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("yy/MM/dd");
		    	
		    	if(node.hasProperty("jcr:content/article/datepublished")) {
		    		DateTime dt = dateFormatter.parseDateTime(node.getProperty("jcr:content/article/datepublished").getString());
					MutableDateTime mdt = dt.toMutableDateTime();
					String datePublished = mdt.getDayOfMonth() + " " + getMonth(mdt.getMonthOfYear()) + " " + mdt.getYear();
		    		itemMap.put("datepublished", datePublished);
		    	}
	
		    	//Get hub tag
		    	if(node.hasProperty("jcr:content/article/hubTag")) {
		    		Property hubTagsProperty = node.getProperty("jcr:content/article/hubTag");
		    		Value[] hubTags = hubTagsProperty.getValues();
					if(hubTags.length > 0) {
						Tag tag = tagManager.resolve(hubTags[0].getString());
						itemMap.put("hubtag", tag.getTitle().toUpperCase());
					}
		    	}
				
				itemMap.put("path", resource.getPath());
				
				itemList.add(itemMap);
			}
		} catch (Exception e) {
			LOG.error("Error with exception: ", e);
		}
		
		this.setPageList(itemList);
	}
	
	public String getMonth(int month) {
		return new DateFormatSymbols().getMonths()[month-1];
	}
	
	public List<Map<String, String>> getPageList() {
		return pageList;
	}

	public void setPageList(List<Map<String, String>> pageList) {
		this.pageList = pageList;
	}
	
}
