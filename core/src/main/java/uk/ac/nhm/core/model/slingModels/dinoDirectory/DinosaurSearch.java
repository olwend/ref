package uk.ac.nhm.core.model.slingModels.dinoDirectory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Source;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.nhm.core.services.DinoDirectoryEnvironmentService;

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
		
		this.setDinosaurList(dinosaurList);
	}

	public List<Map<String, String>> getDinosaurList() {
		return dinosaurList;
	}

	public void setDinosaurList(List<Map<String, String>> dinosaurList) {
		this.dinosaurList = dinosaurList;
	}
	
}
