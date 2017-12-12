package uk.ac.nhm.core.services;

import java.util.Iterator;

import javax.jcr.RepositoryException;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.jackrabbit.api.security.user.Group;
import org.apache.jackrabbit.api.security.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component (label = "User Utils Service", description = "User Utility.", metatype = true, immediate = true)
@Service (value = UserUtil.class)
@Properties(value = {
		@Property(name = "youtubeCollectorService.apiKey", label = "Youtube Public API KEY", description = "API KEY for public access, configured on &quot;https://console.developers.google.com/project&quot; inside de Youtube Project API &amp; auth, Credentials."),
        @Property(name = "youtubeCollectorService.channelIds", cardinality = 20, label = "Channel IDs", description = "List of Channels accessible by the Author Content Finder of Youtube Videos."),
        @Property(name = "service.pid", value = "com.ensemble.nhm.services.youtube.YoutubeCollectorService", propertyPrivate = false),
        @Property(name = "service.description", value = "Service to get information of the Videos on the Channels configured.", propertyPrivate = false),
        @Property(name = "service.vendor", value = "Ensemble Systems Ltd.", propertyPrivate = false)
})
public class UserUtil {
	
	private static final String SIDEKICK_GROUP_PATH = "/home/groups/s/sidekickaccess";
	
	protected static final Logger LOG = LoggerFactory.getLogger(UserUtil.class);
	
	public boolean userHasSidekickRights(final User user) {
		
		if (user.isAdmin()) {
			return true;
		}
		
		try {
					
			final Iterator<Group> groups = user.memberOf();
			
			while(groups.hasNext()) {
				final Group group = groups.next();
				
				if (group.getPath().equals(SIDEKICK_GROUP_PATH)) {
					return true;
				}
			}
				
			return false;
			
		} catch (RepositoryException e) {
			LOG.error("Repository Error.", e);
			return false;
		}
	}
}
