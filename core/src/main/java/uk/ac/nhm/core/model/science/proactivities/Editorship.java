package uk.ac.nhm.core.model.science.proactivities;



public class Editorship extends ProfessionalActivity {

	private String role;
	private String publisher;

	public Editorship(String url, String title, final String reportingDate, String yearStartDate, String monthStartDate, String dayStartDate, 
			String yearEndDate, String monthEndDate, String dayEndDate, String editorialRole, String publisher) {
		super(url, title, reportingDate, yearStartDate, monthStartDate, dayStartDate, yearEndDate, monthEndDate, dayEndDate);
		this.role = editorialRole;
		this.publisher = publisher;
	}

	@Override
	public String getHTMLContent(String currentAuthor) {
		final StringBuffer stringBuffer = new StringBuffer();
		
		// Role,_
		if (this.role != null){
			stringBuffer.append(this.role);
			stringBuffer.append(", ");
		}
		
		// <a href=url>PublicationTitle</a>,_ 
		if (this.title != null && !this.title.equals("")){
			if (this.url != null) {
				stringBuffer.append("<a href=\"");
				stringBuffer.append(this.url);
				stringBuffer.append("\">");
			}
			stringBuffer.append(this.title);
			if (this.url != null) {
				stringBuffer.append("</a>");
			}
			stringBuffer.append(", ");
		}
		
		// Publisher_
		if (this.publisher != null) {
			stringBuffer.append(this.publisher);
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