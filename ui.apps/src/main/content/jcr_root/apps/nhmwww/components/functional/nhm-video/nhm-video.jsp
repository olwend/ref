<%--

  NHM Video component.

  Insert a video in the pages

--%><%
%><%@page session="false"%>
<%@include file="/apps/nhmwww/components/global.jsp"%>
<%@page import="uk.ac.nhm.core.componentHelpers.*"%>
<%
%><%
	// TODO add you code here
%>
<%
	NHMVideoHelper helper = new NHMVideoHelper(slingRequest, currentPage, properties);
%>

<div class="video-wrapper" data-nhm-videoid="<%= helper.getVideoId() %>">
          	<div id="<%= helper.getVideoId()%>" class="youtubeplayer"></div> 
</div>

   