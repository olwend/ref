package uk.ac.nhm.core.impl.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import com.day.cq.tagging.JcrTagManagerFactory;
import com.day.cq.tagging.TagManager;

import uk.ac.nhm.core.services.QAHubService;

@Component
@Service
public class QAHubServiceImpl implements QAHubService {

	private final static Logger LOG = LoggerFactory.getLogger(QAHubServiceImpl.class);
	
	@Reference
	private SlingRepository repository;
	
	@Reference
	private JcrTagManagerFactory jcrTagManagerFactory;
	private TagManager tagManager;
	
	@Reference
	private QueryBuilder builder;
	
	//List<Map<String, String>> sorter
	private Comparator<Map<String, String>> mapComparator = new Comparator<Map<String, String>>() {
	    public int compare(Map<String, String> m1, Map<String, String> m2) {
	        return m1.get("position").compareTo(m2.get("position"));
	    }
	};
	
	@Override
	public List<Map<String, String>> getQuestionData(String rootPath, String[] tags) {
		List<Map<String, String>> questionList = new ArrayList<Map<String, String>>();
		
		try {
			final Session session = repository.loginService("searchService", null);
			tagManager = jcrTagManagerFactory.getTagManager(session);
			
			Map<String, String> queryMap = new HashMap<String, String>();

			//Path
			queryMap.put("path", rootPath);
		    queryMap.put("type", "nt:unstructured");
		    queryMap.put("1_property", "sling:resourceType");
		    queryMap.put("1_property.value", "nhmwww/components/functional/q-and-a");

		    //Tags
		    if(tags != null) {
		    	for(int i=0; i<tags.length; i++) {
		    		String tagid = "group." + i + "_tagid";
		    		String tagidproperty = "group." + i + "_tagid.property";
				    queryMap.put(tagid, tags[i]);
				    queryMap.put(tagidproperty, "otherTags");
		    	}
		    }
		    
		    //Query
		    Query query = builder.createQuery(PredicateGroup.create(queryMap), session);
		    
		    query.setStart(0);

		    SearchResult result = query.getResult();
		    
		    LOG.info(result.getQueryStatement());
		    
		    for(Hit hits : result.getHits()) {
		    	Map<String, String> questionMap = new HashMap<String, String>();
		    	Node node = hits.getNode();
		    	
		    	//Get properties for each article
		    	questionMap.put("path", node.getPath());
		    	
		    	if(node.hasProperty("position")) {
		    		//Arrayify the position value
		    		String position = node.getProperty("position").getString();
		    		int pos = Integer.valueOf(position) - 1;
		    		position = String.valueOf(pos);
		    				
		    		questionMap.put("position", position);
		    	}
		    	
		    	if(node.hasProperty("question")) {
		    		questionMap.put("question", node.getProperty("question").getString());
		    	}
		    	
		    	if(node.hasProperty("answer")) {
		    		questionMap.put("answer", node.getProperty("answer").getString());
		    	}
		    	
		    	LOG.error(questionMap.get("position"));
		    	
		    	questionList.add(questionMap);
		    }

		} catch (Exception e) {
			LOG.error("Error with exception: ", e);
		}
		
		//Sort list of maps by position variable
		Collections.sort(questionList, mapComparator);

		for(int i=0; i<questionList.size(); i++) {
			LOG.error(questionList.get(i).get("position"));
		}
		
		return questionList;
	}
}