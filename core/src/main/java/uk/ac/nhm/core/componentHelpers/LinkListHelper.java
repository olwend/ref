package uk.ac.nhm.core.componentHelpers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.jcr.AccessDeniedException;
import javax.jcr.ItemNotFoundException;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.nhm.core.utils.NodeUtils;
import uk.ac.nhm.core.utils.TextUtils;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

public class LinkListHelper extends ListHelper {

	protected static final Logger LOG = LoggerFactory.getLogger(LinkListHelper.class);	
	
	private String numColumns;
	private String description;
	private String backgroundColor;
	private boolean isFullWidth;


	List<String> headersList;
	List<String> headersLinksList;	
	List<Boolean> headersLinksNewWindowsList;
	List<Boolean> hasHeadersList;
	List<String[]> columnItemsList;
	
	TextUtils textUtils = new TextUtils();
	
	public LinkListHelper(ValueMap properties, PageManager pageManager, Page currentPage, HttpServletRequest request, ResourceResolver resourceResolver) {
		super(properties, pageManager, currentPage, request, resourceResolver);
	}
	
	@Override
	protected void init() {
		isFullWidth = false;
		
		numColumns = "";
		headersList = new ArrayList<String>();
		headersLinksList = new ArrayList<String>();
		headersLinksNewWindowsList = new ArrayList<Boolean>();
		hasHeadersList = new ArrayList<Boolean>();
		columnItemsList = new ArrayList<String[]>();
		
		String emptyStr = StringUtils.EMPTY;
		String[] emptyStrArr = { emptyStr, emptyStr, emptyStr } ;
		Collections.addAll(headersList, emptyStr, emptyStr, emptyStr);
		Collections.addAll(headersLinksList, emptyStr, emptyStr, emptyStr);
		Collections.addAll(columnItemsList, emptyStrArr, emptyStrArr, emptyStrArr);
		Collections.addAll(headersLinksNewWindowsList, false, false, false);
		Collections.addAll(hasHeadersList, false, false, false);
		
		// Links Description 
		if(this.properties.get("description", String.class) != null){
			this.description = this.properties.get("description", String.class);
		}
		
		// Number of Columns being used, probably will be removed
		if(this.properties.get("numColumns", String.class) != null){
			this.numColumns = this.properties.get("numColumns", String.class);
		}
		
		// Choose between white and grey background
		if(this.properties.get("backgroundcolor", String.class) != null){
			this.backgroundColor = this.properties.get("backgroundcolor", String.class);
		}
		
		// Links for the Three Columns
		if(this.properties.get("firstLinkListItems", String.class) != null){
			this.columnItemsList.set(0, this.properties.get("firstLinkListItems", String[].class));
			
			if(this.properties.get("secondLinkListItems", String.class) != null){
				this.columnItemsList.set(1, this.properties.get("secondLinkListItems", String[].class));
				
				if(this.properties.get("thirdLinkListItems", String.class) != null){
					this.columnItemsList.set(2, this.properties.get("thirdLinkListItems", String[].class));
				}
			}
		}
		
		// First Column	Header
		if(this.properties.get("firstHeader", String.class) != null){
			this.headersList.set(0, this.properties.get("firstHeader", String.class));
			this.headersLinksList.set(0, this.properties.get("firstHeaderLink", String.class));
			this.headersLinksNewWindowsList.set(0, this.properties.get("firstHeaderNewwindow", false));
			this.hasHeadersList.set(0, true);
			
			// Second Column
			if(this.properties.get("secondHeader", String.class) != null){
				this.headersList.set(1, this.properties.get("secondHeader", String.class));
				this.headersLinksList.set(1, this.properties.get("secondHeaderLink", String.class));
				this.headersLinksNewWindowsList.set(1, this.properties.get("secondHeaderNewwindow", false));
				this.hasHeadersList.set(1, true);
			}
			
			// Third Column
			if(this.properties.get("thirdHeader", String.class) != null){
				this.headersList.set(2, this.properties.get("thirdHeader", String.class));
				this.headersLinksList.set(2, this.properties.get("thirdHeaderLink", String.class));
				this.headersLinksNewWindowsList.set(2, this.properties.get("thirdHeaderNewwindow", false));
				this.hasHeadersList.set(2, true);
			}
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
		Integer i = 0;
		Boolean exit = false;
		
		// Will exit if there are no links in columns 1 then 2 then 3.
		while (i <= getColumnStyles()-1 && !exit) {
			if(this.columnItemsList.get(i) != null){
				columns.append(addList(i));
				i++;
			} else {
				exit = true;
			}
		}
		columns.append("</ul>");
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
	
	public StringBuffer addList(Integer columnNumber) throws JSONException {
		StringBuffer columnString = new StringBuffer();
		
		columnString.append("<li>");
			columnString.append(generateColumnDiv(columnNumber));
				columnString.append(generateLinkItems(columnNumber));
			columnString.append("</div>");
		columnString.append("</li>");
				
	    return columnString;
	}
	
	public StringBuffer generateColumnDiv(Integer columnNumber) {
		StringBuffer ret = new StringBuffer();
		StringBuffer div = new StringBuffer();
		StringBuffer h3 = new StringBuffer();
		div.append("<div class=\"linklist--column ");
		
		// Appends [linklist--column--no-header] and generates headers if there are any
		if (this.hasHeadersList.get(0)){
			if(columnNumber == 0){
				h3.append(addHeader(columnNumber));				
			} else {
				if(this.hasHeadersList.get(columnNumber)){
					h3.append(addHeader(columnNumber));
				} else {
					div.append(" linklist--column--no-header");
				}
			}
		}
		div.append("\">");
		ret.append(div);
		ret.append(h3);
		return ret;
	}

	private StringBuffer generateLinkItems(Integer columnNumber) throws JSONException {
		StringBuffer ret = new StringBuffer();
		
		ret.append("<ul class=\"linklist--link-items\">");
		if (this.columnItemsList.get(columnNumber) != null) {
		    for (String linkItem : this.columnItemsList.get(columnNumber)) {
		    	//Check that linkItem is not null
		    	if(linkItem != null) {
		    		//Check that linkItem contains valid JSON
			    	if(textUtils.isJSONValid(linkItem) == true) {
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
						ret.append(createListItem(linkTitle, linkURL, windowTarget));
			    	}
			    	else {
			    		ret.append("<p>This component is not configured correctly</p>");
			    		ret.append("</ul>");
			    		return ret;
			    	}
		    	}
		    	else {
		    		ret.append("<p>This component is not configured correctly</p>");
		    		ret.append("</ul>");
		    		return ret;
		    	}
		    }
		}
		ret.append("</ul>");
		
		return ret;
	}
	
	public String hasHeaders(Integer columnNumber){
		String ret = StringUtils.EMPTY;
		switch (columnNumber) {
		case 1:
			if(this.hasHeadersList.get(0) && !this.hasHeadersList.get(1)){
				ret = " linklist--column--no-header";
			}
			break;
		case 2:
			if(this.hasHeadersList.get(0) && !this.hasHeadersList.get(2)){
				ret = " linklist--column--no-header";
			}
			break;
		}
		return ret;
	}
	
	private StringBuffer addHeader(Integer columnNumber) {
		StringBuffer ret = new StringBuffer();
		
		if(this.headersList.get(columnNumber) != null || !this.headersList.equals("")){
			ret.append("<h3 class=\"linklist--column--header\">");
				if(this.headersLinksList.get(columnNumber) != null && !this.headersLinksList.get(columnNumber).equals("")){ ret.append("<a href=\"" + this.headersLinksList.get(columnNumber) + "\">"); }
					ret.append(this.headersList.get(columnNumber));				
				if(this.headersLinksList.get(columnNumber) != null && !this.headersLinksList.get(columnNumber).equals("")){ ret.append("</a>"); }
			ret.append("</h3>");
		}
		return ret;
	}
	
	public StringBuffer createListItem(String title, String url, String target){
		StringBuffer listItem = new StringBuffer();
		listItem.append("<li>");
			if (url != null && !url.equals("")){ listItem.append("<a href=\"" + url + "\" target=\"" + target + "\">"); }
						listItem.append(title);
			if (url != null && !url.equals("")){ listItem.append("</a>"); }
		listItem.append("</li>");
		return listItem;
	}
}
