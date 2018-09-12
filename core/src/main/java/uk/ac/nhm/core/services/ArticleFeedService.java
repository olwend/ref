package uk.ac.nhm.core.services;

import java.util.List;
import java.util.Map;

import javax.jcr.Node;

import org.apache.sling.api.resource.ResourceResolver;

import com.day.cq.search.result.Hit;

public interface ArticleFeedService {

	public List<Hit> getArticleNodes(String rootPath, String[] tags, String order, String tagsOperator, String limit, ResourceResolver resourceResolver);
	
	public Map<String, String> getNodeMap(Node node, ResourceResolver resourceResolver);
}
