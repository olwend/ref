<%@page session="false"%>
<%@include file="/apps/nhmwww/components/global.jsp"%>
<%@page import="uk.ac.nhm.nhm_www.core.componentHelpers.*"%>

<%
	BigSplashHelper helper = new BigSplashHelper(slingRequest, currentPage, properties);
%>

<!-- Basic tab -->
<%= helper.getVideoId() %>

<%= helper.getImagePath() %>

<h1><%= helper.getTitle() %></h1>

<%= helper.getSubtitle() %>

<%= helper.getCaption() %>

<!-- CTA tab -->

<a href="<%= helper.getCtaUrl() %>"><%= helper.getCtaText() %></a>