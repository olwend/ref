package uk.ac.nhm.nhm_www.core.componentHelpers;

import javax.jcr.AccessDeniedException;
import javax.jcr.ItemNotFoundException;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.nhm.nhm_www.core.utils.NodeUtils;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

public class LinkListHelper extends ListHelper {

	//Awaiting Theo's styling
	public static final String ONE_COLUMN_STYLE		= "one";
	public static final String TWO_COLUMN_STYLE		= "two";
	public static final String THREE_COLUMN_STYLE	= "three";
	
	protected static final Logger LOG = LoggerFactory.getLogger(LinkListHelper.class);	
	
	private String description = StringUtils.EMPTY;
	private String numColumns = StringUtils.EMPTY;
	private String firstHeader;
	private String secondHeader;
	private String thirdHeader;
	private String[] firstColumnItems;
	private String[] secondColumnItems;
	private String[] thirdColumnItems;
	public boolean isFullWidth = false;
	
	public LinkListHelper(ValueMap properties, PageManager pageManager, Page currentPage, HttpServletRequest request, ResourceResolver resourceResolver) {
		super(properties, pageManager, currentPage, request, resourceResolver);
		
		if (properties.get("description", String.class) != null)
			this.description = properties.get("description", String.class);
	}
	
	@Override
	protected void init() {
		
		// Links Description 
		if(this.properties.get("description", String.class) != null){
			this.description = this.properties.get("description", String.class);
		}
		
		// Number of Columns being used, probably will be removed
		if(this.properties.get("numColumns", String.class) != null){
			this.numColumns = this.properties.get("numColumns", String.class);
		}
		
		// First Column Links
		if(this.properties.get("firstHeader", String.class) != null){
			this.firstHeader = this.properties.get("firstHeader", String.class);
		}
		
		if(this.properties.get("firstColumnItems", String.class) != null){
			this.firstColumnItems = this.properties.get("firstColumnItems", String[].class);
		}
		
		// Second Column Links
		if(this.properties.get("secondHeader", String.class) != null){
			this.secondHeader = this.properties.get("secondHeader", String.class);
		}
		
		if(this.properties.get("secondColumnItems", String.class) != null){
			this.secondColumnItems = this.properties.get("secondColumnItems", String[].class);
		}
		
		// Column Links
		if(this.properties.get("thirdHeader", String.class) != null){
			this.thirdHeader = this.properties.get("thirdHeader", String.class);
		}
		
		if(this.properties.get("thirdColumnItems", String.class) != null){
			this.thirdColumnItems = this.properties.get("thirdColumnItems", String[].class);
		}
		
		super.init();
		
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public boolean hasDescription() {
		boolean res = false;
		if (!getDescription().isEmpty()){
			res = true; 
		}
		return res;
	}
	
	public void getFullWidth(Node node, Resource resource) throws AccessDeniedException, ItemNotFoundException, RepositoryException {
		node = resource.getParent().adaptTo(Node.class);
		if (NodeUtils.getRowType(node) == NodeUtils.RowType.ROWFULLWIDTH)
		{
			this.isFullWidth = true;
		}
		else
		{
			this.isFullWidth = false;
		}
	}
	
}
