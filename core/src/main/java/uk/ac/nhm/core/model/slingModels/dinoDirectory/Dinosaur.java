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
public class Dinosaur {

	private final static Logger LOG = LoggerFactory.getLogger(Dinosaur.class);
	
	@Inject
	private String name;
	
	private String country;
	private String description;
	private String diet;
	private String genus;
	private String mya;
	private String nameMeaning;
	private String namePronounciation;
	private String namedBy;
	private String period;
	private String taxonomy;
	private List<Map<String, String>> textBlockCollection;
	private String type;
	
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
			
			//Country
			JSONArray countries = dinosaur.getJSONArray("countries");
			StringBuffer countriesBuffer = new StringBuffer();
			for(int i=0; i<countries.length(); i++) {
				JSONObject country = countries.getJSONObject(i);
				
				if(i == (countries.length() - 1) && countries.length() > 1) {
					countriesBuffer.append(" and " + country.getString("country"));
				} else if(i > 0) {
					countriesBuffer.append(", " + country.getString("country"));
				} else {
					countriesBuffer.append(country.getString("country"));
				}
			}

			this.setCountry(countriesBuffer.toString());
			
			//Description
			JSONObject bodyShape = dinosaur.getJSONObject("bodyShape");
			
			int mass = 0;			
			if(!dinosaur.isNull("massFrom")) {
				mass = dinosaur.getInt("massFrom");
			} else if(!dinosaur.isNull("massTo")) {
				mass = dinosaur.getInt("massTo");
			}
			
			int length = 0;
			if(!dinosaur.isNull("lengthFrom")) {
				length = dinosaur.getInt("lengthFrom");
			} else if(!dinosaur.isNull("lengthTo")) {
				length = dinosaur.getInt("lengthTo");
			}
			
			StringBuffer descriptionBuffer = new StringBuffer();
			
			if(mass > 0) {
				descriptionBuffer.append(String.valueOf(mass) + "kg");
			}
			if(length > 0) {
				if(mass > 0) {
					descriptionBuffer.append(", " + String.valueOf(length) + "m");
				} else {
					descriptionBuffer.append(String.valueOf(length) + "m");
				}
			}
			
			descriptionBuffer.append(" " + bodyShape.getString("bodyShape").toLowerCase());
			this.setDescription(descriptionBuffer.toString());
			
			this.setDiet(dinosaur.getString("dietTypeName"));
			this.setGenus(dinosaur.getString("genus"));
			this.setNameMeaning(dinosaur.getString("nameMeaning"));
			this.setNamePronounciation(dinosaur.getString("namePronounciation"));
			this.setNamedBy(dinosaur.getString("genusNamedBy") + " (" + String.valueOf(dinosaur.getInt("genusYear")) + ")");
			
			//Period
			JSONObject period = dinosaur.getJSONObject("period");
			this.setPeriod(period.getString("period"));
			
			String myaFrom = String.valueOf(dinosaur.getInt("myaFrom"));
			String myaTo = String.valueOf(dinosaur.getInt("myaTo"));
			
			String mya = null;
			
			if(!myaFrom.equals(null) && !myaTo.equals(null)) {
				mya = myaFrom + " - " + myaTo + " million years ago";
			} else if(!myaFrom.equals(null)) {
				mya = myaFrom + " million years ago";
			} else if(!myaTo.equals(null)) {
				mya = myaTo + " million years ago";
			}
			
			this.setMya(mya);
			
			//Taxonomy
			JSONObject taxonomy = dinosaur.getJSONObject("taxTaxon");
			this.setTaxonomy(taxonomy.getString("taxonomyCSV").replaceAll(",", ", "));
			this.setType(taxonomy.getString("taxon"));
			
			this.setImageUrl("http://www.nhm.ac.uk/resources/nature-online/life/dinosaurs/dinosaur-directory/"
					+ dinosaurMedia.getString("mediaTypePath") + "/"
					+ dinosaurMedia.getString("mediaContentTypeName")
					+ "/small/" + dinosaurMedia.getString("identifier") + ".jpg");
			
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
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDiet() {
		return diet;
	}

	public void setDiet(String diet) {
		this.diet = diet;
	}

	public String getGenus() {
		return genus;
	}

	public void setGenus(String genus) {
		this.genus = genus;
	}

	public String getMya() {
		return mya;
	}

	public void setMya(String mya) {
		this.mya = mya;
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

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getTaxonomy() {
		return taxonomy;
	}

	public void setTaxonomy(String taxonomy) {
		this.taxonomy = taxonomy;
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

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
}
