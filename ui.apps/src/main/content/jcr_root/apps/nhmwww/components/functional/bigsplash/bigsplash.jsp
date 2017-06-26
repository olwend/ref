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
<div class="hero-video-wrapper-controls">
		<i class="ico svg-ico hero-video-wrapper-controls-pause" data-svg-src="/etc/designs/nhmwww/img/svg-icons/icon_l_bigsplash_pause.svg" data-svg-title="icon__bigsplash-pause" data-alt="Pause" data-base-color="#FFFFFF" data-stroke-width="0" data-fallback=""></i>
		<i class="ico svg-ico hero-video-wrapper-controls-play" data-svg-src="/etc/designs/nhmwww/img/svg-icons/icon_l_bigsplash_play.svg" data-svg-title="icon__bigsplash-play" data-alt="Play" data-base-color="#FFFFFF" data-stroke-width="0" data-fallback=""></i>
	</div>