package uk.ac.nhm.nhm_www.core.model.science.proactivities;



public class Fellowship extends ProfessionalActivity {

	private String[] cities;
	private String[] countries;
	private String[] organisations;

	public Fellowship(String url, String title, final String reportingDate, String yearStartDate, String monthStartDate, String dayStartDate, 
			String yearEndDate, String monthEndDate, String dayEndDate, String fellowshipOrganisations) {
		super(url, title, reportingDate, yearStartDate, monthStartDate, dayStartDate, yearEndDate, monthEndDate, dayEndDate);
		this.cities = fellowshipCities;
		this.countries = fellowshipCountries;
		this.organisations = fellowshipOrganisations;
	}

	@Override
	public String getHTMLContent(String currentAuthor) {
		final StringBuffer stringBuffer = new StringBuffer();
		
		stringBuffer.append(" ");
		
		// Title,_
		if (this.title != null){
			stringBuffer.append(this.title);
			stringBuffer.append(", ");
		}
		
		if (organisations.length > 0){
			for (int i = 0; i < organisations.length; i++) {
				// <a href=url>OrganisationName</a>,_ 
				if (organisations[i] != null && !organisations[i].equals("")){
					if (this.url != null) {
						stringBuffer.append("<a href=\"");
						stringBuffer.append(this.url);
						stringBuffer.append("\">");
					}
					
					stringBuffer.append(this.organisations[i]);
					
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