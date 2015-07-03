<%@page import="uk.ac.nhm.nhm_www.core.componentHelpers.ScientistProfileHelper,
				uk.ac.nhm.nhm_www.core.model.science.Publication,
				java.util.Set,
				org.apache.commons.lang3.StringUtils"%>
<%@include file="/apps/nhmwww/components/global.jsp"%> 
<%
	final ScientistProfileHelper helper = new ScientistProfileHelper(resource);

	final Set<Publication> publications = helper.getPublications();
%>
<div class="small-12 medium-8 large-8 columns">
	<% if (publications != null && !publications.isEmpty()) { %>
		<div id="publications" class="content science-profiles-detail-page--tabs-content-container">
			<% for (final Publication publication:publications) { %>
			<p><%= publication.getHTMLContent(helper.getLastName() + " " + helper.getInitials(), false) %></p>
			<% } %>
		</div>
	<% } %>
</div>