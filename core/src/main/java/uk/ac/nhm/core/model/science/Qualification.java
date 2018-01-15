package uk.ac.nhm.core.model.science;

import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;

public class Qualification implements Comparable<Qualification> {
	private int fromYear, toYear;
	private String qualification, institution, thesisTitle, city, country, majorFieldStudy;
	
	public Qualification (final JSONObject jsonObject) {
		try {
			this.stractJSON(jsonObject);
		} catch (JSONException e) {
			this.fromYear = -1;
			this.toYear = -1;
			this.qualification = null;
			this.institution = null;
			this.thesisTitle = null;
			this.city = null;
			this.country = null;
			this.majorFieldStudy = null;
		}
	}
	
	public Qualification (final String jsonString) {
		try {
			final JSONObject jsonObject = new JSONObject(jsonString);
		
			this.stractJSON(jsonObject);
		} catch (JSONException e) {
			this.fromYear = -1;
			this.toYear = -1;
			this.qualification = null;
			this.institution = null;
			this.thesisTitle = null;
			this.city = null;
			this.country = null;
			this.majorFieldStudy = null;
		}
	}
	
	public Qualification (final int fromYear, final int toYear, final String qualification, 
			final String institution, final String thesisTitle, final String city,
			final String country, final String majorFieldStudy) {
		this.fromYear  = fromYear;
		this.toYear = toYear;
		this.qualification = qualification;
		this.institution = institution;
		this.thesisTitle = thesisTitle;
		this.city = city;
		this.country = country;
		this.majorFieldStudy = majorFieldStudy;
	}

	public int getFromYear() {
		return fromYear;
	}
	
	public int getToYear() {
		return toYear;
	}

	public String getQualification() {
		return qualification;
	}
	
	public String getInstitution() {
		return institution;
	}
	
	public String getThesisTitle() {
		return thesisTitle;
	}

	public String getCity() {
		return city;
	}

	public String getCountry() {
		return country;
	}

	public String getMajorFieldStudy() {
		return majorFieldStudy;
	}

	public boolean isValid() {
		return this.qualification != null && !this.qualification.isEmpty();
	}
	
	private void stractJSON(final JSONObject jsonObject) throws JSONException {
		if (jsonObject.has("to")) {
			this.toYear = jsonObject.getInt("to");
		} else {
			this.toYear = -1;
		}
		
		if (jsonObject.has("from")) {
			this.fromYear = jsonObject.getInt("from");
		} else {
			this.fromYear = -1;
		}
		
		if (jsonObject.has("title")) {
			this.qualification = jsonObject.getString("title");
		}
		
		if (jsonObject.has("institution")) {
			this.institution = jsonObject.getString("institution");
		}
	}

	public int compareTo(final Qualification q) {
		final boolean qIsOnGoing = q.toYear == -1 && q.fromYear != -1;
		final boolean thisNotYears = this.toYear == -1 && this.fromYear == -1;
		final boolean qNotYears = q.toYear == -1 && this.fromYear == -1;
		
		if (thisNotYears) {
			if (qNotYears) {
				return this.qualification.compareTo(q.qualification);
			}
			return 1;
		}
		
		if (this.toYear == -1) {
			if (qIsOnGoing) {
				return this.qualification.compareTo(q.qualification);
			}
			return -1;
		}
		
		if (qIsOnGoing) {
			return 1;
		}
		
		if (this.toYear != q.toYear) {
			return q.toYear - this.toYear;
		}
		
		if (!q.isValid()) {
			return -1;
		}
		
		if (!this.isValid()) {
			return 1;
		}
		
		return this.qualification.compareTo(q.qualification);
	}
	
}
