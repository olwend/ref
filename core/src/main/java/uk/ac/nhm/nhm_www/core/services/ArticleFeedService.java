package uk.ac.nhm.nhm_www.core.services;

import java.util.List;
import java.util.Map;

import org.apache.sling.api.SlingHttpServletRequest;

public interface ArticleFeedService {

	public List<Map<String, String>> getPageData(String rootPath, String[] tags, String order, String tagsOperator, String limit);
}
