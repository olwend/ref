package uk.ac.nhm.nhm_www.core.componentHelpers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.servlet.http.HttpServletRequest;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.models.annotations.Source;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;

import uk.ac.nhm.nhm_www.core.services.ArticleSearchService;

public class ArticleSearchHelper {

	private String test;
	private SearchResult result;
	private List<String> list;
	private String tagString;

	ArticleSearchService service;
	
	public ArticleSearchHelper(ValueMap properties, Resource resource, HttpServletRequest request, ResourceResolver resourceResolver) {
		QueryBuilder builder = resourceResolver.adaptTo(QueryBuilder.class);
		Session session = resourceResolver.adaptTo(Session.class);
		
		String fulltextSearchTerm = "Geometrixx";
        
	    // create query description as hash map (simplest way, same as form post)
	    Map<String, String> map = new HashMap<String, String>();
	   
	// create query description as hash map (simplest way, same as form post)
	    String path = properties.get("rootpath", String.class);
	    String[] tags = properties.get("cq:tags", String[].class);
	    
	    tagString = "";
	    
	    for(int i=0; i<tags.length; i++) {
	    	tagString += tags[i];	    	
	    }
	    
	    map.put("path", path);
	    map.put("type", "cq:Page");
	    map.put("tagid", tagString);
	    map.put("tagid.property", "jcr:content/cq:tags");
	  
	    // can be done in map or with Query methods
	    map.put("p.offset", "0"); // same as query.setStart(0) below
	    map.put("p.limit", "200"); // same as query.setHitsPerPage(20) below
	                     
	    Query query = builder.createQuery(PredicateGroup.create(map), session);
	    query.setStart(0);
	    query.setHitsPerPage(200);
	               
	    result = query.getResult();	
	   
	}
	
	public String getTagString() {
		return tagString;
	}

	public void setTagString(String tagString) {
		this.tagString = tagString;
	}

	public String getTest() {
		return "lol";
	}

	public void setTest(String test) {
		this.test = test;
	}

	public SearchResult getResult() {
		return result;
	}

	public void setResult(SearchResult result) {
		this.result = result;
	}

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}
	
	
}
