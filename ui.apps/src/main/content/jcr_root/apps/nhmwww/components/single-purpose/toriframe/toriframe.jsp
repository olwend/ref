<%@page session="false"
	import="uk.ac.nhm.core.componentHelpers.TorIframeHelper"%>
<%@include file="/apps/nhmwww/components/global.jsp"%>

<%TorIframeHelper helper = new TorIframeHelper(properties, request); %>

<%=helper.getIframeCode()%>