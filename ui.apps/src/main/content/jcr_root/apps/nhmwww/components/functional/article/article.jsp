<%@include file="/apps/nhmwww/components/global.jsp" %>
<%@page session="false"
		import="com.day.cq.wcm.api.WCMMode,
				java.util.Calendar,
				java.util.List,
				java.util.Map,
				uk.ac.nhm.core.componentHelpers.ArticleHelper"%>
<cq:includeClientLib categories="nhmwww.article"/>
<%
	final ArticleHelper helper = new ArticleHelper(resource, request, xssAPI, slingRequest);

	if (!helper.isConfigured()) {
%>
	<div class="row">
		<h4>Article</h4>
		Required fields:
		<ul>
			<li>Article title</li>
			<li>Introduction text</li>
			<li>Snippet</li>
			<li>Lead image or video</li>
		</ul>
	</div>
<%
		return;
	}

	if (WCMMode.fromRequest(request) == WCMMode.EDIT) { %>
	<div class="row">
		<h4>Article</h4>
		Edit the article dialog here
	</div>
<% }
	
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
			<div class="article--image-caption">
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
			<div class="small-12 medium-8 large-8 columns article--container">

				<%if(helper.getHubTag() != null) { 
					Map<String, String> hubTag = helper.getHubTag();
					if(hubTag.containsKey("path") && hubTag.get("path") != null && !hubTag.get("path").equals("")) {%>
						<div class="article--tags-header"><a href="<%=hubTag.get("path")%>"><%= hubTag.get("title")%></a></div>
					<% } else { %>
						<div class="article--tags-header"><%= hubTag.get("title")%></div>
				<%	} 
				} %>

				<h1 class="article--title-header"><%= xssAPI.filterHTML(helper.getTitle()) %></h1>

				<div class="article--header-container">
					<div class="article--meta-container">
						<%if(helper.getAuthor() != null) { %>
							<div class="article--meta-author">By <%=helper.getAuthor()%></div>
						<%} %>

						<div class="article--meta-publication-date">First published <%=helper.getPublishedDate()%></div>
						<%if(helper.getUpdatedDate() != null && !helper.getUpdatedDate().equals(helper.getPublishedDate())) { %>
						<div class="article--meta-updated-date">Last updated <%=helper.getUpdatedDate()%></div>
						<%} %>
					</div>

					<div class="article--social-share-header">
						<cq:include path="socialshareheader" resourceType="nhmwww/components/functional/socialshare"/>
					</div>
				</div>

				<div class="article--introduction"><%= xssAPI.filterHTML(helper.getIntroduction()) %></div>

				<div class="article--content">
					<cq:include path="par" resourceType="foundation/components/parsys"/>
				</div>

				<div class="discover-publication-footer">
					<div class="flags small-12 medium-12 large-12 columns">
						<div class="article--social-share-footer">
							<cq:include path="socialsharefooter" resourceType="nhmwww/components/functional/socialshare"/>
						</div>
						<hr class="article--hr-footer">
						<div class="article--tags-footer">
							<ul>
							<% List<Map<String, String>> tagList = helper.getTagList();
							for(int i=0; i<tagList.size(); i++) {%>
                                <%if(tagList.get(i).containsKey("path") && tagList.get(i).get("path") != null && !tagList.get(i).get("path").equals("")) {%>
                                	<li><a href="<%=tagList.get(i).get("path") %>"><%=tagList.get(i).get("title") %></a></li>
                                <%} else {%>
									<li><%=tagList.get(i).get("title") %></li>
                                <%}%>
							<%} %>
							</ul>
						</div>
					</div>
				</div>
			</div>

			<div class="small-12 medium-4 large-4 columns">
				<cq:include path="ctapar" resourceType="foundation/components/parsys" />
			</div>

			<!-- <div class="row"> -->
				<div class="small-12 medium-12 large-12 columns">
					<cq:include path="socialsignup" resourceType="nhmwww/components/functional/social-signup"/>
				</div>
			<!-- </div> -->

			<!-- <div class="row"> -->
				<div class="small-12 medium-12 large-12 columns">
					<cq:include path="relatedpar" resourceType="foundation/components/parsys"/>
				</div>
			<!-- </div> -->

		</div>
	</div>