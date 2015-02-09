package uk.ac.nhm.nhm_www.core.utils;

import java.net.MalformedURLException;
import java.net.URL;

public class LinkUtils {
	public static String getFormattedLink(String linkUrl) {
		try {
			URL url = new URL(linkUrl);
		} catch (MalformedURLException e) {
			if (!linkUrl.endsWith(".html") && linkUrl.startsWith("/content")) {
				linkUrl = linkUrl + ".html";
			}

		}
		return linkUrl;
	}
	
	public static boolean validateUrl(String url) {
		
		//Regular Expression stolen from: http://regexlib.com/REDetails.aspx?regexp_id=1854
		//There's probably a better way of validating URLs but this will do for now!
		
		final String reUrlValidator = "^(http(?:s)?\\:\\/\\/[a-zA-Z0-9\\-]+(?:\\.[a-zA-Z0-9\\-]+)*\\.[a-zA-Z]{2,6}(?:\\/?|(?:\\/[\\w\\-]+)*)(?:\\/?|\\/\\w+\\.[a-zA-Z]{2,4}(?:\\?[\\w]+\\=[\\w\\-]+)?)?(?:\\&[\\w]+\\=[\\w\\-]+)*)$";	
		if (url.matches(reUrlValidator))	{return true;} else {return false;}
			

		/*
		 *  Commented out due to problems with library dependencies with org.apache.commons.validator.routines.UrlValidator
		 *  
		  
		String[] schemes = {"http","https"};  
		UrlValidator urlValidator = new UrlValidator(schemes);
		if (urlValidator.isValid(this.hyperLink)) {
		   return true;
		} else {
		   return false;
		}*/
	}
}
