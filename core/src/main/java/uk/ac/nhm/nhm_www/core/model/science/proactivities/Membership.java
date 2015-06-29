package uk.ac.nhm.nhm_www.core.model.science.proactivities;



public class Membership extends ProfessionalActivity {

	private String city;
	private String country;
	private String institution;
	private String role;

	public Membership(String url, String title, final String reportingDate, String yearStartDate, 
			String monthStartDate, String dayStartDate, String yearEndDate, String monthEndDate, String dayEndDate, 
			String membershipCity, String membershipCountry, String membershipInstitution, String membershipRole) {
		super(url, title, reportingDate, yearStartDate, monthStartDate, dayStartDate, yearEndDate, monthEndDate, dayEndDate);
		this.role = membershipRole;
		this.city = membershipCity;
		this.country = membershipCountry;
		this.institution = membershipInstitution;
	}

	public String getHTMLContent(String currentAuthor) {
		final StringBuffer stringBuffer = new StringBuffer();
		
		stringBuffer.append("####This is an InternalOrExternalPosition####");
		stringBuffer.append(" ");
		
		// Role,_
		if (this.role != null){
			stringBuffer.append(this.role);
			stringBuffer.append(", ");
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
	
}