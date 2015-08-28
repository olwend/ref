package uk.ac.nhm.nhm_www.core.model.science.proactivities;

import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;



public class MediaBroadcast extends ProfessionalActivity {
	private String description;
	private String department;
	private String interviewerName;

	public MediaBroadcast(String url, String title, final String reportingDate, String yearStartDate, String monthStartDate, String dayStartDate, 
			String yearEndDate, String monthEndDate, String dayEndDate, String description, String department, String interviewerName) {
		super(url, title, reportingDate, yearStartDate, monthStartDate, dayStartDate, yearEndDate, monthEndDate, dayEndDate);
		this.description = description;
		this.interviewerName = interviewerName;
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
		
		// Description,_
		if ( this.description != null ){
			stringBuffer.append(this.description);
			stringBuffer.append(", ");
		}
		
		// EventName,_ 
		if (this.department != null){
			stringBuffer.append(this.department);
			stringBuffer.append(", ");
		}		
		
		// InterviewerName,_ 
		if (this.interviewerName != null){
			stringBuffer.append(this.interviewerName);
			stringBuffer.append(", ");
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
		
		// URL,_ 
		if (this.url != null){
			stringBuffer.append(this.url);
			stringBuffer.append(".");
		}
		
		return stringBuffer.toString();
	}
}