package uk.ac.nhm.nhm_www.core.impl.servlets;

import java.io.IOException;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.PageManagerFactory;

import uk.ac.nhm.nhm_www.core.componentHelpers.NewsletterSignUpHelper;
import uk.ac.nhm.nhm_www.core.services.NewslettersService;

/**
 * Servlet created for the NHM Newsletter Sign Up Form, validating the data and requesting to the NHM Service
 * to get return the final decision. Returning a JSON Response with the result of the action (success true or false)
 * If the Success is <code>true</code> then the servlet returns the Thank You Page where has to go the Page where the Form is.
 * If the Success is <code>false</code> then the servlet returns the Error Message to show in the form.
 */
@Component(metatype = false)
@Service(value = Servlet.class)
@Properties({
        @Property(name = "sling.servlet.resourceTypes", value = {"nhmwww/components/page/defaultpage"}, propertyPrivate = false),
        @Property(name = "sling.servlet.selectors", value = {"newslettersignup"}, propertyPrivate = false),
        @Property(name = "sling.servlet.methods", value = {"POST", "GET"}, propertyPrivate = false),
        @Property(name = "service.description", value = "Validate the information of a Newsletter Sign Up Form and send the request to the right NHM Service.", propertyPrivate = false),
        @Property(name = "nhm.newsletter.service", label = "NHM Newsletter Service", description = "URL to request for the Newsletter Sign Up Service.")
})
public class NewsletterSingUpServlet extends SlingAllMethodsServlet {
	
	private static final long serialVersionUID = 1L;
	
	private static final String NOT_VALIDATED_MESSAGE = "Not Validated";
	//private static final String INTERNAL_ERROR_MESSAGE = "There has been an error and we cannot collect your email at this time.";
	
	protected static final Logger LOG = LoggerFactory.getLogger(NewsletterSingUpServlet.class);
	
	// /publicwifi-signups/form.json
	
	@Reference
	protected PageManagerFactory pageManagerFactory;
	
	@Reference
	protected NewslettersService newslettersService;

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
		this.callExternalServlet(request, response);
	}
	
	@Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
		this.callExternalServlet(request, response);
	}
	
	private void callExternalServlet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
		final Resource resource = request.getResource();
		
		ResourceResolver resourceResolver = request.getResourceResolver();
		
		PageManager pageManager = pageManagerFactory.getPageManager(resourceResolver);
		
		final NewsletterSignUpHelper helper = new NewsletterSignUpHelper(resource, pageManager);
		
		/* Validate form */
		final String name 	  = request.getParameter("name");
		final String firstname 	  = request.getParameter("firstname");
		final String lastname 	  = request.getParameter("lastname");
		final String email 	  = request.getParameter("email");
		final String question = request.getParameter("question");
		/* Optional parameter for CRM marketing campaign (exclude from validation) 
		 * we may want to validate this separately later - depends on CRM workflow */
		final String campaign = request.getParameter("campaign");
		
		/* parameters provided must contain either name OR firstname and lastname */ 
		final boolean isFormValidated = ((name != null && !name.isEmpty()) || (firstname != null && !firstname.isEmpty() && lastname != null && !lastname.isEmpty())) 
				&& email != null && isEmailValid(email) && (question == null || question.isEmpty());
		
		
		final JSONObject jsonResponse = new JSONObject();
		try {
			if (isFormValidated) {
				/* If the Form parameters are valid, request for the NHM Service to do the Sign Up Action. */
				final HttpClient httpClient = new HttpClient();
				
				final PostMethod postMethod = new PostMethod(newslettersService.getNHMNewslettersSignUpService());
				String host = "Remote host: " + request.getRemoteHost() + " Server name:" + request.getServerName() + " Servlet path: " + request.getServletPath();
				LOG.error("REQUEST VALUES: " +host);
				postMethod.addParameter("source", "www.nhm.ac.uk");
				if (name != null) {
					postMethod.addParameter("name"  , name);
				}
				if (firstname != null) {
					postMethod.addParameter("firstname", firstname);
				}
				if (lastname != null) {
					postMethod.addParameter("lastname", lastname);
				}
				postMethod.addParameter("email" , email);
				if (campaign != null) {
					postMethod.addParameter("campaign" , campaign);
				}
		        
				postMethod.addRequestHeader("accept", "application/json");
				
				httpClient.executeMethod(postMethod);
								
				final byte[] responseBody = postMethod.getResponseBody();
				
				final String responseString = new String(responseBody);
				
				final JSONObject externalResponseJSONObject = new JSONObject(responseString);
				
				final boolean valid  = externalResponseJSONObject.getBoolean("valid");
				final String message = externalResponseJSONObject.getString("message");
				
				jsonResponse.append("success", valid);
				
				if (valid) {
					/* If the response is the action was done successfully return the Thank you URL. */
					final String thankyouURL = helper.getThankYouURL();	
			        jsonResponse.append("thankyouURL", thankyouURL);	
				} else {
					/* If the response is the action was not done successfully return the Message. */
					jsonResponse.append("errorMessage", message);
				}
			} else {
				/* If the Form parameters are not valid, return the message error. */
				jsonResponse.append("success"	  , false);
				jsonResponse.append("errorMessage", NOT_VALIDATED_MESSAGE);
			}
//			response.addHeader("Access-Control-Allow-Origin", "http://wcm-pblsh1-stg.nhm.ac.uk/");
//			response.addHeader("Access-Control-Allow-Origin", "http://wcm-pblsh2-stg.nhm.ac.uk/");
			response.getWriter().append(jsonResponse.toString());
		} catch (JSONException e) {
			LOG.error("Error forming JSON Response.", e);
			response.sendError(500);
		}
	}

	/* Check if a Email Address is valid. */
	private boolean isEmailValid(final String emailAddress) {
		try {
			InternetAddress internetAddress = new InternetAddress(emailAddress);
			internetAddress.validate();
			return true;
		} catch (AddressException e) {
			return false;
		}
	}
	
}
