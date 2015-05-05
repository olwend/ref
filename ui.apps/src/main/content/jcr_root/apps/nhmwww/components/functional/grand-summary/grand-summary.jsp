<%@page session="false"%>
<%@include file="/apps/nhmwww/components/global.jsp"%>
<%@page import="uk.ac.nhm.nhm_www.core.componentHelpers.*"%>
<cq:defineObjects />
<cq:includeClientLib />
<%
	GrandSummaryHelper helper = new GrandSummaryHelper(slingRequest, currentPage, properties);
%>

<div class="video-wrapper" >
	<% helper.getMobileImage().draw(out); %>
	<% helper.getImage().draw(out); %>

	<div class="caption-outer-wrapper">
		<div class="caption-inner-wrapper">
			<div class="caption">
				<% if (helper.getTitle() != null) { %> <h1><%=helper.getTitle()%></h1> <% } %>
				
				<% if (helper.getSummary() != null) { %> <p><%=helper.getSummary()%></p> <% } %>
			</div>
		</div>
	</div>
</div>
<% %>
