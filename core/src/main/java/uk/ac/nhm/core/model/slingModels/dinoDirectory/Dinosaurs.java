package uk.ac.nhm.core.model.slingModels.dinoDirectory;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Model(adaptables = SlingHttpServletRequest.class)
public class Dinosaurs {

	private final static Logger LOG = LoggerFactory.getLogger(Dinosaurs.class);
	
	@Inject
	SlingHttpServletRequest request;

	private List<String> names;
	
	private static final String BASE_URL = "http://staging.nhm.ac.uk/api/dino-directory-api/dinosaur";
	
	@PostConstruct
	protected void init() {
		String data = request.getParameter("data");
		String filter = request.getParameter("filter");
		
		if(filter != null && data != null) {
			String requestUrl = BASE_URL + "/" + filter + "/" + data;
			
			List<String> namesb = new ArrayList<String>();
			
			HttpClient httpClient = new HttpClient();
			GetMethod getMethod = new GetMethod(requestUrl);
			
			LOG.error(requestUrl);
			
			try {
				httpClient.executeMethod(getMethod);
				JSONArray dinosaurs = new JSONArray(getMethod.getResponseBodyAsString());
				
				for(int i=0; i<dinosaurs.length(); i++) {
					JSONObject dinosaur = dinosaurs.getJSONObject(i);
					namesb.add(dinosaur.getString("genus"));
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			this.setNames(namesb);
		}
	}
	
	public List<String> getNames() {
		return names;
	}

	public void setNames(List<String> names) {
		this.names = names;
	}
	
}
