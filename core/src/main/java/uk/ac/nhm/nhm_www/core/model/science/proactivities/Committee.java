package uk.ac.nhm.nhm_www.core.model.science.proactivities;



public class Committee extends ProfessionalActivity {

	private String committeeMembership;
	private String city;
	private String country;
	private String institution;

	public Committee(String url, String title, final String reportingDate, String yearStartDate, String monthStartDate, String dayStartDate, 
			String yearEndDate, String monthEndDate, String dayEndDate, String committeeMembershipType, String committeeCity, String committeeCountry, 
			String committeeInstitution) {
		super(url, title, reportingDate, yearStartDate, monthStartDate, dayStartDate, yearEndDate, monthEndDate, dayEndDate);
		this.committeeMembership = committeeMembershipType;
		this.city = committeeCity;
		this.country = committeeCountry;
		this.institution = committeeInstitution;
	}

	@Override
	public String getHTMLContent(String currentAuthor) {
		final StringBuffer stringBuffer = new StringBuffer();
		
		stringBuffer.append("####This is a Committee####");
		stringBuffer.append(" ");
		
		// CommitteeMembership,_
		if (this.committeeMembership!= null){
			stringBuffer.append(this.committeeMembership);
			stringBuffer.append(", ");
		}
		
		// CommitteeName,_
		if (this.title != null){
			stringBuffer.append(this.title);
			stringBuffer.append(" ");
		}
		
		// <a href=url>InstitutionName</a>,_ 
		if (this.url != null) {
			stringBuffer.append("<a href=\"");
			stringBuffer.append(this.url);
			stringBuffer.append("\">");
		}
		if (this.institution != null && !this.institution.equals("")){
			stringBuffer.append(this.institution);
		}
		
		if (this.url != null) {
			stringBuffer.append("</a>");
			stringBuffer.append(", ");
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
		
		if (this.dayed != null && this.monthed != null && this.yeared != null){
			stringBuffer.append(this.yearsd);
			stringBuffer.append(" - ");
			stringBuffer.append(this.yeared);
		} else {
			stringBuffer.append("Ongoing");
		}
		
		return stringBuffer.toString();
	}
}