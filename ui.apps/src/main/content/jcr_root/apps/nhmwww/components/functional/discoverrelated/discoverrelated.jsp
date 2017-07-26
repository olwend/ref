<%@include file="/apps/nhmwww/components/global.jsp" %>
<%@page session="false" 
		import="com.day.cq.wcm.api.Page,
				com.day.cq.wcm.api.WCMMode,
				java.text.DateFormat,
				java.util.Locale,
				org.apache.sling.api.resource.Resource,
				uk.ac.nhm.nhm_www.core.componentHelpers.DiscoverPublicationHelper"%>
<cq:includeClientLib categories="nhm-www.discoverpublication" />				
<%
	final String[] posts = properties.get("posts", String[].class);
	final String title = properties.get("title", "Related posts");
	if (posts != null) {
%>
<div class="discover">
	<h3><%= title %></h3>
	
	<div class="related-posts">
<%
		for (final String post : posts) {
			final Page postPage = pageManager.getPage(post);
			if (page == null) {
				continue;
			}
			
			final Resource postResource = postPage.getContentResource("discoverpublication");
			
			if (postResource == null) {
				continue;
			}
			
			final DiscoverPublicationHelper helper = new DiscoverPublicationHelper(postResource, request, xssAPI);
			
			if (!helper.isConfigured()) {
				continue;
			}
	
			if (helper.isImageHeadType() && !helper.isImageConfigured() ||
					helper.isVideoHeadType() && helper.getVideo() == null) {
				continue;
			}
			
			final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.ENGLISH);
%>
		<div class="discover-element large-3 medium-6 small-12 <%= helper.getType() %>">
			<div class="discover-element-wrapper">
				<a href="<%= postPage.getPath() %>.html">
<%
			if (helper.isImageHeadType() && helper.isImageConfigured()) {
				WCMMode beforeMode = WCMMode.fromRequest(slingRequest);
	  			WCMMode.PREVIEW.toRequest(slingRequest);
%>
				<cq:include path="<%= postResource.getPath() + "/image" %>" resourceType="nhmwww/components/functional/discoverrelatedimage" />
<%
				beforeMode.toRequest(slingRequest);
			} else {
%>
					<img src="http://img.youtube.com/vi/<%= helper.getVideo() %>/0.jpg" alt="<%=helper.getTitle() %>">
<%
			}
%>
				</a>
				<div class="row image-footer">
					<div class="columns small-2">
						<p class="discover-element-tag"><%= helper.getType() %></p>
					</div>
					<div class="columns small-2">
						<% if(helper.getType().equals("news")) { %>
						<p class="discover-element-date"><%= dateFormat.format(helper.getCreationDate()) %></p>
						<% } %>
					</div>
				</div>
				<div class="discover-element-text">
					<a class="element-title" href="<%= postPage.getPath() %>.html">
						<%= xssAPI.filterHTML(helper.getTitle()) %>
					</a>
					<p class="element-text">
						<%= xssAPI.filterHTML(helper.getSnippet()) %>
					</p>
				</div>
			</div>
		</div>
<%			
			
		}
%>
	</div>
	</div>
<%
	}
%>
<cq:includeClientLib css="nhm-www.discover" />
<cq:includeClientLib categories="nhm-www.discover.related"/>