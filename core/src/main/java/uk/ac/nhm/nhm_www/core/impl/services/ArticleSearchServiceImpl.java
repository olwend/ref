package uk.ac.nhm.nhm_www.core.impl.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.Session;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.jcr.api.SlingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import uk.ac.nhm.nhm_www.core.services.ArticleSearchService;

/**
 * @author alisp2
 *
 * Service that...
 */

@Component
@Service
public class ArticleSearchServiceImpl implements ArticleSearchService {

	private final static Logger LOG = LoggerFactory.getLogger(ArticleSearchServiceImpl.class);
	
	@Reference
	private SlingRepository repository;
	
	@Reference
	private QueryBuilder builder;

	@Override
	public List<Map<String, String>> getPageData(String rootPath, String[] tags, String order, String tagsOperator, String limit) {

		List<Map<String, String>> nodeList = new ArrayList<Map<String, String>>();

		try {
			final Session session = repository.loginService("searchService", null);

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
				    queryMap.put(tagidproperty, "jcr:content/cq:tags");
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

		    //Query
		    Query query = builder.createQuery(PredicateGroup.create(queryMap), session);

		    query.setStart(0);

		    //Query - limit
		    if(limit != null) {
			    query.setHitsPerPage(Long.parseLong(limit));
		    }

		    SearchResult result = query.getResult();

		    LOG.info(result.getQueryStatement());

		    //For each query hit, gather required fields and add them to a Map
		    //Subsequently add map to @nodeList that is returned to the view
		    for(Hit hits : result.getHits()) {
		    	Map<String, String> nodeMap = new HashMap<String, String>();
		    	Node node = hits.getNode();
		    	//Get properties for each article
		    	nodeMap.put("path", node.getPath());
		    	
		    	if(node.hasProperty("jcr:content/jcr:description")) {
		    		nodeMap.put("title", node.getProperty("jcr:content/jcr:title").getString());
		    	} else {
		    		nodeMap.put("title", node.getName());
		    	}
		    	
		    	if(node.hasProperty("jcr:content/jcr:description")) {
		    		nodeMap.put("excerpt", node.getProperty("jcr:content/jcr:description").getString());
		    	}
		    	
		    	//Get image for Article template pages
		    	if(node.getProperty("jcr:content/cq:template").getString().equals("/apps/nhmwww/templates/articlepage")) {
		    		LOG.error("yes");
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
		    		LOG.error("yes");
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

		    	nodeList.add(nodeMap);
		    }

		} catch (Exception e) {
			LOG.error("Error with exception: ", e);
		}

		return nodeList;
	}
}
