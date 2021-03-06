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
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.nhm.core.services.dinoDirectory.DinoDirectoryEnvironmentService;

@Model(adaptables = Resource.class)
public class Dinosaur {

	private final static Logger LOG = LoggerFactory.getLogger(Dinosaur.class);

	@Inject
	@Source("osgi-services")
	DinoDirectoryEnvironmentService service;

	@Inject
	private String name;
	
	private Map<String, String> bodyShape;
	private List<Map<String, String>> countryList;
	private String diet;
	private List<Map<String, String>> dinosaurMediaCollection;
	private String food;
	private String genus;
	private String howItMoved;
	private String imageUrl;
	private String imageCredit;
	private double length;
	private int mass;
	private String mya;
	private String nameHyphenated;
	private String nameMeaning;
	private String namePronounciation;
	private String namedBy;
	private Map<String, String> period;
	private String taxonomy;
	private String teeth;
	private List<Map<String, String>> textBlockCollection;
	private String type;

	private static final String BASE_IMAGE_URL = "http://www.nhm.ac.uk/resources/nature-online/life/dinosaurs/dinosaur-directory/";

	@PostConstruct
	protected void init() {
		String host = getHost();
		
		try {
			JSONObject dinosaur = getDinosaurJson();
			setModelVariables(dinosaur, host);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setModelVariables(JSONObject dinosaurJson, String host) throws JSONException {
		JSONObject dinosaur = dinosaurJson;
		
		//Country
		if(!dinosaur.isNull("countries")) {
			List<Map<String, String>> countryList = new ArrayList<Map<String, String>>();
			JSONArray countries = dinosaur.getJSONArray("countries");

			for(int i=0; i<countries.length(); i++) {
				Map<String, String> countryMap = new HashMap<String, String>();
				JSONObject country = countries.getJSONObject(i);

				countryMap.put("name", country.getString("country"));
				countryMap.put("url", "http://" + host + ".nhm.ac.uk/discover/dino-directory/country/" 
						+ country.getString("country").toLowerCase().replaceAll(" ", "-") + "/gallery.html");
				countryList.add(countryMap);
			}
			
			this.setCountryList(countryList);
		}

		//Mass and length
		int massValue = 0;
		if(!dinosaur.isNull("massFrom")) {
			massValue = dinosaur.getInt("massFrom");
		} else if(!dinosaur.isNull("massTo")) {
			massValue = dinosaur.getInt("massTo");
		}
		
		this.setMass(massValue);

		double lengthValue = 0;
		if(!dinosaur.isNull("lengthFrom")) {
			lengthValue = dinosaur.getDouble("lengthFrom");
		} else if(!dinosaur.isNull("lengthTo")) {
			lengthValue = dinosaur.getDouble("lengthTo");
		}

		this.setLength(lengthValue);
		
		//Body shape
		if(!dinosaur.isNull("bodyShape")) {
			JSONObject bodyShape = dinosaur.getJSONObject("bodyShape");
			Map<String, String> bodyShapeMap = new HashMap<String, String>();

			if(lengthValue > 0) {
				bodyShapeMap.put("name", bodyShape.getString("bodyShape").toLowerCase());
			} else {
				bodyShapeMap.put("name", bodyShape.getString("bodyShape").toLowerCase());
			}
			bodyShapeMap.put("url", "http://" + host + ".nhm.ac.uk/discover/dino-directory/body-shape/" 
					+ bodyShape.getString("bodyShape").toLowerCase().replaceAll(" ", "-") + "/gallery.html");

			this.setBodyShape(bodyShapeMap);
		}
		
		if(!dinosaur.isNull("dietTypeName")) {
			this.setDiet(dinosaur.getString("dietTypeName"));
		}
		
		if(!dinosaur.isNull("nameHyphenated")) {
			this.setNameHyphenated(dinosaur.getString("nameHyphenated"));
		} else {
			this.setNameHyphenated(dinosaur.getString("genus"));
		}
		
		this.setGenus(dinosaur.getString("genus"));
		this.setNameMeaning(dinosaur.getString("nameMeaning"));
		this.setNamePronounciation(dinosaur.getString("namePronounciation"));
		
		if(!dinosaur.isNull("genusNamedBy") && !dinosaur.isNull("genusYear")){
			this.setNamedBy(dinosaur.getString("genusNamedBy") + " (" + String.valueOf(dinosaur.getInt("genusYear")) + ")");
		} else if (!dinosaur.isNull("genusNamedBy")) {
			this.setNamedBy(dinosaur.getString("genusNamedBy"));
		}

		//Period
		if(!dinosaur.isNull("period")) {
			JSONObject period = dinosaur.getJSONObject("period");
			Map<String, String> periodMap = new HashMap<String, String>();
	
			periodMap.put("name", period.getString("period"));
			periodMap.put("url", "http://" + host + ".nhm.ac.uk/discover/dino-directory/timeline/" + period.getString("period").toLowerCase().replaceAll(" ", "-") + "/gallery.html");
	
			this.setPeriod(periodMap);
		}
	
		String myaFrom = null;
		String myaTo = null;
		String mya = null;
		
		if(!dinosaur.isNull("myaFrom")) {
			myaFrom = String.valueOf(dinosaur.getInt("myaFrom"));
		}

		if(!dinosaur.isNull("myaTo")) {
			myaTo = String.valueOf(dinosaur.getInt("myaTo"));
		}
		
		if(!(myaFrom == null) && !(myaTo == null)) {
			mya = myaFrom + "-" + myaTo + " million years ago";
		} else if(!(myaFrom == null)) {
			mya = myaFrom + " million years ago";
		} else if(!(myaTo == null)) {
			mya = myaTo + " million years ago";
		}

		this.setMya(mya);
		
		//Images
		JSONArray dinosaurMediaArray = dinosaur.getJSONArray("mediaCollection");			
		List<Map<String,String>> dinosaurMediaCollection = new ArrayList<Map<String, String>>();
		for(int i=0; i<dinosaurMediaArray.length(); i++) {
			JSONObject dinosaurMediaElement = dinosaurMediaArray.getJSONObject(i);
			String dinosaurImageURL = getImagePath(dinosaurMediaElement);
			String dinosaurImageCopyright = null;
			String dinosaurImageCredit = null;
			String dinosaurImageCreditType = null;
			
			if(dinosaurMediaElement.isNull("copyright")) {
					dinosaurImageCredit = null;
			}else {
				dinosaurImageCopyright = dinosaurMediaElement.getString("copyright");
				if(!dinosaurMediaElement.isNull("creditType")) {
					dinosaurImageCreditType = dinosaurMediaElement.getString("creditType");
					if(dinosaurImageCreditType.equals("Copyright")) {
						dinosaurImageCredit = "\u00a9 " + dinosaurImageCopyright;
					} else {
						dinosaurImageCredit = "Credit: " + dinosaurImageCopyright;
					}
				} else {
					dinosaurImageCredit = null;
				}
			}		
			if(dinosaurMediaElement.getBoolean("isDefault") == true) {
				this.setImageUrl(dinosaurImageURL);
				this.setImageCredit(dinosaurImageCredit);
			} else {
				Map<String, String> dinosaurImageMap = new HashMap<String, String>();
				dinosaurImageMap.put("url", dinosaurImageURL);	
				dinosaurImageMap.put("credit", dinosaurImageCredit);
				dinosaurMediaCollection.add(dinosaurImageMap);
			}

		}
		this.setDinosaurMediaCollection(dinosaurMediaCollection);

		//Taxonomy
		if(!dinosaur.isNull("taxTaxon")) {
			JSONObject taxonomy = dinosaur.getJSONObject("taxTaxon");
			this.setTaxonomy(taxonomy.getString("taxonomyCSV").replaceAll(",", ", "));
			this.setType(taxonomy.getString("taxon"));
		}

		//Text block
		JSONArray textBlockArray = dinosaur.getJSONArray("textBlockCollection");
		List<Map<String, String>> textBlockCollection = new ArrayList<Map<String, String>>();

		for(int i=0; i<textBlockArray.length(); i++) {
			JSONObject textBlock = textBlockArray.getJSONObject(i);

			String identifier = textBlock.getString("identifier");
			if(identifier.equals("detail")) {
				Map<String, String> textBlockMap = new HashMap<String, String>();

				if(!textBlock.isNull("title")) {
					textBlockMap.put("title", textBlock.getString("title"));
				}
				if(!textBlock.isNull("textBlock")) {
					textBlockMap.put("textBlock", textBlock.getString("textBlock"));
				}

				textBlockCollection.add(textBlockMap);
			}
		}
		this.setTextBlockCollection(textBlockCollection);

		//Optional fields
		
		if(!dinosaur.isNull("dentition")) {
			String teeth = dinosaur.getString("dentition");
			this.setTeeth(teeth);
		}

		if(!dinosaur.isNull("locomotion")) {
			String howItMoved = dinosaur.getString("locomotion");
			this.setHowItMoved(howItMoved);
		}
		
		if(!dinosaur.isNull("diet")) {
			String food = dinosaur.getString("diet");
			this.setFood(food);
		}
	}
	
	protected JSONObject getDinosaurJson() {
		final String BASE_URL = service.getDinoDirectoryUrl();

		String requestUrl = BASE_URL + "/dinosaurs/" + name;

		HttpClient httpClient = new HttpClient();
		GetMethod getMethod = new GetMethod(requestUrl);

		LOG.info(requestUrl);

		JSONObject dinosaur = null;
		
		try {
			httpClient.executeMethod(getMethod);
			dinosaur = new JSONObject(getMethod.getResponseBodyAsString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return dinosaur;
	}
	
	private String getHost() {
		if(service.getDinoDirectoryUrl().contains("staging")) {
			return("staging");
		} else {
			return("www");
		}
	}
	
	public String getImagePath(JSONObject dinosaurMediaElement) throws JSONException {
		String imagePath = 
				BASE_IMAGE_URL
				+ dinosaurMediaElement.getString("mediaTypePath") 
				+ "/" + dinosaurMediaElement.getString("mediaContentTypeName")
				+ "/small/" + dinosaurMediaElement.getString("identifier") + ".jpg";
		
		return imagePath;
	}
	
	public Map<String, String> getBodyShape() {
		return bodyShape;
	}

	public void setBodyShape(Map<String, String> bodyShape) {
		this.bodyShape = bodyShape;
	}

	public List<Map<String, String>> getCountryList() {
		return countryList;
	}

	public void setCountryList(List<Map<String, String>> countryList) {
		this.countryList = countryList;
	}

	public String getDiet() {
		return diet;
	}

	public void setDiet(String diet) {
		this.diet = diet;
	}
	
	public String getFood() {
		return food;
	}

	public void setFood(String food) {
		this.food = food;
	}

	public String getGenus() {
		return genus;
	}

	public void setGenus(String genus) {
		this.genus = genus;
	}

	public String getHowItMoved() {
		return howItMoved;
	}

	public void setHowItMoved(String howItMoved) {
		this.howItMoved = howItMoved;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}
	
	public int getMass() {
		return mass;
	}

	public void setMass(int mass) {
		this.mass = mass;
	}

	public String getMya() {
		return mya;
	}

	public void setMya(String mya) {
		this.mya = mya;
	}

	public String getNameHyphenated() {
		return nameHyphenated;
	}

	public void setNameHyphenated(String nameHyphenated) {
		this.nameHyphenated = nameHyphenated;
	}

	public String getNameMeaning() {
		return nameMeaning;
	}

	public void setNameMeaning(String nameMeaning) {
		this.nameMeaning = nameMeaning;
	}

	public String getNamePronounciation() {
		return namePronounciation;
	}

	public void setNamePronounciation(String namePronounciation) {
		this.namePronounciation = namePronounciation;
	}

	public String getNamedBy() {
		return namedBy;
	}

	public void setNamedBy(String namedBy) {
		this.namedBy = namedBy;
	}

	public Map<String, String> getPeriod() {
		return period;
	}

	public void setPeriod(Map<String, String> period) {
		this.period = period;
	}

	public String getTaxonomy() {
		return taxonomy;
	}

	public void setTaxonomy(String taxonomy) {
		this.taxonomy = taxonomy;
	}

	public String getTeeth() {
		return teeth;
	}

	public void setTeeth(String teeth) {
		this.teeth = teeth;
	}

	public List<Map<String, String>> getTextBlockCollection() {
		return textBlockCollection;
	}

	public void setTextBlockCollection(List<Map<String, String>> textBlockCollection) {
		this.textBlockCollection = textBlockCollection;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Map<String, String>> getDinosaurMediaCollection() {
		return dinosaurMediaCollection;
	}

	public void setDinosaurMediaCollection(List<Map<String, String>> dinosaurMediaCollection) {
		this.dinosaurMediaCollection = dinosaurMediaCollection;
	}

	public String getImageCredit() {
		return imageCredit;
	}

	public void setImageCredit(String imageCredit) {
		this.imageCredit = imageCredit;
	}


	
 
}