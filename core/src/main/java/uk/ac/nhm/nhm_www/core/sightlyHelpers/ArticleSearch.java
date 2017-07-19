package uk.ac.nhm.nhm_www.core.sightlyHelpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUse;

import uk.ac.nhm.nhm_www.core.services.ArticleSearchService;

public class ArticleSearch extends WCMUse {
	
	private Logger LOG = LoggerFactory.getLogger(ArticleSearch.class);
	
//	private String rootPath;
//	private String[] tags;

	protected uk.ac.nhm.nhm_www.core.services.ArticleSearchService service;
	
	@Override
	public void activate() throws Exception {
//		rootPath = getProperties().get("rootpath", String.class);
//		tags = getProperties().get("cq:tags", String[].class);

		service = getSlingScriptHelper().getService(ArticleSearchService.class);
		LOG.error(service.toString());
	}

//	public String getRootPath() {
//		return rootPath;
//	}
//	
//	public String[] getTags() {
//		return tags;
//	}

	
//	public String getArticleString() {
//		return service.getArticleString();
//	}

}