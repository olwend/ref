<%--

  List Feed component.

  Will list the items under a specified root

--%>
<%@page import="com.day.cq.tagging.TagManager"%>
<%@page import="org.apache.sling.api.resource.Resource"%>
<%@page import="com.day.cq.tagging.Tag"%>
<%@page import="uk.ac.nhm.nhm_www.core.services.FeedListPaginationService"%>
<%@page import="uk.ac.nhm.nhm_www.core.componentHelpers.DatedAndTaggedFeedListHelper"%>
<%@page import="uk.ac.nhm.nhm_www.core.model.DatedAndTaggedFeedListElement"%>
<%@page import="java.util.Iterator"%>
<%-- <%@page import="uk.ac.nhm.nhm_www.core.componentHelpers.FeedListHelper"%>  --%>
<%@include file="/apps/nhmwww/components/global.jsp"%> 
<%@page session="false" %>
<cq:defineObjects />
<%-- <cq:includeClientLib categories="nhm-www.pressreleaseslist"/> --%>
<cq:includeClientLib categories="nhm-www.newslist"/>

<%	DatedAndTaggedFeedListHelper helper = new  DatedAndTaggedFeedListHelper(properties, pageManager, currentPage, request, resourceResolver);
	if (helper.hasTags()) {
		final FeedListPaginationService searchService = sling.getService(FeedListPaginationService.class);
		final String[] tags = helper.getTags();
		searchService.setCqTags(tags);
	}
	else {
		%><p>This component is not configured correctly</p><%
		return;
	}

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
	if (helper.getComponentID() != null) {
		componentID = new String(helper.getComponentID()).toLowerCase();
	}
	
	boolean hideMonths = false;
	if (helper.getHideMonths() != null){
		hideMonths = helper.getHideMonths();
	}
	
%>

<% if(helper.isInitialised()) { %>
	<div class="js-feed-wrapper" id="js-feed-wrapper-<%=componentID%>" data-rootpath="<%= path  %>" data-pagesize="<%=noOfItems %>" 
							data-componentid="<%=componentID %>" data-hidemonths="<%=hideMonths %>" data-multilevel="true"  
							data-resourcetype="nhmwww/components/page/newscontentpage" data-tags="<%= helper.getTagsString()%>">
								
		<%if (helper.getComponentTitle() != null) {%><h3><%if (helper.getHyperLink() != null) {%><a href="<%=helper.getHyperLink() %>"<%=helper.getNewwindow()%>><%}%><%=helper.getComponentTitle() %><%if (helper.getHyperLink() != null) {%></a><%}%></h3> <%}%>
	    <!-- START PAGINATION -->
	    
	    <!-- END PAGINATION -->
	    <div class="feed--list" id="feed--list-<%=componentID%>">
	    </div>
	</div>
<% } else { %>
	<p> The component has not been initialised</p>
<% } %>	




