package uk.ac.nhm.nhm_www.core.model.science.proactivities;

public class EventAdministration extends ProfessionalActivity {

	private String city;
	private String country;
	private String institution;
	private String eventType;
	private String role;

	public EventAdministration(String url, String title, final String reportingDate, String yearStartDate, String monthStartDate, String dayStartDate, 
			String yearEndDate, String monthEndDate, String dayEndDate, String role, String eventType, String eventCity, String eventCountry,
			String eventInstitution) {
		super(url, title, reportingDate, yearStartDate, monthStartDate, dayStartDate, yearEndDate, monthEndDate, dayEndDate);
		this.role = role;
		this.eventType = eventType;
		this.city = eventCity;
		this.country = eventCountry;
		this.institution = eventInstitution;
	}

	@Override
	public String getHTMLContent(String currentAuthor) {
		final StringBuffer stringBuffer = new StringBuffer();
		
		stringBuffer.append(" ");
		
		// AdministrativeRole,_
		if (this.role!= null){
			stringBuffer.append(this.role);
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
		
		// InstitutionName,_ 
		if (this.institution != null && !this.institution.equals("")){
			stringBuffer.append(this.institution);
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
		
		return stringBuffer.toString();
	}
}