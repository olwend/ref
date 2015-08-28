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
<div class="small-12 medium-8 large-8 columns">
<% if (activities != null && !activities.isEmpty()) { %>
	<div id="professionalactivities" class="content science-profiles-detail-page--tabs-content-container">
	
		<%-- Awards --%>
		<%= helper.getAwards(activities) %>

		<%= helper.getResearchPresentation(activities) %>
		
		<%= helper.getGuestPresentation(activities) %>
		
		<%-- Public Engagement --%>
			<%-- Internal Activities --%>
			<%-- <%= helper.getIorEPositions(activities, false) --%>
			
			<%-- External Activities --%>
			<%-- <%= helper.getIorEPositions(activities, true) %> --%>
		
		<%= helper.getMedia(activities) %>
		
	</div>
<% } %>
</div>