package uk.ac.nhm.nhm_www.core.componentHelpers;

import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.scripting.SlingScriptHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUse;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.designer.Style;
import uk.ac.nhm.nhm_www.core.utils.LinkUtils;
import uk.ac.nhm.nhm_www.core.InformationalPageHeadService;


public class InformationalPageHeadHelper {

	protected static final Logger logger = LoggerFactory
			.getLogger(InformationalPageHeadHelper.class);

	
	private boolean hasJoinRenewButton;
	private String joinRenewButtonText;
	private String joinRenewButtonLink;
	private boolean hasDonateButton;
	private String donateButtonText;
	private String donateButtonLink;
	
	private boolean hasBookTicketsButton;
	private String bookTicketsButtonText;
	private String bookTicketsButtonLink;
	
	private boolean hasGettingHereLink;
	private boolean componentInitialised;
	private String title;
	private String bodyText;

	public String propertiesvalues;

	public InformationalPageHeadHelper(Page page, ValueMap properties, Style currentStyle) {
		// This below is to attempt to accessing the content via SLING, however
		// it isn't working, scraping for now.
		// public InformationalPageHeadHelper(Resource res) {
		// ValueMap properties = res.adaptTo(ValueMap.class);
		/*
		 * Page page = res.adaptTo(Page.class); ValueMap properties =
		 * page.getProperties();
		 */
		String pagePath = page.getPath();
		this.componentInitialised = false;
		this.hasJoinRenewButton = false;
		this.hasDonateButton = false;
		this.hasBookTicketsButton = false;
		this.hasGettingHereLink = false;
		this.componentInitialised = false;
		this.hasJoinRenewButton = properties.get("joinRenewButton", false);
		this.hasDonateButton = properties.get("donateButton", false);
		this.hasBookTicketsButton = properties.get("bookTicketsButton", false);
		this.hasGettingHereLink = properties.get("gettingHereLink", false);
		this.title = page.getTitle();
		//Resource res =currentStyle.get("informationalPageHeadButtonOneTitle", "title is undefined");
		this.joinRenewButtonText = currentStyle.get("informationalPageHeadButtonOneTitle", "title is undefined");
		this.joinRenewButtonLink = LinkUtils.getFormattedLink(currentStyle.get("informationalPageHeadButtonOnePath", ""));
		this.bookTicketsButtonText = currentStyle.get("informationalPageHeadButtonTwoTitle", "title is undefined");
		this.bookTicketsButtonLink = LinkUtils.getFormattedLink(currentStyle.get("informationalPageHeadButtonTwoPath", ""));
		this.donateButtonText = currentStyle.get("informationalPageHeadButtonThreeTitle", "title is undefined");
		this.donateButtonLink = LinkUtils.getFormattedLink(currentStyle.get("informationalPageHeadButtonThreePath", ""));
		
		// this.bodyText =
		// This below is to attempt to accessing the content via SLING, however
		// it isn't working, scraping for now.
		/*
		 * String location = page.getPath() +
		 * "/jcr:content/par/informationalpagehea/bodyText"; ResourceResolver
		 * resourceResolver = null; try { resourceResolver =
		 * resolverFactory.getAdministrativeResourceResolver(null); } catch
		 * (LoginException e) { // TODO Auto-generated catch block
		 * logger.debug("resource resolver not working"); } Resource res =
		 * resourceResolver.getResource(location); Page bodyTextPageAdapt =
		 * res.adaptTo(Page.class); this.bodyText =
		 * bodyTextPageAdapt.getProperties().get("text", String.class);
		 * resource.getPath("bodyText/text")
		 * page.getContentResource("bodyText").get .get("text", String.class);
		 */

		/*
		 * for (Entry<String, Object> entry : properties.entrySet()) { String
		 * key = entry.getKey(); String value = entry.getValue().toString();
		 * propertiesvalues += key + " => " + value + "<br/>"; }
		 * 
		 * propertiesvalues += "PATH of resoucePage =>" + pagePath;
		 */

		/*if ((this.title != null && !this.title.trim().isEmpty())
				&& (this.hasJoinRenewButton || this.hasDonateButton
						|| this.hasBookTicketsButton || this.hasGettingHereLink || (this.bodyText != null && !this.bodyText
						.trim().isEmpty()))) {*/
		if(this.title != null && !this.title.trim().isEmpty()) {
			this.componentInitialised = true;
		} else {
			this.componentInitialised = false;
		}

	}

	public boolean isComponentInitialised() {
		return componentInitialised;
	}

	public void setComponentInitialised(boolean componentInitialised) {
		this.componentInitialised = componentInitialised;
	}

	public boolean isHasJoinRenewButton() {
		return hasJoinRenewButton;
	}

	public void setHasJoinRenewButton(boolean hasJoinRenewButton) {
		this.hasJoinRenewButton = hasJoinRenewButton;
	}

	public String getJoinRenewButtonText() {
		return joinRenewButtonText;
	}

	public void setJoinRenewButtonText(String joinRenewButtonText) {
		this.joinRenewButtonText = joinRenewButtonText;
	}
	
	

	public String getJoinRenewButtonLink() {
		return joinRenewButtonLink;
	}

	public void setJoinRenewButtonLink(String joinRenewButtonLink) {
		this.joinRenewButtonLink = joinRenewButtonLink;
	}

	public boolean isHasDonateButton() {
		return hasDonateButton;
	}
	
	public String getDonateButtonText() {
		return donateButtonText;
	}

	public void setDonateButtonText(String donateButtonText) {
		this.donateButtonText = donateButtonText;
	}

	public String getDonateButtonLink() {
		return donateButtonLink;
	}

	public void setDonateButtonLink(String donateButtonLink) {
		this.donateButtonLink = donateButtonLink;
	}

	public void setHasDonateButton(boolean hasDonateButton) {
		this.hasDonateButton = hasDonateButton;
	}

	public boolean isHasBookTicketsButton() {
		return hasBookTicketsButton;
	}

	public void setHasBookTicketsButton(boolean hasBookTicketsButton) {
		this.hasBookTicketsButton = hasBookTicketsButton;
	}
	
	

	public String getBookTicketsButtonText() {
		return bookTicketsButtonText;
	}

	public void setBookTicketsButtonText(String bookTicketsButtonText) {
		this.bookTicketsButtonText = bookTicketsButtonText;
	}

	public String getBookTicketsButtonLink() {
		return bookTicketsButtonLink;
	}

	public void setBookTicketsButtonLink(String bookTicketsButtonLink) {
		this.bookTicketsButtonLink = bookTicketsButtonLink;
	}

	public boolean isHasGettingHereLink() {
		return hasGettingHereLink;
	}

	public void setHasGettingHereLink(boolean hasGettingHereLink) {
		this.hasGettingHereLink = hasGettingHereLink;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBodyText() {
		return bodyText;
	}

	public void setBodyText(String bodyText) {
		this.bodyText = bodyText;
	}

	private Boolean convertCheckboxValue(String value) {
		Boolean convertedValue = false;
		if (value != null && value.equals("on")) {
			convertedValue = true;
		}
		return convertedValue;
	}



}
