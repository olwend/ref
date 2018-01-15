package uk.ac.nhm.core.model.science;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Publication implements Comparable<Publication> {
	private static final Logger LOG = LoggerFactory.getLogger(Publication.class);
	private List<String> authors;
	private String title;
	private String publicationYear;
	private boolean favorite;
	private String href;
	
	private String reportingDate;
	
	public Publication(final String title, final List<String> authorsList, boolean favorite, final String publicationYear,
			final String href, final String reportingDate) {
		this.authors = authorsList;
		this.title = title;
		this.publicationYear = publicationYear;
		this.favorite = favorite;
		this.href = href;
		//this.reportingDate = reportingDate;
	}

	public abstract String getHTMLContent(final String currentAuthor, final boolean isFavorite);
	
	public boolean isValid() {
		return true;
	}

	public int compareTo(final Publication p) {
		if (p.reportingDate == null) {
			return 1;
		}
		
		if (this.reportingDate == null) {
			return -1;
		}
		
		final int reportingDatesComparation = p.reportingDate.compareTo(p.reportingDate) * -1;
		
		if (reportingDatesComparation == 0) {
			return this.title.compareTo(p.title);
		}
		
		return reportingDatesComparation;
		
	}

	public String getPublicationYear() {
		return publicationYear;
	}

	public boolean isFavorite() {
		return favorite;
	}

	public String getTitle() {
		return title;
	}

	public List<String> getAuthors() {
		return authors;
	}

	public String getLink() {
		return href;
	}
	
    public static String normalizeName(String fullName, boolean onlyFirstInitial){
//    	LOG.error("Inside normalizeName, our fullName is " + fullName);
    	//fullName will be for example: 			e.g: Ouvrard D N M || Ouvrard DNM || Ouvrard D 
		StringBuffer ret = new StringBuffer();
		List<String> namesList = new ArrayList(Arrays.asList(fullName.split(" ")));
		
		//#############
		//## Surname ##
		//#############
		//Possible cases for surnames				e.g: Ouvrard || OUVRARD
		String surname = namesList.get(0);
											//		LOG.error("Surname is " + surname);
		//First Letter of Surname in uppercase		e.g: Ouvrard > O
		ret.append(surname.substring(0, 1).toUpperCase());
		//Rest of the surname in lowercase			e.g: UVRARD > uvrard
		ret.append(surname.substring(1, surname.length()).toLowerCase());
											//		LOG.error("Surname ended up looking like this : " + ret);

		//Done with the Surname, removing it from the list
		namesList.remove(0);
		
		//Separator between Surname and Name Initials
		ret.append(" ");
			
		//#####################
		//## Name Initial(s) ##
		//#####################
		//Possible cases for name initials: 		e.g: D N M || DNM || D
		String initials = StringUtils.join(namesList.toArray(new String[namesList.size()]), "");
											//		LOG.error("Initials are " + initials);
		
		//If onlyFirstInitial we append just the first initial
		if(onlyFirstInitial) {
			ret.append(initials.substring(0, 1));
		} else {
			ret.append(initials);
		}
		
		//All together it turns it into				e.g: Ouvrard DNM 
		//If firstInitial was true it will be		e.g: Ouvrard D
		//We surround it with # to delimit the beginning and the end of the name.
		return "#" + ret + "#";
	}
    
    /**
     * Returns the surname and first initial of a given author
     * @param fullName	the name of the author
     * @return			the surname and first initial of the author
     */
    public static String getSurname(String fullName) {
    	List<String> nameList = new ArrayList(Arrays.asList(fullName.split(" ")));
    	String surname = nameList.get(0) + " " + nameList.get(1).charAt(0);
    	return surname;
    }

}
