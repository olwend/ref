package uk.ac.nhm.nhm_www.core.model.science;

import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;

public class WorkExperience implements Comparable<WorkExperience> {
	private int startDate, endDate;
	private String position, organisation, subOrganisation, city, country;
	
	public WorkExperience (final JSONObject jsonObject) {
		try {
			this.stractJSON(jsonObject);
		} catch (JSONException e) {
			this.startDate 		 = -1;
			this.endDate 		 = -1;
			this.position 		 = null;
			this.organisation 	 = null;
			this.subOrganisation = null;
			this.city 			 = null;
			this.country 		 = null;
		}
	}
	
	public WorkExperience (final String jsonString) {
		try {
			final JSONObject jsonObject = new JSONObject(jsonString);
		
			this.stractJSON(jsonObject);
		} catch (JSONException e) {
			this.startDate 		 = -1;
			this.endDate 		 = -1;
			this.position 		 = null;
			this.organisation 	 = null;
			this.subOrganisation = null;
			this.city 			 = null;
			this.country 		 = null;
		}
	}
	
	public WorkExperience (final int startDate, final int endDate, final String position, final String organisation, final String subOrganisation, final String city, final String country) {
		this.startDate 		 = startDate;
		this.endDate 	 	 = endDate;
		this.position		 = position;
		this.organisation  	 = organisation;
		this.subOrganisation = subOrganisation;
		this.city			 = city;
		this.country 		 = country;
	}
	
	public boolean isValid() {
		return this.startDate > 0 && this.organisation != null && !this.organisation.isEmpty();
	}
	
	private void stractJSON(final JSONObject jsonObject) throws JSONException {
		if (jsonObject.has("startDate")) {
			this.startDate = jsonObject.getInt("startDate");
		} else {
			this.startDate = -1;
		}
		
		if (jsonObject.has("endDate")) {	
			this.endDate = jsonObject.getInt("endDate");
		} else {
			this.endDate = -1;
		}
		
		if (jsonObject.has("organization")) {
			this.organisation = jsonObject.getString("organisation");
		} else {
			this.organisation = null;
		}
		
		if (jsonObject.has("suborganization")) {
			this.subOrganisation = jsonObject.getString("subOrganisation");
		} else {
			this.subOrganisation = null;
		}
		
		if (jsonObject.has("country")) {
			this.country = jsonObject.getString("country");
		}
	}

	public int getStartDate() {
		return startDate;
	}

	public int getEndDate() {
		return endDate;
	}

	public String getOrganisation() {
		return organisation;
	}

	public String getSubOrganisation() {
		return subOrganisation;
	}

	public String getCountry() {
		return country;
	}

	public String getPosition() {
		return position;
	}

	public String getCity() {
		return city;
	}
	
	public int compareTo(final WorkExperience w) {
		final boolean wIsOnGoing = w.endDate == -1 && w.startDate != -1;
		final boolean thisNotYears = this.endDate == -1 && this.startDate == -1;
		final boolean wNotYears = w.endDate == -1 && this.startDate == -1;
		
		if (thisNotYears) {
			if (wNotYears) {
				return this.position.compareTo(w.position);
			}
			return 1;
		}
		
		if (this.endDate == -1) {
			if (wIsOnGoing) {
				return this.position.compareTo(w.position);
			}
			return -1;
		}
		
		if (wIsOnGoing) {
			return 1;
		}
		
		if (!w.isValid()) {
			return -1;
		}
		
		if (!this.isValid()) {
			return 1;
		}
		
		if (w.startDate == this.startDate) {
			return this.position.compareTo(w.position);
		}
		
		return w.startDate - this.startDate;
	}	

}
