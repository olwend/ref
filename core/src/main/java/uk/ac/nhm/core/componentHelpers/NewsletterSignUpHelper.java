package uk.ac.nhm.core.componentHelpers;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;

import com.day.cq.wcm.api.PageManager;

/**
 * Newsletter Sign Up Component Helper.
 */
public class NewsletterSignUpHelper extends HelperBase {
	private ValueMap properties;
	private PageManager pageManager;
	
	/**
	 * Constructor getting the properties of the component and the {@link PageManager Page Manager} of 
	 * the Page.
	 * @param properties Component Properties.
	 * @param pageManager {@link PageManager Page Manager} of the Page where the Component is. 
	 */
	public NewsletterSignUpHelper(final ValueMap properties, final PageManager pageManager) {
		this.properties  = properties;
		this.pageManager = pageManager;
	}
	
	/**
	 * Constructor getting the resource representing the Newsletter Sign Up component and 
	 * the {@link PageManager Page Manager} of the Page.
	 * @param resource Resource representing the component.
	 * @param pageManager {@link PageManager Page Manager} of the Page where the Component is. 
	 */
	public NewsletterSignUpHelper(final Resource resource, final PageManager pageManager) {
		this.properties  = resource.adaptTo(ValueMap.class);
		this.pageManager = pageManager;
	}
	
	/**
	 * Gets the Title configured on the Component.
	 * @return jcr:title attribute configured on the component.
	 */
	public String getTitle() {
		//TODO: tona - This is hard coded. It needs to be updated to be user configurable.
		//return this.properties.get("jcr:title", String.class);
		return "Sign up for our emails";
	}
	
	/**
	 * Gets the Thank You URL configured on the Component.
	 * @return The internal or external URL.
	 */
	public String getThankYouURL() {
		
		
		//TODO: tona - This is hard coded. It needs to be updated to be user configurable.
		//final String thankyouProperty = this.properties.get("thankyouurl", String.class);
		String thankyouProperty = "/content/nhmwww/en/home/about-us/thanks-for-signing-up";
		
		if (thankyouProperty == null) {
			return null;
		}
			
		if (thankyouProperty.startsWith("http")) {
			return thankyouProperty;
		}
		
		if (thankyouProperty.startsWith("/") ) {
			return thankyouProperty + ".html";
		}
		
		return null;
	}

	/**
	 * Gets the HTML defining to show on the Data Policy for the Component.
	 * @return Data Policy configured.
	 */
	public String getDataPolicy() {
		//TODO: tona - This is hard coded. It needs to be updated to be user configurable.
		//return properties.get("datapolicy", String.class);
		return "<label>We will use your personal information in accordance with the Data Protection Act 1998. <a href=\"/my-nhm/privacy-policy/index.html\">View our privacy notice.</a></label>";
	}
	
	/**
	 * Checks if the component is completely configured.
	 * @return <code>true</code> if Title, Thank You URL and Data Policy are configured and are not null anyone.
	 */
	public boolean isComponentInitialised() {
		return this.getDataPolicy() != null && this.getThankYouURL() != null && this.getTitle() != null;
	}
	
}
