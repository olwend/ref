package uk.ac.nhm.nhm_www.core.model.science.proactivities;



public class Membership extends ProfessionalActivity {

	private String[] cities;
	private String[] countries;
	private String[] institutions;
	private String role;

	public Membership(String url, String title, final String reportingDate, String yearStartDate, 
			String monthStartDate, String dayStartDate, String yearEndDate, String monthEndDate, String dayEndDate, 
			String[] membershipCities, String[] membershipCountries, String[] membershipInstitution, String membershipRole) {
		super(url, title, reportingDate, yearStartDate, monthStartDate, dayStartDate, yearEndDate, monthEndDate, dayEndDate);
		this.role = membershipRole;
		this.cities = membershipCities;
		this.countries = membershipCountries;
		this.institutions = membershipInstitution;
	}

	public String getHTMLContent(String currentAuthor) {
		final StringBuffer stringBuffer = new StringBuffer();
		
		stringBuffer.append(" ");
		
		// Role,_
		if (this.role != null){
			stringBuffer.append(this.role);
			stringBuffer.append(", ");
		}
		
		if (institutions.length > 0){
			for (int i = 0; i < institutions.length; i++) {
				// <a href=url>InstitutionName</a>,_ 
				if (institutions[i] != null && !institutions[i].equals("")){
					if (this.url != null) {
						stringBuffer.append("<a href=\"");
						stringBuffer.append(this.url);
						stringBuffer.append("\">");
					}
					
					stringBuffer.append(this.institutions[i]);
					
					if (this.url != null) {
						stringBuffer.append("</a>");
						stringBuffer.append(", ");
					}
				}
				
				// City,_
				if(cities.length > 0 ){
					if (cities[i] != null && !cities[i].equals("")){
						stringBuffer.append(cities[i]);
						stringBuffer.append(", ");
					}	
				}
				
				// Country,_
				if (countries.length > 0){
					if (countries[i] != null && !countries[i].equals("")){
						stringBuffer.append(this.countries[i]);
						stringBuffer.append(", ");
					}
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