<%@page session="false"
        import="com.day.cq.wcm.api.WCMMode"%>
<%@include file="/apps/nhmwww/components/global.jsp"%>
<cq:includeClientLib categories="cq.widgets"/>

<%
if (WCMMode.fromRequest(request) == WCMMode.DISABLED || WCMMode.fromRequest(request) == WCMMode.PREVIEW) {
%>       
<!-- Add these libraries manually for mobile devices   -->
<script src="/etc/clientlibs/foundation/shared.js" type="text/javascript"></script>
<script src="/libs/cq/ui/rte.js" type="text/javascript"></script>
<script src="/libs/cq/ui/widgets.js" type="text/javascript"></script>
<script src="/libs/cq/searchpromote/widgets.js" type="text/javascript"></script>
<script src="/apps/sidekick-customization/clientlib.js" type="text/javascript"></script>
<script src="/libs/cq/ui/widgets/themes/default.js" type="text/javascript"></script>
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
                <h5 id="noResultsToday">There are no museum activities today</h5>
                <h5 id="noResults">There are no museum activities on this day that match your search criteria</h5>
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
<div id="events-calendar-loading"></div>