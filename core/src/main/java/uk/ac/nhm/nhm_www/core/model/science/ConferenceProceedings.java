package uk.ac.nhm.nhm_www.core.model.science;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class ConferenceProceedings extends Publication{
	
	private String conferenceName;
	private String place;

	public ConferenceProceedings(final String title, final  List<String> authorsList, final  boolean favorite, final  int publicationYear,
			final  String href,	final String reportingDate, final  String conferenceName, final  String placeOfPublication) {
		super(title, authorsList, favorite, publicationYear, href, reportingDate);
		this.conferenceName = conferenceName;
		this.place = placeOfPublication;
	}

	public String getHTMLContent(final String currentAuthor, final boolean isFavourite) {
		// Barrett, P. M. (2012) The Complete Dinosaur (Second Edition). Indiana University Press: Bloomington and Indianapolis.
		// Authors Editors (publication year) Title. Publisher. Place. Page
		final List<String> authors = this.getAuthors();
		String authorsString;
		if (authors.size() > 5 && isFavourite) {
			authorsString = StringUtils.join(authors.toArray(new String[authors.size()]), ", ", 0, 5) + ", <i>et al</i>";
		} else {
			authorsString = StringUtils.join(authors.toArray(new String[authors.size()]), ", ");
		}
		authorsString = authorsString.replaceAll(currentAuthor, "<b>" + currentAuthor + "</b>");
		
		final StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(authorsString);
		stringBuffer.append(". ");
		
		stringBuffer.append(" (");
		stringBuffer.append(this.getPublicationYear());
		stringBuffer.append(") ");
		
		stringBuffer.append("<i>");
		stringBuffer.append(this.getTitle());
		stringBuffer.append("</i>. ");
		
		if (this.conferenceName != null) {
			stringBuffer.append(this.conferenceName);
			stringBuffer.append(".");
		}
		
		if (this.place != null) {
			stringBuffer.append(this.place);
			stringBuffer.append(".");
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
	
}
