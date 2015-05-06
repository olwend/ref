<%--

  List Feed component.

  Will list the items under a specified root

--%>

<%@page import="uk.ac.nhm.nhm_www.core.componentHelpers.DatedAndTaggedFeedListHelper"%>
<%@page import="uk.ac.nhm.nhm_www.core.model.DatedAndTaggedFeedListElement"%>
<%@include file="/apps/nhmwww/components/global.jsp"%> 
<%@page session="false" %>
<cq:defineObjects />
<cq:includeClientLib categories="nhm-www.newslist"/>

<%	DatedAndTaggedFeedListHelper helper = new  DatedAndTaggedFeedListHelper(properties, pageManager, currentPage, request, resourceResolver);

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

<div class="newslistfeed-wrapper" id="newslistfeed_wrapper_<%=componentID%>" data-rootpath="<%= path  %>" data-pagesize="<%=noOfItems %>" data-componentid="<%=componentID %>" data-hidemonths="<%=hideMonths %>" data-multilevel="true"  data-tags="<%= helper.getTagsString()%>">
	<%if (helper.getComponentTitle() != null) {%><h3><%if (helper.getHyperLink() != null) {%><a href="<%=helper.getHyperLink() %>"<%=helper.getNewwindow()%>><%}%><%=helper.getComponentTitle() %><%if (helper.getHyperLink() != null) {%></a><%}%></h3> <%}%>
    <!-- START PAGINATION -->
    
    <!-- END PAGINATION -->
    <div class="press-office--list" id="press-office--list-<%=componentID%>">
    </div>
</div>





