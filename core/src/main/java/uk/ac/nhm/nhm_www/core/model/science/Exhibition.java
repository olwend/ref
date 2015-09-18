package uk.ac.nhm.nhm_www.core.model.science;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class Exhibition extends Publication{
	
	private String location;
	private String publisherURL;
	private int startDay;
	private int startMonth;
	private int startYear;
	private int endDay;
	private int endMonth;
	private int endYear;

	public Exhibition(final String title, final  List<String> authorsList, final  boolean favorite, final  int publicationYear,
			final  String href,	final String reportingDate, String exhibitionLocation, String exhibitionPublisherURL, 
			int startExhibitionDay, int startExhibitionMonth, int startExhibitionYear, int endExhibitionDay, int endExhibitionMonth, 
			int endExhibitionYear){
		super(title, authorsList, favorite, publicationYear, href, reportingDate);
		this.location = exhibitionLocation;
		this.publisherURL = exhibitionPublisherURL;
		this.startDay = startExhibitionDay;
		this.startMonth = startExhibitionMonth;
		this.startYear = startExhibitionYear;
		this.endDay = endExhibitionDay;
		this.endMonth = endExhibitionMonth;
		this.endYear = endExhibitionYear;
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
		
		if (authorsString.contains(currentAuthor)) {
			authorsString = authorsString.replaceAll(currentAuthor, "<b>" + currentAuthor + "</b>");
		} else if (authorsString.contains(firstInitial)) {
			authorsString = authorsString.replaceAll(firstInitial, "<b>" + currentAuthor + "</b>");
		}
		//Remove name delimiters placed there by the normalizer
		authorsString = authorsString.replaceAll("#", "");
		
		final StringBuffer stringBuffer = new StringBuffer();
//		stringBuffer.append("####This is an Exhibition####");
		
		stringBuffer.append(authorsString);
		stringBuffer.append(". ");

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
		
		// Venue._
		stringBuffer.append(this.location);
		stringBuffer.append(". ");
		
		// Start Date - Finish Date, Year._
		if (this.startDay > 0 && this.startMonth > 0 && this.startYear > 0 && this.endDay > 0 && this.endMonth > 0 && this.endYear > 0 ){
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
			
		return stringBuffer.toString();
	}
	
}
