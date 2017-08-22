<%@include file="/apps/nhmwww/components/global.jsp" %>
<%@page session="false" 
		import="com.day.cq.wcm.api.WCMMode,
				java.util.Calendar,
				uk.ac.nhm.nhm_www.core.componentHelpers.ArticleHelper"%>

<%
	final ArticleHelper helper = new ArticleHelper(resource, request, xssAPI);

	if (!helper.isConfigured()) {
%>
	<div class="row">
		<span class="noconfigured">Please configure the article page correctly.</span>
	</div>
<%
		return;
	}

	if (helper.isImageHeadType()) {
		if (helper.isImageConfigured()) {
%>
	<div class="row">
		<div class="discover-image small-12 medium-12 large-12 columns">
<%
  			WCMMode beforeMode = WCMMode.fromRequest(slingRequest);
  			WCMMode.PREVIEW.toRequest(slingRequest);
%>
	<cq:include path="image" resourceType="nhmwww/components/functional/foundation5image" />

<%
			beforeMode.toRequest(slingRequest);
			if (helper.hasImageCaption()) {
%>
			<div class="discover-image-caption-icon small-1 medium-1 large-1">
				<a>
					<i class="ico svg-ico" data-svg-src="/etc/designs/nhmwww/img/svg-icons/icon_l_general_info.svg" data-svg-title="icon__general_info" data-stroke-width="4" data-base-color="#FFFFFF"></i>
				</a>
			</div>
			<div class="discover-image-caption" style="display: none;">
				<div class="close-icon">
					<a>
						<i class="ico svg-ico" data-svg-src="/etc/designs/nhmwww/img/svg-icons/icon_l_general_close.svg" data-svg-title="icon__general_close" data-stroke-width="4" data-base-color="#FFFFFF"></i>
					</a>
				</div>
				<%= xssAPI.filterHTML(helper.getImageCaption()) %>
			</div>
<%
			}
%>
		</div>
	</div>
<%
		}
	}
	
	if (helper.isVideoHeadType()) {
		final String videoId = helper.getVideo();
%>
	<div class="row">
		<div class="discover-video small-12 medium-12 large-12 columns">
			<div class="video-wrapper" data-nhm-videoid="<%= videoId %>">
				<div id="<%= videoId %>" class="youtubeplayer"></div>
			</div>
		</div>
	</div>
<% 
	}
%> 
	<div class="section row2cells21">
		<div class="row">
			<div class="small-12 medium-8 large-8 columns">
				<h1><%= xssAPI.filterHTML(helper.getTitle()) %></h1>
				<div class="discover-publication-date">By <%=helper.getAuthor()%> <%=helper.getDate()%></div>
				<div class="introduction"><%= xssAPI.filterHTML(helper.getIntroduction()) %></div>
				<cq:include path="par" resourceType="foundation/components/parsys"/>
				<div class="discover-publication-footer">
					<div class="flags small-12 medium-12 large-12 columns">
						<div class="facebook small-12 medium-3 large-3 columns">
							<cq:includeClientLib categories="cq.social.plugins.facebook"/>
							<div id="fb-root"></div>
							<script src="//connect.facebook.net/en_US/all.js"></script>
		
							<script type="text/javascript">
			    				$(document).ready(function(){
				        			FB.Event.subscribe('xfbml.render',function(){
				            			$('.FB_Loader').css('background','url()');
				        			});
			    				});
							</script>
							<div class="fb-like" data-layout="button_count" data-action="like" data-show-faces="false" data-share="true"></div>
						</div>
						<div class="rest small-12 medium-9 large-9 columns"></div>
					</div>
				</div>
			</div>
			<div class="small-12 medium-4 large-4 columns">
				<cq:include path="ctapar" resourceType="foundation/components/parsys" />
			</div>
		</div>
	</div>