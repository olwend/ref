<%--

  NewsList component.

--%>
<%@page session="false" 
	import="uk.ac.nhm.nhm_www.core.model.NewsFeedElement,
			uk.ac.nhm.nhm_www.core.componentHelpers.NewsListHelper, 
			uk.ac.nhm.nhm_www.core.services.NewsPublicationService"%>
<%@include file="/apps/nhmwww/components/global.jsp"%>
<%
	final NewsListHelper helper = new NewsListHelper(resource);

	if (helper.hasTags()) {
		final NewsPublicationService searchService = sling.getService(NewsPublicationService.class);
		final String[] tags = helper.getTags();
		if (!tags[0].equals(searchService.getCqTags())) {
			searchService.setCqTags(tags[0]);
		}
	}
%>