package uk.ac.nhm.core.impl.servlets;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.commons.lang3.StringUtils;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(label="Natural History Museum Scientists Pagination Search Servlet", description="This Servlet will perform a doPost to get the results of our search in the Scientist area taking care of the pagination and the filtering.",
			metatype=true, immediate=true)
@Service(value=Servlet.class)
@Properties({
	@Property(name="sling.servlet.paths", value={"/bin/scientistprofilesearch"}, propertyPrivate = true),
	@Property(name="sling.servlet.extensions", value={"json"}, propertyPrivate = true),
	@Property(name="sling.servlet.methods", value={"GET"}, propertyPrivate = true),
	@Property(name="scientists.rootPage", value="/content/nhmwww/aem/home", label="Root search page", description="Restrict search under this page")
})

public class ScientistSearchServlet extends SlingSafeMethodsServlet {
	
	String rootPage = StringUtils.EMPTY;
	private static final Logger LOG = LoggerFactory.getLogger(ScientistSearchServlet.class);

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write("Result: " + this.rootPage);
	}
	
	@Activate
	protected void activate(final ComponentContext componentContext) throws Exception {
		rootPage = componentContext.getProperties().get("scientists.rootPage").toString();
	}
}
