package uk.ac.nhm.nhm_www.core.services;

import java.util.List;

public interface ArticleSearchService {

	public String getArticleString();

	public List<String> getPageTitles(String rootPath, String[] tags);
}
