<%@include file="/apps/nhmwww/components/global.jsp" %>
<%@page session="false" 
		import="com.day.cq.wcm.api.WCMMode,
				java.util.Calendar,
				uk.ac.nhm.nhm_www.core.componentHelpers.ArticleHelper"%>

<%
	final ArticleHelper helper = new ArticleHelper(resource, request, xssAPI, slingRequest);

	if (!helper.isConfigured()) {
%>
	<div class="row">
		<span class="noconfigured">
			Please configure the article page correctly. Required fields:
			<ul>
				<li>Article title</li>
				<li>Introduction text</li>
				<li>Lead image or video</li>
			</ul>
		</span>
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
				<div><%= helper.getHubTagName()%></div>
				
				<h1><%= xssAPI.filterHTML(helper.getTitle()) %></h1>
				
				<%if(helper.getAuthor() != null) { %>
					<div>By <%=helper.getAuthor()%></div>
				<%} %> 
				
				<div class="discover-publication-date"><%=helper.getDate()%></div>
				
				<div class="introduction"><%= xssAPI.filterHTML(helper.getIntroduction()) %></div>
				
				<cq:include path="par" resourceType="foundation/components/parsys"/>
				
				
				
				<div class="discover-publication-footer">
					<div class="flags small-12 medium-12 large-12 columns">
						<div>
							<p>This is where the bottom stuff will go<p>
							<ul>
								<li>More share</li>
								<li>Tags</li>
							</ul>
						</div>
					</div>
				</div>
			</div>
			
			<div class="small-12 medium-4 large-4 columns">
				<cq:include path="ctapar" resourceType="foundation/components/parsys" />
			</div>
			
			<div class="row">
				<div class="small-12 medium-12 large-12 columns">
					<cq:include path="socialsignup" resourceType="nhmwww/components/functional/social-signup"/>
				</div>
			</div>
			
			<div class="row">
				<div class="small-12 medium-12 large-12 columns">
					Include Article Search component
				</div>
			</div>
			
		</div>
	</div>