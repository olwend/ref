package uk.ac.nhm.nhm_www.core.componentHelpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.scripting.SlingScriptHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.nhm.nhm_www.core.model.science.Book;
import uk.ac.nhm.nhm_www.core.model.science.BookChapter;
import uk.ac.nhm.nhm_www.core.model.science.ConferenceProceedings;
import uk.ac.nhm.nhm_www.core.model.science.Dataset;
import uk.ac.nhm.nhm_www.core.model.science.Exhibition;
import uk.ac.nhm.nhm_www.core.model.science.InternetPublication;
import uk.ac.nhm.nhm_www.core.model.science.JournalArticle;
import uk.ac.nhm.nhm_www.core.model.science.NewspaperMagazine;
import uk.ac.nhm.nhm_www.core.model.science.Other;
import uk.ac.nhm.nhm_www.core.model.science.PhoneNumber;
import uk.ac.nhm.nhm_www.core.model.science.Poster;
import uk.ac.nhm.nhm_www.core.model.science.Publication;
import uk.ac.nhm.nhm_www.core.model.science.Qualification;
import uk.ac.nhm.nhm_www.core.model.science.Report;
import uk.ac.nhm.nhm_www.core.model.science.ScholarlyEdition;
import uk.ac.nhm.nhm_www.core.model.science.Scientist;
import uk.ac.nhm.nhm_www.core.model.science.Software;
import uk.ac.nhm.nhm_www.core.model.science.ThesisDissertation;
import uk.ac.nhm.nhm_www.core.model.science.WebSite;
import uk.ac.nhm.nhm_www.core.model.science.Webpage;
import uk.ac.nhm.nhm_www.core.model.science.WebsitePublicationType;
import uk.ac.nhm.nhm_www.core.model.science.WorkExperience;
import uk.ac.nhm.nhm_www.core.model.science.grants.Grant;
import uk.ac.nhm.nhm_www.core.model.science.proactivities.Committee;
import uk.ac.nhm.nhm_www.core.model.science.proactivities.Consultancy;
import uk.ac.nhm.nhm_www.core.model.science.proactivities.Editorship;
import uk.ac.nhm.nhm_www.core.model.science.proactivities.EventAdministration;
import uk.ac.nhm.nhm_www.core.model.science.proactivities.EventParticipation;
import uk.ac.nhm.nhm_www.core.model.science.proactivities.Fellowship;
import uk.ac.nhm.nhm_www.core.model.science.proactivities.Fieldwork;
import uk.ac.nhm.nhm_www.core.model.science.proactivities.InternalOrExternalPosition;
import uk.ac.nhm.nhm_www.core.model.science.proactivities.Membership;
import uk.ac.nhm.nhm_www.core.model.science.proactivities.Partnership;
import uk.ac.nhm.nhm_www.core.model.science.proactivities.ProfessionalActivity;
import uk.ac.nhm.nhm_www.core.model.science.proactivities.ReviewerOrRefereeGrant;
import uk.ac.nhm.nhm_www.core.model.science.proactivities.ReviewerOrRefereePublication;
import uk.ac.nhm.nhm_www.core.model.science.projects.Project;
import uk.ac.nhm.nhm_www.core.model.science.projects.ProjectType;
import uk.ac.nhm.nhm_www.core.services.ScientistsGroupsService;

import com.day.cq.wcm.api.components.DropTarget;
import com.day.cq.wcm.foundation.Image;

public class ScientistProfileHelper {
	
    private static final Logger LOG = LoggerFactory.getLogger(ScientistProfileHelper.class);
    
    
	/* Information Nodes Structure */
	public static final String PERSONAL_INFORMATION_NODE_NAME 	  = "personalInformation";
	public static final String DEPARTAMENT_INFORMATION_NODE_NAME = "department";
	public static final String PROFESIONAL_INFORMATION_NODE_NAME = "professional";
	
	/* Attribute names */
	public static final String AUTHORS_ATTRIBUTE 		  = "authors";
	public static final String ARRIVE_DATE_ATTRIBUTE	  = "arriveDate";
	public static final String BOOK_TITLE_ATTRIBUTE 	  = "bookTitle";
	public static final String DEPARTMENT_ATTRIBUTE		  = "department";
	public static final String DIVISION_ATTRIBUTE		  = "division";
	public static final String DOI_LINK_ATTRIBUTE 		  = "doiLink";
	public static final String DOI_TEXT_ATTRIBUTE 		  = "doiText";
	public static final String EDITORS_ATTRIBUTE 		  = "editors";
	public static final String EMAIL_ATTRIBUTE 			  = "email";
	public static final String END_DATE_ATTRIBUTE 		  = "endDate";
	public static final String END_PAGE_ATTRIBUTE 		  = "endPage";
	public static final String INITIALS_ATTRIBUTE		  = "initials";
	public static final String FAVORITE_ATTRIBUTE 		  = "isFavourite";
	public static final String FIRSTNAME_ATTRIBUTE		  = "firstName";
	public static final String FROM_ATTRIBUTE 			  = "from";
	public static final String FUNCTION_ATTRIBUTE 		  = "function";
	public static final String GROUP_PATH_ATTRIBUTE		  = "groupPath";
	public static final String GROUP_NAME_ATTRIBUTE		  = "groupName";
	public static final String INSTITUTION_ATTRIBUTE 	  = "institution";
	public static final String ISSUE_ATTRIBUTE 		   	  = "issue";
	public static final String JOURNAL_NAME_ATTRIBUTE 	  = "journalName";
	public static final String KNOWS_AS_ATTRIBUTE		  = "knownAs";
	public static final String LABEL_ATTRIBUTE 		   	  = "label";
	public static final String LASTNAME_ATTRIBUTE		  = "lastName";
	public static final String NAME_ATTRIBUTE			  = "name";
	public static final String LINK_ATTRIBUTE 			  = "href";
	public static final String ORGANISATION_ATTRIBUTE 	  = "organisation";
	public static final String CITY_ATTRIBUTE			  = "city";
	public static final String COUNTRY_ATTRIBUTE 		  = "country";
	public static final String INSTITUTION_ORGANISATIONS_ATTRIBUTE	= "institutionOrganisations";
	public static final String INSTITUTION_CITIES_ATTRIBUTE			= "institutionCities";
	public static final String INSTITUTION_COUNTRIES_ATTRIBUTE 		= "institutionCountries";
	public static final String PAGE_COUNT_ATTRIBUTE 	  = "pageCount";
	public static final String PHONE_ATTRIBUTE 		   	  = "phone";
	public static final String PLACE_ATTRIBUTE 		   	  = "place";
	public static final String POSITION_ATTRIBUTE 		  = "position";
	public static final String PUBLICATION_DATE_ATTRIBUTE 	= "publicationDate";
	public static final String PUBLICATION_MONTH_ATTRIBUTE	= "publicationMonth";
	public static final String PUBLICATION_DAY_ATTRIBUTE 	= "publicationDay";
	public static final String START_YEAR_ATTRIBUTE 		= "startYear";
	public static final String START_MONTH_ATTRIBUTE 		= "startMonth";
	public static final String START_DAY_ATTRIBUTE			= "startDay";
	public static final String END_YEAR_ATTRIBUTE 			= "endYear";
	public static final String END_MONTH_ATTRIBUTE 			= "endMonth";
	public static final String END_DAY_ATTRIBUTE			= "endDay";
	
	//Professional Activities
	public static final String URL_ATTRIBUTE						= "url";
	public static final String START_DATE_DAY_NAME_ATTRIBUTE		= "startDay";
	public static final String START_DATE_MONTH_NAME_ATTRIBUTE		= "startMonth";
	public static final String START_DATE_YEAR_NAME_ATTRIBUTE		= "startYear";
	public static final String END_DATE_DAY_NAME_ATTRIBUTE			= "endDay";
	public static final String END_DATE_MONTH_NAME_ATTRIBUTE		= "endMonth";
	public static final String END_DATE_YEAR_NAME_ATTRIBUTE			= "endYear";
	public static final String COMMITTEE_ROLE_ATTRIBUTE				= "committeeRole";
	public static final String EDITORSHIP_ROLE_ATTRIBUTE			= "editorshipRole";
	public static final String ADMINISTRATIVE_ROLE_ATTRIBUTE		= "administrativeRole";
	public static final String PARTICIPATION_ROLES_ATTRIBUTE		= "participationRoles";
	public static final String EVENT_TYPE_ATTRIBUTE					= "eventType";
	public static final String C_TEXT_1_ATTRIBUTE					= "publicationTitle";
	public static final String PUBLICATION_TYPE_ATTRIBUTE			= "publicationType";
	public static final String REVIEW_TYPE_ATTRIBUTE				= "reviewType";
	public static final String MEMBERSHIP_ROLE_ATTRIBUTE			= "membershipRole";
	public static final String OFFICE_HELD_TYPE_ATTRIBUTE			= "officeHeldType";
	public static final String OFFICE_OTHER_HELD_TYPE_ATTRIBUTE		= "officeOtherHeldType";
	public static final String INTERNAL_OR_EXTERNAL_ATTRIBUTE		= "internalOrExternalPosition";
	public static final String AREA_OR_REGION						= "areaOrRegion";
	
	public static final String PUBLISHER_ATTRIBUTE 	   	  	= "publisher";
	public static final String TITLE_ATTRIBUTE 		   	  	= "title";
	public static final String TO_ATTRIBUTE 			  	= "to";
	public static final String TYPE_ATTRIBUTE 			  	= "type";
	public static final String REPORTING_DATE_ATTRIBUTE   	= "reportingDate";
	public static final String SPECIALISMS_ATTRIBUTE	  	= "specialisms";
	public static final String START_DATE_ATTRIBUTE 	  	= "startDate";
	public static final String START_PAGE_ATTRIBUTE 	  	= "startPage";
	public static final String STATEMENT_ATTRIBUTE		  	= "statement";
	public static final String SUBORGANISATION_ATTRIBUTE  	= "subOrganisation";
	public static final String VOLUME_ATTRIBUTE 		  	= "volume";
	public static final String CONFERENCE_NAME_ATTRIBUTE  	= "conferenceName";
	public static final String CONFIDENTIAL_ATTRIBUTE	  	= "confidential";
	public static final String THESIS_TYPE_ATTRIBUTE	  	= "thesisType";
	public static final String PUBLISHER_URL_ATTRIBUTE	  	= "publisherURL";
	public static final String PUBLISHER_LOCATION_ATTRIBUTE = "location";
	
	
	//Projects
	public static final String FUNDING_SOURCE_ATTRIBUTE		= "fundingSource";
	public static final String EXTERNAL_COLLABORATORS		= "externalCollaborators";
	public static final String NHM_URL						= "nhmURL";
	
	
	//Grants
	public static final String PROPOSAL_TITLE				= "proposalTitle";
	public static final String ROLE							= "role";
	public static final String ROLE_PRINCIPAL_INVESTIGATOR	= "principalInvestigator";
	public static final String ROLE_CO_INVESTIGATOR			= "coInvestigator";
	public static final String FUNDER_NAME					= "funderName";
	public static final String FUNDER_NAME_OTHER			= "funderNameOther";
	public static final String TOTAL_VALUE_AWARDED			= "totalAwarded";
	public static final String NHM_VALUE_AWARDED			= "nhmAwarded";
	
	
	/* Personal Information */
	private static final String INITIALS_ATTRIBUTE_NAME    	  = PERSONAL_INFORMATION_NODE_NAME + "/" + INITIALS_ATTRIBUTE;
	private static final String TITLE_ATTRIBUTE_NAME 	   	  = PERSONAL_INFORMATION_NODE_NAME + "/" + TITLE_ATTRIBUTE;
	private static final String FIRSTNAME_ATTRIBUTE_NAME   	  = PERSONAL_INFORMATION_NODE_NAME + "/" + FIRSTNAME_ATTRIBUTE;
	private static final String LASTNAME_ATTRIBUTE_NAME    	  = PERSONAL_INFORMATION_NODE_NAME + "/" + LASTNAME_ATTRIBUTE;
	private static final String NICKNAME_ATTRIBUTE_NAME    	  = PERSONAL_INFORMATION_NODE_NAME + "/" + KNOWS_AS_ATTRIBUTE;
	private static final String EMAIL_ATTRIBUTE_NAME 	   	  = PERSONAL_INFORMATION_NODE_NAME + "/" + EMAIL_ATTRIBUTE;
	private static final String STATEMENT_ATTRIBUTE_NAME   	  = PERSONAL_INFORMATION_NODE_NAME + "/" + STATEMENT_ATTRIBUTE;
	private static final String PHONE_ATTRIBUTE_NAME	   	  = PERSONAL_INFORMATION_NODE_NAME + "/" + PHONE_ATTRIBUTE;
	private static final String SPECIALISMS_ATTRIBUTE_NAME 	  = PERSONAL_INFORMATION_NODE_NAME + "/" + SPECIALISMS_ATTRIBUTE;
	public  static final String WEB_SITE_PREFIX_NODE_NAME	  = "web";
	public  static final String WEB_SITES_NODE_NAME			  = "websites";
	private static final String WEB_SITES_NODE_PATH			  = PERSONAL_INFORMATION_NODE_NAME + "/" + WEB_SITES_NODE_NAME;
	public  static final String PHONE_NUMBER_PREFIX_NODE_NAME = "phonenumber";
	public  static final String PHONE_NUMBERS_NODE_NAME		  = "phonenumbers";
	private static final String PHONE_NUMBERS_NODE_PATH		  = PERSONAL_INFORMATION_NODE_NAME + "/" + PHONE_NUMBERS_NODE_NAME;
	
	/* Department Information */
	private static final String FUNCTION_ATTRIBUTE_NAME  	   = DEPARTAMENT_INFORMATION_NODE_NAME + "/function";
	private static final String POSITION_ATTRIBUTE_NAME  	   = DEPARTAMENT_INFORMATION_NODE_NAME + "/position";
	private static final String DIVISION_ATTRIBUTE_NAME  	   = DEPARTAMENT_INFORMATION_NODE_NAME + "/" + DIVISION_ATTRIBUTE;
	private static final String DEPARTMENT_NAME_ATTRIBUTE_NAME = DEPARTAMENT_INFORMATION_NODE_NAME + "/" + NAME_ATTRIBUTE;
	private static final String GROUP_PATH_ATTRIBUTE_NAME 	   = DEPARTAMENT_INFORMATION_NODE_NAME + "/" + GROUP_PATH_ATTRIBUTE;
	
	/* Qualifications */
	public  static final String QUALIFICATION_PREFIX_NODE_NAME 	  = "qualification";
	public	static final String DEGREES_NODE_NAME				  = "degrees";
	private static final String DEGREES_NODE_PATH  			   	  = PROFESIONAL_INFORMATION_NODE_NAME + "/" + DEGREES_NODE_NAME;
	public  static final String CERTIFICATES_NODE_NAME			  = "certificates";
	private static final String CERTIFICATES_NODE_PATH  		  = PROFESIONAL_INFORMATION_NODE_NAME + "/" + CERTIFICATES_NODE_NAME;
	public  static final String POST_GRADUATE_TRAININGS_NODE_NAME = "postgraduatetrainings";
	private static final String POST_GRADUATE_TRAININGS_NODE_PATH = PROFESIONAL_INFORMATION_NODE_NAME + "/" + POST_GRADUATE_TRAININGS_NODE_NAME;
	
	/* Work Experience */
	public  static final String WORK_EXPERIENCE_PREFIX_NODE_NAME  = "workExperience";
	public  static final String ACADEMIC_HISTORY_NODE_NAME		  = "academicAppointments";
	private static final String ACADEMIC_HISTORY_NODE_PATH  	  = PROFESIONAL_INFORMATION_NODE_NAME + "/" + ACADEMIC_HISTORY_NODE_NAME;
	public  static final String NON_ACADEMIC_HISTORY_NODE_NAME	  = "nonAcademicAppointments";
	private static final String NON_ACADEMIC_HISTORY_NODE_PATH    = PROFESIONAL_INFORMATION_NODE_NAME + "/" + NON_ACADEMIC_HISTORY_NODE_NAME;
	
	/* Publications */
	public  static final String PUBLICATION_PREFIX_NODE_NAME	  = "publication";
	public  static final String PUBLICATIONS_NODE_NAME			  = "publications";
	public  static final String AUTHORED_PUBLICATIONS_NODE_NAME	  = "authored";
	private static final String PUBLICATIONS_NODE_PATH			  = PUBLICATIONS_NODE_NAME + "/" + AUTHORED_PUBLICATIONS_NODE_NAME;
	
	public static final String PUBLICATION_TYPE_BOOK 					= "Book";
	public static final String PUBLICATION_TYPE_CHAPTER					= "Chapter";
	public static final String PUBLICATION_TYPE_ARTICLE 				= "Journal Article";
	public static final String PUBLICATION_TYPE_CONFERENCE_PROCEEDINGS	= "Conference Proceedings";
	public static final String PUBLICATION_TYPE_DATASET					= "Dataset";
	public static final String PUBLICATION_TYPE_INTERNET_PUBLICATION	= "Internet Publications";
	public static final String PUBLICATION_TYPE_NEWSPAPER_OR_MAGAZINE	= "Newspaper/Magazine Article";
	public static final String PUBLICATION_TYPE_OTHER					= "Other";
	public static final String PUBLICATION_TYPE_POSTER					= "Poster";
	public static final String PUBLICATION_TYPE_REPORT					= "Report";
	public static final String PUBLICATION_TYPE_SCHOLARLY_EDITION		= "Scholarly Edition";
	public static final String PUBLICATION_TYPE_SOFTWARE				= "Software";
	public static final String PUBLICATION_TYPE_THESIS_OR_DISERTATION	= "Thesis/Dissertation";
	public static final String PUBLICATION_TYPE_WEBPAGE					= "Webpage";
	public static final String PUBLICATION_TYPE_WEBSITE					= "Website";
	public static final String PUBLICATION_TYPE_EXHIBITION				= "Exhibition";
	
	/* Professional Activities */
	public  static final String PROFESSIONAL_ACTIVITIES_PREFIX_NODE_NAME 		= "professionalActivity";
	public  static final String PROFESSIONAL_ACTIVITIES_NODE_NAME  				= "professionalActivities";
	public  static final String ASSOCIATED_PROFESSIONAL_ACTIVITIES_NODE_NAME  	= "associated";
	private static final String PROFESSIONAL_ACTIVITIES_NODE_PATH			  	= PROFESSIONAL_ACTIVITIES_NODE_NAME + "/" + ASSOCIATED_PROFESSIONAL_ACTIVITIES_NODE_NAME;
	
	public static final String PROFESSIONAL_ACTIVITY_TYPE_COMMITTEES					= "Commmittee";
	public static final String PROFESSIONAL_ACTIVITY_TYPE_EVENT_ADMINISTRATION			= "Event Administration";
	public static final String PROFESSIONAL_ACTIVITY_TYPE_EVENT_PARTICIPATION			= "Event Participation";
	public static final String PROFESSIONAL_ACTIVITY_TYPE_EXTERNAL_INTERNAL_POSITION	= "External Positions";
	public static final String PROFESSIONAL_ACTIVITY_TYPE_EDITORSHIP					= "Editorship";
	public static final String PROFESSIONAL_ACTIVITY_TYPE_FELLOWSHIP					= "Fellowship";
	public static final String PROFESSIONAL_ACTIVITY_TYPE_MEMBERSHIP					= "Membership";
	public static final String PROFESSIONAL_ACTIVITY_TYPE_EXHIBITION					= "Exhibition";
	
	public static final String PROFESSIONAL_ACTIVITY_PARAMETER_EXTERNAL					= "External";
	public static final String PROFESSIONAL_ACTIVITY_PARAMETER_INTERNAL					= "Internal";
	public static final String PROFESSIONAL_ACTIVITY_PARAMETER_WORKSHOP					= "Workshop";
	public static final String PROFESSIONAL_ACTIVITY_PARAMETER_CONFERENCE				= "Conference";
	public static final String PROFESSIONAL_ACTIVITY_PARAMETER_MEETING					= "Meeting";
	public static final String PROFESSIONAL_ACTIVITY_PARAMETER_COLLOQUIUM				= "Colloquium";
	public static final String PROFESSIONAL_ACTIVITY_PARAMETER_CONGRESS					= "Congress";
	public static final String PROFESSIONAL_ACTIVITY_PARAMETER_CONVENTION				= "Convention";
	public static final String PROFESSIONAL_ACTIVITY_PARAMETER_SYMPOSIUM				= "Symposium";
	
	public static final String PROFESSIONAL_ACTIVITY_TYPE_REVIEW_REFEREE_PUBLICATION	= "Review Referee Publication";
	public static final String PROFESSIONAL_ACTIVITY_TYPE_REVIEW_REFEREE_GRANT			= "Review Referee Grant";

	/* Projects */
	public static final String PROJECTS_PREFIX_NODE_NAME 				= "project";
	public static final String PROJECTS_NODE_NAME  						= "projects";
	public static final String PROJECTS_CONTAINER_NODE_NAME  			= "container";
	private static final String PROJECTS_NODE_PATH			  	= PROJECTS_NODE_NAME + "/" + PROJECTS_CONTAINER_NODE_NAME;
	
	public static final String PROFESSIONAL_ACTIVITY_TYPE_CONSULTING	= "Consulting";
	public static final String PROFESSIONAL_ACTIVITY_TYPE_PARTNERSHIP	= "Partnership";
	public static final String PROFESSIONAL_ACTIVITY_TYPE_FIELDWORK 	= "Fieldwork";
	
	public static final String PROJECT_TYPE_PROJECT						= "Project";
	
	/* Grants */
	public static final String GRANT_PREFIX_NODE_NAME 					= "grant";
	public static final String GRANT_NODE_NAME  						= "grants";
	public static final String GRANT_CONTAINER_NODE_NAME  				= "container";
	private static final String GRANT_NODE_PATH			  	= GRANT_NODE_NAME + "/" + GRANT_CONTAINER_NODE_NAME;
	
	public static final String GRANT_TYPE_GRANT							= "Grant";
	
	private static final String IMAGE_NODE_NAME	= "image";
	
	private ValueMap properties;
	private Resource resource;
	
	public ScientistProfileHelper(final Resource resource) {
		this.resource = resource;
		this.properties = resource.adaptTo(ValueMap.class);
	}
	
	public Image getImage() {
		final Resource imageResource = this.resource.getChild(IMAGE_NODE_NAME);
		
		if (imageResource == null) {
			return null;
		}
		
		final Image result = new Image(resource);
		
		result.addCssClass(DropTarget.CSS_CLASS_PREFIX + "image");
		result.addCssClass("desktop tablet");
		return result;

	}
	
	public String getTitle() {
		if (!this.hasProperties()) {
			return null;
		}
		
		return this.properties.get(TITLE_ATTRIBUTE_NAME, String.class);
	}
	
	public String getInitials() {
		if (!this.hasProperties()) {
			return null;
		}
		return this.properties.get(INITIALS_ATTRIBUTE_NAME, String.class);
	}
	
	public String getFirstName() {
		if (!this.hasProperties()) {
			return null;
		}
		return this.properties.get(FIRSTNAME_ATTRIBUTE_NAME, String.class);
	}
	
	public String getLastName() {
		if (!this.hasProperties()) {
			return null;
		}
		return this.properties.get(LASTNAME_ATTRIBUTE_NAME, String.class);
	}
	
	public String getNickName() {
		if (!this.hasProperties()) {
			return null;
		}
		return this.properties.get(NICKNAME_ATTRIBUTE_NAME, String.class);
	}
	
	public String getEmail() {
		if (!this.hasProperties()) {
			return null;
		}
		return this.properties.get(EMAIL_ATTRIBUTE_NAME, String.class);
	}
	
	public String getPhone() {
		if (!this.hasProperties()) {
			return null;
		}
		return this.properties.get(PHONE_ATTRIBUTE_NAME, String.class);
	}
	
	public String getSpecialisms() {
		if (!this.hasProperties()) {
			return null;
		}
		
		final String[] specialisms = this.properties.get(SPECIALISMS_ATTRIBUTE_NAME, String[].class);
		
		if (specialisms == null) {
			return null;
		}
		
		return StringUtils.join(specialisms, ", "); 
	}
	
	public String getStatement() {
		if (!this.hasProperties()) {
			return null;
		}
		return this.properties.get(STATEMENT_ATTRIBUTE_NAME, String.class);
	}
	
	public String getFunction() {
		if (!this.hasProperties()) {
			return null;
		}
		return this.properties.get(FUNCTION_ATTRIBUTE_NAME, String.class);
	}
	
	public String getPosition() {
		if (!this.hasProperties()) {
			return null;
		}
		return this.properties.get(POSITION_ATTRIBUTE_NAME, String.class);
	}
	
	public String getDepartmentName() {
		if (!this.hasProperties()) {
			return null;
		}
		return this.properties.get(DEPARTMENT_NAME_ATTRIBUTE_NAME, String.class);
	}
	
	public String getDivision() {
		if (!this.hasProperties()) {
			return null;
		}
		return this.properties.get(DIVISION_ATTRIBUTE_NAME, String.class);
	}
	
	/*public Set<Qualification> getDegrees() {
		return this.extractQualifications(DEGREES_ATTRIBUTE_NAME);
	}*/
	
	public Set<Qualification> getDegrees() {
		return this.extractQualifications(DEGREES_NODE_PATH);
	}
	
	public Set<Qualification> getCertificates() {
		return this.extractQualifications(CERTIFICATES_NODE_PATH);
	}
	
	public Set<Qualification> getPostGraduateTrainings() {
		return this.extractQualifications(POST_GRADUATE_TRAININGS_NODE_PATH);
	}
	
	public Set<WorkExperience> getAcademicHistory() {
		return this.extractWorkExperiences(ACADEMIC_HISTORY_NODE_PATH);
	}
	
	public Set<WorkExperience> getNonAcademicHistory() {
		return this.extractWorkExperiences(NON_ACADEMIC_HISTORY_NODE_PATH);
	}
	
	public Set<Publication> getPublications() {
		return this.extractPublications(PUBLICATIONS_NODE_PATH);
	}
	
	public Map<String, Set<ProfessionalActivity>> getProfessionalActivities() {
		return this.extractProfessionalActivities(PROFESSIONAL_ACTIVITIES_NODE_PATH);
	}
	
	public Map<String, Set<Project>> getProjects() {
		return this.extractProjects(PROJECTS_NODE_PATH);
	}
	
	public Map<String, Set<Grant>> getGrants() {
		return this.extractGrants(GRANT_NODE_PATH);
	}
	
	public Set<WebSite> getWebSites() {
		return this.extractWebsites(WEB_SITES_NODE_PATH);
	}
	
	public boolean hasExternalWebSites() { 
		final Resource websitesResource = this.resource.getChild(WEB_SITES_NODE_PATH);
		
		if (websitesResource == null) {
			return false;
		}
		
		final Iterator<Resource> children = websitesResource.listChildren();
		
		while (children.hasNext()) {
			final Resource child = children.next();
			
			if (child.getName().startsWith(WEB_SITE_PREFIX_NODE_NAME)) {
				final ValueMap childProperties = child.adaptTo(ValueMap.class);
				
				final WebSite website = new WebSite(childProperties.get(LABEL_ATTRIBUTE, String.class), childProperties.get(LINK_ATTRIBUTE, String.class), childProperties.get(TYPE_ATTRIBUTE, String.class));
				
				if (website.isValid() && (!website.isPersonalInformationWebSite() && !WebSite.TWITTER_TYPE.equals(website.getType()))) {
					return true;
				}
			}
		}
		return false;
	}
	
	public String getTwitterUserName() {
		final Set<WebSite> websites = this.getWebSites();
		
		if (websites == null || websites.isEmpty()) {
			return null;
		}
		
		for (final WebSite site : websites) {
			if (WebSite.TWITTER_TYPE.equals(site.getType())) {
				final String url = site.getLink();
				if (url == null) {
					return null;
				}
				
				final String twitterURL = "twitter.com/";
				
				if (!url.contains(twitterURL)) {
					return null;
				}
				
				final String userName = url.substring(url.indexOf(twitterURL) + twitterURL.length());
				
				return userName;
			}
		}
		
		return null;
	}
	
	public List<PhoneNumber> getPhones() {
		return this.extractPhones(PHONE_NUMBERS_NODE_PATH);
	}
	
	public boolean hasGroup() {
		return this.properties.get(GROUP_PATH_ATTRIBUTE_NAME, String.class) != null;
	}
	
	public String getGroupPath() {
		return this.properties.get(GROUP_PATH_ATTRIBUTE_NAME, String.class);
	}
	
	private boolean hasProperties() {
		return this.properties != null;
	}
	
	private Set<Qualification> extractQualifications(final String nodeName) {
		final Set<Qualification> result = new TreeSet<Qualification>(); 
		
		final Resource qualificationsResource = this.resource.getChild(nodeName);
		
		if (qualificationsResource == null) {
			return result;
		}
		
		final Iterator<Resource> children = qualificationsResource.listChildren();
		
		while (children.hasNext()) {
			final Resource child = children.next();
			
			if (child.getName().startsWith(QUALIFICATION_PREFIX_NODE_NAME)) {
				final ValueMap childProperties = child.adaptTo(ValueMap.class);
				final Qualification qualification = new Qualification(childProperties.get(FROM_ATTRIBUTE, -1), childProperties.get(TO_ATTRIBUTE, -1), 
						childProperties.get(TITLE_ATTRIBUTE, String.class), childProperties.get(INSTITUTION_ATTRIBUTE, String.class), null, 
						null, childProperties.get(COUNTRY_ATTRIBUTE, String.class), null);
				
				if (qualification.isValid()) {
					result.add(qualification);
				}
			}
		}
		
		return result;
		
	}

	private Set<WorkExperience> extractWorkExperiences(final String nodeName) {
		final Set<WorkExperience> result = new TreeSet<WorkExperience>(); 
		
		final Resource workExperienceResource = this.resource.getChild(nodeName);
		
		if (workExperienceResource == null) {
			return result;
		}
		
		final Iterator<Resource> children = workExperienceResource.listChildren();
		while (children.hasNext()) {
		
			final Resource child = children.next();
			
			if (child.getName().startsWith(WORK_EXPERIENCE_PREFIX_NODE_NAME)) {
				final ValueMap childProperties = child.adaptTo(ValueMap.class);
				
				final int startDate = childProperties.get(START_DATE_ATTRIBUTE, -1);
				final int endDate = childProperties.get(END_DATE_ATTRIBUTE, -1);
				
				final WorkExperience workExperience = new WorkExperience(startDate,
							endDate,
							childProperties.get(POSITION_ATTRIBUTE, String.class),
							childProperties.get(ORGANISATION_ATTRIBUTE, String.class),
							childProperties.get(SUBORGANISATION_ATTRIBUTE, String.class),
							childProperties.get(COUNTRY_ATTRIBUTE, String.class),
							null);
				
				if (workExperience.isValid()) {
					result.add(workExperience);
				}
			}
		}
		
		return result;
	}
	
	private Set<Publication> extractPublications(final String nodeName) {
		final Set<Publication> result = new TreeSet<Publication>(); 
		
		final Resource publicationsResource = this.resource.getChild(nodeName);
		
		if (publicationsResource == null) {
			return result;
		}
		
		final Iterator<Resource> children = publicationsResource.listChildren();
		
		while (children.hasNext()) {
			final Resource child = children.next();
			
			if (child.getName().startsWith(PUBLICATION_PREFIX_NODE_NAME)) {
				final ValueMap childProperties = child.adaptTo(ValueMap.class);
				final String title = childProperties.get(TITLE_ATTRIBUTE, String.class);
				final int publicationYear = childProperties.get(PUBLICATION_DATE_ATTRIBUTE, -1);
				final String type = childProperties.get(TYPE_ATTRIBUTE, String.class);
				final boolean favorite = childProperties.get(FAVORITE_ATTRIBUTE, false);
				final String[] authors = childProperties.get(AUTHORS_ATTRIBUTE, String[].class);
				final String reportingDate;
				if (childProperties.get(REPORTING_DATE_ATTRIBUTE, String.class) != null){
					reportingDate = childProperties.get(REPORTING_DATE_ATTRIBUTE, String.class);
				} else {
					reportingDate = "";
				}
				List<String> authorsList = new ArrayList<>();
				if (authors != null) {
					//Collections.addAll(authorsSet, authors);
					authorsList = Arrays.asList(authors);
				}
				final String link = childProperties.get(LINK_ATTRIBUTE, String.class);
				//	LOG.error("Loading Publication with type : " + type);
				
				switch (type) {
				case PUBLICATION_TYPE_BOOK:
					final String[] editors = childProperties.get(EDITORS_ATTRIBUTE, String[].class);
					List<String> editorsSet = new ArrayList<>();
					if (editors != null) {
						//Collections.addAll(editorsSet, editors);
						editorsSet = Arrays.asList(editors);
					}
					final String publisher = childProperties.get(PUBLISHER_ATTRIBUTE, String.class);
					final String place	   = childProperties.get(PLACE_ATTRIBUTE, String.class);
					final int bookBeginPage = childProperties.get(START_PAGE_ATTRIBUTE, -1);
					final int bookEndPage = childProperties.get(END_PAGE_ATTRIBUTE, -1);
					final int page	   	   = childProperties.get(PAGE_COUNT_ATTRIBUTE, -1);
					result.add(new Book(title, authorsList, favorite, publicationYear, link, reportingDate, editorsSet, publisher, place, 
							page, bookBeginPage, bookEndPage));
					break;

				case PUBLICATION_TYPE_CHAPTER:
					final String[] bookEditors = childProperties.get(EDITORS_ATTRIBUTE, String[].class);
					//final Set<String> bookEditorsSet = new TreeSet<>();
					List<String> bookEditorsSet = new ArrayList<>();
					if (bookEditors != null) {
						//Collections.addAll(bookEditorsSet, bookEditors);
						bookEditorsSet = Arrays.asList(bookEditors);
					}
					final String bookPublisher = childProperties.get(PUBLISHER_ATTRIBUTE, String.class);
					final String bookPlace	   = childProperties.get(PLACE_ATTRIBUTE, String.class);
					final int beginPage = childProperties.get(START_PAGE_ATTRIBUTE, -1);
					final int endPage   = childProperties.get(END_PAGE_ATTRIBUTE, -1);
					final int chapterpage	= childProperties.get(PAGE_COUNT_ATTRIBUTE, -1);
					final String bookTitle = childProperties.get(BOOK_TITLE_ATTRIBUTE, String.class);
					result.add(new BookChapter(title, authorsList, favorite, publicationYear, link, reportingDate, bookEditorsSet, bookTitle, 
							beginPage, endPage, chapterpage, bookPublisher, bookPlace));
					break;
					
				case PUBLICATION_TYPE_CONFERENCE_PROCEEDINGS:
					final String[] conferenceEditors = childProperties.get(EDITORS_ATTRIBUTE, String[].class);
					//final Set<String> bookEditorsSet = new TreeSet<>();
					List<String> conferenceEditorsSet = new ArrayList<>();
					if (conferenceEditors != null) {
						//Collections.addAll(bookEditorsSet, bookEditors);
						conferenceEditorsSet = Arrays.asList(conferenceEditors);
					}
					final String conferencePublisher = childProperties.get(PUBLISHER_ATTRIBUTE, String.class);
					final String conferencePublisherURL = childProperties.get(PUBLISHER_URL_ATTRIBUTE, String.class);
					final String conferencePublishingPlace	   = childProperties.get(PLACE_ATTRIBUTE, String.class);
					final int conferenceBeginPage = childProperties.get(START_PAGE_ATTRIBUTE, -1);
					final int conferenceEndPage = childProperties.get(END_PAGE_ATTRIBUTE, -1);
					final int conferencePage = childProperties.get(PAGE_COUNT_ATTRIBUTE, -1);
					final String conferenceDoiText = childProperties.get(DOI_TEXT_ATTRIBUTE, String.class);
					final String conferenceDoiLink = childProperties.get(DOI_LINK_ATTRIBUTE, String.class);
					final String conferenceName = childProperties.get(CONFERENCE_NAME_ATTRIBUTE, String.class);
					final int conferenceVolume = childProperties.get(VOLUME_ATTRIBUTE, -1);
					final int conferenceIssue = childProperties.get(ISSUE_ATTRIBUTE, -1);
					final String conferencePublishedProceedings = childProperties.get(JOURNAL_NAME_ATTRIBUTE, String.class);
					final int startConferenceYear = childProperties.get(START_YEAR_ATTRIBUTE, -1);
					final int startConferenceMonth= childProperties.get(START_MONTH_ATTRIBUTE, -1);
					final int startConferenceDay = childProperties.get(START_DAY_ATTRIBUTE, -1);
					final int endConferenceYear = childProperties.get(START_YEAR_ATTRIBUTE, -1);
					final int endConferenceMonth= childProperties.get(START_MONTH_ATTRIBUTE, -1);
					final int endConferenceDay = childProperties.get(START_DAY_ATTRIBUTE, -1);
					result.add(new ConferenceProceedings(title, authorsList, favorite, publicationYear, link, reportingDate, conferenceName, conferencePublisherURL,
							conferenceEditorsSet, conferencePublisher, conferencePublishingPlace, conferenceBeginPage, conferenceEndPage, conferencePage,
							conferenceDoiText, conferenceDoiLink, conferenceVolume, conferenceIssue, conferencePublishedProceedings, startConferenceYear, 
							startConferenceMonth, startConferenceDay, endConferenceYear, endConferenceMonth, endConferenceDay));
					break;
					
				case PUBLICATION_TYPE_DATASET:
					final String datasetPublisherURL = childProperties.get(PUBLISHER_URL_ATTRIBUTE, String.class);
					final String datasetDoiText = childProperties.get(DOI_TEXT_ATTRIBUTE, String.class);
					final String datasetDoiURL = childProperties.get(DOI_LINK_ATTRIBUTE, String.class);
					result.add(new Dataset(title, authorsList, favorite, publicationYear, link, reportingDate, datasetDoiText, datasetDoiURL, datasetPublisherURL));
					break;

				case PUBLICATION_TYPE_INTERNET_PUBLICATION:
					final String publisherURL = childProperties.get(PUBLISHER_URL_ATTRIBUTE, String.class);
					final String internetPublisher = childProperties.get(PUBLISHER_ATTRIBUTE, String.class);
					final int internetBeginPage = childProperties.get(START_PAGE_ATTRIBUTE, -1);
					final int internetEndPage = childProperties.get(END_PAGE_ATTRIBUTE, -1);
					final int internetPage = childProperties.get(PAGE_COUNT_ATTRIBUTE, -1);
					final int iPublicationMonth = childProperties.get(PUBLICATION_MONTH_ATTRIBUTE, -1);
					final int iPublicationDay = childProperties.get(PUBLICATION_DAY_ATTRIBUTE, -1);
					result.add(new InternetPublication(title, authorsList, favorite, publicationYear, iPublicationMonth, iPublicationDay, link, 
							reportingDate, internetPublisher, publisherURL, internetBeginPage, internetEndPage, internetPage));
					break;
					
				case PUBLICATION_TYPE_ARTICLE:
					final int articleBeginPage = childProperties.get(START_PAGE_ATTRIBUTE, -1);
					final int articleEndPage = childProperties.get(END_PAGE_ATTRIBUTE, -1);
					final int journalpage	= childProperties.get(PAGE_COUNT_ATTRIBUTE, -1);
					final String doiText = childProperties.get(DOI_TEXT_ATTRIBUTE, String.class);
					final String doiLink = childProperties.get(DOI_LINK_ATTRIBUTE, String.class);
					final int volume = childProperties.get(VOLUME_ATTRIBUTE, -1);
					final int issue = childProperties.get(ISSUE_ATTRIBUTE, -1);
					final String journalName = childProperties.get(JOURNAL_NAME_ATTRIBUTE, String.class);
					result.add(new JournalArticle(title, authorsList, favorite, publicationYear, link, reportingDate, journalName, volume, issue, 
							articleBeginPage, articleEndPage, journalpage, doiText, doiLink));
					break;

				case PUBLICATION_TYPE_NEWSPAPER_OR_MAGAZINE:
					final String newsmagPublisherURL = childProperties.get(PUBLISHER_URL_ATTRIBUTE, String.class);
					final int newsmagBeginPage = childProperties.get(START_PAGE_ATTRIBUTE, -1);
					final int newsmagEndPage = childProperties.get(END_PAGE_ATTRIBUTE, -1);
					final int newsmagPage = childProperties.get(PAGE_COUNT_ATTRIBUTE, -1);
					final String newsmagDoiText = childProperties.get(DOI_TEXT_ATTRIBUTE, String.class);
					final String newsmagDoiLink = childProperties.get(DOI_LINK_ATTRIBUTE, String.class);
					final int newsmagVolume = childProperties.get(VOLUME_ATTRIBUTE, -1);
					final int newsmagIssue = childProperties.get(ISSUE_ATTRIBUTE, -1);
					result.add(new NewspaperMagazine(title, authorsList, favorite, publicationYear, link, reportingDate, newsmagPublisherURL,
							newsmagBeginPage, newsmagEndPage, newsmagPage, newsmagDoiLink, newsmagDoiText, newsmagVolume, newsmagIssue));
					break;
					
				case PUBLICATION_TYPE_OTHER:
					final String[] otherEditors = childProperties.get(EDITORS_ATTRIBUTE, String[].class);
					//final Set<String> bookEditorsSet = new TreeSet<>();
					List<String> otherEditorsSet = new ArrayList<>();
					if (otherEditors != null) {
						//Collections.addAll(bookEditorsSet, bookEditors);
						otherEditorsSet = Arrays.asList(otherEditors);
					}
					final String otherPublisher = childProperties.get(PUBLISHER_ATTRIBUTE, String.class);
					final String otherPublisherURL = childProperties.get(PUBLISHER_URL_ATTRIBUTE, String.class);
					final String otherPublishingPlace	   = childProperties.get(PLACE_ATTRIBUTE, String.class);
					final int otherBeginPage = childProperties.get(START_PAGE_ATTRIBUTE, -1);
					final int otherEndPage = childProperties.get(END_PAGE_ATTRIBUTE, -1);
					final int otherPage = childProperties.get(PAGE_COUNT_ATTRIBUTE, -1);
					final String otherDoiText = childProperties.get(DOI_TEXT_ATTRIBUTE, String.class);
					final String otherDoiLink = childProperties.get(DOI_LINK_ATTRIBUTE, String.class);
					final int otherVolume = childProperties.get(VOLUME_ATTRIBUTE, -1);
					final int otherIssue = childProperties.get(ISSUE_ATTRIBUTE, -1);
					final String otherJournalName = childProperties.get(JOURNAL_NAME_ATTRIBUTE, String.class);
					final int otherPublicationMonth = childProperties.get(PUBLICATION_MONTH_ATTRIBUTE, -1);
					final int otherPublicationDay = childProperties.get(PUBLICATION_DAY_ATTRIBUTE, -1);
					final String otherBookTitle = childProperties.get(BOOK_TITLE_ATTRIBUTE, String.class);
					final boolean otherConfidential = childProperties.get(CONFIDENTIAL_ATTRIBUTE, false);
					result.add(new Other(title, authorsList, favorite, publicationYear, link, reportingDate, otherConfidential, 
							otherPublicationMonth, otherPublicationDay, otherPublisherURL, otherJournalName, otherVolume, otherIssue,
							otherEditorsSet, otherPublisher, otherPublishingPlace, otherBeginPage, otherEndPage, otherPage, otherDoiLink, 
							otherDoiText, otherBookTitle));
					break;
					
				case PUBLICATION_TYPE_POSTER:
					final String posterNameOfConference = childProperties.get(CONFERENCE_NAME_ATTRIBUTE, String.class);
					final String posterCity = childProperties.get(CITY_ATTRIBUTE, String.class);
					result.add(new Poster(title, authorsList, favorite, publicationYear, link, reportingDate, posterNameOfConference, posterCity));
					break;
					
				case PUBLICATION_TYPE_REPORT:
					final String reportPublisher = childProperties.get(PUBLISHER_ATTRIBUTE, String.class);
					final String reportPublishingPlace	   = childProperties.get(PLACE_ATTRIBUTE, String.class);
					final int reportBeginPage = childProperties.get(START_PAGE_ATTRIBUTE, -1);
					final int reportEndPage = childProperties.get(END_PAGE_ATTRIBUTE, -1);
					final int reportPage = childProperties.get(PAGE_COUNT_ATTRIBUTE, -1);
					final boolean confidential = childProperties.get(CONFIDENTIAL_ATTRIBUTE, false);
					result.add(new Report(title, authorsList, favorite, publicationYear, link, reportingDate, confidential, reportBeginPage, reportEndPage, 
							reportPublisher, reportPublishingPlace, reportPage));
					break;
					
				case PUBLICATION_TYPE_SCHOLARLY_EDITION:
					final String scholarlyPublisher = childProperties.get(PUBLISHER_ATTRIBUTE, String.class);
					final String scholarlyPublisherURL = childProperties.get(PUBLISHER_URL_ATTRIBUTE, String.class);
					final int scholarlyBeginPage = childProperties.get(START_PAGE_ATTRIBUTE, -1);
					final int scholarlyEndPage = childProperties.get(END_PAGE_ATTRIBUTE, -1);
					final int scholarlyPage = childProperties.get(PAGE_COUNT_ATTRIBUTE, -1);
					final String scholarlydoiText = childProperties.get(DOI_TEXT_ATTRIBUTE, String.class);
					final String scholarlydoiLink = childProperties.get(DOI_LINK_ATTRIBUTE, String.class);
					result.add(new ScholarlyEdition(title, authorsList, favorite, publicationYear, link, reportingDate, scholarlyPublisherURL, 
							scholarlyBeginPage, scholarlyEndPage, scholarlyPage, scholarlydoiText, scholarlydoiLink, scholarlyPublisher));
					break;
					
				case PUBLICATION_TYPE_SOFTWARE:
					final String doiTxt = childProperties.get(DOI_TEXT_ATTRIBUTE, String.class);
					final String doiURL = childProperties.get(DOI_LINK_ATTRIBUTE, String.class);
					result.add(new Software(title, authorsList, favorite, publicationYear, link, reportingDate, doiTxt, doiURL));
					break;
					
				case PUBLICATION_TYPE_THESIS_OR_DISERTATION:
					final String[] supervisors = childProperties.get(EDITORS_ATTRIBUTE, String[].class);
					List<String> supervisorsSet = new ArrayList<>();
					if (supervisors != null) {
						supervisorsSet = Arrays.asList(supervisors);
					}
					final String thesisPublisher = childProperties.get(PUBLISHER_ATTRIBUTE, String.class);
					final String thesisPublishingPlace	   = childProperties.get(PLACE_ATTRIBUTE, String.class);
					final int thesisBeginPage = childProperties.get(START_PAGE_ATTRIBUTE, -1);
					final int thesisEndPage = childProperties.get(END_PAGE_ATTRIBUTE, -1);
					final int thesisPage = childProperties.get(PAGE_COUNT_ATTRIBUTE, -1);
					final String thesisType = childProperties.get(THESIS_TYPE_ATTRIBUTE, String.class);
					result.add(new ThesisDissertation(title, authorsList, favorite, publicationYear, link, reportingDate, supervisorsSet, thesisType, 
							thesisPublisher, thesisPublishingPlace, thesisBeginPage, thesisEndPage, thesisPage));
					break;
					
				case PUBLICATION_TYPE_WEBPAGE:
					final String webpagePublisher = childProperties.get(PUBLISHER_ATTRIBUTE, String.class);
					final String webpagePublisherURL = childProperties.get(PUBLISHER_URL_ATTRIBUTE, String.class);
					final String webpagePublishingPlace	   = childProperties.get(PLACE_ATTRIBUTE, String.class);
					result.add(new Webpage(title, authorsList, favorite, publicationYear, link, reportingDate, webpagePublisher, 
							webpagePublisherURL, webpagePublishingPlace));
					break;
					
				case PUBLICATION_TYPE_WEBSITE:
					final String websitePublisher = childProperties.get(PUBLISHER_ATTRIBUTE, String.class);
					final String websitePublisherURL = childProperties.get(PUBLISHER_URL_ATTRIBUTE, String.class);
					final String websitePublishingPlace	   = childProperties.get(PLACE_ATTRIBUTE, String.class);
					result.add(new WebsitePublicationType(title, authorsList, favorite, publicationYear, link, reportingDate, 
							websitePublisher, websitePublisherURL, websitePublishingPlace));
					break;
					
				case PUBLICATION_TYPE_EXHIBITION:
					final String exhibitionPublisherURL = childProperties.get(PUBLISHER_URL_ATTRIBUTE, String.class);
					final String exhibitionLocation = childProperties.get(PUBLISHER_LOCATION_ATTRIBUTE, String.class);
					final int startExhibitionYear = childProperties.get(START_YEAR_ATTRIBUTE, -1);
					final int startExhibitionMonth= childProperties.get(START_MONTH_ATTRIBUTE, -1);
					final int startExhibitionDay = childProperties.get(START_DAY_ATTRIBUTE, -1);
					final int endExhibitionYear = childProperties.get(START_YEAR_ATTRIBUTE, -1);
					final int endExhibitionMonth = childProperties.get(START_MONTH_ATTRIBUTE, -1);
					final int endExhibitionDay = childProperties.get(START_DAY_ATTRIBUTE, -1);
					result.add(new Exhibition(title, authorsList, favorite, publicationYear, link, reportingDate, 
							exhibitionLocation, exhibitionPublisherURL, startExhibitionDay, startExhibitionMonth, startExhibitionYear,
							endExhibitionDay, endExhibitionMonth, endExhibitionYear));
					break;
					
				default:
					break;
				}
			}
		}
		
		return result;
	}
	
	private Set<WebSite> extractWebsites(final String nodeName) {
		final Set<WebSite> result = new TreeSet<WebSite>(); 
		
		final Resource websitesResource = this.resource.getChild(nodeName);
		
		if (websitesResource == null) {
			return result;
		}
		
		final Iterator<Resource> children = websitesResource.listChildren();
		
		while (children.hasNext()) {
			final Resource child = children.next();
			
			if (child.getName().startsWith(WEB_SITE_PREFIX_NODE_NAME)) {
				final ValueMap childProperties = child.adaptTo(ValueMap.class);
				
				final WebSite website = new WebSite(childProperties.get(LABEL_ATTRIBUTE, String.class), childProperties.get(LINK_ATTRIBUTE, String.class), childProperties.get(TYPE_ATTRIBUTE, String.class));
				
				if (website.isValid()) {
					result.add(website);
				}
			}
		}
		return result;
	}
	
	private List<PhoneNumber> extractPhones(final String nodeName) {
		final List<PhoneNumber> result = new ArrayList<PhoneNumber>(); 
		
		final Resource phonesResource = this.resource.getChild(nodeName);
		
		if (phonesResource == null) {
			return result;
		}
		
		final Iterator<Resource> children = phonesResource.listChildren();
		
		while (children.hasNext()) {
			final Resource child = children.next();
			
			if (child.getName().startsWith(PHONE_NUMBER_PREFIX_NODE_NAME)) {
				final ValueMap childProperties = child.adaptTo(ValueMap.class);
				
				final PhoneNumber phone = new PhoneNumber(childProperties.get(PHONE_ATTRIBUTE, String.class), childProperties.get(LABEL_ATTRIBUTE, String.class));
				
				if (phone.isValid()) {
					result.add(phone);
				}
			}
		}
		return result;
	}
	
	private Map<String, Set<ProfessionalActivity>> extractProfessionalActivities(final String nodeName) {
		final Map<String, Set<ProfessionalActivity>> result = new TreeMap<String, Set<ProfessionalActivity>>();
		
		Set<ProfessionalActivity> setCommittees = new TreeSet<ProfessionalActivity>();
		Set<ProfessionalActivity> setEditorships = new TreeSet<ProfessionalActivity>();
		Set<ProfessionalActivity> setEventsAdministration = new TreeSet<ProfessionalActivity>();
		Set<ProfessionalActivity> setEventsParticipation = new TreeSet<ProfessionalActivity>();
		Set<ProfessionalActivity> setPositions = new TreeSet<ProfessionalActivity>(); // External then Internal
		Set<ProfessionalActivity> setFellowships = new TreeSet<ProfessionalActivity>();
		Set<ProfessionalActivity> setReviewPublications = new TreeSet<ProfessionalActivity>();
		Set<ProfessionalActivity> setReviewGrants = new TreeSet<ProfessionalActivity>();
		Set<ProfessionalActivity> setMemberships = new TreeSet<ProfessionalActivity>();
		Set<ProfessionalActivity> setConsultancies = new TreeSet<ProfessionalActivity>();
		Set<ProfessionalActivity> setPartnerships = new TreeSet<ProfessionalActivity>();
		Set<ProfessionalActivity> setFieldworks = new TreeSet<ProfessionalActivity>();
		
		
		final Resource professionalActivitiesResource = this.resource.getChild(nodeName);
		
		if (professionalActivitiesResource == null) {
			return result;
		}
		
		final Iterator<Resource> children = professionalActivitiesResource.listChildren();
		
		while (children.hasNext()) {
			final Resource child = children.next();
			
			if (child.getName().startsWith(PROFESSIONAL_ACTIVITIES_PREFIX_NODE_NAME)) {
				final ValueMap childProperties = child.adaptTo(ValueMap.class);
				final String type = childProperties.get(TYPE_ATTRIBUTE, String.class);
				
				final String url = childProperties.get(URL_ATTRIBUTE, String.class);
				final String title = childProperties.get(TITLE_ATTRIBUTE, String.class);
				final String yearStartDate = childProperties.get(START_DATE_YEAR_NAME_ATTRIBUTE, String.class);
				final String monthStartDate = childProperties.get(START_DATE_MONTH_NAME_ATTRIBUTE, String.class);
				final String dayStartDate = childProperties.get(START_DATE_DAY_NAME_ATTRIBUTE, String.class);
				final String yearEndDate = childProperties.get(END_DATE_YEAR_NAME_ATTRIBUTE, String.class);
				final String monthEndDate = childProperties.get(END_DATE_MONTH_NAME_ATTRIBUTE, String.class);
				final String dayEndDate = childProperties.get(END_DATE_DAY_NAME_ATTRIBUTE, String.class);
				final String reportingDate;
				if (childProperties.get(REPORTING_DATE_ATTRIBUTE, String.class) != null ){
					reportingDate = childProperties.get(REPORTING_DATE_ATTRIBUTE, String.class);
				} else {
					reportingDate = "";
				}
				
				switch (type) {
				
				case PROFESSIONAL_ACTIVITY_TYPE_EXTERNAL_INTERNAL_POSITION:
                    final String inOrExInstitution = childProperties.get(INSTITUTION_ORGANISATIONS_ATTRIBUTE, String.class);

					final String internalOrExternal = childProperties.get(INTERNAL_OR_EXTERNAL_ATTRIBUTE, String.class);
					final String officeHeldType = childProperties.get(OFFICE_HELD_TYPE_ATTRIBUTE, String.class);
					final String officeOtherHeldType = childProperties.get(OFFICE_OTHER_HELD_TYPE_ATTRIBUTE, String.class);
					setPositions.add(new InternalOrExternalPosition(url, title, reportingDate, yearStartDate, monthStartDate, dayStartDate, yearEndDate, monthEndDate, dayEndDate,
							internalOrExternal, officeHeldType, officeOtherHeldType, inOrExInstitution));
					break;	
					
				case PROFESSIONAL_ACTIVITY_TYPE_FELLOWSHIP:
                    final String fellowshipOrganisations = childProperties.get(ORGANISATION_ATTRIBUTE, String.class);
                    
					setFellowships.add(new Fellowship(url, title, reportingDate, yearStartDate, monthStartDate, dayStartDate, yearEndDate, monthEndDate, dayEndDate, 
							fellowshipOrganisations));
					break;
					
				case PROFESSIONAL_ACTIVITY_TYPE_COMMITTEES:
                	final String committeeInstitution = childProperties.get(INSTITUTION_ORGANISATIONS_ATTRIBUTE, String.class);
                	
                	final String committeeRole = childProperties.get(COMMITTEE_ROLE_ATTRIBUTE, String.class);
					setCommittees.add(new Committee(url, title, reportingDate, yearStartDate, monthStartDate, dayStartDate, yearEndDate, monthEndDate, dayEndDate, 
							committeeRole, committeeInstitution));
					break;
					
				case PROFESSIONAL_ACTIVITY_TYPE_MEMBERSHIP:
                    final String membershipInstitution = childProperties.get(INSTITUTION_ORGANISATIONS_ATTRIBUTE, String.class);
                    
                    final String membershipRole = childProperties.get(MEMBERSHIP_ROLE_ATTRIBUTE, String.class);
                    setMemberships.add(new Membership(url, title, reportingDate, yearStartDate, monthStartDate, dayStartDate, yearEndDate, monthEndDate, dayEndDate,
                    		membershipRole, membershipInstitution));
                    break; 
                    
				case PROFESSIONAL_ACTIVITY_TYPE_EDITORSHIP:
					final String editorialRole = childProperties.get(EDITORSHIP_ROLE_ATTRIBUTE, String.class);
					final String editorialPublisher = childProperties.get(C_TEXT_1_ATTRIBUTE, String.class);
					setEditorships.add(new Editorship(url, title, reportingDate, yearStartDate, monthStartDate, dayStartDate, yearEndDate, monthEndDate, dayEndDate,
							editorialRole, editorialPublisher));
					break;
					
				case PROFESSIONAL_ACTIVITY_TYPE_REVIEW_REFEREE_PUBLICATION:
					final String publication = childProperties.get(C_TEXT_1_ATTRIBUTE, String.class);
					final String publicationType = childProperties.get(PUBLICATION_TYPE_ATTRIBUTE, String.class);
					final String reviewType = childProperties.get(REVIEW_TYPE_ATTRIBUTE, String.class);
					
					setReviewPublications.add(new ReviewerOrRefereePublication(url, title, reportingDate, yearStartDate, monthStartDate, dayStartDate, yearEndDate, monthEndDate, dayEndDate,
							publication, publicationType, reviewType));
					break;
					
				case PROFESSIONAL_ACTIVITY_TYPE_REVIEW_REFEREE_GRANT:
                    final String grantOrganisations = childProperties.get(ORGANISATION_ATTRIBUTE, String.class);
                   
					setReviewGrants.add(new ReviewerOrRefereeGrant(url, title, reportingDate, yearStartDate, monthStartDate, dayStartDate, yearEndDate, monthEndDate, dayEndDate,
							grantOrganisations));
					break;
					
                case PROFESSIONAL_ACTIVITY_TYPE_EVENT_ADMINISTRATION:
			        final String eventOrganisationsInstitution = childProperties.get(INSTITUTION_ORGANISATIONS_ATTRIBUTE, String.class);
			        
                    final String eventOrganisationsRole = childProperties.get(ADMINISTRATIVE_ROLE_ATTRIBUTE, String.class);
                    final String eventOrganisationsType = childProperties.get(EVENT_TYPE_ATTRIBUTE, String.class);
                    setEventsAdministration.add(new EventAdministration(url, title, reportingDate, yearStartDate, monthStartDate, dayStartDate, yearEndDate, monthEndDate, dayEndDate, 
                                        eventOrganisationsRole, eventOrganisationsType, eventOrganisationsInstitution));
                    break;  	
					
				case PROFESSIONAL_ACTIVITY_TYPE_EVENT_PARTICIPATION:
                	final String eventParticipationInstitution = childProperties.get(INSTITUTION_ORGANISATIONS_ATTRIBUTE, String.class);
                	
					final String[] eventParticipationRoles = childProperties.get(PARTICIPATION_ROLES_ATTRIBUTE, String[].class);
					final String eventParticipationType = childProperties.get(EVENT_TYPE_ATTRIBUTE, String.class);
					setEventsParticipation.add(new EventParticipation(url, title, reportingDate, yearStartDate, monthStartDate, dayStartDate, yearEndDate, monthEndDate, dayEndDate, 
							eventParticipationRoles, eventParticipationType, eventParticipationInstitution));
					break;	
					
				case PROFESSIONAL_ACTIVITY_TYPE_CONSULTING:
                    final String consultingOrganisations = childProperties.get(ORGANISATION_ATTRIBUTE, String.class);
					setConsultancies.add(new Consultancy(url, title, reportingDate, yearStartDate, monthStartDate, dayStartDate,
							yearEndDate, monthEndDate, dayEndDate, consultingOrganisations));
					break;
					
					
				case PROFESSIONAL_ACTIVITY_TYPE_PARTNERSHIP:
                    final String partnershipOrganisations = childProperties.get(ORGANISATION_ATTRIBUTE, String.class);
					setPartnerships.add(new Partnership(url, title, reportingDate, yearStartDate, monthStartDate, dayStartDate,
							yearEndDate, monthEndDate, dayEndDate, partnershipOrganisations));
					break;
					
					
				case PROFESSIONAL_ACTIVITY_TYPE_FIELDWORK:
                    final String fieldworkOrganisations = childProperties.get(ORGANISATION_ATTRIBUTE, String.class);
                    final String fieldworkDepartment = childProperties.get(DEPARTMENT_ATTRIBUTE, String.class);
                    final String fieldworkAreaOrRegion = childProperties.get(AREA_OR_REGION, String.class);
                    
					setFieldworks.add(new Fieldwork(url, title, reportingDate, yearStartDate, monthStartDate, dayStartDate,
							yearEndDate, monthEndDate, dayEndDate, fieldworkOrganisations, fieldworkDepartment, fieldworkAreaOrRegion));
					break;
					
				default:
					break;
				}
			}
		}
		
		result.put(PROFESSIONAL_ACTIVITY_TYPE_EXTERNAL_INTERNAL_POSITION, setPositions);
		result.put(PROFESSIONAL_ACTIVITY_TYPE_FELLOWSHIP, setFellowships);
		result.put(PROFESSIONAL_ACTIVITY_TYPE_COMMITTEES, setCommittees);
		result.put(PROFESSIONAL_ACTIVITY_TYPE_MEMBERSHIP, setMemberships);
		result.put(PROFESSIONAL_ACTIVITY_TYPE_EDITORSHIP, setEditorships);
		result.put(PROFESSIONAL_ACTIVITY_TYPE_REVIEW_REFEREE_PUBLICATION, setReviewPublications);
		result.put(PROFESSIONAL_ACTIVITY_TYPE_REVIEW_REFEREE_GRANT, setReviewGrants);
		result.put(PROFESSIONAL_ACTIVITY_TYPE_EVENT_ADMINISTRATION, setEventsAdministration);
		result.put(PROFESSIONAL_ACTIVITY_TYPE_EVENT_PARTICIPATION, setEventsParticipation);
		result.put(PROFESSIONAL_ACTIVITY_TYPE_CONSULTING, setConsultancies);
		result.put(PROFESSIONAL_ACTIVITY_TYPE_PARTNERSHIP, setPartnerships);
		result.put(PROFESSIONAL_ACTIVITY_TYPE_FIELDWORK, setFieldworks);
		
		
		return result;
	}
	
	public Set<ProfessionalActivity> getProfessionalActivitySet(Map<String, Set<ProfessionalActivity>> activities, String professionalActivity){
		Set<ProfessionalActivity> result = activities.get(professionalActivity);
		return result;
	}
	
	public String getIorEPositions(Map<String, Set<ProfessionalActivity>> activities, boolean externalPosition){
		StringBuilder result = new StringBuilder(); 
		StringBuilder aux = new StringBuilder(); 
		Set<ProfessionalActivity> setPositions = getProfessionalActivitySet(activities, PROFESSIONAL_ACTIVITY_TYPE_EXTERNAL_INTERNAL_POSITION);
		if (!setPositions.isEmpty()){ 
			if (externalPosition){
				for (final ProfessionalActivity activity: setPositions) { 
					String[] parameters = {
							ScientistProfileHelper.PROFESSIONAL_ACTIVITY_PARAMETER_EXTERNAL
					};
					aux.append(activity.getFilteredHTMLContent(getLastName() + " " + getInitials(), parameters));
					
				} 
				if (aux.length() > 0) { 
					result.append("<h3>External Positions</h3>");
					result.append(aux);
				} 
			} else {
				for (final ProfessionalActivity activity: setPositions) {
					String[] parameters = {
							ScientistProfileHelper.PROFESSIONAL_ACTIVITY_PARAMETER_INTERNAL
					};
					aux.append(activity.getFilteredHTMLContent(getLastName() + " " + getInitials(), parameters));
				}
				if (aux.length() > 0) {
					result.append("<h3>Internal Positions</h3>");
					result.append(aux);
				}
			}
		} 
		return result.toString();
	}
	
	public String getFellowships(Map<String, Set<ProfessionalActivity>> activities){
		StringBuilder result = new StringBuilder(); 
		Set<ProfessionalActivity> setFellowships = getProfessionalActivitySet(activities, ScientistProfileHelper.PROFESSIONAL_ACTIVITY_TYPE_FELLOWSHIP); 
		if (!setFellowships.isEmpty()) { 
			result.append("<h3>Fellowships</h3>");
			for (final ProfessionalActivity activity: setFellowships) { 
				result.append("<p>");
				result.append(activity.getHTMLContent(getLastName() + " " + getInitials()));
				result.append("</p>");
			} 
		} 
		return result.toString();
	}
	
	public String getCommittees(Map<String, Set<ProfessionalActivity>> activities){
		StringBuilder result = new StringBuilder(); 
		Set<ProfessionalActivity> setCommittees = getProfessionalActivitySet(activities, ScientistProfileHelper.PROFESSIONAL_ACTIVITY_TYPE_COMMITTEES); 
		if (!setCommittees.isEmpty()) { 
			result.append("<h3>Committees</h3>");
			for (final ProfessionalActivity activity: setCommittees) { 
				result.append("<p>");
				result.append(activity.getHTMLContent(getLastName() + " " + getInitials()));
				result.append("</p>");
			} 
		} 
		return result.toString();
	}
	
	public String getMemberships(Map<String, Set<ProfessionalActivity>> activities){
		StringBuilder result = new StringBuilder(); 
		Set<ProfessionalActivity> setMemberships = getProfessionalActivitySet(activities, ScientistProfileHelper.PROFESSIONAL_ACTIVITY_TYPE_MEMBERSHIP); 
		if (!setMemberships.isEmpty()) { 
			result.append("<h3>Memberships</h3>");
			for (final ProfessionalActivity activity: setMemberships) { 
				result.append("<p>");
				result.append(activity.getHTMLContent(getLastName() + " " + getInitials()));
				result.append("</p>");
			} 
		} 
		return result.toString();
	}
	
	public String getEditorships(Map<String, Set<ProfessionalActivity>> activities){
		StringBuilder result = new StringBuilder(); 
		Set<ProfessionalActivity> setEditorships = getProfessionalActivitySet(activities, ScientistProfileHelper.PROFESSIONAL_ACTIVITY_TYPE_EDITORSHIP); 
		if (!setEditorships.isEmpty()) { 
			result.append("<h3>Editorships</h3>");
			for (final ProfessionalActivity activity: setEditorships) { 
				result.append("<p>");
				result.append(activity.getHTMLContent(getLastName() + " " + getInitials()));
				result.append("</p>");
			} 
		} 
		return result.toString();
	}
	
	public String getReviewsRefereed(Map<String, Set<ProfessionalActivity>> activities){
		StringBuilder result = new StringBuilder(); 
		Set<ProfessionalActivity> setPublications = getProfessionalActivitySet(activities, ScientistProfileHelper.PROFESSIONAL_ACTIVITY_TYPE_REVIEW_REFEREE_PUBLICATION); 
		Set<ProfessionalActivity> setGrants = getProfessionalActivitySet(activities, ScientistProfileHelper.PROFESSIONAL_ACTIVITY_TYPE_REVIEW_REFEREE_GRANT); 
		if (!setPublications.isEmpty() || !setGrants.isEmpty()) { 
			result.append("<h3>Reviewer / referee</h3>");
				if (!setPublications.isEmpty()) { 
					result.append("<h4>Publications</h4>");
						for (final ProfessionalActivity activity: setPublications) { 
							result.append("<p>");
							result.append(activity.getHTMLContent(getLastName() + " " + getInitials()));
							result.append("</p>");
						} 
				} 
				// ReviewerReferee / Grants --
				if (!setGrants.isEmpty()) { 
					result.append("<h4>Grants</h4>");
						for (final ProfessionalActivity activity: setGrants) { 
							result.append("<p>");
							result.append(activity.getHTMLContent(getLastName() + " " + getInitials()));
							result.append("</p>");
						} 
				} 
		} 
		return result.toString();
	}
	
	public String getEvents(Map<String, Set<ProfessionalActivity>> activities){
		StringBuilder result = new StringBuilder(); 

		// - Events' variables -
		Set<ProfessionalActivity> setEParticipations = getProfessionalActivitySet(activities, ScientistProfileHelper.PROFESSIONAL_ACTIVITY_TYPE_EVENT_PARTICIPATION);
		Set<ProfessionalActivity> setEAdministrations = getProfessionalActivitySet(activities, ScientistProfileHelper.PROFESSIONAL_ACTIVITY_TYPE_EVENT_ADMINISTRATION);
		StringBuilder eventsAdministrationStringBuilder = new StringBuilder();
		StringBuilder eventsWorkshopStringBuilder = new StringBuilder();
		StringBuilder otherEventStringBuilder = new StringBuilder();

		// - Events / Organisation -
		if (!setEAdministrations.isEmpty()) {
			for (final ProfessionalActivity activity: setEAdministrations) {
				eventsAdministrationStringBuilder.append(activity.getHTMLContent(getLastName() + " " + getInitials()));
			}
		}
		
		// - Events / Participation -
		if (!setEParticipations.isEmpty()) {
			// - Events / Participation / Conference Attendance -
			for (final ProfessionalActivity activity: setEParticipations) {
				String[] parameters = {
					ScientistProfileHelper.PROFESSIONAL_ACTIVITY_PARAMETER_CONFERENCE,
					ScientistProfileHelper.PROFESSIONAL_ACTIVITY_PARAMETER_MEETING,
					ScientistProfileHelper.PROFESSIONAL_ACTIVITY_PARAMETER_COLLOQUIUM,
					ScientistProfileHelper.PROFESSIONAL_ACTIVITY_PARAMETER_CONGRESS,
					ScientistProfileHelper.PROFESSIONAL_ACTIVITY_PARAMETER_CONVENTION,
					ScientistProfileHelper.PROFESSIONAL_ACTIVITY_PARAMETER_SYMPOSIUM
				};
				otherEventStringBuilder.append(activity.getFilteredHTMLContent(getLastName() + " " + getInitials(), parameters));
			}
			
			// - Events / Participation / Workshop -
			for (final ProfessionalActivity activity: setEParticipations) {
										String[] parameters = {
						ScientistProfileHelper.PROFESSIONAL_ACTIVITY_PARAMETER_WORKSHOP
					};
					eventsWorkshopStringBuilder.append(activity.getFilteredHTMLContent(getLastName() + " " + getInitials(), parameters));
			}
		}
		
		// - Displaying Events -
		if (eventsWorkshopStringBuilder.length() > 0 || otherEventStringBuilder.length() > 0 || eventsAdministrationStringBuilder.length() > 0) {
			result.append("<h3>Events</h3>");
			if (otherEventStringBuilder.length() > 0) {
				result.append("<h4>Conference Attendance</h4>");
				result.append(otherEventStringBuilder);
			}
			if (eventsWorkshopStringBuilder.length() > 0) {
				result.append("<h4>Workshop Attendance</h4>");
				result.append(eventsWorkshopStringBuilder);
			}
			if (eventsAdministrationStringBuilder.length() > 0) {
				result.append("<h4>Organisation</h4>");
				result.append(eventsAdministrationStringBuilder);
			}
		}
		
		return result.toString();
	}
	
	public boolean displayConsultancies(Resource resource){
		boolean res = false;
		StringBuilder aux = new StringBuilder();
		final ScientistProfileHelper helper = new ScientistProfileHelper(resource);
		final Map<String, Set<ProfessionalActivity>> activities = helper.getProfessionalActivities();

		if (activities != null && !activities.isEmpty()) {
			aux.append(helper.getConsultancies(activities));
			if (aux.length() > 0){
				res = true;
			}
		}
		return res;
	}
	
	public String getConsultancies(Map<String, Set<ProfessionalActivity>> activities){
		StringBuilder result = new StringBuilder(); 
		Set<ProfessionalActivity> setConsultancies = getProfessionalActivitySet(activities, ScientistProfileHelper.PROFESSIONAL_ACTIVITY_TYPE_CONSULTING); 
		if (!setConsultancies.isEmpty()) { 
			result.append("<h3>Consultancy</h3>");
			for (final ProfessionalActivity activity: setConsultancies) { 
				result.append("<p>");
				result.append(activity.getHTMLContent(getLastName() + " " + getInitials()));
				result.append("</p>");
			} 
		} 
		return result.toString();
	}
	
	public boolean displayPartnerships(Resource resource){
		boolean res = false;
		StringBuilder aux = new StringBuilder();
		final ScientistProfileHelper helper = new ScientistProfileHelper(resource);
		final Map<String, Set<ProfessionalActivity>> activities = helper.getProfessionalActivities();

		if (activities != null && !activities.isEmpty()) {
			aux.append(helper.getPartnerships(activities));
			if (aux.length() > 0){
				res = true;
			}
		}
		return res;
	}
	
	public String getPartnerships(Map<String, Set<ProfessionalActivity>> activities){
		StringBuilder result = new StringBuilder(); 
		Set<ProfessionalActivity> setPartnerships = getProfessionalActivitySet(activities, ScientistProfileHelper.PROFESSIONAL_ACTIVITY_TYPE_PARTNERSHIP); 
		if (!setPartnerships.isEmpty()) { 
			result.append("<h3>Partnership</h3>");
			for (final ProfessionalActivity activity: setPartnerships) { 
				result.append("<p>");
				result.append(activity.getHTMLContent(getLastName() + " " + getInitials()));
				result.append("</p>");
			} 
		} 
		return result.toString();
	}
	
	public boolean displayFieldworks(Resource resource){
		boolean res = false;
		StringBuilder aux = new StringBuilder();
		final ScientistProfileHelper helper = new ScientistProfileHelper(resource);
		final Map<String, Set<ProfessionalActivity>> activities = helper.getProfessionalActivities();

		if (activities != null && !activities.isEmpty()) {
			aux.append(helper.getFieldworks(activities));
			if (aux.length() > 0){
				res = true;
			}
		}
		return res;
	}
	
	public String getFieldworks(Map<String, Set<ProfessionalActivity>> activities){
		StringBuilder result = new StringBuilder(); 
		Set<ProfessionalActivity> setFieldworks = getProfessionalActivitySet(activities, ScientistProfileHelper.PROFESSIONAL_ACTIVITY_TYPE_FIELDWORK); 
		if (!setFieldworks.isEmpty()) { 
			result.append("<h3>Fieldwork</h3>");
			for (final ProfessionalActivity activity: setFieldworks) { 
				result.append("<p>");
				result.append(activity.getHTMLContent(getLastName() + " " + getInitials()));
				result.append("</p>");
			} 
		} 
		return result.toString();
	}
	
	public boolean displayProfessionalActivitiesTab(Resource resource){
		boolean res = false;
		StringBuilder aux = new StringBuilder();
		final ScientistProfileHelper helper = new ScientistProfileHelper(resource);
		final Map<String, Set<ProfessionalActivity>> activities = helper.getProfessionalActivities();
		
		if (activities != null && !activities.isEmpty()) {
			aux.append(helper.getIorEPositions(activities, true));
			aux.append(helper.getFellowships(activities));
			aux.append(helper.getCommittees(activities));
			aux.append(helper.getMemberships(activities));
			aux.append(helper.getEditorships(activities));
			aux.append(helper.getReviewsRefereed(activities));
			aux.append(helper.getEvents(activities));
			aux.append(helper.getIorEPositions(activities, false));
			if (aux.length() > 0){
				res = true;
			}
		}
		return res;
	}

	
	public boolean displayPublicationsTab(Resource resource) {
		final ScientistProfileHelper helper = new ScientistProfileHelper(resource);
		final Set<Publication> publications = helper.getPublications();
		boolean res = false;
		if (publications != null && !publications.isEmpty()) {
			res = true;
		}
		return res;
	}
	
	public boolean displayGroupsAndSpecialismsBox(Resource resource, SlingScriptHelper sling) {
		final ScientistProfileHelper helper = new ScientistProfileHelper(resource);
		boolean res = false;
		if ( !res && helper.getSpecialisms() != null) {
			res = true;
		}
		if ( !res && helper.hasGroup() ){
			final ScientistsGroupsService groupService = sling.getService(ScientistsGroupsService.class);
			final Set<Scientist> groupScientists = groupService.getGroupScientists(resource);
			if (!groupScientists.isEmpty()) {
				res = true;
			}
		}
		return res;
	}
	

	
	/*
	 * ##################
	 * #### PROJECTS ####
	 * ##################
	 */

	private Map<String, Set<Project>> extractProjects(final String nodeName) {
		final Map<String, Set<Project>> result = new TreeMap<String, Set<Project>>();
		
		Set<Project> setProjects = new TreeSet<Project>();
		
		final Resource projectResource = this.resource.getChild(nodeName);
		
		if (projectResource == null) {
			return result;
		}
		
		final Iterator<Resource> children = projectResource.listChildren();
		
		while (children.hasNext()) {
			final Resource child = children.next();
			
			if (child.getName().startsWith(PROJECTS_PREFIX_NODE_NAME)) {
				final ValueMap childProperties = child.adaptTo(ValueMap.class);
				
				final String type = childProperties.get(TYPE_ATTRIBUTE, String.class);
				final String yearStartDate = childProperties.get(START_DATE_YEAR_NAME_ATTRIBUTE, String.class);
				final String monthStartDate = childProperties.get(START_DATE_MONTH_NAME_ATTRIBUTE, String.class);
				final String dayStartDate = childProperties.get(START_DATE_DAY_NAME_ATTRIBUTE, String.class);
				final String yearEndDate = childProperties.get(END_DATE_YEAR_NAME_ATTRIBUTE, String.class);
				final String monthEndDate = childProperties.get(END_DATE_MONTH_NAME_ATTRIBUTE, String.class);
				final String dayEndDate = childProperties.get(END_DATE_DAY_NAME_ATTRIBUTE, String.class);
				final String url = childProperties.get(NHM_URL, String.class);
				final String name = childProperties.get(NAME_ATTRIBUTE, String.class);
				final String collaborator = childProperties.get(EXTERNAL_COLLABORATORS, String.class);
				final String fundingSource = childProperties.get(FUNDING_SOURCE_ATTRIBUTE, String.class);
				final String reportingDate;
				if (childProperties.get(REPORTING_DATE_ATTRIBUTE, String.class) != null ){
					reportingDate = childProperties.get(REPORTING_DATE_ATTRIBUTE, String.class);
				} else {
					reportingDate = "";
				}
				
//				Uncomment when more types of projects are present
//				switch (type) {
//				case PROJECT_TYPE_PROJECT:
					setProjects.add(new ProjectType(url, name, reportingDate, yearStartDate, monthStartDate, dayStartDate,
							yearEndDate, monthEndDate, dayEndDate, fundingSource, collaborator));
//					break;
//					
//				default:
//					break;
//				}
			}
		}
		
		result.put(PROJECT_TYPE_PROJECT, setProjects);
		
		return result;
	}
	
	public Set<Project> getProjectSet(Map<String, Set<Project>> projects, String project){
		Set<Project> result = projects.get(project);
		return result;
	}
	
	
	public String getProjects(Map<String, Set<Project>> project){
		StringBuilder result = new StringBuilder(); 
		Set<Project> setProjects = getProjectSet(project, ScientistProfileHelper.PROJECT_TYPE_PROJECT); 
		if (!setProjects.isEmpty()) { 
			result.append("<h3>Other projects</h3>");
			for (final Project projectType: setProjects) { 
				result.append("<p>");
				result.append(projectType.getHTMLContent(getLastName() + " " + getInitials()));
				result.append("</p>");
			} 
		} 
		return result.toString();
	}
	
	public boolean displayProjects(Resource resource){
		boolean res = false;
		StringBuilder aux = new StringBuilder();
		final ScientistProfileHelper helper = new ScientistProfileHelper(resource);
		final Map<String, Set<Project>> projects = helper.getProjects();
		
		if (projects != null && !projects.isEmpty()) {
			aux.append(helper.getProjects(projects));
			if (aux.length() > 0){
				res = true;
			}
		}
		
		return res;
	}
	
	public boolean displayProjectsTab(Resource resource){
		boolean res = false;
		StringBuilder aux = new StringBuilder();
		final ScientistProfileHelper helper = new ScientistProfileHelper(resource);
		final Map<String, Set<ProfessionalActivity>> activities = helper.getProfessionalActivities();
		
		if ( displayGrants(resource) ){
			res = true;
		}
		
		if ( displayProjects(resource) ) {
			res = true;
		}

		if (activities != null && !activities.isEmpty()) {
			aux.append(helper.getConsultancies(activities));
			aux.append(helper.getPartnerships(activities));
			aux.append(helper.getFieldworks(activities));
			if (aux.length() > 0){
				res = true;
			}
		}
		
		return res;
	}
	
	
	/*
	 * ################
	 * #### GRANTS ####
	 * ################
	 */
	
	private Map<String, Set<Grant>> extractGrants(final String nodeName) {
		final Map<String, Set<Grant>> result = new TreeMap<String, Set<Grant>>();
		
		Set<Grant> setGrants = new TreeSet<Grant>();
		
		final Resource projectResource = this.resource.getChild(nodeName);
		
		if (projectResource == null) {
			return result;
		}
		
		final Iterator<Resource> children = projectResource.listChildren();
		
		while (children.hasNext()) {
			final Resource child = children.next();
			
			if (child.getName().startsWith(GRANT_PREFIX_NODE_NAME)) {
				final ValueMap childProperties = child.adaptTo(ValueMap.class);
				
				final String type = childProperties.get(TYPE_ATTRIBUTE, String.class);
				final String yearStartDate = childProperties.get(START_DATE_YEAR_NAME_ATTRIBUTE, String.class);
				final String monthStartDate = childProperties.get(START_DATE_MONTH_NAME_ATTRIBUTE, String.class);
				final String dayStartDate = childProperties.get(START_DATE_DAY_NAME_ATTRIBUTE, String.class);
				final String yearEndDate = childProperties.get(END_DATE_YEAR_NAME_ATTRIBUTE, String.class);
				final String monthEndDate = childProperties.get(END_DATE_MONTH_NAME_ATTRIBUTE, String.class);
				final String dayEndDate = childProperties.get(END_DATE_DAY_NAME_ATTRIBUTE, String.class);
				final String reportingDate;
				if (childProperties.get(REPORTING_DATE_ATTRIBUTE, String.class) != null ){
					reportingDate = childProperties.get(REPORTING_DATE_ATTRIBUTE, String.class);
				} else {
					reportingDate = "";
				}
				
				final String proposalTitle = childProperties.get(PROPOSAL_TITLE, String.class);
				final String[] principalInvestigator = childProperties.get(ROLE_PRINCIPAL_INVESTIGATOR, String[].class);
				List<String> principalsList = new ArrayList<>();
				if (principalInvestigator != null) {
					//Collections.addAll(authorsSet, authors);
					principalsList = Arrays.asList(principalInvestigator);
				}
				final String coInvestigator = childProperties.get(ROLE_CO_INVESTIGATOR, String.class);
				List<String> coInvestigatorsList = new ArrayList<>();
				if (coInvestigator != null) {
					//Collections.addAll(authorsSet, authors);
					coInvestigatorsList = Arrays.asList(coInvestigator);
				}
				final String funderName = childProperties.get(FUNDER_NAME, String.class);
				final String funderNameOther = childProperties.get(FUNDER_NAME_OTHER, String.class);
				final String totalAwarded = childProperties.get(TOTAL_VALUE_AWARDED, String.class);
				final String nhmAwarded = childProperties.get(NHM_VALUE_AWARDED, String.class);
				
				
//				Uncomment when more types of grants are present
//				switch (type) {
//				case GRANT_TYPE_GRANT:
					setGrants.add(new Grant(proposalTitle, reportingDate, yearStartDate, monthStartDate, dayStartDate,
							yearEndDate, monthEndDate, dayEndDate, principalsList, coInvestigatorsList, funderName, 
							funderNameOther, totalAwarded, nhmAwarded));
//					break;
//					
//				default:
//					break;
//				}
			}
		}
		
		result.put(GRANT_TYPE_GRANT, setGrants);
		
		return result;
	}
	
	public Set<Grant> getGrantSet(Map<String, Set<Grant>> grants, String grant){
		Set<Grant> result = grants.get(grant);
		return result;
	}
	
	public String getGrants(Map<String, Set<Grant>> grant){
		StringBuilder result = new StringBuilder(); 
		Set<Grant> setGrants = getGrantSet(grant, ScientistProfileHelper.GRANT_TYPE_GRANT);
		if (!setGrants.isEmpty()) { 
			result.append("<h3>Grants</h3>");
			for (final Grant grantType: setGrants) { 
				result.append("<p>");
				result.append(grantType.getHTMLContent(getLastName() + " " + getInitials()));
				result.append("</p>");
			} 
		} 
		return result.toString();
	}
	
	public boolean displayGrants(Resource resource){
		boolean res = false;
		StringBuilder aux = new StringBuilder();
		final ScientistProfileHelper helper = new ScientistProfileHelper(resource);
		final Map<String, Set<Grant>> grants = helper.getGrants();
		
		if (grants != null && !grants.isEmpty()) {
			aux.append(helper.getGrants(grants));
			if (aux.length() > 0){
				res = true;
			}
		}
		return res;
	}
}
