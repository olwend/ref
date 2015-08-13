<%@include file="/apps/nhmwww/components/global.jsp"%>
<%@ page session="false" %>
    <meta charset="ISO-8859-1" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
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
        <cq:includeClientLib categories="nhm-www.discover"/>
        <cq:includeClientLib categories="discoverservice" />
		<cq:includeClientLib categories="youtubeservice" />
		<script type="text/javascript">var switchTo5x=true;</script>
		<script type="text/javascript" src="http://w.sharethis.com/button/buttons.js"></script>
		<script type="text/javascript">stLight.options({publisher: "4069e561-f5e3-40c7-a58e-a86ba8d470ac", doNotHash: false, doNotCopy: false, hashAddressBar: false, onhover: false});</script>
		