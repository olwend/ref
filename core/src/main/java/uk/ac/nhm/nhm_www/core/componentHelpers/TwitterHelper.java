package uk.ac.nhm.nhm_www.core.componentHelpers;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ValueMap;

public class TwitterHelper extends HelperBase {
	

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getWidgetId() {
		//seems to be a bug somewhere in AEM/ExtJs where numbers are being rounded even if they are a string, so doing a quick hack to get it working...
		
		if (this.widgetId !=null)
		{
			if (this.widgetId.equals("homepage"))
			{return "532208252889464835";}
			
			else if (this.widgetId.equals("visit-us"))
			{return "532209756673277952";}
			
			else if (this.widgetId.equals("our-science"))
			{return "345479384238530560";}
			
			else if (this.widgetId.equals("museum"))
			{return "345224080041070593";}
			
			else if (this.widgetId.equals("london"))
			{return "532202770846060545";}
			
			else if (this.widgetId.equals("visiting"))
			{return "532203702992388096";}
			
			else if (this.widgetId.equals("events"))
			{return "363242570119270400";}
			
			else if (this.widgetId.equals("library"))
			{return "532213370254929920";}

			else if (this.widgetId.equals("nhm-id"))
			{return "535099551150256128";}
			
			else if (this.widgetId.equals("nhm-wpy"))
			{return "535468768726876160";}
		}
		return null;
	}

	public void setWidgetid(String widgetId) {
		this.widgetId = widgetId;
	}

	private ValueMap properties;
	private String height;
	private String widgetId;
	

	public TwitterHelper(SlingHttpServletRequest request)
	{
		this.helperFactory = new HelperFactory(request);
		properties = helperFactory.getProperties();
		init();
	}
	
	private void init()
	{
		
		if (this.properties.get("height", String.class) != null)
		{
			this.height = this.properties.get("height",String.class);
		}
		
		if (this.properties.get("widgetid", String.class) != null)
		{
			this.widgetId = this.properties.get("widgetid",String.class);
		}

	}
}
	