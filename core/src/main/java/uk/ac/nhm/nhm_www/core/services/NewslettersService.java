package uk.ac.nhm.nhm_www.core.services;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Modified;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.osgi.service.component.ComponentContext;

/**
 * OSGi Service created to give the option to configure the Newsletter Sign Up URL where ask for the NHM Sign Up Process
 */
@Component (label = "NHM Newsletters Service", description = "Natural History Museum Newsletters Information.", metatype = true, immediate = true)
@Service (value = NewslettersService.class)
@Properties(value = {
		@Property(name = "nhm.newsletter.externalService", label = "Newsletters Sign Up External URL", description = "External URL to AEM 6 Server to request for the Newsletters Sign Up Process."),
        @Property(name = "service.pid", value = "uk.ac.nhm.nhm_www.core.services.NewslettersService", propertyPrivate = false),
        @Property(name = "service.description", value = "Service to get information of the Videos on the Channels configured.", propertyPrivate = false),
        @Property(name = "service.vendor", value = "Ensemble Systems Ltd.", propertyPrivate = false)
})
public class NewslettersService {
	private String nhmService;
	
	/*
	 * Get the information configured for the Service on the Configuration section in /system/console.
	 */
	@Activate
    @Modified
    protected void activate(final ComponentContext context) {
		final Object nhmServiceConfigured = context.getProperties().get("nhm.newsletter.externalService");
        
        if (nhmServiceConfigured instanceof String) {
        	this.nhmService = (String)nhmServiceConfigured;
        }
	}
	
	/**
	 * Gets the Service configured on System Console under the option NHM Newsletters Service.
	 * @return The URL configured on the System Console or <code>null</code> if it is not configured.
	 */
	public String getNHMNewslettersSignUpService() {
		return this.nhmService;
	}
	
}
