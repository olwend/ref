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
                <select id="filterOne">
                    <c:set var="filterOneLabel" value="<%= properties.get("filterOneLabel" , String.class) %>" />
                    <option value="none" selected="selected">${filterOneLabel}</option>
                    <c:set var="filterOneOptions" value="<%= properties.get("filterOne", String[].class) %>" />
				    <c:forEach var="filterOneOption" items="${filterOneOptions}">
                        <c:set var="filterOneName" value="${fn:split(filterOneOption, '/')}"/>
                        <option value="${filterOneOption}">${filterOneName[1]}</option> 
                    </c:forEach>	
				</select>
            </div>
            <div class="small-12 medium-12 large-12 columns">
                <select id="filterTwo" class="mb-0">
                    <c:set var="filterTwoLabel" value="<%= properties.get("filterTwoLabel", String.class) %>" />
                    <option value="none" selected="selected">${filterTwoLabel}</option>
                    <c:set var="filterTwoOptions" value="<%= properties.get("filterTwo", String[].class) %>" />
				    <c:forEach var="filterTwoOption" items="${filterTwoOptions}">
                        <c:set var="filterTwoName" value="${fn:split(filterTwoOption, '/')}"/>
                        <option value="${filterTwoOption}">${filterTwoName[1]}</option> 
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

