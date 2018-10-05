package uk.ac.nhm.core.model.slingModels;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Source;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.search.result.Hit;

import uk.ac.nhm.core.services.ArticleFeedService;

@Model(adaptables = SlingHttpServletRequest.class)
public class ArticleFeed {

	private final static Logger LOG = LoggerFactory.getLogger(ArticleFeed.class);

	@Inject
	SlingHttpServletRequest request;

	@Inject
	ValueMap properties;

	@Inject
	ResourceResolver resourceResolver;
	
	@Inject
	@Source("osgi-services")
	ArticleFeedService service;

	private List<Map<String, String>> pageList;
	
	private String mediumColumn = null;
	private String largeColumn = null;
	private String cssClass = null;
	
	private String readmorelink;
	
	@PostConstruct
	protected void init() {
		setPageList(properties);
		setReadMoreLink(properties);

		//Set row size values
		String rowSize = properties.get("rowsize", String.class);
		
		if(rowSize.equals("fullwidth")) {
			this.setMediumColumn("2");
			this.setLargeColumn("4");
			this.setCssClass("articlefeed__full-width");
		}
		
		if(rowSize.equals("twocolumn")) {
			this.setMediumColumn("2");
			this.setLargeColumn("2");
			this.setCssClass("articlefeed__two-column");
		}
		
		if(rowSize.equals("onecolumn")) {
			this.setMediumColumn("1");
			this.setLargeColumn("1");
			this.setCssClass("articlefeed__one-column");
		}		
	}
	
	public void setPageList(ValueMap properties) {
		//Set page list
		String[] tags = properties.get("cq:tags", String[].class);
		String rootPath = properties.get("rootpath", String.class);
		String tagsOperator = properties.get("tagsoperator", String.class);
		String order = properties.get("order", String.class);
		String limit = properties.get("limit", String.class);
		
		List<Hit> nodes = service.getArticleNodes(rootPath, tags, order, tagsOperator, limit, resourceResolver);
		List<Map<String, String>> itemList = new ArrayList<Map<String, String>>();
		
	    //For each query hit, gather required fields and add them to a Map
	    //Subsequently add Map to @nodeList that is returned to the view
	    for(Hit hits : nodes) {
			try {
				Node node = hits.getNode();
				itemList.add(service.getNodeMap(node, resourceResolver));
			} catch (RepositoryException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }

		pageList = itemList;
	}

	public void setReadMoreLink(ValueMap properties) {
		//Set read more link if present
		readmorelink = properties.get("readmorelink",String.class);
		if(readmorelink != null) {
			if(!readmorelink.isEmpty()) {
				if(!readmorelink.contains(".html")) {
					readmorelink = readmorelink + ".html";
				}
			}
		}
	}
	
	public List<Map<String, String>> getPageList() {
		return pageList;
	}
	
	public String getReadmorelink() {
		return readmorelink;
	}
	
	public String getMediumColumn() {
		return mediumColumn;
	}

	public void setMediumColumn(String mediumColumn) {
		this.mediumColumn = mediumColumn;
	}

	public String getLargeColumn() {
		return largeColumn;
	}

	public void setLargeColumn(String largeColumn) {
		this.largeColumn = largeColumn;
	}

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}
}
