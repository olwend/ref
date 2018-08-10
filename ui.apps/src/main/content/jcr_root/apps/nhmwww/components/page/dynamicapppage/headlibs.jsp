<%@ include file="/apps/nhmwww/components/global.jsp"%>

<%@ page session="false" %>
<%@ page import="uk.ac.nhm.core.componentHelpers.DynamicPageHelper"%>
	<meta charset="utf-8" />

	<%@ include file="/apps/nhmwww/components/page/defaultpage/headlibincludes/tagmanager.jsp" %>

	<meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no" />

	<% DynamicPageHelper helper = new DynamicPageHelper(resource, properties, request); %>

	<link rel="stylesheet" href="<%= helper.getProtocol() + hostPort + currentDesign.getPath() + "/webfont/stylesheet.css"%>" />

	<link rel="stylesheet" href="<%= helper.getProtocol() + hostPort + currentDesign.getPath() + "/css/normalize.css"%>" />
	<link rel="stylesheet" href="<%= helper.getProtocol() + hostPort + currentDesign.getPath() + "/css/nhm-foundation.css"%>" />
	<link rel="stylesheet" href="<%= helper.getProtocol() + hostPort + currentDesign.getPath() + "/css/styles.css"%>" />

	<!--adding test needs to be removed -->
	<link rel="stylesheet" href="<%= helper.getProtocol() + hostPort + currentDesign.getPath() + "/css/twitter.css"%>" />
	<% if(helper.isDefaultLegacyCSS()) { %>
		<link rel="stylesheet" href="<%= helper.getProtocol() + hostPort + currentDesign.getPath() + "/css/nhm-legacy-global.css"%>" />
	<% } %>
	<% if(helper.getLegacyApp() != null && !helper.getLegacyApp().equals("")){ %>
		<link rel="stylesheet" href="<%= helper.getProtocol() + hostPort + currentDesign.getPath() + "/css/nhm-legacy-" + helper.getLegacyApp() + ".css"%>" />
	 <% } %>

	<% if (isOnEditMode || isOnDesignMode) {%>
	<!-- START CQ.JQUERY FOR AUTHOR ONLY -->
		<script type="text/javascript" src="<%= helper.getProtocol() + hostPort  + "/etc/clientlibs/granite/jquery.js"%>"></script>
		<script type="text/javascript" src="<%= helper.getProtocol() + hostPort  + "/etc/clientlibs/granite/utils.js"%>"></script>
		<script type="text/javascript" src="<%= helper.getProtocol() + hostPort + "/etc/clientlibs/granite/jquery/granite.js"%>"></script>
		<script type="text/javascript" src="<%= helper.getProtocol() + hostPort + "/etc/clientlibs/foundation/jquery.js"%>"></script>
	<!-- END CQ.JQUERY FOR AUTHOR ONLY -->
	<%}%>

	<!-- START JQUERY 1.11.2 CDN -->
	<script src="https://code.jquery.com/jquery-1.11.2.min.js" integrity="sha256-Ls0pXSlb7AYs7evhd+VLnWsZ/AqEHcXBeMZUycz/CcA=" crossorigin="anonymous"></script>
	<!-- END JQUERY 1.11.2 CDN -->

	<script src="<%= helper.getProtocol() + hostPort + currentDesign.getPath() + "/js/foundation.min.js"%>"></script>
	<script src="<%= helper.getProtocol() + hostPort + currentDesign.getPath() + "/js/plugins.js"%>"></script>
	<script src="<%= helper.getProtocol() + hostPort + currentDesign.getPath() + "/js/main.js"%>"></script>
	<script src="<%= helper.getProtocol() + hostPort + currentDesign.getPath() + "/js/dynamicapps-legacy-global.js"%>"></script>