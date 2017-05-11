<%@page import="uk.ac.nhm.nhm_www.core.componentHelpers.DynamicPageHelper"%>
<%@include file="/apps/nhmwww/components/global.jsp"%>
<%@ page session="false" %>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no" />
    <% DynamicPageHelper helper = new DynamicPageHelper(resource, properties, request); %>
	<link rel="stylesheet" href="<%= helper.getProtocol() + hostPort + currentDesign.getPath() + "/webfont/stylesheet.css"%>" />

        <!--[if lte IE 8]>
        <link rel="stylesheet" href="<%= helper.getProtocol() + hostPort + currentDesign.getPath() + "/css/normalize.css"%>" />
        <link rel="stylesheet" href="<%= helper.getProtocol() + hostPort + currentDesign.getPath() + "/css/nhm-foundation.css"%>" />
		<link rel="stylesheet" href="<%= helper.getProtocol() + hostPort + currentDesign.getPath() + "/css/styles-desktop.css"%>" />
        <![endif]-->
        <!--[if gt IE 8]><!-->
        <link rel="stylesheet" href="<%= helper.getProtocol() + hostPort + currentDesign.getPath() + "/css/normalize.css"%>" />
        <link rel="stylesheet" href="<%= helper.getProtocol() + hostPort + currentDesign.getPath() + "/css/nhm-foundation.css"%>" />
        <link rel="stylesheet" href="<%= helper.getProtocol() + hostPort + currentDesign.getPath() + "/css/styles.css"%>" />
        <!--<![endif]-->
        <!--adding test needs to be removed -->
        <link rel="stylesheet" href="<%= helper.getProtocol() + hostPort + currentDesign.getPath() + "/css/twitter.css"%>" />
        <% if(helper.isDefaultLegacyCSS()) { %>
        	<link rel="stylesheet" href="<%= helper.getProtocol() + hostPort + currentDesign.getPath() + "/css/nhm-legacy-global.css"%>" />
       	<% } %>
        <% if(helper.getLegacyApp() != null && !helper.getLegacyApp().equals("")){ %>
        	<link rel="stylesheet" href="<%= helper.getProtocol() + hostPort + currentDesign.getPath() + "/css/nhm-legacy-" + helper.getLegacyApp() + ".css"%>" />
         <% } %>
        
        
        
        <script type="text/javascript" src="<%= helper.getProtocol() + hostPort  + "/etc/clientlibs/granite/jquery.js"%>"></script>
		<script type="text/javascript" src="<%= helper.getProtocol() + hostPort  + "/etc/clientlibs/granite/utils.js"%>"></script>
		<script type="text/javascript" src="<%= helper.getProtocol() + hostPort + "/etc/clientlibs/granite/jquery/granite.js"%>"></script>
		<script type="text/javascript" src="<%= helper.getProtocol() + hostPort + "/etc/clientlibs/foundation/jquery.js"%>"></script>
        
        <script src="<%= helper.getProtocol() + hostPort + currentDesign.getPath() + "/js/foundation.min.js"%>"></script>
        <script src="<%= helper.getProtocol() + hostPort + currentDesign.getPath() + "/js/plugins.js"%>"></script>
        <script src="<%= helper.getProtocol() + hostPort + currentDesign.getPath() + "/js/main.js"%>"></script>
        <script src="<%= helper.getProtocol() + hostPort + currentDesign.getPath() + "/js/dynamicapps-legacy-global.js"%>"></script>

        <script>dataLayer = [];</script>
        
		<script>dataLayer = [];</script>
		<!-- Google Tag Manager -->
		<script>(function(w,d,s,l,i){w[l]=w[l]||[];w[l].push({'gtm.start':
		new Date().getTime(),event:'gtm.js'});var f=d.getElementsByTagName(s)[0],
		j=d.createElement(s),dl=l!='dataLayer'?'&l='+l:'';j.async=true;j.src=
		'https://www.googletagmanager.com/gtm.js?id='+i+dl;f.parentNode.insertBefore(j,f);
		})(window,document,'script','dataLayer','GTM-5TDGNT');</script>
		<!-- End Google Tag Manager -->
        