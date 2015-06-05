package uk.ac.nhm.nhm_www.core.model.science;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

public class Other extends Publication{
	
	public Other(final String title, final  List<String> authorsList, final  boolean favorite, final  int publicationYear,
			final  String href,	final String reportingDate) {
		super(title, authorsList, favorite, publicationYear, href, reportingDate);
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
		stringBuffer.append("####This is a + "
								+ "Other"
							+ " #####");
		stringBuffer.append(authorsString);
		stringBuffer.append(". ");

		stringBuffer.append(" (");
		stringBuffer.append(this.getPublicationYear());
		stringBuffer.append(") ");
		
		stringBuffer.append("<i>");
		stringBuffer.append(this.getTitle());
		stringBuffer.append("</i>. ");
			
		return stringBuffer.toString();
	}
	
}
