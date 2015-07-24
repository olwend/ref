package uk.ac.nhm.nhm_www.core.model.science.teaching;

import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;

import uk.ac.nhm.nhm_www.core.model.science.proactivities.Institution;

public class Supervision extends TeachingActivityTemplate {

	private Institution[] institutions;
	private String degreeType;
	private String otherDegreeType;
	private String role;
	private String person;
	private String coContributors;
	private String degreeSubject;
	private String funder;

	public Supervision(String url, String title, final String reportingDate, String yearStartDate, String monthStartDate, String dayStartDate, 
			String yearEndDate, String monthEndDate, String dayEndDate, String degreeType, String otherDegreeType, String supervisoryRole, 
			String person, String coContributors, String degreeSubject, String supervisionInstitution, String funder) {
		super(url, title, reportingDate, yearStartDate, monthStartDate, dayStartDate, yearEndDate, monthEndDate, dayEndDate);
		this.degreeType = degreeType;
		this.otherDegreeType = otherDegreeType;
		this.role = supervisoryRole;
		this.person = person;
		this.coContributors = coContributors;
		this.degreeSubject = degreeSubject;
		this.funder = funder;
		assignJSON(supervisionInstitution);
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
		
		// DegreeType_
		if (this.degreeType != null) {
			if (this.degreeType.equals("Other")){
				stringBuffer.append(this.otherDegreeType);
			} else {
				stringBuffer.append(this.degreeType);
			}
		}
		
		// SupervisoryRole to Person NM_<br>
		if (this.role != null && this.person != null) {
			stringBuffer.append(this.role);
			stringBuffer.append(" to ");
			stringBuffer.append(this.person);
			stringBuffer.append("<br>");
		}
		
		// Co supervisor(s) Person NM
		if (this.coContributors != null ){
			stringBuffer.append("Co supervisor(s) ");
			stringBuffer.append(this.coContributors);
			stringBuffer.append("<br>");
		}
		
		// Title,_ 
		if (this.title != null && !this.title.equals("")){
			stringBuffer.append(this.title);
			if ( this.degreeSubject != null ){
				stringBuffer.append(", ");
				stringBuffer.append(this.degreeSubject);
			}
			stringBuffer.append("<br>");
		}
		
		if ( institutions != null ){
			for (Institution institution  : institutions) {
				// InstitutionName,_ 
				if (institution.getOrganisation() != null){
					stringBuffer.append(institution.getOrganisation());
				}
				
				// City,_
				if (institution.getCity() != null){
					stringBuffer.append(", ");
					stringBuffer.append(institution.getCity() );
					
					// Country,_
					if (institution.getCountry() != null){
						stringBuffer.append(", ");
						stringBuffer.append(institution.getCountry());
					}
				}
				stringBuffer.append(" ");
			}
		}
		
		if ( this.funder != null ){
			stringBuffer.append("Funded by ");
			stringBuffer.append(this.funder);
		}
		
		// startDay / startMonth / startYear - endDay / endMonth / endYear. || startDay / starthMonth / startYear - on going.
		if (this.yearsd != null){
			if (this.yeared != null) {
				if (this.daysd != null){
					stringBuffer.append(this.daysd);
					stringBuffer.append("/");
					if (this.monthsd != null){
						stringBuffer.append(this.monthsd);
						stringBuffer.append("/");
					}
				}
				stringBuffer.append(this.yearsd);
				stringBuffer.append(" - ");
				if (this.dayed != null){
					stringBuffer.append(this.dayed);
					stringBuffer.append("/");
					if (this.monthed != null){
						stringBuffer.append(this.monthed);
						stringBuffer.append("/");
					}
				}
				stringBuffer.append(this.yeared);
				stringBuffer.append(".");
			} else {
				if (this.daysd != null){
					stringBuffer.append(this.daysd);
					stringBuffer.append("/");
					if (this.monthsd != null){
						stringBuffer.append(this.monthsd);
						stringBuffer.append("/");
					}
				}
				stringBuffer.append(this.yearsd);
				stringBuffer.append(" - ");
				stringBuffer.append("on going");
				stringBuffer.append(".");
			}
		}
		
		return stringBuffer.toString();
	}
}