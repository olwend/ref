package uk.ac.nhm.nhm_www.core.model.science.teaching;

import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;

import uk.ac.nhm.nhm_www.core.model.science.proactivities.Institution;

public class Examiner extends TeachingActivityTemplate {

	private Institution[] institutions;
	private String role;
	private String level;

	public Examiner(String url, String title, final String reportingDate, String yearStartDate, String monthStartDate, String dayStartDate, 
			String yearEndDate, String monthEndDate, String dayEndDate, String examinationRole, String examinationLevel, String examinationInstitution) {
		super(url, title, reportingDate, yearStartDate, monthStartDate, dayStartDate, yearEndDate, monthEndDate, dayEndDate);
		this.role = examinationRole;
		this.level = examinationLevel;
		assignJSON(examinationInstitution);
	}
	
	private void assignJSON(String aux) {
		try {
			if (aux == null){
				return;
			}
			final JSONObject jsonObject = new JSONObject(aux);
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
		
		// Role,_ 
		if (this.role != null){
			stringBuffer.append(this.role);
			
			// Level,_ 
			if (this.level != null){
				stringBuffer.append(", ");
				stringBuffer.append(this.level);
			}
			stringBuffer.append("<br>");
		}
		
		if ( institutions != null ){
			for (Institution institution : institutions) {
				// InstitutionName,_ 
				if (institution.getOrganisation() != null){
					stringBuffer.append(institution.getOrganisation());
				}
				
				// City,_
				if (institution.getCity() != null){
					stringBuffer.append(", ");
					stringBuffer.append(institution.getCity() );
					
				}
				// Country,_
				if (institution.getCountry() != null){
					stringBuffer.append(", ");
					stringBuffer.append(institution.getCountry());
				}
				stringBuffer.append("<br>");
			}
		}
		
		// startDay / startMonth / startYear
		if (this.yearsd != null){
			if (this.monthsd != null){
				if ( this.daysd != null) {
					stringBuffer.append(this.daysd);
					stringBuffer.append("/");
				}
				stringBuffer.append(this.monthsd);
				stringBuffer.append("/");
			}
			stringBuffer.append(this.yearsd);
		}
		
		return stringBuffer.toString();
	}
}