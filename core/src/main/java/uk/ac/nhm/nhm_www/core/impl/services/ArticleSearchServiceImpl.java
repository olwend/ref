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

@Component
@Service
public class ArticleSearchServiceImpl implements ArticleSearchService {

	private final Logger LOG = LoggerFactory.getLogger(ArticleSearchServiceImpl.class);
	
	@Reference
	private SlingRepository repository;
	
	@Reference
	private QueryBuilder builder;
	
	@Override
	public String getArticleString() {
		LOG.error("returning lol");
		return "lol";
	}

	@Override
	public List<String> getPageTitles(String rootPath, String[] tags) {
		List<String> pageTitles = new ArrayList<String>();
		
		String tagsString = "";
		
		for(int i=0; i<tags.length; i++) {
			tagsString += tags[i] + " ";
		}
		
		tagsString.trim();
		
		try {
			final Session session = repository.loginService("searchService", null);
			
			Map<String, String> map = new HashMap<String, String>();
			
			map.put("path", rootPath);
		    map.put("type", "cq:Page");
		    map.put("tagid", tagsString);
		    LOG.error(tagsString);
		    map.put("tagid.property", "jcr:content/cq:tags");
		    
		    Query query = builder.createQuery(PredicateGroup.create(map), session);
		    
		    query.setStart(0);
		    query.setHitsPerPage(200);
		    
		    SearchResult result = query.getResult();
		    
		    LOG.error(result.getQueryStatement());
		    for(Hit hits : result.getHits()) {
		    	pageTitles.add(hits.getTitle());
		    }
		} catch (Exception e) {
			// TODO: handle exception
		}
				
		return pageTitles;
	}
}
