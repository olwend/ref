package uk.ac.nhm.nhm_www.core.model.science;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThesisDissertation extends Publication{
	
	private static final Logger LOG = LoggerFactory.getLogger(ThesisDissertation.class);
	
	private String thesisType;
	private List<String> supervisors;
	private String publisher;
	private String place;
	private String beginPage;
	private String endPage;
	private String page;

	public ThesisDissertation(final String title, final List<String> authorsList, final boolean favorite, final String publicationYear,
			final String href, final String reportingDate, List<String> supervisorsSet, String thesisType, String thesisPublisher, String thesisPublishingPlace, 
			String thesisBeginPage, String thesisEndPage, String thesisPage){
		super(title, authorsList, favorite, publicationYear, href, reportingDate);
		this.publisher = thesisPublisher;
		this.place = thesisPublishingPlace;
		this.thesisType = thesisType;
		this.supervisors = supervisorsSet;
		this.beginPage = thesisBeginPage;
		this.endPage = thesisEndPage;
		this.page = thesisPage;
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
//		stringBuffer.append("####This is a Thesis | Dissertation Publication####");
		
		// Author NM, Author NM
		stringBuffer.append(authorsString);

		// _(Year)_
		stringBuffer.append(" (");
		stringBuffer.append(this.getPublicationYear());
		stringBuffer.append(") ");
		
		// ThesisTitle._	
		stringBuffer.append(this.getTitle());
		stringBuffer.append(". ");
		
		// ThesisType._
		stringBuffer.append(this.thesisType);
		stringBuffer.append(". ");
		
		// (Supervisor(s)) Supervisor NM, Supervisor NM._
		final List<String> supervisors = this.supervisors;
		if (supervisors != null && supervisors.size() > 0) {
			String supervisorsString = StringUtils.join(supervisors.toArray(new String[supervisors.size()]), ", ");
			supervisorsString = supervisorsString.replaceAll(currentAuthor, "<b>" + currentAuthor + "</b>");
			if (supervisorsString.contains(currentAuthor)) {
				supervisorsString = supervisorsString.replaceAll(currentAuthor, "<b>" + currentAuthor + "</b>");
			} else if (authorsString.contains(firstInitial)) {
				supervisorsString = supervisorsString.replaceAll(firstInitial, "<b>" + currentAuthor + "</b>");
			}
			
			stringBuffer.append("(Supervisor(s)) ");
			stringBuffer.append(supervisorsString);
			stringBuffer.append(". ");
		}
		
		// Publisher :_
		if (this.publisher != null) {
			stringBuffer.append(this.publisher);
			stringBuffer.append(" : "); 
		}
		
		// :PublishingLocation._
		if (this.place != null) {
			stringBuffer.append(this.place);
			stringBuffer.append(". ");
		}
		
		// PagesBegin - PagesEnd._ || PageCount._
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

	public String getThesisType() {
		return thesisType;
	}

	public void setThesisType(String thesisType) {
		this.thesisType = thesisType;
	}

	public List<String> getSupervisors() {
		return supervisors;
	}

	public void setSupervisors(List<String> supervisors) {
		this.supervisors = supervisors;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
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
	
}
