<%@page import="java.util.Iterator"%>
<%@page import="uk.ac.nhm.nhm_www.core.componentHelpers.ScientistProfileHelper,
				uk.ac.nhm.nhm_www.core.model.science.Publication,
				java.util.Set,
				java.util.SortedSet,
				java.util.TreeSet,
				java.util.Collections,
				org.apache.commons.lang3.StringUtils"%>
<%@include file="/apps/nhmwww/components/global.jsp"%> 
<%
	final ScientistProfileHelper helper = new ScientistProfileHelper(resource);

	final Set<Publication> authoredPublications = helper.getPublications();
	final Set<Publication> contributedPublications = helper.getContributedPublications();
%>
<div class="small-12 medium-8 large-8 columns">
	<% if (authoredPublications != null && !authoredPublications.isEmpty()) { %>	
		<div id="publications" class="content science-profiles-detail-page--tabs-content-container">
			<h3>Publications</h3>
			<% for (final Publication publication:authoredPublications) { %>		
			<p><%= publication.getHTMLContent(helper.getLastName() + " " + helper.getInitials(), false) %></p>
			<% } %>
		</div>
	<% } %>
	<% if (contributedPublications != null && !contributedPublications.isEmpty()) { %>	
		<div id="publications" class="content science-profiles-detail-page--tabs-content-container">
			<h3>Contributed Publications</h3>
			<% for (final Publication publication:contributedPublications) { %>
			<p><%= publication.getHTMLContent(helper.getLastName() + " " + helper.getInitials(), false) %></p>
			<% } %>
		</div>
	<% } %>

</div>