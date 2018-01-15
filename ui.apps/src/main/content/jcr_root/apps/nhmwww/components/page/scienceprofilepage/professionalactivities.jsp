<%@page import="uk.ac.nhm.core.model.science.proactivities.ProfessionalActivity,
				uk.ac.nhm.core.componentHelpers.ScientistProfileHelper,
				uk.ac.nhm.core.model.science.Publication,
				java.util.Set,
				java.util.Map,
				org.apache.commons.lang3.StringUtils"%>
<%@include file="/apps/nhmwww/components/global.jsp"%> 
<%
	final ScientistProfileHelper helper = new ScientistProfileHelper(resource);
	final Map<String, Set<ProfessionalActivity>> activities = helper.getProfessionalActivities();
%>
<div class="small-12 medium-8 large-8 columns">
<% if (activities != null && !activities.isEmpty()) { %>
	<div id="professionalactivities" class="content science-profiles-detail-page--tabs-content-container">
	
		<%-- External Positions --%>
		<%= helper.getIorEPositions(activities, true) %>
		
		<%-- Fellowships --%>
		<%= helper.getFellowships(activities) %>
			
		<%-- Committees --%>
		<%= helper.getCommittees(activities) %>
			
		<%-- Memberships --%>
		<%= helper.getMemberships(activities) %>
			
		<%-- Editorships --%>
		<%= helper.getEditorships(activities) %>
		
		<%-- Reviewer / Referee --%>
		<%= helper.getReviewsRefereed(activities) %>

		<%-- Events --%>
		<%= helper.getEvents(activities) %>
		
		<%-- Internal Positions --%>
		<%= helper.getIorEPositions(activities, false) %>
	</div>
<% } %>
</div>