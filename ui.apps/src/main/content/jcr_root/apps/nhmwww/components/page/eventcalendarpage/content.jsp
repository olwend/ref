<%@page session="false"
        import="com.day.cq.wcm.api.WCMMode"%>
<%@include file="/apps/nhmwww/components/global.jsp"%>

<%
if (WCMMode.fromRequest(request) == WCMMode.DISABLED || WCMMode.fromRequest(request) == WCMMode.PREVIEW) {
%>       
<!-- Add these libraries manually for mobile devices   -->
<script src="/etc/clientlibs/foundation/shared.js" type="text/javascript"></script>

<cq:includeClientLib categories="nhmwww.eventscalendarwidgets"/>
<%
}
%>
    
<div class="main-section calendar--page">
    <div class="small-12 large-text-left columns">
        <cq:include path="title" resourceType="nhmwww/components/functional/title"/>
	</div>
	<cq:include path="par" resourceType="foundation/components/parsys" />
        <%
        if (WCMMode.fromRequest(request) == WCMMode.DISABLED || WCMMode.fromRequest(request) == WCMMode.PREVIEW) {
        %>   
            <cq:includeClientLib categories="nhmwww.eventcalendarpage"/>
            <div id="searchResult" class="row"></div>
            <div id="showMore">
                <h5 class="more-results-text__event--calendar--search--results">Show more</h5>
            </div>
            <div class="row event--calendar--no--results">
                <h5 id="noResults">There are no Museum events matching your search</h5>
            </div>
        <%
        }
        else {
        %>
        <div align="center"><< Search results will be displayed here >></div>
        <%
           }
        %>
        <cq:include path="par-below" resourceType="foundation/components/parsys" />
    
</div>
<div id="events-calendar-loading" style="display:none"></div>