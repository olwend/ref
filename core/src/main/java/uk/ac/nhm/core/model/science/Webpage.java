package uk.ac.nhm.core.model.science;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

public class Webpage extends Publication{
	
	private String publisher;
	private String publisherURL;
	private String publishingPlace;

	public Webpage(final String title, final List<String> authorsList, final boolean favorite, final String publicationYear,
			final String href, final String reportingDate, String webpagePublisher, String webpagePublisherURL, String webpagePublishingPlace){
		super(title, authorsList, favorite, publicationYear, href, reportingDate);
		this.publisher = webpagePublisher;
		this.publisherURL = webpagePublisherURL;
		this.publishingPlace = webpagePublishingPlace;
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

		
		//Remove name delimiters placed there by the normalizer
		authorsString = authorsString.replaceAll("#", "");
		
//		LOG.error("After being replaced: " + authorsString);
		
		final StringBuffer stringBuffer = new StringBuffer();
//		stringBuffer.append("####This is a Webpage Publication####");

		// Author NM, Author NM
		stringBuffer.append(authorsString);
		
		// _(Year)_
		stringBuffer.append(" (");
		stringBuffer.append(this.getPublicationYear());
		stringBuffer.append(") ");
		
		//Link Opening for publisher-url
		if (this.publisherURL != null) {
			stringBuffer.append("<a href=\"");
			stringBuffer.append(this.publisherURL);
			stringBuffer.append("\">");
		}
		
		// Title_
		stringBuffer.append("<i>");
		stringBuffer.append(this.getTitle());
		stringBuffer.append("</i>");

		//Link Closing for publisher-url
		if (this.publisherURL != null) {
			stringBuffer.append("</a>");
		}
			
		// Publisher :_
		if (this.publisher != null) {
			stringBuffer.append(this.publisher);
			stringBuffer.append(" : "); 
		}
		
		// PublishingLocation._
		if (this.publishingPlace != null) {
			stringBuffer.append(this.publishingPlace);
			stringBuffer.append(". ");
		}
			
		return stringBuffer.toString();
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getPublisherURL() {
		return publisherURL;
	}

	public void setPublisherURL(String publisherURL) {
		this.publisherURL = publisherURL;
	}

	public String getPublishingPlace() {
		return publishingPlace;
	}

	public void setPublishingPlace(String publishingPlace) {
		this.publishingPlace = publishingPlace;
	}
	
}
