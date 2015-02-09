package uk.ac.nhm.nhm_www.core.componentHelpers;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.designer.Design;

import javax.jcr.Node;

public class HelperFactory {

	private Resource resource;
	private Page page;
	private ValueMap properties;
	private Design design;
	private Node node;
	private SlingHttpServletRequest request;
	
	public HelperFactory(Resource resource) {
		this.resource = resource;
	}
	
	public HelperFactory(SlingHttpServletRequest request) {
		this.resource = request.getResource();
		this.request = request;
	}
	
	protected Page getPage()
	{
		if (this.page == null)
		{
			this.page = this.resource.adaptTo(Page.class);			
		}
		return this.page;
	}

	protected Node getNode()
	{
		if (this.node == null)
		{
			this.node = this.resource.adaptTo(Node.class);			
		}
		return this.node;
	}
	
	protected ValueMap getProperties()
	{
		if (this.properties == null)
		{
			this.properties = this.resource.adaptTo(ValueMap.class);			
		}
		return this.properties;
	}
	
	protected ValueMap getDesign()
	{
		if (this.design == null)
		{
			this.design = this.resource.adaptTo(Design.class);			
		}
		return this.properties;
	}
	
	protected Resource getResource()
	{
		return this.resource;
	}
	
	protected SlingHttpServletRequest getRequest()
	{
		return this.request;
	}
}