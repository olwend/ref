package uk.ac.nhm.nhm_www.core.services;

import java.util.List;

import javax.jcr.RepositoryException;
import javax.ws.rs.core.Response;

import org.apache.sling.commons.json.JSONException;

import com.day.cq.search.result.SearchResult;

public interface ArticleSearchService {

//	public List getArticles(String rootPath, String number, String selector) throws RepositoryException, JSONException;
//	
//	public String getMatches();
	
	public String getArticleString();

	Response getAll() throws RepositoryException, JSONException;
}
