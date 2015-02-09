package uk.ac.nhm.nhm_www.core.impl;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.ReferencePolicy;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.commons.WCMUtils;

import uk.ac.nhm.nhm_www.core.InformationalPageHeadService;

@Service 
@Component(immediate=true, metatype=false)
public class InformationalPageHeadServiceImpl implements InformationalPageHeadService{

	protected static final Logger logger = LoggerFactory
			.getLogger(InformationalPageHeadService.class);
	
	@Reference(policy=ReferencePolicy.STATIC)
	private ResourceResolverFactory resolverFactory;

	public String buttonProperties(Page page) {
		String joinRenewButtonText = "";
		ResourceResolver resourceResolver = null;
		try {
			if (resolverFactory != null) {
				resourceResolver = resolverFactory.getAdministrativeResourceResolver(null);
				joinRenewButtonText = WCMUtils.getInheritedProperty(page, resourceResolver, "cq:informationalPageHeadButtonOneTitle");
			} else {
				joinRenewButtonText = "resolverFactory is NULL";
			}

		} catch (LoginException e) {
			// TODO Auto-generated catch block
			logger.debug("resource resolver not working");
		}
		
		return joinRenewButtonText;

	}

}
