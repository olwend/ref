package uk.ac.nhm.nhm_www.core.model.science;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

public class Report extends Publication{
	
	private boolean confidential;
	
	/* Pagination */
	private int beginPage;
	private int endPage;

	private String publisher;
	private String publishingPlace;
	private int page;
	
	public Report(final String title, final  List<String> authorsList, final  boolean favorite, final  int publicationYear,
			final  String href,	final String reportingDate, boolean confidential, final int paginationBeginPage,
			final int paginationEndPage, final String publisher, final String place, int page) {
		super(title, authorsList, favorite, publicationYear, href, reportingDate);
		this.confidential = confidential;
		this.beginPage = paginationBeginPage;
		this.endPage = paginationEndPage;
		this.publisher = publisher;
		this.publishingPlace = place;
		this.page = page;
	}
	
	@Override
	public String getHTMLContent(final String author, final boolean isFavourite) {
		// Butler, R. J. and Barrett, P. M. (2013) Global Cambrian trilobite palaeobiogeography assessed using parsimony analysis of endemicity. In: D.A.T Harper and T Servais (eds) Early Palaeozoic Biogeography and Palaeogeography, pp. 273-296. Geological Society, London.
		// Authors (publication year) Title. Editors, Book Title, startPage - endPage. Publisher. Place.
		final StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("");
		
		if (!confidential) {
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
			
			//LOG.error("This is the list of authors parsed: " + authorsString);
			//LOG.error("Current Author: " + currentAuthor);
			//LOG.error("First Initial Author: " + firstInitial);
			if (authorsString.contains(currentAuthor)) {
				authorsString = authorsString.replaceAll(currentAuthor, "<b>" + currentAuthor + "</b>");
			} else if (authorsString.contains(firstInitial)) {
				authorsString = authorsString.replaceAll(firstInitial, "<b>" + currentAuthor + "</b>");
			}
			//Remove name delimiters placed there by the normalizer
			authorsString = authorsString.replaceAll("#", "");
			
			//LOG.error("After being replaced: " + authorsString);
			
			stringBuffer.append("####This is a Report Publication####");
			
			// Author NM, Author NM
			stringBuffer.append(authorsString);
			
			// _(Year)_
			stringBuffer.append(" (");
			stringBuffer.append(this.getPublicationYear());
			stringBuffer.append(") ");
			
			// Title._
			stringBuffer.append(this.getTitle());
			stringBuffer.append(". ");
			
			// Publisher :_
			if (this.publisher != null){
				stringBuffer.append(" : ");			
				stringBuffer.append(this.publisher);
			}
			
			// PublishPlace._
			if (this.publishingPlace != null) {
				stringBuffer.append(this.publishingPlace);
				stringBuffer.append(". ");
			}
			
			// PagesBegin-PagesEnd._ || PageCount._
			if (this.beginPage > 0 && this.endPage > 0) {
				stringBuffer.append(this.beginPage);
				stringBuffer.append(" - ");
				stringBuffer.append(this.endPage);
				stringBuffer.append(". ");
			} else {
				if (this.page > 0) {
					stringBuffer.append(this.page);
					stringBuffer.append(". ");
				}
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

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getPlace() {
		return publishingPlace;
	}

	public void setPlace(String place) {
		this.publishingPlace = place;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}
	
}
