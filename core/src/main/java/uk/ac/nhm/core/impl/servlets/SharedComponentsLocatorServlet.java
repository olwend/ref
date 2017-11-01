package uk.ac.nhm.core.impl.servlets;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.nhm.core.model.SharedComponent;
import uk.ac.nhm.core.services.SharedComponentsService;


@Component(metatype = true, immediate = true)
@Service(value = Servlet.class)
@Properties({
	@Property(name = "sling.servlet.resourceTypes", value = {"sling/servlet/default"}),
    @Property(name = "sling.servlet.selectors", value = {"sharedcomponents"}),
    @Property(name = "sling.servlet.extensions", value = {"json"}),
    @Property(name = "sling.servlet.methods", value = {"GET"})
})
public class SharedComponentsLocatorServlet extends SlingSafeMethodsServlet {
	
	private static final long serialVersionUID = 1L;
		
	protected static final Logger LOG = LoggerFactory.getLogger(SharedComponentsLocatorServlet.class);
	
	@Reference
	protected SharedComponentsService sharedComponentsService;

	@Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) 
    		throws ServletException, IOException {
		final JSONArray jsonResponse = new JSONArray();

        try {
        	final String path = request.getResource().getPath();
        	
        	final List<SharedComponent> sharedComponents = sharedComponentsService.getSharedComponents(path);

            Collections.sort(sharedComponents);

            if (sharedComponents != null) {

                for (final SharedComponent component : sharedComponents) {
                    JSONObject option = new JSONObject();
                    option.put("text", component.getTitle());
                    option.put("value", component.getPath());

                    jsonResponse.put(option);
                }
            }

        } catch (final JSONException e) {
            LOG.error("Error creating response with Twitter Account Names of the client.", e);
            throw new IOException(e);
        }
		
		response.setContentType("application/json");
        response.getWriter().append(jsonResponse.toString());
	}

}
