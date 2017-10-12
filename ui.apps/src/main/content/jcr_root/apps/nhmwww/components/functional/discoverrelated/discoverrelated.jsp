<%@include file="/apps/nhmwww/components/global.jsp" %>
<%@page session="false"
		import="com.day.cq.wcm.api.Page,
				com.day.cq.wcm.api.WCMMode,
				java.text.DateFormat,
				java.util.Locale,
				org.apache.sling.api.resource.Resource,
				uk.ac.nhm.nhm_www.core.componentHelpers.ArticleHelper"%>
<cq:includeClientLib categories="nhm-www.discoverpublication" />
<%
	final String[] posts = properties.get("posts", String[].class);
	final String title = properties.get("title", "Related posts");
	if(posts != null) {
%>
<div class="discover">
	<h3><%= title %></h3>
	<div class="related-posts--container">
<%
		for (final String post : posts) {
			final Page postPage = pageManager.getPage(post);
			if (page == null) continue;

			Resource postResource = null;
			String template = postPage.getProperties().get("cq:template", String.class);

					if(template.equals("/apps/nhmwww/templates/articlepage")) {
							postResource = postPage.getContentResource("article");
					}
						if(template.equals("/apps/nhmwww/templates/discoverpublicationpage")) {
							postResource = postPage.getContentResource("discoverpublication");
					}
			if (postResource == null) continue;

			final ArticleHelper helper = new ArticleHelper(postResource, request, xssAPI, slingRequest);
			if (!helper.isConfigured()) continue;

			if (helper.isImageHeadType() && !helper.isImageConfigured() ||
					helper.isVideoHeadType() && helper.getVideo() == null) {
				continue;
			}

			final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.ENGLISH);
%>
		<!-- <div class="discover-element large-3 medium-6 small-12"> -->
		<div class="discover-element">
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
					<div class="discover-element--video-container">
						<img class="discover-element--video-icon" src="/etc/designs/nhmwww/img/png-icons/youtube_icon_thumbnail.png" alt="Video">
						<img class="discover-element--video-thumbnail" src="http://img.youtube.com/vi/<%= helper.getVideo() %>/mqdefault.jpg" alt="<%=helper.getTitle() %>">
					</div>
<%
			}
%>
				</a>

				<div class="discover-element-text">

					<%if(helper.getHubTagName() != null) { %>
						<div class="element-tag">
							<%= helper.getHubTagName() %>
						</div>
					<%} %>

					<div>
						<a class="element-title" href="<%= postPage.getPath() %>.html">
							<%= xssAPI.filterHTML(helper.getTitle()) %>
						</a>
					</div>

					<div class="element-date">
						<%= helper.getPublishedDate() %>
					</div>

					<div class="element-text">
						<%= xssAPI.filterHTML(helper.getSnippet()) %>
					</div>
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