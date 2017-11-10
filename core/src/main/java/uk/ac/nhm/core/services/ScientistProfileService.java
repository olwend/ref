package uk.ac.nhm.core.services;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.jcr.api.SlingRepository;
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
	@Property(name="scheduler.expression", value="0 0 3 * * ? *"),
})
public class ScientistProfileService implements Runnable {

	protected final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Reference
	WorkflowService workflowService;
	
	@Reference
	ResourceResolverFactory resolverFactory;
	
	@Reference
	SlingRepository repository;
	
	@Override
	public void run() {

		SimpleCredentials credential = new SimpleCredentials("workflow-user", "crxde1".toCharArray());
		Session session = null;
		String model = "/etc/workflow/models/import-scientists/jcr:content/model";

		try {
			session = repository.login(credential);
			
			WorkflowSession wfSession = workflowService.getWorkflowSession(session);
			WorkflowModel wfModel = wfSession.getModel(model);
			WorkflowData wfData = wfSession.newWorkflowData("JCR_PATH", "/tmp");
			wfSession.startWorkflow(wfModel, wfData);
		}  catch (javax.jcr.LoginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WorkflowException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		log.info(model + " run using scheduler.");
	}
}
