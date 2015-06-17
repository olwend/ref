package uk.ac.nhm.nhm_www.core.model.science;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

public class Other extends Publication{
	
	private boolean confidential;
	private int publicationMonth;
	private int publicationDay;
	private String publisherURL;
	private String journalName;
	private int volume;
	private int issue;
	private List<String> editors;
	private String publisher;
	private String publishingPlace;
	private int beginPage;
	private int endPage;
	private int page;
	private String doiLink;
	private String doiText;
	private String bookTitle;

	public Other(final String title, final  List<String> authorsList, final  boolean favorite, final  int publicationYear,
			final  String href,	final String reportingDate, boolean confidential, int publicationMonth, int publicationDay, 
			String publisherURL, String journalName, int volume, int issue, List<String> editors, String publisher, 
			String publishingPlace, int beginPage, int endPage, int page, String doiLink, String doiText, String bookTitle) {
		super(title, authorsList, favorite, publicationYear, href, reportingDate);
		this.confidential = confidential;
		this.publicationMonth = publicationMonth;
		this.publicationDay = publicationDay;
		this.publisherURL = publisherURL;
		this.journalName = journalName;
		this.volume = volume;
		this.issue = issue;
		this.editors = editors;
		this.publisher = publisher;
		this.publishingPlace = publishingPlace;
		this.beginPage = beginPage;
		this.endPage = endPage;
		this.page = page;
		this.doiLink = doiLink;
		this.doiText = doiText;
		this.bookTitle = bookTitle;
	}

	@Override
	public String getHTMLContent(final String author, final boolean isFavourite) {
		final StringBuffer stringBuffer = new StringBuffer();
		
		if (!confidential) {
			// Butler, R. J. and Barrett, P. M. (2013) Global Cambrian trilobite palaeobiogeography assessed using parsimony analysis of endemicity. In: D.A.T Harper and T Servais (eds) Early Palaeozoic Biogeography and Palaeogeography, pp. 273-296. Geological Society, London.
			// Authors (publication year) Title. Editors, Book Title, startPage - endPage. Publisher. Place.
			final List<String> authors = this.getAuthors();
			String authorsString = StringUtils.EMPTY;
			
			// First we normalize the author's name e.g: 
			// Ouvrard D N M  || OUVRARD DNM >> will become Ouvrard DNM
			String currentAuthor = normalizeName(author, false);
			String firstInitial = normalizeName(currentAuthor, true);
			
			Iterator<String> authorsIt = authors.iterator();
			List<String> processedAuthors = new ArrayList<String>();
			
			while( authorsIt.hasNext() ){
				String authorName = authorsIt.next().toString();
				processedAuthors.add(normalizeName(authorName, false));
			}
			
			if (processedAuthors.size() > 5 && isFavourite) {
				authorsString = StringUtils.join(processedAuthors.toArray(new String[processedAuthors.size()]), ", ", 0, 5) + ", et al";
			} else {
				authorsString = StringUtils.join(processedAuthors.toArray(new String[processedAuthors.size()]), ", ");
			}
			
			// LOG.error("This is the list of authors parsed: " + authorsString);
			// LOG.error("Current Author: " + currentAuthor);
			// LOG.error("First Initial Author: " + firstInitial);
			if (authorsString.contains(currentAuthor)) {
				authorsString = authorsString.replaceAll(currentAuthor, "<b>" + currentAuthor + "</b>");
			} else if (authorsString.contains(firstInitial)) {
				authorsString = authorsString.replaceAll(firstInitial, "<b>" + currentAuthor + "</b>");
			}
			//Remove name delimiters placed there by the normalizer
			authorsString = authorsString.replaceAll("#", "");
			
			// LOG.error("After being replaced: " + authorsString);
			
			stringBuffer.append("####This is a + "
					+ "Other"
					+ " #####");
			
			// Author NM, Author NM
			stringBuffer.append(authorsString);
			stringBuffer.append(". ");
			
			// (Year) || (Year, Month) || (Year, Month, Day)
			stringBuffer.append(" (");
			stringBuffer.append(this.getPublicationYear());
			if (this.getPublicationMonth() > 0 ){
				stringBuffer.append("/" + this.getPublicationMonth());
				if (this.getPublicationDay() > 0){
					stringBuffer.append("/" + this.getPublicationDay());
				}
			}
			stringBuffer.append(") ");
			
			// Link Opening for PublisherURL
			if (this.getPublisherURL() != null) {
				stringBuffer.append("<a href=\"");
				stringBuffer.append(this.getPublisherURL());
				stringBuffer.append("\">");
			}
			
			// Title
			stringBuffer.append("<i>");
			stringBuffer.append(this.getTitle());
			stringBuffer.append("</i>. ");
			
			// Link Closing for PublisherURL
			if (this.getPublisherURL() != null) {
				stringBuffer.append("</a>");
			}
			
			// <i>JournalName</i>
			if (this.getJournalName() != null ){				
				stringBuffer.append("<i>");
				stringBuffer.append(this.getJournalName());
				stringBuffer.append("</i>");
				stringBuffer.append(". ");
			}
			
			// <b>Volume</b>
			if (this.volume > 0) {
				stringBuffer.append("<b>");
				stringBuffer.append(this.volume);
				stringBuffer.append("</b>");
				if ( this.issue < 0 ) { 
					if ( (this.beginPage > 0 && this.endPage > 0) || this.page > 0) {
						stringBuffer.append(": "); 
					} else {
						stringBuffer.append(". ");
					}
				}
			}
			
			// (Issue)
			if (this.issue >= 0) {
				stringBuffer.append("(");
				stringBuffer.append(this.issue);
				stringBuffer.append(") ");
				if ( (this.beginPage > 0 && this.endPage > 0) || this.page > 0) { 
					stringBuffer.append(": "); 
				} else {
					stringBuffer.append(". ");
				}
			}
			
			// Editor N.M., Editor N.M. (Eds).
			final List<String> editors = this.getEditors();
			if (editors != null && editors.size() > 0) {
				String editorsString = StringUtils.join(editors.toArray(new String[editors.size()]), ", ");
				editorsString = editorsString.replaceAll(currentAuthor, "<b>" + currentAuthor + "</b>");
				
				if (editorsString.contains(currentAuthor)) {
					editorsString = editorsString.replaceAll(currentAuthor, "<b>" + currentAuthor + "</b>");
				} else if (authorsString.contains(firstInitial)) {
					editorsString = editorsString.replaceAll(firstInitial, "<b>" + currentAuthor + "</b>");
				}
				
				stringBuffer.append(editorsString);
				stringBuffer.append(" (Eds). ");
			}
			
			// In: <i>BookTitle</i>,
			if (this.getBookTitle() != null){
				stringBuffer.append("In: ");
				stringBuffer.append("<i>");
				stringBuffer.append(this.getBookTitle());
				stringBuffer.append("</i>. ");
			}
			
			// Publisher
			if (this.getPublisher() != null) {
				stringBuffer.append(this.getPublisher());
				if (this.getPublishingPlace() != null) { 
					stringBuffer.append(" : "); 
				} else {
					if ( (this.getBeginPage() > 0 && this.getEndPage() > 0) || this.getPage() > 0) { 
						stringBuffer.append(" : "); 
					} else {
						stringBuffer.append(". ");
					}
				}
			}
			
			// :PublishingLocation
			if (this.getPublishingPlace() != null) {
				stringBuffer.append(this.getPublishingPlace());
				if ( (this.getBeginPage() > 0 && this.getEndPage() > 0) || this.getPage() > 0 )  {
					stringBuffer.append(", "); 
				} else {
					stringBuffer.append(". ");
				}
			}
			
			// : PagesBegin-PagesEnd.
			if (this.getBeginPage() > 0 && this.getEndPage() > 0) {
				stringBuffer.append(this.getBeginPage());
				stringBuffer.append(" - ");
				stringBuffer.append(this.getEndPage());
				stringBuffer.append(". ");
			} else {
				if (this.getPage() > 0) {
					stringBuffer.append(this.getPage());
					stringBuffer.append(". ");
				}
			}
			
			// DOI hyperlink
			if (this.doiLink != null && this.doiText != null) {
				stringBuffer.append("<a href=\"");
				stringBuffer.append(this.doiLink);
				stringBuffer.append("\">");
				stringBuffer.append("doi: ");
				stringBuffer.append(this.doiText);
				stringBuffer.append("</a>");
			}
		}
			
		return stringBuffer.toString();
	}

	public boolean isConfidential() {
		return confidential;
	}

	public void setConfidential(boolean confidential) {
		this.confidential = confidential;
	}

	public int getPublicationMonth() {
		return publicationMonth;
	}

	public void setPublicationMonth(int publicationMonth) {
		this.publicationMonth = publicationMonth;
	}

	public int getPublicationDay() {
		return publicationDay;
	}

	public void setPublicationDay(int publicationDay) {
		this.publicationDay = publicationDay;
	}

	public String getPublisherURL() {
		return publisherURL;
	}

	public void setPublisherURL(String publisherURL) {
		this.publisherURL = publisherURL;
	}

	public String getJournalName() {
		return journalName;
	}

	public void setJournalName(String journalName) {
		this.journalName = journalName;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public int getIssue() {
		return issue;
	}

	public void setIssue(int issue) {
		this.issue = issue;
	}

	public List<String> getEditors() {
		return editors;
	}

	public void setEditors(List<String> editors) {
		this.editors = editors;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getPublishingPlace() {
		return publishingPlace;
	}

	public void setPublishingPlace(String publishingPlace) {
		this.publishingPlace = publishingPlace;
	}

	public int getBeginPage() {
		return beginPage;
	}

	public void setBeginPage(int beginPage) {
		this.beginPage = beginPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getDoiLink() {
		return doiLink;
	}

	public void setDoiLink(String doiLink) {
		this.doiLink = doiLink;
	}

	public String getDoiText() {
		return doiText;
	}

	public void setDoiText(String doiText) {
		this.doiText = doiText;
	}

	public String getBookTitle() {
		return bookTitle;
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}
	
}
