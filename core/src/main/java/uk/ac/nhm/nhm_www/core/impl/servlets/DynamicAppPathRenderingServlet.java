package uk.ac.nhm.nhm_www.core.impl.servlets;

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
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;

import uk.ac.nhm.nhm_www.core.services.DynamicAppPageRenderingService;


@Component(label = "Natural History Museum Dynamaic App Path Rendering Servlet", description = "Natural History Museum Dynamaic App Path Rendering Servlet", metatype = true, immediate = true)
@Service(value = Servlet.class)
@Properties({
		@Property(name = "sling.servlet.paths", value = { "/bin/list/appintegration" }, propertyPrivate = true),
		@Property(name = "sling.servlet.extensions", value = {"json"}),
		@Property(name = "sling.servlet.methods", value = { "GET" }, propertyPrivate = true),
		@Property(name = "service.description", value = "Return the Path of the required item"),
		@Property(name = "pageId", value = "home", description = "The default item ID"),
		
		
})
public class DynamicAppPathRenderingServlet extends SlingAllMethodsServlet {

	/**
	 * 
	 */
	
	private static final Logger LOG = LoggerFactory.getLogger(DynamicAppPathRenderingServlet.class);
	
	@Reference
	DynamicAppPageRenderingService pageRenderingService;
	
	private static final long serialVersionUID = 1L;

	
	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException, NumberFormatException {
		String pageId = request.getParameter("pageId");
		Page page = pageRenderingService.getPage(request, pageId);
		try {
			JSONObject jsonObject = pageRenderingService.getJSON(page);
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(jsonObject.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			LOG.error("Dynamic app template rendering error JSON Exception ", e);
		}
		
		
		
		
	}
}
