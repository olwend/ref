<%@page import="uk.ac.nhm.nhm_www.core.componentHelpers.ScientistProfileHelper,
				uk.ac.nhm.nhm_www.core.model.science.Publication,
				java.util.Set,
				org.apache.commons.lang3.StringUtils"%>
<%@include file="/apps/nhmwww/components/global.jsp"%> 
<%
	final ScientistProfileHelper helper = new ScientistProfileHelper(resource);

	final Set<Publication> publications = helper.getPublications();
%>
<% if (publications != null && !publications.isEmpty()) { %>
	<!-- Accordion -->	
		<div class="large-12 medium-12 columns ">
		    <dl class="accordion" data-accordion>	
		        <dd class="accordion-navigation">
		            <a href="#professionalactivities"><h2>Professional Activities </h2><span class="fa accordion-icon"></span></a>
		            <div id="professionalactivities" class="content">
						<% for (final Publication publication:publications) { %>
							<p><%= publication.getHTMLContent(helper.getLastName() + " " + helper.getInitials(), false) %></p>
						<% } %>
					</div>
		        </dd>
		    </dl>        
		</div>
	<!-- end Accordion-->
<% } %>