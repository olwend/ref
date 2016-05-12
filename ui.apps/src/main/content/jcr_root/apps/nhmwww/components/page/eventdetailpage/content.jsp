<%@page session="false"
          import="com.day.cq.wcm.api.WCMMode"%>
<%@include file="/apps/nhmwww/components/global.jsp"%>
<%
    if (WCMMode.fromRequest(request) == WCMMode.EDIT) {
       %> <cq:includeClientLib categories="nhmwww.eventdetailpageconfig"/><%
    }
    String title = (String) properties.get("jcr:eventTitle", "");
    String eventSelect = (String) properties.get("eventSelect", "").toLowerCase();
    //TO DO: Parse properly from the dialog properties
    if (eventSelect.equals("science")) {
          eventSelect = "our-science";                                                     
    }
    else if (eventSelect.equals("school")) {
          eventSelect = "schools";                                                     
    }
    else if (eventSelect.equals("tring") || eventSelect.equals("visitor")) {
          eventSelect = "visit";                                                     
    }
%>
<div class="main-section <%= eventSelect %>"> 
	<div class="small-12 large-text-left columns">
        <div class="row title-bar">
            <div class="small-12 columns">
                <h1><%= title %></h1>
            </div>
        </div>
	</div>
	<cq:include path="par" resourceType="foundation/components/parsys" />
</div>
