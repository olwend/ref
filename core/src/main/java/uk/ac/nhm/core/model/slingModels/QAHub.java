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

import uk.ac.nhm.core.services.QAHubService;

@Model(adaptables = SlingHttpServletRequest.class)
public class QAHub {

	private final static Logger LOG = LoggerFactory.getLogger(QAHub.class);

	@Inject
	SlingHttpServletRequest request;

	@Inject
	ValueMap properties;

	@Inject
	@Source("osgi-services")
	QAHubService service;

	private List<Map<String, String>> questionList = null;
	
	@PostConstruct
	protected void init() {
		String rootPath = properties.get("rootpath", String.class);
		String[] tags = properties.get("cq:tags", String[].class);

		this.setQuestionList(service.getQuestionData(rootPath, tags));
	}

	public List<Map<String, String>> getQuestionList() {
		return questionList;
	}
	
	public void setQuestionList(List<Map<String, String>> questionList) {
		this.questionList = questionList;
	}
}