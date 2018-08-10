package uk.ac.nhm.core.impl.services;

import java.util.List;
import java.util.Map;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.nhm.core.services.QAService;

@Component
@Service
public class QAServiceImpl implements QAService {

	private final static Logger LOG = LoggerFactory.getLogger(QAServiceImpl.class);
	
	@Override
	public Map<String, String> getNextQuestion(Integer position,List<Map<String, String>> questionList){
		Map<String, String> nextQuestion = null;
		//List<Map<String, String>> questionList = getQuestionData(rootPath,tags);
		if(questionList.size() == 1 || position == null) {
			//if there is only one question or the position is null then there is no next question
			nextQuestion = null;
		} else {
			for(int i=0; i<questionList.size(); i++) {
				Map<String, String> currentQuestion  = questionList.get(i);
				Integer currentPosition = Integer.parseInt(currentQuestion.get("position"));
				if(nextQuestion == null && currentPosition > position) {
					nextQuestion = currentQuestion;
				}
			}
			if(nextQuestion == null) {
				//if there is no question with a larger position then we set the next question as the first question in the list
				nextQuestion = questionList.get(0);
			}
		}
		
		return nextQuestion;	
	}
	
	@Override
	public Map<String, String> getPreviousQuestion(Integer position, List<Map<String, String>> questionList){
		Map<String, String> previousQuestion = null;
		if(questionList.size() < 2) {
			previousQuestion = null;
		} else {
			for(int i=questionList.size()-1; i>=0; i--) {
				Map<String, String> currentQuestion  = questionList.get(i);
				Integer currentPosition = Integer.parseInt(currentQuestion.get("position"));
				if(previousQuestion == null && currentPosition < position) {
					previousQuestion = currentQuestion;
				}
			}
			if(previousQuestion == null) {
				//if there is no question with a smaller position then we set the previous question as the last question in the list
				previousQuestion = questionList.get(questionList.size()-1);
			}
		}
		
		return previousQuestion;
	}
	
}