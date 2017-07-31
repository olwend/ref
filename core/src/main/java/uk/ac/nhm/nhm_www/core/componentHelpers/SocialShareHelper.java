package uk.ac.nhm.nhm_www.core.componentHelpers;

import com.adobe.cq.sightly.WCMUse;

public class SocialShareHelper extends WCMUse {
	
	private String emailLink;
	
	@Override
	public void activate() throws Exception {
		
		//Strip '/content/nhmwww/en/home' from page path
		String pagePath = getResourcePage().getPath();
		if(pagePath.contains("/content/nhmwww/en/home")) {
			pagePath = pagePath.replaceAll("/content/nhmwww/en/home", "");
		}
		
		this.emailLink = pagePath;
	}

	public String getEmailLink() {
		return emailLink;
	}

}