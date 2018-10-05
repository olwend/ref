<%@page session="false"
          import="com.day.cq.wcm.api.Page,
                  com.day.cq.wcm.api.WCMMode"%>
<%@include file="/apps/nhmwww/components/global.jsp"%>
<%
    if (WCMMode.fromRequest(request) == WCMMode.EDIT) {
       %> <cq:includeClientLib categories="nhmwww.eventdetailpageconfig"/><%
    }
%>
<div class="main-section">
<%if(properties.get("hideTitleBar") == null) { %> 
	<div class="small-12 large-text-left columns">
			<cq:include path="title" resourceType="nhmwww/components/functional/title"/>
	</div>
<%} %>
	<%-- <cq:include path="title" resourceType="nhmwww/components/functional/title" />--%>
	<cq:include path="par" resourceType="foundation/components/parsys" />
</div>