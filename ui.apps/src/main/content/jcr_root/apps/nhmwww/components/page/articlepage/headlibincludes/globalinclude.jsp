<%@ include file="/apps/nhmwww/components/global.jsp" %>
<%@ page session="false" %>
<%@ page import="uk.ac.nhm.core.componentHelpers.DefaultPageHelper" %>

<%@ include file="/apps/nhmwww/components/page/articlepage/headlibincludes/tagmanager.jsp" %>

<meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no" />

<cq:includeClientLib css="nhmwww.webfont" />
<cq:includeClientLib css="nhmwww.main.normalize" />
<cq:includeClientLib css="nhmwww.main.foundation" />

<% if (isOnEditMode || isOnDesignMode || isOnEventCalendarPage) {%>
<!-- START CQ.JQUERY FOR AUTHOR AND EVENTS PAGES ONLY -->
	<%@ include file="/apps/nhmwww/components/page/defaultpage/headlibincludes/cqjquery.jsp" %>
<!-- END CQ.JQUERY FOR AUTHOR AND EVENTS PAGES ONLY -->
<%} else {%>
<!-- START JQUERY 1.11.2 CDN -->
	<script src="https://code.jquery.com/jquery-1.11.2.min.js" integrity="sha256-Ls0pXSlb7AYs7evhd+VLnWsZ/AqEHcXBeMZUycz/CcA=" crossorigin="anonymous"></script>
<!-- END JQUERY 1.11.2 CDN -->
<%}%>

<cq:includeClientLib css="nhmwww.main" />

<!-- START WR-1011: split mainJS clientlib into separate clientlibs -->
<cq:includeClientLib js="nhmwww.main.foundation" />
<cq:includeClientLib js="nhmwww.main.plugins" />
<cq:includeClientLib js="nhmwww.main" />
<!--  END WR-1011 -->

<% if (isOnEditMode || isOnDesignMode) {%>
<!--
Edit Mode is <% if (isOnEditMode) {%>on<%} else {%>off<%}%>
Design Mode is <% if (isOnDesignMode) {%>on<%} else {%>off<%}%>
Preview Mode is <% if (isOnPreviewMode) {%>on<%} else {%>off<%}%>
-->
	<script src="<%= currentDesign.getPath() + "/js/aem.js" %>"></script>
<%}%>