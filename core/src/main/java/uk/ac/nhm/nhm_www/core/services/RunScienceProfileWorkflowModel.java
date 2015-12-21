package uk.ac.nhm.nhm_www.core.services;

import java.io.IOException;

import javax.jcr.Session;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.felix.scr.annotations.Reference;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestParameterMap;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.workflow.WorkflowException;
import com.day.cq.workflow.WorkflowService;
import com.day.cq.workflow.WorkflowSession;
import com.day.cq.workflow.exec.WorkflowData;
import com.day.cq.workflow.model.WorkflowModel;

@Component
@Service
@Properties({
	@Property(name="sling.servlet.paths", value="/bin/runprofileworkflow"),
	@Property(name="sling.serlvet.methods", value="GET")
})
public class RunScienceProfileWorkflowModel extends SlingSafeMethodsServlet {

	static private final Logger log = LoggerFactory.getLogger(RunScienceProfileWorkflowModel.class);

	@Reference
	WorkflowService workflowService;
	
	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
		ResourceResolver resourceResolver = request.getResourceResolver();
        Session session = resourceResolver.adaptTo(Session.class);
		
		String model = "/etc/workflow/models/import-scientists/jcr:content/model";

		WorkflowSession wfSession = workflowService.getWorkflowSession(session);
		
		try {
			WorkflowModel wfModel = wfSession.getModel(model);
			WorkflowData wfData = wfSession.newWorkflowData("JCR_PATH", "/tmp");
			wfSession.startWorkflow(wfModel, wfData);
		} 
		catch (WorkflowException ex) {
            response.getWriter().write("failure");
            log.error("Error starting workflow.", ex);
        }
		
		response.getWriter().write("success");
	}
	
}
