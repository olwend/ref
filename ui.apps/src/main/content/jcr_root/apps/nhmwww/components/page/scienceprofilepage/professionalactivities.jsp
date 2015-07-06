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
		
		<%-- Fellowships --%>
		<% Set<ProfessionalActivity> setFellowships = helper.getProfessionalActivitySet(activities, ScientistProfileHelper.PROFESSIONAL_ACTIVITY_TYPE_FELLOWSHIP); %>
		<% if (!setFellowships.isEmpty()) { %>
			<h2>Fellowships</h2>
			<% for (final ProfessionalActivity activity: setFellowships) { %>
				<p><%= activity.getHTMLContent(helper.getLastName() + " " + helper.getInitials()) %></p>
			<% } %>
		<% } %>
			
		<%-- Committees --%>
		<% Set<ProfessionalActivity> setCommittees = helper.getProfessionalActivitySet(activities, ScientistProfileHelper.PROFESSIONAL_ACTIVITY_TYPE_COMMITTEES); %>
		<% if (!setCommittees.isEmpty()) { %>
			<h2>Committees</h2>
			<% for (final ProfessionalActivity activity: setCommittees) { %>
				<p><%= activity.getHTMLContent(helper.getLastName() + " " + helper.getInitials()) %></p>
			<% } %>
		<% } %>
			
		<%-- Memberships --%>
		<% Set<ProfessionalActivity> setMemberships = helper.getProfessionalActivitySet(activities, ScientistProfileHelper.PROFESSIONAL_ACTIVITY_TYPE_MEMBERSHIP); %>
		<% if (!setMemberships.isEmpty()) { %>
			<h2>Societies and memberships</h2>
			<% for (final ProfessionalActivity activity: setMemberships) { %>
				<p><%= activity.getHTMLContent(helper.getLastName() + " " + helper.getInitials()) %></p>
			<% } %>
		<% } %>
			
		<%-- Editorships --%>
		<% Set<ProfessionalActivity> setEditorships = helper.getProfessionalActivitySet(activities, ScientistProfileHelper.PROFESSIONAL_ACTIVITY_TYPE_EDITORSHIP); %>
		<% if (!setEditorships.isEmpty()) { %>
			<h2>Editorial boards</h2>
			<% for (final ProfessionalActivity activity: setEditorships) { %>
				<p><%= activity.getHTMLContent(helper.getLastName() + " " + helper.getInitials()) %></p>
			<% } %>
		<% } %>
		
		<%-- Reviewer / Referee --%>
		<% Set<ProfessionalActivity> setPublications = helper.getProfessionalActivitySet(activities, ScientistProfileHelper.PROFESSIONAL_ACTIVITY_TYPE_REVIEW_REFEREE_PUBLICATION); %>
		<% Set<ProfessionalActivity> setGrants = helper.getProfessionalActivitySet(activities, ScientistProfileHelper.PROFESSIONAL_ACTIVITY_TYPE_REVIEW_REFEREE_GRANT); %>
		<% if (!setPublications.isEmpty() || !setGrants.isEmpty()) { %>
			<h2>Reviewer / referee</h2>
				<%-- ReviewerReferee / Publications --%>
				<% if (!setPublications.isEmpty()) { %>
					<h3>Publications</h3>
						<% for (final ProfessionalActivity activity: setPublications) { %>
							<p><%= activity.getHTMLContent(helper.getLastName() + " " + helper.getInitials()) %></p>
						<% } %>
				<% } %>
				<%-- ReviewerReferee / Grants --%>
				<% if (!setGrants.isEmpty()) { %>
					<h3>Grants</h3>
						<% for (final ProfessionalActivity activity: setGrants) { %>
							<p><%= activity.getHTMLContent(helper.getLastName() + " " + helper.getInitials()) %></p>
						<% } %>
				<% } %>
		<% } %>

			<%-- Events Participation Logic--%>
				<% Set<ProfessionalActivity> setEParticipations = helper.getProfessionalActivitySet(activities, ScientistProfileHelper.PROFESSIONAL_ACTIVITY_TYPE_EVENT_PARTICIPATION); %>
				<% Set<ProfessionalActivity> setEAdministrations = helper.getProfessionalActivitySet(activities, ScientistProfileHelper.PROFESSIONAL_ACTIVITY_TYPE_EVENT_ADMINISTRATION); %>
				<% StringBuffer eventsAdministrationStringBuffer = new StringBuffer(); %>
				<% StringBuffer eventsWorkshopStringBuffer = new StringBuffer(); %>
				<% StringBuffer otherEventStringBuffer = new StringBuffer(); %>

				<%-- Events / Organisation --%>
					<% if (!setEAdministrations.isEmpty()) { %>
						<% for (final ProfessionalActivity activity: setEAdministrations) { %>
							<% eventsAdministrationStringBuffer.append(activity.getHTMLContent(helper.getLastName() + " " + helper.getInitials())); %>
						<% } %>
					<% } %>
				
				<%-- Events / Conference Attendance && Workshop --%>
				<% if (!setEParticipations.isEmpty()) { %>
				
					<%-- Events / Conference Attendance --%>
						<% for (final ProfessionalActivity activity: setEParticipations) { %>
							<%
								String[] parameters = {
									ScientistProfileHelper.PROFESSIONAL_ACTIVITY_PARAMETER_CONFERENCE,
									ScientistProfileHelper.PROFESSIONAL_ACTIVITY_PARAMETER_MEETING,
									ScientistProfileHelper.PROFESSIONAL_ACTIVITY_PARAMETER_COLLOQUIUM,
									ScientistProfileHelper.PROFESSIONAL_ACTIVITY_PARAMETER_CONGRESS,
									ScientistProfileHelper.PROFESSIONAL_ACTIVITY_PARAMETER_CONVENTION,
									ScientistProfileHelper.PROFESSIONAL_ACTIVITY_PARAMETER_SYMPOSIUM
								};
								otherEventStringBuffer.append(activity.getFilteredHTMLContent(helper.getLastName() + " " + helper.getInitials(), parameters));
							%>
						<% } %>
					
					<%-- Events / Workshop --%>
						<% for (final ProfessionalActivity activity: setEParticipations) { %>
							<%
								String[] parameters = {
									ScientistProfileHelper.PROFESSIONAL_ACTIVITY_PARAMETER_WORKSHOP
								};
								eventsWorkshopStringBuffer.append(activity.getFilteredHTMLContent(helper.getLastName() + " " + helper.getInitials(), parameters));
							%>
						<% } %>
					
					<%-- Displaying Events --%>
						<% if (eventsWorkshopStringBuffer.length() > 0 || otherEventStringBuffer.length() > 0 || eventsAdministrationStringBuffer.length() > 0) { %>
							<h2>Events</h2>
							<% if (otherEventStringBuffer.length() > 0) { %>
								<h3>Conference Attendance</h3>
								<%= otherEventStringBuffer %>
							<% } %>
							<% if (eventsWorkshopStringBuffer.length() > 0) { %>
								<h3>Workshop Attendance</h3>
								<%= eventsWorkshopStringBuffer %>
							<% } %>
							<% if (eventsAdministrationStringBuffer.length() > 0) { %>
								<h3>Organisation</h3>
								<%= eventsAdministrationStringBuffer %>
							<% } %>
											
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