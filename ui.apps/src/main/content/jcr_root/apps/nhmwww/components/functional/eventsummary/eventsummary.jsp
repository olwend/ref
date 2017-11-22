<%@page session="false"%>
<%@include file="/apps/nhmwww/components/global.jsp"%>
<%@page import="uk.ac.nhm.core.componentHelpers.EventSummaryHelper"%>
<%@page import="uk.ac.nhm.core.model.SVGImage" %>
<!-- START OF EVENT & INFO SUMMARY -->
<cq:defineObjects/>
<cq:includeClientLib />
<%
EventSummaryHelper helper = new EventSummaryHelper(properties, cssClassSection.toLowerCase());
SVGImage svg = helper.getSVGImage();
%>
<% if(!helper.isInitialised()) { %>
	<div class="row">
		<h4>Event & exhibition summary</h4>
		Required fields:
		<ul>
			<li>Text</li>
		</ul>
	</div>
<% } else { %>
	<ul class="event-summary">
    	<li class="<%= helper.getIconClass()%>">
				<%=svg.toHtml(currentDesign.getPath() + "/") %>
        	<p><%=helper.getText() %></p>
		</li>
	</ul>
<% } %>
