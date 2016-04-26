<%@page session="false"
          import="com.day.cq.wcm.api.Page"%>
<%@include file="/apps/nhmwww/components/global.jsp"%>
<cq:includeClientLib categories="nhmwww.eventcalendarpage"/>
<cq:includeClientLib categories="cq.widgets"/>
<div class="main-section calendar--page">
    <div class="small-12 large-text-left columns">
        <cq:include path="title" resourceType="nhmwww/components/functional/title"/>
	</div>
	<cq:include path="par" resourceType="foundation/components/parsys" />
    <%
        if (WCMMode.fromRequest(request) == WCMMode.DISABLED || WCMMode.fromRequest(request) == WCMMode.PREVIEW) {
        %>     
            <div id="searchResult" class="row"></div>
            <div id="showMore" class="row more--results" style="display: none;">
                <h5 class="more-results-text more-results-text__directory-search-results">Show more</h5>
            </div>
            <div class="row no--results">
                <h5 id="noResultsToday" class="more-results-text more-results-text__directory-search-results" style="display: none;">There are no museum activities today</h5>
                <h5 id="noResults" class="more-results-text more-results-text__directory-search-results" style="display: none;">There are no museum activities on this day that match your search criteria</h5>
            </div>   
        <%
        }
    %>
</div>