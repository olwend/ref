<%@page session="false"%>
<%@include file="/libs/foundation/global.jsp" %>
<cq:includeClientLib categories="nhmwww.eventcalendarcomponent"/>

<div class="row event--calendar--wrapper">
    <ul class="small-block-grid-1 medium-block-grid-3 directory-search--fields-block-grid">
        <li>
            <legend class="calendar--search--label">Keyword search</legend>
            <div class="search-keywords small-10 medium-10 large-10 columns">
                <input type="text" id="keywordsInput" name="Keywords" placeholder='Your keyword'/>
            </div>
            <div class="search-icon small-2 medium-2 large-2 columns">
                <img src="/etc/designs/nhmwww/img/icons/search-icon.png"/>
            </div>
        </li>
        <li>
            <div class="small-12 medium-12 large-12 columns">
                <legend class="calendar--search--label left">Filter by</legend>
                <legend class="reset--filter right">Reset filter</legend>
            </div>
            <div class="small-12 medium-12 large-12 columns calendar--select">
                <select id="filterOne">
                    <c:set var="filterOneLabel" value="<%= properties.get("filterOneLabel" , String.class) %>" />
                    <option value="none" selected="selected">${filterOneLabel}</option>
                    <c:set var="filterOneOptions" value="<%= properties.get("filterOne", String[].class) %>" />
				    <c:forEach var="filterOneOption" items="${filterOneOptions}">
                        <c:set var="filterOneName" value="${fn:split(filterOneOption, '/')}"/>
                        <c:set var="filterOneName2" value="${fn:replace(filterOneName[1], '-', ' ')}" />
                        <option value="${filterOneOption}">${filterOneName2}</option> 
                    </c:forEach>
				</select>
            </div>
            <div class="small-12 medium-12 large-12 columns calendar--select">
                <select id="filterTwo" class="mb-0">
                    <c:set var="filterTwoLabel" value="<%= properties.get("filterTwoLabel", String.class) %>" />
                    <option value="none" selected="selected">${filterTwoLabel}</option>
                    <c:set var="filterTwoOptions" value="<%= properties.get("filterTwo", String[].class) %>" />
				    <c:forEach var="filterTwoOption" items="${filterTwoOptions}">
                        <c:set var="filterTwoName" value="${fn:split(filterTwoOption, '/')}"/>
                        <c:set var="filterTwoName2" value="${fn:replace(filterTwoName[1], '-', ' ')}" />
                        <option value="${filterTwoOption}">${filterTwoName2}</option> 
                    </c:forEach>
				</select> 
            </div>
        </li>
        <li>
            <div class="small-12 medium-12 large-12 columns helper">
                <legend class="calendar--search--label">Date</legend>
            </div>
            <div class="small-6 medium-6 large-6 columns pr-10 date--picker">
                <div class="date--wrapper columns">
                    <div class="small-12 medium-12 large-12 columns">
                        <legend class="dates--label">From</legend>
                    </div>
                    <div class="small-12 medium-12 large-12 columns">
                        <div class="small-10 medium-10 large-10 columns calendar--text">
                            <input readonly="true" type="text" placeholder="___ __/__/____ " id="dateFrom" class="dp">
                        </div>
                        <div class="small-2 medium-2 large-2 columns calendar--icon">
                            <img src="/etc/designs/nhmwww/img/icons/calendar-icon.png"/>
                        </div>
                    </div>
                </div>
            </div>
            <div class="small-6 medium-6 large-6 columns date--picker">
                <div class="date--wrapper columns">
                    <div class="small-12 medium-12 large-12 columns">
                        <legend class="dates--label">To</legend>
                    </div>
                   <div class="small-12 medium-12 large-12 columns">
                        <div class="small-10 medium-10 large-10 columns calendar--text">
                            <input readonly="true" type="text" placeholder="___ __/__/____ " id="dateTo" class="dp"/>
                        </div>
                        <div class="small-2 medium-2 large-2 columns calendar--icon">
                            <img src="/etc/designs/nhmwww/img/icons/calendar-icon.png"/>
                        </div>
                    </div>
                </div>
            </div>
            <c:set var="displayExtra" value="<%= properties.get("extraOptions", Boolean.class) %>" />
            <c:if test="${displayExtra}">
                <div class="small-4 medium-4 large-4 columns pr-10">
                    <div id="today" class="small-12 medium-12 large-12 columns date--button">
                        <input readonly="true" type="button" value="Today" name="submit"/>
                    </div>
                </div>
                <div class="small-4 medium-4 large-4 columns pr-10">
                    <div id="sevenDays" class="small-12 medium-12 large-12 columns date--button">
                        <input readonly="true" type="button" value="Next 7 days" name="submit"/>
                    </div>
                </div>
                <div class="small-4 medium-4 large-4 columns">
                    <div id="thirtyDays" class="small-12 medium-12 large-12 columns date--button">
                        <input readonly="true" type="button" value="Next 30 days" name="submit"/>
                    </div>
                </div>
            </c:if>
        </li>
    </ul>
</div>