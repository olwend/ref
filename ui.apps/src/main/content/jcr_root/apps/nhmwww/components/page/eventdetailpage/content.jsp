<%@page session="false"
          import="com.day.cq.wcm.api.Page,
                  com.day.cq.wcm.api.WCMMode"%>
<%@include file="/apps/nhmwww/components/global.jsp"%>
<%
    if (WCMMode.fromRequest(request) == WCMMode.EDIT) {
       %> <cq:includeClientLib categories="nhmwww.eventdetailpage"/><%
    }
%>
<div class="main-section"> 
	<div class="small-12 large-text-left columns">
			<cq:include path="title" resourceType="nhmwww/components/functional/title"/>
	</div>
	<cq:include path="par" resourceType="foundation/components/parsys" />
</div>
