package uk.ac.nhm.core.model.science;

public class WebSite implements Comparable<WebSite> {
	public static final String BLOG_TYPE 		   = "blog";
	public static final String TWITTER_TYPE 	   = "twitter";
	public static final String COMPANY_TYPE		   = "company";
	public static final String PERSONAL_TYPE 	   = "personal";
	public static final String LINKEDIN_TYPE 	   = "linkedin";
	public static final String GOOGLE_SCHOLAR_TYPE = "googlescholar";
	public static final String RESEARCH_GATE_TYPE  = "researchgate";
	public static final String FIG_SHARE_TYPE 	   = "figshare";
	public static final String MENDELEY_TYPE 	   = "mendeley";
	public static final String OTHER_TYPE 		   = "other";
	public static final String PORTFOLIO_TYPE 	   = "portfolio";
    
	private String label, link, type;

	public WebSite(String label, String link, String type) {
		super();
		this.label = label;
		this.type = type;
		this.link = link;
	}

	public String getLabel() {
		if (label == null || label.isEmpty()) {
			if (link.contains("//")) {
				final int indexSlash = link.indexOf("/", link.indexOf("//") + 2);
				
				if (indexSlash < 0) {
					return link;
				}
				
				return link.substring(0, indexSlash);
			}
			
			if (link.contains("/")) {
				return link.substring(0, link.indexOf("/"));
			}
			
			return link;
		}
		return label == null || label.isEmpty() ? link : label; 
	}

	public String getLink() {
		return link;
	}
	
	public String getType() {
		return type;
	}
	
	public boolean isValid() {
		return this.link != null;
	}
	
	public boolean isPersonalInformationWebSite() {
		if (this.type == null) {
			return false;
		}
		
		switch (this.type) {
		case BLOG_TYPE:
		case PERSONAL_TYPE:
		case LINKEDIN_TYPE:
		case GOOGLE_SCHOLAR_TYPE:
		case RESEARCH_GATE_TYPE:
		case FIG_SHARE_TYPE:
		case MENDELEY_TYPE:
			return true;
		}
		
		return false;
	}

	public int compareTo(WebSite o) {
		if (this.label == null || o.label == null) {
			return this.link.compareTo(o.link);
		}
		return this.label.compareTo(o.label);
	}
	
}
