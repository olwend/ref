<%@page import="uk.ac.nhm.nhm_www.core.model.science.ProfessionalActivity,
				uk.ac.nhm.nhm_www.core.componentHelpers.ScientistProfileHelper,
				uk.ac.nhm.nhm_www.core.model.science.Publication,
				java.util.Set,
				org.apache.commons.lang3.StringUtils"%>
<%@include file="/apps/nhmwww/components/global.jsp"%> 
<%
	final ScientistProfileHelper helper = new ScientistProfileHelper(resource);

	final Set<ProfessionalActivity> activities = helper.getProfessionalActivities();
%>
<% if (activities != null && !activities.isEmpty()) { %>
	<!-- Accordion -->	
		<div class="large-12 medium-12 columns ">
		    <dl class="accordion" data-accordion>	
		        <dd class="accordion-navigation">
		            <a href="#professionalactivities"><h2>Professional Activities </h2><span class="fa accordion-icon"></span></a>
		            <div id="professionalactivities" class="content">
						<% for (final ProfessionalActivity activity: activities) { %>
							<p><%= activity.getHTMLContent(helper.getLastName() + " " + helper.getInitials()) %></p>
						<% } %>
					</div>
		        </dd>
		    </dl>        
		</div>
	<!-- end Accordion-->
<% } %>