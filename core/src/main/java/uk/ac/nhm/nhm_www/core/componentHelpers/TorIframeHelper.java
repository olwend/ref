package uk.ac.nhm.nhm_www.core.componentHelpers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.sling.api.resource.ValueMap;

public class TorIframeHelper {

	protected ValueMap properties;
	protected HttpServletRequest request;
	
	private final String ROOT_URL = "https://www.maximweb.net/NHMResponsive_UI";
	
	public TorIframeHelper(ValueMap properties, HttpServletRequest request) {
		this.properties = properties;
		this.request = request;
	}

	public String getIframeCode() {
		String iframeCode;
		String eventConfigParam = request.getParameter("eventconfig");
		
		if(eventConfigParam != null && !eventConfigParam.equals("")) {
			
			//The value of the eventconfig parameter must be one or more digits
			Pattern pattern = Pattern.compile("^\\d+");
	        Matcher matcher = pattern.matcher(eventConfigParam);
	        
	        if(matcher.find()) {
	        	//Deep linking page
	        	iframeCode = 
	        			"<iframe class=\"js--tor-iframe\" onload=\"javascript:window.parent.parent.scrollTo(0,0)\" frameborder=\"0\" src=\"" + ROOT_URL + "/day?eventconfig=" + matcher.group(0) + "\" width=\"100%\"> </iframe>";
	        } else {
	        	//Default page
	        	iframeCode = 
	        			"<iframe class=\"js--tor-iframe\" onload=\"javascript:window.parent.parent.scrollTo(0,0)\" frameborder=\"0\" src=\"" + ROOT_URL + "/EventsList\" width=\"100%\"> </iframe>";
	        }
		} else {
			//Default page
        	iframeCode = 
        			"<iframe class=\"js--tor-iframe\" onload=\"javascript:window.parent.parent.scrollTo(0,0)\" frameborder=\"0\" src=\"" + ROOT_URL + "/EventsList\" width=\"100%\"> </iframe>";
        }
		return iframeCode;
	}
}