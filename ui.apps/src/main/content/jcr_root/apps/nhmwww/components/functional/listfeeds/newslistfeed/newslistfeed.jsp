<%--

  List Feed component.

  Will list the items under a specified root

--%>
<%@page import="uk.ac.nhm.nhm_www.core.componentHelpers.DatedAndTaggedFeedListHelper"%>
<%@page import="uk.ac.nhm.nhm_www.core.model.DatedAndTaggedFeedListElement"%>
<%@page import="java.util.Iterator"%>
<%-- <%@page import="uk.ac.nhm.nhm_www.core.componentHelpers.FeedListHelper"%>  --%>
<%@include file="/apps/nhmwww/components/global.jsp"%> 
<%@page session="false" %>
<cq:defineObjects />
<%-- <cq:includeClientLib categories="nhm-www.pressreleaseslist"/> --%>
<cq:includeClientLib categories="nhm-www.newslist"/>

<% DatedAndTaggedFeedListHelper helper = new  DatedAndTaggedFeedListHelper(properties, pageManager, currentPage, request, resourceResolver); %>


<%
	Integer noOfItems = 3;
	if (helper.getNoOfItems() != null && helper.getNoOfItems() >= 0){
		noOfItems = helper.getNoOfItems();
	}
	
	Boolean hideMonths = false;
	if (helper.getHideMonths() != null){
		hideMonths = helper.getHideMonths();
	}
	
	String componentID = "";
	if( helper.getComponentTitle() != null && !helper.getComponentTitle().equals("")){
		componentID = new String(helper.getComponentTitle()).toLowerCase();
	}
	
	String path = "";
	if(helper.getRootPagePath() !=null && !helper.getRootPagePath().equals("")) {
		path = helper.getRootPagePath();
	} else {
		path = currentPage.getPath(); 
	}
	Page rootPage = pageManager.getPage(path);
%>

<%-- ###START### Component Logic for YEAR Page --%>
<%-- This is the logic that's meant to happen @ YEAR and NEWS. Needs further improvement --%>

	ComponentID (aka ComponentTitle) : <%=componentID %>
	Depth (aka Difference with Year): <%=depth %>
<%
	Iterator<Page> children = rootPage.listChildren();
	if (helper.getComponentTitle() != null) {%>
		<h3>
			<%if (helper.getHyperLink() != null) {%>
				<a href="<%=helper.getHyperLink() %>" <%=helper.getNewwindow()%>>
			<%}%> 
			
			<%=helper.getComponentTitle() %>
			
			<%if (helper.getHyperLink() != null) {%>
				</a>
			<%}%>
		</h3>
	<%}
	while (children.hasNext()) {
		Page child = children.next(); 
		if (child.getProperties().get("cq:template").equals("/apps/nhmwww/templates/contentpage")) { 
			%>
			<div class="pressreleaselistfeed-wrapper" id="pressreleaselistfeed_wrapper"
					data-rootpath="<%=child.getPath() %>"
					data-pagesize="<%=noOfItems %>"  
					data-componentid="<%=componentID %>"
					data-hidemonths="<%=hideMonths %>">
					
				<h4>
					<%if ( child.getTitle() != null ) {%>
						<%=child.getTitle() %>
					<% } else { %>
						<%=child.getName() %>
					<% } %>
				</h4>
				<div class="press-office--list" id="press-office--list-<%=componentID %>">

		    	</div>
			</div>
			<%
		}
	}
%>
<%-- ####END#### Component Logic for YEAR Page --%>