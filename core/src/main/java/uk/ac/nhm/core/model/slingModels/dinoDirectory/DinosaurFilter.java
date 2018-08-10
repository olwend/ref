package uk.ac.nhm.core.model.slingModels.dinoDirectory;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Source;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.nhm.core.services.dinoDirectory.DinoDirectoryDinosaurFilterService;
import uk.ac.nhm.core.services.dinoDirectory.DinoDirectoryEnvironmentService;

@Model(adaptables = Resource.class)
public class DinosaurFilter {

	private final static Logger LOG = LoggerFactory.getLogger(DinosaurFilter.class);

	@Inject
	@Source("osgi-services")
	DinoDirectoryEnvironmentService environmentService;

	@Inject
	@Source("osgi-services")
	DinoDirectoryDinosaurFilterService filterService;

	@Inject
	private String filterOne;

	@Inject
	private String filterTwo;

	private String title;
	private String description;
	private List<Map<String, String>> dinosaurList = null;

	@PostConstruct
	protected void init() {
		String environmentUrl = environmentService.getDinoDirectoryUrl();

		this.setTitle(filterService.getTitle(filterOne, filterTwo));
		this.setDescription(filterService.getDescription(filterOne, filterTwo, environmentUrl));
		this.setDinosaurList(filterService.getDinosaurList(filterOne, filterTwo, environmentUrl));
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Map<String, String>> getDinosaurList() {
		return dinosaurList;
	}

	public void setDinosaurList(List<Map<String, String>> dinosaurList) {
		this.dinosaurList = dinosaurList;
	}

}
