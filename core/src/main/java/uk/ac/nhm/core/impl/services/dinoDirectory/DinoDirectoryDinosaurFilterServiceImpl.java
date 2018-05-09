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

import uk.ac.nhm.core.services.dinoDirectory.DinoDirectoryDinosaurFilterService;

@Component
@Service
public class DinoDirectoryDinosaurFilterServiceImpl implements DinoDirectoryDinosaurFilterService {

	private final static Logger LOG = LoggerFactory.getLogger(DinoDirectoryDinosaurFilterServiceImpl.class);

	private final static String BASE_CONTENT_URL = "/content/nhmwww/en/home/discover/dino-directory/";

	private HttpClient httpClient = new HttpClient();

	public List<Map<String, String>> getDinosaurList(String filterOne, String filterTwo, String environmentUrl) {
		List<Map<String, String>> dinosaurList = new ArrayList<Map<String, String>>();

		final String BASE_URL = environmentUrl;

		String requestUrl = BASE_URL + "/" + filterOne + "/" + filterTwo + "/dinosaurs";

		GetMethod getMethod = new GetMethod(requestUrl);

		LOG.info(requestUrl);

		try {
			httpClient.executeMethod(getMethod);
			JSONArray dinosaurs = new JSONArray(getMethod.getResponseBodyAsString());
			for(int i=0; i<dinosaurs.length(); i++) {
				if(dinosaurs.getJSONObject(i).getBoolean("publish") == true) {
					Map<String, String> dinosaurMap = new HashMap<String, String>();

					dinosaurMap.put("genus", dinosaurs.getJSONObject(i).getString("genus"));
					dinosaurMap.put("url", BASE_CONTENT_URL + dinosaurs.getJSONObject(i).getString("genus").toLowerCase().replaceAll(" ", "") + ".html");

					if(!dinosaurs.getJSONObject(i).isNull("nameHyphenated")) {
						dinosaurMap.put("nameHyphenated", dinosaurs.getJSONObject(i).getString("nameHyphenated"));
					} else {
						dinosaurMap.put("nameHyphenated", null);
					}

					JSONObject dinosaurMedia = dinosaurs.getJSONObject(i).getJSONArray("mediaCollection").getJSONObject(0);

					String imageUrl = "http://www.nhm.ac.uk/resources/nature-online/life/dinosaurs/dinosaur-directory/images/reconstruction/small/"
							+ dinosaurMedia.getString("identifier") + ".jpg";

					dinosaurMap.put("imageUrl", imageUrl);

					dinosaurList.add(dinosaurMap);
				}
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

		if(filterOne.equals("body-shapes")) {
			title = WordUtils.capitalizeFully(title);
			title = title + " dinosaurs";
		}

		if(filterOne.equals("countries")) {
			title = WordUtils.capitalizeFully(title);
			title = "Dinosaurs in " + title;
		}

		if(filterOne.equals("initials")) {
			title = "Dinosaurs beginning with " + title.toUpperCase();
		}

		if(filterOne.equals("periods")) {
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
		String requestUrl = BASE_URL + "/" + filterOne + "/" + filterTwo;
		filterTwo = filterTwo.replaceAll("%20", " ").replaceAll("-", "").replaceAll(" ", "").toLowerCase();

		GetMethod getMethod = new GetMethod(requestUrl);

		LOG.info(requestUrl);

		try {
			httpClient.executeMethod(getMethod);
			JSONObject filterItem = new JSONObject(getMethod.getResponseBodyAsString());

			if(filterOne.equals("body-shapes")) {
				description = filterItem.getString("description");
			}

			if(filterOne.equals("countries")) {
				if(filterItem.getInt("dinosaurCount") > 1) {
					description = String.valueOf(filterItem.getInt("dinosaurCount")) + " dinosaurs found in " + filterItem.getString("country");
				} else {
					description = String.valueOf(filterItem.getInt("dinosaurCount")) + " dinosaur found in " + filterItem.getString("country");
				}
			}

			if(filterOne.equals("periods")) {
				if(filterItem.getInt("dinosaurCount") > 1) {
					description = "(" + String.valueOf(filterItem.getInt("myaFrom")) + " to " + String.valueOf(filterItem.getInt("myaTo")) + " million years ago)<br>"
						+ String.valueOf(filterItem.getInt("dinosaurCount")) + " dinosaurs from the " + filterItem.getString("period");
				} else {
					description = "(" + String.valueOf(filterItem.getInt("myaFrom")) + " to " + String.valueOf(filterItem.getInt("myaTo")) + " million years ago)<br>"
						+ String.valueOf(filterItem.getInt("dinosaurCount")) + " dinosaur from the " + filterItem.getString("period");
				}
			}

			if(filterOne.equals("initials")) {
				if(filterItem.getInt("dinosaurCount") > 1) {
					description = String.valueOf(filterItem.getInt("dinosaurCount")) + " dinosaurs beginning with " + filterItem.getString("initial").toUpperCase();
				} else {
					description = String.valueOf(filterItem.getInt("dinosaurCount")) + " dinosaur beginning with " + filterItem.getString("initial").toUpperCase();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		//Transform description
		description = description.substring(0, 1).toUpperCase() + description.substring(1);

		if(filterOne.equals("body-shapes")) {
			description = description + ".";
		}

		return description;
	}

}
