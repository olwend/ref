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
import uk.ac.nhm.core.services.QAService;
@Model(adaptables = SlingHttpServletRequest.class)
public class QA {
	
	private final static Logger LOG = LoggerFactory.getLogger(QA.class);

	@Inject
	SlingHttpServletRequest request;

	@Inject
	ValueMap properties;

	@Inject
	@Source("osgi-services")
	QAService service;
	
	@Inject
	@Source("osgi-services")
	QAHubService hubService;

	private List<Map<String, String>> questionList = null;
	private Map<String, String> nextQuestion = null;
	private Map<String, String> previousQuestion = null;
	
	@PostConstruct
	protected void init() {
		String rootPath = properties.get("path", String.class);
		String[] tags = properties.get("cq:tags", String[].class);
		Integer position = Integer.parseInt(properties.get("position",String.class));
		
		this.setQuestionList(hubService.getQuestionData(rootPath, tags));
		this.setNextQuestion(service.getNextQuestion(position,questionList));
		this.setPrevioustQuestion(service.getPreviousQuestion(position,questionList));
	}

	public List<Map<String, String>> getQuestionList() {
		return questionList;
	}
	
	public void setQuestionList(List<Map<String, String>> questionList) {
		this.questionList = questionList;
	}
	
	public Map<String, String> getNextQuestion(){
		return nextQuestion;
	}
	
	public void setNextQuestion(Map<String, String> nextQuestion) {
		this.nextQuestion = nextQuestion;
	}
	
	public Map<String, String> getPreviousQuestion(){
		return previousQuestion;
	}
	
	public void setPrevioustQuestion(Map<String, String> previousQuestion) {
		this.previousQuestion = previousQuestion;
	}	
}
