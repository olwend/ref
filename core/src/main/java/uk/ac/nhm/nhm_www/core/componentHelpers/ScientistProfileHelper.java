package uk.ac.nhm.nhm_www.core.componentHelpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.components.DropTarget;
import com.day.cq.wcm.foundation.Image;

import uk.ac.nhm.nhm_www.core.impl.workflows.science.ImportXMLWorkflow;
import uk.ac.nhm.nhm_www.core.model.science.Book;
import uk.ac.nhm.nhm_www.core.model.science.BookChapter;
import uk.ac.nhm.nhm_www.core.model.science.ConferenceProceedings;
import uk.ac.nhm.nhm_www.core.model.science.Dataset;
import uk.ac.nhm.nhm_www.core.model.science.InternetPublication;
import uk.ac.nhm.nhm_www.core.model.science.JournalArticle;
import uk.ac.nhm.nhm_www.core.model.science.NewspaperMagazine;
import uk.ac.nhm.nhm_www.core.model.science.Other;
import uk.ac.nhm.nhm_www.core.model.science.Poster;
import uk.ac.nhm.nhm_www.core.model.science.Publication;
import uk.ac.nhm.nhm_www.core.model.science.Qualification;
import uk.ac.nhm.nhm_www.core.model.science.Report;
import uk.ac.nhm.nhm_www.core.model.science.ScholarlyEdition;
import uk.ac.nhm.nhm_www.core.model.science.Software;
import uk.ac.nhm.nhm_www.core.model.science.ThesisDissertation;
import uk.ac.nhm.nhm_www.core.model.science.WebSite;
import uk.ac.nhm.nhm_www.core.model.science.Webpage;
import uk.ac.nhm.nhm_www.core.model.science.WebsitePublicationType;
import uk.ac.nhm.nhm_www.core.model.science.WorkExperience;
import uk.ac.nhm.nhm_www.core.model.science.PhoneNumber;

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
	public static final String CITY_ATTRIBUTE			  = "city";
	public static final String COUNTRY_ATTRIBUTE 		  = "country";
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
	public static final String PAGE_COUNT_ATTRIBUTE 	  = "pageCount";
	public static final String PHONE_ATTRIBUTE 		   	  = "phone";
	public static final String PLACE_ATTRIBUTE 		   	  = "place";
	public static final String POSITION_ATTRIBUTE 		  = "position";
	public static final String PUBLICATION_DATE_ATTRIBUTE = "publicationDate";
	public static final String PUBLICATION_MONTH_ATTRIBUTE = "publicationMonth";
	public static final String PUBLICATION_DAY_ATTRIBUTE = "publicationDay";
	public static final String PUBLISHER_ATTRIBUTE 	   	  = "publisher";
	public static final String TITLE_ATTRIBUTE 		   	  = "title";
	public static final String TO_ATTRIBUTE 			  = "to";
	public static final String TYPE_ATTRIBUTE 			  = "type";
	public static final String REPORTING_DATE_ATTRIBUTE   = "reportingDate";
	public static final String SPECIALISMS_ATTRIBUTE	  = "specialisms";
	public static final String START_DATE_ATTRIBUTE 	  = "startDate";
	public static final String START_PAGE_ATTRIBUTE 	  = "startPage";
	public static final String STATEMENT_ATTRIBUTE		  = "statement";
	public static final String SUBORGANISATION_ATTRIBUTE  = "subOrganisation";
	public static final String VOLUME_ATTRIBUTE 		  = "volume";
	public static final String CONFERENCE_NAME_ATTRIBUTE  = "conferenceName";
	public static final String CONFIDENTIAL_ATTRIBUTE	  = "confidential";
	public static final String THESIS_TYPE_ATTRIBUTE	  = "thesisType";
	public static final String PUBLISHER_URL_ATTRIBUTE	  = "publisherURL";
	
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
				
//				LOG.error("Loading Publication with type : " + type);
				
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
					final int page	   	   = childProperties.get(PAGE_COUNT_ATTRIBUTE, -1);
					result.add(new Book(title, authorsList, favorite, publicationYear, link, reportingDate, editorsSet, publisher, place, page));
					break;

				case PUBLICATION_TYPE_CHAPTER:
					final String bookTitle = childProperties.get(BOOK_TITLE_ATTRIBUTE, String.class);
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
					result.add(new BookChapter(title, authorsList, favorite, publicationYear, link, reportingDate, bookEditorsSet, bookTitle, 
							beginPage, endPage, bookPublisher, bookPlace));
					break;
					
				case PUBLICATION_TYPE_ARTICLE:
					final String journalName = childProperties.get(JOURNAL_NAME_ATTRIBUTE, String.class);
					final int volume = childProperties.get(VOLUME_ATTRIBUTE, -1);
					final int issue = childProperties.get(ISSUE_ATTRIBUTE, -1);
					final int articleBeginPage = childProperties.get(START_PAGE_ATTRIBUTE, -1);
					final int articleEndPage = childProperties.get(END_PAGE_ATTRIBUTE, -1);
					final String doiText = childProperties.get(DOI_TEXT_ATTRIBUTE, String.class);
					final String doiLink = childProperties.get(DOI_LINK_ATTRIBUTE, String.class);
					result.add(new JournalArticle(title, authorsList, favorite, publicationYear, link, reportingDate, journalName, volume, issue, 
							articleBeginPage, articleEndPage, doiText, doiLink));
					break;

				case PUBLICATION_TYPE_CONFERENCE_PROCEEDINGS:
					final String conferenceName = childProperties.get(CONFERENCE_NAME_ATTRIBUTE, String.class);
					final String placeOfPublication = childProperties.get(PLACE_ATTRIBUTE, String.class);
					result.add(new ConferenceProceedings(title, authorsList, favorite, publicationYear, link, reportingDate, conferenceName, placeOfPublication));
					break;
					
				case PUBLICATION_TYPE_DATASET:
					final String datasetDoiText = childProperties.get(DOI_TEXT_ATTRIBUTE, String.class);
					final String datasetDoiURL = childProperties.get(DOI_LINK_ATTRIBUTE, String.class);
					final String datasetPublisherURL = childProperties.get(PUBLISHER_URL_ATTRIBUTE, String.class);
					result.add(new Dataset(title, authorsList, favorite, publicationYear, link, reportingDate, datasetDoiText, datasetDoiURL, datasetPublisherURL));
					break;
					
				case PUBLICATION_TYPE_INTERNET_PUBLICATION:
					final String internetPublisher = childProperties.get(PUBLISHER_ATTRIBUTE, String.class);
					final int internetBeginPage = childProperties.get(START_PAGE_ATTRIBUTE, -1);
					final int internetEndPage = childProperties.get(END_PAGE_ATTRIBUTE, -1);
					final int internetPage = childProperties.get(PAGE_COUNT_ATTRIBUTE, -1);
					final int iPublicationMonth = childProperties.get(PUBLICATION_MONTH_ATTRIBUTE, -1);
					final int iPublicationDay = childProperties.get(PUBLICATION_DAY_ATTRIBUTE, -1);
					final String publisherURL = childProperties.get(PUBLISHER_URL_ATTRIBUTE, String.class);
					result.add(new InternetPublication(title, authorsList, favorite, publicationYear, iPublicationMonth, iPublicationDay, link, 
							reportingDate, internetPublisher, publisherURL, internetBeginPage, internetEndPage, internetPage));
					break;
					
				case PUBLICATION_TYPE_NEWSPAPER_OR_MAGAZINE:
					result.add(new NewspaperMagazine(title, authorsList, favorite, publicationYear, link, reportingDate));
					break;
					
				case PUBLICATION_TYPE_OTHER:
					result.add(new Other(title, authorsList, favorite, publicationYear, link, reportingDate));
					break;
					
				case PUBLICATION_TYPE_POSTER:
					result.add(new Poster(title, authorsList, favorite, publicationYear, link, reportingDate));
					break;
					
				case PUBLICATION_TYPE_REPORT:
					final boolean confidential = childProperties.get(CONFIDENTIAL_ATTRIBUTE, false);
					final String reportPublisher = childProperties.get(PUBLISHER_ATTRIBUTE, String.class);
					final String reportPublishingPlace	   = childProperties.get(PLACE_ATTRIBUTE, String.class);
					final int reportBeginPage = childProperties.get(START_PAGE_ATTRIBUTE, -1);
					final int reportEndPage = childProperties.get(END_PAGE_ATTRIBUTE, -1);
					final int reportPage = childProperties.get(PAGE_COUNT_ATTRIBUTE, -1);
					result.add(new Report(title, authorsList, favorite, publicationYear, link, reportingDate, confidential, reportBeginPage, reportEndPage, 
							reportPublisher, reportPublishingPlace, reportPage));
					break;
					
				case PUBLICATION_TYPE_SCHOLARLY_EDITION:
					result.add(new ScholarlyEdition(title, authorsList, favorite, publicationYear, link, reportingDate));
					break;
					
				case PUBLICATION_TYPE_SOFTWARE:
					final String doiTxt = childProperties.get(DOI_TEXT_ATTRIBUTE, String.class);
					final String doiURL = childProperties.get(DOI_LINK_ATTRIBUTE, String.class);
					result.add(new Software(title, authorsList, favorite, publicationYear, link, reportingDate, doiTxt, doiURL));
					break;
					
				case PUBLICATION_TYPE_THESIS_OR_DISERTATION:
					final String thesisPublisher = childProperties.get(PUBLISHER_ATTRIBUTE, String.class);
					final String thesisPublishingPlace	   = childProperties.get(PLACE_ATTRIBUTE, String.class);
					final String thesisType = childProperties.get(THESIS_TYPE_ATTRIBUTE, String.class);
					final int thesisBeginPage = childProperties.get(START_PAGE_ATTRIBUTE, -1);
					final int thesisEndPage = childProperties.get(END_PAGE_ATTRIBUTE, -1);
					final int thesisPage = childProperties.get(PAGE_COUNT_ATTRIBUTE, -1);
					final String[] supervisors = childProperties.get(EDITORS_ATTRIBUTE, String[].class);
					List<String> supervisorsSet = new ArrayList<>();
					if (supervisors != null) {
						supervisorsSet = Arrays.asList(supervisors);
					}
					result.add(new ThesisDissertation(title, authorsList, favorite, publicationYear, link, reportingDate, supervisorsSet, thesisType, 
							thesisPublisher, thesisPublishingPlace, thesisBeginPage, thesisEndPage, thesisPage));
					break;
					
				case PUBLICATION_TYPE_WEBPAGE:
					result.add(new Webpage(title, authorsList, favorite, publicationYear, link, reportingDate));
					break;
					
				case PUBLICATION_TYPE_WEBSITE:
					result.add(new WebsitePublicationType(title, authorsList, favorite, publicationYear, link, reportingDate));
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
	
}
