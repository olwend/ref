package uk.ac.nhm.core.componentHelpers;

import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;

public class SocialSignupHelper extends HelperBase {

	/**
	 * Provide a default data protection statement
	 */
	private String dataProtection = "<p>Get email updates about our news, science, exhibitions, events, products, services and fundraising activities. You must be over the age of 13."
			+ " <a href=\"http://www.nhm.ac.uk/about-us/privacy-policy.html\">Privacy notice</a>.</p>";

	private String title = "Don't miss a thing";
	private String description = "Follow us on social media";

	/**
	 * Set a default campaign in case it does not get set
	 */
	private String campaign = "eNewsletters";

	public SocialSignupHelper(ValueMap properties, Resource resource) throws PersistenceException {

		if (properties.get("title") != null) {
			this.title = properties.get("title", String.class);
		}
		if (properties.get("description") != null) {
			this.description = properties.get("description", String.class);
		}
		if (properties.get("campaign") != null) {
			this.campaign = properties.get("campaign", String.class);
		}
//		if (properties.get("dataProtection") != null) {
//			this.dataProtection = properties.get("dataProtection", String.class);
//		} else {
//			// Modify the resource in the content JCR tree
//			ModifiableValueMap map = resource.adaptTo(ModifiableValueMap.class);
//			map.put("dataProtection", dataProtection);
//			resource.getResourceResolver().commit();
//		}
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
