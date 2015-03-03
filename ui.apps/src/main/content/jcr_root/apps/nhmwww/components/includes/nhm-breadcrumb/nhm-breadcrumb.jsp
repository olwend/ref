<%@page session="false"%><%--
  Copyright 1997-2008 Day Management AG
  Barfuesserplatz 6, 4001 Basel, Switzerland
  All Rights Reserved.

  This software is the confidential and proprietary information of
  Day Management AG, ("Confidential Information"). You shall not
  disclose such Confidential Information and shall use it only in
  accordance with the terms of the license agreement you entered into
  with Day.

  ==============================================================================

  Breadcrumb component

  Draws the breadcrumb

--%><%@include file="/libs/foundation/global.jsp"%><%
%><%

    // get starting point of trail
    long level = 3;
    long endLevel = 1;
    String delimStr = currentStyle.get("delim", "&nbsp;/&nbsp;");
    String trailStr = currentStyle.get("trail", "&nbsp;/&nbsp;");
    int currentLevel = currentPage.getDepth();
    String delim = "";
    while (level < currentLevel - endLevel) {
        Page trail = currentPage.getAbsoluteParent((int) level);
        if (trail == null) {
            break;
        }
        String title = trail.getNavigationTitle();
        if (title == null || title.equals("")) {
            title = trail.getNavigationTitle();
        }
        if (title == null || title.equals("")) {
            title = trail.getTitle();
        }
        if (title == null || title.equals("")) {
            title = trail.getName();
        }
        %><%= xssAPI.filterHTML(delim) %><%
        String path = "";
        if(level == 3){
        	path = "/";
        } else {
        	path = xssAPI.getValidHref(trail.getPath()+".html");
        }
        %><a href="<%= path %>" onclick="CQ_Analytics.record({event:'followBreadcrumb',values: { breadcrumbPath: '<%= xssAPI.getValidHref(trail.getPath()) %>' },collect: false,options: { obj: this },componentPath: '<%=resource.getResourceType()%>'})"><%= xssAPI.encodeForHTML(title) %></a><% delim = delimStr; level++;} %><%= xssAPI.filterHTML(trailStr)%><strong><%= currentPage.getTitle() %></strong>
    


