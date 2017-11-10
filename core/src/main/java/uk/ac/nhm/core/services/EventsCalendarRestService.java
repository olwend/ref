package uk.ac.nhm.core.services;

import javax.jcr.RepositoryException;
import javax.ws.rs.core.Response;

import org.apache.sling.commons.json.JSONException;

public interface EventsCalendarRestService {

	public Response getAll() throws RepositoryException, JSONException;
	
	public Response getMonth(int year, String month) throws RepositoryException, JSONException;
	
	public Response getWeek() throws RepositoryException, JSONException;
	
	public Response getWeekByDate(int year, int month, int day) throws RepositoryException, JSONException;
	
	public Response getDay() throws RepositoryException, JSONException;
	
	public Response getDay(int year, int month, int day) throws RepositoryException, JSONException;
	
}
