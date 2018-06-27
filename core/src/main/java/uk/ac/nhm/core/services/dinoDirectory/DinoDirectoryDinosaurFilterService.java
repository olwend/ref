package uk.ac.nhm.core.services.dinoDirectory;

import java.util.List;
import java.util.Map;

public interface DinoDirectoryDinosaurFilterService {

	public List<Map<String, String>> getDinosaurList(String filterOne, String filterTwo, String environmentUrl);

	public String getTitle(String filterOne, String filterTwo);

	public String getDescription(String filterOne, String filterTwo, String environmentUrl);
}
