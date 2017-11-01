package uk.ac.nhm.core.model.science.proactivities;

import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;



public class ReviewerOrRefereeGrant extends ProfessionalActivity {

	private Organisation[] organisations;

	public ReviewerOrRefereeGrant(String url, String title, final String reportingDate, String yearStartDate, String monthStartDate, String dayStartDate, 
			String yearEndDate, String monthEndDate, String dayEndDate, String grantOrganisations) {
		super(url, title, reportingDate, yearStartDate, monthStartDate, dayStartDate, yearEndDate, monthEndDate, dayEndDate);
		
		assignJSON(grantOrganisations);
	}
	
	private void assignJSON(String aux) {
		try {
			if (aux == null){
				return;
			}
			final JSONObject jsonObject = new JSONObject(aux);
			final JSONArray jsonArray = jsonObject.getJSONArray("organisations");
			
			this.organisations = new Organisation[jsonArray.length()];
			
			for (int i = 0; i < jsonArray.length(); i++) {
				final JSONObject organisationJson = jsonArray.getJSONObject(i);
				
				final Organisation organisation = new Organisation(organisationJson);
				this.organisations[i] = organisation;
			}
		} catch (final JSONException e) {
			this.organisations = null;
		}
	}

	@Override
	public String getHTMLContent(String currentAuthor) {
		final StringBuffer stringBuffer = new StringBuffer();
		
		stringBuffer.append(" ");
		
		// Title,_
		if (this.title != null){
			stringBuffer.append(this.title);
			stringBuffer.append(", ");
		}
		
		if (organisations != null){
			for (Organisation organisation  : organisations) {
				// <a href=url>InstitutionName</a>,_ 
				if (organisation.getOrganisation() != null){
					stringBuffer.append(organisation.getOrganisation());
					stringBuffer.append(", ");
				}
				
				// City,_
				if (organisation.getCity() != null){
					stringBuffer.append(organisation.getCity() );
					stringBuffer.append(", ");
				}	
				
				// Country,_
				if (organisation.getCountry() != null){
					stringBuffer.append(organisation.getCountry());
					stringBuffer.append(", ");
				}
			}
		}
		
		// startYear - endYear. || startYear - on going.
		if (this.yearsd != null){
			if (this.yeared != null) {
				stringBuffer.append(this.yearsd);
				stringBuffer.append(" - ");
				stringBuffer.append(this.yeared);
				stringBuffer.append(".");
			} else {
				stringBuffer.append(this.yearsd);
				stringBuffer.append(" - ");
				stringBuffer.append("on going");
				stringBuffer.append(".");
			}
		}
		
		return stringBuffer.toString();
	}
}