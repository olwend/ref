package uk.ac.nhm.nhm_www.core.impl.servlets;

import java.io.IOException;
import java.io.PrintWriter;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.jackrabbit.api.security.user.User;

import uk.ac.nhm.nhm_www.core.services.UserUtil;


@Component(metatype = false)
@Service(value = Servlet.class)
@Properties({
    @Property(name = "sling.servlet.paths", value = {"/bin/users/checkcomponentpanelrights"}),
    @Property(name = "sling.servlet.methods", value = {"POST"})
})
public class CheckSidekickComponentPanelRightsServlet extends SlingAllMethodsServlet {

	@Reference
	protected UserUtil userUtil;
	
	private static final long serialVersionUID = 1L;
		
	protected static final Logger LOG = LoggerFactory.getLogger(CheckSidekickComponentPanelRightsServlet.class);
	

	@Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) 
    		throws ServletException, IOException {
		final User user = request.getResourceResolver().adaptTo(User.class);
					
		final PrintWriter out = response.getWriter();
		out.append(Boolean.toString(userUtil.userHasSidekickRights(user)));
		out.close();
	}

}
