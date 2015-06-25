package uk.ac.nhm.nhm_www.core.model.science;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

public class Poster extends Publication{
	
	private String nameOfConference;
	private String city;

	public Poster(final String title, final  List<String> authorsList, final  boolean favorite, final  int publicationYear,
			final  String href,	final String reportingDate, String posterNameOfConference, String posterCity) {
		super(title, authorsList, favorite, publicationYear, href, reportingDate);
		this.nameOfConference = posterNameOfConference;
		this.city = posterCity;
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
								+ "Poster"
							+ " #####");
		
		// Author NM, Author NM
		stringBuffer.append(authorsString);

		// _(Year)_
		stringBuffer.append(" (");
		stringBuffer.append(this.getPublicationYear());
		stringBuffer.append(") ");
		
		// Title._
		stringBuffer.append("<i>");
		stringBuffer.append(getTitle());
		stringBuffer.append("</i>. ");
		
		//Presented At nConference._
		if( this.nameOfConference != null ){
			stringBuffer.append("Presented at ");
			stringBuffer.append(this.nameOfConference);
			stringBuffer.append(". ");			
		}
		
		// City._
		if ( this.city != null ) {
			stringBuffer.append(this.city);
			stringBuffer.append(". ");		
		}
			
		return stringBuffer.toString();
	}

	public String getNameOfConference() {
		return nameOfConference;
	}

	public void setNameOfConference(String nameOfConference) {
		this.nameOfConference = nameOfConference;
	}
	
}
