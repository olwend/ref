<%@page session="false"%>
<%@include file="/apps/nhmwww/components/global.jsp"%>
<%@page import="uk.ac.nhm.nhm_www.core.componentHelpers.*"%>

<%
	BigSplashHelper helper = new BigSplashHelper(slingRequest, currentPage, properties);
%>

	<!-- Basic tab -->

<div class="bigsplash-video js--bigsplash-video" data-nhm-videoid="<%= helper.getVideoId() %>">
	<div id="<%= helper.getVideoId() %>" class="youtubeplayer"></div>
</div>

<div class="bigsplash-image" style="background-image: url(<%= helper.getImagePath() %>);"></div>
<div class="bigsplash-text">
	<div class="row cf">
		<div class="bigsplash-text--title-container">
			<% if(helper.getapplyLinkToTitle()) {%>
				<a href="<%= helper.getCtaUrl() %>">
			<%} %>
				<h1 class="bigsplash-text--title"><%= helper.getTitle() %></h1>
			<% if(helper.getapplyLinkToTitle()) {%>
				</a>
			<%} %>
		</div>
		<% if(helper.getSubtitle()!=null) {%>
			<div class="bigsplash-text--subtitle-container">
			<% if(helper.getapplyLinkToTitle()) {%>
				<a href="<%= helper.getCtaUrl() %>">
			<%} %>
				<p class="bigsplash-text--subtitle"><%= helper.getSubtitle() %></p>
			<% if(helper.getapplyLinkToTitle()) {%>
				</a>
			<%} %>
			</div>
		<%} %>

		<% if(helper.getCtaUrl()!=null && helper.getCtaText()!=null) {%>
			<!-- CTA tab -->
			<div class="bigsplash-text--cta-container">
				<a href="<%= helper.getCtaUrl() %>" class="bigsplash-text--cta"><%= helper.getCtaText() %></a>
			</div>
		<%} %>

	<div class="bigsplash-video--controls">
		<i class="ico svg-ico bigsplash-video--controls-pause js--bigsplash-video--controls-pause" data-svg-src="/etc/designs/nhmwww/img/svg-icons/icon_l_bigsplash_pause.svg" data-svg-title="icon__bigsplash-pause" data-alt="Pause" data-base-color="#FFFFFF" data-stroke-width="0" data-fallback=""></i>
		<i class="ico svg-ico bigsplash-video--controls-play js--bigsplash-video--controls-play" data-svg-src="/etc/designs/nhmwww/img/svg-icons/icon_l_bigsplash_play.svg" data-svg-title="icon__bigsplash-play" data-alt="Play" data-base-color="#FFFFFF" data-stroke-width="0" data-fallback=""></i>
	</div>

	</div>
</div>