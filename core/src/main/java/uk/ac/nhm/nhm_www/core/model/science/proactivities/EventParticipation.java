package uk.ac.nhm.nhm_www.core.model.science.proactivities;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class EventParticipation extends ProfessionalActivity {
	
    private static final Logger LOG = LoggerFactory.getLogger(EventParticipation.class);

	private Institution[] institutions;
	private String eventType;
	private String[] roles;

	private String[] eventParticipationType;

	public EventParticipation(String url, String title, final String reportingDate, String yearStartDate, String monthStartDate, String dayStartDate, 
			String yearEndDate, String monthEndDate, String dayEndDate, String[] roles, String eventType, String eventParticipationInstitution) {
		super(url, title, reportingDate, yearStartDate, monthStartDate, dayStartDate, yearEndDate, monthEndDate, dayEndDate);
		this.roles = roles;
		this.eventType = eventType;
		assignJSON(eventParticipationInstitution);
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
	public String getFilteredHTMLContent(String currentAuthor, String[] parameters) {
		this.eventParticipationType = new String[parameters.length];
		
		for(int i = 0; i < parameters.length; i++){
			this.eventParticipationType[i] = parameters[i];
		}
		
		return super.getFilteredHTMLContent(currentAuthor, parameters);
	}


	@Override
	public String getHTMLContent(String currentAuthor) {
		final StringBuffer stringBuffer = new StringBuffer();
		
		stringBuffer.append("");
		
		boolean displayEvent = eventParticipationTypeMatchesRequestedProfessionalActivity();
		
		if (displayEvent){
			
			stringBuffer.append("<p>");
			
			// ParticipationRole, ParticipationRole,_
			final String[] roles = this.roles;
			String rolesString = StringUtils.EMPTY;
			if (this.roles!= null){
				rolesString = StringUtils.join(roles, ", ");
				stringBuffer.append(rolesString);
				stringBuffer.append(", ");
			}
			
			// <a href=url>EventTitle</a>,_
			if (this.title != null){
				if (this.url != null) {
					stringBuffer.append("<a href=\"");
					stringBuffer.append(this.url);
					stringBuffer.append("\">");
				}
				
				stringBuffer.append(this.title);
				stringBuffer.append(" ");
				
				if (this.url != null) {
					stringBuffer.append("</a>");
					stringBuffer.append(", ");
				}
			}
			
			// (EventType),_
			if (this.eventType != null){
				stringBuffer.append("(");
				stringBuffer.append(eventType);
				stringBuffer.append("), ");
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
			stringBuffer.append("</p>");
			
		}
		return stringBuffer.toString();
	}
	
	public boolean eventParticipationTypeMatchesRequestedProfessionalActivity() {
		boolean res = false;
		if (this.eventType != null) {
			for (String aux : this.eventParticipationType) {
				if (!res){
					if (aux.equals(this.eventType)){
						res = true;
					}
				}
			}
		}
		
		return res;
	}
}