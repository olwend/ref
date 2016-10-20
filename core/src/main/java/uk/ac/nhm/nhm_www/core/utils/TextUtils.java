package uk.ac.nhm.nhm_www.core.utils;

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
}