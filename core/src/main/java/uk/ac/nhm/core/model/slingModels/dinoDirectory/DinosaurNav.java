package uk.ac.nhm.core.model.slingModels.dinoDirectory;

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

import uk.ac.nhm.core.services.dinoDirectory.DinoDirectoryNavService;

@Model(adaptables = SlingHttpServletRequest.class)
public class DinosaurNav {

	Logger LOG = LoggerFactory.getLogger(DinosaurNav.class);
	
	@Inject
	SlingHttpServletRequest request;
	
	@Inject
	@Source("osgi-services")
	DinoDirectoryNavService service;

	@Inject
	ValueMap properties;
	
	private List<Map<String, String>> dinosaurList;
	private String targetPage;
	
	@PostConstruct
	protected void init() {
		this.setDinosaurList(service.getSearchResults(request.getParameter("search")));
		if(properties.get("targetpage", String.class) == null) {
			this.setTargetPage(request.getRequestURI().toString());
		} else {
			this.setTargetPage(properties.get("targetpage", String.class) + ".html");
		}
	}

	public List<Map<String, String>> getDinosaurList() {
		return dinosaurList;
	}

	public void setDinosaurList(List<Map<String, String>> dinosaurList) {
		this.dinosaurList = dinosaurList;
	}

	public String getTargetPage() {
		return targetPage;
	}

	public void setTargetPage(String targetPage) {
		this.targetPage = targetPage;
	}
	
}
