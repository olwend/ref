package uk.ac.nhm.core.services;

import java.util.List;
import java.util.Map;

public interface QAHubService {

	public List<Map<String, String>> getQuestionData(String rootPath, String[] tags);
}
