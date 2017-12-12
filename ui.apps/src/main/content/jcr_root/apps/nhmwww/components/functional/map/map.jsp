<%@page session="false"%>
<%@include file="/apps/nhmwww/components/global.jsp"%>
<%@page import="uk.ac.nhm.core.componentHelpers.GoogleMapsHelper"%>
<cq:defineObjects/>

<%
	GoogleMapsHelper helper = new GoogleMapsHelper(slingRequest);
%>
<% if (isOnEditMode) { %>
	<h4>Map</h4>
<% } %>

<div class="google-maps">
<iframe width="600" height="450" frameborder="0" style="border:0"
src="https://www.google.com/maps/embed/v1/place?q=<%=helper.getPlace()%>&key=<%=helper.getKey()%>"></iframe>
</div>