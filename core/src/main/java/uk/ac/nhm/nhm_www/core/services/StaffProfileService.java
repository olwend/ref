package uk.ac.nhm.nhm_www.core.services;

import java.util.ArrayList;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;

import org.apache.commons.lang3.StringUtils;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.PageManager;

@Component(label = "Natural History Museum Staff Profile Servlet",
           description = "Returns the complete top level list of staff profiles for the staff profile search page",
           metatype = true,
           immediate = true)
@Service(value = StaffProfileService.class)
@Properties({
        @Property(name = "service.description", value = "Returns the complete top level list of staff profiles for the staff profile search page"),
        @Property(name = "queryLimit", intValue = 500, description = "Query Limit"),
        @Property(name = "jcrPath", value = "/content", description = "JCR Path where the staff profiles are located.")
        
})
public class StaffProfileService {
    private int queryLimit;
    private String jcrPath;
    private static final Logger LOG = LoggerFactory.getLogger(StaffProfileService.class);
    
    @Activate
    protected void activate(final ComponentContext componentContext) throws Exception {
        loadProperties(componentContext);
    }
    
    public List<StaffProfile> getStaffProfile (final Resource resource) throws Exception {
        try {
            final List<StaffProfile> profileList = new ArrayList<StaffProfile>();
            
            final ResourceResolver resourceResolver = resource.getResourceResolver();
    
            final Session session = resourceResolver.adaptTo(Session.class);
            final PageManager pmanager = resourceResolver.adaptTo(PageManager.class);
            
            final QueryManager queryMgr = session.getWorkspace().getQueryManager();
            final Query query = queryMgr.createQuery(getQuery(), Query.JCR_SQL2);
            
            query.setLimit(queryLimit);
            
            final QueryResult queryResult = query.execute();

            final NodeIterator itr = queryResult.getNodes();
                        
            while (itr.hasNext())
            {
                final Node node = itr.nextNode();
                
                profileList.add (new StaffProfile (node,pmanager.getContainingPage(node.getPath()).getPath() + ".html"));
            }
            
            return profileList;
        } catch (Exception e) {
            LOG.error("StaffProfile Get Exception: ", e);
            throw e;
        }
    }
    
    private String getQuery() {
        return "SELECT * FROM [nt:unstructured] as S WHERE ISDESCENDANTNODE (S, '" + jcrPath + "') AND NAME () = 'personalInformation'";
    }
    
    private void loadProperties(final ComponentContext componentContext) {
        try {
            queryLimit = (Integer) componentContext.getProperties().get("queryLimit");
            jcrPath = componentContext.getProperties().get("jcrPath").toString();
        } catch (Exception e) {
            LOG.error("StaffProfile loadProperties Exception: ", e);
        }
    }
    
    public class StaffProfile {
    	private final String imagePath;
        private final String firstName;
        private final String lastName;
        private final String department;
        private final String division;
        private final String collection;
        private final String collectionGroup;
        private final String job;
        private final String[] specialisms;
        private final String url;
        
        /**
         * @param node
         * @param url
         * @throws Exception
         */
        public StaffProfile (Node node, String url) throws Exception {
        	imagePath = node.getParent().getPath() + "/image";
        	
        	if(node.hasProperty("knownAs")){
        		firstName = node.getProperty("knownAs").getString();
        	} else {
        		firstName = node.getProperty("firstName").getString();
        	}
            lastName = node.getProperty("lastName").getString();
            //division = node.getProperty("department").getString();
            final Node departmentNode = node.getParent().getNode("department");
            department = departmentNode.getProperty("name").getString();
            
            String textDivision = departmentNode.getProperty("division").getString();
            if (textDivision.startsWith("LS ") || textDivision.startsWith("ES ")) {
            	division = textDivision.substring(3);
            } else {
            	division = textDivision;
            }
            job = departmentNode.getProperty("position").getString();
            
            if (departmentNode.hasProperty("function")) {
            	collection = departmentNode.getProperty("function").getString();
            } else {
            	collection = "#Empty#";
            }
            
            if (departmentNode.hasProperty("groupName") && departmentNode.getProperty("groupName").getString() != null ) {
            	collectionGroup = departmentNode.getProperty("groupName").getString();
            } else {
            	collectionGroup = "#Empty#";
            }
            
            if (node.hasProperty("specialisms")) {
            
	            final javax.jcr.Property specialismsProperty = node.getProperty("specialisms");
	            
            	final Value[] specialismsValues = specialismsProperty.getValues();
            	
            	specialisms = new String[specialismsValues.length];
            	
            	for (int i = 0; i < specialismsValues.length; i++) {
            		specialisms[i] = specialismsValues[i].toString();
            	}
            } else {
            	specialisms = new String[0];
            }
            this.url = url; 
        }
        
        public String getCollection() {
			return collection;
		}

		public String getCollectionGroup() {
			return collectionGroup;
		}

		public String getFirstName() {
            return firstName;
        }
        
        public String getLastName() {
            return lastName;
        }
        
        public String getDepartment() {
        	return department;
        }
        
        public String getDivision() {
            return division;
        }
        
        public String getJob() {
            return job;
        }
        
        public String getUrl() {
            return url;
        }
        
        public String getSpecialisms() {
        	if (specialisms == null) {
        		return "";
        	}
        	
        	return StringUtils.join(specialisms, ", ");
        }
        
        public String getImagePath() {
        	return imagePath;
        }
    }
}
