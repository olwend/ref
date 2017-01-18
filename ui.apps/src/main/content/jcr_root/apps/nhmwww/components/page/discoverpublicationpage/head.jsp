<%@page session="false"%>
<%@page import="uk.ac.nhm.nhm_www.core.componentHelpers.DiscoverPublicationHelper"%><%--
  Copyright 1997-2010 Day Management AG
  Barfuesserplatz 6, 4001 Basel, Switzerland
  All Rights Reserved.

  This software is the confidential and proprietary information of
  Day Management AG, ("Confidential Information"). You shall not
  disclose such Confidential Information and shall use it only in
  accordance with the terms of the license agreement you entered into
  with Day.

  ==============================================================================

  Default head script.

  Draws the HTML head with some default content:
  - includes the WCML init script
  - includes the head libs script
  - includes the favicons
  - sets the HTML title
  - sets some meta data

  ==============================================================================

--%><%@include file="/libs/foundation/global.jsp" %><%
%><%@ page import="com.day.cq.commons.Doctype" %>
<%@ page import="uk.ac.nhm.nhm_www.core.utils.*" %>

<%	
		DiscoverPublicationHelper helper = new DiscoverPublicationHelper(resource);

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
	
	
	<%if(!helper.getOgTitle().equals("")) { %>
		<meta property="og:title" content="<%=helper.getOgTitle() %>">
	<% }
	else { %>
		<meta property="og:title" content="<%=helper.getPageTitle() %>">
	<%} %>
	
	<%if(!helper.getOgDescription().equals("")) { %>
		<meta property="og:description" content="<%=helper.getOgDescription() %>" >
	<% }
	else { %>
		<meta property="og:description" content="<%=helper.getPageDescription() %>">
	<%} %>
	
	<%if(!helper.getOgImagePath().equals("")) { 
		if(helper.getSelectTab().equals("radioImage")) {%>
			<meta property="og:image" content="http://<%=request.getServerName() %><%=helper.getOgImagePath() %>">
		<%}
		else if(helper.getSelectTab().equals("radioVideo")) { %>
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
    <cq:includeClientLib categories="nhmwww.youtubefluid" />
</head>
