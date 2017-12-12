package uk.ac.nhm.core.impl.servlets;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.commons.json.JSONObject;

import uk.ac.nhm.core.services.DiscoverPublicationsSearchService;

/**
 * Servlet used to get the previous and next publication links.
 */
@Component(label = "Natural History Museum Discover Navigation Servlet", description = "Natural History Museum Discover Navigation Servlet", metatype = true, immediate = true)
@Service(value = Servlet.class)
@Properties({
    	@Property(name = "sling.servlet.paths", value = {"/bin/discover/navigation"}, propertyPrivate = true),
    	@Property(name = "sling.servlet.extensions", value = {"json"}),
        @Property(name = "sling.servlet.methods", value = {"POST"}, propertyPrivate = true),
        @Property(name = "service.description", value = "Return Previous and Next links")
})
public class DiscoverPublicationNavigationServlet extends SlingAllMethodsServlet {

    private static final long serialVersionUID = 2538426139166749515L;

    private static final String PUBLICATION_PARAM = "publication";
    
    @Reference
    private DiscoverPublicationsSearchService searchService;
        
    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) 
    		throws ServletException,IOException {
	
    	final String publication = request.getParameter(PUBLICATION_PARAM);
	
		JSONObject jsonResponse = searchService.getNextPrevious(publication);
		
		response.getWriter().write(jsonResponse.toString());				
    }

}
