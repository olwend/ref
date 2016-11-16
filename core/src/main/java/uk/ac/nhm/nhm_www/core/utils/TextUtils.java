package uk.ac.nhm.nhm_www.core.utils;

import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;

public class TextUtils {

    /**
     * Returns a boolean value and is used to check if a supplied string is valid JSON data
     */
	public boolean isJSONValid(String jsonText) {
		if(jsonText == null) return false;

	    try {
	        new JSONObject(jsonText);
	    } catch (JSONException ex) {
	        try {
	            new JSONArray(jsonText);
	        } catch (JSONException ex1) {
	            return false;
	        }
	    }
	    return true;
	}
	
	/**
	 * Utils class to transform authr/pblsh server name to disp
	 * Expects hostname to be in form of 'aem-authr1-stg'
	 * 
	 * @return hostname
	 * @throws UnknownHostException
	 */
	public String transformHostName(String hostname) throws UnknownHostException {
		//Split current hostname into tokens
		String[] tokens = hostname.split("-");
		
		String aemName = tokens[0];
		
		Pattern pattern = Pattern.compile("[0-9]");
		Matcher matcher = pattern.matcher(tokens[1]);
		String aemNum = null;
		if(matcher.find()) {
			aemNum = matcher.group(0);
        }
		
		String aemEnv = tokens[2];
		
		//Construct new hostname
		String newHost = aemName + "-disp" + aemNum + "-" + aemEnv;
		
		return newHost;
	}
}