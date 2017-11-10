<%@page import="uk.ac.nhm.core.services.SharedComponentsService"%>
<%@include file="/apps/nhmwww/components/global.jsp"%>
<%@page session="false"
		import="com.day.cq.wcm.api.WCMMode"%>
<%
	final boolean isOnAuthorView = WCMMode.fromRequest(slingRequest) == WCMMode.EDIT || WCMMode.fromRequest(slingRequest) == WCMMode.DESIGN;
	final String sourcePath = properties.get("contentSource", String.class);
	
	if (sourcePath == null) {
		if (isOnAuthorView) {
%>
		<span class="warning">Shared Component Not Configured.</span>
<%
		}
		return;
	}

	final SharedComponentsService sharedComponentService = sling.getService(SharedComponentsService.class);
	final String sourceResourceType = sharedComponentService.getResourceType(sourcePath);

	if (sourceResourceType == null) {
		if (isOnAuthorView) {
%>
		<span class="error">Shared Component bad configuration.</span>
<%
		}
		return;
	}
	
		
	WCMMode beforeMode = WCMMode.fromRequest(slingRequest);
	WCMMode.PREVIEW.toRequest(slingRequest);
%>
		<sling:include path="<%= sourcePath %>" resourceType="<%= sourceResourceType %>" />
<%
	beforeMode.toRequest(slingRequest);
%>