package uk.ac.nhm.core.model.slingModels.dinoDirectory;

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
public class Dinosaur {

	private final static Logger LOG = LoggerFactory.getLogger(Dinosaur.class);
	
	@Inject
	private String name;
	
	private String genus;
	private String diet;
	private String imageUrl;
	
	private static final String BASE_URL = "http://staging.nhm.ac.uk/api/dino-directory-api/dinosaur/name";
	
	@PostConstruct
	protected void init() {
		String requestUrl = BASE_URL + "/" + name;
		
		HttpClient httpClient = new HttpClient();
		GetMethod getMethod = new GetMethod(requestUrl);
		
		LOG.info(requestUrl);
		
		try {
			httpClient.executeMethod(getMethod);
			JSONObject dinosaur = new JSONObject(getMethod.getResponseBodyAsString());
			JSONObject dinosaurMedia = dinosaur.getJSONArray("mediaCollection").getJSONObject(0);
			
			this.setGenus(dinosaur.getString("genus"));
			this.setDiet(dinosaur.getString("dietTypeName"));
			
			this.setImageUrl("http://www.nhm.ac.uk/resources/nature-online/life/dinosaurs/dinosaur-directory"
					+ dinosaurMedia.getString("mediaTypePath")
					+ dinosaurMedia.getString("mediaContentTypePath")
					+ "/small/" + dinosaurMedia.getString("identifier") + ".jpg");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getGenus() {
		return genus;
	}

	public void setGenus(String genus) {
		this.genus = genus;
	}

	public String getDiet() {
		return diet;
	}

	public void setDiet(String diet) {
		this.diet = diet;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
}
