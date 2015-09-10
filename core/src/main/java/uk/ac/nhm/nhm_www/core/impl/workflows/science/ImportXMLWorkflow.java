package uk.ac.nhm.nhm_www.core.impl.workflows.science;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.TreeSet;

import javax.activation.MimetypesFileTypeMap;
import javax.jcr.ItemExistsException;
import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.jcr.ValueFormatException;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.nodetype.NoSuchNodeTypeException;
import javax.jcr.version.VersionException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang3.StringUtils;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.jackrabbit.commons.JcrUtils;
import org.apache.jackrabbit.util.Text;
import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.nhm.nhm_www.core.componentHelpers.ScientistProfileHelper;
import uk.ac.nhm.nhm_www.core.impl.workflows.science.generated.AcademicAppointment;
import uk.ac.nhm.nhm_www.core.impl.workflows.science.generated.AcademicAppointments;
import uk.ac.nhm.nhm_www.core.impl.workflows.science.generated.Address;
import uk.ac.nhm.nhm_www.core.impl.workflows.science.generated.Degree;
import uk.ac.nhm.nhm_www.core.impl.workflows.science.generated.Degrees;
import uk.ac.nhm.nhm_www.core.impl.workflows.science.generated.Field;
import uk.ac.nhm.nhm_www.core.impl.workflows.science.generated.Line;
import uk.ac.nhm.nhm_www.core.impl.workflows.science.generated.Link;
import uk.ac.nhm.nhm_www.core.impl.workflows.science.generated.NonAcademicEmployment;
import uk.ac.nhm.nhm_www.core.impl.workflows.science.generated.NonAcademicEmployments;
import uk.ac.nhm.nhm_www.core.impl.workflows.science.generated.Ns1Object;
import uk.ac.nhm.nhm_www.core.impl.workflows.science.generated.OrganisationDefinedData;
import uk.ac.nhm.nhm_www.core.impl.workflows.science.generated.Pagination;
import uk.ac.nhm.nhm_www.core.impl.workflows.science.generated.Person;
import uk.ac.nhm.nhm_www.core.impl.workflows.science.generated.PhoneNumber;
import uk.ac.nhm.nhm_www.core.impl.workflows.science.generated.Record;
import uk.ac.nhm.nhm_www.core.impl.workflows.science.generated.WebAddress;
import uk.ac.nhm.nhm_www.core.impl.workflows.science.generated.WebProfile;
import uk.ac.nhm.nhm_www.core.impl.workflows.science.generated.WebProfile.Grants;
import uk.ac.nhm.nhm_www.core.impl.workflows.science.generated.WebProfile.ProfessionalActivities;
import uk.ac.nhm.nhm_www.core.impl.workflows.science.generated.WebProfile.Projects;
import uk.ac.nhm.nhm_www.core.impl.workflows.science.generated.WebProfile.Projects.ChampionOf.Project;
import uk.ac.nhm.nhm_www.core.impl.workflows.science.generated.WebProfile.TeachingActivities;
import uk.ac.nhm.nhm_www.core.impl.workflows.science.generated.WebProfile.TeachingActivities.Associated.Activity;
import uk.ac.nhm.nhm_www.core.model.science.projects.ProjectTemplate;
import uk.ac.nhm.nhm_www.core.services.ScientistsGroupsService;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.AssetManager;

@Component(label = "Import Staff Profiles XML Workflow Process", description = "Natural History Musuem Import Staff Profiles XML Workflow Process", metatype = true, immediate = true)
@Service
@Properties({
    @Property(name = "process.label", value = "NHM Import Staff XML Workflow Process") })
public class ImportXMLWorkflow implements WorkflowProcess {

    private static final Logger LOG = LoggerFactory.getLogger(ImportXMLWorkflow.class);
    
    private static final String GROUPS_ROOT_NODE_NAME = "groups";
    private static final String SCIENCE_PROFILE_PAGE_RESOURCE_TYPE = "nhmwww/components/page/scienceprofilepage";
    private static final String TYPE_PRIVACITY_PUBLIC = "public";
    private static final String DOI_TEXT = "doi";

    private Session session;
    
    private String xmlPath;
    private String contentPath;
	private String damPath;
	private String imagesPath;

	// Sling ResourceResolver
	private ResourceResolver resourceResolver;
	
	private Node rootGroupsNode;
	 
    /**
     * Function to save the XML information in the AEM repository
     */
    @Override
    public void execute(final WorkItem item, final WorkflowSession wfsession, final MetaDataMap args) throws WorkflowException  {

        if(args.get("xmlPath") != null) {
            this.xmlPath = args.get("xmlPath").toString();
        } else {
            LOG.error("xml path is null");
            return;
        }
        
        if(args.get("contentPath") != null) {
            this.contentPath = args.get("contentPath").toString();
        }
        
        if(args.get("damPath") != null) {
			this.damPath = args.get("damPath").toString() + "/";
		}
        
        if (args.get("imagesPath") != null) {
        	this.imagesPath = args.get("imagesPath").toString();
        	if (!this.imagesPath.endsWith("/")) {
        		this.imagesPath = this.imagesPath + "/";
        	}
        }
    
        final Path path = Paths.get(this.xmlPath);
        
        try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(path, "*.xml");) {
            this.session =  wfsession.adaptTo(Session.class);
            
            this.resourceResolver  = wfsession.adaptTo(ResourceResolver.class);

            this.deletePreviousInformation();
            
            for(Path dirItem: dirStream) {
                try (Reader reader = Files.newBufferedReader(dirItem, Charset.forName("UTF-8"));) {
                	/* Look for the Image associated with this scientist and import it to the dam. */
                    final String name = dirItem.getFileName().toString();
                    
                    final String fileName = name.substring(0, name.length() - 4);
                    
                    final Path imagesPath = Paths.get(this.imagesPath);
        			
        			try (final DirectoryStream<Path> imageDirStream = Files.newDirectoryStream(imagesPath, fileName.replaceAll("user", "userphoto") + ".*");) {
	        			
	                	
	                    reader.read();
	                    final JAXBContext jaxbContext = JAXBContext.newInstance(WebProfile.class);
	                    final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
	
	                    final WebProfile profile = (WebProfile) unmarshaller.unmarshal(reader);
	                    String imageDamPath = null;
	        			
	        			for (final Path imagePath:imageDirStream) {
		        			final File file = new File(imagePath.toString());
		        			if(file.exists() && !file.isDirectory()) {
			        			try (final InputStream is = new FileInputStream(file);) {
				        		    // Get MimeType
				        		    final String mimeType = new MimetypesFileTypeMap().getContentType(file.getName());
				        		    // Save to AEM Assets 
				        		    final Asset asset = writeToDam(is,file.getName(),mimeType,profile);
				        		    imageDamPath = asset.getPath();
			        			}
			        		    break;
		        			}
	        			}
	                    this.processFile (profile, imageDamPath);
        			}
                }
            }
            session.save();
        }
        catch (Exception e)
        {
            LOG.error("Unable to import XML: ", e);
        }
    }
    
    private void deletePreviousInformation () throws PathNotFoundException, RepositoryException {
    	//Get the contentPath node in the JCR
        final Node rootNode = session.getNode(contentPath);
        
        /* Delete information about groups. This is going to be a node called groups inside of root --> jcr:content */
        if (rootNode.hasNode(JcrConstants.JCR_CONTENT)) {
        	final Node rootJcrContentNode = rootNode.getNode(JcrConstants.JCR_CONTENT); 
        
        	if (rootJcrContentNode.hasNode(GROUPS_ROOT_NODE_NAME)) {
        		this.rootGroupsNode = rootJcrContentNode.getNode(GROUPS_ROOT_NODE_NAME);
        		
        		/* Delete all the groups previously defined on the Groups Node. */
        		Iterable<Node> itr = JcrUtils.getChildNodes(this.rootGroupsNode);
                for (Node itrNode:itr) {
                    itrNode.remove();
                }
        	} else {
        		/* Create the Groups information node. */
        		this.rootGroupsNode = rootJcrContentNode.addNode(GROUPS_ROOT_NODE_NAME);
        	}
        } 
        
        final Iterable<Node> itr = JcrUtils.getChildNodes(rootNode);
        
        for (final Node node : itr) {
        	if (!node.hasNode(JcrConstants.JCR_CONTENT)) {
        		continue;
        	}
        	
        	final Node jcrNode = node.getNode(JcrConstants.JCR_CONTENT);
        	
            if (jcrNode.hasProperty("sling:resourceType") && 
            		SCIENCE_PROFILE_PAGE_RESOURCE_TYPE.equals(jcrNode.getProperty("sling:resourceType").getString())) {
            	node.remove();
            }
        }
    }
    
    private void setNodeProfile (final Node personalInfo, final Ns1Object ns1) throws Exception {
        personalInfo.setProperty(ScientistProfileHelper.TITLE_ATTRIBUTE		 , ns1.getObject().getTitle());
        personalInfo.setProperty(ScientistProfileHelper.INITIALS_ATTRIBUTE	 , ns1.getObject().getInitials());
        personalInfo.setProperty(ScientistProfileHelper.LASTNAME_ATTRIBUTE	 , ns1.getObject().getLastName());
        personalInfo.setProperty(ScientistProfileHelper.FIRSTNAME_ATTRIBUTE	 , ns1.getObject().getFirstName());
        personalInfo.setProperty(ScientistProfileHelper.KNOWS_AS_ATTRIBUTE	 , ns1.getObject().getKnownAs());
        personalInfo.setProperty(ScientistProfileHelper.ARRIVE_DATE_ATTRIBUTE, ns1.getObject().getArriveDate());
        personalInfo.setProperty(ScientistProfileHelper.POSITION_ATTRIBUTE	 , ns1.getObject().getPosition());
        personalInfo.setProperty(ScientistProfileHelper.DEPARTMENT_ATTRIBUTE , ns1.getObject().getDepartment());
        personalInfo.setProperty(ScientistProfileHelper.EMAIL_ATTRIBUTE	 	 , ns1.getObject().getEmailAddress().getContent().get(0).toString());
        
        final List<Field> fields = ns1.getObject().getRecords().getRecord().get(0).getNative().getField();
        
        for (final Field field : fields) {
        	switch (field.getName()) {
        		case "personal-websites":
        			this.setWebsitesAndBlog(personalInfo, field);
        			break;
        		case "phone-numbers":
        			this.addPhones(personalInfo, field);
        			break;
        		case "overview":
        			personalInfo.setProperty(ScientistProfileHelper.STATEMENT_ATTRIBUTE, field.getText());
        			break;
        	}
        }
    }
    
    private void addPhones(final Node personalInfo, final Field phoneField) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException {
    	final Node phoneNumbersNode = personalInfo.addNode(ScientistProfileHelper.PHONE_NUMBERS_NODE_NAME);
    	final List<PhoneNumber> phoneNumbers = phoneField.getPhoneNumbers().getPhoneNumber();
    	
    	int count = 0;
    	
    	for (final PhoneNumber phoneNumber : phoneNumbers) {
    		if (phoneNumber != null && TYPE_PRIVACITY_PUBLIC.equals(phoneNumber.getPrivacy())) {
        		String phone = phoneNumber.getNumber();
        		String label = phoneNumber.getType();
        		
        		if (phoneNumber.getExtension() != null) {
        			phone = phone + " " + phoneNumber.getExtension();
        		}
        		
        		final Node phoneNumberNode = phoneNumbersNode.addNode(ScientistProfileHelper.PHONE_NUMBER_PREFIX_NODE_NAME + "_" + count++);
        		phoneNumberNode.setProperty(ScientistProfileHelper.PHONE_ATTRIBUTE, phone);
        		phoneNumberNode.setProperty(ScientistProfileHelper.LABEL_ATTRIBUTE, label);
        	}	
    	}
    }
    
    private void setWebsitesAndBlog(final Node personalInfo, final Field websitesField) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException {
    	final List<WebAddress> webAddresses = websitesField.getWebAddresses().getWebAddress();
    	
    	final Node websitesNode = personalInfo.addNode(ScientistProfileHelper.WEB_SITES_NODE_NAME);
    	
    	int nodesCount = 0;
    	
    	for (final WebAddress webAddress : webAddresses) {
    		final String label = webAddress.getLabel();
    		
    		final Node websiteNode = websitesNode.addNode(ScientistProfileHelper.WEB_SITE_PREFIX_NODE_NAME + "_" + nodesCount);
    		
    		websiteNode.setProperty(ScientistProfileHelper.LINK_ATTRIBUTE, webAddress.getUrl());
    		
    		websiteNode.setProperty(ScientistProfileHelper.TYPE_ATTRIBUTE, webAddress.getType());
    		
    		if (label != null && !label.equals("")) {
    			websiteNode.setProperty(ScientistProfileHelper.LABEL_ATTRIBUTE, label);
    		}
    		
    		nodesCount++;
    	}
    }
    
    // Used as well Mentor property and Coaching property sort in alphabetic order, these are as well in the same xml element.
    private void addSpecialismsProperties (final Node personalInfo, final Activity activity) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException {
    	final List<Field> fields = activity.getObject().getRecords().getRecord().get(0).getNative().getField();
    	
    	for (final Field field : fields) {
    		if (!"description".equals(field.getName())) {
    			continue;
    		}
    		
    		final Set<String> specialismsPropertyList = new TreeSet<>();
    		if (personalInfo.hasProperty(ScientistProfileHelper.SPECIALISMS_ATTRIBUTE)) {
    			final Value[] specialismsValues = personalInfo.getProperty(ScientistProfileHelper.SPECIALISMS_ATTRIBUTE).getValues();
    			
    			for (final Value value : specialismsValues) {
    				specialismsPropertyList.add(value.getString());
    			}
    		}
    		
    		final String specialismText = field.getText();
    		final String[] specialismWords = specialismText.split(",");
    		
    		for (final String specialismWord : specialismWords) {
    			specialismsPropertyList.add(specialismWord.startsWith(" ") ? specialismWord.substring(1) : specialismWord);
    			
    		}
    		
    		personalInfo.setProperty(ScientistProfileHelper.SPECIALISMS_ATTRIBUTE, specialismsPropertyList.toArray(new String[specialismsPropertyList.size()]));
    	}
    }
    
    private void setDepartment (final Node departmentNode, final Ns1Object ns1) throws Exception {
        departmentNode.setProperty(ScientistProfileHelper.NAME_ATTRIBUTE, ns1.getObject().getPrimaryGroupDescriptor());
        
        final List<OrganisationDefinedData> organisationInformation = ns1.getObject().getOrganisationDefinedData(); 
        for (final OrganisationDefinedData organisationDefinition : organisationInformation) {
        	switch (organisationDefinition.getFieldName()) {
        		case "Function":
        			departmentNode.setProperty(ScientistProfileHelper.FUNCTION_ATTRIBUTE, organisationDefinition.getContent());
        			break;
        		case "Position":
        	        departmentNode.setProperty(ScientistProfileHelper.POSITION_ATTRIBUTE, organisationDefinition.getContent());
        			break;
        		case "Division":
        			departmentNode.setProperty(ScientistProfileHelper.DIVISION_ATTRIBUTE, organisationDefinition.getContent());
        			break;
        		case "Group Name":
        			departmentNode.setProperty(ScientistProfileHelper.GROUP_NAME_ATTRIBUTE, organisationDefinition.getContent());
        			final String groupName = organisationDefinition.getContent();
        			if (groupName != null && !groupName.equals("")) {
        				departmentNode.setProperty(ScientistProfileHelper.GROUP_PATH_ATTRIBUTE, this.createGroupInformation(groupName, departmentNode.getParent().getParent()));
        			}
        	}
        }
    }
    
    /**
     * Save the information of the Scientist's Group adding the Scientist's Node to the Group Information.
     * @param groupName Name of the group, used to identify the name of the node.
     * @param scientistNode {@link Node Node} of the Scientist to get its path.
     * @return Group Node's Path.
     * @throws RepositoryException 
     * @throws IllegalStateException 
     * @throws ConstraintViolationException 
     * @throws LockException 
     * @throws VersionException 
     * @throws ValueFormatException 
     * @throws PathNotFoundException 
     */
    private String createGroupInformation(final String groupName, final Node scientistPageNode) throws PathNotFoundException, ValueFormatException, VersionException, LockException, ConstraintViolationException, IllegalStateException, RepositoryException {
    	final String nodeName = Text.escapeIllegalJcrChars(groupName.toLowerCase());
    	
    	if (this.rootGroupsNode == null) {
    		return null;
    	}
    	
    	if (this.rootGroupsNode.hasNode(nodeName)) {
    		final Node groupNode = this.rootGroupsNode.getNode(nodeName);
    		if (!groupNode.hasProperty(ScientistsGroupsService.GROUP_NAME_PROPERTY_NAME)) {
    			groupNode.setProperty(ScientistsGroupsService.GROUP_NAME_PROPERTY_NAME, groupName);
    		}
    		
    		if (groupNode.hasProperty(ScientistsGroupsService.SCIENTISTS_PROPERTY_NAME)) {
    			final javax.jcr.Property scientistsProperty = groupNode.getProperty(ScientistsGroupsService.SCIENTISTS_PROPERTY_NAME);
    			final Value[] scientistsValues = scientistsProperty.getValues();
    			
    			final String newScientistPath = scientistPageNode.getPath();
    			final List<String> scientistPaths = new ArrayList<>();
    			for (final Value value : scientistsValues) {
    				final String stringValue = value.getString();
    				
    				/* The property has already stored the value. */
    				if (newScientistPath.equals(stringValue)) {
    					return groupNode.getPath();
    				}
    				scientistPaths.add(stringValue);
    			}
    			
    			scientistPaths.add(scientistPageNode.getPath());
    			groupNode.setProperty(ScientistsGroupsService.SCIENTISTS_PROPERTY_NAME, scientistPaths.toArray(new String[scientistPaths.size()]));
    		} else {
    			final String[] scientists = {scientistPageNode.getPath()};
    			groupNode.setProperty(ScientistsGroupsService.SCIENTISTS_PROPERTY_NAME, scientists);
    		}
    		return groupNode.getPath();
    	} else {
    		final Node groupNode = this.rootGroupsNode.addNode(nodeName);
    		groupNode.setProperty(ScientistsGroupsService.GROUP_NAME_PROPERTY_NAME, groupName);
    		final String[] scientists = {scientistPageNode.getPath()};
			groupNode.setProperty(ScientistsGroupsService.SCIENTISTS_PROPERTY_NAME, scientists);
			return groupNode.getPath();
    	}
    }
    
    private void addAppointments(final Node node, AcademicAppointments appointments) throws Exception {
        int counter = 0;
        
        final Node appNode = node.addNode(ScientistProfileHelper.ACADEMIC_HISTORY_NODE_NAME, JcrConstants.NT_UNSTRUCTURED);
        for (AcademicAppointment app: appointments.getAcademicAppointment()) {
            final Node singleAppNode = appNode.addNode(ScientistProfileHelper.WORK_EXPERIENCE_PREFIX_NODE_NAME + counter++, JcrConstants.NT_UNSTRUCTURED);
            
            final List<Line> lines = app.getInstitution().getLine();
            
            for (final  Line line : lines) {
            	switch (line.getType()) {
            		case "organisation":
            			singleAppNode.setProperty(ScientistProfileHelper.ORGANISATION_ATTRIBUTE, line.getContent());
            			break;
            		case "suborganisation":
            			singleAppNode.setProperty(ScientistProfileHelper.SUBORGANISATION_ATTRIBUTE, line.getContent());
            			break;
            		case "city":
            			singleAppNode.setProperty(ScientistProfileHelper.CITY_ATTRIBUTE, line.getContent());
            			break;
            		case "country":
            			singleAppNode.setProperty(ScientistProfileHelper.COUNTRY_ATTRIBUTE, line.getContent());
            	}
            }
            
            singleAppNode.setProperty(ScientistProfileHelper.POSITION_ATTRIBUTE, app.getPosition());
            singleAppNode.setProperty(ScientistProfileHelper.START_DATE_ATTRIBUTE, app.getStartDate().getYear().longValue());
            
            if (app.getEndDate() != null) {
                singleAppNode.setProperty(ScientistProfileHelper.END_DATE_ATTRIBUTE, app.getEndDate().getYear().longValue());
            }
        }
    }
    
    private void addNonAppointments(final Node node, NonAcademicEmployments appointments) throws Exception {
        int counter = 0;
        
        final Node appNode = node.addNode(ScientistProfileHelper.NON_ACADEMIC_HISTORY_NODE_NAME, JcrConstants.NT_UNSTRUCTURED);
        for (final NonAcademicEmployment app: appointments.getNonAcademicEmployment()) {
	        final Node singleAppNode = appNode.addNode(ScientistProfileHelper.WORK_EXPERIENCE_PREFIX_NODE_NAME + counter++, JcrConstants.NT_UNSTRUCTURED);
	            
	        final List<Line> lines = app.getEmployer().getLine();
            
            for (final  Line line : lines) {
            	switch (line.getType()) {
            		case "organisation":
            			singleAppNode.setProperty(ScientistProfileHelper.ORGANISATION_ATTRIBUTE, line.getContent());
            			break;
            		case "suborganisation":
            			singleAppNode.setProperty(ScientistProfileHelper.SUBORGANISATION_ATTRIBUTE, line.getContent());
            			break;
            		case "city":
            			singleAppNode.setProperty(ScientistProfileHelper.CITY_ATTRIBUTE, line.getContent());
            			break;
            		case "country":
            			singleAppNode.setProperty(ScientistProfileHelper.COUNTRY_ATTRIBUTE, line.getContent());
            			break;
            	}
            }
            
	        singleAppNode.setProperty(ScientistProfileHelper.POSITION_ATTRIBUTE, app.getPosition());
	        singleAppNode.setProperty(ScientistProfileHelper.START_DATE_ATTRIBUTE, app.getStartDate().getYear().longValue());
	            
	        if (app.getEndDate() != null) {
	            singleAppNode.setProperty(ScientistProfileHelper.END_DATE_ATTRIBUTE, app.getEndDate().getYear().longValue());
	        }
        }
    }
    
    
    private void addDegrees (final Node node, Degrees degrees) throws Exception {
        int counter = 0;
        
        final Node appNode = node.addNode(ScientistProfileHelper.DEGREES_NODE_NAME, JcrConstants.NT_UNSTRUCTURED);
        
        for (Degree degree: degrees.getDegree()) {
            final Node degreeNode = appNode.addNode(ScientistProfileHelper.QUALIFICATION_PREFIX_NODE_NAME + counter++, JcrConstants.NT_UNSTRUCTURED);
            
            degreeNode.setProperty(ScientistProfileHelper.TITLE_ATTRIBUTE, degree.getName());
            
            if (degree.getStartDate() != null) {
                degreeNode.setProperty(ScientistProfileHelper.FROM_ATTRIBUTE, degree.getStartDate().getYear().longValue());
            }
            
            if (degree.getEndDate() != null) {
                degreeNode.setProperty(ScientistProfileHelper.TO_ATTRIBUTE, degree.getEndDate().getYear().longValue());
            }
            
            degreeNode.setProperty(ScientistProfileHelper.INSTITUTION_ATTRIBUTE, degree.getInstitution().getLine().get(0).getContent());
            degreeNode.setProperty(ScientistProfileHelper.COUNTRY_ATTRIBUTE, degree.getInstitution().getLine().get(1).getContent());
        }
    }
    
    private void processRecords (final Node rootNode, List<Record> recordList) throws Exception {
        final Node node = rootNode.addNode(ScientistProfileHelper.PROFESIONAL_INFORMATION_NODE_NAME, JcrConstants.NT_UNSTRUCTURED);
        
        for (Record record: recordList) {
            for (Field field: record.getNative().getField()) {
                switch (field.getType()) {
                    case "academic-appointment-list":
                        addAppointments (node, field.getAcademicAppointments());
                        break;
                    case "non-academic-employment-list":
                        addNonAppointments (node, field.getNonAcademicEmployments());
                        break;
                    case "degree-list":
                        addDegrees (node, field.getDegrees());
                }
            }
        }
    }
    
    
	/*
	 * ##################
	 * ## Publications ##
	 * ##################
	 */
    
    private String resolvePublicationType (final int number) {
        switch (number) {
	        case 2 : return ScientistProfileHelper.PUBLICATION_TYPE_BOOK;
	        case 3 : return ScientistProfileHelper.PUBLICATION_TYPE_CHAPTER;
	        case 4 : return ScientistProfileHelper.PUBLICATION_TYPE_CONFERENCE_PROCEEDINGS;
	        case 5 : return ScientistProfileHelper.PUBLICATION_TYPE_ARTICLE;
	        case 7 : return ScientistProfileHelper.PUBLICATION_TYPE_REPORT;
	        case 8 : return ScientistProfileHelper.PUBLICATION_TYPE_SOFTWARE;
	        case 13: return ScientistProfileHelper.PUBLICATION_TYPE_EXHIBITION;
	        case 14: return ScientistProfileHelper.PUBLICATION_TYPE_OTHER;
	        case 15: return ScientistProfileHelper.PUBLICATION_TYPE_INTERNET_PUBLICATION;
	        case 16: return ScientistProfileHelper.PUBLICATION_TYPE_SCHOLARLY_EDITION;
	        case 17: return ScientistProfileHelper.PUBLICATION_TYPE_POSTER;
	        case 18: return ScientistProfileHelper.PUBLICATION_TYPE_THESIS_OR_DISERTATION;
	        case 22: return ScientistProfileHelper.PUBLICATION_TYPE_DATASET;
	        case 78: return ScientistProfileHelper.PUBLICATION_TYPE_WEBPAGE;
	        case 80: return ScientistProfileHelper.PUBLICATION_TYPE_WEBSITE;
	        case 81: return ScientistProfileHelper.PUBLICATION_TYPE_NEWSPAPER_OR_MAGAZINE;
	        default: return "Publication";
        }
    }
    
    private void addPublication (final Node rootNode, final List<WebProfile.Publications.Contributed.Publication> publications) throws Exception {
        int i  = 0;
        
        for (WebProfile.Publications.Contributed.Publication publication : publications) {
            final Node publicationsNode = rootNode.addNode(ScientistProfileHelper.PUBLICATION_PREFIX_NODE_NAME + i++, JcrConstants.NT_UNSTRUCTURED);
            
            publicationsNode.setProperty(ScientistProfileHelper.FAVORITE_ATTRIBUTE, publication.isIsFavourite() == null ? false : publication.isIsFavourite());
            
            final String type = resolvePublicationType (publication.getObject().getTypeId().intValue());
            
            final String reportingDate = publication.getObject().getReportingDate1();
            publicationsNode.setProperty(ScientistProfileHelper.REPORTING_DATE_ATTRIBUTE, reportingDate);

            publicationsNode.setProperty(ScientistProfileHelper.TYPE_ATTRIBUTE, type);
            
            publicationsNode.setProperty(ScientistProfileHelper.LINK_ATTRIBUTE, publication.getObject().getHref());
            
            for (Record record: publication.getObject().getRecords().getRecord()) {
                for (Field field: record.getNative().getField()) {
                    switch (field.getName()) {
                        case "title":
                            publicationsNode.setProperty(ScientistProfileHelper.TITLE_ATTRIBUTE, field.getText());
                            break;
                        case "publication-date":
                            publicationsNode.setProperty(ScientistProfileHelper.PUBLICATION_DATE_ATTRIBUTE, field.getDate().getYear().longValue());
                            final BigInteger publicationMonth = field.getDate().getMonth();
                            final BigInteger publicationDay = field.getDate().getDay();
                            if ( publicationMonth != null ){
                            	publicationsNode.setProperty(ScientistProfileHelper.PUBLICATION_MONTH_ATTRIBUTE, publicationMonth.longValue());
                            }
                            if ( publicationDay != null ){
                            	publicationsNode.setProperty(ScientistProfileHelper.PUBLICATION_DAY_ATTRIBUTE, publicationDay.longValue());
                            }
                            break;
                            
                        case "finish-date":
                        	final BigInteger finishYear = field.getDate().getMonth();
                            final BigInteger finishMonth = field.getDate().getMonth();
                            final BigInteger finishDay = field.getDate().getDay();
                            if ( finishYear != null ){
                            	publicationsNode.setProperty(ScientistProfileHelper.END_YEAR_ATTRIBUTE, finishYear.longValue());
                            }
                            if ( finishMonth != null ){
                            	publicationsNode.setProperty(ScientistProfileHelper.END_MONTH_ATTRIBUTE, finishMonth.longValue());
                            }
                            if ( finishDay != null ){
                            	publicationsNode.setProperty(ScientistProfileHelper.END_DAY_ATTRIBUTE, finishDay.longValue());
                            }
                            break;
                            
                        case "start-date":
                        	final BigInteger startYear = field.getDate().getMonth();
                            final BigInteger startMonth = field.getDate().getMonth();
                            final BigInteger startDay = field.getDate().getDay();
                            if ( startYear != null ){
                            	publicationsNode.setProperty(ScientistProfileHelper.START_YEAR_ATTRIBUTE, startYear.longValue());
                            }
                            if ( startMonth != null ){
                            	publicationsNode.setProperty(ScientistProfileHelper.START_MONTH_ATTRIBUTE, startMonth.longValue());
                            }
                            if ( startDay != null ){
                            	publicationsNode.setProperty(ScientistProfileHelper.START_DAY_ATTRIBUTE, startDay.longValue());
                            }
                            break;
                            
                        case "authors":
                            final List<Person> personList = field.getPeople().getPerson();
                            
                            String[] authors = new String[personList.size()];
                            
                            for (int j = 0; j < personList.size(); j++) {
                                Person person = personList.get(j);
                                authors[j] =  person.getLastName() + " " + person.getInitials();
                            }
                            
                            publicationsNode.setProperty(ScientistProfileHelper.AUTHORS_ATTRIBUTE, authors);
                            break;
                        case "editors":
							final List<Person> editorsList = field.getPeople().getPerson();
                            
                            String[] editors = new String[editorsList.size()];
                            
                            for (int j = 0; j < editorsList.size(); j++) {
                                Person person = editorsList.get(j);
                                editors[j] =  person.getLastName() + " " + person.getInitials();
                            }
                            
                            publicationsNode.setProperty(ScientistProfileHelper.EDITORS_ATTRIBUTE, editors);
                        	break;
                        case "journal":
                        	publicationsNode.setProperty(ScientistProfileHelper.JOURNAL_NAME_ATTRIBUTE, field.getText());
                        	break;
                        case "volume":
                        	publicationsNode.setProperty(ScientistProfileHelper.VOLUME_ATTRIBUTE, field.getText());
                        	break;
                        case "issue":
                        	publicationsNode.setProperty(ScientistProfileHelper.ISSUE_ATTRIBUTE, field.getText());
                        	break;
                        case "pagination":
                        	final Pagination pagination = field.getPagination();
                        	final String startPage = pagination.getBeginPage();
                        	final String endPage = pagination.getEndPage();
                        	final BigInteger pageCount = pagination.getPageCount();
                        	if (startPage != null) {
                        		publicationsNode.setProperty(ScientistProfileHelper.START_PAGE_ATTRIBUTE, startPage);
                        	}
                        	if (endPage != null) {
                        		publicationsNode.setProperty(ScientistProfileHelper.END_PAGE_ATTRIBUTE, endPage);
                        	}
                        	if (pageCount != null) {
                        		publicationsNode.setProperty(ScientistProfileHelper.PAGE_COUNT_ATTRIBUTE, pageCount.intValue());
                        	}
                        	break;
                        case DOI_TEXT:
                        	publicationsNode.setProperty(ScientistProfileHelper.DOI_TEXT_ATTRIBUTE, field.getText());
                        	final List<Link> links = field.getLinks().getLink();
                        	for (final Link link : links) {
                        		if (DOI_TEXT.equals(link.getType())) {
                        			publicationsNode.setProperty(ScientistProfileHelper.DOI_LINK_ATTRIBUTE, link.getHref());
                        		}
                        	}
                        	break;
                        case "publisher":
                        	publicationsNode.setProperty(ScientistProfileHelper.PUBLISHER_ATTRIBUTE, field.getText());
                        	break;
                        case "parent-title":
                        	publicationsNode.setProperty(ScientistProfileHelper.BOOK_TITLE_ATTRIBUTE, field.getText());
                        	break;
                        case "place-of-publication":
                        	publicationsNode.setProperty(ScientistProfileHelper.PLACE_ATTRIBUTE, field.getText());
                        	break;
                        case "name-of-conference":
                        	publicationsNode.setProperty(ScientistProfileHelper.CONFERENCE_NAME_ATTRIBUTE, field.getText());
                        	break;
                        case "confidential":
                        	publicationsNode.setProperty(ScientistProfileHelper.CONFIDENTIAL_ATTRIBUTE, field.getText());
                        	break;
                        case "thesis-type":
                        	publicationsNode.setProperty(ScientistProfileHelper.THESIS_TYPE_ATTRIBUTE, field.getText());
                        	break;
                        case "publisher-url":
                        	publicationsNode.setProperty(ScientistProfileHelper.PUBLISHER_URL_ATTRIBUTE, field.getText());
                        	break;
                        case "location":
                        	publicationsNode.setProperty(ScientistProfileHelper.PUBLISHER_LOCATION_ATTRIBUTE, field.getText());
                        	break;
                        case "addresses":
//                        	final List<Line> lines = app.getEmployer().getLine();
//                           for (final  Line line : lines) {
//                        	List<Address> ladd = field.getAddresses().getAddress().listIterator();
//                        	pubNode.setProperty(ScientistProfileHelper.PUBLISHER_URL_ATTRIBUTE, );
                        	break;                    	
                    }
                }
            }
        }
    }
    
	/*
	 * #############################
	 * ## Professional Activities ##
	 * #############################
	 */
    
    private String resolveProfessionalActivityType (final int number) {
        switch (number) {
        	// Professional Activities Tab
	        case 32 : return ScientistProfileHelper.PROFESSIONAL_ACTIVITY_TYPE_EXTERNAL_INTERNAL_POSITION;
	        case 60 : return ScientistProfileHelper.PROFESSIONAL_ACTIVITY_TYPE_FELLOWSHIP;
        	case 31 : return ScientistProfileHelper.PROFESSIONAL_ACTIVITY_TYPE_COMMITTEES;
        	case 37 : return ScientistProfileHelper.PROFESSIONAL_ACTIVITY_TYPE_MEMBERSHIP;
        	case 56 : return ScientistProfileHelper.PROFESSIONAL_ACTIVITY_TYPE_EDITORSHIP;
        	case 76 : return ScientistProfileHelper.PROFESSIONAL_ACTIVITY_TYPE_REVIEW_REFEREE_PUBLICATION;
        	case 44 : return ScientistProfileHelper.PROFESSIONAL_ACTIVITY_TYPE_REVIEW_REFEREE_GRANT;
        	case 36 : return ScientistProfileHelper.PROFESSIONAL_ACTIVITY_TYPE_EVENT_PARTICIPATION;
        	case 33 : return ScientistProfileHelper.PROFESSIONAL_ACTIVITY_TYPE_EVENT_ADMINISTRATION;
        	
        	// Projects Tab
        	case 39 : return ScientistProfileHelper.PROFESSIONAL_ACTIVITY_TYPE_CONSULTING;
        	case 66 : return ScientistProfileHelper.PROFESSIONAL_ACTIVITY_TYPE_PARTNERSHIP;
        	case 54 : return ScientistProfileHelper.PROFESSIONAL_ACTIVITY_TYPE_FIELDWORK;
        	
        	// Impact and Outreach Tab
        	case 48 : return ScientistProfileHelper.PROFESSIONAL_ACTIVITY_TYPE_AWARD;
        	case 75 : return ScientistProfileHelper.PROFESSIONAL_ACTIVITY_TYPE_RESEARCH_PRESENTATION;
        	case 30 : return ScientistProfileHelper.PROFESSIONAL_ACTIVITY_TYPE_GUEST_PRESENTATION;
//        	case 36 : return ScientistProfileHelper.PROFESSIONAL_ACTIVITY_TYPE_EVENT_PARTICIPATION;
        	case 46 : return ScientistProfileHelper.PROFESSIONAL_ACTIVITY_TYPE_MEDIA_BROADCAST;
        	case 47 : return ScientistProfileHelper.PROFESSIONAL_ACTIVITY_TYPE_MEDIA_INTERVIEW;
	        default: return "Professional Activity";
        }
    }
    
    private void addProfessionalActivities (final Node rootNode, final ProfessionalActivities activities) throws Exception {
        int i  = 0;
        
        ListIterator<Activity> listIt = activities.getAssociated().getActivity().listIterator();
        
        while (listIt.hasNext()){
        	Activity activity = listIt.next();

            final Node professionalANode = rootNode.addNode(ScientistProfileHelper.PROFESSIONAL_ACTIVITIES_PREFIX_NODE_NAME + i++, JcrConstants.NT_UNSTRUCTURED);
            
            // Setting up type of Professional Activity
            final String type = resolveProfessionalActivityType (activity.getObject().getTypeId().intValue());  
            professionalANode.setProperty(ScientistProfileHelper.TYPE_ATTRIBUTE, type);
            
            final String reportingDate = activity.getObject().getReportingDate1();
            professionalANode.setProperty(ScientistProfileHelper.REPORTING_DATE_ATTRIBUTE, reportingDate);
            
            for (Record record: activity.getObject().getRecords().getRecord()) {
                for (Field field: record.getNative().getField()) {
                    switch (field.getName()) {
                        case "title":
                        		professionalANode.setProperty(ScientistProfileHelper.TITLE_ATTRIBUTE, field.getText());
	                            break;
                        case "url":
	                            professionalANode.setProperty(ScientistProfileHelper.URL_ATTRIBUTE, field.getText());
	                            break;
                        case "end-date":
	                        	final BigInteger endYear = field.getDate().getYear();
	                        	final BigInteger endMonth = field.getDate().getMonth();
	                        	final BigInteger endDay = field.getDate().getDay();
	                        	if ( endYear != null ){
	                        		professionalANode.setProperty(ScientistProfileHelper.END_DATE_YEAR_NAME_ATTRIBUTE, endYear.longValue());
	                        	}
	                        	if ( endMonth != null ){
	                        		professionalANode.setProperty(ScientistProfileHelper.END_DATE_MONTH_NAME_ATTRIBUTE, endMonth.longValue());
	                        	}
	                        	if ( endDay != null ){
	                        		professionalANode.setProperty(ScientistProfileHelper.END_DATE_DAY_NAME_ATTRIBUTE, endDay.longValue());
	                        	}
	                        	break;
                        	
                        case "start-date":
	                        	final BigInteger startYear = field.getDate().getYear();
	                        	final BigInteger startMonth = field.getDate().getMonth();
	                        	final BigInteger startDay = field.getDate().getDay();
	                        	if ( startYear != null ){
	                        		professionalANode.setProperty(ScientistProfileHelper.START_DATE_YEAR_NAME_ATTRIBUTE, startYear.longValue());
	                        	}
	                        	if ( startMonth != null ){
	                        		professionalANode.setProperty(ScientistProfileHelper.START_DATE_MONTH_NAME_ATTRIBUTE, startMonth.longValue());
	                        	}
	                        	if ( startDay != null ){
	                        		professionalANode.setProperty(ScientistProfileHelper.START_DATE_DAY_NAME_ATTRIBUTE, startDay.longValue());
	                        	}
	                        	break;

                        case "organisation":	
	                            final ListIterator<Address> organisationTypes = field.getAddresses().getAddress().listIterator();
	                            JSONArray jsonOrganisationsArray = new JSONArray(); 
	                            
	                            while(organisationTypes.hasNext()) {
	                            	Address address = organisationTypes.next();
	                            	List<java.lang.Object> lines = address.getContent();
	                            	
	                            	JSONObject jsonAddress = new JSONObject();
	                            	for (final java.lang.Object object : lines) {
	                            		if (! (object instanceof Line)) {
	                            			continue;
	                            		}
	                            		final Line line = (Line) object;
	                            		switch (line.getType()) {
	                            		case "name":
	                            			if(line.getContent() != null){
	                            				jsonAddress.put("name", line.getContent());
	                            			}
	                            			break;
	                            		case "organisation":
	                            			if(line.getContent() != null){
	                            				jsonAddress.put("organisation", line.getContent());
	                            			}
	                            			break;
	                            		case "city":
	                            			if(line.getContent() != null){
	                            				jsonAddress.put("city", line.getContent());
	                            			}
	                            			break;
	                            		case "country":
	                            			if(line.getContent() != null){
	                            				jsonAddress.put("country", line.getContent());
	                            			}
	                            			break;
	                            		}
	                            		
	                            	}
	                            	jsonOrganisationsArray.put(jsonAddress);
								}
	                            final JSONObject organisations = new JSONObject();
	                            organisations.put("organisations", jsonOrganisationsArray);
	                            professionalANode.setProperty(ScientistProfileHelper.ORGANISATION_ATTRIBUTE, organisations.toString());
	                            break;
                            
                        case "institution":	
	                            final ListIterator<Address> institutionTypes = field.getAddresses().getAddress().listIterator();
	                            JSONArray jsonInstitutionsArray = new JSONArray(); 
	                            
	                            while(institutionTypes.hasNext()) {
	                            	Address address = institutionTypes.next();
	                            	List<java.lang.Object> lines = address.getContent();
	                            	
	                            	JSONObject jsonAddress = new JSONObject();
	                            	for (final java.lang.Object object : lines) {
	                            		if (! (object instanceof Line)) {
	                            			continue;
	                            		}
	                            		final Line line = (Line) object;
	                            		switch (line.getType()) {
	                            		case "name":
	                            			if(line.getContent() != null){
	                            				jsonAddress.put("name", line.getContent());
	                            			}
	                            			break;
	                            		case "organisation":
	                            			if(line.getContent() != null){
	                            				jsonAddress.put("organisation", line.getContent());
	                            			}
	                            			break;
	                            		case "city":
	                            			if(line.getContent() != null){
	                            				jsonAddress.put("city", line.getContent());
	                            			}
	                            			break;
	                            		case "country":
	                            			if(line.getContent() != null){
	                            				jsonAddress.put("country", line.getContent());
	                            			}
	                            			break;
	                            		}
	                            	}
	                            	jsonInstitutionsArray.put(jsonAddress);
								}
	                            final JSONObject institutions = new JSONObject();
	                            institutions.put("organisations", jsonInstitutionsArray);
	                            professionalANode.setProperty(ScientistProfileHelper.INSTITUTION_ORGANISATIONS_ATTRIBUTE, institutions.toString());
	                            break;
                            
                        case "c-committee-roles":
	                          	final String committeeRole = field.getText();
	                          	if ( committeeRole != null ){
	                          		professionalANode.setProperty(ScientistProfileHelper.COMMITTEE_ROLE_ATTRIBUTE, committeeRole);
	                          	}
	                          	break;
	                          	
                        case "c-editorship-role":
	                        	final String editorshipRole = field.getText();
	                        	if ( editorshipRole != null ){
	                        		professionalANode.setProperty(ScientistProfileHelper.EDITORSHIP_ROLE_ATTRIBUTE, editorshipRole);
	                        	}
	                        	break;
	                        	
                        case "c-text1":
	                        	final String publisher = field.getText();
	                        	if ( publisher != null ){
	                        		professionalANode.setProperty(ScientistProfileHelper.C_TEXT_1_ATTRIBUTE, publisher);
	                        	}
	                        	break;

                        case "c-administrative-role":
	                        	final String administrativeRole = field.getText();
	                        	if ( administrativeRole != null ){
	                        		professionalANode.setProperty(ScientistProfileHelper.ADMINISTRATIVE_ROLE_ATTRIBUTE, administrativeRole);
	                        	}
	                        	break;  
	                        	
                        case "c-event-role":                       	
	                        	final List<String> rolesList = field.getItems().getItem();
	                        	String[] participationRoles = new String[rolesList.size()];
                        		for (int j = 0; j < rolesList.size(); j++){
                        			participationRoles[j] = rolesList.get(j);
                        		}
                        		professionalANode.setProperty(ScientistProfileHelper.PARTICIPATION_ROLES_ATTRIBUTE, participationRoles);
	                        	break;  
	                    
                        case "c-event-type":
	                          	final String eventType = field.getText();
	                          	if ( eventType != null ){
	                          		professionalANode.setProperty(ScientistProfileHelper.EVENT_TYPE_ATTRIBUTE, eventType);
	                          	}
	                          	break;
	                          	
                        case "c-internal-or-external":
	                          	final String internalOrExternalPosition = field.getText();
	                          	if ( internalOrExternalPosition != null ){
	                          		professionalANode.setProperty(ScientistProfileHelper.INTERNAL_OR_EXTERNAL_ATTRIBUTE, internalOrExternalPosition);
	                          	}
	                          	break;

                        case "c-office-held-type":
	                        	final String officeHeldType = field.getText();
	                        	if ( officeHeldType != null ){
	                        		professionalANode.setProperty(ScientistProfileHelper.OFFICE_HELD_TYPE_ATTRIBUTE, officeHeldType);
	                        	}
	                        	break;
                        	
                        case "c-other-office-held-types":
	                        	final String officeOtherHeldType = field.getText();
	                        	if ( officeOtherHeldType != null ){
	                        		professionalANode.setProperty(ScientistProfileHelper.OFFICE_OTHER_HELD_TYPE_ATTRIBUTE, officeOtherHeldType);
	                        	}
	                        	break;

                        case "c-publication-type":
	                          	final String publicationType = field.getText();
	                          	if ( publicationType != null ){
	                          		professionalANode.setProperty(ScientistProfileHelper.PUBLICATION_TYPE_ATTRIBUTE, publicationType);
	                          	}
	                          	break;
	                          	
                        case "c-review-type":
	                          	final String reviewType = field.getText();
	                          	if ( reviewType != null ){
	                          		professionalANode.setProperty(ScientistProfileHelper.REVIEW_TYPE_ATTRIBUTE, reviewType);
	                          	}
	                          	break;
	                          	
                        case "c-society-or-membership-role":
	                        	final String societyMembershipRole = field.getText();
	                        	if ( societyMembershipRole != null ){
	                        		professionalANode.setProperty(ScientistProfileHelper.MEMBERSHIP_ROLE_ATTRIBUTE, societyMembershipRole);
	                        	}
	                        	break;

                        case "department":
	                          	final String department = field.getText();
	                          	if ( department != null ){
	                          		professionalANode.setProperty(ScientistProfileHelper.DEPARTMENT_ATTRIBUTE, department);
	                          	}
	                          	break;
	                          	
                        case "c-area-or-region":
	                          	final String areaOrRegion = field.getText();
	                          	if ( areaOrRegion != null ){
	                          		professionalANode.setProperty(ScientistProfileHelper.DEPARTMENT_ATTRIBUTE, areaOrRegion);
	                          	}
	                          	break;
	                          	
                        case "event-name":
	                          	final String eventName = field.getText();
	                          	if ( eventName != null ){
	                          		professionalANode.setProperty(ScientistProfileHelper.EVENT_NAME_ATTRIBUTE, eventName);
	                          	}
	                          	break;
	                          	
                        case "invited":
	                          	final String invited = field.getText();
	                          	if ( invited != null ){
	                          		professionalANode.setProperty(ScientistProfileHelper.INVITED_ATTRIBUTE, invited);
	                          	}
	                          	break;
	                          	
                        case "keynote":
	                          	final String keynote = field.getText();
	                          	if ( keynote != null ){
	                          		professionalANode.setProperty(ScientistProfileHelper.KEYNOTE_ATTRIBUTE, keynote);
	                          	}
	                          	break;	        
	                          	
                        case "description":
	                          	final String description = field.getText();
	                          	if ( description != null ){
	                          		professionalANode.setProperty(ScientistProfileHelper.DESCRIPTION_ATTRIBUTE, description);
	                          	}
	                          	break;	
	                          	
                        case "c-interviewer-name":
								if (field.getPeople() != null) {
									final List<Person> interviewersList = field.getPeople().getPerson();
									
									String[] interviewers = new String[interviewersList.size()];
									
									for (int j = 0; j < interviewersList.size(); j++) {
										Person person = interviewersList.get(j);
										interviewers[j] =  person.getLastName() + " " + person.getInitials();
									}
									professionalANode.setProperty(ScientistProfileHelper.INTERVIEWER_NAME_ATTRIBUTE, interviewers);
								}
								break;
                    }
                }
            }
        }
    }
    
	
	/*
	 * ##################
	 * #### Projects ####
	 * ##################
	 */

    private void addProjects (final Node rootNode, final Projects projects) throws Exception {
		int i = 0;
		
		if(projects.getChampionOf().getProjects().listIterator() != null){
			i = projectNodeTypeAssign(i, rootNode, projects.getChampionOf().getProjects().listIterator(), ScientistProfileHelper.PROJECT_NODETYPE_CHAMPION);
		}
		if(projects.getResearcherOn().getProjects().listIterator() != null){
			i = projectNodeTypeAssign(i, rootNode, projects.getResearcherOn().getProjects().listIterator(), ScientistProfileHelper.PROJECT_NODETYPE_RESEARCHER);
		}
		if(projects.getManagerOf().getProjects().listIterator() != null){
			i = projectNodeTypeAssign(i, rootNode, projects.getManagerOf().getProjects().listIterator(), ScientistProfileHelper.PROJECT_NODETYPE_MANAGER);
		}
		if(projects.getMemberOf().getProjects().listIterator() != null){
			i = projectNodeTypeAssign(i, rootNode, projects.getMemberOf().getProjects().listIterator(), ScientistProfileHelper.PROJECT_NODETYPE_MEMBER);
		}
		if(projects.getLeaderOf().getProjects().listIterator() != null){
			i = projectNodeTypeAssign(i, rootNode, projects.getLeaderOf().getProjects().listIterator(), ScientistProfileHelper.PROJECT_NODETYPE_LEADER);
		}
		if(projects.getFundedBy().getProjects().listIterator() != null){
			i = projectNodeTypeAssign(i, rootNode, projects.getFundedBy().getProjects().listIterator(), ScientistProfileHelper.PROJECT_NODETYPE_FUNDEDBY);
		}
    }

	private int projectNodeTypeAssign(int i, final Node rootNode, ListIterator<Project> projects, String nodeType) throws Exception {

		while (projects.hasNext()){
			uk.ac.nhm.nhm_www.core.impl.workflows.science.generated.WebProfile.Projects.ChampionOf.Project project = projects.next();

			final Node projectsNode = rootNode.addNode(ScientistProfileHelper.PROJECTS_PREFIX_NODE_NAME + i++, JcrConstants.NT_UNSTRUCTURED);

			projectsNode.setProperty(ScientistProfileHelper.PROJECT_NODE_TYPE, nodeType);
			final String type = ScientistProfileHelper.PROJECT_TYPE_PROJECT;
			projectsNode.setProperty(ScientistProfileHelper.TYPE_ATTRIBUTE, type);

            final String reportingDate = project.getObject().getReportingDate1();
            projectsNode.setProperty(ScientistProfileHelper.REPORTING_DATE_ATTRIBUTE, reportingDate);

			for (Record record: project.getObject().getRecords().getRecord()) {
				for (Field field: record.getNative().getField()) {
					switch (field.getName()) {
					case "name":
							projectsNode.setProperty(ScientistProfileHelper.NAME_ATTRIBUTE, field.getText());
							break;

					case "c-external-collaborators":
							final ListIterator<Address> externalCollaborators = field.getAddresses().getAddress().listIterator();
							JSONArray jsonInstitutionsArray = new JSONArray(); 

							while(externalCollaborators.hasNext()) {
								Address address = externalCollaborators.next();
								List<java.lang.Object> lines = address.getContent();

								JSONObject jsonAddress = new JSONObject();
								for (final java.lang.Object object : lines) {
									if (! (object instanceof Line)) {
										continue;
									}
									final Line line = (Line) object;
									switch (line.getType()) {
									case "name":
										if(line.getContent() != null){
											jsonAddress.put("name", line.getContent());
										}
										break;
									case "organisation":
										if(line.getContent() != null){
											jsonAddress.put("organisation", line.getContent());
										}
										break;
									case "city":
										if(line.getContent() != null){
											jsonAddress.put("city", line.getContent());
										}
										break;
									case "country":
										if(line.getContent() != null){
											jsonAddress.put("country", line.getContent());
										}
										break;
									}
								}
								jsonInstitutionsArray.put(jsonAddress);
							}
							final JSONObject collaborators = new JSONObject();
							collaborators.put("collaborators", jsonInstitutionsArray);
							projectsNode.setProperty(ScientistProfileHelper.EXTERNAL_COLLABORATORS, collaborators.toString());
							break;

					case "c-end-date":
							final BigInteger endYear = field.getDate().getYear();
							final BigInteger endMonth = field.getDate().getMonth();
							final BigInteger endDay = field.getDate().getDay();
							if ( endYear != null ){
								projectsNode.setProperty(ScientistProfileHelper.END_DATE_YEAR_NAME_ATTRIBUTE, endYear.longValue());
							}
							if ( endMonth != null ){
								projectsNode.setProperty(ScientistProfileHelper.END_DATE_MONTH_NAME_ATTRIBUTE, endMonth.longValue());
							}
							if ( endDay != null ){
								projectsNode.setProperty(ScientistProfileHelper.END_DATE_DAY_NAME_ATTRIBUTE, endDay.longValue());
							}
							break;

					case "c-start-date":
							final BigInteger startYear = field.getDate().getYear();
							final BigInteger startMonth = field.getDate().getMonth();
							final BigInteger startDay = field.getDate().getDay();
							if ( startYear != null ){
								projectsNode.setProperty(ScientistProfileHelper.START_DATE_YEAR_NAME_ATTRIBUTE, startYear.longValue());
							}
							if ( startMonth != null ){
								projectsNode.setProperty(ScientistProfileHelper.START_DATE_MONTH_NAME_ATTRIBUTE, startMonth.longValue());
							}
							if ( startDay != null ){
								projectsNode.setProperty(ScientistProfileHelper.START_DATE_DAY_NAME_ATTRIBUTE, startDay.longValue());
							}
							break;

					case "c-funding-source2":
							final List<String> fundingSources = field.getItems().getItem();
							projectsNode.setProperty(ScientistProfileHelper.FUNDING_SOURCE_ATTRIBUTE, fundingSources.toArray(new String[fundingSources.size()]));
							break;

					case "c-nhm-url1":
							projectsNode.setProperty(ScientistProfileHelper.NHM_URL, field.getText());
							break;
					}
				}
			}
		}
		return i;
	}
    
	
	/*
	 * ################
	 * #### Grants ####
	 * ################
	 */

    private void addGrants (final Node rootNode, final Grants grants, String uniqueName) throws Exception{
    	int i = 0;

//    	LOG.error("Scanning: " + uniqueName );

		List<Ns1Object> allGrants = new ArrayList<Ns1Object>();

		if(grants != null){
			if(grants.getPrimaryInvestigator() != null){
				if (grants.getPrimaryInvestigator().getGrant() != null){
//					LOG.error("Found Primary");	
					for (Ns1Object ns1Object : grants.getPrimaryInvestigator().getGrant()) {
//						LOG.error("Adding a PrimaryInvestigator future Node!");
						allGrants.add(ns1Object);
					}
				}
			}

			if(grants.getSecondaryInvestigator() != null){
				if (grants.getSecondaryInvestigator().getGrant() != null){
//					LOG.error("Found Secondary");	
					for (Ns1Object ns1Object : grants.getSecondaryInvestigator().getGrant()) {
//						LOG.error("Adding a SecondaryInvestigator future Node!!");
						allGrants.add(ns1Object);
					}
				}
			}

			if(grants.getFundedBy() != null){
				if (grants.getFundedBy().getGrant() != null){
//					LOG.error("Found FundedBy");	
					for (Ns1Object ns1Object : grants.getFundedBy().getGrant()) {
//						LOG.error("Adding a Funded By future Node!!");
						allGrants.add(ns1Object);
					}
				}
			}
		}

		for (Ns1Object grant : allGrants) {
				final Node grantsNode = rootNode.addNode(ScientistProfileHelper.GRANT_PREFIX_NODE_NAME + i++, JcrConstants.NT_UNSTRUCTURED);

				final String type = ScientistProfileHelper.GRANT_TYPE_GRANT;
				grantsNode.setProperty(ScientistProfileHelper.TYPE_ATTRIBUTE, type);

	            final String reportingDate = grant.getObject().getReportingDate1();
	            grantsNode.setProperty(ScientistProfileHelper.REPORTING_DATE_ATTRIBUTE, reportingDate);

				for (Record record: grant.getObject().getRecords().getRecord()) {
					for (Field field: record.getNative().getField()) {
						switch (field.getName()) {

						case "c-proposal-title":
							if (field.getText() != null) {
								final String proposalTitle = field.getText().toString();
								grantsNode.setProperty(ScientistProfileHelper.PROPOSAL_TITLE, proposalTitle);
							}
							break;

						case "c-role":
							if (field.getText() != null) {
								final String role = field.getText().toString();
								grantsNode.setProperty(ScientistProfileHelper.ROLE, role);
							}
							break;

						case "c-role-principal-investigator":
							if (field.getPeople() != null) {
								final List<Person> principalPersonList = field.getPeople().getPerson();
								
								String[] principals = new String[principalPersonList.size()];
								
								for (int j = 0; j < principalPersonList.size(); j++) {
									Person person = principalPersonList.get(j);
									principals[j] =  person.getLastName() + " " + person.getInitials();
								}
								grantsNode.setProperty(ScientistProfileHelper.ROLE_PRINCIPAL_INVESTIGATOR, principals);
							}
							break;

						case "c-role-co-investigator":
							if (field.getPeople() != null ) {
								final List<Person> coInvestigatorPersonList = field.getPeople().getPerson();

								String[] coInvestigators = new String[coInvestigatorPersonList.size()];

								for (int j = 0; j < coInvestigatorPersonList.size(); j++) {
									Person person = coInvestigatorPersonList.get(j);
									coInvestigators[j] =  person.getLastName() + " " + person.getInitials();
								}

								grantsNode.setProperty(ScientistProfileHelper.ROLE_CO_INVESTIGATOR, coInvestigators);
							}
                            break;
							
						case "c-funder-name":					//if "Other" >> use c-funder-name-other.getText() instead
							if (field.getText() != null ) {
								grantsNode.setProperty(ScientistProfileHelper.FUNDER_NAME, field.getText());
							}
							break;

						case "c-funder-name-other":
							if (field.getText() != null ) {
								grantsNode.setProperty(ScientistProfileHelper.FUNDER_NAME_OTHER, field.getText());
							}
							break;

						case "c-total-value-awarded":
//							if ( uniqueName.equals("chris-stringer")){
//								LOG.error("Grant value in String is : >>" + field.getMoney().getValue().toString() + "<< longValue is : >>" + field.getMoney().getValue().longValue());
//							}
							if ( field.getMoney() != null ) {
								if ( field.getMoney().getValue() != null )	{
									grantsNode.setProperty(ScientistProfileHelper.TOTAL_VALUE_AWARDED, field.getMoney().getValue().toString());
								}
							}
							break;

						case "c-value-to-nhm-awarded":
//							if ( uniqueName.equals("chris-stringer")){
//								LOG.error("Grant value in String is : >>" + field.getMoney().getValue().toString() + "<< longValue is : >>" + field.getMoney().getValue().longValue());
//							}
							if ( field.getMoney() != null ) {
								if ( field.getMoney().getValue() != null )	{
									grantsNode.setProperty(ScientistProfileHelper.NHM_VALUE_AWARDED, field.getMoney().getValue().toString());
								}
							}
							break;

						case "c-end-date":
							final BigInteger endYear = field.getDate().getYear();
							final BigInteger endMonth = field.getDate().getMonth();
							final BigInteger endDay = field.getDate().getDay();
							if ( endYear != null ){
								grantsNode.setProperty(ScientistProfileHelper.END_DATE_YEAR_NAME_ATTRIBUTE, endYear.longValue());
							}
							if ( endMonth != null ){
								grantsNode.setProperty(ScientistProfileHelper.END_DATE_MONTH_NAME_ATTRIBUTE, endMonth.longValue());
							}
							if ( endDay != null ){
								grantsNode.setProperty(ScientistProfileHelper.END_DATE_DAY_NAME_ATTRIBUTE, endDay.longValue());
							}
							break;

						case "c-start-date":
							final BigInteger startYear = field.getDate().getYear();
							final BigInteger startMonth = field.getDate().getMonth();
							final BigInteger startDay = field.getDate().getDay();
							if ( startYear != null ){
								grantsNode.setProperty(ScientistProfileHelper.START_DATE_YEAR_NAME_ATTRIBUTE, startYear.longValue());
							}
							if ( startMonth != null ){
								grantsNode.setProperty(ScientistProfileHelper.START_DATE_MONTH_NAME_ATTRIBUTE, startMonth.longValue());
							}
							if ( startDay != null ){
								grantsNode.setProperty(ScientistProfileHelper.START_DATE_DAY_NAME_ATTRIBUTE, startDay.longValue());
							}
							break;
						}
					}
				}
		}
    }


	/*
	 * #########################
	 * ## Teaching Activities ##
	 * #########################
	 */

    private String resolveTeachingActivityType (final int number) {
        switch (number) {
        	// Teaching Activities Tab
        	case 69 : return ScientistProfileHelper.TEACHING_ACTIVITIES_TYPE_SUPERVISION;
	        case 23 : return ScientistProfileHelper.TEACHING_ACTIVITIES_TYPE_TAUGHT_COURSES;
	        case 27 : return ScientistProfileHelper.TEACHING_ACTIVITIES_TYPE_EXAMINER;
	        case 25 : return ScientistProfileHelper.TEACHING_ACTIVITIES_TYPE_PROGRAM_DEVELOPED;
	        case 24 : return ScientistProfileHelper.TEACHING_ACTIVITIES_TYPE_COURSES_DEVELOPED;

	        default: return "Teaching Activity";
        }
    }

    private void addTeachingActivities (final Node rootNode, final TeachingActivities activities, final String uniqueName) throws Exception {
        int i  = 0;
        
    	LOG.error("Scanning: " + uniqueName );
    	
//    	if ( uniqueName.equals("chris-stringer")){ 
//    		String warnMe = ">>>>>>>>>>>>>############beWarned############<<<<<<<<<<<<";
//    		LOG.error(warnMe);
//    	}
        
        List<Ns1Object> list = activities.getAssociated().getTeachingActivity();

        ListIterator<Ns1Object> listIt = list.listIterator();
        while (listIt.hasNext()){
        	Ns1Object activity = listIt.next();

            final Node teachingANode = rootNode.addNode(ScientistProfileHelper.TEACHING_ACTIVITIES_PREFIX_NODE_NAME + i++, JcrConstants.NT_UNSTRUCTURED);

            // Setting up type of Teaching Activity
            final String type = resolveTeachingActivityType (activity.getObject().getTypeId().intValue());  
            teachingANode.setProperty(ScientistProfileHelper.TYPE_ATTRIBUTE, type);
//        	LOG.error("Adding a teachingactivityType : " + type);

            for (Record record: activity.getObject().getRecords().getRecord()) {
                for (Field field: record.getNative().getField()) {
                    switch (field.getName()) {
                        case "title":
                        		teachingANode.setProperty(ScientistProfileHelper.TITLE_ATTRIBUTE, field.getText());
	                            break;
                        case "url":
	                            teachingANode.setProperty(ScientistProfileHelper.URL_ATTRIBUTE, field.getText());
	                            break;
                        case "end-date":
	                        	final BigInteger endYear = field.getDate().getYear();
	                        	final BigInteger endMonth = field.getDate().getMonth();
	                        	final BigInteger endDay = field.getDate().getDay();
	                        	if ( endYear != null ){
	                        		teachingANode.setProperty(ScientistProfileHelper.END_DATE_YEAR_NAME_ATTRIBUTE, endYear.longValue());
	                        	}
	                        	if ( endMonth != null ){
	                        		teachingANode.setProperty(ScientistProfileHelper.END_DATE_MONTH_NAME_ATTRIBUTE, endMonth.longValue());
	                        	}
	                        	if ( endDay != null ){
	                        		teachingANode.setProperty(ScientistProfileHelper.END_DATE_DAY_NAME_ATTRIBUTE, endDay.longValue());
	                        	}
	                        	break;

                        case "start-date":
	                        	final BigInteger startYear = field.getDate().getYear();
	                        	final BigInteger startMonth = field.getDate().getMonth();
	                        	final BigInteger startDay = field.getDate().getDay();
	                        	if ( startYear != null ){
	                        		teachingANode.setProperty(ScientistProfileHelper.START_DATE_YEAR_NAME_ATTRIBUTE, startYear.longValue());
	                        	}
	                        	if ( startMonth != null ){
	                        		teachingANode.setProperty(ScientistProfileHelper.START_DATE_MONTH_NAME_ATTRIBUTE, startMonth.longValue());
	                        	}
	                        	if ( startDay != null ){
	                        		teachingANode.setProperty(ScientistProfileHelper.START_DATE_DAY_NAME_ATTRIBUTE, startDay.longValue());
	                        	}
	                        	break;

                        case "organisation":
	                            final ListIterator<Address> organisationTypes = field.getAddresses().getAddress().listIterator();
	                            JSONArray jsonOrganisationsArray = new JSONArray(); 

	                            while(organisationTypes.hasNext()) {
	                            	Address address = organisationTypes.next();
	                            	List<java.lang.Object> lines = address.getContent();

	                            	JSONObject jsonAddress = new JSONObject();
	                            	for (final java.lang.Object object : lines) {
	                            		if (! (object instanceof Line)) {
	                            			continue;
	                            		}
	                            		final Line line = (Line) object;
	                            		switch (line.getType()) {
	                            		case "name":
	                            			if(line.getContent() != null){
	                            				jsonAddress.put("name", line.getContent());
	                            			}
	                            			break;
	                            		case "organisation":
	                            			if(line.getContent() != null){
	                            				jsonAddress.put("organisation", line.getContent());
	                            			}
	                            			break;
	                            		case "city":
	                            			if(line.getContent() != null){
	                            				jsonAddress.put("city", line.getContent());
	                            			}
	                            			break;
	                            		case "country":
	                            			if(line.getContent() != null){
	                            				jsonAddress.put("country", line.getContent());
	                            			}
	                            			break;
	                            		}
	                            	}
	                            	jsonOrganisationsArray.put(jsonAddress);
								}
	                            final JSONObject organisations = new JSONObject();
	                            organisations.put("organisations", jsonOrganisationsArray);
	                            teachingANode.setProperty(ScientistProfileHelper.ORGANISATION_ATTRIBUTE, organisations.toString());
	                            break;

                        case "institution":	
	                            final ListIterator<Address> institutionTypes = field.getAddresses().getAddress().listIterator();
	                            JSONArray jsonInstitutionsArray = new JSONArray(); 

	                            while(institutionTypes.hasNext()) {
	                            	Address address = institutionTypes.next();
	                            	List<java.lang.Object> lines = address.getContent();

	                            	JSONObject jsonAddress = new JSONObject();
	                            	for (final java.lang.Object object : lines) {
	                            		if (! (object instanceof Line)) {
	                            			continue;
	                            		}
	                            		final Line line = (Line) object;
	                            		switch (line.getType()) {
	                            		case "name":
	                            			if(line.getContent() != null){
	                            				jsonAddress.put("name", line.getContent());
	                            			}
	                            			break;
	                            		case "organisation":
	                            			if(line.getContent() != null){
	                            				jsonAddress.put("organisation", line.getContent());
	                            			}
	                            			break;
	                            		case "city":
	                            			if(line.getContent() != null){
	                            				jsonAddress.put("city", line.getContent());
	                            			}
	                            			break;
	                            		case "country":
	                            			if(line.getContent() != null){
	                            				jsonAddress.put("country", line.getContent());
	                            			}
	                            			break;
	                            		}
	                            	}
	                            	jsonInstitutionsArray.put(jsonAddress);
								}
	                            final JSONObject institutions = new JSONObject();
	                            institutions.put("organisations", jsonInstitutionsArray);
	                            teachingANode.setProperty(ScientistProfileHelper.INSTITUTION_ORGANISATIONS_ATTRIBUTE, institutions.toString());
	                            break;

                        case "c-course-level":
	                          	final String committeeRole = field.getText();
	                          	if ( committeeRole != null ){
	                          		teachingANode.setProperty(ScientistProfileHelper.COURSE_LEVEL_ATTRIBUTE, committeeRole);
	                          	}
	                          	break;

                        case "c-degree-type":
	                        	final String degreeType = field.getText();
	                        	if ( degreeType != null ){
	                        		teachingANode.setProperty(ScientistProfileHelper.DEGREE_TYPE_ATTRIBUTE, degreeType);
	                        	}
	                        	break;

                        case "c-other-degree-type":
	                        	final String otherDegreeType = field.getText();
	                        	if ( otherDegreeType != null ){
	                        		teachingANode.setProperty(ScientistProfileHelper.OTHER_DEGREE_TYPE_ATTRIBUTE, otherDegreeType);
	                        	}
	                        	break;

                        case "c-supervisory-role":
	                        	final String supervisoryRole = field.getText();
	                        	if ( supervisoryRole != null ){
	                        		teachingANode.setProperty(ScientistProfileHelper.SUPERVISORY_ROLE_ATTRIBUTE, supervisoryRole);
	                        	}
	                        	break;

                        case "person":
	                        	final Person person = field.getPerson();
	                        	if ( person != null ){
	                        		String personString = person.getLastName() + " " + person.getInitials();
	                        		teachingANode.setProperty(ScientistProfileHelper.PERSON_ATTRIBUTE, personString);
	                        	}
	                        	break;  

						case "co-contributors":
								if (field.getPeople() != null) {
									final List<Person> coContributorsPersonList = field.getPeople().getPerson();
									
									String[] coContributors = new String[coContributorsPersonList.size()];
									
									for (int j = 0; j < coContributorsPersonList.size(); j++) {
										Person coContributor = coContributorsPersonList.get(j);
										coContributors[j] =  coContributor.getLastName() + " " + coContributor.getInitials();
									}
									teachingANode.setProperty(ScientistProfileHelper.CO_CONTRIBUTORS_ATTRIBUTE, coContributors);
								}
								break;

                        case "c-degree-subject":                       	
	                        	final String degreeSubject = field.getText();
	                        	if ( degreeSubject != null ){
	                        		teachingANode.setProperty(ScientistProfileHelper.DEGREE_SUBJECT_ATTRIBUTE, degreeSubject);
	                        	}
	                        	break; 

                        case "c-funder":
	                        	final String funder = field.getText();
	                        	if ( funder != null ){
	                        		teachingANode.setProperty(ScientistProfileHelper.FUNDER_ATTRIBUTE, funder);
	                        	}
	                        	break;

                        case "examination-role":
	                        	final String examinationRole = field.getText();
	                        	if ( examinationRole != null ){
	                        		teachingANode.setProperty(ScientistProfileHelper.EXAMINATION_ROLE_ATTRIBUTE, examinationRole);
	                        	}
	                        	break;

                        case "c-examination-role":
	                        	final String cExaminationRole = field.getText();
	                        	if ( cExaminationRole != null ){
	                        		teachingANode.setProperty(ScientistProfileHelper.EXAMINATION_ROLE_ATTRIBUTE, cExaminationRole);
	                        	}
	                        	break;

                        case "c-examination-level":
	                        	final String examinationLevel = field.getText();
	                        	if ( examinationLevel != null ){
	                        		teachingANode.setProperty(ScientistProfileHelper.EXAMINATION_LEVEL_ATTRIBUTE, examinationLevel);
	                        	}
	                        	break;

                        case "degree-level":
	                        	final String degreeLevel = field.getText();
	                        	if ( degreeLevel != null ){
	                        		teachingANode.setProperty(ScientistProfileHelper.DEGREE_LEVEL_ATTRIBUTE, degreeLevel);
	                        	}
	                        	break;

                        case "degree-type":
	                        	final String programDegreeType = field.getText();
	                        	if ( programDegreeType != null ){
	                        		teachingANode.setProperty(ScientistProfileHelper.DEGREE_TYPE_ATTRIBUTE, programDegreeType);
	                        	}
	                        	break;

                        case "partners":	
	                            final ListIterator<Address> partnersList = field.getAddresses().getAddress().listIterator();
	                            JSONArray jsonPartnersArray = new JSONArray(); 

	                            while(partnersList.hasNext()) {
	                            	Address address = partnersList.next();
	                            	List<java.lang.Object> lines = address.getContent();

	                            	JSONObject jsonAddress = new JSONObject();
	                            	for (final java.lang.Object object : lines) {
	                            		if (! (object instanceof Line)) {
	                            			continue;
	                            		}
	                            		final Line line = (Line) object;
	                            		switch (line.getType()) {
	                            		case "name":
	                            			if(line.getContent() != null){
	                            				jsonAddress.put("name", line.getContent());
	                            			}
	                            			break;
	                            		case "organisation":
	                            			if(line.getContent() != null){
	                            				jsonAddress.put("organisation", line.getContent());
	                            			}
	                            			break;
	                            		case "city":
	                            			if(line.getContent() != null){
	                            				jsonAddress.put("city", line.getContent());
	                            			}
	                            			break;
	                            		case "country":
	                            			if(line.getContent() != null){
	                            				jsonAddress.put("country", line.getContent());
	                            			}
	                            			break;
	                            		}
	                            	}
	                            	jsonPartnersArray.put(jsonAddress);
								}
	                            final JSONObject partners = new JSONObject();
	                            partners.put("partners", jsonPartnersArray);
	                            teachingANode.setProperty(ScientistProfileHelper.PARTNER_ATTRIBUTE, partners.toString());
	                            break;

                        case "release-date":
	                        	final BigInteger releaseYear = field.getDate().getYear();
	                        	final BigInteger releaseMonth = field.getDate().getMonth();
	                        	final BigInteger releaseDay = field.getDate().getDay();
	                        	if ( releaseYear != null ){
	                        		teachingANode.setProperty(ScientistProfileHelper.RELEASE_DATE_YEAR_NAME_ATTRIBUTE, releaseYear.longValue());
	                        	}
	                        	if ( releaseMonth != null ){
	                        		teachingANode.setProperty(ScientistProfileHelper.RELEASE_DATE_MONTH_NAME_ATTRIBUTE, releaseMonth.longValue());
	                        	}
	                        	if ( releaseDay != null ){
	                        		teachingANode.setProperty(ScientistProfileHelper.RELEASE_DATE_DAY_NAME_ATTRIBUTE, releaseDay.longValue());
	                        	}
	                        	break;

                    }
                }
            }
        }
    }

    private void processFile (final WebProfile webProfile, final String imagePath) throws Exception {
        //Get the contentPath node in the JCR
        Node rootNode = session.getNode(contentPath);

        // ############################
        // ## Getting the WebProfile ##
        // ############################
        final Ns1Object profile = webProfile.getProfile();

        // ############################
        // ## Building the Node Name ##
        // ############################
        String firstNameOutput = "";
        //We need this information for our node's name of the page.
        if(profile.getObject().getKnownAs() != null && !profile.getObject().getKnownAs().equals("")){
        	firstNameOutput = profile.getObject().getKnownAs();
        } else {
        	firstNameOutput = profile.getObject().getFirstName();
        }

        // Node Name
        final String uniqueName = firstNameOutput.toLowerCase() + "-" + profile.getObject().getLastName().toLowerCase(); 

        // ####################
        // ## Page Structure ##
        // ####################
        // Setting up nodes:
        //
        //      	andy-purvis 											(cq:Page)
        //      		`-- jcr:content										(cq:PageContent)
        //      				|-- personalInformation						(nt:unstructured)
        //      				|		|-- websites						(nt:unstructured)
        //      				|		 `- phonenumbers					(nt:unstructured)
        //      				|-- department								(nt:unstructured)
        //      				|-- professional							(nt:unstructured)
        //      				|		|-- academicAppointments			(nt:unstructured)
        //      				|		|-- degrees							(nt:unstructured)
        //      				|		 `- nonAcademicAppointments			(nt:unstructured)
        //      				|-- professionalActivities					(nt:unstructured)
        //      				|		 `- associated						(nt:unstructured)
        //      				|-- teachingActivities						(nt:unstructured)
        //      				|		 `- associated						(nt:unstructured)
        //      				|-- projects								(nt:unstructured)
        //      				|		 `- container						(nt:unstructured)
        //      				|-- grants									(nt:unstructured)
        //      				|		 `- container						(nt:unstructured)
        //      				|-- publications							(nt:unstructured)
        //      				|		 `- authored						(nt:unstructured)
        //      				 `- image									(nt:unstructured)
        //

        // Node : e.g: andy-purvis
        final Node profileNode = rootNode.addNode(uniqueName, "cq:Page");

        // Node : jcr:content
        final Node jcrContentNode = profileNode.addNode(JcrConstants.JCR_CONTENT, "cq:PageContent");
        jcrContentNode.setProperty("sling:resourceType", SCIENCE_PROFILE_PAGE_RESOURCE_TYPE);
        jcrContentNode.setProperty(JcrConstants.JCR_TITLE, firstNameOutput + " " + profile.getObject().getLastName());

        // Node : personalInformation
        final Node personalInfo = jcrContentNode.addNode(ScientistProfileHelper.PERSONAL_INFORMATION_NODE_NAME, JcrConstants.NT_UNSTRUCTURED);
        setNodeProfile (personalInfo, profile);
        final ProfessionalActivities activities = webProfile.getProfessionalActivities();
        if (activities != null) {
        	final List<Activity> activitiesList = activities.getAssociated().getActivity();
        	for (final Activity activity : activitiesList) {
        		if (activity.getObject().getTypeId().intValue() == 51) {
        			addSpecialismsProperties(personalInfo, activity);
        		}
        	}
        }

        // Node : department
        final Node department = jcrContentNode.addNode(ScientistProfileHelper.DEPARTAMENT_INFORMATION_NODE_NAME, JcrConstants.NT_UNSTRUCTURED);
        setDepartment (department, profile);  

        // Node : professional
        processRecords (jcrContentNode, profile.getObject().getRecords().getRecord());

        // Node : professionalActivities
        final Node professionalActivities = jcrContentNode.addNode(ScientistProfileHelper.PROFESSIONAL_ACTIVITIES_NODE_NAME, JcrConstants.NT_UNSTRUCTURED);
        final Node associated = professionalActivities.addNode(ScientistProfileHelper.ASSOCIATED_PROFESSIONAL_ACTIVITIES_NODE_NAME, JcrConstants.NT_UNSTRUCTURED);
        addProfessionalActivities(associated, activities);  

        // Node : teachingActivities
        final Node teachingActivities = jcrContentNode.addNode(ScientistProfileHelper.TEACHING_ACTIVITIES_NODE_NAME, JcrConstants.NT_UNSTRUCTURED);
        final Node teachingAssociated = teachingActivities.addNode(ScientistProfileHelper.ASSOCIATED_TEACHING_ACTIVITIES_NODE_NAME, JcrConstants.NT_UNSTRUCTURED);
        addTeachingActivities(teachingAssociated, webProfile.getTeachingActivities(), uniqueName);

        // Node : projects
        final Node projectsNode = jcrContentNode.addNode(ScientistProfileHelper.PROJECTS_NODE_NAME, JcrConstants.NT_UNSTRUCTURED);
        final Node projects = projectsNode.addNode(ScientistProfileHelper.PROJECTS_CONTAINER_NODE_NAME, JcrConstants.NT_UNSTRUCTURED);
        addProjects(projects, webProfile.getProjects());

        // Node : grants
        final Node grantsNode = jcrContentNode.addNode(ScientistProfileHelper.GRANT_NODE_NAME, JcrConstants.NT_UNSTRUCTURED);
        final Node grants = grantsNode.addNode(ScientistProfileHelper.GRANT_CONTAINER_NODE_NAME, JcrConstants.NT_UNSTRUCTURED);
        addGrants(grants, webProfile.getGrants(), uniqueName);

        // Node : publications
        final Node publications = jcrContentNode.addNode(ScientistProfileHelper.PUBLICATIONS_NODE_NAME, JcrConstants.NT_UNSTRUCTURED);
        final Node authored = publications.addNode(ScientistProfileHelper.AUTHORED_PUBLICATIONS_NODE_NAME, JcrConstants.NT_UNSTRUCTURED);
        addPublication (authored, webProfile.getPublications().getAuthored().getPublication());

        // Node : image
        final Node imageNode = jcrContentNode.addNode("image");
        if (imagePath != null) {
        	imageNode.setProperty("fileReference", imagePath);
        	imageNode.setProperty("sling:resourceType", "foundation/components/image");
        }
    }

	/**
	 * Private Functions
	 */

	//Save the uploaded file into the AEM DAM using AssetManager API
	private Asset writeToDam(InputStream is, String fileName, String mymeType,WebProfile profile) {
		try {
			//Use AssetManager to place the file into the AEM DAM
		    AssetManager  assetMgr = resourceResolver.adaptTo(AssetManager.class);
		    String newFile = damPath +fileName; 
		    Asset asset = assetMgr.createAsset(newFile, is,mymeType, true);

		    Resource metadataRes = asset.adaptTo(Resource.class).getChild("jcr:content/metadata");
		    ModifiableValueMap map = metadataRes.adaptTo(ModifiableValueMap.class);
		    map.put("dc:title", profile.getProfile().getObject().getTitle() +" " + profile.getProfile().getObject().getFirstName()+" "+profile.getProfile().getObject().getLastName());
		    resourceResolver.commit();

		    //assetMgr.createRevision(asset, "Metadata-title", "updating metadata title");
		    // Return the asset that was stored

		    return asset;
		}
		catch(Exception e) {
		    LOG.error("writeToDam() ", e);
		}
		return null;
	}
}