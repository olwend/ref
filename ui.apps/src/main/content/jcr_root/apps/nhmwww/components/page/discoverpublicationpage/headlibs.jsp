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
        
        <script>
        	(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
        	(i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
        	m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
        	})(window,document,'script','//www.google-analytics.com/analytics.js','ga');
        
        	ga('create', 'UA-57477456-1', 'auto', {'allowLinker' : true});
        	ga('require','linker');
        	ga('linker:autoLink', ['secpay.com','nhm.ac.uk'] );
        	ga('send', 'pageview');
		</script>
        
        <cq:includeClientLib css="nhm-www.discover" />
		<cq:includeClientLib categories="nhm-www.discoverpublication" />
		<script type="text/javascript">var switchTo5x=true;</script>
		<script type="text/javascript" src="https://ws.sharethis.com/button/buttons.js"></script>
		<script type="text/javascript">stLight.options({publisher: "4069e561-f5e3-40c7-a58e-a86ba8d470ac", doNotHash: false, doNotCopy: false, hashAddressBar: false, onhover: false});</script>
		
		<!-- WR-927 Adobe Analytics/DTM trial -->
		<%if(request.getServerName().equals("www.nhm.ac.uk")) {%>
			<script src="//assets.adobedtm.com/b018ac1d55b0c810cb8357c0a52ba0b195706b2a/satelliteLib-2e3a2d951df95a7f9b84f5addda482287d462b79.js"></script>
		<%} else {%>
			<script src="//assets.adobedtm.com/b018ac1d55b0c810cb8357c0a52ba0b195706b2a/satelliteLib-2e3a2d951df95a7f9b84f5addda482287d462b79-staging.js"></script>
		<%}%>
