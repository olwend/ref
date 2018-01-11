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
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Model(adaptables = Resource.class)
public class DinosaurSearch {

	private final static Logger LOG = LoggerFactory.getLogger(DinosaurSearch.class);
	
	//Filter one is top level filter - country, body shape
	@Inject
	private String filterOne;
	
	//Filter two is second level filter - Scotland, sauropod
	@Inject
	private String filterTwo;
	
	private List<Map<String, String>> dinosaurList = null;
	
	private static final String BASE_URL = "http://staging.nhm.ac.uk/api/dino-directory-api/dinosaur";
	
	@PostConstruct
	protected void init() {
		String requestUrl = BASE_URL + "/" + filterOne + "/" + filterTwo;
		
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
				dinosaurMap.put("bodyShape", dinosaurs.getJSONObject(i).getJSONObject("bodyShape").getString("bodyShape"));
				dinosaurList.add(dinosaurMap);
			}
//			JSONObject dinosaurMedia = dinosaur.getJSONArray("mediaCollection").getJSONObject(0);
//			
//			this.setGenus(dinosaur.getString("genus"));
//			this.setDiet(dinosaur.getString("dietTypeName"));
//			
//			this.setImageUrl("http://www.nhm.ac.uk/resources/nature-online/life/dinosaurs/dinosaur-directory"
//					+ dinosaurMedia.getString("mediaTypePath")
//					+ dinosaurMedia.getString("mediaContentTypePath")
//					+ "/small/" + dinosaurMedia.getString("identifier") + ".jpg");
			
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
