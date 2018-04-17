package uk.ac.nhm.core.model.slingModels.dinoDirectory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Model(adaptables = SlingHttpServletRequest.class)
public class DinosaurNav {

	Logger LOG = LoggerFactory.getLogger(DinosaurNav.class);
	
	@Inject
	SlingHttpServletRequest request;

	@Inject
	ValueMap properties;

	private String targetPage;
	
	@PostConstruct
	protected void init() {
		if(properties.get("targetpage", String.class) == null) {
			this.setTargetPage(request.getRequestURI().toString());
		} else {
			this.setTargetPage(properties.get("targetpage", String.class) + ".html");
		}
	}

	public String getTargetPage() {
		return targetPage;
	}

	public void setTargetPage(String targetPage) {
		this.targetPage = targetPage;
	}
	
}
