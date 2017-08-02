<%@include file="/apps/nhmwww/components/global.jsp" %>
<%@page session="false" 
		import="com.day.cq.wcm.api.WCMMode,
				java.util.Calendar,
				uk.ac.nhm.nhm_www.core.componentHelpers.DiscoverPublicationHelper,
				uk.ac.nhm.nhm_www.core.services.DiscoverPublicationService,
				uk.ac.nhm.nhm_www.core.services.DiscoverPublicationsSearchService"%>

<%
	final DiscoverPublicationService componentService = sling.getService(DiscoverPublicationService.class);

	componentService.creationDate(resource);
	componentService.orderTags(resource);

	final DiscoverPublicationHelper helper = new DiscoverPublicationHelper(resource, request, xssAPI);
	
	final DiscoverPublicationsSearchService searchService = sling.getService(DiscoverPublicationsSearchService.class);
	final String[] tags = helper.getTags();
	if (tags != null && tags.length > 0) {
		if (!tags[0].equals(searchService.getCqTags())) {
			searchService.setCqTags(tags[0]);
		}
	}

	if (!helper.isConfigured()) {
%>
	<div class="row">
		<span class="noconfigured">Discover Publication NOT configured properly.</span>
	</div>
<%
		return;
	}

	if (helper.isImageHeadType()) {
		if (helper.isImageConfigured()) {
%>
	<div class="row">
		<!-- Removing due to design difficulties 
		<div class="leftarrow medium-1 large-1 columns" data-path="<%= resource.getPath() %>">
			<div class="desktop tablet" data-equalizer-watch>
				<a>
					<i class="ico svg-ico arrowl" data-svg-src="/etc/designs/nhmwww/img/svg-icons/icon_l_general_arrow_l.svg" data-svg-title="icon__arrow" data-stroke-width="4" data-base-color="#808080"></i>
				</a>
			</div>
		</div> -->
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
		<!-- Removing due to design difficulties
		<div class="rightarrow medium-1 large-1 columns" data-path="<%= resource.getPath() %>">
			<div class="desktop tablet" data-equalizer-watch>
				<a>
					<i class="ico svg-ico arrowl" data-svg-src="/etc/designs/nhmwww/img/svg-icons/icon_l_general_arrow_r.svg" data-svg-title="icon__arrow" data-stroke-width="4" data-base-color="#808080"></i>
				</a>
			</div>
		</div> -->
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
				<!-- <iframe width="960" height="600" src="https://www.youtube.com/embed/<%= videoId%>" frameborder="0" allowfullscreen></iframe> -->
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
			<div class="introduction"><%= xssAPI.filterHTML(helper.getIntroduction()) %></div>
			<cq:include path="par" resourceType="foundation/components/parsys"/>
		</div>
		<div class="small-12 medium-4 large-4 columns">
			<cq:include path="ctapar" resourceType="foundation/components/parsys" />
		</div>
	</div>
	</div>
	
	<div class="row">
		<div class="small-12 medium-12 large-12 columns">
			<cq:include path="relatedposts" resourceType="nhmwww/components/functional/discoverrelated"/>
		</div>
	</div>