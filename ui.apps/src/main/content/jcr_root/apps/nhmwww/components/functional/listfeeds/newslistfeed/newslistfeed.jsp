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
	String path = "";
	if(helper.getRootPagePath() !=null && !helper.getRootPagePath().equals("")) {
		path = helper.getRootPagePath();
	} else {
		path = currentPage.getPath(); 
	}
	Integer noOfItems = 3;
	if (helper.getNoOfItems() != null){
		noOfItems = helper.getNoOfItems();
	}
	String componentID = "";
	if (helper.getComponentTitle() != null) {
		componentID = new String(helper.getComponentTitle()).toLowerCase();
	}
%>

<%-- ###START### Component Logic for Development Testing in Month Page (Logic not needed, but basic functionality was here)--%>
<div class="pressreleaselistfeed-wrapper" id="pressreleaselistfeed_wrapper" data-rootpath="<%= path  %>" data-pagesize="<%=noOfItems %>" data-componentid="<%=componentID %>" >
	<%if (helper.getComponentTitle() != null) {%>
		<h3>
			<%if (helper.getHyperLink() != null) {%>
				<a href="<%=helper.getHyperLink() %>" <%=helper.getNewwindow()%>>
			<%}%> 
			
			<%=helper.getComponentTitle() %>
			
			<%if (helper.getHyperLink() != null) {%>
				</a>
			<%}%>
		</h3>
	<%}%>
	<!-- START PAGINATION -->
    
    <!-- END PAGINATION -->
    <div class="press-office--list" id="press-office--list-<%=componentID %>">
    </div>
</div>
<%-- ####END#### Component Logic for Development Testing in Month Page (Logic not needed, but basic functionality was here) --%>



<%-- ###START### Component Logic for YEAR Page --%>
<%-- This is the logic that's meant to happen @ YEAR and NEWS. Needs further improvement --%>
<% if (currentPage.getPath().equals(helper.getRootPagePath())){ %>
<%-- If Statement to separate functionality from basic Development Testing with Month pages, NEEDS FIXING--%>

	<%	
		Iterator<Page> children = currentPage.listChildren();
		while (children.hasNext()) {
			Page child = children.next(); 
			if (child.getProperties().get("cq:template").equals("/apps/nhmwww/templates/contentpage")) { 
				--%>
				<h4>
					<%if ( child.getTitle() != null ) {%>
						<%=child.getTitle() %>
					<% } else { %>
						<%=child.getName() %>
					<% } %>
				</h4>
				<%
				%>
				<div class="pressreleaselistfeed-wrapper" id="pressreleaselistfeed_wrapper"
						data-rootpath="<%=child.getPath() %>"
						data-pagesize="<%=noOfItems %>"  
						data-componentid="<%=componentID %>">
	
					<div class="press-office--list" id="press-office--list-<%=componentID %>">
			    	</div>
				</div>
				<%
			}
		}
	%>
<%}%>
<%-- ####END#### Component Logic for YEAR Page --%>