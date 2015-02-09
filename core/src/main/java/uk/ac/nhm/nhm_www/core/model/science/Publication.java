package uk.ac.nhm.nhm_www.core.model.science;

import java.util.List;
import java.util.Set;

public abstract class Publication implements Comparable<Publication> {
	private List<String> authors;
	
	private String title;
	
	private int publicationYear;
	
	private boolean favorite;
	
	private String href;
	
	private String reportingDate;
	
	public Publication(final String title, final List<String> authorsList, boolean favorite, final int publicationYear,
			final String href, final String reportingDate) {
		this.authors = authorsList;
		this.title = title;
		this.publicationYear = publicationYear;
		this.favorite = favorite;
		this.href = href;
	}

	public abstract String getHTMLContent(final String currentAuthor, final boolean isFavorite);
	
	public boolean isValid() {
		return true;
	}

	public int compareTo(final Publication p) {
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

	public int getPublicationYear() {
		return publicationYear;
	}

	public boolean isFavorite() {
		return favorite;
	}

	public String getTitle() {
		return title;
	}

	public List<String> getAuthors() {
		return authors;
	}

	public String getLink() {
		return href;
	}

}
