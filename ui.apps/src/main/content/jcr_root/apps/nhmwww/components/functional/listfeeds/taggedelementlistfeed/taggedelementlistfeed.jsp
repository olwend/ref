<%--

  List Feed component.

  Will list the items under a specified root

--%>
<%@page import="com.day.cq.tagging.TagManager"%>
<%@page import="org.apache.sling.api.resource.Resource"%>
<%@page import="com.day.cq.tagging.Tag"%>
<%@page import="uk.ac.nhm.core.services.FeedListPaginationService"%>
<%@page import="uk.ac.nhm.core.componentHelpers.TaggedFeedListHelper"%>
<%@page import="uk.ac.nhm.core.model.TaggedFeedListElement"%>
<%@page import="java.util.Iterator"%>
<%-- <%@page import="uk.ac.nhm.core.componentHelpers.FeedListHelper"%>  --%>
<%@include file="/apps/nhmwww/components/global.jsp"%> 
<%@page session="false" %>
<cq:defineObjects />
<%-- <cq:includeClientLib categories="nhm-www.pressreleaseslist"/> --%>
<cq:includeClientLib categories="nhm-www.taggedelementlistfeed"/>

<%	TaggedFeedListHelper helper = new  TaggedFeedListHelper(properties, pageManager, currentPage, request, resourceResolver);
	
	helper.setIsFullWidth(resource);
	
	if (helper.hasTags()) {
		final FeedListPaginationService searchService = sling.getService(FeedListPaginationService.class);
		final String[] tags = helper.getTags();
		searchService.setCqTags(tags);
	}
	else {%>
	    <p>This component is not configured correctly</p>
		<%return;
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

%>

<% if(helper.isInitialised()) { %>
	<div class="js-feed-wrapper" id="js-feed-wrapper-<%=componentID%>" data-rootpath="<%= path  %>" data-pagesize="<%=noOfItems %>" 
							data-componentid="<%=componentID %>" data-multilevel="true" data-tags="<%= helper.getTagsString()%>"
							data-resourcetype="nhmwww/components/page/taggedcontentpage">
								
		<%if (helper.getComponentTitle() != null) {%><h3><%if (helper.getHyperLink() != null) {%><a href="<%=helper.getHyperLink() %>"<%=helper.getNewwindow()%>><%}%><%=helper.getComponentTitle() %><%if (helper.getHyperLink() != null) {%></a><%}%></h3> <%}%>
	    <!-- START PAGINATION -->
	    <!-- END PAGINATION -->
		<div class="small-block-grid-1 medium-block-grid-2 <%=helper.getWidthStyle()%> feed--tiles" id="feed--tiles-<%=componentID%>" data-equalizer>
			
		</div>
	</div>
<% } else { %>
	<p> The component has not been initialised</p>
<% } %>	



