<%@page import="com.day.cq.wcm.api.WCMMode,
				uk.ac.nhm.nhm_www.core.componentHelpers.ScientistProfileHelper"%>
<%@include file="/apps/nhmwww/components/global.jsp"%> 
<%
	final ScientistProfileHelper helper = new ScientistProfileHelper(resource);
%>

<div class="profile-img">
<%
  	final WCMMode beforeMode = WCMMode.fromRequest(slingRequest);
  	WCMMode.PREVIEW.toRequest(slingRequest);
%>

	<cq:include path="image" resourceType="nhmwww/components/functional/foundation5image" />
<%
	beforeMode.toRequest(slingRequest);
%>
</div>