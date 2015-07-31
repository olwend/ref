package uk.ac.nhm.nhm_www.core.model.science.teaching;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class TeachingActivityTemplate implements Comparable<TeachingActivityTemplate> {
	private static final Logger LOG = LoggerFactory.getLogger(TeachingActivityTemplate.class);
	protected String url;
	protected String title;
	protected String reportingDate;
	protected String yearsd;
	protected String monthsd;
	protected String daysd;
	protected String yeared;
	protected String monthed;
	protected String dayed;
	
	public TeachingActivityTemplate(final String url, final String title, final String reportingDate, final String yearsd, final String monthsd, 
			final String daysd, final String yeared, final String monthed, final String dayed) {
		this.url = url;
		this.title = title;
		this.yearsd = yearsd;
		this.monthsd = monthsd;
		this.daysd = daysd;
		this.yeared = yeared;
		this.monthed = monthed;
		this.dayed = dayed;
	}

	public abstract String getHTMLContent(final String currentAuthor);
	
	public String getFilteredHTMLContent(final String currentAuthor, String[] parameter){
		String res = getHTMLContent(currentAuthor);
		return res;
	}
	
	public boolean isValid() {
		return true;
	}
	
	public int compareTo(final TeachingActivityTemplate p) {
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
		return this.daysd + "/" + this.monthsd + "/" + this.yearsd; 
	}
	
	public String getEndDate(){
		return this.dayed + "/" + this.monthed + "/" + this.yeared; 
	}
}