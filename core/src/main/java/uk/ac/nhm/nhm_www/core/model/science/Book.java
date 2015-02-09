package uk.ac.nhm.nhm_www.core.model.science;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

public class Book extends Publication{
	private List<String> editors;
	private String publisher;
	private String place;
	private int page;
	
	public Book(final String title, final List<String> authorsList, boolean favorite, final int publicationYear,
			final String href, final String reportingDate, final List<String> editorsSet, final String publisher, final String place,
			final int page) {
		super(title, authorsList, favorite, publicationYear, href, reportingDate);
		
		this.editors = editorsSet;
		this.publisher = publisher;
		this.place = place;
		this.page = page;
	}

	public List<String> getEditors() {
		return editors;
	}

	public String getPublisher() {
		return publisher;
	}

	public String getPlace() {
		return place;
	}

	public int getPage() {
		return page;
	}

	@Override
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
		
		final List<String> editors = this.getEditors();
		String editorsString = StringUtils.join(editors.toArray(new String[editors.size()]), ", ");
		editorsString = editorsString.replaceAll(currentAuthor, "<b>" + currentAuthor + "</b>");
		
		stringBuffer.append(", ");
		stringBuffer.append(editorsString);
		
		stringBuffer.append(" (");
		stringBuffer.append(this.getPublicationYear());
		stringBuffer.append(") ");
		
		stringBuffer.append("<i>");
		stringBuffer.append(this.getTitle());
		stringBuffer.append("</i>. ");
		
		stringBuffer.append(this.publisher);
		stringBuffer.append(". ");
		
		if (this.place != null) {
			stringBuffer.append(this.place);
			stringBuffer.append(".");
		}
		
		if (this.page > 0) {
			stringBuffer.append(" ");
			stringBuffer.append(this.page);
		}
			
		return stringBuffer.toString();
	}
	
}
