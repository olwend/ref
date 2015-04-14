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
	if (helper.getNoOfItems() != null){
		noOfItems = helper.getNoOfItems();
	}
	String componentID = "";
	if (helper.getComponentTitle() != null) {
		componentID = new String(helper.getComponentTitle()).toLowerCase();
	}
%> 

<% String path = "";
	if(helper.getRootPagePath() !=null && !helper.getRootPagePath().equals("")) {
		path = helper.getRootPagePath();
	} else {
		path = currentPage.getPath(); 
	}
%>
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

<% if (currentPage.getPath().equals(helper.getRootPagePath())){ %>
	
	<%
	
	//^^^ Trick done @ line 59 won't work when stuff is placed in News and in Year folders instead. Needs FIXING
	
	Iterator<Page> children = currentPage.listChildren();
	while (children.hasNext()) {
		Page child = children.next(); 
		if (child.getProperties().get("cq:template").equals("/apps/nhmwww/templates/contentpage")) { 
			%>
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
	
	
<% }%>
