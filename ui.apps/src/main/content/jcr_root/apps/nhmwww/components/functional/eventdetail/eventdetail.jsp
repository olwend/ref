<%@include file="/apps/nhmwww/components/global.jsp" %>
<%@page session="false" 
		import="com.day.cq.wcm.api.WCMMode,
				java.util.Calendar"%>
<% if (WCMMode.fromRequest(request) == WCMMode.EDIT) { %>
	<p>Event Detail</p>
	<p>Edit the event details dialog here</p> 
<% } %>

<cq:include path="par" resourceType="foundation/components/parsys" /> 