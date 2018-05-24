<%@include file="/apps/nhmwww/components/global.jsp" %>
<%@page session="false"
		import="com.day.cq.wcm.api.Page,
				com.day.cq.wcm.api.WCMMode,
				java.text.DateFormat,
				java.util.Locale,
				java.util.Map,
				org.apache.sling.api.resource.Resource,
				uk.ac.nhm.core.componentHelpers.ArticleHelper"%>
<!-- <cq:includeClientLib categories="nhm-www.discoverpublication" /> -->
<%
	final String[] posts = properties.get("posts", String[].class);
	final String title = properties.get("title", "Related posts");
	final String showReadMore = properties.get("showreadmore", "false");
	String readMoreLink = properties.get("readmorelink", "");

	if(readMoreLink != null && !readMoreLink.contains(".html")) {
        readMoreLink = readMoreLink + ".html";
    }

if(posts == null) { %>
	<div class="row">
		<h4>Discover related</h4>
		Required fields:
		<ul>
			<li>Related posts</li>
		</ul>
	</div>
<% } else {
%>
<div class="discover">
	<h3 class="discover-related--title"><%= title %></h3>
	<ul class="related-posts--container small-block-grid-1 medium-block-grid-2 large-block-grid-4">
<%
		for (final String post : posts) {
			try {
			final Page postPage = pageManager.getPage(post);
			if (page == null) continue;

			Resource postResource = null;
			String template = postPage.getProperties().get("cq:template", String.class);

					if(template.equals("/apps/nhmwww/templates/articlepage")) {
						postResource = postPage.getContentResource("article");
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
		<li class="discover-element">
			<div class="discover-element-wrapper">
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

					<%if(helper.getHubTag() != null) {
						Map<String, String> hubTag = helper.getHubTag();%>
							<div class="element-tag"><%= hubTag.get("title")%></div>
					<%	} %>

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
		</li>
<%

			} catch (Exception e) {
				//Do something
			}


			}
%>
	</ul>
	<% if(showReadMore.equals("true")) { %>
		<div class="discover-related--read-more-link">
			<a href="<%=readMoreLink%>">Read more</a>
		</div>
	<%}%>
	</div>
<%
	}
%>

<!-- <cq:includeClientLib css="nhm-www.discover" /> -->
<!-- <cq:includeClientLib categories="nhm-www.discover.related"/> -->
