<%@ page import="uk.ac.nhm.core.componentHelpers.DefaultPageHelper" %>

<% DefaultPageHelper helper = new DefaultPageHelper(resource, properties, request); %>
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