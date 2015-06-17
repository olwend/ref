package uk.ac.nhm.nhm_www.core.model.science;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.nhm.nhm_www.core.componentHelpers.ScientistProfileHelper;

public class BookChapter extends Publication{
	private static final Logger LOG = LoggerFactory.getLogger(BookChapter.class);
	
	private List<String> editors;
	private String bookTitle;

	/* Pagination */
	private int paginationBeginPage;
	private int paginationEndPage;

	private String publisher;
	private String place;

	private int page;
	
	public BookChapter(final String title, final List<String> authorsList, boolean favorite, final int publicationYear,
			final String href, final String reportingDate, final List<String> bookEditorsSet, final String bookTitle, final int paginationBeginPage,
			final int paginationEndPage, int page, final String publisher, final String place) {
		super(title, authorsList, favorite, publicationYear, href, reportingDate);
		
		this.page = page;
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
			authorsString = StringUtils.join(processedAuthors.toArray(new String[processedAuthors.size()]), ", ", 0, 5) + ", et al. ";
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
		stringBuffer.append(" >>>>>>>> This is a BookChapter <<<<<<<< ");
		
		// Author N.M., Author N.M.
		stringBuffer.append(authorsString);
		
		// (Year)
		stringBuffer.append(" (");
		stringBuffer.append(this.getPublicationYear());
		stringBuffer.append(") ");
		
		// ChapterTitle,
		stringBuffer.append(this.getTitle());
		stringBuffer.append(", ");
		
		// In: <i>BookTitle</i>,
		stringBuffer.append("In: ");
		stringBuffer.append("<i>");
		stringBuffer.append(this.bookTitle);
		stringBuffer.append("</i>, ");
		
		// Editor N.M., Editor N.M. (Eds).
		final List<String> editors = this.getEditors();
		if (editors != null && editors.size() > 0) {
			String editorsString = StringUtils.join(editors.toArray(new String[editors.size()]), ", ");
			editorsString = editorsString.replaceAll(currentAuthor, "<b>" + currentAuthor + "</b>");
			
			if (editorsString.contains(currentAuthor)) {
				editorsString = editorsString.replaceAll(currentAuthor, "<b>" + currentAuthor + "</b>");
			} else if (authorsString.contains(firstInitial)) {
				editorsString = editorsString.replaceAll(firstInitial, "<b>" + currentAuthor + "</b>");
			}
			
			stringBuffer.append(editorsString);
			stringBuffer.append(" (Eds). ");
		}
		
		// Publisher
		if (this.publisher != null) {
			stringBuffer.append(this.publisher);
			if (this.place != null) { stringBuffer.append(" : "); }
		}
		
		// :PublishingLocation
		if (this.place != null) {
			stringBuffer.append(this.place);
			if ( (this.paginationBeginPage > 0 && this.paginationEndPage > 0) || (this.page > 0 ) ) {
				stringBuffer.append(", "); 
			} else {
				stringBuffer.append(". ");
			}
		}
		
		// : PagesBegin-PagesEnd.
		if (this.paginationBeginPage > 0 && this.paginationEndPage > 0) {
			stringBuffer.append(this.paginationBeginPage);
			stringBuffer.append(" - ");
			stringBuffer.append(this.paginationEndPage);
			stringBuffer.append(". ");
		} else {
			if (this.page > 0) {
				stringBuffer.append(this.page);
				stringBuffer.append(". ");
			}
		}
		
//		LOG.error("#### The final result for the publication is: " + stringBuffer + "####"); 
		
		return stringBuffer.toString();
	}
	
}
