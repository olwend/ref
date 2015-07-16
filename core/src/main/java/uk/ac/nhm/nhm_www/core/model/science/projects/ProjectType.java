package uk.ac.nhm.nhm_www.core.model.science.projects;

import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;

import uk.ac.nhm.nhm_www.core.model.science.proactivities.Organisation;



public class ProjectType extends Project {

	private String fundingSource;
	private Collaborator[] collaborators;

	public ProjectType(String url, String name, final String reportingDate, String yearStartDate, String monthStartDate, String dayStartDate, 
			String yearEndDate, String monthEndDate, String dayEndDate, String fundingSource, String collaborator) {
		super(url, name, reportingDate, yearStartDate, monthStartDate, dayStartDate, yearEndDate, monthEndDate, dayEndDate);
		this.fundingSource = fundingSource;
		assignJSON(collaborator);
	}
	
	private void assignJSON(String aux) {
		try {
			if (aux == null){
				return;
			}
			final JSONObject jsonObject = new JSONObject(aux);
			final JSONArray jsonArray = jsonObject.getJSONArray("organisations");
			
			this.collaborators = new Collaborator[jsonArray.length()];
			
			for (int i = 0; i < jsonArray.length(); i++) {
				final JSONObject organisationJson = jsonArray.getJSONObject(i);
				
				final Collaborator institution = new Collaborator(organisationJson);
				this.collaborators[i] = institution;
			}
		} catch (final JSONException e) {
			this.collaborators = null;
		}
	}

	@Override
	public String getHTMLContent(String currentAuthor) {
		final StringBuffer stringBuffer = new StringBuffer();
		
		stringBuffer.append(" ");
		
		// FundingSource,_
		if (this.fundingSource!= null){
			stringBuffer.append(this.fundingSource);
			stringBuffer.append(", ");
		}
		
		// Name,_
		if (this.name != null){
			stringBuffer.append(this.name);
			stringBuffer.append(", ");
		}
		
		if ( collaborators != null ){
			for (Collaborator collaborator  : collaborators) {
				// <a href=url>Name</a>,_ 
				if (collaborator.getName() != null){
					if (this.url != null) {
						stringBuffer.append("<a href=\"");
						stringBuffer.append(this.url);
						stringBuffer.append("\">");
					}
					stringBuffer.append(collaborator.getName());
					if (this.url != null) {
						stringBuffer.append("</a>");
					}
					stringBuffer.append(", ");
				}
				
				// Organisation,_ 
				if (collaborator.getOrganisation() != null){
					stringBuffer.append(collaborator.getOrganisation());
					stringBuffer.append("<br>");
				}
				
				// City,_
				if (collaborator.getCity() != null){
					stringBuffer.append(collaborator.getCity() );
					stringBuffer.append(", ");
				}	
				
				// Country,_
				if (collaborator.getCountry() != null){
					stringBuffer.append(collaborator.getCountry());
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