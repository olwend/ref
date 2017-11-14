<%@page session="false"%>
<%@include file="/apps/nhmwww/components/global.jsp"%>
<%@page import="uk.ac.nhm.nhm_www.core.componentHelpers.*"%>
<%@page import="uk.ac.nhm.nhm_www.core.model.SVGImage" %>
<!-- START OF EVENT & INFO SUMMARY -->
<cq:defineObjects/>
<cq:includeClientLib />
<%
EventSummaryHelper helper = new EventSummaryHelper(properties, cssClassSection.toLowerCase());
SVGImage svg = helper.getSVGImage();
%>
	<ul class="event-summary">
    	<li class="<%= helper.getIconClass()%>">
				<%=svg.toHtml(currentDesign.getPath() + "/") %>
        	<p><%=helper.getText() %></p>
		</li>
	</ul>
<!-- END OF EVENT & INFO SUMMARY -->