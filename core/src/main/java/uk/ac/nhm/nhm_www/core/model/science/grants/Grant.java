package uk.ac.nhm.nhm_www.core.model.science.grants;

import java.util.List;

public class Grant extends GrantTemplate {

	private List<String> principalInvestigators;
	private List<String> coInvestigators;
	private String funderName;
	private String funderNameOther;
	private String totaAwarded;
	private String nhmAwarded;

	public Grant(String proposalTitle, final String reportingDate, String yearStartDate, String monthStartDate, String dayStartDate, 
			String yearEndDate, String monthEndDate, String dayEndDate, List<String> principalsList, List<String> coInvestigatorsList, 
			String funderName, String funderNameOther, String totalAwarded, String nhmAwarded) {
		super(proposalTitle, reportingDate, yearStartDate, monthStartDate, dayStartDate, yearEndDate, monthEndDate, dayEndDate);
		this.principalInvestigators = principalsList;
		this.coInvestigators = coInvestigatorsList;
		this.funderName = funderName;
		this.funderNameOther = funderNameOther;
		this.totaAwarded = totalAwarded;
		this.nhmAwarded = nhmAwarded;
	}
	

	@Override
	public String getHTMLContent(String currentAuthor) {
		final StringBuffer stringBuffer = new StringBuffer();
		
		stringBuffer.append(" ");
		
		// ProposalTitle,_
		if (this.name != null){
			stringBuffer.append(this.name);
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