package uk.ac.nhm.core.impl.services.dinoDirectory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.Session;

import org.apache.commons.text.similarity.LevenshteinDistance;
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

import uk.ac.nhm.core.services.dinoDirectory.DinoDirectorySearchResultsService;

@Component
@Service
public class DinoDirectorySearchResultsServiceImpl implements DinoDirectorySearchResultsService {

	private static final Logger LOG = LoggerFactory.getLogger(DinoDirectorySearchResultsServiceImpl.class);
	
	@Reference
	private SlingRepository repository;
	
	@Reference
	private QueryBuilder builder;
	
	@Override
	public List<Map<String, String>> getSearchResults(String searchValue) {
		
		List<Map<String, String>> dinosaurList = new ArrayList<Map<String, String>>();
		
		try {
			final Session session = repository.loginService("searchService", null);
			
			Map<String, String> queryMap = new HashMap<String, String>();

			queryMap.put("path", "/content/nhmwww/en/home/discover/dino-directory");
			queryMap.put("type", "cq:Page");
			
			//Pre-search string cleanup
			searchValue = searchValue.replaceAll("soros", "saurus");
			searchValue = searchValue.replaceAll("sorus", "saurus");
			
			//Use the tilde to activate Lucene's fuzzy matching
			queryMap.put("fulltext", searchValue + "~");
			
			//Query
		    Query query = builder.createQuery(PredicateGroup.create(queryMap), session);
		    
		    query.setStart(0);

		    SearchResult result = query.getResult();

		    LOG.info(result.getQueryStatement());
		    
		    for(Hit hit : result.getHits()) {
		    	Map<String, String> dinosaurMap = new HashMap<String, String>();
		    	dinosaurMap.put("title", hit.getTitle());
		    	dinosaurList.add(dinosaurMap);
		    	
		    	int score = 100;
		    	if(searchValue.toLowerCase().equals(hit.getTitle().toLowerCase())) {
		    		dinosaurMap.put("score", String.valueOf(score));
		    	} else { 
			    	LevenshteinDistance matcher = new LevenshteinDistance();
			    	Integer distance = matcher.apply(searchValue, hit.getTitle());
			    	score = score - (distance * 5);
			    	
			    	if(searchValue.toLowerCase().startsWith(hit.getTitle().substring(0, 3).toLowerCase())) {
			    		score = score + 9;
			    	} else if(searchValue.toLowerCase().startsWith(hit.getTitle().substring(0, 2).toLowerCase())) {
			    		score = score + 8;
			    	} else if(searchValue.toLowerCase().startsWith(hit.getTitle().substring(0, 1).toLowerCase())) {
			    		score = score + 6;
			    	}
			    	
			    	dinosaurMap.put("score", String.valueOf(score));
		    	}
		    }
		} catch(Exception e) {
			//Do something with e
		}

		Collections.sort(dinosaurList, mapComparator);
		
		List<?> shallowCopy = dinosaurList.subList(0, dinosaurList.size());
		Collections.reverse(shallowCopy);
		
		return dinosaurList;
	}

	private Comparator<Map<String, String>> mapComparator = new Comparator<Map<String, String>>() {
		public int compare(Map<String, String> m1, Map<String, String> m2) {
			Integer position1 = Integer.parseInt(m1.get("score"));
			Integer position2 = Integer.parseInt(m2.get("score"));
			return position1.compareTo(position2);
		}
	};
	
}
