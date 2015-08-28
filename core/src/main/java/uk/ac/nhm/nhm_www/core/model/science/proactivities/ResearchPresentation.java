package uk.ac.nhm.nhm_www.core.model.science.proactivities;

import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;



public class ResearchPresentation extends ProfessionalActivity {

	private Institution[] institutions;

	public ResearchPresentation(String url, String title, final String reportingDate, String yearStartDate, String monthStartDate, String dayStartDate, 
			String yearEndDate, String monthEndDate, String dayEndDate, String awardInstitutions) {
		super(url, title, reportingDate, yearStartDate, monthStartDate, dayStartDate, yearEndDate, monthEndDate, dayEndDate);
		assignJSON(awardInstitutions);
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
		
		stringBuffer.append(" ");
		
		// AwardName,_
		if (this.title != null){
			stringBuffer.append(this.title);
			stringBuffer.append(", ");
		}
		
		// AwardedBy,_
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
					if (this.url != null) {
						stringBuffer.append("</a>");
					}
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
			if(this.daysd != null && this.monthsd != null) {
				stringBuffer.append(this.daysd);
				stringBuffer.append("/");
				stringBuffer.append(this.monthsd);
				stringBuffer.append("/");
			}
			stringBuffer.append(this.yearsd);
			if (this.yeared != null) {
				stringBuffer.append(" - ");
				if(this.dayed != null && this.monthed != null) {
					stringBuffer.append(this.dayed);
					stringBuffer.append("/");
					stringBuffer.append(this.monthed);
					stringBuffer.append("/");
				}
				stringBuffer.append(this.yeared);
				stringBuffer.append(".");
			} else {
				stringBuffer.append(".");
			}
		}
		
		return stringBuffer.toString();
	}
}