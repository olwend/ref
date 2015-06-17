package uk.ac.nhm.nhm_www.core.model.science;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

public class InternetPublication extends Publication{
	
	private String publisher;
	private int paginationBeginPage;
	private int paginationEndPage;
	private int page;
	private int iPublicationMonth;
	private int iPublicationDay;
	private String publisherURL;

	public InternetPublication(final String title, final  List<String> authorsList, final  boolean favorite, final  int publicationYear,
			int iPublicationMonth, int iPublicationDay, final  String href,	final String reportingDate, String internetPublisher, 
			String publisherURL, int internetBeginPage, int internetEndPage, int internetPage) {
		super(title, authorsList, favorite, publicationYear, href, reportingDate);
		this.iPublicationMonth = iPublicationMonth;
		this.iPublicationDay = iPublicationDay;
		this.publisher = internetPublisher;
		this.publisherURL = publisherURL;
		this.paginationBeginPage = internetBeginPage;
		this.paginationEndPage = internetEndPage;
		this.page = internetPage;
	}

	public String getPublisherURL() {
		return publisherURL;
	}

	public void setPublisherURL(String publisherURL) {
		this.publisherURL = publisherURL;
	}

	public int getiPublicationMonth() {
		return iPublicationMonth;
	}

	public void setiPublicationMonth(int iPublicationMonth) {
		this.iPublicationMonth = iPublicationMonth;
	}

	public int getiPublicationDay() {
		return iPublicationDay;
	}

	public void setiPublicationDay(int iPublicationDay) {
		this.iPublicationDay = iPublicationDay;
	}

	public int getPaginationBeginPage() {
		return paginationBeginPage;
	}

	public void setPaginationBeginPage(int paginationBeginPage) {
		this.paginationBeginPage = paginationBeginPage;
	}

	public int getPaginationEndPage() {
		return paginationEndPage;
	}

	public void setPaginationEndPage(int paginationEndPage) {
		this.paginationEndPage = paginationEndPage;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
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
		if (authorsString.contains(currentAuthor)) {
			authorsString = authorsString.replaceAll(currentAuthor, "<b>" + currentAuthor + "</b>");
		} else if (authorsString.contains(firstInitial)) {
			authorsString = authorsString.replaceAll(firstInitial, "<b>" + currentAuthor + "</b>");
		}
		//Remove name delimiters placed there by the normalizer
		authorsString = authorsString.replaceAll("#", "");
		
//		LOG.error("After being replaced: " + authorsString);
		
		final StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("####This is a + "
								+ "Internet Publication"
							+ " #####");
		
		// Author NM, Author NM
		stringBuffer.append(authorsString);
		stringBuffer.append(". ");

		// (Year) || (Year, Month) || (Year, Month, Day)
		stringBuffer.append(" (");
		stringBuffer.append(this.getPublicationYear());
		if (this.getiPublicationMonth() > 0 ){
			stringBuffer.append("/" + this.getiPublicationMonth());
			if (this.getiPublicationDay() > 0){
				stringBuffer.append("/" + this.getiPublicationDay());
			}
		}
		stringBuffer.append(") ");
		
		
		//Link Opening
		if (this.getLink() != null) {
			stringBuffer.append("<a href=\"");
			stringBuffer.append(this.getPublisherURL());
			stringBuffer.append("\">");
		}
		
		//Title
		stringBuffer.append("<i>");
		stringBuffer.append(this.getTitle());
		stringBuffer.append("</i>");

		//Link Closing
		if (this.getLink() != null) {
			stringBuffer.append("</a>");
		}
		
		// Publisher
		if (this.publisher != null) {
			stringBuffer.append(this.publisher);
			if ( (this.paginationBeginPage > 0 && this.paginationEndPage > 0) || this.page > 0) { stringBuffer.append(" : "); }
		}
		
		// PagesBegin-PagesEnd.
		if (this.paginationBeginPage > 0 && this.paginationEndPage > 0) {
			stringBuffer.append(this.paginationBeginPage);
			stringBuffer.append(" - ");
			stringBuffer.append(this.paginationEndPage);
		} else {
			if (this.page > 0) {
				stringBuffer.append(this.page);
			}
		}
			
		return stringBuffer.toString();
	}
	
}
