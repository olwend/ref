package uk.ac.nhm.nhm_www.core.model.science;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class Software extends Publication{
	
	private String doiText;
	private String doiLink;
	
	public Software(final String title, final List<String> authorsList, final boolean favorite, final String publicationYear,
			final String href,	final String reportingDate, String doiTxt, String doiURL){
		super(title, authorsList, favorite, publicationYear, href, reportingDate);
		this.doiText = doiTxt;
		this.doiLink = doiURL;
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
//		stringBuffer.append("####This is a Software Publication####");
		
		// Author NM, Author NM
		stringBuffer.append(authorsString);

		// _(Year)_
		stringBuffer.append(" (");
		stringBuffer.append(this.getPublicationYear());
		stringBuffer.append(") ");

		// Link Opening
		if (this.getLink() != null) {
			stringBuffer.append("<a href=\"");
			stringBuffer.append(this.getLink());
			stringBuffer.append("\">");
		}
		
		// <i>Title<i>_
		stringBuffer.append("<i>");
		stringBuffer.append(this.getTitle());
		stringBuffer.append("</i>");

		// Link Closing
		if (this.getLink() != null) {
			stringBuffer.append("</a>");
		}
		
		stringBuffer.append(". ");
		
		// DOI hyperlink
		if (this.doiLink != null && this.doiText != null) {
			stringBuffer.append(" <a href=\"");
			stringBuffer.append(this.doiLink);
			stringBuffer.append("\">");
			stringBuffer.append("doi: ");
			stringBuffer.append(this.doiText);
			stringBuffer.append("</a>");
		}
			
		return stringBuffer.toString();
	}

	public String getDoiText() {
		return doiText;
	}

	public void setDoiText(String doiText) {
		this.doiText = doiText;
	}

	public String getDoiLink() {
		return doiLink;
	}

	public void setDoiLink(String doiLink) {
		this.doiLink = doiLink;
	}
	
}
