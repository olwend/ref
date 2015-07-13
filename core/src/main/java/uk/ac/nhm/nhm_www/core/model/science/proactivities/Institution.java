package uk.ac.nhm.nhm_www.core.model.science.proactivities;

import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;

public class Institution {
	protected String organisation;
	protected String city;
	protected String country;

	public Institution(JSONObject jsonObject) {
		try {
			this.organisation = jsonObject.getString("organisation");
		} catch (JSONException e) {
			this.organisation = null;
		}
		try {
			this.city = jsonObject.getString("city");
		} catch (JSONException e) {
			this.city = null;
		}
		try {
			this.country = jsonObject.getString("country");
		} catch (JSONException e) {
			this.country = null;
		}
	}

	public String getOrganisation() {
		return organisation;
	}

	public String getCity() {
		return city;
	}

	public String getCountry() {
		return country;
	}
}