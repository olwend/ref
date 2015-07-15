package uk.ac.nhm.nhm_www.core.model.science.proactivities;

import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;



public class Fieldwork extends ProfessionalActivity {

	private Organisation[] organisations;

	public Fieldwork(String url, String title, final String reportingDate, String yearStartDate, String monthStartDate, String dayStartDate, 
			String yearEndDate, String monthEndDate, String dayEndDate, String organisation) {
		super(url, title, reportingDate, yearStartDate, monthStartDate, dayStartDate, yearEndDate, monthEndDate, dayEndDate);
		assignJSON(organisation);
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
		
		// <a href=url>Title</a>,_ 
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
		
		if ( organisations != null ){
			for (Organisation organisation  : organisations) {
				// Organisation,_ 
				if (organisation.getOrganisation() != null){
					stringBuffer.append(organisation.getOrganisation());
					stringBuffer.append("<br>");
				}
				
				// City,_
				if (organisation.getCity() != null){
					stringBuffer.append(organisation.getCity() );
					stringBuffer.append("<br>");
				}	
				
				// Country,_
				if (organisation.getCountry() != null){
					stringBuffer.append(organisation.getCountry());
					stringBuffer.append("<br>");
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