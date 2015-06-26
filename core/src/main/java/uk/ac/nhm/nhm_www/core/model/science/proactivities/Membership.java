package uk.ac.nhm.nhm_www.core.model.science.proactivities;



public class Membership extends ProfessionalActivity {

	private String internalOrExternal;
	private String officeHeldType;
	private String officeOtherHeldType;
	private String city;
	private String country;
	private String institution;

	public Membership(String url, String title, final String reportingDate, String yearStartDate, 
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
	public String getHTMLContent(String currentAuthor) {
		final StringBuffer stringBuffer = new StringBuffer();
		
		stringBuffer.append("####This is an InternalOrExternalPosition####");
		stringBuffer.append(" ");
		
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
		if (this.dayed != null && this.monthed != null && this.yeared != null){
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
		
		return stringBuffer.toString();
	}

	public String getInternalOrExternal() {
		return internalOrExternal;
	}

	public void setInternalOrExternal(String internalOrExternal) {
		this.internalOrExternal = internalOrExternal;
	}
	
}