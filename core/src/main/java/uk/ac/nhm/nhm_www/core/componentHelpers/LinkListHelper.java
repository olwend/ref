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
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.nhm.nhm_www.core.utils.NodeUtils;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

public class LinkListHelper extends ListHelper {

	//Awaiting Theo's styling

	
	protected static final Logger LOG = LoggerFactory.getLogger(LinkListHelper.class);	
	
	private String description = StringUtils.EMPTY;
	private String numColumns = StringUtils.EMPTY;
	private String firstHeader;
	private String secondHeader;
	private String thirdHeader;
	private String[] firstLinkListItems;
	private String[] secondLinkListItems;
	private String[] thirdLinkListItems;
	private boolean isFullWidth = false;
	
	public LinkListHelper(ValueMap properties, PageManager pageManager, Page currentPage, HttpServletRequest request, ResourceResolver resourceResolver) {
		super(properties, pageManager, currentPage, request, resourceResolver);
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
		
		if(this.properties.get("firstLinkListItems", String.class) != null){
			this.firstLinkListItems = this.properties.get("firstLinkListItems", String[].class);
		}
		
		// Second Column Links
		if(this.properties.get("secondHeader", String.class) != null){
			this.secondHeader = this.properties.get("secondHeader", String.class);
		}
		
		if(this.properties.get("secondLinkListItems", String.class) != null){
			this.secondLinkListItems = this.properties.get("secondLinkListItems", String[].class);
		}
		
		// Third Column Links
		if(this.properties.get("thirdHeader", String.class) != null){
			this.thirdHeader = this.properties.get("thirdHeader", String.class);
		}
		
		if(this.properties.get("thirdLinkListItems", String.class) != null){
			this.thirdLinkListItems = this.properties.get("thirdLinkListItems", String[].class);
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
	
	public StringBuffer displayColumns() throws JSONException {
		StringBuffer columns = new StringBuffer();
		
		if (firstLinkListItems != null){
			columns.append(addHeader(firstHeader));
			columns.append(addList(firstLinkListItems));	
			
			if (numColumns != "firstcolumn"){
				if (secondLinkListItems != null){
					columns.append(addHeader(secondHeader));
					columns.append(addList(secondLinkListItems));
					
					if (numColumns != "secondcolumn"){
						if (thirdLinkListItems != null){
							columns.append(addHeader(thirdHeader));
							columns.append(addList(thirdLinkListItems));
						}
					}
				}	
			}
		}
		return columns;
	}

	private Object addHeader(String header) {
		String ret = StringUtils.EMPTY;
		if(header != null){
			ret = "<h3>" + header + "</h3>";
		}
		return ret;
	}
	
	public StringBuffer addList(String[] columnItems) throws JSONException {
		StringBuffer columnString = new StringBuffer();
		columnString.append("<ul class=\"first-column\">");

	    if (columnItems != null)
	    {
	        for (String linkItem : columnItems) {
	            JSONObject json = new JSONObject(linkItem);
	            
				String linkTitle = json.getString("text");
				
	            String linkURL = json.getString("url");
	            
				Boolean isNewWindow = json.getBoolean("openInNewWindow"); 
				String windowTarget = "";
				if (isNewWindow == true) {
					windowTarget = "_blank";
				}
				else {
					windowTarget = "_self";
				}
				
				columnString.append(createListItem(linkTitle, linkURL, windowTarget));
	            
	        }
	    }
	    columnString.append("</ul>");
	    
	    return columnString;
	}
	
	public StringBuffer createListItem(String title, String url, String target){
		StringBuffer listItem = new StringBuffer();
		listItem.append(""
				+ "<li>"
					+ "<a href=\"" + url + "\" target=\"" + target + "\">"
						+ title
					+ "</a>"
				+ "</li>");
		return listItem;
	}
	
	
}
