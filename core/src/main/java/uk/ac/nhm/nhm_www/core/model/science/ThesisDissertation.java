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
	private int paginationBeginPage;
	private int paginationEndPage;
	private int page;

	public ThesisDissertation(final String title, final  List<String> authorsList, final  boolean favorite, final  int publicationYear,
			final  String href,	final String reportingDate, List<String> supervisorsSet, String thesisType, String thesisPublisher, String thesisPublishingPlace, 
			int thesisBeginPage, int thesisEndPage, int thesisPage){
		super(title, authorsList, favorite, publicationYear, href, reportingDate);
		this.publisher = thesisPublisher;
		this.place = thesisPublishingPlace;
		this.thesisType = thesisType;
		this.supervisors = supervisorsSet;
		this.paginationBeginPage = thesisBeginPage;
		this.paginationEndPage = thesisEndPage;
		this.page = thesisPage;
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

	@Override
	public String getHTMLContent(final String author, final boolean isFavourite) {
		// Butler, R. J. and Barrett, P. M. (2013) Global Cambrian trilobite palaeobiogeography assessed using parsimony analysis of endemicity. In: D.A.T Harper and T Servais (eds) Early Palaeozoic Biogeography and Palaeogeography, pp. 273-296. Geological Society, London.
		// Authors (publication year) Title. Editors, Book Title, startPage - endPage. Publisher. Place.
		final List<String> authors = this.getAuthors();
		String authorsString = StringUtils.EMPTY;
		
		LOG.error("Inside a Thesis Dissertation");
		
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
								+ "ThesisDissertation"
							+ " #####");
		
		// Author NM
		stringBuffer.append(authorsString);
		stringBuffer.append(". ");

		// (Year)
		stringBuffer.append(" (");
		stringBuffer.append(this.getPublicationYear());
		stringBuffer.append(") ");
		
		// ThesisTitle.		
		stringBuffer.append(this.getTitle());
		stringBuffer.append(". ");
		
		// ThesisType.
		stringBuffer.append(this.getThesisType());
		stringBuffer.append(". ");
		
		// Supervisor N.M., Supervisor N.M. (Eds).
		final List<String> supervisors = this.getSupervisors();
		if (supervisors != null && supervisors.size() > 0) {
			String supervisorsString = StringUtils.join(supervisors.toArray(new String[supervisors.size()]), ", ");
			supervisorsString = supervisorsString.replaceAll(currentAuthor, "<b>" + currentAuthor + "</b>");
			if (supervisorsString.contains(currentAuthor)) {
				supervisorsString = supervisorsString.replaceAll(currentAuthor, "<b>" + currentAuthor + "</b>");
			} else if (authorsString.contains(firstInitial)) {
				supervisorsString = supervisorsString.replaceAll(firstInitial, "<b>" + currentAuthor + "</b>");
			}
			
			stringBuffer.append(supervisorsString);
			stringBuffer.append(" (Eds). ");
		}
		
		
		// Publisher
		if (this.publisher != null) {
			stringBuffer.append(this.publisher);
			if (this.place != null) { stringBuffer.append(" : "); }
		}
		
		// :PublishingLocation, 
		if (this.place != null) {
			stringBuffer.append(this.place);
			if (this.paginationBeginPage > 0 && this.paginationEndPage > 0) { stringBuffer.append(", "); }
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
		
		stringBuffer.append(".");
		
		return stringBuffer.toString();
	}
	
}
