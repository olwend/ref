<%@page session="false"
          import="com.day.cq.wcm.api.Page"%>
<%@include file="/apps/nhmwww/components/global.jsp"%>
<cq:defineObjects/>

<body>

	<!-- Google Tag Manager -->
	<noscript><iframe src="//www.googletagmanager.com/ns.html?id=GTM-5TDGNT"
	height="0" width="0" style="display:none;visibility:hidden"></iframe></noscript>
	<script>(function(w,d,s,l,i){w[l]=w[l]||[];w[l].push({'gtm.start':
	new Date().getTime(),event:'gtm.js'});var f=d.getElementsByTagName(s)[0],
	j=d.createElement(s),dl=l!='dataLayer'?'&l='+l:'';j.async=true;j.src=
	'//www.googletagmanager.com/gtm.js?id='+i+dl;f.parentNode.insertBefore(j,f);
	})(window,document,'script','dataLayer','GTM-5TDGNT');</script>
	<!-- End Google Tag Manager -->
	<cq:include script="header.jsp"/>
	<div class="main-section <%= templateType.toLowerCase() %> <%=cssClassSection.toLowerCase() %>">
		<%if(currentPage != null && currentPage.getDepth() > 5) { %>
		<div class="breadcrumb tablet desktop">  
		   	<div class="row">	
				<div class="small-12 columns">
						<cq:include path="nhmtrail" resourceType="nhmwww/components/includes/nhm-breadcrumb" />
				</div>
			</div>
		</div>
		<% } %>
		<cq:include script="content.jsp"/>
	</div>
	<cq:include script="footer.jsp"/>

	<script type="text/javascript" src="/etc/clientlibs/nhmwww/smartbanner.js"></script>
	<script type="text/javascript">
      new SmartBanner({
          daysHidden: 0,   // days to hide banner after close button is clicked (defaults to 15)
          daysReminder: 0, // days to hide banner after "VIEW" button is clicked (defaults to 90)
          appStoreLanguage: 'us', // language code for the App Store (defaults to user's browser language)
          title: 'Natural History Museum App',
          author: 'Natural History Museum',
          button: 'VIEW',
          store: {
        	  ios: 'On the App Store',
              android: 'In Google Play'
          },
          price: {
        	  ios: 'FREE',
              android: 'FREE'
          }
          // , theme: '' // put platform type ('ios', 'android', etc.) here to force single theme on all device
          , icon: '/etc/designs/nhmwww/img/icons/nhm-app-icon.jpg' // full path to icon image if not using website icon image
          // , force: 'ios' // Uncomment for platform emulation
      });
    </script>
</body>