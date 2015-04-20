<%@include file="/apps/nhmwww/components/global.jsp"%>
<%@ page session="false" %>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<link rel="stylesheet" href="<%= hostPort + currentDesign.getPath() + "/webfont/stylesheet.css"%>" />

        <!--[if lte IE 8]>
		<link rel="stylesheet" href="<%= hostPort + currentDesign.getPath() + "/css/styles-desktop.css"%>" />
        <![endif]-->
        <!--[if gt IE 8]><!-->
        <link rel="stylesheet" href="<%= hostPort + currentDesign.getPath() + "/css/styles.css"%>" />
        <!--<![endif]-->
        
        <link rel="stylesheet" href="<%= hostPort + currentDesign.getPath() + "/css/twitter.css"%>" />
        <link rel="stylesheet" href="<%= hostPort + currentDesign.getPath() + "/css/nhm-legacy.css"%>" />
        
        <cq:includeClientLib js="cq.jquery" />
        <script src="<%= hostPort + currentDesign.getPath() + "/js/foundation.min.js"%>"></script>
        <script src="<%= hostPort + currentDesign.getPath() + "/js/plugins.js"%>"></script>
        <script src="<%= hostPort + currentDesign.getPath() + "/js/main.js"%>"></script>

        <script>dataLayer = [];</script>
		<script type="text/javascript">var switchTo5x=true;</script>
		<script type="text/javascript" src="http://w.sharethis.com/button/buttons.js"></script>
		<script type="text/javascript">stLight.options({publisher: "4069e561-f5e3-40c7-a58e-a86ba8d470ac", doNotHash: false, doNotCopy: false, hashAddressBar: false, onhover: false});</script>
		
		
		