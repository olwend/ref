package uk.ac.nhm.nhm_www.core.componentHelpers;

import java.util.List;

import org.apache.sling.api.resource.ValueMap;

import uk.ac.nhm.nhm_www.core.model.FeedListElement;
import uk.ac.nhm.nhm_www.core.utils.LinkUtils;

public class FeedListHelper extends HelperBase {
	private ValueMap properties;
	private String componentTitle;
	private String hyperLink;
	private List<Object> elements;
	
	
	public FeedListHelper(ValueMap properties) {
		this.properties = properties;
		init();
	}

	private void init() {
		if (this.properties.get("title", String.class) != null) {
			this.componentTitle = this.properties.get("title",String.class);
		}
		if (this.properties.get("hyperlink", String.class) != null) {
			this.hyperLink = LinkUtils.getFormattedLink(this.properties.get("hyperlink",String.class));
		}
		
	}

	public void setComponentTitle(String title) {
		this.componentTitle = title; 
		
	}

	public Object getComponentTitle() {
		return this.componentTitle;
	}

	public void setHyperlink(String string) {
		this.hyperLink = string;
	}

	public String getHyperlink() {
		return this.hyperLink;
	}

	public boolean validateHyperlink() {
		return LinkUtils.validateUrl(this.hyperLink);
	}

	public void addListElement(FeedListElement element) {
		// TODO Auto-generated method stub
		
	}
	
	public List<Object> getChildrenElements() {
		return  this.elements;
	}

	
	

}
