<%@page session="false"%>
<%@include file="/apps/nhmwww/components/global.jsp"%>
<%@page import="uk.ac.nhm.nhm_www.core.componentHelpers.*"%>

<%
	BigSplashHelper helper = new BigSplashHelper(slingRequest, currentPage, properties);
%>

	<!-- Basic tab -->

<div class="hero-video-wrapper" data-nhm-videoid="<%= helper.getVideoId() %>">
	<div id="<%= helper.getVideoId() %>" class="youtubeplayer"></div> 
</div>

<div class="hero-video-image">
	<img src="<%= helper.getImagePath() %>" alt="<%= helper.getImageAlt() %>">
</div>


<div class="hero-video-text">
	<div class="row cf">
		<div class="hero-video-text-title">
			<h1><%= helper.getTitle() %></h1>
		</div>

		<% if(helper.getSubtitle()!=null) {%>
			<!-- <div class="hero-video-text-subtitle">
				<span><%= helper.getSubtitle() %></span>
			</div> -->
		<%} %>

		<% if(helper.getCaption()!=null) {%>
			<div class="hero-video-text-caption">
				<span><%= helper.getCaption() %></span>
			</div>
		<%} %>

		<% if(helper.getCtaUrl()!=null && helper.getCtaText()!=null) {%>
			<!-- CTA tab -->
			<div class="hero-video-text-cta">
				<a href="<%= helper.getCtaUrl() %>"><%= helper.getCtaText() %></a>
			</div>
		<%} %>
	</div>
</div>