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
				<h2>External Positions</h2><%
				for (final ProfessionalActivity activity: setPositions) { %>
					<p><%= activity.getHTMLContent(helper.getLastName() + " " + helper.getInitials()) %></p>
				<% } %>
			<% } %>
		<%-- External Positions --%>
			
			
		<h2>Fellowships</h2>
		<%-- Fellowships --%><%
			Set<ProfessionalActivity> setFellowships = helper.getProfessionalActivitySet(activities, ScientistProfileHelper.PROFESSIONAL_ACTIVITY_TYPE_FELLOWSHIP);
			for (final ProfessionalActivity activity: setFellowships) { %>
				<p><%= activity.getHTMLContent(helper.getLastName() + " " + helper.getInitials()) %></p>
			<% } %>
		<h2>Committees</h2>
		<%-- Committees --%><%
			Set<ProfessionalActivity> setCommittees = helper.getProfessionalActivitySet(activities, ScientistProfileHelper.PROFESSIONAL_ACTIVITY_TYPE_COMMITTEES);
			for (final ProfessionalActivity activity: setCommittees) { %>
				<p><%= activity.getHTMLContent(helper.getLastName() + " " + helper.getInitials()) %></p>
			<% } %>
		<h2>Societies and memberships</h2>
		<%-- Membeships --%><%
			Set<ProfessionalActivity> setMemberships = helper.getProfessionalActivitySet(activities, ScientistProfileHelper.PROFESSIONAL_ACTIVITY_TYPE_MEMBERSHIP);
			for (final ProfessionalActivity activity: setMemberships) { %>
				<p><%= activity.getHTMLContent(helper.getLastName() + " " + helper.getInitials()) %></p>
			<% } %>
		<h2>Editorial boards</h2>
		<%-- Editorships --%><%
			Set<ProfessionalActivity> setEditorships = helper.getProfessionalActivitySet(activities, ScientistProfileHelper.PROFESSIONAL_ACTIVITY_TYPE_EDITORSHIP);
			for (final ProfessionalActivity activity: setEditorships) { %>
				<p><%= activity.getHTMLContent(helper.getLastName() + " " + helper.getInitials()) %></p>
			<% } %>
		<h2>Reviewer / referee</h2>
			<h3>Publications</h3>
			<%-- ReviewerReferee / Publications --%><%
				Set<ProfessionalActivity> setPublications = helper.getProfessionalActivitySet(activities, ScientistProfileHelper.PROFESSIONAL_ACTIVITY_TYPE_REVIEW_REFEREE_PUBLICATION);
				for (final ProfessionalActivity activity: setPublications) { %>
					<p><%= activity.getHTMLContent(helper.getLastName() + " " + helper.getInitials()) %></p>
				<% } %>
			<h3>Grants</h3>
			<%-- ReviewerReferee / Grants --%><%
				Set<ProfessionalActivity> setGrants = helper.getProfessionalActivitySet(activities, ScientistProfileHelper.PROFESSIONAL_ACTIVITY_TYPE_REVIEW_REFEREE_GRANT);
				for (final ProfessionalActivity activity: setGrants) { %>
					<p><%= activity.getHTMLContent(helper.getLastName() + " " + helper.getInitials()) %></p>
				<% } %>
				
		<h2>Events</h2>
			<%-- Events Participation --%><%
			
			Set<ProfessionalActivity> setParticipations = helper.getProfessionalActivitySet(activities, ScientistProfileHelper.PROFESSIONAL_ACTIVITY_TYPE_EVENT_PARTICIPATION);
			for (final ProfessionalActivity activity: setParticipations) { %>
				<p><%= activity.getHTMLContent(helper.getLastName() + " " + helper.getInitials()) %></p>
			<% } %>
			<%-- Events / Conference Attendance --%>
			<%-- Events / Workshop --%>
		<h3>Organisation</h3>
		<%-- Events / Organisation --%><%
			Set<ProfessionalActivity> setAdministrations = helper.getProfessionalActivitySet(activities, ScientistProfileHelper.PROFESSIONAL_ACTIVITY_TYPE_EVENT_ADMINISTRATION);
			for (final ProfessionalActivity activity: setAdministrations) { %>
				<p><%= activity.getHTMLContent(helper.getLastName() + " " + helper.getInitials()) %></p>
			<% } %>
		<%-- Internal Positions --%><%
		%>
	</div>
<% } %>