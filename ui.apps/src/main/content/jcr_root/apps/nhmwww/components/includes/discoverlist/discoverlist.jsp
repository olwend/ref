<%@ page session="false"
	import="java.util.List,
			uk.ac.nhm.nhm_www.core.componentHelpers.DiscoverListHelper,
			uk.ac.nhm.nhm_www.core.model.discover.*,
			uk.ac.nhm.nhm_www.core.services.DiscoverPublicationsSearchService"%>
<%@include file="/apps/nhmwww/components/global.jsp"%>
<%
	final DiscoverListHelper helper = new DiscoverListHelper(resource);

	if (helper.hasTags()) {
		final DiscoverPublicationsSearchService searchService = sling.getService(DiscoverPublicationsSearchService.class);
		final String[] tags = helper.getTags();
		if (!tags[0].equals(searchService.getCqTags())) {
			searchService.setCqTags(tags[0]);
		}
	}
%>
<%
	if (helper.hasTitle()) {
		final boolean hasLink = helper.hasTitleLink();
%>

<cq:include path="title" resourceType="nhmwww/components/functional/title" />
<%--<div class="row discover-header">
<%
		if (hasLink) {
%>
	<a href="<%= helper.getTitleLink() %>">
<%
		}
%>
		<h1 class="discover-color discover-title"><%= helper.getTitle() %></h1>
<%
		if (hasLink) {
%>
	</a>
<%
		}
%>
</div>--%>
<%
	}
%>

<div id="main_row" class="row main-row"></div>

<div id="more_results" class="row more-results">
	<a>
		<h5 id="more_results_text" class="more-results-text">More results</h5>
	</a>
</div>



