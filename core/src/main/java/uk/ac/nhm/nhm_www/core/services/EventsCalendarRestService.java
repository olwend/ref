package uk.ac.nhm.nhm_www.core.services;

import javax.jcr.RepositoryException;
import javax.ws.rs.core.Response;

import org.apache.sling.commons.json.JSONException;

public interface EventsCalendarRestService {

	public Response getAll() throws RepositoryException, JSONException;
	
	public Response getWeek() throws RepositoryException, JSONException;
	
	public Response getDay() throws RepositoryException, JSONException;
	
}
