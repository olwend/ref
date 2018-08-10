package uk.ac.nhm.core.services;

import java.util.List;
import java.util.Map;

public interface QAService {
	
	public Map<String, String> getNextQuestion(Integer position,List<Map<String, String>> questionList);
	
	public Map<String, String> getPreviousQuestion(Integer position, List<Map<String,String>> questionList);
}
