package uk.ac.nhm.nhm_www.core.services;

import java.text.ParseException;

import javax.jcr.LoginException;
import javax.jcr.RepositoryException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.json.JSONException;

/**
 * Service to manage the creation of the XML Feed
 */
public interface CreateXMLFeed {

	public void createXML() throws LoginException, RepositoryException, JSONException, ParseException, ParserConfigurationException, TransformerException;
	
}
