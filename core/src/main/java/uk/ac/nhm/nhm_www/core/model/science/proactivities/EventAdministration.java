package uk.ac.nhm.nhm_www.core.model.science.proactivities;

import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;

public class EventAdministration extends ProfessionalActivity {

	private Institution[] institutions;
	private String eventType;
	private String role;

	public EventAdministration(String url, String title, final String reportingDate, String yearStartDate, String monthStartDate, String dayStartDate, 
			String yearEndDate, String monthEndDate, String dayEndDate, String role, String eventType, String eventOrganisationsInstitution) {
		super(url, title, reportingDate, yearStartDate, monthStartDate, dayStartDate, yearEndDate, monthEndDate, dayEndDate);
		this.role = role;
		this.eventType = eventType;
		assignJSON(eventOrganisationsInstitution);
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

	@Override
	public String getHTMLContent(String currentAuthor) {
		final StringBuffer stringBuffer = new StringBuffer();
		
		stringBuffer.append(" ");
		
		stringBuffer.append("<p>");
		
		// AdministrativeRole,_
		if (this.role!= null){
			stringBuffer.append(this.role);
			stringBuffer.append(", ");
		}
		
		// <a href=url>EventTitle</a>,_
		if (this.title != null){
			if (this.url != null) {
				stringBuffer.append("<a href=\"");
				stringBuffer.append(this.url);
				stringBuffer.append("\">");
			}
			stringBuffer.append(this.title);
			if (this.url != null) {
				stringBuffer.append("</a>");
			}
			stringBuffer.append(", ");
		}
		
		// (EventType),_
		if (this.eventType != null){
			stringBuffer.append("(");
			stringBuffer.append(eventType);
			stringBuffer.append("), ");
		}
		
		if ( institutions != null ){
			for (Institution institution  : institutions) {
				// <a href=url>InstitutionName</a>,_ 
				if (institution.getOrganisation() != null){
					stringBuffer.append(institution.getOrganisation());
					stringBuffer.append(", ");
				}
				
				// City,_
				if (institution.getCity() != null){
					stringBuffer.append(institution.getCity() );
					stringBuffer.append(", ");
				}	
				
				// Country,_
				if (institution.getCountry() != null){
					stringBuffer.append(institution.getCountry());
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
		
		stringBuffer.append("</p>");
		
		return stringBuffer.toString();
	}
}