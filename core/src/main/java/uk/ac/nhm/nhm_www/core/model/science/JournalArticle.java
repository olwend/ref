package uk.ac.nhm.nhm_www.core.model.science;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

public class JournalArticle extends Publication{
	private String journalName;
	private int volume;
	private int issue;
	/* Pagination */
	private int paginationBeginPage;
	private int paginationEndPage;
	
	private String doiText;
	private String doiLink;
	
	public JournalArticle(final String title, final List<String> authorsList, boolean favorite, final int publicationYear,
			final String href, final String reportingDate, final String journalName, final int volume, final int issue,
			final int paginationBeginPage, final int paginationEndPage, final String doiText,
			final String doiLink) {
		super(title, authorsList, favorite, publicationYear, href, reportingDate);
		this.journalName = journalName;
		this.volume = volume;
		this.issue = issue;
		this.paginationBeginPage = paginationBeginPage;
		this.paginationEndPage = paginationEndPage;
		this.doiText = doiText;
		this.doiLink = doiLink;
	}

	public String getJournalName() {
		return journalName;
	}

	public int getVolume() {
		return volume;
	}

	public int getIssue() {
		return issue;
	}

	public int getPaginationBeginPage() {
		return paginationBeginPage;
	}

	public int getPaginationEndPage() {
		return paginationEndPage;
	}

	public String getDoiText() {
		return doiText;
	}

	public String getDoiLink() {
		return doiLink;
	}

	@Override
	public String getHTMLContent(final String currentAuthor, final boolean isFavourite) {
		//Lee, M. R., Hodson, M. E., Brown, D. J., MacKenzie, M. and Smith, C. L. (2008) The composition and crystallinity of the near-surface regions of weathered alkali feldspars. Geochimica et Cosmochimica Acta, 72 (issue, if any): 4962-4975.
		// Authors (publication year) Title. Yournal name, Volume (issue): beginPage - endPage. <a href="href"> doi text </a>

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
		
		stringBuffer.append("<i>");
		stringBuffer.append(this.getJournalName());
		stringBuffer.append("</i>");
		stringBuffer.append(", ");
		
		if (this.volume > 0) {
			stringBuffer.append("<b>");
			stringBuffer.append(this.volume);
			stringBuffer.append("</b>");
			stringBuffer.append(" ");
		}
		
		if (this.issue >= 0) {
			stringBuffer.append("(");
			stringBuffer.append(this.issue);
			stringBuffer.append(")");
		}
		
		if (this.paginationBeginPage > 0 && this.paginationEndPage > 0) {
			stringBuffer.append(": ");
			stringBuffer.append(this.paginationBeginPage);
			stringBuffer.append(" - ");
			stringBuffer.append(this.paginationEndPage);
			stringBuffer.append(". ");
		}
		
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
