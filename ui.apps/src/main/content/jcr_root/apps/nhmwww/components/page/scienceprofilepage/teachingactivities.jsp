<%@page import="uk.ac.nhm.nhm_www.core.model.science.teaching.TeachingActivityTemplate,
				uk.ac.nhm.nhm_www.core.componentHelpers.ScientistProfileHelper,
				uk.ac.nhm.nhm_www.core.model.science.Publication,
				java.util.Set,
				java.util.Map,
				org.apache.commons.lang3.StringUtils"%>
<%@include file="/apps/nhmwww/components/global.jsp"%> 
<%
	final ScientistProfileHelper helper = new ScientistProfileHelper(resource);
	final Map<String, Set<TeachingActivityTemplate>> activities = helper.getTeachingActivities();
%>
<div class="small-12 medium-8 large-8 columns">
<% if (activities != null && !activities.isEmpty()) { %>
	<div id="teachingactivities" class="content science-profiles-detail-page--tabs-content-container">
	
		<%-- Taught Courses --%>
		<%= helper.getTaughtCourses(activities) %>
			
		<%-- Supervisions --%>
		<%= helper.getSupervisions(activities) %>
		
		<%-- Examiner --%>
		<%= helper.getExaminer(activities) %>
		
		<%-- Program Developed --%>
		<%= helper.getProgramDeveloped(activities) %>
		
		<%-- Courses developed --%>
		<%= helper.getCourseDeveloped(activities) %>
		
	</div>
<% } %>
</div>