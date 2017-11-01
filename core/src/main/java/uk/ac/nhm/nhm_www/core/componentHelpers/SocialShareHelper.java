package uk.ac.nhm.nhm_www.core.componentHelpers;

import com.adobe.cq.sightly.WCMUsePojo;

public class SocialShareHelper extends WCMUsePojo {
	
	private String socialLink;
	
	//@Override
	public void activate() throws Exception {
		
		//Strip '/content/nhmwww/en/home' from page path
		String pagePath = getResourcePage().getPath();
		if(pagePath.contains("/content/nhmwww/en/home")) {
			pagePath = pagePath.replaceAll("/content/nhmwww/en/home", "");
		}
		
		this.socialLink = pagePath;
	}

	public String getSocialLink() {
		return socialLink;
	}

}