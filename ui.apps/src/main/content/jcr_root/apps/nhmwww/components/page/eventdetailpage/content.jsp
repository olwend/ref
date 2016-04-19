<%@page session="false"
          import="com.day.cq.wcm.api.Page,
                  com.day.cq.wcm.api.WCMMode"%>
<%@include file="/apps/nhmwww/components/global.jsp"%>
<cq:includeClientLib categories="nhmwww.eventdetailpage"/>

<%
    if (WCMMode.fromRequest(request) == WCMMode.EDIT) {
       %> <cq:includeClientLib categories="nhmwww.eventdetailpageconfig"/><%
    }
    String title = (String) properties.get("jcr:eventTitle", "");
    String eventSelect = (String) properties.get("eventSelect", "").toLowerCase();
%>
<div class="main-section event--detail--page"> 
	<div class="small-12 large-text-left columns">
        <div class="row title-bar <%= eventSelect %>">
            <div class="small-12 columns">
                <h1><%= title %></h1>
            </div>
        </div>
	</div>
	<cq:include path="par" resourceType="foundation/components/parsys" />
</div>
