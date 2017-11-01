package uk.ac.nhm.core.model.science.grants;

import java.text.DecimalFormat;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class Grant extends GrantTemplate {

	private List<String> principalInvestigators;
	private List<String> coInvestigators;
	private String funderName;
	private String funderNameOther;
	private String totaAwarded;
	private String nhmAwarded;
	private String proposalTitle;
	private String nhmURL;

	public Grant(String proposalTitle, final String reportingDate, String yearStartDate, String monthStartDate, String dayStartDate, 
			String yearEndDate, String monthEndDate, String dayEndDate, List<String> principalsList, List<String> coInvestigatorsList, 
			String funderName, String funderNameOther, String totalAwarded, String nhmAwarded, String nhmURL) {
		super(proposalTitle, reportingDate, yearStartDate, monthStartDate, dayStartDate, yearEndDate, monthEndDate, dayEndDate);
		this.proposalTitle = proposalTitle;
		this.principalInvestigators = principalsList;
		this.coInvestigators = coInvestigatorsList;
		this.funderName = funderName;
		this.funderNameOther = funderNameOther;
		this.totaAwarded = totalAwarded;
		this.nhmAwarded = nhmAwarded;
		this.nhmURL = nhmURL;
	}
	
	@Override
	public String getHTMLContent(String currentAuthor) {
		final StringBuffer stringBuffer = new StringBuffer();
		
		stringBuffer.append(" ");
		
		// ProposalTitle
		if (this.proposalTitle != null){
			if(this.nhmURL != null) {
				stringBuffer.append("<a href=" + this.nhmURL + ">");
			}
			stringBuffer.append(this.proposalTitle);
			if(this.nhmURL != null) {
				stringBuffer.append("</a>");
			}
			stringBuffer.append("<br>");
		}
		
		// Principal INV, Principal INV
		final List<String> principals = this.principalInvestigators;
		if (principals != null && principals.size() > 0) {
			String principalsString = StringUtils.join(principals.toArray(new String[principals.size()]), ", ");
			
			if(principalsString.contains(currentAuthor)) {
				//principalsString = principalsString.replaceAll(currentAuthor, "<b>" + currentAuthor + "</b>");
				stringBuffer.append("Role: Principal investigator");
				stringBuffer.append("<br/>");
			}
			else {
				stringBuffer.append("Role: Principal investigator");
				stringBuffer.append(principalsString);
				stringBuffer.append("<br/>");
			}
		}
		
		// CoInvestigator INV, CoInvestigator INV
		final List<String> coInvs = this.coInvestigators;
		if (coInvs != null && coInvs.size() > 0) {
			String coInvsString = StringUtils.join(coInvs.toArray(new String[coInvs.size()]), ", ");
			
			if(coInvsString.contains(currentAuthor)) {
				//coInvsString = coInvsString.replaceAll(currentAuthor, "<b>" + currentAuthor + "</b>");
				stringBuffer.append("Role: Co-investigator");
				stringBuffer.append("<br/>");
			}
			else {
				stringBuffer.append("Co-investigator: ");
				stringBuffer.append(coInvsString);
				stringBuffer.append("<br/>");
			}
		}	
		
		// FunderName
		if (this.funderName != null){
			stringBuffer.append("Funding: ");
			if ( this.funderName.equals("Other") && (this.funderNameOther != null && !this.funderNameOther.equals("") )  ){
				stringBuffer.append(this.funderNameOther);
			} else {
				stringBuffer.append(this.funderName);
			}
			stringBuffer.append("<br>");
		}
		
		// ValueAwarded
		if (this.totaAwarded != null){
			stringBuffer.append("Total value &pound");
			stringBuffer.append(priceToString(Double.parseDouble(this.totaAwarded)));
			
			// NHMAwarded
			if (this.nhmAwarded != null){
				stringBuffer.append(" (to Museum &pound");
				stringBuffer.append(priceToString(Double.parseDouble(this.nhmAwarded)));
				stringBuffer.append(") ");
			}
			
			stringBuffer.append("<br>");
		}
		
		// startYear - endYear. || startYear - on going.
		if (this.yearsd != null){
			stringBuffer.append("Dates: ");
			if (this.yeared != null) {
				stringBuffer.append(this.yearsd);
				stringBuffer.append(" - ");
				stringBuffer.append(this.yeared);
				//stringBuffer.append(".");
			} else {
				stringBuffer.append(this.yearsd);
				stringBuffer.append(" - ");
				stringBuffer.append("on going");
				//stringBuffer.append(".");
			}
		}
		
		return stringBuffer.toString();
	}
	
	
	public static String priceWithDecimal (Double price) {
	    DecimalFormat formatter = new DecimalFormat("###,###,###.00");
	    return formatter.format(price);
	}

	public static String priceWithoutDecimal (Double price) {
	    DecimalFormat formatter = new DecimalFormat("###,###,###.##");
	    return formatter.format(price);
	}

	public static String priceToString(Double price) {
	    String toShow = priceWithoutDecimal(price);
	    if (toShow.indexOf(".") > 0) {
	        return priceWithDecimal(price);
	    } else {
	        return priceWithoutDecimal(price);
	    }
	}
}