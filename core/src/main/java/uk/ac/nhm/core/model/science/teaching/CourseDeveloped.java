package uk.ac.nhm.core.model.science.teaching;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;

import uk.ac.nhm.core.model.science.proactivities.Institution;

public class CourseDeveloped extends TeachingActivityTemplate {

	private Institution[] institutions;
	private String dayReleaseDate;
	private String monthReleaseDate;
	private String yearReleaseDate;
	private List<String> coContributors;

	public CourseDeveloped(String url, String title, final String reportingDate, String yearStartDate, String monthStartDate, String dayStartDate, 
			String yearEndDate, String monthEndDate, String dayEndDate, List<String> coContributorsList, String institution, 
			String dayReleaseDate, String monthReleaseDate, String yearReleaseDate) {
		super(url, title, reportingDate, yearStartDate, monthStartDate, dayStartDate, yearEndDate, monthEndDate, dayEndDate);
		this.coContributors = coContributorsList;
		this.dayReleaseDate = dayReleaseDate;
		this.monthReleaseDate = monthReleaseDate;
		this.yearReleaseDate = yearReleaseDate;
		assignJSON(institution);
	}
	
	private void assignJSON(String aux) {
		try {
			if (aux == null){
				return;
			}
			final JSONObject jsonObject = new JSONObject(aux);
			final JSONArray jsonArray = jsonObject.getJSONArray("organisations");
			
			this.institutions = new Institution[jsonArray.length()];
			
			for (int i = 0; i < jsonArray.length(); i++) {
				final JSONObject organisationJson = jsonArray.getJSONObject(i);
				
				final Institution institution = new Institution(organisationJson);
				this.institutions[i] = institution;
			}
		} catch (final JSONException e) {
			this.institutions = null;
		}
	}

	@Override
	public String getHTMLContent(String currentAuthor) {
		final StringBuffer stringBuffer = new StringBuffer();
		
		// Principal INV, Principal INV
		final List<String> contributors = this.coContributors;
		if (contributors != null && contributors.size() > 0) {
			String contributorsString = StringUtils.join(contributors.toArray(new String[contributors.size()]), ", ");
			contributorsString = contributorsString.replaceAll(currentAuthor, "<b>" + currentAuthor + "</b>");
			
			stringBuffer.append("Co developers: ");
			stringBuffer.append(contributorsString);
			stringBuffer.append("<br>");
		}
		
		// Title,_ 
		if (this.title != null && !this.title.equals("")){
			stringBuffer.append(this.title);
			stringBuffer.append(", ");
		}
		
		if ( institutions != null ){
			for (Institution institution : institutions) {
				// InstitutionName,_ 
				if (institution.getOrganisation() != null){
					stringBuffer.append(institution.getOrganisation());
				}
				
				// City,_
				if (institution.getCity() != null){
					stringBuffer.append(", ");
					stringBuffer.append(institution.getCity() );
					
					// Country,_
					if (institution.getCountry() != null){
						stringBuffer.append(", ");
						stringBuffer.append(institution.getCountry());
					}
				}
				stringBuffer.append("<br>");
			}
		}
		
		// releaseDay / releaseMonth / releaseYear
		if (this.yearReleaseDate != null){
			if (this.monthReleaseDate != null){
				if ( this.dayReleaseDate != null) {
					stringBuffer.append(this.dayReleaseDate);
					stringBuffer.append("/");
				}
				stringBuffer.append(this.monthReleaseDate);
				stringBuffer.append("/");
			}
			stringBuffer.append(this.yearReleaseDate);
			stringBuffer.append("<br>");
		}
		
		return stringBuffer.toString();
	}
}