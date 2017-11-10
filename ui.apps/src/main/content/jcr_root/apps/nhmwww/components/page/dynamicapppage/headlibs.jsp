<%@page import="uk.ac.nhm.core.componentHelpers.DynamicPageHelper"%>
<%@include file="/apps/nhmwww/components/global.jsp"%>
<%@ page session="false" %>
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

        <!-- START CQ.JQUERY FOR AUTHOR ONLY -->
        <% if (isOnEditMode || isOnDesignMode) {%>
        <script type="text/javascript" src="<%= helper.getProtocol() + hostPort  + "/etc/clientlibs/granite/jquery.js"%>"></script>
		<script type="text/javascript" src="<%= helper.getProtocol() + hostPort  + "/etc/clientlibs/granite/utils.js"%>"></script>
		<script type="text/javascript" src="<%= helper.getProtocol() + hostPort + "/etc/clientlibs/granite/jquery/granite.js"%>"></script>
		<script type="text/javascript" src="<%= helper.getProtocol() + hostPort + "/etc/clientlibs/foundation/jquery.js"%>"></script>
        <%}%>
        <!-- END CQ.JQUERY FOR AUTHOR ONLY -->
        
        <!-- START JQUERY 1.11.2 CDN -->
        <script src="https://code.jquery.com/jquery-1.11.2.min.js" integrity="sha256-Ls0pXSlb7AYs7evhd+VLnWsZ/AqEHcXBeMZUycz/CcA=" crossorigin="anonymous"></script>
        <!-- END JQUERY 1.11.2 CDN -->
        <script src="<%= helper.getProtocol() + hostPort + currentDesign.getPath() + "/js/foundation.min.js"%>"></script>
        <script src="<%= helper.getProtocol() + hostPort + currentDesign.getPath() + "/js/plugins.js"%>"></script>
        <script src="<%= helper.getProtocol() + hostPort + currentDesign.getPath() + "/js/main.js"%>"></script>
        <script src="<%= helper.getProtocol() + hostPort + currentDesign.getPath() + "/js/dynamicapps-legacy-global.js"%>"></script>        