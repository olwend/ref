<%@include file="/apps/nhmwww/components/global.jsp"%>
<%@ page session="false" %>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no" />
    
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
	
	<link rel="stylesheet" href="<%= currentDesign.getPath() + "/webfont/stylesheet.css"%>" />

        <!--[if lte IE 8]>
        <link rel="stylesheet" href="<%= currentDesign.getPath() + "/css/normalize.css"%>" />
        <link rel="stylesheet" href="<%= currentDesign.getPath() + "/css/nhm-foundation.css"%>" />
        <link rel="stylesheet" href="<%= currentDesign.getPath() + "/css/styles-desktop.css"%>" />
        <![endif]-->
        <!--[if gt IE 8]><!-->
        <cq:includeClientLib css="nhmwww.main.normalize" />
        <cq:includeClientLib css="nhmwww.main.foundation" />
        <cq:includeClientLib css="nhmwww.main" />
        <!--<![endif]-->
        <cq:includeClientLib js="cq.jquery" />
        <cq:includeClientLib js="nhmwww.main" />
        <% if (isOnEditMode || isOnDesignMode) {%>
        <script src="<%= currentDesign.getPath() + "/js/aem.js"%>"></script>
        <%}%>
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

		<style>
			.js--carousel-image {
				display: none;
			}
		</style>