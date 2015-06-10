<%@page import="uk.ac.nhm.nhm_www.core.componentHelpers.DynamicPageHelper"%>
<%@include file="/apps/nhmwww/components/global.jsp"%>
<%@ page session="false" %>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <% DynamicPageHelper helper = new DynamicPageHelper(resource, properties, request); %>
	<link rel="stylesheet" href="<%= helper.getProtocol() + hostPort + currentDesign.getPath() + "/webfont/stylesheet.css"%>" />

        <!--[if lte IE 8]>
		<link rel="stylesheet" href="<%= helper.getProtocol() + hostPort + currentDesign.getPath() + "/css/styles-desktop.css"%>" />
        <![endif]-->
        <!--[if gt IE 8]><!-->
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

        <script>dataLayer = [];</script>
		<script type="text/javascript">var switchTo5x=true;</script>
		<script type="text/javascript" src="http://w.sharethis.com/button/buttons.js"></script>
		<script type="text/javascript">stLight.options({publisher: "4069e561-f5e3-40c7-a58e-a86ba8d470ac", doNotHash: false, doNotCopy: false, hashAddressBar: false, onhover: false});</script>
		
		
		