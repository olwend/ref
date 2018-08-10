package uk.ac.nhm.core.componentHelpers;

import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;

public class ENewsSignupHelper extends HelperBase {

	/**
	 * Provide a default data protection statement
	 */
	private String dataProtection = "<p>Please contact me with news about the Natural History Museum's activities, events, products, services and fundraising activities. By signing up I confirm that I am over the age of 13.</p>"
			+ "<p> I consent to the Museum using my personal data in accordance with data protection legislation. I understand that the Museum will also use my personal data to provide the services requested and to analyse visitor activity and preferences. I understand I can unsubscribe at any time by clicking the unsubscribe link at the bottom of the emails. "
			+ "<a href=\"http://www.nhm.ac.uk/about-us/privacy-notice.html\"> Privacy notice</a>.</p>";

	private String title;
	private String description;
	
	/**
	 * Set a default campaign in case it does not get set
	 */
	private String campaign = "eNewsletters";

	public ENewsSignupHelper(ValueMap properties, Resource resource) throws PersistenceException {

		if (properties.get("title") != null) {
			this.title = properties.get("title", String.class);
		} else {
			this.title = "Be the first to hear about our news, science, exhibitions, events, products and more.";
		}
		
		if (properties.get("description") != null) {
			this.description = properties.get("description", String.class);
		}
		
		if (properties.get("campaign") != null) {
			this.campaign = properties.get("campaign", String.class);
		}
		
		if (properties.get("dataProtection") != null) {
			this.dataProtection = properties.get("dataProtection", String.class);
		} else {
			// Modify the resource in the content JCR tree
			ModifiableValueMap map = resource.adaptTo(ModifiableValueMap.class);
			map.put("dataProtection", dataProtection);
			resource.getResourceResolver().commit();
		}
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCampaign() {
		return campaign;
	}

	public void setCampaign(String campaign) {
		this.campaign = campaign;
	}

	public String getDataProtection() {
		return dataProtection;
	}

	public void setDataProtection(String dataProtection) {
		this.dataProtection = dataProtection;
	}

}
