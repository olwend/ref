package uk.ac.nhm.nhm_www.core.model.science.projects;

import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;

import uk.ac.nhm.nhm_www.core.model.science.proactivities.Organisation;

public class ProjectType extends ProjectTemplate {

	private String role;

	public ProjectType(String url, String name, final String reportingDate, String yearStartDate, String monthStartDate, String dayStartDate, 
			String yearEndDate, String monthEndDate, String dayEndDate, String fundingSource, String collaborator, String nodeType) {
		super(url, name, reportingDate, yearStartDate, monthStartDate, dayStartDate, yearEndDate, monthEndDate, dayEndDate);
		this.role = nodeType;
	}
	
	@Override
	public String getHTMLContent(String currentAuthor) {
		final StringBuffer stringBuffer = new StringBuffer();
		
		stringBuffer.append(" ");
		
		// Name
		if (this.name != null){
			stringBuffer.append(this.name);
			stringBuffer.append("<br>");
		}
		
		// Role: NodeType
		if (this.role != null){
			stringBuffer.append("Role: ");
			stringBuffer.append(this.role);
			stringBuffer.append("<br>");
		}
		
		// Funding: Museum development
		stringBuffer.append("Funding: Museum development");
		stringBuffer.append("<br>");
		
		// startYear - endYear. || startYear - on going.
		if (this.yearsd != null){
			stringBuffer.append("Dates: ");
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