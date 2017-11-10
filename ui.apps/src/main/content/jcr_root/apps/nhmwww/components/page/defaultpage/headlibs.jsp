<%@include file="/apps/nhmwww/components/global.jsp"%>
<%@ page session="false" %>
<%@ page import="uk.ac.nhm.core.componentHelpers.DefaultPageHelper" %>
    <meta charset="utf-8" />
    
    <!-- WR-1074 - add Google Optimise for A/B testing -->
	<!-- Initialise the dataLayer -->
	<script>dataLayer = [];</script>
	
	<!-- Page hiding snippet -->
	<style>.async-hide { opacity: 0 !important} </style>
	<script>(function(a,s,y,n,c,h,i,d,e){s.className+=' '+y;h.start=1*new Date;
	h.end=i=function(){s.className=s.className.replace(RegExp(' ?'+y),'')};
	(a[n]=a[n]||[]).hide=h;setTimeout(function(){i();h.end=null},c);h.timeout=c;
	})(window,document.documentElement,'async-hide','dataLayer',4000,
	{'GTM-5D4H9KX':true});</script>
	<!-- End page hiding snippet -->
	
	<!-- Google Analytics Snippet - No Pageview Fired -->
	<script>
	(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
	(i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
	m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
	})(window,document,'script','https://www.google-analytics.com/analytics.js','ga');
	
	ga('create', 'UA-57477456-1', 'auto');
	ga('require', 'linker');
	ga('linker:autoLink', ['secpay.com','nhmimages.com','nhm.ac.uk'] );
	ga('require', 'GTM-5D4H9KX');
	</script>
	<!-- End Google Analytics -->
	
	<!-- Google Tag Manager -->
	<script>(function(w,d,s,l,i){w[l]=w[l]||[];w[l].push({'gtm.start':
	new Date().getTime(),event:'gtm.js'});var f=d.getElementsByTagName(s)[0],
	j=d.createElement(s),dl=l!='dataLayer'?'&l='+l:'';j.async=true;j.src=
	'https://www.googletagmanager.com/gtm.js?id='+i+dl;f.parentNode.insertBefore(j,f);
	})(window,document,'script','dataLayer','GTM-5TDGNT');</script>
	<!-- End Google Tag Manager -->
	<!-- END WR-1074 -->
    
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

        <cq:includeClientLib css="nhmwww.main.normalize" />
        <cq:includeClientLib css="nhmwww.main.foundation" />
        <cq:includeClientLib css="nhmwww.main" />

        <!-- START CQ.JQUERY FOR AUTHOR ONLY -->
        <% if (isOnEditMode || isOnDesignMode) {%>
        	<cq:includeClientLib css="cq.jquery.ui" />
        	<cq:includeClientLib js="cq.jquery" />
         	<cq:includeClientLib js="cq.jquery.ui" />
        <%}%>
        <!-- END CQ.JQUERY FOR AUTHOR ONLY -->
        <!-- START JQUERY 1.11.2 CDN -->
        <script src="https://code.jquery.com/jquery-1.11.2.min.js" integrity="sha256-Ls0pXSlb7AYs7evhd+VLnWsZ/AqEHcXBeMZUycz/CcA=" crossorigin="anonymous"></script>
        <!-- END JQUERY 1.11.2 CDN -->
        <!--  START NHMWWW.MAIN -->
        <cq:includeClientLib js="nhmwww.main" />
        <!--  END NHMWWW.MAIN -->
        <% if (isOnEditMode || isOnDesignMode) {%>
        <script src="<%= currentDesign.getPath() + "/js/aem.js"%>"></script>
        <%}%>

		<style>
			.js--carousel-image {
				display: none;
			}
		</style>