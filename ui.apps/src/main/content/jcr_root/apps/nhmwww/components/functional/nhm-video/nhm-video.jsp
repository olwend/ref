<%@page session="false"%>
<%@include file="/apps/nhmwww/components/global.jsp"%>
<%@page import="uk.ac.nhm.core.componentHelpers.*"%>

<%
	NHMVideoHelper helper = new NHMVideoHelper(slingRequest, currentPage, properties);
%>

<% if(helper.getVideoId() == null) { %>
	<div class="row">
		<h4>Video</h4>
		Required fields:
		<ul>
			<li>YouTube video ID</li>
		</ul>
	</div>
<% } else { %>
	<div class="video-wrapper" data-nhm-videoid="<%= helper.getVideoId() %>">
		<div id="<%= helper.getVideoId()%>" class="youtubeplayer"></div> 
	</div>
<% } %>

   