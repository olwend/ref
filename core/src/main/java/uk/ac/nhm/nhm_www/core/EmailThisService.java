package uk.ac.nhm.nhm_www.core;

import org.apache.commons.mail.EmailException;

public interface EmailThisService {
	public void sendEmail(String emailFrom, String emailTo) throws EmailException;
	public void sendEmail();
	
}
