package uk.ac.nhm.nhm_www.core.model.science;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ProfessionalActivity implements Comparable<ProfessionalActivity> {
	private static final Logger LOG = LoggerFactory.getLogger(ProfessionalActivity.class);
	protected String title;
	protected String reportingDate;
	protected String yearsd;
	protected String monthsd;
	protected String daysd;
	protected String yeared;
	protected String monthed;
	protected String dayed;
	
	public ProfessionalActivity(final String title, final String reportingDate, final String yearsd, final String monthsd, 
			final String daysd, final String yeared, final String monthed, final String dayed) {
		this.title = title;
		this.yearsd = yearsd;
		this.monthsd = monthsd;
		this.daysd = daysd;
		this.yeared = yeared;
		this.monthed = monthed;
		this.dayed = dayed;
	}

	public abstract String getHTMLContent(final String currentAuthor);
	
	public boolean isValid() {
		return true;
	}
	
	public int compareTo(final ProfessionalActivity p) {
		if (p.reportingDate == null) {
			return 1;
		}
		
		if (this.reportingDate == null) {
			return -1;
		}
		
		final int reportingDatesComparation = p.reportingDate.compareTo(p.reportingDate) * -1;
		
		if (reportingDatesComparation == 0) {
			return this.title.compareTo(p.title);
		}
		
		return reportingDatesComparation;
		
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getStartDate(){
		return daysd + "/" + monthsd + "/" + yearsd; 
	}
	
	public String getEndDate(){
		return dayed + "/" + monthed + "/" + yeared; 
	}
}