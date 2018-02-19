package uk.ac.nhm.core.services;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Modified;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.osgi.service.component.ComponentContext;

@Component (label = "Dino Directory Environment Service", description = "Service to configure environment for Dino Directory API calls.", metatype = true, immediate = true)
@Service (value = DinoDirectoryEnvironmentService.class)
@Properties(value = {
		@Property(name = "nhm.dinoDirectory.environment", label = "Dino Directory API base URL", value = "http://staging.nhm.ac.uk/api/dino-directory-api"),
        @Property(name = "service.pid", value = "uk.ac.nhm.core.services.DinoDirectoryEnvironmentService", propertyPrivate = false),
        @Property(name = "service.description", value = "Service to configure environment for Dino Directory API calls.", propertyPrivate = false)
})
public class DinoDirectoryEnvironmentService {
	private String dinoDirectoryUrl;
	
	@Activate
    @Modified
    protected void activate(final ComponentContext context) {
		final Object serviceConfigured = context.getProperties().get("nhm.dinoDirectory.environment");
        
        if (serviceConfigured instanceof String) {
        	this.dinoDirectoryUrl = (String)serviceConfigured;
        }
	}

	public String getDinoDirectoryUrl() {
		return dinoDirectoryUrl;
	}

}
