package uk.ac.nhm.nhm_www.core.model.science.teaching;

import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;

import uk.ac.nhm.nhm_www.core.model.science.proactivities.Institution;

public class TaughtCourse extends TeachingActivityTemplate {

	private Institution[] institutions;
	private String courseLevel;

	public TaughtCourse(String url, String title, final String reportingDate, String yearStartDate, String monthStartDate, String dayStartDate, 
			String yearEndDate, String monthEndDate, String dayEndDate, String courseLevel, String institution) {
		super(url, title, reportingDate, yearStartDate, monthStartDate, dayStartDate, yearEndDate, monthEndDate, dayEndDate);
		this.courseLevel = courseLevel;
		assignJSON(institution);
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
		
		// CourseLevel,_ 
		if (this.courseLevel != null && !this.courseLevel.equals("")){
			stringBuffer.append(this.courseLevel);
			stringBuffer.append(": ");
		}
		
		// <a href=url>CourseTitle</a>,_ 
		if (this.title != null && !this.title.equals("")){
			if (this.url != null) {
				stringBuffer.append("<a href=\"");
				stringBuffer.append(this.url);
				stringBuffer.append("\">");
			}
			stringBuffer.append(this.title);
			if (this.url != null) {
				stringBuffer.append("</a>");
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
				stringBuffer.append("<br>");
			}
		}
		
		// startMonth / startYear - endMonth / endYear. || starthMonth / startYear - on going.
		if (this.yearsd != null){
			if (this.yeared != null) {
				if (this.monthsd != null){
					stringBuffer.append(this.monthsd);
					stringBuffer.append("/");
				}
				stringBuffer.append(this.yearsd);
				stringBuffer.append(" - ");
				if (this.monthed != null){
					stringBuffer.append(this.monthed);
					stringBuffer.append("/");
				}
				stringBuffer.append(this.yeared);
				stringBuffer.append(".");
			} else {
				if (this.monthsd != null){
					stringBuffer.append(this.monthsd);
					stringBuffer.append("/");
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