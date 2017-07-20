package uk.ac.nhm.nhm_www.core.services;

import java.util.List;

public interface ArticleSearchService {

	public List<String> getPageTitles(String rootPath, String[] tags, String order);
}
