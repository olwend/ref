<%@page import="com.day.cq.wcm.api.WCMMode,
				uk.ac.nhm.core.componentHelpers.ScientistProfileHelper"%>
<%@include file="/apps/nhmwww/components/global.jsp"%> 
<%
	final ScientistProfileHelper helper = new ScientistProfileHelper(resource);
%>

<%
  	final WCMMode beforeMode = WCMMode.fromRequest(slingRequest);
  	WCMMode.PREVIEW.toRequest(slingRequest);
%>

	<cq:include path="image" resourceType="nhmwww/components/functional/foundation5image" />
<%
	beforeMode.toRequest(slingRequest);
%>