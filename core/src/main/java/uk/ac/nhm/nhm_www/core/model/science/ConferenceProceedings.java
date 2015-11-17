package uk.ac.nhm.nhm_www.core.model.science;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class ConferenceProceedings extends Publication{
	
	private String conferenceName;
	private String place;
	private String publisherURL;
	private List<String> editors;
	private String publisher;
	private String publishingPlace;
	private String beginPage;
	private String endPage;
	private String page;
	private String doiLink;
	private String doiText;
	private String volume;
	private String issue;
	private String publishedProceedings;
	private String startYear;
	private String startMonth;
	private String startDay;
	private String endYear;
	private String endMonth;
	private String endDay;

	public ConferenceProceedings(final String title, final  List<String> authorsList, final  boolean favorite, final String publicationYear,
			final  String href,	final String reportingDate, final  String conferenceName, String conferencePublisherURL, List<String> conferenceEditorsSet,
			String conferencePublisher, String conferencePublishingPlace, String conferenceBeginPage, String conferenceEndPage, String conferencePage, 
			String conferenceDoiText, String conferenceDoiLink, String conferenceVolume, String conferenceIssue, String conferencePublishedProceedings, 
			String startConferenceYear, String startConferenceMonth, String startConferenceDay, String endConferenceYear, String endConferenceMonth, String endConferenceDay) {
		super(title, authorsList, favorite, publicationYear, href, reportingDate);
		this.conferenceName = conferenceName;
		this.publisherURL = conferencePublisherURL;
		this.editors = conferenceEditorsSet;
		this.publisher = conferencePublisher;
		this.publishingPlace = conferencePublishingPlace;
		this.beginPage = conferenceBeginPage;
		this.endPage = conferenceEndPage;
		this.page = conferencePage;
		this.doiLink = conferenceDoiLink;
		this.doiText = conferenceDoiText;
		this.volume = conferenceVolume;
		this.issue = conferenceIssue;
		this.publishedProceedings = conferencePublishedProceedings;
		this.startYear = startConferenceYear;
		this.startMonth = startConferenceMonth;
		this.startDay = startConferenceDay;
		this.endYear = endConferenceYear;
		this.endMonth = endConferenceMonth;
		this.endDay = endConferenceDay;
	}

	public String getHTMLContent(final String author, final boolean isFavourite) {
		// Butler, R. J. and Barrett, P. M. (2013) Global Cambrian trilobite palaeobiogeography assessed using parsimony analysis of endemicity. In: D.A.T Harper and T Servais (eds) Early Palaeozoic Biogeography and Palaeogeography, pp. 273-296. Geological Society, London.
		// Authors (publication year) Title. Editors, Book Title, startPage - endPage. Publisher. Place.
		final List<String> authors = this.getAuthors();
		String authorsString = StringUtils.EMPTY;
		
		// First we normalize the author's name e.g: 
		// Ouvrard D N M  || OUVRARD DNM >> will become Ouvrard DNM
		String currentAuthor = normalizeName(author, false);
		String firstInitial = normalizeName(currentAuthor, true);
		String surname = getSurname(author);
		
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
		
//		LOG.error("This is the list of authors parsed: " + authorsString);
//		LOG.error("Current Author: " + currentAuthor);
//		LOG.error("First Initial Author: " + firstInitial);
		
		//Use regular expression to find full author name including all intials
		//Replace with same string in HTML bold tags
		if (authorsString.contains(surname)) {
			authorsString = authorsString.replaceAll(surname + "[A-Z]*", "<b>$0</b>");
		}
		
		//Remove name delimiters placed there by the normalizer
		authorsString = authorsString.replaceAll("#", "");
		
//		LOG.error("After being replaced: " + authorsString);
		
		final StringBuffer stringBuffer = new StringBuffer();
//		stringBuffer.append("####This is a Conference Proceeding Publication####");

		// Author NM, Author NM
		stringBuffer.append(authorsString);
		
		// (Year) || (Year, Month) || (Year, Day Month)
		stringBuffer.append(" (");
		stringBuffer.append(this.getPublicationYear());
		stringBuffer.append(") ");
		
		// Link Opening for PublisherURL
		if (this.publisherURL != null) {
			stringBuffer.append("<a href=\"");
			stringBuffer.append(this.publisherURL);
			stringBuffer.append("\">");
		}
		
		// Title._
		stringBuffer.append(this.getTitle());
		stringBuffer.append(". ");
		
		// Link Closing for PublisherURL
		if (this.publisherURL != null) {
			stringBuffer.append("</a>");
		}
		
		// Editor NM, Editor NM (Eds)._
		final List<String> editors = this.editors;
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
		
		// <i>Conference Title</i>,_
		if (this.conferenceName != null) {
			stringBuffer.append("<i>");
			stringBuffer.append(this.conferenceName);
			stringBuffer.append("</i>, ");
		}
		
		// Place,_
		if (this.place != null) {
			stringBuffer.append(this.place);
			stringBuffer.append(", ");
		}
		
		// Start Date - Finish Date, Year._
		if (this.startDay!= null && this.startMonth!= null && this.startYear!= null && this.endDay!= null && this.endMonth!= null && this.endYear!= null ){
			stringBuffer.append(this.startDay);
			
			if ( this.startMonth != this.endMonth){
				stringBuffer.append(this.startMonth);
				if (this.startYear != this.endYear){
					stringBuffer.append(" ");
					stringBuffer.append(this.startMonth);
				}
			} 
			stringBuffer.append(" - ");
			stringBuffer.append(this.endDay);
			stringBuffer.append(" ");
			stringBuffer.append(this.startMonth);
			stringBuffer.append(" ");
			stringBuffer.append(this.startMonth);
		}
		
		// <i>Published proceedings</i>_
		if (this.publishedProceedings != null) {
			stringBuffer.append("<i>");
			stringBuffer.append(this.publishedProceedings);
			stringBuffer.append("</i> ");
		}

		// <b>Volume</b>
		if (this.volume!= null) {
			stringBuffer.append("<b>");
			stringBuffer.append(this.volume);
			stringBuffer.append("</b> ");
			
			// (Issue) :_
			if (this.issue!= null) {
				stringBuffer.append("(");
				stringBuffer.append(this.issue);
				stringBuffer.append(")");
			}
			stringBuffer.append(" : ");
		}
		
		// Publisher :_
		if (this.publisher != null){
			stringBuffer.append(this.publisher);
			stringBuffer.append(" : ");

			// PublishPlace._
			if (this.publishingPlace != null) {
				stringBuffer.append(this.publishingPlace);
				stringBuffer.append(". ");
			}
		}
		
		// PagesBegin-PagesEnd._ || PageCount._
		if (this.beginPage!= null && this.endPage!= null) {
			stringBuffer.append(this.beginPage);
			stringBuffer.append(" - ");
			stringBuffer.append(this.endPage);
			stringBuffer.append(". ");
		} else {
			if (this.page!= null) {
				stringBuffer.append(this.page);
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
			
		return stringBuffer.toString();
	}

	public String getConferenceName() {
		return conferenceName;
	}

	public void setConferenceName(String conferenceName) {
		this.conferenceName = conferenceName;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getPublisherURL() {
		return publisherURL;
	}

	public void setPublisherURL(String publisherURL) {
		this.publisherURL = publisherURL;
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

	public String getBeginPage() {
		return beginPage;
	}

	public void setBeginPage(String beginPage) {
		this.beginPage = beginPage;
	}

	public String getEndPage() {
		return endPage;
	}

	public void setEndPage(String endPage) {
		this.endPage = endPage;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
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

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}
	
}
