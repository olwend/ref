<%@page import="uk.ac.nhm.nhm_www.core.model.science.proactivities.ProfessionalActivity,
				uk.ac.nhm.nhm_www.core.componentHelpers.ScientistProfileHelper,
				uk.ac.nhm.nhm_www.core.model.science.Publication,
				java.util.Set,
				java.util.Map,
				org.apache.commons.lang3.StringUtils"%>
<%@include file="/apps/nhmwww/components/global.jsp"%> 
<%
	final ScientistProfileHelper helper = new ScientistProfileHelper(resource);
	final Map<String, Set<ProfessionalActivity>> activities = helper.getProfessionalActivities();
			
%>
<% if (activities != null && !activities.isEmpty()) { %>
	<div id="professionalactivities" class="content">
		<%-- External Positions --%>
		<% Set<ProfessionalActivity> setPositions = helper.getProfessionalActivitySet(activities, ScientistProfileHelper.PROFESSIONAL_ACTIVITY_TYPE_EXTERNAL_INTERNAL_POSITION); %>
		<% if (!setPositions.isEmpty()){ %>
			<% StringBuffer externalPositionsStringBuffer = new StringBuffer(); %>
			<% for (final ProfessionalActivity activity: setPositions) { %>
				<%
					String[] parameters = {
						ScientistProfileHelper.PROFESSIONAL_ACTIVITY_PARAMETER_EXTERNAL
					};
					externalPositionsStringBuffer.append(activity.getFilteredHTMLContent(helper.getLastName() + " " + helper.getInitials(), parameters));
				%>
			<% } %>
			<% if (externalPositionsStringBuffer.length() > 0) { %>
				<h2>External Positions</h2>
				<%= externalPositionsStringBuffer %>
			<% } %>
		<% } %>

		<%-- Internal Positions --%>
		<% if (!setPositions.isEmpty()){ %>
			<% StringBuffer internalPositionsStringBuffer = new StringBuffer(); %>
			<% for (final ProfessionalActivity activity: setPositions) { %>
				<%
					String[] parameters = {ScientistProfileHelper.PROFESSIONAL_ACTIVITY_PARAMETER_INTERNAL};
					internalPositionsStringBuffer.append(activity.getFilteredHTMLContent(helper.getLastName() + " " + helper.getInitials(), parameters));
				%>
			<% } %>
			<% if (internalPositionsStringBuffer.length() > 0) { %>
				<h2>Internal Positions</h2>
				<%= internalPositionsStringBuffer %>
			<% } %>
		<% } %>
	</div>
<% } %>