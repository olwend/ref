package uk.ac.nhm.nhm_www.core.utils;

/**
 * Helper class to centralise the repository user credentials
 * @author juanm
 *
 */
public class EventCalendarLoginUtils {
	private static final String USER_ID = "calendar-user";
	private static final String USER_PASSWORD = "crxde1"; 
	
	public String getUserID() {
		return USER_ID;
	}
	
	public String getUserPassword() {
		return USER_PASSWORD;
	}
}
