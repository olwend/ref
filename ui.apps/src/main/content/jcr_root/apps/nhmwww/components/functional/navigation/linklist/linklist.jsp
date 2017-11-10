<%@page import="uk.ac.nhm.core.componentHelpers.LinkListHelper"%>
<%@page session="false"%>
<%@include file="/apps/nhmwww/components/global.jsp"%>
<cq:defineObjects />

<% LinkListHelper helper = new LinkListHelper(properties, pageManager, currentPage, request, resourceResolver); %>

<%-- [Mandatory] Background Color --%>
<div class="linklist--container linklist--container__<%=helper.getBackgroundColor() %> ">
	<% helper.setIsFullWidth(resource); %>
	<%-- [Optional] Title & HyperLink --%>
	<%if (helper.getComponentTitle() != null) {%><h2 class="linklist--container--header"><%if (helper.getHyperLink() != null) {%><a href="<%=helper.getHyperLink() %>"<%=helper.getNewwindow()%>><%}%><%=helper.getComponentTitle() %><%if (helper.getHyperLink() != null) {%></a><%}%></h2> <%}%>
	
	<%-- [Optional] Description --%>
	<% if (helper.getDescription() != null) { %> <div class="linklist--container--description"> <%= helper.getDescription() %></div> <% } %>
	
	<%-- [Mandatory] Link Lists Generation --%>
	<% StringBuffer strBuff= helper.displayColumns(); %>
	<%= strBuff %>
</div>
<% %>