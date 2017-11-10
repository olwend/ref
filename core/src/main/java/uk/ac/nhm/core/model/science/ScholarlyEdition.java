package uk.ac.nhm.core.model.science;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

public class ScholarlyEdition extends Publication{
	
	private String publisherURL;
	private String beginPage;
	private String endPage;
	private String page;
	private String doiLink;
	private String doiText;
	private String publisher;

	public ScholarlyEdition(final String title, final List<String> authorsList, final boolean favorite, final String publicationYear,
			final String href, final String reportingDate, String scholarlyPublisherURL, String scholarlyBeginPage, String scholarlyEndPage, 
			String scholarlyPage, String scholarlydoiText, String scholarlydoiLink, String scholarlyPublisher){
		super(title, authorsList, favorite, publicationYear, href, reportingDate);
		this.publisherURL = scholarlyPublisherURL;
		this.beginPage = scholarlyBeginPage;
		this.endPage = scholarlyEndPage;
		this.page = scholarlyPage;
		this.doiLink = scholarlydoiLink;
		this.doiText = scholarlydoiText;
		this.publisher = scholarlyPublisher;
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
		

		//Use regular expression to find full author name including all intials
		//Replace with same string in HTML bold tags
		if (authorsString.contains(surname)) {
			authorsString = authorsString.replaceAll(surname + "[A-Z]*", "<b>$0</b>");
		}
		
		//Remove name delimiters placed there by the normalizer
		authorsString = authorsString.replaceAll("#", "");
		
		final StringBuffer stringBuffer = new StringBuffer();
//		stringBuffer.append("####This is a Scholarly Edition Publication####");
		
		// Author NM, Author NM
		stringBuffer.append(authorsString);
		
		// (Year) || (Year, Month) || (Year, Day Month)
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
		
		// Publisher :_
		stringBuffer.append(this.publisher);
		stringBuffer.append(" : ");		
		
		// PagesBegin-PagesEnd._ || PageCount._
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
		
		// DOI hyperlink
		if (this.doiLink != null && this.doiText != null) {
			stringBuffer.append("<a href=\"");
			stringBuffer.append(this.doiLink);
			stringBuffer.append("\">");
			stringBuffer.append("doi: ");
			stringBuffer.append(this.doiText);
			stringBuffer.append("</a>");
		}
		
		return stringBuffer.toString();
	}

	public String getPublisherURL() {
		return publisherURL;
	}

	public void setPublisherURL(String publisherURL) {
		this.publisherURL = publisherURL;
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

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getDoiLink() {
		return doiLink;
	}

	public void setDoiLink(String doiLink) {
		this.doiLink = doiLink;
	}

	public String getDoiText() {
		return doiText;
	}

	public void setDoiText(String doiText) {
		this.doiText = doiText;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	
}
