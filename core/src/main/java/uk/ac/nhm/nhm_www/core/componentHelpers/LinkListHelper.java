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

import com.day.cq.commons.jcr.JcrUtil;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

public class LinkListHelper extends ListHelper {

	//Awaiting Theo's styling

	
	protected static final Logger LOG = LoggerFactory.getLogger(LinkListHelper.class);	
	
	private String numColumns;
	private String description;
	private String backgroundColor;
	private String firstHeader;
	private String secondHeader;
	private String thirdHeader;
	private String[] firstLinkListItems;
	private String[] secondLinkListItems;
	private String[] thirdLinkListItems;
	private boolean isFullWidth = false;

	private boolean hasFirstColumnHeader;

	private boolean hasSecondColumnHeader;

	private boolean hasThirdColumnHeader;

	
	public LinkListHelper(ValueMap properties, PageManager pageManager, Page currentPage, HttpServletRequest request, ResourceResolver resourceResolver) {
		super(properties, pageManager, currentPage, request, resourceResolver);
	}
	
	@Override
	protected void init() {
		
		// Links Description 
		if(this.properties.get("description", String.class) != null){
			LOG.error("Getting Properties > Description is empty" );
			this.description = this.properties.get("description", String.class);
			LOG.error("Getting Properties > Description should not be empty now and is : " + this.description);
		}
		
		// Number of Columns being used, probably will be removed
		if(this.properties.get("numColumns", String.class) != null){
			this.numColumns = this.properties.get("numColumns", String.class);
		}
		
		// Choose between white and grey background
		if(this.properties.get("backgroundcolor", String.class) != null){
			this.backgroundColor = this.properties.get("backgroundcolor", String.class);
		}
		
		// First Column	
		if(this.properties.get("firstHeader", String.class) != null){
			this.firstHeader = this.properties.get("firstHeader", String.class);
			this.hasFirstColumnHeader = true;
		}
		
		if(this.properties.get("firstLinkListItems", String.class) != null){
			this.firstLinkListItems = this.properties.get("firstLinkListItems", String[].class);
		}
		
		// Second Column
		if(this.properties.get("secondHeader", String.class) != null){
			this.secondHeader = this.properties.get("secondHeader", String.class);
			this.hasSecondColumnHeader = true;
		}
		
		if(this.properties.get("secondLinkListItems", String.class) != null){
			this.secondLinkListItems = this.properties.get("secondLinkListItems", String[].class);
		}
		
		// Third Column
		if(this.properties.get("thirdHeader", String.class) != null){
			this.thirdHeader = this.properties.get("thirdHeader", String.class);
			this.hasThirdColumnHeader = true;
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
	
	public String getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	
	public void setIsFullWidth(Resource resource) throws AccessDeniedException, ItemNotFoundException, RepositoryException {
		Node parentNode = resource.getParent().adaptTo(Node.class);
		if (NodeUtils.getRowType(parentNode) == NodeUtils.RowType.ROWFULLWIDTH) {
			this.isFullWidth = true;
		} else {
			this.isFullWidth = false;
		}
	}
	
	public int getWidthNumber() {
		int ret = 6;
		if(this.isFullWidth){
			ret = 12;
		} else if (numColumns.equals("firstcolumn")){
			ret = 4;
		}
		return ret;
	}
	
	public StringBuffer displayColumns() throws JSONException {
		init();
		StringBuffer columns = new StringBuffer();
		columns.append("<ul class=\"linklist--column-container"
				+ " small-block-grid-1"
				+ " medium-block-grid-" + getColumnStyles() + ""
				+ " large-block-grid-" + getColumnStyles() + "\">");
		
		if (firstLinkListItems != null){
			columns.append(addHeader(firstHeader));
			columns.append(addList(firstLinkListItems));	
			
			if (!numColumns.equals("firstcolumn")){
				if (secondLinkListItems != null){
					columns.append(addHeader(secondHeader));
					columns.append(addList(secondLinkListItems));
					
					if (!numColumns.equals("secondcolumn")){
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

	public Integer getColumnStyles() {
		int ret = 0;
		switch (this.numColumns) {
		case "firstcolumn":
			ret = 1;
			break;
		case "secondcolumn":
			ret = 2;
			break;
		case "thirdcolumn":
			ret = 3;
			break;
		}
		return ret;
	}

	private String addHeader(String header) {
		
		//+ "<div class=\"linklist--column" + hasHeaders() + "\">"
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
	
	public String hasHeaders(){
		String ret = StringUtils.EMPTY;
		if (this.hasFirstColumnHeader){
			if(!this.hasSecondColumnHeader){
				ret = " linklist--column--no-header";
			}
			if(!this.hasThirdColumnHeader){
				ret = " linklist--column--no-header";
			}
		}
		return ret;
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
