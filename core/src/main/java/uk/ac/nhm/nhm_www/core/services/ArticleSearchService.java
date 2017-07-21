package uk.ac.nhm.nhm_www.core.services;

import java.util.List;
import java.util.Map;

public interface ArticleSearchService {

	public List<Map<String, String>> getPageTitles(String rootPath, String[] tags, String order, String tagsOperator, String limit);
}
