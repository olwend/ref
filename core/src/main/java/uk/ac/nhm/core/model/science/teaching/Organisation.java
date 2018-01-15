package uk.ac.nhm.core.model.science.teaching;

import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;

public class Organisation {
	protected String name;
	protected String organisation;
	protected String city;
	protected String country;

	public Organisation(JSONObject jsonObject) {
		try {
			this.name = jsonObject.getString("name");
		} catch (JSONException e) {
			this.name = null;
		}
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

	public String getName() {
		return name;
	}
}