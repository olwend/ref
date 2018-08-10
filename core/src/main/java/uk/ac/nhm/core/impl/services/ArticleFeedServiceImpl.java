package uk.ac.nhm.core.impl.services;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.Session;
import javax.jcr.Value;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.jcr.api.SlingRepository;
import org.joda.time.DateTime;
import org.joda.time.MutableDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.day.cq.tagging.JcrTagManagerFactory;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;

import uk.ac.nhm.core.services.ArticleFeedService;
import uk.ac.nhm.core.utils.TextUtils;

/**
 * @author alisp2
 *
 * Service that builds and runs a query based on user-selected parameters
 * in an Article Feed component dialog. Returns a List object containing 
 * Map objects representing properties from one or more article pages.
 * 
 */

@Component
@Service
public class ArticleFeedServiceImpl implements ArticleFeedService {

	private final static Logger LOG = LoggerFactory.getLogger(ArticleFeedServiceImpl.class);
	
	@Reference
	private SlingRepository repository;
	
	@Reference
	private JcrTagManagerFactory jcrTagManagerFactory;
	private TagManager tagManager;
	
	@Reference
	private QueryBuilder builder;

	@Override
	public List<Hit> getArticleNodes(String rootPath, String[] tags, String order, String tagsOperator, String limit, ResourceResolver resourceResolver) {

		List<Hit> nodes = new ArrayList<Hit>();
		
		try {
			final Session session = repository.loginService("searchService", null);
			tagManager = resourceResolver.adaptTo(TagManager.class);
			
			Map<String, String> queryMap = new HashMap<String, String>();

			//Path
			queryMap.put("path", rootPath);
		    queryMap.put("type", "cq:Page");

		    //Tags
		    if(tags != null) {
		    	
		    	for(int i=0; i<tags.length; i++) {
		    		String tagid = "group." + i + "_tagid";
		    		String tagidproperty = "group." + i + "_tagid.property";
				    queryMap.put(tagid, tags[i]);
				    queryMap.put(tagidproperty, "jcr:content/article/cq:tags");
		    	}
		    	if(!tagsOperator.equals(null) && tagsOperator != null) {
			    	if(tagsOperator.equals("and")) queryMap.put("group.p.or", "false");
			    	if(tagsOperator.equals("or")) queryMap.put("group.p.or", "true");
		    	}
		    }

		    //Order by
		    if(!order.equals(null) && order != null) {
		    	switch(order) {
		    		case "datemodified" :
		    			queryMap.put("orderby", "@jcr:content/article/datelastupdated");
		    			queryMap.put("orderby.sort", "desc");
		    			break;
		    		case "datecreated" :
		    			queryMap.put("orderby", "@jcr:content/article/datepublished");
		    			queryMap.put("orderby.sort", "desc");
		    			break;
		    	}
		    }

		    //Query
		    Query query = builder.createQuery(PredicateGroup.create(queryMap), session);
		    
		    query.setStart(0);

		    if(limit != null) {
			    query.setHitsPerPage(Long.parseLong(limit));
		    }

		    SearchResult result = query.getResult();
		    LOG.info(result.getQueryStatement());
		    nodes = result.getHits();

		} catch (Exception e) {
			LOG.error("Error with exception: ", e);
		}

		return nodes;
	}
	
	@Override
	public Map<String, String> getNodeMap(Node node) {
		
		Map<String, String> nodeMap = new HashMap<String, String>();
    	
    	try {
			nodeMap.put("path", node.getPath());
    	
	    	if(node.hasProperty("jcr:content/jcr:title")) {
	    		nodeMap.put("title", node.getProperty("jcr:content/jcr:title").getString());
	    	} else {
	    		nodeMap.put("title", node.getName());
	    	}
	    	
	    	if(node.hasProperty("jcr:content/article/snippet")) {
	    		//Snippet string likely to contain html tags - strip them out
	    		String snippet = node.getProperty("jcr:content/article/snippet").getString();
	    		nodeMap.put("excerpt", snippet);
	    	} else if(node.hasProperty("jcr:content/jcr:description")) {
	    		nodeMap.put("excerpt", node.getProperty("jcr:content/jcr:description").getString());
	    	}
	
	    	//Get image for Article template pages
	    	if(node.getProperty("jcr:content/cq:template").getString().equals("/apps/nhmwww/templates/articlepage")
	    			|| node.getProperty("jcr:content/cq:template").getString().equals("/apps/nhmwww/templates/imagepage")) {
		    	if(node.hasProperty("jcr:content/article/headType")) {
		    		if(node.getProperty("jcr:content/article/headType").getString().equals("image")) {
		    			if(node.hasProperty("jcr:content/article/altFileReference")) {
		    				String fileReference = node.getProperty("jcr:content/article/altFileReference").getString();
		    				String extension = "." + fileReference.substring(fileReference.lastIndexOf(".") + 1);
		    				
							nodeMap.put("context", fileReference);
							nodeMap.put("extension", extension);
							nodeMap.put("imageType", "image");
		    			} else if(node.hasProperty("jcr:content/article/image/fileReference")) {
		    				String fileReference = node.getProperty("jcr:content/article/image/fileReference").getString();
		    				String extension = "." + fileReference.substring(fileReference.lastIndexOf(".") + 1);
		    				
							nodeMap.put("context", fileReference);
							nodeMap.put("extension", extension);
							nodeMap.put("imageType", "image");
						}
		    		} else if(node.getProperty("jcr:content/article/headType").getString().equals("video")) {
		    			if(node.hasProperty("jcr:content/article/video/youtube")) {
		    				String youtubeImagePath = "http://img.youtube.com/vi/" + node.getProperty("jcr:content/article/video/youtube").getString() + "/mqdefault.jpg";
				    		nodeMap.put("imagePath", youtubeImagePath);
				    		nodeMap.put("imageType", "video");
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
	    		nodeMap.put("datepublished", datePublished);
	    	}
	
	    	//Get hub tag
	    	if(node.hasProperty("jcr:content/article/hubTag")) {
	    		Property hubTagsProperty = node.getProperty("jcr:content/article/hubTag");
	    		Value[] hubTags = hubTagsProperty.getValues();
				if(hubTags.length > 0) {
					Tag tag = tagManager.resolve(hubTags[0].getString());
					nodeMap.put("hubtag", tag.getTitle().toUpperCase());
				}
	    	}
    	} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return nodeMap;
	}
	
	public String getMonth(int month) {
		return new DateFormatSymbols().getMonths()[month-1];
	}
}
