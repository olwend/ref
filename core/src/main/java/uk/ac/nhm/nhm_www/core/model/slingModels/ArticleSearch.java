package uk.ac.nhm.nhm_www.core.model.slingModels;

import java.util.List;

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

	private List<String> pageTitles = null;
	
	@PostConstruct
	protected void init() {
		String rootPath = properties.get("rootpath", String.class);
		String[] tags = properties.get("cq:tags", String[].class);
		String order = properties.get("order", String.class);
		this.setPageTitles(service.getPageTitles(rootPath, tags, order));
	}

	public List<String> getPageTitles() {
		return pageTitles;
	}

	public void setPageTitles(List<String> pageTitles) {
		this.pageTitles = pageTitles;
	}
	
}
