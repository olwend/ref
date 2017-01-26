<%@page session="false"%>
<%@ page import ="java.util.*,
				  java.text.*,
				  java.util.Date,
				  uk.ac.nhm.nhm_www.core.componentHelpers.EventScheduleHelper" %>
<%@include file="/libs/foundation/global.jsp" %>

<%
	final EventScheduleHelper helper = new EventScheduleHelper(resourceResolver, currentPage);
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
                        <td class="small-5 medium-5 large-3 event--schedule--td event--schedule--title">Event dates ${today}</td>
                        <td class="small-7 medium-7 large-9  event--schedule--td event--schedule--title"></td>

                    </tr>
                </thead>
                <tbody class="event--schedule--tbody">
                    <c:forEach var="date" items="${sortedDates}" varStatus="loop">
                    
						<% DateFormat df = new SimpleDateFormat("dd MMM yyyy");
						String s = String.valueOf(pageContext.getAttribute("date"));
						Date calDate = null;
						String newDateString = null;
						Date today = new Date();
						try {
							calDate = df.parse(s);
					        newDateString = df.format(calDate);
					    } catch (ParseException e) {
					        e.printStackTrace();
					    }
						if(today.before(calDate)) { %>

	                        <tr>
	                            <c:choose>
	                                <c:when test="${loop.index < 7}">
	                                    <td class="event--schedule--td">${date}</td>
	                                    <td class="event--schedule--times">${datesMap[date]}</td>
	                                </c:when>
	                                <c:otherwise>
	                                    <td style="display:none;" class="event--schedule--td">${date}</td>
	                                    <td style="display:none;" class="event--schedule--times">${datesMap[date]}</td>
	                                </c:otherwise>
	                            </c:choose>
	                        </tr>
	                        
                        <%}%>
                        
                    </c:forEach>
                </tbody>
            </table>
            <c:if test="${fn:length(sortedDates) > 7}">
                <div class="small-12 medium-12 large-12 event--schedule--show--more" id="showMore">Show more</div>
            </c:if>
        </div>
    </c:when>
    <c:otherwise>
        The page has not been set up yet.
    </c:otherwise>
</c:choose>