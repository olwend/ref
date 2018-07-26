<%@page session="false"%>
<%@page import="uk.ac.nhm.core.componentHelpers.ArticleHelper"%>
<%@include file="/libs/foundation/global.jsp" %><%
%><%@ page import="com.day.cq.commons.Doctype" %>
<%@ page import="uk.ac.nhm.core.utils.*" %>

<%
	ArticleHelper helper = new ArticleHelper(resource, slingRequest);
%>

<%
  String xs = Doctype.isXHTML(request) ? "/" : "";
  String favIcon = currentDesign.getPath() + "/favicon.ico";
  if (resourceResolver.getResource(favIcon) == null) {
    favIcon = null;
  }
%><head>
	<meta charset="utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta http-equiv="content-type" content="text/html; charset=UTF-8"<%=xs%>>
	
	<title><%= currentPage.getTitle() == null ? xssAPI.encodeForHTML(currentPage.getName()) : xssAPI.encodeForHTML(currentPage.getTitle()) %> | Natural History Museum</title>
	<meta name="description" content="<%=PageUtils.EncodeMetaDescription(properties.get("jcr:description", ""))%>"<%=xs%>>
	<meta name="keywords" content="<%= xssAPI.encodeForHTMLAttr(WCMUtils.getKeywords(currentPage, false)) %>"<%=xs%>>
	
	<meta name="twitter:widgets:csp" content="on">
	<meta name="twitter:card" content="summary_large_image">
	<meta name="twitter:site" content="@NHM_London">
	<meta name="twitter:creator" content="@NHM_London">

	<%if(helper.getOgTitle() != null) {%>
		<meta property="og:title" content="<%=helper.getOgTitle() %>">
		<meta name="twitter:title" content="<%=helper.getOgTitle() %>">
	<%} %>
	<%if(helper.getOgDescription() != null) {%>
		<meta property="og:description" content="<%=helper.getOgDescription() %>" >
		<meta name="twitter:description" content="<%=helper.getOgDescription() %>">
	<%} %>
	<%if(helper.getOgImagePath() != null) {
		if(helper.getSelectTab().equals("image")) {%>
			<meta property="og:image" content="http://<%=request.getServerName() %><%=helper.getOgImagePath() %>">
			<meta name="twitter:image" content="http://<%=request.getServerName() %><%=helper.getOgImagePath() %>">
		<%}
		else if(helper.getSelectTab().equals("video")) { %>
			<meta property="og:image" content="http://img.youtube.com/vi/<%= helper.getOgImagePath()%>/maxresdefault.jpg"/>
			<meta name="twitter:image" content="http://img.youtube.com/vi/<%= helper.getOgImagePath()%>/maxresdefault.jpg">
		<%}
	}%>

	
	
	<cq:include script="headlibs.jsp"/>
	<cq:include script="/libs/wcm/core/components/init/init.jsp"/>

	<% if (favIcon != null) { %>
	<link rel="icon" type="image/vnd.microsoft.icon" href="<%= xssAPI.getValidHref(favIcon) %>"<%=xs%>>
	<link rel="shortcut icon" type="image/vnd.microsoft.icon" href="<%= xssAPI.getValidHref(favIcon) %>"<%=xs%>>
	<% } %>
</head>
