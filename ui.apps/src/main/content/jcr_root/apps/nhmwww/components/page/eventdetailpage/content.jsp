<%@page session="false"
          import="com.day.cq.wcm.api.WCMMode"%>
<%@include file="/apps/nhmwww/components/global.jsp"%>
<%
    if (WCMMode.fromRequest(request) == WCMMode.EDIT) {
       %> <cq:includeClientLib categories="nhmwww.eventdetailpageconfig"/><%
    }
    String title = (String) properties.get("jcr:eventTitle", "");
    String eventSelectTypeClassName = (String) properties.get("eventSelect", "").toLowerCase();
    //TO DO: Parse properly from the dialog properties
    if (eventSelectTypeClassName.equals("science")) {
          eventSelectTypeClassName = "our-science";                                                     
    }
    else if (eventSelectTypeClassName.equals("school")) {
          eventSelectTypeClassName = "schools";                                                     
    }
    else if (eventSelectTypeClassName.equals("tring") || eventSelectTypeClassName.equals("visitor")) {
          eventSelectTypeClassName = "visit";                                                     
    }
%>
<div class="main-section <%= eventSelectTypeClassName %>"> 
	<div class="small-12 large-text-left columns">
        <div class="row title-bar">
            <div class="small-12 columns">
                <h1><%= title %></h1>
            </div>
        </div>
	</div>
	<cq:include path="par" resourceType="foundation/components/parsys" />
</div>
