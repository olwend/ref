package uk.ac.nhm.nhm_www.core.model.science.proactivities;

import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.nhm.nhm_www.core.componentHelpers.ScientistProfileHelper;


public class InternalOrExternalPosition extends ProfessionalActivity {
	
    private static final Logger LOG = LoggerFactory.getLogger(InternalOrExternalPosition.class);
	
	private String positionType;
	private String internalOrExternal;
	private String officeHeldType;
	private String officeOtherHeldType;
	private Institution[] institutions;

	public InternalOrExternalPosition(String url, String title, final String reportingDate, String yearStartDate, 
			String monthStartDate, String dayStartDate, String yearEndDate, String monthEndDate, String dayEndDate,
			String internalOrExternal, String officeHeldType, String officeOtherHeldType, String inOrExInstitution) {
		super(url, title, reportingDate, yearStartDate, monthStartDate, dayStartDate, yearEndDate, monthEndDate, dayEndDate);
		this.internalOrExternal = internalOrExternal;
		this.officeHeldType = officeHeldType;
		this.officeOtherHeldType = officeOtherHeldType;
		
		assignJSON(inOrExInstitution);
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
		this.positionType = parameters[0];
		return super.getFilteredHTMLContent(currentAuthor, parameters);
	}

	@Override
	public String getHTMLContent(String currentAuthor) {
		final StringBuffer stringBuffer = new StringBuffer();
		
		stringBuffer.append("");
		
		if (positionTypeMatchesRequestedProfessionalActivity()) {
			
			stringBuffer.append("<p>");
			
			// Title,_
			if (this.title != null){
				stringBuffer.append(this.title);
				stringBuffer.append(", ");
			}
			
			// OfficeHeldType,_ || OfficeOtherHeldType,_
			if (this.officeHeldType != null && !this.officeHeldType.equals("Other")){
				stringBuffer.append(this.officeHeldType);
				stringBuffer.append(" ");
			} else {
				if (this.officeOtherHeldType != null){
					stringBuffer.append(this.officeOtherHeldType);
					stringBuffer.append(" ");
				}
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
						
						if (this.url != null) {
							stringBuffer.append("</a>");
							stringBuffer.append(", ");
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

	public boolean positionTypeMatchesRequestedProfessionalActivity() {
		if (this.internalOrExternal != null) {
			if (this.positionType.equals(this.internalOrExternal)){
				return true;
			} else {
				return false;
			}
		} else {
			if (this.positionType.equals(ScientistProfileHelper.PROFESSIONAL_ACTIVITY_PARAMETER_INTERNAL)){
				return true;
			} else {
				return false;
			}
		}
	}
}