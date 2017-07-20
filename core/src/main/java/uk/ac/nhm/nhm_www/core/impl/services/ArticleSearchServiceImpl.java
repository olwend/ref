package uk.ac.nhm.nhm_www.core.impl.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public List<String> getPageTitles(String rootPath, String[] tags, String order) {
		List<String> pageTitles = new ArrayList<String>();
		
		try {
			final Session session = repository.loginService("searchService", null);
			
			Map<String, String> map = new HashMap<String, String>();
			
			//Path
			map.put("path", rootPath);
		    map.put("type", "cq:Page");
		    
		    //Tags
		    if(tags.length > 0) {
		    	for(int i=0; i<tags.length; i++) {
		    		String tagid = i + "_tagid";
		    		String tagidproperty = i + "_tagid.property";
				    map.put(tagid, tags[i]);
				    map.put(tagidproperty, "jcr:content/cq:tags");
		    	}
		    }
		    
		    //Order by
		    if(!order.equals(null) && order != null) {
		    	switch(order) {
		    		case "datemodified" :
		    			map.put("orderby", "@jcr:content/cq:lastModified");
		    			map.put("orderby.sort", "desc");
		    			break;
		    		case "datecreated" :
		    			map.put("orderby", "@jcr:content/jcr:created");
		    			map.put("orderby.sort", "desc");
		    			break;
		    	}
		    }
		    
		    Query query = builder.createQuery(PredicateGroup.create(map), session);

		    query.setStart(0);
		    query.setHitsPerPage(200);
		    
		    SearchResult result = query.getResult();
		    
		    LOG.info(result.getQueryStatement());
		    for(Hit hits : result.getHits()) {
		    	pageTitles.add(hits.getTitle());
		    }
		} catch (Exception e) {
			LOG.error("Error with exception: ", e);
		}
				
		return pageTitles;
	}
}
