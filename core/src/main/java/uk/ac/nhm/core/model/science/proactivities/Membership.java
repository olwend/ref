package uk.ac.nhm.core.model.science.proactivities;

import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;



public class Membership extends ProfessionalActivity {

	private Institution[] institutions;
	private String role;

	public Membership(String url, String title, final String reportingDate, String yearStartDate, 
			String monthStartDate, String dayStartDate, String yearEndDate, String monthEndDate, String dayEndDate, 
			String membershipRole, String membershipInstitution) {
		super(url, title, reportingDate, yearStartDate, monthStartDate, dayStartDate, yearEndDate, monthEndDate, dayEndDate);
		this.role = membershipRole;
		assignJSON(membershipInstitution);
	}
	
	private void assignJSON(String inOrExInstitution) {
		try {
			if (inOrExInstitution == null){
				return;
			}
			final JSONObject jsonObject = new JSONObject(inOrExInstitution);
			final JSONArray jsonArray = jsonObject.getJSONArray("organisations");
			
			this.institutions = new Institution[jsonArray.length()];
			
			for (int i = 0; i < jsonArray.length(); i++) {
				final JSONObject organisationJson = jsonArray.getJSONObject(i);
				
				final Institution institution = new Institution(organisationJson);
				this.institutions[i] = institution;
			}
		} catch (final JSONException e) {
			this.institutions = null;
		}
	}

	public String getHTMLContent(String currentAuthor) {
		final StringBuffer stringBuffer = new StringBuffer();
		
		stringBuffer.append(" ");
		
		// Role,_
		if (this.role != null){
			stringBuffer.append(this.role);
			stringBuffer.append(", ");
		}
		
		if ( institutions != null ){
			for (Institution institution  : institutions) {

				if (institution.getOrganisation() != null) {
					if (this.url != null) {
						stringBuffer.append("<a href=\"");
						stringBuffer.append(this.url);
						stringBuffer.append("\">");
					}
					stringBuffer.append(institution.getOrganisation());
					if (this.url != null) {
						stringBuffer.append("</a>");
					}
					if (institution.getName() != null || institution.getCity() != null || institution.getCountry() != null || this.yearsd != null) {
						stringBuffer.append(", ");
					}
				}

				if (institution.getName() != null) {
					if (this.url != null) {
						stringBuffer.append("<a href=\"");
						stringBuffer.append(this.url);
						stringBuffer.append("\">");
					}
					stringBuffer.append(institution.getName());
					if (this.url != null) {
						stringBuffer.append("</a>");
					}
					if (institution.getCity() != null || institution.getCountry() != null || this.yearsd != null) {
						stringBuffer.append(", ");
					}
				}

				// City,_
				if (institution.getCity() != null){
					stringBuffer.append(institution.getCity() );
					if (institution.getCountry() != null || this.yearsd != null) {
						stringBuffer.append(", ");
					}
				}

				// Country,_
				if (institution.getCountry() != null){
					stringBuffer.append(institution.getCountry());
					if (this.yearsd != null) {
						stringBuffer.append(", ");
					}
				}
			}
		}
		
		// startYear - endYear. || startYear - on going.
		if (this.yearsd != null){
			if (this.yeared != null) {
				stringBuffer.append(this.yearsd);
				stringBuffer.append(" - ");
				stringBuffer.append(this.yeared);
			} else {
				stringBuffer.append(this.yearsd);
				stringBuffer.append(" - ");
				stringBuffer.append("on going");
			}
		}
		
		stringBuffer.append(".");
		
		return stringBuffer.toString();
	}
	
}