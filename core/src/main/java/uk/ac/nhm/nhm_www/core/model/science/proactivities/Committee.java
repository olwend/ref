package uk.ac.nhm.nhm_www.core.model.science.proactivities;



public class Committee extends ProfessionalActivity {

	private String committeeRole;
	private String[] cities;
	private String[] countries;
	private String[] institutions;

	public Committee(String url, String title, final String reportingDate, String yearStartDate, String monthStartDate, String dayStartDate, 
			String yearEndDate, String monthEndDate, String dayEndDate, String committeeRole, String[] committeeCities, String[] committeeCountries, 
			String[] committeeInstitution) {
		super(url, title, reportingDate, yearStartDate, monthStartDate, dayStartDate, yearEndDate, monthEndDate, dayEndDate);
		this.committeeRole = committeeRole;
		this.cities = committeeCities;
		this.countries = committeeCountries;
		this.institutions = committeeInstitution;
	}

	@Override
	public String getHTMLContent(String currentAuthor) {
		final StringBuffer stringBuffer = new StringBuffer();
		
		stringBuffer.append(" ");
		
		// CommitteeRole,_
		if (this.committeeRole!= null){
			stringBuffer.append(this.committeeRole);
			stringBuffer.append(", ");
		}
		
		// CommitteeName,_
		if (this.title != null){
			stringBuffer.append(this.title);
			stringBuffer.append(" ");
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