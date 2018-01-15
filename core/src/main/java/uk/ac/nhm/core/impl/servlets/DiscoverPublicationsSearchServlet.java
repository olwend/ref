package uk.ac.nhm.core.impl.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.commons.json.JSONObject;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.nhm.core.model.discover.ResourceComponent;
import uk.ac.nhm.core.services.DiscoverPublicationsSearchService;

/**
 * Servlet used to get a list of Discover Publication pages.
 * The request can provide a start index and maximum number of result to allow paginate the results.
 */
@Component(label = "Natural History Museum Discover Search Servlet", description = "Natural History Museum Discover Search Servlet", metatype = true, immediate = true)
@Service(value = Servlet.class)
@Properties({
		@Property(name = "sling.servlet.paths", value = { "/bin/discover/grid" }, propertyPrivate = true),
		@Property(name = "sling.servlet.extensions", value = {"json"}),
		@Property(name = "sling.servlet.methods", value = { "POST" }, propertyPrivate = true),
		@Property(name = "service.description", value = "Return Grid List"),
		@Property(name = "startIndex", intValue = 0, description = "Default Start Index"),
		@Property(name = "numElements", intValue = 20, description = "Default Number of Elements")

})
public class DiscoverPublicationsSearchServlet extends SlingAllMethodsServlet {

	private static final long serialVersionUID = 2598426539166789515L;

	private static final Logger LOG = LoggerFactory.getLogger(DiscoverPublicationsSearchServlet.class);

	@Reference
	private DiscoverPublicationsSearchService searchService;

	private int configStartIndex;

	private int configNumElements;

	private static final String START_INDEX_PARAM_NAME = "startIndex";

	private static final String NUM_ELEMENTS_PARAM_NAME = "numElements";

	/**
	 * Automatically called as part of service activation
	 * 
	 * @param componentContext
	 * @throws Exception
	 */
	@Activate
	protected void activate(final ComponentContext componentContext) throws Exception {
		this.loadProperties(componentContext);
	}

	@Override
	protected void doPost(SlingHttpServletRequest request,SlingHttpServletResponse response) throws ServletException,IOException {

		try {
			List<ResourceComponent> listResources = searchService.searchCQ(request);

			if (listResources == null) {
				listResources = new ArrayList<ResourceComponent>();
			}

			int startIndex;
			int numElements;

			if (request.getParameter(START_INDEX_PARAM_NAME) != null) {
				startIndex = Integer.parseInt(request.getParameter(START_INDEX_PARAM_NAME));
			} else {
				startIndex = configStartIndex;
			}
			if (request.getParameter(NUM_ELEMENTS_PARAM_NAME) != null) {
				numElements = Integer.parseInt(request.getParameter(NUM_ELEMENTS_PARAM_NAME));
			} else {
				numElements = configNumElements;
			}

			JSONObject jsonObject = searchService.getJSON(listResources,startIndex, numElements);

			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(jsonObject.toString());

		} catch (NumberFormatException e) {
			LOG.error(" SearchServlet doPost NumberFormatException.", e);
		} catch (LoginException e) {
			LOG.error(" Login error in doPost.", e);
		}
	}

	/**
	 * Private Functions
	 */

	/**
	 * Search properties
	 * 
	 * @param componentContext
	 */
	private void loadProperties(final ComponentContext componentContext) {

		try {
			configStartIndex = (Integer) componentContext.getProperties().get("startIndex");
			if (configStartIndex < 0)
				configStartIndex = 0;
			configNumElements = (Integer) componentContext.getProperties().get("numElements");
		} catch (NumberFormatException e) {
			LOG.error(" SearchServlet loadProperties NumberFormatException ", e);
		} catch (Exception e) {
			LOG.error("SearchServlet loadProperties Exception ", e);
		}

	}

}