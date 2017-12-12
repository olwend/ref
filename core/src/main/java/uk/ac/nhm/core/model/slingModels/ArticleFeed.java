package uk.ac.nhm.core.model.slingModels;

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

import uk.ac.nhm.core.services.ArticleFeedService;

@Model(adaptables = SlingHttpServletRequest.class)
public class ArticleFeed {

	private final static Logger LOG = LoggerFactory.getLogger(ArticleFeed.class);

	@Inject
	SlingHttpServletRequest request;

	@Inject
	ValueMap properties;

	@Inject
	@Source("osgi-services")
	ArticleFeedService service;

	private List<Map<String, String>> pageList = null;
	private String columns = null;
	private String readmorelink = null;
	

	@PostConstruct
	protected void init() {
		String categoryParameter = request.getParameter("category");
		String[] tags = null;

		if(categoryParameter != null && !categoryParameter.equals("")) {
			tags = new String[2];
			tags[0] = "nhm:" + categoryParameter;
			tags[1] = "nhm:discover/" + categoryParameter;
		} else {
			tags = properties.get("cq:tags", String[].class);
		}

		String rootPath = properties.get("rootpath", String.class);
		String tagsOperator = properties.get("tagsoperator", String.class);
		String order = properties.get("order", String.class);
		String limit = properties.get("limit", String.class);
		this.setPageList(service.getPageData(rootPath, tags, order, tagsOperator, limit));

		String rowSize = properties.get("rowsize", String.class);
		if(rowSize.equals("fullwidth")) {
			this.setColumns("4");
		}
		if(rowSize.equals("twocolumn")) {
			this.setColumns("2");
		}
		
		
		//this.showreadmore = properties.get("showreadmore",boolean.class);
		this.readmorelink = properties.get("readmorelink",String.class);
		if(this.readmorelink != null) {
			if(!this.readmorelink.isEmpty()) {
				if(!this.readmorelink.contains(".html")) {
					this.readmorelink = this.readmorelink + ".html";
				}
			}

		}
	}

	public List<Map<String, String>> getPageList() {
		return pageList;
	}

	public void setPageList(List<Map<String, String>> pageList) {
		this.pageList = pageList;
	}

	public String getColumns() {
		return columns;
	}

	public void setColumns(String columns) {
		this.columns = columns;
	}
	
	public String getReadmorelink() {
		return readmorelink;
	}

	
	
}
