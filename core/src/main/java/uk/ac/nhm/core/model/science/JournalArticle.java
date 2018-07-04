package uk.ac.nhm.core.model.science;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

public class JournalArticle extends Publication{
	private String journalName;
	private String volume;
	private String issue;
	private String beginPage;
	private String endPage;
	private String doiText;
	private String doiLink;
	private String page;
	
	public JournalArticle(final String title, final List<String> authorsList, boolean favorite, final String publicationYear,
			final String href, final String reportingDate, final String journalName, final String volume, final String issue,
			final String paginationBeginPage, final String paginationEndPage, String page, final String doiText,
			final String doiLink) {
		super(title, authorsList, favorite, publicationYear, href, reportingDate);
		
		this.page = page;
		this.journalName = journalName;
		this.volume = volume;
		this.issue = issue;
		this.beginPage = paginationBeginPage;
		this.endPage = paginationEndPage;
		this.doiText = doiText;
		this.doiLink = doiLink;
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
		// We store the author name with a single initial	e.g: Ouvrard D
		String firstInitial = normalizeName(author, true);
		
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
		



		//Remove name delimiters placed there by the normalizer
		authorsString = authorsString.replaceAll("#", "");
		
//		LOG.error("After being replaced: " + authorsString);
		
		final StringBuffer stringBuffer = new StringBuffer();
		//stringBuffer.append("####This is a Journal Article Publication####");
		
		// Author NM, Author NM
		stringBuffer.append(authorsString);
		
		// (Year)
		stringBuffer.append(" (");
		stringBuffer.append(this.getPublicationYear());
		stringBuffer.append(") ");
		
		// ArticleTitle._
		stringBuffer.append(this.getTitle());
		stringBuffer.append(". ");
		
		// <i>JournalName</i>
		stringBuffer.append("<i>");
		stringBuffer.append(this.journalName);
		stringBuffer.append("</i>");
		stringBuffer.append(", ");
		
		// <b>Volume</b>
		if (this.volume != null) {
			stringBuffer.append("<b>");
			stringBuffer.append(this.volume);
			stringBuffer.append("</b> ");
			
			// (Issue) :_
			if (this.issue != null) {
				stringBuffer.append("(");
				stringBuffer.append(this.issue);
				stringBuffer.append(")");
			}
			stringBuffer.append(" : ");
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

	public String getJournalName() {
		return journalName;
	}

	public void setJournalName(String journalName) {
		this.journalName = journalName;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
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

	public String getDoiText() {
		return doiText;
	}

	public void setDoiText(String doiText) {
		this.doiText = doiText;
	}

	public String getDoiLink() {
		return doiLink;
	}

	public void setDoiLink(String doiLink) {
		this.doiLink = doiLink;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}
	
}
