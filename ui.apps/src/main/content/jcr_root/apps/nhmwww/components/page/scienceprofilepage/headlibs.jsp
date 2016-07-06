<%@include file="/apps/nhmwww/components/global.jsp"%>
<%@ page session="false" %>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no" />
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
         
        <cq:includeClientLib categories="nhm-www.scienceprofiles" />
        <script type="text/javascript">var switchTo5x=true;</script>
		<script type="text/javascript" src="https://ws.sharethis.com/button/buttons.js"></script>
		<script type="text/javascript">stLight.options({publisher: "4069e561-f5e3-40c7-a58e-a86ba8d470ac", doNotHash: false, doNotCopy: false, hashAddressBar: false, onhover: false});</script>
        