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
	<div class="main-section <%=cssClassSection.toLowerCase() %>">
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
</body>