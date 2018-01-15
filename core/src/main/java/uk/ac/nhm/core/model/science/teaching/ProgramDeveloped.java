package uk.ac.nhm.core.model.science.teaching;

import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;

import uk.ac.nhm.core.model.science.proactivities.Institution;

public class ProgramDeveloped extends TeachingActivityTemplate {

	private Institution[] institutions;
	private PartnerOrganisation[] partners;
	private String degreeType;
	private String degreeLevel;
	private String dayReleaseDate;
	private String monthReleaseDate;
	private String yearReleaseDate;

	public ProgramDeveloped(String url, String title, final String reportingDate, String yearStartDate, String monthStartDate, String dayStartDate, 
			String yearEndDate, String monthEndDate, String dayEndDate, String programDegreeType, String programDegreeLevel, String programPartners, 
			String dayReleaseDate, String monthReleaseDate, String yearReleaseDate, String institution) {
		super(url, title, reportingDate, yearStartDate, monthStartDate, dayStartDate, yearEndDate, monthEndDate, dayEndDate);
		this.degreeType = programDegreeType;
		this.degreeLevel = programDegreeLevel;
		this.dayReleaseDate = dayReleaseDate;
		this.monthReleaseDate = monthReleaseDate;
		this.yearReleaseDate = yearReleaseDate;
		assignJSON(institution);
		assignPartners(programPartners);
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
	
	private void assignPartners(String aux) {
		try {
			if (aux == null){
				return;
			}
			final JSONObject jsonObject = new JSONObject(aux);
			final JSONArray jsonArray = jsonObject.getJSONArray("organisations");
			
			this.partners = new PartnerOrganisation[jsonArray.length()];
			
			for (int i = 0; i < jsonArray.length(); i++) {
				final JSONObject organisationJson = jsonArray.getJSONObject(i);
				
				final PartnerOrganisation partner = new PartnerOrganisation(organisationJson);
				this.partners[i] = partner;
			}
		} catch (final JSONException e) {
			this.partners = null;
		}
	}

	@Override
	public String getHTMLContent(String currentAuthor) {
		final StringBuffer stringBuffer = new StringBuffer();
		
		// DegreeLevel,_ 
		if (this.degreeLevel != null && !this.degreeLevel.equals("")){
			stringBuffer.append(this.degreeLevel);
			stringBuffer.append(", ");
		}
		
		// Title,_ 
		if (this.title != null && !this.title.equals("")){
			stringBuffer.append(this.title);
			stringBuffer.append(", ");
		}
		
		// Institutions_
		if ( institutions != null ){
			for (Institution institution : institutions) {
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
		
		// PartnerOrganisations_
		if ( partners != null ){
			for (PartnerOrganisation partner : partners) {
				// InstitutionName,_ 
				if (partner.getOrganisation() != null){
					stringBuffer.append(partner.getOrganisation());
				}
				
				// City,_
				if (partner.getCity() != null){
					stringBuffer.append(", ");
					stringBuffer.append(partner.getCity() );
					
					// Country,_
					if (partner.getCountry() != null){
						stringBuffer.append(", ");
						stringBuffer.append(partner.getCountry());
					}
				}
				stringBuffer.append("<br>");
			}
		}
		
		// DegreeType,_ 
		if (this.degreeType != null && !this.degreeType.equals("")){
			stringBuffer.append(this.degreeType);
			stringBuffer.append(", ");
		}
		
		// releaseDay / releaseMonth / releaseYear
		if (this.yearReleaseDate != null){
			if (this.monthReleaseDate != null){
				if ( this.dayReleaseDate != null) {
					stringBuffer.append(this.dayReleaseDate);
					stringBuffer.append("/");
				}
				stringBuffer.append(this.monthReleaseDate);
				stringBuffer.append("/");
			}
			stringBuffer.append(this.yearReleaseDate);
		}
		
		
		return stringBuffer.toString();
	}
}