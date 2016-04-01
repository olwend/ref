<%@page session="false"%>
<%@include file="/libs/foundation/global.jsp" %>
<cq:includeClientLib categories="nhmwww.eventcalendarcomponent"/>

<div class="row event--calendar--wrapper">
    <ul class="small-block-grid-1 medium-block-grid-3 directory-search--fields-block-grid">
        <li>
            <legend class="directory-search--label">Keyword search</legend>
            <div class="search-keywords">
                <input type="text" id="keywordsInput" name="Keywords" placeholder='Your keyword' />
            </div>
        </li>
        <li>
            <div class="small-12 medium-12 large-12 columns">
                <legend class="directory-search--label">Filter by</legend>
            </div>
            <div class="small-12 medium-12 large-12 columns">
                <select id="event">
                    <option value="none" selected="selected">Event type</option>
                    <c:set var="events" value="<%= properties.get("eventTags", String[].class) %>" />
				    <c:forEach var="event" items="${events}">
                        <c:set var="eventName" value="${fn:split(event, '/')}"/>
                        <option value="${event}">${eventName[1]}</option> 
                    </c:forEach>	
				</select>
            </div>
            <div class="small-12 medium-12 large-12 columns">
                <select id="audience" class="mb-0">
                    <option value="none" selected="selected">Who am I</option>
                    <c:set var="audiences" value="<%= properties.get("audiencesTags", String[].class) %>" />
				    <c:forEach var="audience" items="${audiences}">
                        <c:set var="audienceName" value="${fn:split(audience, '/')}"/>
                        <option value="${audience}">${audienceName[1]}</option> 
                    </c:forEach>
				</select> 
            </div>
        </li>
        <li>
            <div class="small-12 medium-12 large-12 columns">
                <legend class="directory-search--label">Date</legend>
            </div>
            <div class="small-6 medium-6 large-6 columns pr-10">
                HERE ONE DATEPICKER
            </div>
            <div class="small-6 medium-6 large-6 columns">
                HERE ANOTHER DATEPICKER
            </div>
            <div class="small-4 medium-4 large-4 columns pr-10">
                <div class="small-12 medium-12 large-12 columns date--button">
                    <input id="today" type="button" value="Today" name="submit"/>
                </div>
            </div>
            <div class="small-4 medium-4 large-4 columns pr-10">
                <div class="small-12 medium-12 large-12 columns date--button">
                    <input id="sevenDays" type="button" value="Next 7 days" name="submit"/>
                </div>
            </div>
            <div class="small-4 medium-4 large-4 columns">
                <div class="small-12 medium-12 large-12 columns date--button">
                    <input id="thirtyDays" type="button" value="Next 30 days" name="submit"/>
                </div>
            </div>
        </li>
    </ul>
</div>

