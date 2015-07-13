package uk.ac.nhm.nhm_www.core.model.science.proactivities;

import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;



public class Committee extends ProfessionalActivity {

	private String committeeRole;
	private Institution[] institutions;

	public Committee(String url, String title, final String reportingDate, String yearStartDate, String monthStartDate, String dayStartDate, 
			String yearEndDate, String monthEndDate, String dayEndDate, String committeeRole,
			String committeeInstitution) {
		super(url, title, reportingDate, yearStartDate, monthStartDate, dayStartDate, yearEndDate, monthEndDate, dayEndDate);
		this.committeeRole = committeeRole;
		assignJSON(committeeInstitution);
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
		
		// CommitteeRole,_
		if (this.committeeRole!= null){
			stringBuffer.append(this.committeeRole);
			stringBuffer.append(", ");
		}
		
		// CommitteeName,_
		if (this.title != null){
			stringBuffer.append(this.title);
			stringBuffer.append(" ");
		}
		
		if ( institutions != null ){
			for (Institution institution  : institutions) {
				// <a href=url>InstitutionName</a>,_ 
				if (institution.getOrganisation() != null){
					if (this.url != null) {
						stringBuffer.append("<a href=\"");
						stringBuffer.append(this.url);
						stringBuffer.append("\">");
					}
					
					stringBuffer.append(institution.getOrganisation());
					stringBuffer.append(", ");
					
					if (this.url != null) {
						stringBuffer.append("</a>");
					}
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
		
		return stringBuffer.toString();
	}
}