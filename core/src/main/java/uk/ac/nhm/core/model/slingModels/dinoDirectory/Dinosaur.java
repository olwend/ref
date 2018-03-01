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
	private String description;
	private double length;
	private String diet;
	private String genus;
	private String mya;
	private String nameMeaning;
	private String namePronounciation;
	private String namedBy;
	private Map<String, String> period;
	private String taxonomy;
	private List<Map<String, String>> textBlockCollection;
	private String type;

	private String imageUrl;
	private String imageUrl2;

	private JSONObject dinosaurMedia2;

	@PostConstruct
	protected void init() {
		String host = null;
		if(service.getDinoDirectoryUrl().contains("staging")) {
			host = "staging";
		} else {
			host = "www";
		}

		final String BASE_URL = service.getDinoDirectoryUrl();

		String requestUrl = BASE_URL + "/dinosaur/name/" + name;

		HttpClient httpClient = new HttpClient();
		GetMethod getMethod = new GetMethod(requestUrl);

		LOG.info(requestUrl);

		try {
			httpClient.executeMethod(getMethod);
			JSONObject dinosaur = new JSONObject(getMethod.getResponseBodyAsString());
			JSONObject dinosaurMedia = dinosaur.getJSONArray("mediaCollection").getJSONObject(0);

			if ( dinosaur.getJSONArray("mediaCollection").length() > 1 ) {
				dinosaurMedia2 = dinosaur.getJSONArray("mediaCollection").getJSONObject(1);
			}

			//Country
			List<Map<String, String>> countryList = new ArrayList<Map<String, String>>();
			JSONArray countries = dinosaur.getJSONArray("countries");

			for(int i=0; i<countries.length(); i++) {
				Map<String, String> countryMap = new HashMap<String, String>();
				JSONObject country = countries.getJSONObject(i);

				countryMap.put("name", country.getString("country"));
				countryMap.put("url", "http://" + host + ".nhm.ac.uk/discover/dino-directory/country/" + country.getString("country") + "/gallery.html");
				countryList.add(countryMap);
			}

			this.setCountryList(countryList);

			//Body shape
			JSONObject bodyShape = dinosaur.getJSONObject("bodyShape");
			Map<String, String> bodyShapeMap = new HashMap<String, String>();

			bodyShapeMap.put("name", bodyShape.getString("bodyShape").toLowerCase());
			bodyShapeMap.put("url", "http://" + host + ".nhm.ac.uk/discover/dino-directory/body-shape/" + bodyShape.getString("bodyShape").toLowerCase().replaceAll(" ", "-") + ".html");

			this.setBodyShape(bodyShapeMap);

			//Description
			int mass = 0;
			if(!dinosaur.isNull("massFrom")) {
				mass = dinosaur.getInt("massFrom");
			} else if(!dinosaur.isNull("massTo")) {
				mass = dinosaur.getInt("massTo");
			}

			double lengthValue = 0;
			if(!dinosaur.isNull("lengthFrom")) {
				lengthValue = dinosaur.getDouble("lengthFrom");
			} else if(!dinosaur.isNull("lengthTo")) {
				lengthValue = dinosaur.getDouble("lengthTo");
			}

			StringBuffer descriptionBuffer = new StringBuffer();

			if(mass > 0) {
				descriptionBuffer.append(String.valueOf(mass) + "kg");
			}
			if(lengthValue > 0) {
				if(mass > 0) {
					descriptionBuffer.append(", " + String.valueOf(lengthValue) + "m");
				} else {
					descriptionBuffer.append(String.valueOf(lengthValue) + "m");
				}
			}

			this.setDescription(descriptionBuffer.toString());
			this.setLength(lengthValue);
			this.setDiet(dinosaur.getString("dietTypeName"));
			this.setGenus(dinosaur.getString("genus"));
			this.setNameMeaning(dinosaur.getString("nameMeaning"));
			this.setNamePronounciation(dinosaur.getString("namePronounciation"));
			this.setNamedBy(dinosaur.getString("genusNamedBy") + " (" + String.valueOf(dinosaur.getInt("genusYear")) + ")");

			//Period
			JSONObject period = dinosaur.getJSONObject("period");
			Map<String, String> periodMap = new HashMap<String, String>();

			periodMap.put("name", period.getString("period"));
			periodMap.put("url", "http://" + host + ".nhm.ac.uk/discover/dino-directory/timeline/" + period.getString("period").toLowerCase().replaceAll(" ", "-") + "/gallery.html");

			this.setPeriod(periodMap);

			String myaFrom = String.valueOf(dinosaur.getInt("myaFrom"));
			String myaTo = String.valueOf(dinosaur.getInt("myaTo"));

			String mya = null;

			if(!myaFrom.equals(null) && !myaTo.equals(null)) {
				mya = myaFrom + " - " + myaTo + " million BCE";
			} else if(!myaFrom.equals(null)) {
				mya = myaFrom + " million BCE";
			} else if(!myaTo.equals(null)) {
				mya = myaTo + " million BCE";
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

			if (dinosaurMedia2 != null && dinosaurMedia2.length() > 0 ) {
				this.setImageUrl2("http://www.nhm.ac.uk/resources/nature-online/life/dinosaurs/dinosaur-directory/"
					+ dinosaurMedia2.getString("mediaTypePath") + "/"
					+ dinosaurMedia2.getString("mediaContentTypeName")
					+ "/small/" + dinosaurMedia2.getString("identifier") + ".jpg");
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

		} catch (Exception e) {
			e.printStackTrace();
		}
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
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

	public String getImageUrl2() {
		return imageUrl2;
	}

	public void setImageUrl2(String imageUrl2) {
		this.imageUrl2 = imageUrl2;
	}

}
