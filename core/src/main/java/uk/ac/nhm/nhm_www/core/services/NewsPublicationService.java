package uk.ac.nhm.nhm_www.core.services;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.jcr.api.SlingRepository;
import org.apache.sling.settings.SlingSettingsService;

/**
 * Service to work with the Discover Publication Components on the repository. Ordering its tags and configuring the Creation Date of the component.
 */
@Component(metatype = false, immediate = true)
@Service(value = NewsPublicationService.class)
@Properties(value = {
	@Property(name = "service.pid", value = "uk.ac.nhm.nhm_www.core.delivery.service.NewsPublicationService", propertyPrivate = false)
})
public class NewsPublicationService extends DiscoverPublicationService {
	
}
