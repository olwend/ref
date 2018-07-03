package uk.ac.nhm.core.model.science;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.nhm.core.componentHelpers.ScientistProfileHelper;

public class BookChapter extends Publication{
	private static final Logger LOG = LoggerFactory.getLogger(BookChapter.class);
	
	private List<String> editors;
	private String bookTitle;
	private String beginPage;
	private String endPage;
	private String publisher;
	private String place;
	private String page;
	
	public BookChapter(final String title, final List<String> authorsList, boolean favorite, final String publicationYear,
			final String href, final String reportingDate, final List<String> bookEditorsSet, final String bookTitle, final String beginPage,
			final String endPage, String page, final String publisher, final String place) {
		super(title, authorsList, favorite, publicationYear, href, reportingDate);
		
		this.page = page;
		this.editors = bookEditorsSet;
		this.bookTitle = bookTitle;
		this.beginPage = beginPage;
		this.endPage = endPage;
		this.publisher = publisher;
		this.place = place;
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
			authorsString = StringUtils.join(processedAuthors.toArray(new String[processedAuthors.size()]), ", ", 0, 5) + ", et al. ";
		} else {
			authorsString = StringUtils.join(processedAuthors.toArray(new String[processedAuthors.size()]), ", ");
		}
		
//		LOG.error("This is the list of authors parsed: " + authorsString);
//		LOG.error("Current Author: " + currentAuthor);
//		LOG.error("First Initial Author: " + firstInitial);
		
		//Use regular expression to find full author name including all intials
		//Replace with same string in HTML bold tags
		/*if (authorsString.contains(surname)) {
			authorsString = authorsString.replaceAll(surname + "[A-Z]*", "<b>$0</b>");
		}*/
		
		//Remove name delimiters placed there by the normalizer
		authorsString = authorsString.replaceAll("#", "");
		
//		LOG.error("After being replaced: " + authorsString);
		
		final StringBuffer stringBuffer = new StringBuffer();
//		stringBuffer.append("####This is a BookChapter Publication####");
		
		// Author NM, Author NM
		stringBuffer.append(authorsString);
		
		// _(Year)_
		stringBuffer.append(" (");
		stringBuffer.append(this.getPublicationYear());
		stringBuffer.append(") ");
		
		// ChapterTitle,_
		stringBuffer.append(this.getTitle());
		stringBuffer.append(", ");
		
		// In: <i>BookTitle</i>,_
		stringBuffer.append("In: ");
		stringBuffer.append("<i>");
		stringBuffer.append(this.bookTitle);
		stringBuffer.append("</i>, ");
		
		// Editor NM, Editor NM (Eds)._
		final List<String> editors = this.editors;
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
		
		// Publisher :_
		stringBuffer.append(this.publisher);
		stringBuffer.append(" : ");			
		
		// PublishPlace._
		if (this.place != null) {
			stringBuffer.append(this.place);
			stringBuffer.append(". ");
		}
		
		// PagesBegin-PagesEnd.
		if (this.beginPage != null && this.endPage != null) {
			stringBuffer.append(this.beginPage);
			stringBuffer.append(" - ");
			stringBuffer.append(this.endPage);
			stringBuffer.append(". ");
		} else {
			if (this.page != null) {
				stringBuffer.append(this.page);
				stringBuffer.append(". ");
			}
		}
		
//		LOG.error("#### The final result for the publication is: " + stringBuffer + "####"); 
		
		return stringBuffer.toString();
	}

	public List<String> getEditors() {
		return editors;
	}

	public void setEditors(List<String> editors) {
		this.editors = editors;
	}

	public String getBookTitle() {
		return bookTitle;
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	public String getBeginPage() {
		return beginPage;
	}

	public void setBeginPage(String beginPage) {
		this.beginPage = beginPage;
	}

	public String getEndPage() {
		return endPage;
	}

	public void setEndPage(String endPage) {
		this.endPage = endPage;
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

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}
	
}
