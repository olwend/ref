<%@page session="false"%>
<%@include file="/apps/nhmwww/components/global.jsp"%>
<%@page import="uk.ac.nhm.core.componentHelpers.*"%>
<%@page import="uk.ac.nhm.core.model.SVGImage" %>
<!-- START OF EVENT & INFO SUMMARY -->
<cq:defineObjects/>
<cq:includeClientLib />
<%
EventSummaryHelper helper = new EventSummaryHelper(properties, cssClassSection.toLowerCase());
SVGImage svg = helper.getSVGImage();
%>
	<ul class="info-sidebar">
    	<li class="<%= helper.getIconClass()%>">
				<%=svg.toHtml(currentDesign.getPath() + "/") %>
        	<p><%=helper.getText() %></p>
		</li>
	</ul>
<!-- END OF EVENT & INFO SUMMARY -->