<%@page session="false"
          import="com.day.cq.wcm.api.Page"%>
<%@include file="/apps/nhmwww/components/global.jsp"%>
<cq:defineObjects/>

<body>
	<!-- Google Tag Manager (noscript) -->
	<noscript><iframe src="https://www.googletagmanager.com/ns.html?id=GTM-5TDGNT"
	height="0" width="0" style="display:none;visibility:hidden"></iframe></noscript>
	<!-- End Google Tag Manager (noscript) -->
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
	
	
	<%if(request.getServerName().equals("localhost")) {%>
	<!-- Inject BrowserSync JS for localhost build environment. Does not load for live sites. Handles auto reload of local AEM instances -->
		<cq:includeClientLib js="nhmwww.aembrowsersync" />		
	<%} %>
		
</body>