<%@page import="uk.ac.nhm.nhm_www.core.componentHelpers.LinkListHelper"%>
<%@page session="false"%>
<%@include file="/apps/nhmwww/components/global.jsp"%>
<cq:defineObjects />

<% LinkListHelper helper = new LinkListHelper(properties, pageManager, currentPage, request, resourceResolver); %>
<%-- [Optional] Title & HyperLink --%>
<%if (helper.getComponentTitle() != null) {%><h2><%if (helper.getHyperLink() != null) {%><a href="<%=helper.getHyperLink() %>"<%=helper.getNewwindow()%>><%}%><%=helper.getComponentTitle() %><%if (helper.getHyperLink() != null) {%></a><%}%></h2> <%}%>

<%-- [Optional] Description --%>
<%if (helper.hasDescription()) { %> <div> <%= helper.getDescription() %></div> <% } %>


<% 
	StringBuffer strBuff= helper.displayColumns();
%>

Getting it Done switftly 



<% %>