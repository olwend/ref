<%@include file="/apps/nhmwww/components/global.jsp"%>
<%@ page session="false"%>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no" />
        
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
        <script>
        // WR-1042 - YouTube API was loading quicker than page DOM, causing a "YT.Player is not a constructor" error and stopping Discover captions from being displayed on-click.
        // Moving the YouTube API function into the headlibs allows the API to load quicker and resolves the issues.
        	onYouTubeIframeAPIReady();
        </script>
        <cq:includeClientLib css="nhm-www.discover" />
		<cq:includeClientLib categories="nhm-www.discoverpublication" />
