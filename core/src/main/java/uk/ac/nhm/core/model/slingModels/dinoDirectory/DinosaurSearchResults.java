package uk.ac.nhm.core.model.slingModels.dinoDirectory;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Source;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.nhm.core.services.dinoDirectory.DinoDirectorySearchResultsService;

@Model(adaptables = SlingHttpServletRequest.class)
public class DinosaurSearchResults {

	Logger LOG = LoggerFactory.getLogger(DinosaurSearchResults.class);
	
	@Inject
	SlingHttpServletRequest request;
	
	@Inject
	@Source("osgi-services")
	DinoDirectorySearchResultsService service;
	
	private List<Map<String, String>> dinosaurList;
	
	@PostConstruct
	protected void init() {
		this.setDinosaurList(service.getSearchResults(request.getParameter("search")));
	}

	public List<Map<String, String>> getDinosaurList() {
		return dinosaurList;
	}

	public void setDinosaurList(List<Map<String, String>> dinosaurList) {
		this.dinosaurList = dinosaurList;
	}
	
}
