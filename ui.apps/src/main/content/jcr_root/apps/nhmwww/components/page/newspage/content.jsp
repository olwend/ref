<%--

  News Page Rendering component.
  
  Will 

--%>
<%@include file="/apps/nhmwww/components/global.jsp"%>
<div class="main-section">
	News List
	<cq:include path="newslist" resourceType="nhmwww/components/includes/newslist" />
	News List
</div>


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
<div class="pressreleaselistfeed-wrapper" id="pressreleaselistfeed_wrapper" data-rootpath="<%= path  %>" data-pagesize="<%=helper.getNumberOfItems()%>" >
	<%if (helper.getComponentTitle() != null) {%><h3><%if (helper.getHyperLink() != null) {%><a href="<%=helper.getHyperLink() %>"<%=helper.getNewwindow()%>><%}%><%=helper.getComponentTitle() %><%if (helper.getHyperLink() != null) {%></a><%}%></h3> <%}%>
    <!-- START PAGINATION -->
    
    <!-- END PAGINATION -->
    <div class="press-office--list">
    </div>
</div>
