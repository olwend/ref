package uk.ac.nhm.nhm_www.core.componentHelpers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.sling.api.resource.ValueMap;

public class TorIframeHelper {

	protected ValueMap properties;
	protected HttpServletRequest request;
	
	public String iframeCode;
	
	public TorIframeHelper(ValueMap properties, HttpServletRequest request) {
		this.properties = properties;
		this.request = request;
		
		String eventConfigParam = request.getParameter("eventconfig");
		
		if(eventConfigParam != null && !eventConfigParam.equals("")) {
			
			//The value of the eventconfig parameter must be one or more digits
			Pattern pattern = Pattern.compile("^\\d+");
	        Matcher matcher = pattern.matcher(eventConfigParam);
	        
	        if(matcher.find()) {
	        	//Deep linking page
	        	this.iframeCode = 
	        			"<iframe onload=\"javascript:window.parent.parent.scrollTo(0,0)\" frameborder=\"0\" height=\"1500px\" src=\"https://www.maximweb.net/NHMResponsive_UI/day?eventconfig=" + matcher.group(0) + "\" width=\"100%\"> </iframe>";
	        } else {
	        	//Default page
	        	this.iframeCode = 
	        			"<iframe onload=\"javascript:window.parent.parent.scrollTo(0,0)\" frameborder=\"0\" height=\"1500px\" src=\"https://www.maximweb.net/NHMResponsive_UI/EventsList\" width=\"100%\"> </iframe>";
	        }
		} else {
			//Default page
        	this.iframeCode = 
        			"<iframe onload=\"javascript:window.parent.parent.scrollTo(0,0)\" frameborder=\"0\" height=\"1500px\" src=\"https://www.maximweb.net/NHMResponsive_UI/EventsList\" width=\"100%\"> </iframe>";
        }
	}

	public String getIframeCode() {
		return iframeCode;
	}

	public void setIframeCode(String iframeCode) {
		this.iframeCode = iframeCode;
	}
}