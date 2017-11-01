<%@page session="false"%>
<%@ page import ="java.util.*,
				  java.text.*,
				  java.util.Date,
				  java.util.Calendar,
				  uk.ac.nhm.core.componentHelpers.EventScheduleHelper" %>
<%@include file="/libs/foundation/global.jsp" %>

<%
	final EventScheduleHelper helper = new EventScheduleHelper(resourceResolver, currentPage, properties);

%>

<cq:includeClientLib categories="nhmwww.eventschedule"/>
<c:set var="eventType" value="<%= helper.getEventType() %>" />
<c:set var="sortedDates" value="<%= helper.getSortedDates() %>" />
<c:choose>
    <c:when test="${not empty eventType && not empty sortedDates}">
        <c:set var="datesMap" value="<%= helper.getDatesMap() %>" />
        <div class="event--schedule--wrapper">
            <table id="datesTable" class="small-12 medium-12 large-12">
                <thead>
                    <tr>
                        <td class="<%if(helper.getOneColumn()) {%>small-7 medium-8 large-6<% } else {%>small-7 medium-5 large-3<% } %> event--schedule--td event--schedule--title">Event dates</td>
                        <td class="<%if(helper.getOneColumn()) {%>small-5 medium-4 large-6<% } else {%>small-5 medium-7 large-9<% } %> event--schedule--td event--schedule--title"></td>

                    </tr>
                </thead>
                <tbody class="event--schedule--tbody">
                
                    <% int loopIndex = 0;
                    
                    //WR-962
                    //Get today's date but decrement by one day as Date comparator doesn't work
                    //when date is equal to current date.
                    Date today = new Date();
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(today);
                    cal.add(Calendar.DATE, -1);
                    today = cal.getTime(); %>
                    
                    <c:forEach var="date" items="${sortedDates}" varStatus="loop">
						<% DateFormat df = new SimpleDateFormat("dd MMM yyyy");
                        String s = String.valueOf(pageContext.getAttribute("date"));
                        Date calDate = null;
                        String newDateString = null;

                        try {
                            calDate = df.parse(s);
                            newDateString = df.format(calDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        if(today.before(calDate)) { %>
	                        <tr>
                                <% if(loopIndex < 7) { %>
                                <c:set var="times" value="${fn:split(datesMap[date], ',')}" />

                                <td class="event--schedule--td">${date}</td>
                                <td class="event--schedule--times">
                                    <%if(helper.getOneColumn()) { %>
                                        <c:forEach var="time" items="${times}" varStatus="loop">
                                            ${time}<br>
                                        </c:forEach>
                                    <% } else { %>
										${datesMap[date]}
                                    <% } %>
                                </td>

                                <% } else { %>

                                <td style="display:none;" class="event--schedule--td">${date}</td>
                                <td style="display:none;" class="event--schedule--times">
                                    <%if(helper.getOneColumn()) { %>
                                        <c:forEach var="time" items="${times}" varStatus="loop">
                                            ${time}<br>
                                        </c:forEach>
                                    <% } else { %>
										${datesMap[date]}
                                    <% } %>
                                </td>

                                <% } %>
	                        </tr>
                        <% loopIndex++;
                        }%>
                    </c:forEach>
                </tbody>
            </table>
            <c:if test="${fn:length(sortedDates) > 7}">
                <div class="small-12 medium-12 large-12 event--schedule--show--more" id="showMore">Show more</div>
            </c:if>
        </div>
    </c:when>
    <c:otherwise>
        <p>Event Schedule</p>
        <p>Please add dates/times in the Event Detail dialog</p>
    </c:otherwise>
</c:choose>