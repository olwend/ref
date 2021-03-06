package uk.ac.nhm.core.model.science;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

public class InternetPublication extends Publication{
	
	private String publisher;
	private String beginPage;
	private String endPage;
	private String page;
	private String publicationMonth;
	private String publicationDay;
	private String publisherURL;

	public InternetPublication(final String title, final List<String> authorsList, final boolean favorite, final String publicationYear,
			String iPublicationMonth, String iPublicationDay, final  String href,	final String reportingDate, String internetPublisher, 
			String publisherURL, String internetBeginPage, String internetEndPage, String internetPage) {
		super(title, authorsList, favorite, publicationYear, href, reportingDate);
		this.publicationMonth = iPublicationMonth;
		this.publicationDay = iPublicationDay;
		this.publisher = internetPublisher;
		this.publisherURL = publisherURL;
		this.beginPage = internetBeginPage;
		this.endPage = internetEndPage;
		this.page = internetPage;
	}

	@Override
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
			//processedAuthors.add(normalizeName(authorName, false));
			String formattedName = null;
			if(authorName.contains(surname)) {
				formattedName = "<b>" + authorName + "</b>";
			}else {
				formattedName = authorName;
			}
			processedAuthors.add(formattedName);
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


		//Remove name delimiters placed there by the normalizer
		authorsString = authorsString.replaceAll("#", "");
		
//		LOG.error("After being replaced: " + authorsString);
		
		final StringBuffer stringBuffer = new StringBuffer();
//		stringBuffer.append("####This is an Internet Publication####");
		
		// Author NM, Author NM
		stringBuffer.append(authorsString);

		// (Year) || (Year, Month) || (Year, Day Month)
		stringBuffer.append(" (");
		stringBuffer.append(this.getPublicationYear());
		if (this.publicationDay != null){
			stringBuffer.append(", " + this.publicationDay);
			if (this.publicationMonth != null ){
				stringBuffer.append(" " + this.publicationMonth);
			}
		}
		stringBuffer.append(") ");
		
		//Link Opening
		if (this.publisherURL != null) {
			stringBuffer.append("<a href=\"");
			stringBuffer.append(this.publisherURL);
			stringBuffer.append("\">");
		}
		
		// <i>Title</i>._ 
		stringBuffer.append("<i>");
		stringBuffer.append(this.getTitle());
		stringBuffer.append("</i>. ");

		//Link Closing
		if (this.publisherURL != null) {
			stringBuffer.append("</a>");
		}
		
		// <i>TitleOfNewspaper/Magazine</i>_
		if (this.publisher != null) {
			stringBuffer.append("<i>");
			stringBuffer.append(this.publisher);
			stringBuffer.append("</i> ");
		}
		
		// PagesBegin-PagesEnd._ || PageCount._
		if (this.beginPage != null && this.endPage != null) {
			stringBuffer.append(this.beginPage);
			stringBuffer.append(" - ");
			stringBuffer.append(this.endPage);
			stringBuffer.append(". ");
		} else {
			if (this.page != null) {
				stringBuffer.append(this.page);
				stringBuffer.append(". ");
			}
		}
			
		return stringBuffer.toString();
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
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

	public String getPublicationMonth() {
		return publicationMonth;
	}

	public void setPublicationMonth(String publicationMonth) {
		this.publicationMonth = publicationMonth;
	}

	public String getPublicationDay() {
		return publicationDay;
	}

	public void setPublicationDay(String publicationDay) {
		this.publicationDay = publicationDay;
	}

	public String getPublisherURL() {
		return publisherURL;
	}

	public void setPublisherURL(String publisherURL) {
		this.publisherURL = publisherURL;
	}
	
}
