package uk.ac.nhm.core.componentHelpers;

import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;

public class ENewsSignupHelper extends HelperBase {

	/**
	 * Provide a default data protection statement
	 */
	private String dataProtection = "<p>By providing your details, you agree that the Natural History Museum may contact you by email with news and information about our activities, events, products, services and fundraising.</p>"
			+ "<h4>Data protection</h4>"
			+ "<p>The Natural History Museum will use your personal information in accordance with the Data Protection Act 1998. We will use it to provide the service(s) requested, improve our understanding of"
			+ " our target audiences and supporters, and, if you agree, to send you news and information as set out above. For more information,"
			+ " please see our <a href=\"http://www.nhm.ac.uk/my-nhm/privacy-statement/index.html\">privacy notice</a>.</p>";

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
			this.title = "Please add a title";
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
