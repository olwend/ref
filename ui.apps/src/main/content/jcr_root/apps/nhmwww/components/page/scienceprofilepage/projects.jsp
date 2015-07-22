<%@page import="uk.ac.nhm.nhm_www.core.model.science.projects.Project"%>
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
	final Map<String, Set<Project>> projects = helper.getProjects();
	final boolean displayProjects = helper.displayProjects(resource);
	final boolean displayConsultancies = helper.displayConsultancies(resource);
	final boolean displayPartnerships = helper.displayPartnerships(resource);
	final boolean displayFieldworks = helper.displayFieldworks(resource);
%>
<div class="small-12 medium-8 large-8 columns">

<% if (activities != null && !activities.isEmpty()) { %>
	<div id="projects" class="content science-profiles-detail-page--tabs-content-container">
	
		<%-- Projects --%>
		<% if ( displayProjects ) { %>
			<h3>Other Projects</h3>
			<%= helper.getProjects(projects) %>
		<% } %>
	
		<%-- Consultancies --%>
		<% if ( displayConsultancies ) { %>
			<h3>Consultancy</h3>
			<%= helper.getConsultancies(activities) %>
		<% } %>
		
		<%-- Partnerships --%>
		<% if ( displayPartnerships ) { %>
			<h3>Partnership</h3>
			<%= helper.getPartnerships(activities) %>
		<% } %>
		
		<%-- Fieldworks --%>
		<% if ( displayFieldworks ) { %>
			<h3>Fieldwork</h3>
			<%= helper.getFieldworks(activities) %>
		<% } %>
			
	</div>
<% } %>
</div>