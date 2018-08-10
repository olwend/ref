package uk.ac.nhm.core.model.slingModels;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.Node;

import org.apache.felix.scr.annotations.Reference;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Source;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.tagging.JcrTagManagerFactory;

import uk.ac.nhm.core.services.ArticleFeedService;

@Model(adaptables = SlingHttpServletRequest.class)
public class DiscoverRelated {

	private final static Logger LOG = LoggerFactory.getLogger(DiscoverRelated.class);
	
	@Inject
	ValueMap properties;
	
	@Inject
	ResourceResolver resourceResolver;
	
	@Inject
	@Source("osgi-services")
	ArticleFeedService service;
	
	private List<Map<String, String>> pageList;
	
	private String readmorelink;

	@PostConstruct
	protected void init() {
		setPageList(properties);
		setReadMoreLink(properties);
	}
	
	public List<Map<String, String>> getPageList() {
		return pageList;
	}

	public void setPageList(ValueMap properties) {
		List<Map<String, String>> itemList = new ArrayList<Map<String, String>>();
		String[] posts = null;

		if(properties.get("posts") != null) {
			posts = properties.get("posts", String[].class);
		}
		
		try {
			for(int i=0; i<posts.length; i++) {
				String pagePath = posts[i];
				Resource resource = resourceResolver.getResource(pagePath);
				Node node = resource.adaptTo(Node.class);
			
				itemList.add(service.getNodeMap(node));
			}
		} catch (Exception e) {
			LOG.error("Error with exception: ", e);
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
	
	public String getReadmorelink() {
		return readmorelink;
	}
	
}
