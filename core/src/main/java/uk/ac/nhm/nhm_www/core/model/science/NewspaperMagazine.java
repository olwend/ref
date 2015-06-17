package uk.ac.nhm.nhm_www.core.model.science;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

public class NewspaperMagazine extends Publication{
	
	private int paginationBeginPage;
	private int paginationEndPage;
	private int page;
	private String publisherURL;
	private String doiLink;
	private String doiText;
	private int volume;
	private int issue;

	public NewspaperMagazine(final String title, final  List<String> authorsList, final  boolean favorite, final  int publicationYear,
			final  String href,	final String reportingDate, String newsmagPublisherURL, int newsmagBeginPage, int newsmagEndPage, 
			int newsmagPage, String newsmagDoiLink, String newsmagDoiText, int newsmagVolume, int newsmagIssue) {
		super(title, authorsList, favorite, publicationYear, href, reportingDate);
		this.paginationBeginPage = newsmagBeginPage;
		this.paginationEndPage = newsmagEndPage;
		this.page = newsmagPage;
		this.publisherURL = newsmagPublisherURL;
		this.doiLink = newsmagDoiLink;
		this.doiText = newsmagDoiText;
		this.volume = newsmagVolume;
		this.issue = newsmagIssue;
	}

	public int getPaginationBeginPage() {
		return paginationBeginPage;
	}

	public void setPaginationBeginPage(int paginationBeginPage) {
		this.paginationBeginPage = paginationBeginPage;
	}

	public int getPaginationEndPage() {
		return paginationEndPage;
	}

	public void setPaginationEndPage(int paginationEndPage) {
		this.paginationEndPage = paginationEndPage;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getPublisherURL() {
		return publisherURL;
	}

	public void setPublisherURL(String publisherURL) {
		this.publisherURL = publisherURL;
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

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public int getIssue() {
		return issue;
	}

	public void setIssue(int issue) {
		this.issue = issue;
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
		stringBuffer.append("####This is a + "
								+ "NewspaperMagazine"
							+ " #####");
		
		// Author NM, Author NM
		stringBuffer.append(authorsString);
		stringBuffer.append(". ");

		// (Year)
		stringBuffer.append(" (");
		stringBuffer.append(this.getPublicationYear());
		stringBuffer.append(") ");
		
		// Link Opening to publisher-url
		if (this.getPublisherURL() != null) {
			stringBuffer.append("<a href=\"");
			stringBuffer.append(this.getPublisherURL());
			stringBuffer.append("\">");
		}
		
		// Title
		stringBuffer.append("<i>");
		stringBuffer.append(this.getTitle());
		stringBuffer.append("</i>");

		// Link Closing to publisher-url
		if (this.getPublisherURL() != null) {
			stringBuffer.append("</a>");
		}
		
		stringBuffer.append(". ");
		
		// <b>Volume</b>
		if (this.volume > 0) {
			stringBuffer.append("<b>");
			stringBuffer.append(this.volume);
			stringBuffer.append("</b>");
			if ( this.issue < 0 ) { 
				if ( (this.paginationBeginPage > 0 && this.paginationEndPage > 0) || this.page > 0) {
					stringBuffer.append(": "); 
				} else {
					stringBuffer.append(". ");
				}
			}
		}
		
		// (Issue)
		if (this.issue >= 0) {
			stringBuffer.append("(");
			stringBuffer.append(this.issue);
			stringBuffer.append(") ");
			if ( (this.paginationBeginPage > 0 && this.paginationEndPage > 0) || this.page > 0) { 
				stringBuffer.append(": "); 
			} else {
				
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
	
}
