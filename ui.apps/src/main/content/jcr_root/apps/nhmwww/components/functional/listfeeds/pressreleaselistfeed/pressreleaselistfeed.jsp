<%--

  List Feed component.

  Will list the items under a specified root

--%>
<%@page import="uk.ac.nhm.nhm_www.core.componentHelpers.PressReleaseFeedListHelper"%>
<%@page import="uk.ac.nhm.nhm_www.core.model.PressReleaseFeedListElement"%>
<%@page import="uk.ac.nhm.nhm_www.core.componentHelpers.FeedListHelper"%>
<%@include file="/apps/nhmwww/components/global.jsp"%> 
<%@page session="false" %>
<cq:defineObjects />
<cq:includeClientLib categories="nhm-www.pressreleaseslist"/>

<% PressReleaseFeedListHelper helper = new  PressReleaseFeedListHelper(properties, pageManager, currentPage, request, resourceResolver); %>

<% String path = "";
	if(helper.getRootPagePath() !=null && !helper.getRootPagePath().equals("")) {
		path = helper.getRootPagePath();
	} else {
		path = currentPage.getPath(); 
	}
%>
<div class="js-feed-wrapper" id="js-feed-wrapper" data-rootpath="<%= path  %>" data-pagesize="<%=helper.getNumberOfItems()%>" >
	<%if (helper.getComponentTitle() != null) {%><h3><%if (helper.getHyperLink() != null) {%><a href="<%=helper.getHyperLink() %>"<%=helper.getNewwindow()%>><%}%><%=helper.getComponentTitle() %><%if (helper.getHyperLink() != null) {%></a><%}%></h3> <%}%>
    <!-- START PAGINATION -->
    
    <!-- END PAGINATION -->
    <div class="feed--list">
    </div>
</div>
