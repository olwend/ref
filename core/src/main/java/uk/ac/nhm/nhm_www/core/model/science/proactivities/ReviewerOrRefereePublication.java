package uk.ac.nhm.nhm_www.core.model.science.proactivities;



public class ReviewerOrRefereePublication extends ProfessionalActivity {

	private String publication;
	private String publicationType;
	private String reviewType;

	public ReviewerOrRefereePublication(String url, String title, final String reportingDate, String yearStartDate, String monthStartDate, String dayStartDate, 
			String yearEndDate, String monthEndDate, String dayEndDate, String publication, String publicationType, String reviewType) {
		super(url, title, reportingDate, yearStartDate, monthStartDate, dayStartDate, yearEndDate, monthEndDate, dayEndDate);
		this.publication = publication;
		this.publicationType = publicationType;
		this.reviewType = reviewType;
	}

	@Override
	public String getHTMLContent(String currentAuthor) {
		final StringBuffer stringBuffer = new StringBuffer();
		
		stringBuffer.append(" ");
		 
		// Publication,_
		if (this.publication != null){
			stringBuffer.append(this.publication);
			stringBuffer.append(", ");
		}
		
		// (PublicationType),_
		if (this.publicationType != null){
			stringBuffer.append("(");
			stringBuffer.append(this.publicationType);
			stringBuffer.append(")");
			stringBuffer.append(", ");
		}
		
		// ReviewType,_
		if (this.reviewType != null){
			stringBuffer.append(this.reviewType);
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