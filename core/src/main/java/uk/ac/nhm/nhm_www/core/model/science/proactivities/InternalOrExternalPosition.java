package uk.ac.nhm.nhm_www.core.model.science.proactivities;

import uk.ac.nhm.nhm_www.core.componentHelpers.ScientistProfileHelper;


public class InternalOrExternalPosition extends ProfessionalActivity {
	
	private String positionType;
	private String internalOrExternal;
	private String officeHeldType;
	private String officeOtherHeldType;
	private String city;
	private String country;
	private String institution;

	public InternalOrExternalPosition(String url, String title, final String reportingDate, String yearStartDate, 
			String monthStartDate, String dayStartDate, String yearEndDate, String monthEndDate, String dayEndDate,
			String internalOrExternal, String officeHeldType, String officeOtherHeldType, String inOrExCity, String inOrExCountry, String inOrExInstitution) {
		super(url, title, reportingDate, yearStartDate, monthStartDate, dayStartDate, yearEndDate, monthEndDate, dayEndDate);
		this.internalOrExternal = internalOrExternal;
		this.officeHeldType = officeHeldType;
		this.officeOtherHeldType = officeOtherHeldType;
		this.city = inOrExCity;
		this.country = inOrExCountry;
		this.institution = inOrExInstitution;
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
			
			// <a href=url>InstitutionName</a>,_ 
			if (this.institution != null && !this.institution.equals("")){
				if (this.url != null) {
					stringBuffer.append("<a href=\"");
					stringBuffer.append(this.url);
					stringBuffer.append("\">");
				}
				
				stringBuffer.append(this.institution);
				
				if (this.url != null) {
					stringBuffer.append("</a>");
					stringBuffer.append(", ");
				}
			}
			
			// City,_
			if (this.city != null && !this.city.equals("")){
				stringBuffer.append(this.city);
				stringBuffer.append(", ");
			}	
			
			// Country,_
			if (this.country != null && !this.country.equals("")){
				stringBuffer.append(this.country);
				stringBuffer.append(", ");
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