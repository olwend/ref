<%@page session="false"%>
<%@page import="uk.ac.nhm.nhm_www.core.componentHelpers.ArticleHelper"%>
<%@include file="/libs/foundation/global.jsp" %><%
%><%@ page import="com.day.cq.commons.Doctype" %>
<%@ page import="uk.ac.nhm.nhm_www.core.utils.*" %>

<%	
	ArticleHelper helper = new ArticleHelper(resource);
%>

<%
    String xs = Doctype.isXHTML(request) ? "/" : "";
    String favIcon = currentDesign.getPath() + "/favicon.ico";
    if (resourceResolver.getResource(favIcon) == null) {
        favIcon = null;
    }
%><head>
	
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8"<%=xs%>>
    <meta name="keywords" content="<%= xssAPI.encodeForHTMLAttr(WCMUtils.getKeywords(currentPage, false)) %>"<%=xs%>>
    <meta name="description" content="<%=PageUtils.EncodeMetaDescription(properties.get("jcr:description", ""))%>"<%=xs%>>
	<meta name="twitter:widgets:csp" content="on">
	
	<%if(helper.getOgTitle() != null) {%>
		<meta property="og:title" content="<%=helper.getOgTitle() %>">
	<%} %>
	<%if(helper.getOgDescription() != null) {%>
		<meta property="og:description" content="<%=helper.getOgDescription() %>" >
	<%} %>
	<%if(helper.getOgImagePath() != null) { 
		if(helper.getSelectTab().equals("image")) {%>
			<meta property="og:image" content="http://<%=request.getServerName() %><%=helper.getOgImagePath() %>">
		<%}
		else if(helper.getSelectTab().equals("video")) { %>
			<meta property="og:image" content="http://img.youtube.com/vi/<%= helper.getOgImagePath()%>/maxresdefault.jpg"/>
		<%} 
	}%>

	<title><%= currentPage.getTitle() == null ? xssAPI.encodeForHTML(currentPage.getName()) : xssAPI.encodeForHTML(currentPage.getTitle()) %> | Natural History Museum</title>
    <cq:include script="headlibs.jsp"/>
    <cq:include script="/libs/wcm/core/components/init/init.jsp"/>

    <% if (favIcon != null) { %>
    <link rel="icon" type="image/vnd.microsoft.icon" href="<%= xssAPI.getValidHref(favIcon) %>"<%=xs%>>
    <link rel="shortcut icon" type="image/vnd.microsoft.icon" href="<%= xssAPI.getValidHref(favIcon) %>"<%=xs%>>
    <% } %>
</head>
