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

	private final Logger LOG = LoggerFactory.getLogger(ArticleSearchServiceImpl.class);
	
	@Reference
	private SlingRepository repository;
	
	@Reference
	private QueryBuilder builder;

	@Override
	public List<Map<String, String>> getPageTitles(String rootPath, String[] tags, String order, String tagsOperator, String limit) {

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
		    	
		    	nodeList.add(nodeMap);
		    }

		} catch (Exception e) {
			LOG.error("Error with exception: ", e);
		}
				
		return nodeList;
	}
}
