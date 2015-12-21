<%@page import="java.util.Iterator"%>
<%@page import="uk.ac.nhm.nhm_www.core.componentHelpers.ScientistProfileHelper,
				uk.ac.nhm.nhm_www.core.model.science.Publication,
				uk.ac.nhm.nhm_www.core.impl.science.publications.PublicationSorter,
				java.util.Set,
				java.util.SortedSet,
				java.util.TreeSet,
				java.util.Collections,
				org.apache.commons.lang3.StringUtils"%>
<%@include file="/apps/nhmwww/components/global.jsp"%> 
<%
	final ScientistProfileHelper helper = new ScientistProfileHelper(resource);

	final Set<Publication> publications = helper.getPublications();
	final Set<Publication> contributedPublications = helper.getContributedPublications();
	
	/**SortedSet<Publication> sortedPublications = new TreeSet<Publication>(new PublicationSorter());
	
	if(!contributedPublications.isEmpty()) {
		Iterator<Publication> iterator = contributedPublications.iterator();
		while(iterator.hasNext()) {
			Publication p = iterator.next();
			publications.add(p);
		}
	}
	
	sortedPublications.addAll(publications);*/
%>
<div class="small-12 medium-8 large-8 columns">
	<% if (publications != null && !publications.isEmpty()) { %>	
		<div id="publications" class="content science-profiles-detail-page--tabs-content-container">
			<h3>Publications</h3>
			<% for (final Publication publication:publications) { %>
			<%-- for (final Publication publication:sortedPublications) { --%>
			<p><%= publication.getHTMLContent(helper.getLastName() + " " + helper.getInitials(), false) %></p>
			<% } %>
		</div>
	<% } %>
	<% if (contributedPublications != null && !contributedPublications.isEmpty()) { %>	
		<div id="publications" class="content science-profiles-detail-page--tabs-content-container">
			<% for (final Publication contributedPublication:contributedPublications) { %>
			<p><%= contributedPublication.getHTMLContent(helper.getLastName() + " " + helper.getInitials(), false) %></p>
			<% } %>
		</div>
	<% } %>
</div>