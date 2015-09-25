package uk.ac.nhm.nhm_www.core.model.science.proactivities;

import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;



public class ResearchPresentation extends ProfessionalActivity {

	private Location[] locations;
	private Boolean invited;
	private Boolean keynote;
	private String eventName;

	public ResearchPresentation(String url, String title, final String reportingDate, String yearStartDate, String monthStartDate, String dayStartDate, 
			String yearEndDate, String monthEndDate, String dayEndDate, Boolean invited, Boolean keynote, String eventName, String rpresentationLocations) {
		super(url, title, reportingDate, yearStartDate, monthStartDate, dayStartDate, yearEndDate, monthEndDate, dayEndDate);
		this.invited = invited;
		this.keynote = keynote;
		this.eventName = eventName;
		assignJSON(rpresentationLocations);
	}
	
	private void assignJSON(String aux) {
		try {
			if (aux == null){
				return;
			}
			final JSONObject jsonObject = new JSONObject(aux);
			final JSONArray jsonArray = jsonObject.getJSONArray("organisations");
			
			this.locations = new Location[jsonArray.length()];
			
			for (int i = 0; i < jsonArray.length(); i++) {
				final JSONObject locationJson = jsonArray.getJSONObject(i);
				
				final Location location = new Location(locationJson);
				this.locations[i] = location;
			}
		} catch (final JSONException e) {
			this.locations = null;
		}
	}

	@Override
	public String getHTMLContent(String currentAuthor) {
		final StringBuffer stringBuffer = new StringBuffer();
		
		stringBuffer.append(" ");
		
		// Invited/Keynote speaker,_
		if ( this.invited != null || this.keynote != null){
			if ( this.keynote == true ){
				stringBuffer.append("Keynote speaker, ");
			} else if ( this.invited == true ) {
				stringBuffer.append("Invited speaker, ");
			}
		}
		
		// Title,_
		if (this.title != null){
			stringBuffer.append(this.title);
			stringBuffer.append(", ");
		}
		
		// EventName,_ 
		if (this.eventName != null){
			stringBuffer.append(this.eventName);
			stringBuffer.append(": ");
		}
		
		// Location,_
		if ( locations != null ){
			for (Location location  : locations) {
				// Name,_ 
				if (location.getName() != null){
					stringBuffer.append(location.getName());
					stringBuffer.append(", ");
				}
				
				// Organisation,_ 
				if (location.getOrganisation() != null){
					stringBuffer.append(location.getOrganisation());
					stringBuffer.append(", ");
				}
				
				// City,_
				if (location.getCity() != null){
					stringBuffer.append(location.getCity() );
					stringBuffer.append(", ");
				}	
				
				// Country,_
				if (location.getCountry() != null){
					stringBuffer.append(location.getCountry());
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