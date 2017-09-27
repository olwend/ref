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
			if (this.widgetId.equals("homepage")) {
				return "532208252889464835";
			}

			else if (this.widgetId.equals("visit-us")) {
				return "532209756673277952";
			}

			else if (this.widgetId.equals("our-science")) {
				return "345479384238530560";
			}

			else if (this.widgetId.equals("museum")) {
				return "345224080041070593";
			}

			else if (this.widgetId.equals("london")) {
				return "532202770846060545";
			}

			else if (this.widgetId.equals("visiting")) {
				return "532203702992388096";
			}

			else if (this.widgetId.equals("events")) {
				return "363242570119270400";
			}

			else if (this.widgetId.equals("library")) {
				return "532213370254929920";
			}

			else if (this.widgetId.equals("nhm-id")) {
				return "535099551150256128";
			}

			else if (this.widgetId.equals("nhm-wpy")) {
				return "535468768726876160";
			}

			else if (this.widgetId.equals("nhm-tring")) {
				return "535468327502888960";
			}

			else if (this.widgetId.equals("nhm-journal-systematic-palaentology")) {
				return "483645576722911233";
			}

			else if (this.widgetId.equals("nhm-filming")) {
				return "570240166699495424";
			}

			else if (this.widgetId.equals("nhm-ice-rink")) {
				return "629292203337228288";
			}
			
			else if (this.widgetId.equals("nhm-deworm3")) { 
				return "689822489866813440"; 
			}
			
			else if (this.widgetId.equals("nhm-brachiopoda")) { 
				return "483644889641385984"; 
			}
			
			else if (this.widgetId.equals("nhm-bryozoan")) { 
				return "483645174208135168"; 
			}
			
			else if (this.widgetId.equals("nhm-cephalopoda")) { 
				return "483644384890462208"; 
			}
			
			else if (this.widgetId.equals("nhm-dino-lab")) { 
				return "483645305426944000"; 
			}
			
			else if (this.widgetId.equals("nhm-forensics")) { 
				return "483643504254394368"; 
			}
			
			else if (this.widgetId.equals("nhm-fossil-crocs")) { 
				return "535456136548593664"; 
			}
			
			else if (this.widgetId.equals("nhm-fossil-fish")) { 
				return "483643914205679616"; 
			}
			
			else if (this.widgetId.equals("nhm-meteorites")) { 
				return "535464820708229120"; 
			}
			
			else if (this.widgetId.equals("nhm-micropalaeo")) { 
				return "483644653980225537"; 
			}
			
			else if (this.widgetId.equals("nhm-oology")) { 
				return "535466483837853696"; 
			}
			
			else if (this.widgetId.equals("nhm-palaeobotany")) { 
				return "535469236890918912"; 
			}
			
			else if (this.widgetId.equals("nhm-pterosauria")) { 
				return "535467148668600320"; 
			}
			
			else if (this.widgetId.equals("nhm-coleoptera")) { 
				return "689826170041417728"; 
			}
			
			else if (this.widgetId.equals("nhm-diptera")) { 
				return "689826729561608192"; 
			}
		}
		return null;
	}

	public void setWidgetid(String widgetId) {
		this.widgetId = widgetId;
	}

	private ValueMap properties;
	private String height;
	private String widgetId;
	

	public TwitterHelper(SlingHttpServletRequest request) {
		this.helperFactory = new HelperFactory(request);
		properties = helperFactory.getProperties();
		init();
	}
	
	private void init() {
		if (this.properties.get("height", String.class) != null) {
			this.height = this.properties.get("height",String.class);
		}
		
		if (this.properties.get("widgetid", String.class) != null) {
			this.widgetId = this.properties.get("widgetid",String.class);
		}
	}
	
	public Boolean isConfigured() {
		if(this.widgetId == null || this.height == null) {
			return false;
		} else {
			return true;
		}
	}
}
	