<%@page import="uk.ac.nhm.core.model.science.grants.Grant,
				uk.ac.nhm.core.model.science.projects.ProjectTemplate,
				uk.ac.nhm.core.model.science.proactivities.ProfessionalActivity,
				uk.ac.nhm.core.componentHelpers.ScientistProfileHelper,
				uk.ac.nhm.core.model.science.Publication,
				java.util.Set,
				java.util.Map,
				org.apache.commons.lang3.StringUtils"%>
<%@include file="/apps/nhmwww/components/global.jsp"%> 
<%
	final ScientistProfileHelper helper = new ScientistProfileHelper(resource);
	final Map<String, Set<ProfessionalActivity>> activities = helper.getProfessionalActivities();
	final Map<String, Set<ProjectTemplate>> projects = helper.getProjects();
	final Map<String, Set<Grant>> grants = helper.getGrants();
%>
<div class="small-12 medium-8 large-8 columns">

<% if (activities != null && !activities.isEmpty()) { %>
	<div id="projects" class="content science-profiles-detail-page--tabs-content-container">
	
		<%-- Grants --%>
		<%= helper.getGrants(grants) %>
	
		<%-- Projects --%>
		<%= helper.getProjects(projects) %>
	
		<%-- Consultancies --%>
		<%= helper.getConsultancies(activities) %>
		
		<%-- Partnerships --%>
		<%= helper.getPartnerships(activities) %>
		
		<%-- Fieldworks --%>
		<%= helper.getFieldworks(activities) %>
			
	</div>
<% } %>
</div>