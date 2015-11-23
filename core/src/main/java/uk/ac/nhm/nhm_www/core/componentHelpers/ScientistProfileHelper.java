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
import uk.ac.nhm.nhm_www.core.model.science.EmailAddress;
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
import uk.ac.nhm.nhm_www.core.model.science.proactivities.Award;
import uk.ac.nhm.nhm_www.core.model.science.proactivities.Committee;
import uk.ac.nhm.nhm_www.core.model.science.proactivities.Consultancy;
import uk.ac.nhm.nhm_www.core.model.science.proactivities.Editorship;
import uk.ac.nhm.nhm_www.core.model.science.proactivities.EventAdministration;
import uk.ac.nhm.nhm_www.core.model.science.proactivities.EventParticipation;
import uk.ac.nhm.nhm_www.core.model.science.proactivities.Fellowship;
import uk.ac.nhm.nhm_www.core.model.science.proactivities.Fieldwork;
import uk.ac.nhm.nhm_www.core.model.science.proactivities.GuestPresentation;
import uk.ac.nhm.nhm_www.core.model.science.proactivities.InternalOrExternalPosition;
import uk.ac.nhm.nhm_www.core.model.science.proactivities.MediaBroadcast;
import uk.ac.nhm.nhm_www.core.model.science.proactivities.MediaInterview;
import uk.ac.nhm.nhm_www.core.model.science.proactivities.Membership;
import uk.ac.nhm.nhm_www.core.model.science.proactivities.Partnership;
import uk.ac.nhm.nhm_www.core.model.science.proactivities.ProfessionalActivity;
import uk.ac.nhm.nhm_www.core.model.science.proactivities.ResearchPresentation;
import uk.ac.nhm.nhm_www.core.model.science.proactivities.ReviewerOrRefereeGrant;
import uk.ac.nhm.nhm_www.core.model.science.proactivities.ReviewerOrRefereePublication;
import uk.ac.nhm.nhm_www.core.model.science.projects.ProjectTemplate;
import uk.ac.nhm.nhm_www.core.model.science.projects.ProjectType;
import uk.ac.nhm.nhm_www.core.model.science.teaching.CourseDeveloped;
import uk.ac.nhm.nhm_www.core.model.science.teaching.Examiner;
import uk.ac.nhm.nhm_www.core.model.science.teaching.ProgramDeveloped;
import uk.ac.nhm.nhm_www.core.model.science.teaching.Supervision;
import uk.ac.nhm.nhm_www.core.model.science.teaching.TaughtCourse;
import uk.ac.nhm.nhm_www.core.model.science.teaching.TeachingActivityTemplate;
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
	public static final String PREFERRED_ATTRIBUTE		  = "isPreferred";
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
	public static final String LOCATION_ORGANISATIONS_ATTRIBUTE		= "locationOrganisations";
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
	public static final String RELEASE_DATE_DAY_NAME_ATTRIBUTE		= "releaseDay";
	public static final String RELEASE_DATE_MONTH_NAME_ATTRIBUTE	= "releaseMonth";
	public static final String RELEASE_DATE_YEAR_NAME_ATTRIBUTE		= "releaseYear";
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
	public static final String AREA_OR_REGION_ATTRIBUTE				= "areaOrRegion";
	public static final String EVENT_NAME_ATTRIBUTE					= "eventName";
	public static final String INVITED_ATTRIBUTE					= "invited";
	public static final String KEYNOTE_ATTRIBUTE					= "keynote";
	public static final String DESCRIPTION_ATTRIBUTE				= "description";
	public static final String INTERVIEWER_NAME_ATTRIBUTE			= "interviewerName";
	
	
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
	
	//Teaching Activities
	public static final String COURSE_LEVEL_ATTRIBUTE 				= "courseLevel";
	public static final String DEGREE_TYPE_ATTRIBUTE 				= "degreeType";
	public static final String OTHER_DEGREE_TYPE_ATTRIBUTE 			= "otherDegreeType";
	public static final String SUPERVISORY_ROLE_ATTRIBUTE 			= "supervisoryRole";
	public static final String CO_CONTRIBUTORS_ATTRIBUTE 			= "coContributors";
	public static final String PERSON_ATTRIBUTE			 			= "person";
	public static final String DEGREE_SUBJECT_ATTRIBUTE	 			= "degreeSubject";
	public static final String FUNDER_ATTRIBUTE			 			= "funder";
	public static final String EXAMINATION_ROLE_ATTRIBUTE			= "examinationRole";
	public static final String EXAMINATION_LEVEL_ATTRIBUTE			= "examinationLevel";
	public static final String EXAMINATION_INSTITUTIONS_ATTRIBUTE 	= "institutionOrganisations";
	public static final String DEGREE_LEVEL_ATTRIBUTE				= "degreeLevel";
	public static final String PARTNER_ATTRIBUTE					= "partner";
	public static final String RELEASE_DATE_ATTRIBUTE				= "releaseDate";
	
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
	public static final String EMAIL_ADDRESS_PREFIX_NODE_NAME = "emailaddress";
	public static final String EMAIL_ADDRESSES_NODE_NAME	  = "emailaddresses";
	private static final String EMAIL_ADDRESSES_NODE_PATH 	  = PERSONAL_INFORMATION_NODE_NAME + "/" + EMAIL_ADDRESSES_NODE_NAME;
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

	/* Impact and Outreach */

	public static final String PROFESSIONAL_ACTIVITY_TYPE_AWARD						= "Award";
	public static final String PROFESSIONAL_ACTIVITY_TYPE_RESEARCH_PRESENTATION		= "ResearPresentation";
	public static final String PROFESSIONAL_ACTIVITY_TYPE_GUEST_PRESENTATION		= "GuestPresentation";
	public static final String PROFESSIONAL_ACTIVITY_TYPE_INTERNAL_EXTERNAL			= "InternalExternalImpactOutreach";
	public static final String PROFESSIONAL_ACTIVITY_TYPE_MEDIA_BROADCAST			= "MediaBroadcaster";
	public static final String PROFESSIONAL_ACTIVITY_TYPE_MEDIA_INTERVIEW			= "MediaInterview";
	public static final String PROFESSIONAL_ACTIVITY_PARAMETER_PUBLIC_ENGAGEMENT	= "Public Engagement";
	public static final String EVENT_START_DATE_DAY_NAME_ATTRIBUTE					= "eStartDay";
	public static final String EVENT_START_DATE_MONTH_NAME_ATTRIBUTE				= "eStartMonth";
	public static final String EVENT_START_DATE_YEAR_NAME_ATTRIBUTE					= "eStartYear";
	
	/* Teaching Activities */
	public  static final String TEACHING_ACTIVITIES_PREFIX_NODE_NAME 		= "teachingActivity";
	public  static final String TEACHING_ACTIVITIES_NODE_NAME  				= "teachingActivities";
	public  static final String ASSOCIATED_TEACHING_ACTIVITIES_NODE_NAME  	= "associated";
	private static final String TEACHING_ACTIVITIES_NODE_PATH			  	= TEACHING_ACTIVITIES_NODE_NAME + "/" + ASSOCIATED_TEACHING_ACTIVITIES_NODE_NAME;
	
	public static final String TEACHING_ACTIVITIES_TYPE_SUPERVISION			= "Supervision";
	public static final String TEACHING_ACTIVITIES_TYPE_TAUGHT_COURSES		= "Taught Courses";
	public static final String TEACHING_ACTIVITIES_TYPE_EXAMINER			= "Examiner";
	public static final String TEACHING_ACTIVITIES_TYPE_PROGRAM_DEVELOPED	= "Program Developed";
	public static final String TEACHING_ACTIVITIES_TYPE_COURSES_DEVELOPED	= "Courses Developed";
	
	/* Projects */
	public static final String PROJECTS_PREFIX_NODE_NAME 				= "project";
	public static final String PROJECTS_NODE_NAME  						= "projects";
	public static final String PROJECTS_CONTAINER_NODE_NAME  			= "container";
	private static final String PROJECTS_NODE_PATH			  	= PROJECTS_NODE_NAME + "/" + PROJECTS_CONTAINER_NODE_NAME;
	
	public static final String PROFESSIONAL_ACTIVITY_TYPE_CONSULTING	= "Consulting";
	public static final String PROFESSIONAL_ACTIVITY_TYPE_PARTNERSHIP	= "Partnership";
	public static final String PROFESSIONAL_ACTIVITY_TYPE_FIELDWORK 	= "Fieldwork";
	
	public static final String PROJECT_TYPE_PROJECT						= "Project";
	public static final String PROJECT_NODE_TYPE						= "nodeType";
	public static final String PROJECT_NODETYPE_CHAMPION				= "Champion";
	public static final String PROJECT_NODETYPE_RESEARCHER				= "Researcher";
	public static final String PROJECT_NODETYPE_MANAGER					= "Manager";
	public static final String PROJECT_NODETYPE_MEMBER					= "Member";
	public static final String PROJECT_NODETYPE_LEADER					= "Leader";
	public static final String PROJECT_NODETYPE_FUNDEDBY				= "Funded by";
	
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
	
	public List<EmailAddress> getEmails() {
		return this.extractEmails(EMAIL_ADDRESSES_NODE_PATH);
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
	
	private List<EmailAddress> extractEmails(final String nodeName) {
		final List<EmailAddress> result = new ArrayList<EmailAddress>();
		
		final Resource emailsResource = this.resource.getChild(nodeName);
		
		if(emailsResource == null) {
			return result;
		}
		
		final Iterator<Resource> children = emailsResource.listChildren();
		
		while(children.hasNext()) {
			final Resource child = children.next();
			
			if(child.getName().startsWith(EMAIL_ADDRESS_PREFIX_NODE_NAME)) {
				final ValueMap childProperties = child.adaptTo(ValueMap.class);
				
				final EmailAddress email = new EmailAddress(childProperties.get(EMAIL_ATTRIBUTE, String.class), childProperties.get(LABEL_ATTRIBUTE, String.class));
				
				//TODO - validation
				result.add(email);
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
	 * ## Publications ##
	 * ##################
	 */
	
	public Set<Publication> getPublications() {
		return this.extractPublications(PUBLICATIONS_NODE_PATH);
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
				final String publicationYear = childProperties.get(PUBLICATION_DATE_ATTRIBUTE, String.class);
				final String type = childProperties.get(TYPE_ATTRIBUTE, String.class);
				//final boolean preferred = childProperties.get(PREFERRED_ATTRIBUTE, false);
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
					final String bookBeginPage = childProperties.get(START_PAGE_ATTRIBUTE, String.class);
					final String bookEndPage = childProperties.get(END_PAGE_ATTRIBUTE, String.class);
					final String page	   	   = childProperties.get(PAGE_COUNT_ATTRIBUTE, String.class);
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
					final String beginPage = childProperties.get(START_PAGE_ATTRIBUTE, String.class);
					final String endPage   = childProperties.get(END_PAGE_ATTRIBUTE, String.class);
					final String chapterpage	= childProperties.get(PAGE_COUNT_ATTRIBUTE, String.class);
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
					final String conferenceBeginPage = childProperties.get(START_PAGE_ATTRIBUTE, String.class);
					final String conferenceEndPage = childProperties.get(END_PAGE_ATTRIBUTE, String.class);
					final String conferencePage = childProperties.get(PAGE_COUNT_ATTRIBUTE, String.class);
					final String conferenceDoiText = childProperties.get(DOI_TEXT_ATTRIBUTE, String.class);
					final String conferenceDoiLink = childProperties.get(DOI_LINK_ATTRIBUTE, String.class);
					final String conferenceName = childProperties.get(CONFERENCE_NAME_ATTRIBUTE, String.class);
					final String conferenceVolume = childProperties.get(VOLUME_ATTRIBUTE, String.class);
					final String conferenceIssue = childProperties.get(ISSUE_ATTRIBUTE, String.class);
					final String conferencePublishedProceedings = childProperties.get(JOURNAL_NAME_ATTRIBUTE, String.class);
					final String startConferenceYear = childProperties.get(START_YEAR_ATTRIBUTE, String.class);
					final String startConferenceMonth= childProperties.get(START_MONTH_ATTRIBUTE, String.class);
					final String startConferenceDay = childProperties.get(START_DAY_ATTRIBUTE, String.class);
					final String endConferenceYear = childProperties.get(START_YEAR_ATTRIBUTE, String.class);
					final String endConferenceMonth= childProperties.get(START_MONTH_ATTRIBUTE, String.class);
					final String endConferenceDay = childProperties.get(START_DAY_ATTRIBUTE, String.class);
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
					final String internetBeginPage = childProperties.get(START_PAGE_ATTRIBUTE, String.class);
					final String internetEndPage = childProperties.get(END_PAGE_ATTRIBUTE, String.class);
					final String internetPage = childProperties.get(PAGE_COUNT_ATTRIBUTE, String.class);
					final String iPublicationMonth = childProperties.get(PUBLICATION_MONTH_ATTRIBUTE, String.class);
					final String iPublicationDay = childProperties.get(PUBLICATION_DAY_ATTRIBUTE, String.class);
					result.add(new InternetPublication(title, authorsList, favorite, publicationYear, iPublicationMonth, iPublicationDay, link, 
							reportingDate, internetPublisher, publisherURL, internetBeginPage, internetEndPage, internetPage));
					break;

				case PUBLICATION_TYPE_ARTICLE:
					final String articleBeginPage = childProperties.get(START_PAGE_ATTRIBUTE, String.class);
					final String articleEndPage = childProperties.get(END_PAGE_ATTRIBUTE, String.class);
					final String journalpage	= childProperties.get(PAGE_COUNT_ATTRIBUTE, String.class);
					final String doiText = childProperties.get(DOI_TEXT_ATTRIBUTE, String.class);
					final String doiLink = childProperties.get(DOI_LINK_ATTRIBUTE, String.class);
					final String volume = childProperties.get(VOLUME_ATTRIBUTE, String.class);
					final String issue = childProperties.get(ISSUE_ATTRIBUTE, String.class);
					final String journalName = childProperties.get(JOURNAL_NAME_ATTRIBUTE, String.class);
					result.add(new JournalArticle(title, authorsList, favorite, publicationYear, link, reportingDate, journalName, volume, issue, 
							articleBeginPage, articleEndPage, journalpage, doiText, doiLink));
					break;

				case PUBLICATION_TYPE_NEWSPAPER_OR_MAGAZINE:
					final String newsmagPublisherURL = childProperties.get(PUBLISHER_URL_ATTRIBUTE, String.class);
					final String newsmagBeginPage = childProperties.get(START_PAGE_ATTRIBUTE, String.class);
					final String newsmagEndPage = childProperties.get(END_PAGE_ATTRIBUTE, String.class);
					final String newsmagPage = childProperties.get(PAGE_COUNT_ATTRIBUTE, String.class);
					final String newsmagDoiText = childProperties.get(DOI_TEXT_ATTRIBUTE, String.class);
					final String newsmagDoiLink = childProperties.get(DOI_LINK_ATTRIBUTE, String.class);
					final String newsmagVolume = childProperties.get(VOLUME_ATTRIBUTE, String.class);
					final String newsmagIssue = childProperties.get(ISSUE_ATTRIBUTE, String.class);
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
					final String otherBeginPage = childProperties.get(START_PAGE_ATTRIBUTE, String.class);
					final String otherEndPage = childProperties.get(END_PAGE_ATTRIBUTE, String.class);
					final String otherPage = childProperties.get(PAGE_COUNT_ATTRIBUTE, String.class);
					final String otherDoiText = childProperties.get(DOI_TEXT_ATTRIBUTE, String.class);
					final String otherDoiLink = childProperties.get(DOI_LINK_ATTRIBUTE, String.class);
					final String otherVolume = childProperties.get(VOLUME_ATTRIBUTE, String.class);
					final String otherIssue = childProperties.get(ISSUE_ATTRIBUTE, String.class);
					final String otherJournalName = childProperties.get(JOURNAL_NAME_ATTRIBUTE, String.class);
					final String otherPublicationMonth = childProperties.get(PUBLICATION_MONTH_ATTRIBUTE, String.class);
					final String otherPublicationDay = childProperties.get(PUBLICATION_DAY_ATTRIBUTE, String.class);
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
					final String reportBeginPage = childProperties.get(START_PAGE_ATTRIBUTE, String.class);
					final String reportEndPage = childProperties.get(END_PAGE_ATTRIBUTE, String.class);
					final String reportPage = childProperties.get(PAGE_COUNT_ATTRIBUTE, String.class);
					final boolean confidential = childProperties.get(CONFIDENTIAL_ATTRIBUTE, false);
					result.add(new Report(title, authorsList, favorite, publicationYear, link, reportingDate, confidential, reportBeginPage, reportEndPage, 
							reportPublisher, reportPublishingPlace, reportPage));
					break;

				case PUBLICATION_TYPE_SCHOLARLY_EDITION:
					final String scholarlyPublisher = childProperties.get(PUBLISHER_ATTRIBUTE, String.class);
					final String scholarlyPublisherURL = childProperties.get(PUBLISHER_URL_ATTRIBUTE, String.class);
					final String scholarlyBeginPage = childProperties.get(START_PAGE_ATTRIBUTE, String.class);
					final String scholarlyEndPage = childProperties.get(END_PAGE_ATTRIBUTE, String.class);
					final String scholarlyPage = childProperties.get(PAGE_COUNT_ATTRIBUTE, String.class);
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
					final String thesisBeginPage = childProperties.get(START_PAGE_ATTRIBUTE, String.class);
					final String thesisEndPage = childProperties.get(END_PAGE_ATTRIBUTE, String.class);
					final String thesisPage = childProperties.get(PAGE_COUNT_ATTRIBUTE, String.class);
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
					final String startExhibitionYear = childProperties.get(START_YEAR_ATTRIBUTE, String.class);
					final String startExhibitionMonth= childProperties.get(START_MONTH_ATTRIBUTE, String.class);
					final String startExhibitionDay = childProperties.get(START_DAY_ATTRIBUTE, String.class);
					final String endExhibitionYear = childProperties.get(START_YEAR_ATTRIBUTE, String.class);
					final String endExhibitionMonth = childProperties.get(START_MONTH_ATTRIBUTE, String.class);
					final String endExhibitionDay = childProperties.get(START_DAY_ATTRIBUTE, String.class);
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

	public boolean displayPublicationsTab(Resource resource) {
		final ScientistProfileHelper helper = new ScientistProfileHelper(resource);
		final Set<Publication> publications = helper.getPublications();
		boolean res = false;
		if (publications != null && !publications.isEmpty()) {
			res = true;
		}
		return res;
	}

	/*
	 * #############################
	 * ## Professional Activities ##
	 * #############################
	 */

	public Map<String, Set<ProfessionalActivity>> getProfessionalActivities() {
		return this.extractProfessionalActivities(PROFESSIONAL_ACTIVITIES_NODE_PATH);
	}

	private Map<String, Set<ProfessionalActivity>> extractProfessionalActivities(final String nodeName) {
		final Map<String, Set<ProfessionalActivity>> result = new TreeMap<String, Set<ProfessionalActivity>>();

		// Professional Activities Tab
		Set<ProfessionalActivity> setPositions = new TreeSet<ProfessionalActivity>(); // External then Internal
		Set<ProfessionalActivity> setFellowships = new TreeSet<ProfessionalActivity>();
		Set<ProfessionalActivity> setCommittees = new TreeSet<ProfessionalActivity>();
		Set<ProfessionalActivity> setMemberships = new TreeSet<ProfessionalActivity>();
		Set<ProfessionalActivity> setEditorships = new TreeSet<ProfessionalActivity>();	
		Set<ProfessionalActivity> setReviewPublications = new TreeSet<ProfessionalActivity>();
		Set<ProfessionalActivity> setReviewGrants = new TreeSet<ProfessionalActivity>();
		Set<ProfessionalActivity> setEventsParticipation = new TreeSet<ProfessionalActivity>();
		Set<ProfessionalActivity> setEventsAdministration = new TreeSet<ProfessionalActivity>();

		// Projects Tab
		Set<ProfessionalActivity> setConsultancies = new TreeSet<ProfessionalActivity>();
		Set<ProfessionalActivity> setPartnerships = new TreeSet<ProfessionalActivity>();
		Set<ProfessionalActivity> setFieldworks = new TreeSet<ProfessionalActivity>();

		// Impact and Outreach Tab
		Set<ProfessionalActivity> setAward = new TreeSet<ProfessionalActivity>();
		Set<ProfessionalActivity> setResearchPresentation = new TreeSet<ProfessionalActivity>();
		Set<ProfessionalActivity> setGuestPresentation = new TreeSet<ProfessionalActivity>();
		Set<ProfessionalActivity> setMediaBroadcast = new TreeSet<ProfessionalActivity>();
		Set<ProfessionalActivity> setMediaInterview = new TreeSet<ProfessionalActivity>();

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
					final String eventParticipationLocations = childProperties.get(LOCATION_ORGANISATIONS_ATTRIBUTE, String.class);
					final String[] eventParticipationRoles = childProperties.get(PARTICIPATION_ROLES_ATTRIBUTE, String[].class);
					final String eventParticipationInternalOrExternal = childProperties.get(INTERNAL_OR_EXTERNAL_ATTRIBUTE, String.class);
					final String eventParticipationType = childProperties.get(EVENT_TYPE_ATTRIBUTE, String.class);
					final String eventYearStartDate = childProperties.get(EVENT_START_DATE_YEAR_NAME_ATTRIBUTE, String.class);
					final String eventMonthStartDate = childProperties.get(EVENT_START_DATE_MONTH_NAME_ATTRIBUTE, String.class);
					final String eventDayStartDate = childProperties.get(EVENT_START_DATE_DAY_NAME_ATTRIBUTE, String.class);
					
					setEventsParticipation.add(new EventParticipation(url, title, reportingDate, yearStartDate, monthStartDate, dayStartDate, yearEndDate, monthEndDate, dayEndDate, 
							eventParticipationRoles, eventParticipationType, eventParticipationInstitution, eventParticipationInternalOrExternal, 
							eventYearStartDate, eventMonthStartDate, eventDayStartDate, eventParticipationLocations));
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
                    final String fieldworkAreaOrRegion = childProperties.get(AREA_OR_REGION_ATTRIBUTE, String.class);
                    
					setFieldworks.add(new Fieldwork(url, title, reportingDate, yearStartDate, monthStartDate, dayStartDate,
							yearEndDate, monthEndDate, dayEndDate, fieldworkOrganisations, fieldworkDepartment, fieldworkAreaOrRegion));
					break;

				case PROFESSIONAL_ACTIVITY_TYPE_AWARD:
			        final String awardInstitutions = childProperties.get(INSTITUTION_ORGANISATIONS_ATTRIBUTE, String.class);
			        
					setAward.add(new Award(url, title, reportingDate, yearStartDate, monthStartDate, dayStartDate,
							yearEndDate, monthEndDate, dayEndDate, awardInstitutions));
					break;
					
				case PROFESSIONAL_ACTIVITY_TYPE_RESEARCH_PRESENTATION:
					final Boolean invited = childProperties.get(INVITED_ATTRIBUTE, Boolean.class);
					final Boolean keynote = childProperties.get(KEYNOTE_ATTRIBUTE, Boolean.class);
					final String eventName = childProperties.get(EVENT_NAME_ATTRIBUTE, String.class);
					final String rpresentationLocations = childProperties.get(LOCATION_ORGANISATIONS_ATTRIBUTE, String.class);
					
					setResearchPresentation.add(new ResearchPresentation(url, title, reportingDate, yearStartDate, monthStartDate, dayStartDate,
							yearEndDate, monthEndDate, dayEndDate, invited, keynote, eventName, rpresentationLocations));
					break;
					
				case PROFESSIONAL_ACTIVITY_TYPE_GUEST_PRESENTATION:
					final Boolean invitedGuest = childProperties.get(INVITED_ATTRIBUTE, Boolean.class);
					final Boolean keynoteGuest = childProperties.get(KEYNOTE_ATTRIBUTE, Boolean.class);
					final String eventNameGuest = childProperties.get(EVENT_NAME_ATTRIBUTE, String.class);
					final String guestLocations = childProperties.get(LOCATION_ORGANISATIONS_ATTRIBUTE, String.class);
					
					setGuestPresentation.add(new GuestPresentation(url, title, reportingDate, yearStartDate, monthStartDate, dayStartDate,
							yearEndDate, monthEndDate, dayEndDate, invitedGuest, keynoteGuest, eventNameGuest, guestLocations));
					break;
					
				case PROFESSIONAL_ACTIVITY_TYPE_MEDIA_BROADCAST:
					final String descriptionBroadcast = childProperties.get(DESCRIPTION_ATTRIBUTE, String.class);
					final String departmentBroadcast = childProperties.get(DEPARTMENT_ATTRIBUTE, String.class);
					final String interviewerNameBroadcast = childProperties.get(INTERVIEWER_NAME_ATTRIBUTE, String.class);
					final String urlLabelBroadcast = childProperties.get(C_TEXT_1_ATTRIBUTE, String.class);
					
					setMediaBroadcast.add(new MediaBroadcast(url, title, reportingDate, yearStartDate, monthStartDate, dayStartDate,
							yearEndDate, monthEndDate, dayEndDate, descriptionBroadcast, departmentBroadcast, interviewerNameBroadcast,
							urlLabelBroadcast));
					break;
					
				case PROFESSIONAL_ACTIVITY_TYPE_MEDIA_INTERVIEW:
					final String descriptionInterview = childProperties.get(DESCRIPTION_ATTRIBUTE, String.class);
					final String departmentInterview = childProperties.get(DEPARTMENT_ATTRIBUTE, String.class);
					final String interviewerNameInterview = childProperties.get(INTERVIEWER_NAME_ATTRIBUTE, String.class);
					final String urlLabelArticle = childProperties.get(C_TEXT_1_ATTRIBUTE, String.class);
					
					setMediaInterview.add(new MediaInterview(url, title, reportingDate, yearStartDate, monthStartDate, dayStartDate,
							yearEndDate, monthEndDate, dayEndDate, descriptionInterview, departmentInterview, interviewerNameInterview, 
							urlLabelArticle));
					break;

				default:
					break;
				}
			}
		}
		
		//Professional Activities Tab
		result.put(PROFESSIONAL_ACTIVITY_TYPE_EXTERNAL_INTERNAL_POSITION, setPositions);
		result.put(PROFESSIONAL_ACTIVITY_TYPE_FELLOWSHIP, setFellowships);
		result.put(PROFESSIONAL_ACTIVITY_TYPE_COMMITTEES, setCommittees);
		result.put(PROFESSIONAL_ACTIVITY_TYPE_MEMBERSHIP, setMemberships);
		result.put(PROFESSIONAL_ACTIVITY_TYPE_EDITORSHIP, setEditorships);
		result.put(PROFESSIONAL_ACTIVITY_TYPE_REVIEW_REFEREE_PUBLICATION, setReviewPublications);
		result.put(PROFESSIONAL_ACTIVITY_TYPE_REVIEW_REFEREE_GRANT, setReviewGrants);
		result.put(PROFESSIONAL_ACTIVITY_TYPE_EVENT_ADMINISTRATION, setEventsAdministration);
		result.put(PROFESSIONAL_ACTIVITY_TYPE_EVENT_PARTICIPATION, setEventsParticipation);
		
		//Projects Tab
		result.put(PROFESSIONAL_ACTIVITY_TYPE_CONSULTING, setConsultancies);
		result.put(PROFESSIONAL_ACTIVITY_TYPE_PARTNERSHIP, setPartnerships);
		result.put(PROFESSIONAL_ACTIVITY_TYPE_FIELDWORK, setFieldworks);
		
		//Impact and Outreach Tab
		result.put(PROFESSIONAL_ACTIVITY_TYPE_AWARD, setAward);
		result.put(PROFESSIONAL_ACTIVITY_TYPE_RESEARCH_PRESENTATION, setResearchPresentation);
		result.put(PROFESSIONAL_ACTIVITY_TYPE_GUEST_PRESENTATION, setGuestPresentation);
		result.put(PROFESSIONAL_ACTIVITY_TYPE_MEDIA_BROADCAST, setMediaBroadcast);
		result.put(PROFESSIONAL_ACTIVITY_TYPE_MEDIA_INTERVIEW, setMediaInterview);
		
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


	/*
	 * ##################
	 * #### Projects ####
	 * ##################
	 */

	public Map<String, Set<ProjectTemplate>> getProjects() {
		return this.extractProjects(PROJECTS_NODE_PATH);
	}

	private Map<String, Set<ProjectTemplate>> extractProjects(final String nodeName) {
		final Map<String, Set<ProjectTemplate>> result = new TreeMap<String, Set<ProjectTemplate>>();

		Set<ProjectTemplate> setProjects = new TreeSet<ProjectTemplate>();

		final Resource projectResource = this.resource.getChild(nodeName);

		if (projectResource == null) {
			return result;
		}

		final Iterator<Resource> children = projectResource.listChildren();

		while (children.hasNext()) {
			final Resource child = children.next();

			if (child.getName().startsWith(PROJECTS_PREFIX_NODE_NAME)) {
				final ValueMap childProperties = child.adaptTo(ValueMap.class);

				final String nodeType = childProperties.get(PROJECT_NODE_TYPE, String.class);
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
							yearEndDate, monthEndDate, dayEndDate, fundingSource, collaborator, nodeType));
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

	public Set<ProjectTemplate> getProjectSet(Map<String, Set<ProjectTemplate>> projects, String project){
		Set<ProjectTemplate> result = projects.get(project);
		return result;
	}


	public String getProjects(Map<String, Set<ProjectTemplate>> project){
		StringBuilder result = new StringBuilder(); 
		Set<ProjectTemplate> setProjects = getProjectSet(project, ScientistProfileHelper.PROJECT_TYPE_PROJECT); 
		if (!setProjects.isEmpty()) { 
			result.append("<h3>Other projects</h3>");
			for (final ProjectTemplate projectType: setProjects) { 
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
		final Map<String, Set<ProjectTemplate>> projects = helper.getProjects();

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
	 * #### Grants ####
	 * ################
	 */

	public Map<String, Set<Grant>> getGrants() {
		return this.extractGrants(GRANT_NODE_PATH);
	}

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


	/*
	 * #########################
	 * ## Teaching Activities ##
	 * #########################
	 */

	public Map<String, Set<TeachingActivityTemplate>> getTeachingActivities() {
		return this.extractTeachingActivities(TEACHING_ACTIVITIES_NODE_PATH);
	}

	private Map<String, Set<TeachingActivityTemplate>> extractTeachingActivities(final String nodeName) {
		final Map<String, Set<TeachingActivityTemplate>> result = new TreeMap<String, Set<TeachingActivityTemplate>>();

		Set<TeachingActivityTemplate> setSupervisions = new TreeSet<TeachingActivityTemplate>();
		Set<TeachingActivityTemplate> setTaughtCourses = new TreeSet<TeachingActivityTemplate>();
		Set<TeachingActivityTemplate> setExaminer = new TreeSet<TeachingActivityTemplate>();
		Set<TeachingActivityTemplate> setProgramDeveloped = new TreeSet<TeachingActivityTemplate>();
		Set<TeachingActivityTemplate> setCourseDeveloped = new TreeSet<TeachingActivityTemplate>();

		final Resource teachingActivitiesResource = this.resource.getChild(nodeName);

		if (teachingActivitiesResource == null) {
			return result;
		}

		final Iterator<Resource> children = teachingActivitiesResource.listChildren();

		while (children.hasNext()) {
			final Resource child = children.next();

			LOG.error("Accessing Teaching Activities from " + nodeName + "and the child name is : " + child.getName().toString());

			if (child.getName().startsWith(TEACHING_ACTIVITIES_PREFIX_NODE_NAME)) {
				LOG.error("It does start with teachingActivitiesXXX");
				final ValueMap childProperties = child.adaptTo(ValueMap.class);
				final String type = childProperties.get(TYPE_ATTRIBUTE, String.class);
				LOG.error("type is : " + type);
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

				case TEACHING_ACTIVITIES_TYPE_SUPERVISION:
					final String degreeType = childProperties.get(DEGREE_TYPE_ATTRIBUTE, String.class);
					final String otherDegreeType = childProperties.get(OTHER_DEGREE_TYPE_ATTRIBUTE, String.class);
					final String supervisoryRole = childProperties.get(SUPERVISORY_ROLE_ATTRIBUTE, String.class);
					final String person = childProperties.get(PERSON_ATTRIBUTE, String.class);
					final String coContributors= childProperties.get(CO_CONTRIBUTORS_ATTRIBUTE, String.class);
					final String degreeSubject = childProperties.get(DEGREE_SUBJECT_ATTRIBUTE, String.class);
                    final String supervisionInstitution = childProperties.get(INSTITUTION_ORGANISATIONS_ATTRIBUTE, String.class);

                    final String funder = childProperties.get(FUNDER_ATTRIBUTE, String.class);

                    setSupervisions.add(new Supervision(url, title, reportingDate, yearStartDate, monthStartDate, dayStartDate, yearEndDate, monthEndDate, dayEndDate,
							degreeType, otherDegreeType, supervisoryRole, person, coContributors, degreeSubject, supervisionInstitution, funder));
					break;	

				case TEACHING_ACTIVITIES_TYPE_TAUGHT_COURSES:
                    final String courseLevel = childProperties.get(COURSE_LEVEL_ATTRIBUTE, String.class);
                    final String taughtInstitution = childProperties.get(INSTITUTION_ORGANISATIONS_ATTRIBUTE, String.class);

					setTaughtCourses.add(new TaughtCourse(url, title, reportingDate, yearStartDate, monthStartDate, dayStartDate, yearEndDate, monthEndDate, dayEndDate, 
							courseLevel, taughtInstitution));
					break;

				case TEACHING_ACTIVITIES_TYPE_EXAMINER:
                    final String examinationRole = childProperties.get(EXAMINATION_ROLE_ATTRIBUTE, String.class);
                    final String examinationLevel = childProperties.get(EXAMINATION_LEVEL_ATTRIBUTE, String.class);
                    final String examinationInstitution = childProperties.get(EXAMINATION_INSTITUTIONS_ATTRIBUTE, String.class);

					setExaminer.add(new Examiner(url, title, reportingDate, yearStartDate, monthStartDate, dayStartDate, yearEndDate, monthEndDate, dayEndDate, 
							examinationRole, examinationLevel, examinationInstitution));
					break;

				case TEACHING_ACTIVITIES_TYPE_PROGRAM_DEVELOPED:
                    final String programDegreeLevel = childProperties.get(DEGREE_LEVEL_ATTRIBUTE, String.class);
                    final String programPartners = childProperties.get(PARTNER_ATTRIBUTE, String.class);
                    final String programDegreeType = childProperties.get(DEGREE_TYPE_ATTRIBUTE, String.class);
    				final String yearReleaseDate = childProperties.get(RELEASE_DATE_YEAR_NAME_ATTRIBUTE, String.class);
    				final String monthReleaseDate = childProperties.get(RELEASE_DATE_MONTH_NAME_ATTRIBUTE, String.class);
    				final String dayReleaseDate = childProperties.get(RELEASE_DATE_DAY_NAME_ATTRIBUTE, String.class);
                    final String programDevelopedInstitution = childProperties.get(INSTITUTION_ORGANISATIONS_ATTRIBUTE, String.class);

					setProgramDeveloped.add(new ProgramDeveloped(url, title, reportingDate, yearStartDate, monthStartDate, dayStartDate, yearEndDate, monthEndDate, dayEndDate, 
							programDegreeType, programDegreeLevel, programPartners, dayReleaseDate, monthReleaseDate, yearReleaseDate, programDevelopedInstitution));
					break;

				case TEACHING_ACTIVITIES_TYPE_COURSES_DEVELOPED:
					final String[] coursesDevelopedCoContributors = childProperties.get(CO_CONTRIBUTORS_ATTRIBUTE, String[].class);
					List<String> coContributorsList = new ArrayList<>();
					if (coursesDevelopedCoContributors != null) {
						coContributorsList = Arrays.asList(coursesDevelopedCoContributors);
					}
                    final String coursesDevelopedInstitution = childProperties.get(INSTITUTION_ORGANISATIONS_ATTRIBUTE, String.class);
    				final String coursesDevelopedYearReleaseDate = childProperties.get(RELEASE_DATE_YEAR_NAME_ATTRIBUTE, String.class);
    				final String coursesDevelopedMonthReleaseDate = childProperties.get(RELEASE_DATE_MONTH_NAME_ATTRIBUTE, String.class);
    				final String coursesDevelopedDayReleaseDate = childProperties.get(RELEASE_DATE_DAY_NAME_ATTRIBUTE, String.class);
                    
					setCourseDeveloped.add(new CourseDeveloped(url, title, reportingDate, yearStartDate, monthStartDate, dayStartDate, yearEndDate, monthEndDate, dayEndDate, 
							coContributorsList, coursesDevelopedInstitution, coursesDevelopedDayReleaseDate, coursesDevelopedMonthReleaseDate, coursesDevelopedYearReleaseDate));
					break;

				default:
					break;
				}
			}
		}
		result.put(TEACHING_ACTIVITIES_TYPE_SUPERVISION, setSupervisions);
		result.put(TEACHING_ACTIVITIES_TYPE_TAUGHT_COURSES, setTaughtCourses);
		result.put(TEACHING_ACTIVITIES_TYPE_EXAMINER, setExaminer);
		result.put(TEACHING_ACTIVITIES_TYPE_PROGRAM_DEVELOPED, setProgramDeveloped);
		result.put(TEACHING_ACTIVITIES_TYPE_COURSES_DEVELOPED, setCourseDeveloped);

		return result;
	}
	
	public Set<TeachingActivityTemplate> getTeachingActivitySet(Map<String, Set<TeachingActivityTemplate>> activities, String teachingActivity){
		Set<TeachingActivityTemplate> result = activities.get(teachingActivity);
		return result;
	}

	public String getSupervisions(Map<String, Set<TeachingActivityTemplate>> activities){
		StringBuilder result = new StringBuilder(); 
		Set<TeachingActivityTemplate> setSupervision = getTeachingActivitySet(activities, ScientistProfileHelper.TEACHING_ACTIVITIES_TYPE_SUPERVISION); 
		if (!setSupervision.isEmpty()) { 
			result.append("<h3>Supervision</h3>");
			for (final TeachingActivityTemplate activity: setSupervision) { 
				result.append("<p>");
				result.append(activity.getHTMLContent(getLastName() + " " + getInitials()));
				result.append("</p>");
			} 
		} 
		return result.toString();
	}

	public String getTaughtCourses(Map<String, Set<TeachingActivityTemplate>> activities){
		StringBuilder result = new StringBuilder(); 
		Set<TeachingActivityTemplate> setTaughtCourses = getTeachingActivitySet(activities, ScientistProfileHelper.TEACHING_ACTIVITIES_TYPE_TAUGHT_COURSES);
		if (!setTaughtCourses.isEmpty()) { 
			result.append("<h3>Courses taught</h3>");
			for (final TeachingActivityTemplate activity: setTaughtCourses) { 
				result.append("<p>");
				result.append(activity.getHTMLContent(getLastName() + " " + getInitials()));
				result.append("</p>");
			} 
		} 
		return result.toString();
	}

	public String getExaminer(Map<String, Set<TeachingActivityTemplate>> activities){
		StringBuilder result = new StringBuilder(); 
		Set<TeachingActivityTemplate> setExaminer = getTeachingActivitySet(activities, ScientistProfileHelper.TEACHING_ACTIVITIES_TYPE_EXAMINER);
		if (!setExaminer.isEmpty()) { 
			result.append("<h3>Examiner</h3>");
			for (final TeachingActivityTemplate activity: setExaminer) { 
				result.append("<p>");
				result.append(activity.getHTMLContent(getLastName() + " " + getInitials()));
				result.append("</p>");
			} 
		} 
		return result.toString();
	}

	public String getProgramDeveloped(Map<String, Set<TeachingActivityTemplate>> activities){
		StringBuilder result = new StringBuilder(); 
		Set<TeachingActivityTemplate> setProgramDeveloped = getTeachingActivitySet(activities, ScientistProfileHelper.TEACHING_ACTIVITIES_TYPE_PROGRAM_DEVELOPED);
		if (!setProgramDeveloped.isEmpty()) { 
			result.append("<h3>Program developed</h3>");
			for (final TeachingActivityTemplate activity: setProgramDeveloped) { 
				result.append("<p>");
				result.append(activity.getHTMLContent(getLastName() + " " + getInitials()));
				result.append("</p>");
			} 
		} 
		return result.toString();
	}

	public String getCourseDeveloped(Map<String, Set<TeachingActivityTemplate>> activities){
		StringBuilder result = new StringBuilder(); 
		Set<TeachingActivityTemplate> setCourseDeveloped = getTeachingActivitySet(activities, ScientistProfileHelper.TEACHING_ACTIVITIES_TYPE_COURSES_DEVELOPED);
		if (!setCourseDeveloped.isEmpty()) { 
			result.append("<h3>Course developed</h3>");
			for (final TeachingActivityTemplate activity: setCourseDeveloped) { 
				result.append("<p>");
				result.append(activity.getHTMLContent(getLastName() + " " + getInitials()));
				result.append("</p>");
			} 
		} 
		return result.toString();
	}

	public boolean displayTeachingActivitiesTab(Resource resource){
		boolean res = false;
		StringBuilder aux = new StringBuilder();
		final ScientistProfileHelper helper = new ScientistProfileHelper(resource);
		final Map<String, Set<TeachingActivityTemplate>> activities = helper.getTeachingActivities();
		
		if (activities != null && !activities.isEmpty()) {
			aux.append(helper.getSupervisions(activities));
			aux.append(helper.getTaughtCourses(activities));
			aux.append((helper.getExaminer(activities)));
			aux.append(helper.getProgramDeveloped(activities));
			aux.append(helper.getCourseDeveloped(activities));

			if (aux.length() > 0){
				res = true;
			}
		}
		return res;
	}
	
	/*
	 * #########################
	 * ## Impact and Outreach ##
	 * #########################
	 */
	
	public String getAwards(Map<String, Set<ProfessionalActivity>> activities){
		StringBuilder result = new StringBuilder(); 
		Set<ProfessionalActivity> setAwards = getProfessionalActivitySet(activities, ScientistProfileHelper.PROFESSIONAL_ACTIVITY_TYPE_AWARD); 
		if (!setAwards.isEmpty()) { 
			result.append("<h3>Awards</h3>");
			for (final ProfessionalActivity activity: setAwards) { 
				result.append("<p>");
				result.append(activity.getHTMLContent(getLastName() + " " + getInitials()));
				result.append("</p>");
			} 
		} 
		return result.toString();
	}
	
	public String getResearchPresentation(Map<String, Set<ProfessionalActivity>> activities){
		StringBuilder result = new StringBuilder(); 
		Set<ProfessionalActivity> setResearchPresentation = getProfessionalActivitySet(activities, ScientistProfileHelper.PROFESSIONAL_ACTIVITY_TYPE_RESEARCH_PRESENTATION); 
		if (!setResearchPresentation.isEmpty()) { 
			result.append("<h3>Invited and keynote speaker</h3>");
			for (final ProfessionalActivity activity: setResearchPresentation) { 
				result.append("<p>");
				result.append(activity.getHTMLContent(getLastName() + " " + getInitials()));
				result.append("</p>");
			} 
		} 
		return result.toString();
	}
	
	public String getGuestPresentation(Map<String, Set<ProfessionalActivity>> activities){
		StringBuilder result = new StringBuilder(); 
		Set<ProfessionalActivity> setGuestPresentation = getProfessionalActivitySet(activities, ScientistProfileHelper.PROFESSIONAL_ACTIVITY_TYPE_GUEST_PRESENTATION); 
		if (!setGuestPresentation.isEmpty()) { 
			result.append("<h3>Guest lectures</h3>");
			for (final ProfessionalActivity activity: setGuestPresentation) { 
				result.append("<p>");
				result.append(activity.getHTMLContent(getLastName() + " " + getInitials()));
				result.append("</p>");
			} 
		} 
		return result.toString();
	}
	
	public String getImpactEventsParticipation(Map<String, Set<ProfessionalActivity>> activities){
		StringBuilder result = new StringBuilder(); 
		String externalActivities = getPublicEngagement(activities, true);
		String internalActivities = getPublicEngagement(activities, false); 
		if ( (!externalActivities.isEmpty() || !externalActivities.equals("")) || (!internalActivities.isEmpty() || !internalActivities.equals("")) ){
			result.append("<h3>Public Engagement</h3>");
			if ( !externalActivities.isEmpty() || !externalActivities.equals("") ){
				result.append(externalActivities);
			}
			if ( !internalActivities.isEmpty() || !internalActivities.equals("") ) {
				result.append(internalActivities);
			}
		} 
		return result.toString();	
	}
	
	public String getPublicEngagement(Map<String, Set<ProfessionalActivity>> activities, boolean externalPosition){
		StringBuilder result = new StringBuilder(); 
		StringBuilder aux = new StringBuilder(); 
		Set<ProfessionalActivity> setPositions = getProfessionalActivitySet(activities, PROFESSIONAL_ACTIVITY_TYPE_EVENT_PARTICIPATION);
		if (!setPositions.isEmpty()){ 
			if (externalPosition){
				for (final ProfessionalActivity activity: setPositions) { 
					String[] parameters = {
							PROFESSIONAL_ACTIVITY_PARAMETER_EXTERNAL, 
							PROFESSIONAL_ACTIVITY_PARAMETER_PUBLIC_ENGAGEMENT
					};
					aux.append(activity.getFilteredHTMLContent(getLastName() + " " + getInitials(), parameters));
				} 
				if (aux.length() > 0) { 
					result.append("<h4>External activities</h4>");
					result.append(aux);
				} 
			} else {
				for (final ProfessionalActivity activity: setPositions) {
					String[] parameters = {
							PROFESSIONAL_ACTIVITY_PARAMETER_INTERNAL, 
							PROFESSIONAL_ACTIVITY_PARAMETER_PUBLIC_ENGAGEMENT
					};
					aux.append(activity.getFilteredHTMLContent(getLastName() + " " + getInitials(), parameters));
				}
				if (aux.length() > 0) {
					result.append("<h4>Internal activities</h4>");
					result.append(aux);
				}
			}
		} 
		return result.toString();
	}
	
	public String getMedia(Map<String, Set<ProfessionalActivity>> activities){
		StringBuilder result = new StringBuilder(); 
		Set<ProfessionalActivity> setMediaBroadcast = getProfessionalActivitySet(activities, ScientistProfileHelper.PROFESSIONAL_ACTIVITY_TYPE_MEDIA_BROADCAST);
		Set<ProfessionalActivity> setMediaInterview = getProfessionalActivitySet(activities, ScientistProfileHelper.PROFESSIONAL_ACTIVITY_TYPE_MEDIA_INTERVIEW); 
		if (!setMediaBroadcast.isEmpty() || !setMediaInterview.isEmpty()) { 
			result.append("<h3>Media</h3>");
			
			if (!setMediaBroadcast.isEmpty() ){
				result.append("<h4>Broadcasts</h4>");
				for (final ProfessionalActivity activity: setMediaBroadcast) { 
					result.append("<p>");
					result.append(activity.getHTMLContent(getLastName() + " " + getInitials()));
					result.append("</p>");
				} 
			}

			if (!setMediaInterview.isEmpty()){
				result.append("<h4>Articles</h4>");
				for (final ProfessionalActivity activity: setMediaInterview) {
					result.append("<p>");
					result.append(activity.getHTMLContent(getLastName() + " " + getInitials()));
					result.append("</p>");
				} 
			}
		} 
		return result.toString();
	}

	public boolean displayImpactAndOutreachTab(Resource resource){
		boolean res = false;
		StringBuilder aux = new StringBuilder();
		final ScientistProfileHelper helper = new ScientistProfileHelper(resource);
		final Map<String, Set<ProfessionalActivity>> activities = helper.getProfessionalActivities();

		if (activities != null && !activities.isEmpty()) {
			aux.append(helper.getAwards(activities));
			aux.append(helper.getResearchPresentation(activities));
			aux.append(helper.getGuestPresentation(activities));
			aux.append(helper.getMedia(activities));
			aux.append(helper.getImpactEventsParticipation(activities));
			
			if (aux.length() > 0){
				res = true;
			}
		}
		return res;
	}
}
