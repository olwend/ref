package uk.ac.nhm.core.model.slingModels.dinoDirectory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Source;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.nhm.core.services.dinoDirectory.DinoDirectoryEnvironmentService;

@Model(adaptables = Resource.class)
public class DinosaurSearch {

	private final static Logger LOG = LoggerFactory.getLogger(DinosaurSearch.class);
	
	@Inject
	@Source("osgi-services")
	DinoDirectoryEnvironmentService service;
	
	//Filter one is top level filter - country, body shape
	@Inject
	private String filterOne;
	
	//Filter two is second level filter - Scotland, sauropod
	@Inject
	private String filterTwo;
	
	private final static String BASE_CONTENT_URL = "/content/nhmwww/en/home/discover/dino-directory/";
	
	private String title;
	private String description;
	private List<Map<String, String>> dinosaurList = null;
	
	@PostConstruct
	protected void init() {
		final String BASE_URL = service.getDinoDirectoryUrl();
		
		String requestUrl = BASE_URL + "/dinosaur/" + filterOne + "/" + filterTwo;
		
		HttpClient httpClient = new HttpClient();
		GetMethod getMethod = new GetMethod(requestUrl);
		
		LOG.info(requestUrl);

		dinosaurList = new ArrayList<Map<String, String>>();
		
		try {
			//Filter two value has been modified to create a string that works for API calls.
			//Need to turn it back into presentable string.
			title = filterTwo.replaceAll("%20", " ");
			title = title.replaceAll("-", " ");
			title = title.substring(0, 1).toUpperCase() + title.substring(1);
	
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
				
				//Need to modify title and description based on filter one value.
				//Body shape
				if(filterOne.equals("bodyshape") && i == (dinosaurs.length() - 1)) {
					description = dinosaurs.getJSONObject(i).getJSONObject("bodyShape").getString("description");
					description = description.substring(0, 1).toUpperCase() + description.substring(1);
					title = title + "s";
				}
				
				//country
				
				if(filterOne.equals("initial")) {
					title = "Dinosaurs beginning with " + title;
				}
				
				if(filterOne.equals("period")) {
					title = title + " period";
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.setTitle(title);
		this.setDescription(description);
		this.setDinosaurList(dinosaurList);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Map<String, String>> getDinosaurList() {
		return dinosaurList;
	}

	public void setDinosaurList(List<Map<String, String>> dinosaurList) {
		this.dinosaurList = dinosaurList;
	}
	
}
