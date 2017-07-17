package uk.ac.nhm.nhm_www.core.services;

import javax.jcr.RepositoryException;
import org.apache.sling.commons.json.JSONException;

import com.day.cq.search.result.SearchResult;

public interface ArticleSearchService {

	public SearchResult getArticles() throws RepositoryException, JSONException;
	
}
