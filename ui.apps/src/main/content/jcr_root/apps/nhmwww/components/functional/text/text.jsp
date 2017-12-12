<%@page session="false"%>
<%@include file="/apps/nhmwww/components/global.jsp"%>
<%@page import="uk.ac.nhm.core.componentHelpers.*"%>
<cq:defineObjects/>
<%
	TextHelper helper = new TextHelper(resource);

	if(helper.isComponentInitialised()) {
%>
	<%=helper.getText()%>
<% } else { %>
	<div class="row">
		<h4>Text</h4>
	</div>
<% } %>
