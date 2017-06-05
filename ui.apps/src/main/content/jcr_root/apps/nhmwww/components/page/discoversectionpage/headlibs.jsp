<%@include file="/apps/nhmwww/components/global.jsp"%>
<%@ page session="false" %>
<%@ page import="uk.ac.nhm.nhm_www.core.componentHelpers.DefaultPageHelper" %>
    <meta charset="ISO-8859-1" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no" />
    
    <%
    	DefaultPageHelper helper = new DefaultPageHelper(resource, properties, request);
    %>
    
    <%if(helper.getUseBanner()) { %>
    
    <!-- WR-1031: smartbanner configuration -->
	<meta name="smartbanner:title" content="NHM Visitor app">
	<meta name="smartbanner:author" content="Use our map for phones">
	<meta name="smartbanner:price" content="FREE">
	<meta name="smartbanner:price-suffix-apple" content=" - On the App Store">
	<meta name="smartbanner:price-suffix-google" content=" - In Google Play">
	<meta name="smartbanner:icon-apple" content="/etc/designs/nhmwww/img/icons/nhm-app-icon.jpg">
	<meta name="smartbanner:icon-google" content="/etc/designs/nhmwww/img/icons/nhm-app-icon.jpg">
	<meta name="smartbanner:button" content="VIEW">
	<meta name="smartbanner:button-url-apple" content="https://itunes.apple.com/gb/app/natural-history-museum-app/id948082332?mt=8">
	<meta name="smartbanner:button-url-google" content="market://details?id=uk.ac.nhmexplorer&hl=en_GB">
	<meta name="smartbanner:enabled-platforms" content="android,ios">
	
	<cq:includeClientLib categories="nhmwww.smartbanner" />
	<!-- End smartbanner configuration -->
	
	<% } %>
	
	<link rel="stylesheet" href="<%= currentDesign.getPath() + "/webfont/stylesheet.css"%>" />

        <!--[if lte IE 8]>
        <link rel="stylesheet" href="<%= currentDesign.getPath() + "/css/normalize.css"%>" />
        <link rel="stylesheet" href="<%= currentDesign.getPath() + "/css/nhm-foundation.css"%>" />
		<link rel="stylesheet" href="<%= currentDesign.getPath() + "/css/styles-desktop.css"%>" />
        <![endif]-->
        <!--[if gt IE 8]><!-->
        <link rel="stylesheet" href="<%= currentDesign.getPath() + "/css/normalize.css"%>" />
        <link rel="stylesheet" href="<%= currentDesign.getPath() + "/css/nhm-foundation.css"%>" />
        <link rel="stylesheet" href="<%= currentDesign.getPath() + "/css/styles.css"%>" />
        <!--<![endif]-->
        
        <link rel="stylesheet" href="<%= currentDesign.getPath() + "/css/twitter.css"%>" />
        
        <cq:includeClientLib js="cq.jquery" />
        <script src="<%= currentDesign.getPath() + "/js/foundation.min.js"%>"></script>
        <script src="<%= currentDesign.getPath() + "/js/plugins.js"%>"></script>
        <script src="<%= currentDesign.getPath() + "/js/main.js"%>"></script>
        <% if (isOnEditMode || isOnDesignMode) {%>
        <script src="<%= currentDesign.getPath() + "/js/aem.js"%>"></script>
        <%}%>
        <!-- 
        Edit Mode is <% if (isOnEditMode) {%>on<%} else {%>off<%}%>
        Design Mode is <% if (isOnDesignMode) {%>on<%} else {%>off<%}%>
        Preview Mode is <% if (isOnPreviewMode) {%>on<%} else {%>off<%}%>
         -->
        
		<script>dataLayer = [];</script>
		<!-- Google Tag Manager -->
		<script>(function(w,d,s,l,i){w[l]=w[l]||[];w[l].push({'gtm.start':
		new Date().getTime(),event:'gtm.js'});var f=d.getElementsByTagName(s)[0],
		j=d.createElement(s),dl=l!='dataLayer'?'&l='+l:'';j.async=true;j.src=
		'https://www.googletagmanager.com/gtm.js?id='+i+dl;f.parentNode.insertBefore(j,f);
		})(window,document,'script','dataLayer','GTM-5TDGNT');</script>
		<!-- End Google Tag Manager -->
		
        <cq:includeClientLib categories="nhm-www.discover"/>
        <cq:includeClientLib categories="discoverservice" />
		<cq:includeClientLib categories="youtubeservice" />
