package uk.ac.nhm.nhm_www.core.impl.services;

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

import uk.ac.nhm.nhm_www.core.services.ArticleFeedService;
import uk.ac.nhm.nhm_www.core.utils.TextUtils;

/**
 * @author alisp2
 *
 * Service that...
 */

@Component
@Service
public class ArticleFeedServiceImpl implements ArticleFeedService {

	private final static Logger LOG = LoggerFactory.getLogger(ArticleFeedServiceImpl.class);
	private final TextUtils textUtils = new TextUtils();
	
	@Reference
	private SlingRepository repository;
	
	@Reference
	private JcrTagManagerFactory jcrTagManagerFactory;
	private TagManager tagManager;
	
	@Reference
	private QueryBuilder builder;

	@Override
	public List<Map<String, String>> getPageData(String rootPath, String[] tags, String order, String tagsOperator, String limit) {

		List<Map<String, String>> nodeList = new ArrayList<Map<String, String>>();
		
		try {
			final Session session = repository.loginService("searchService", null);
			tagManager = jcrTagManagerFactory.getTagManager(session);
			
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
		    			queryMap.put("orderby", "@jcr:content/cq:lastModified");
		    			queryMap.put("orderby.sort", "desc");
		    			break;
		    		case "datecreated" :
		    			queryMap.put("orderby", "@jcr:content/jcr:created");
		    			queryMap.put("orderby.sort", "desc");
		    			break;
		    	}
		    }

		    LOG.error(queryMap.toString());
		    //Query
		    Query query = builder.createQuery(PredicateGroup.create(queryMap), session);

		    query.setStart(0);

		    //Query - limit
		    if(limit != null) {
			    query.setHitsPerPage(Long.parseLong(limit));
		    }

		    SearchResult result = query.getResult();

		    //LOG.info(result.getQueryStatement());

		    //For each query hit, gather required fields and add them to a Map
		    //Subsequently add map to @nodeList that is returned to the view
		    for(Hit hits : result.getHits()) {
		    	Map<String, String> nodeMap = new HashMap<String, String>();
		    	Node node = hits.getNode();
		    	//Get properties for each article
		    	nodeMap.put("path", node.getPath());
		    	
		    	if(node.hasProperty("jcr:content/jcr:title")) {
		    		nodeMap.put("title", node.getProperty("jcr:content/jcr:title").getString());
		    	} else {
		    		nodeMap.put("title", node.getName());
		    	}
		    	
		    	if(node.hasProperty("jcr:content/article/snippet")) {
		    		//Snippet string likely to contain html tags - strip them out
		    		String snippet = textUtils.stripHtmlTags(node.getProperty("jcr:content/article/snippet").getString());
		    		nodeMap.put("excerpt", snippet);
		    	} else if(node.hasProperty("jcr:content/jcr:description")) {
		    		nodeMap.put("excerpt", node.getProperty("jcr:content/jcr:description").getString());
		    	}
		    	
		    	//Get image for Article template pages
		    	if(node.getProperty("jcr:content/cq:template").getString().equals("/apps/nhmwww/templates/articlepage")) {
			    	if(node.hasProperty("jcr:content/article/headType")) {
			    		if(node.getProperty("jcr:content/article/headType").getString().equals("image")) {
			    			if(node.hasProperty("jcr:content/article/image/fileReference")) {
								nodeMap.put("imagePath", node.getProperty("jcr:content/article/image/fileReference").getString());
							}
			    		} else if(node.getProperty("jcr:content/article/headType").getString().equals("video")) {
			    			if(node.hasProperty("jcr:content/article/video/youtube")) {
			    				String youtubeImagePath = "http://img.youtube.com/vi/" + node.getProperty("jcr:content/article/video/youtube").getString() + "/mqdefault.jpg";
					    		nodeMap.put("imagePath", youtubeImagePath);
					    	}
			    		}
			    	}
		    	}
		    	
		    	//Get image for Discover template pages
		    	if(node.getProperty("jcr:content/cq:template").getString().equals("/apps/nhmwww/templates/discoverpublicationpage")) {
			    	if(node.hasProperty("jcr:content/discoverpublication/headType")) {
			    		if(node.getProperty("jcr:content/discoverpublication/headType").getString().equals("image")) {
			    			if(node.hasProperty("jcr:content/discoverpublication/image/fileReference")) {
								nodeMap.put("imagePath", node.getProperty("jcr:content/discoverpublication/image/fileReference").getString());
							}
			    		} else if(node.getProperty("jcr:content/discoverpublication/headType").getString().equals("video")) {
			    			if(node.hasProperty("jcr:content/discoverpublication/video/youtube")) {
			    				String youtubeImagePath = "http://img.youtube.com/vi/" + node.getProperty("jcr:content/discoverpublication/video/youtube").getString() + "/mqdefault.jpg";
					    		nodeMap.put("imagePath", youtubeImagePath);
					    	}
			    		}
			    	}
		    	}

		    	DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("MM/dd/yy");
		    	
		    	if(node.hasProperty("jcr:content/article/datepublished")) {
		    		DateTime dt = dateFormatter.parseDateTime(node.getProperty("jcr:content/article/datepublished").getString());
					MutableDateTime mdt = dt.toMutableDateTime();
					String datePublished = mdt.getDayOfMonth() + " " + getMonth(mdt.getMonthOfYear()) + " " + mdt.getYear();
		    		nodeMap.put("datepublished", datePublished);
		    	}

		    	if(node.hasProperty("jcr:content/article/hubTag")) {
		    		Property hubTagsProperty = node.getProperty("jcr:content/article/hubTag");
		    		Value[] hubTags = hubTagsProperty.getValues();
					if(hubTags.length > 0) {
						Tag tag = tagManager.resolve(hubTags[0].getString());
						nodeMap.put("hubtag", tag.getTitle().toUpperCase());
					}
		    	}
		    	
		    	nodeList.add(nodeMap);
		    }

		} catch (Exception e) {
			LOG.error("Error with exception: ", e);
		}

		return nodeList;
	}
	
	public String getMonth(int month) {
		return new DateFormatSymbols().getMonths()[month-1];
	}
}
