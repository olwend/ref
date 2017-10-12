<%@include file="/apps/nhmwww/components/global.jsp" %>
<%@page session="false" 
		import="com.day.cq.wcm.api.WCMMode,
				java.util.Calendar,
				java.util.List,
				java.util.Map,
				uk.ac.nhm.nhm_www.core.componentHelpers.ArticleHelper"%>
<cq:includeClientLib categories="nhmwww.article"/>
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
			<div class="articles--image-caption">
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
			<div class="small-12 medium-8 large-8 columns articles--container">
				
				<%if(helper.getHubTagName() != null) { %>
					<div class="articles--tags-header"><%= helper.getHubTagName()%></div>
				<%} %>

				<h1 class="articles--title-header"><%= xssAPI.filterHTML(helper.getTitle()) %></h1>
				
				<div class="articles--header-container">
					<div class="articles--meta-container">
						<%if(helper.getAuthor() != null) { %>
							<div class="articles--meta-author">By <%=helper.getAuthor()%></div>
						<%} %> 
						
						<div class="articles--meta-publication-date">First published <%=helper.getPublishedDate()%></div>
						<%if(helper.getUpdatedDate() != null) { %>
						<div class="articles--meta-updated-date">Last updated <%=helper.getUpdatedDate()%></div>
						<%} %>
					</div>

					<div class="articles--social-share-header">
						<cq:include path="socialshare-header" resourceType="nhmwww/components/functional/socialshare"/>
					</div>
				</div>

				<div class="articles--introduction"><%= xssAPI.filterHTML(helper.getIntroduction()) %></div>
				
				<div class="articles--content">
					<cq:include path="par" resourceType="foundation/components/parsys"/>
				</div>

				<div class="discover-publication-footer">
					<div class="flags small-12 medium-12 large-12 columns">
						<div class="articles--social-share-footer">
							<cq:include path="socialshare-footer" resourceType="nhmwww/components/functional/socialshare"/>
						</div>
						<hr class="articles--hr-footer">
						<div class="articles--tags-footer">
							<ul>
							<% List<Map<String, String>> tagList = helper.getTagList();
							for(int i=0; i<tagList.size(); i++) {%>
                                <%if(!tagList.get(i).get("path").equals("")) {%>
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
			
			<div class="row">
				<div class="small-12 medium-12 large-12 columns">
					<cq:include path="socialsignup" resourceType="nhmwww/components/functional/social-signup"/>
				</div>
			</div>
			
			<div class="row">
				<div class="small-12 medium-12 large-12 columns">
					<cq:include path="relatedpar" resourceType="foundation/components/parsys"/>
				</div>
			</div>
			
		</div>
	</div>