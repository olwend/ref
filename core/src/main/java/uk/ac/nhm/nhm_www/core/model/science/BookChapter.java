package uk.ac.nhm.nhm_www.core.model.science;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

public class BookChapter extends Publication{
	private List<String> editors;
	private String bookTitle;

	/* Pagination */
	private int paginationBeginPage;
	private int paginationEndPage;

	private String publisher;
	private String place;
	
	public BookChapter(final String title, final List<String> authorsList, boolean favorite, final int publicationYear,
			final String href, final String reportingDate, final List<String> bookEditorsSet, final String bookTitle, final int paginationBeginPage,
			final int paginationEndPage, final String publisher, final String place) {
		super(title, authorsList, favorite, publicationYear, href, reportingDate);
		
		this.editors = bookEditorsSet;
		this.bookTitle = bookTitle;
		this.paginationBeginPage = paginationBeginPage;
		this.paginationEndPage = paginationEndPage;
		this.publisher = publisher;
		this.place = place;
	}

	public List<String> getEditors() {
		return editors;
	}

	public String getBookTitle() {
		return bookTitle;
	}

	public int getPaginationBeginPage() {
		return paginationBeginPage;
	}

	public int getPaginationEndPage() {
		return paginationEndPage;
	}

	public String getPublisher() {
		return publisher;
	}

	public String getPlace() {
		return place;
	}

	@Override
	public String getHTMLContent(final String currentAuthor, final boolean isFavourite) {
		// Butler, R. J. and Barrett, P. M. (2013) Global Cambrian trilobite palaeobiogeography assessed using parsimony analysis of endemicity. In: D.A.T Harper and T Servais (eds) Early Palaeozoic Biogeography and Palaeogeography, pp. 273-296. Geological Society, London.
		// Authors (publication year) Title. Editors, Book Title, startPage - endPage. Publisher. Place.
		final List<String> authors = this.getAuthors();
		String authorsString;
		if (authors.size() > 5 && isFavourite) {
			authorsString = StringUtils.join(authors.toArray(new String[authors.size()]), ", ", 0, 5) + ", et al";
		} else {
			authorsString = StringUtils.join(authors.toArray(new String[authors.size()]), ", ");
		}
		authorsString = authorsString.replaceAll(currentAuthor, "<b>" + currentAuthor + "</b>");
		
		final StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(authorsString);
		
		stringBuffer.append(" (");
		stringBuffer.append(this.getPublicationYear());
		stringBuffer.append(") ");
		
		stringBuffer.append(this.getTitle());
		stringBuffer.append(". ");
		
		final List<String> editors = this.getEditors();
		if (editors != null) {
			String editorsString = StringUtils.join(editors.toArray(new String[editors.size()]), ", ");
			editorsString = editorsString.replaceAll(currentAuthor, "<b>" + currentAuthor + "</b>");
			
			stringBuffer.append(", ");
			stringBuffer.append(editorsString);
			stringBuffer.append(", ");
		}
		
		stringBuffer.append("<i>");
		stringBuffer.append(this.bookTitle);
		stringBuffer.append("</i>.");
		
		if (this.paginationBeginPage > 0 && this.paginationEndPage > 0) {
			stringBuffer.append(", ");
			
			stringBuffer.append("pp. ");
			stringBuffer.append(this.paginationBeginPage);
			stringBuffer.append(" - ");
			stringBuffer.append(this.paginationEndPage);
		}
		stringBuffer.append(". ");
		
		if (this.publisher != null) {
			stringBuffer.append(this.publisher);
			stringBuffer.append(". ");
		}
		
		if (this.place != null) {
			stringBuffer.append(this.place);
			stringBuffer.append(".");
		}
		
		return stringBuffer.toString();
	}
	
}
