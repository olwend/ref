<%@page import="uk.ac.nhm.nhm_www.core.componentHelpers.LinkListHelper"%>
<%@page session="false"%>
<%@include file="/apps/nhmwww/components/global.jsp"%>
<cq:defineObjects />

<% LinkListHelper helper = new LinkListHelper(properties, pageManager, currentPage, request, resourceResolver); %>

<%-- [Mandatory] Background Color --%>
<div class="row linklist--container linklist--container__<%=helper.getBackgroundColor() %>">
	<% helper.setIsFullWidth(resource); %>
	<div class="small-12 medium-<%=helper.getWidthNumber() %> large-<%=helper.getWidthNumber() %> columns">
		<%-- [Optional] Title & HyperLink --%>
		<%if (helper.getComponentTitle() != null) {%><h2 class="linklist--container--header"><%if (helper.getHyperLink() != null) {%><a href="<%=helper.getHyperLink() %>"<%=helper.getNewwindow()%>><%}%><%=helper.getComponentTitle() %><%if (helper.getHyperLink() != null) {%></a><%}%></h2> <%}%>
		
		<%-- [Optional] Description --%>
		<% if (helper.getDescription() != null) { %> <div class="linklist--container--description"> <%= helper.getDescription() %></div> <% } %>
		
		<%-- Debugging --%>
			The color of the background is going to be <%=helper.getBackgroundColor() %>.
			The parsys holding me allows for a medium/large-<%=helper.getWidthNumber() %> width.
			The Linklist--column-container will set medium/large-block-grids to <%= helper.getColumnStyles() %>
		<%-- Debugging --%>
		
		<%-- [Mandatory] Link Lists Generation --%>
		<% StringBuffer strBuff= helper.displayColumns(); %>
		<%= strBuff %>
	</div>
</div>
<% %>