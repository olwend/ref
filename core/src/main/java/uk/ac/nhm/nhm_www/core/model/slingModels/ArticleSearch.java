package uk.ac.nhm.nhm_www.core.model.slingModels;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Source;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.nhm.nhm_www.core.services.ArticleSearchService;

@Model(adaptables = SlingHttpServletRequest.class)
public class ArticleSearch {

	private final Logger LOG = LoggerFactory.getLogger(ArticleSearch.class);
	
	@Inject
	SlingHttpServletRequest request;
	
	@Inject
	ValueMap properties;
	
	@Inject
	@Source("osgi-services")
	ArticleSearchService service;

	private List<Map<String, String>> pageList = null;
	
	@PostConstruct
	protected void init() {
		String rootPath = properties.get("rootpath", String.class);
		String tagsOperator = properties.get("tagsoperator", String.class);
		String[] tags = properties.get("cq:tags", String[].class);
		String order = properties.get("order", String.class);
		String limit = properties.get("limit", String.class);
		this.setPageList(service.getPageTitles(rootPath, tags, order, tagsOperator, limit));
	}

	public List<Map<String, String>> getPageList() {
		return pageList;
	}

	public void setPageList(List<Map<String, String>> pageList) {
		this.pageList = pageList;
	}
}
