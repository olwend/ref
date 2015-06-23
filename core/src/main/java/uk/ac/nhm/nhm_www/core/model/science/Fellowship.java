package uk.ac.nhm.nhm_www.core.model.science;


public class Fellowship extends ProfessionalActivity {

	public Fellowship(String title, final String reportingDate, String yearStartDate, String monthStartDate, String dayStartDate, String yearEndDate, String monthEndDate, String dayEndDate) {
		super(title, reportingDate, yearStartDate, monthStartDate, dayStartDate, yearEndDate, monthEndDate, dayEndDate);
	}

	@Override
	public String getHTMLContent(String currentAuthor) {
		final StringBuffer stringBuffer = new StringBuffer();
		
		stringBuffer.append("####This is a Fellowship####");
		
		stringBuffer.append(" ");
		stringBuffer.append(this.title);
		
		stringBuffer.append(" ");
		stringBuffer.append(this.daysd + "/" + this.monthsd + "/" + this.yearsd);
		stringBuffer.append("-");
		stringBuffer.append(this.dayed + "/" + this.monthed + "/" + this.yeared);
		
		return stringBuffer.toString();
	}

}
