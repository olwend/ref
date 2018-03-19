package uk.ac.nhm.core.impl.services.dinoDirectory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang3.text.WordUtils;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.nhm.core.services.dinoDirectory.DinoDirectoryDinosaurSearchService;

@Component
@Service
public class DinoDirectoryDinosaurSearchServiceImpl implements DinoDirectoryDinosaurSearchService {

	private final static Logger LOG = LoggerFactory.getLogger(DinoDirectoryDinosaurSearchServiceImpl.class);

	private final static String BASE_CONTENT_URL = "/content/nhmwww/en/home/discover/dino-directory/";

	private HttpClient httpClient = new HttpClient();

	public List<Map<String, String>> getDinosaurList(String filterOne, String filterTwo, String environmentUrl) {
		List<Map<String, String>> dinosaurList = new ArrayList<Map<String, String>>();

		final String BASE_URL = environmentUrl;

		String requestUrl = BASE_URL + "/dinosaur/" + filterOne + "/" + filterTwo;

		GetMethod getMethod = new GetMethod(requestUrl);

		LOG.info(requestUrl);

		try {
			httpClient.executeMethod(getMethod);
			JSONArray dinosaurs = new JSONArray(getMethod.getResponseBodyAsString());
			for(int i=0; i<dinosaurs.length(); i++) {
				Map<String, String> dinosaurMap = new HashMap<String, String>();

				dinosaurMap.put("genus", dinosaurs.getJSONObject(i).getString("genus"));
				dinosaurMap.put("url", BASE_CONTENT_URL + dinosaurs.getJSONObject(i).getString("genus").toLowerCase() + ".html");

				JSONObject dinosaurMedia = dinosaurs.getJSONObject(i).getJSONArray("mediaCollection").getJSONObject(0);

				String imageUrl = "http://www.nhm.ac.uk/resources/nature-online/life/dinosaurs/dinosaur-directory/images/reconstruction/thumb/"
						+ dinosaurMedia.getString("identifier") + ".gif";

				dinosaurMap.put("imageUrl", imageUrl);

				dinosaurList.add(dinosaurMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return dinosaurList;
	}

	@Override
	public String getTitle(String filterOne, String filterTwo) {
		//Filter two value has been modified to create a string that works for API calls.
		//Need to turn it back into presentable string.

		String title = filterTwo.replaceAll("%20", " ");
		title = title.replaceAll("-", " ");

		if(filterOne.equals("bodyshape")) {
			title = WordUtils.capitalizeFully(title);
			title = title + " dinosaurs";
		}

		if(filterOne.equals("country")) {
			title = WordUtils.capitalizeFully(title);
			title = "Dinosaurs in " + title;
		}

		if(filterOne.equals("initial")) {
			title = "Dinosaurs beginning with " + title.toUpperCase();
		}

		if(filterOne.equals("period")) {
			title = WordUtils.capitalizeFully(title);
			title = title.substring(0, 1).toLowerCase() + title.substring(1);
			title = "Dinosaurs in the " + title;
		}

		return title;
	}

	@Override
	public String getDescription(String filterOne, String filterTwo, String environmentUrl) {

		final String BASE_URL = environmentUrl;

		String description = null;
		String requestUrl = BASE_URL + "/" + filterOne + "/";
		filterTwo = filterTwo.replaceAll("%20", " ").replaceAll("-", "").replaceAll(" ", "").toLowerCase();

		GetMethod getMethod = new GetMethod(requestUrl);

		LOG.info(requestUrl);

		try {
			httpClient.executeMethod(getMethod);
			JSONArray filterItems = new JSONArray(getMethod.getResponseBodyAsString());
			for(int i=0; i<filterItems.length(); i++) {

				JSONObject filterItem = filterItems.getJSONObject(i);

				if(filterOne.equals("bodyshape")) {
					String filterItemTitle = filterItem.getString("bodyShape").replaceAll("-", "").replaceAll(" ", "").toLowerCase();
					if(filterItemTitle.equals(filterTwo)) {
						description = filterItem.getString("description");
					}
				}

				if(filterOne.equals("country")) {
					String filterItemTitle = filterItem.getString("country").replaceAll("-", "").replaceAll(" ", "").toLowerCase();
					if(filterItemTitle.equals(filterTwo)) {
						if(filterItem.getInt("dinosaurCount") > 1) {
							description = String.valueOf(filterItem.getInt("dinosaurCount")) + " dinosaurs found in " + filterItem.getString("country");
						} else {
							description = String.valueOf(filterItem.getInt("dinosaurCount")) + " dinosaur found in " + filterItem.getString("country");
						}
					}
				}

				if(filterOne.equals("period")) {

				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		//Transform description
		description = description.substring(0, 1).toUpperCase() + description.substring(1);

		if(filterOne.equals("bodyshape")) {
			description = description + ".";
		}

		return description;
	}

}
