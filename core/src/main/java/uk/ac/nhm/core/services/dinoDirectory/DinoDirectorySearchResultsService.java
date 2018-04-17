package uk.ac.nhm.core.services.dinoDirectory;

import java.util.List;
import java.util.Map;

public interface DinoDirectorySearchResultsService {

	public List<Map<String, String>> getSearchResults(String searchValue);
	
}
