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
        <cq:includeClientLib css="cq.jquery.ui" />
        <cq:includeClientLib css="nhmwww.main" />
        <!--<![endif]-->
        <cq:includeClientLib js="cq.jquery" />
        <cq:includeClientLib js="cq.jquery.ui" />
        <cq:includeClientLib js="nhmwww.main" />
        <% if (isOnEditMode || isOnDesignMode) {%>
        <script src="<%= currentDesign.getPath() + "/js/aem.js"%>"></script>
        <%}%>
        <script>dataLayer = [];</script>
        
        <script>dataLayer = [];</script>
        <!-- Google Tag Manager -->
        <script>(function(w,d,s,l,i){w[l]=w[l]||[];w[l].push({'gtm.start':
        new Date().getTime(),event:'gtm.js'});var f=d.getElementsByTagName(s)[0],
        j=d.createElement(s),dl=l!='dataLayer'?'&l='+l:'';j.async=true;j.src=
        'https://www.googletagmanager.com/gtm.js?id='+i+dl;f.parentNode.insertBefore(j,f);
        })(window,document,'script','dataLayer','GTM-5TDGNT');</script>
        <!-- End Google Tag Manager -->

		<style>
			.js--carousel-image {
				display: none;
			}
		</style>