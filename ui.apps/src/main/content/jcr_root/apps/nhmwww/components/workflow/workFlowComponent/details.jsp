<%--
  Copyright 1997-2009 Day Management AG
  Barfuesserplatz 6, 4001 Basel, Switzerland
  All Rights Reserved.

  This software is the confidential and proprietary information of
  Day Management AG, ("Confidential Information"). You shall not
  disclose such Confidential Information and shall use it only in
  accordance with the terms of the license agreement you entered into
  with Day.

  ==============================================================================

  Overlay this to include detailed information into step rendering.

--%><%@page session="false" import="com.day.cq.i18n.I18n"%><%
%><%@include file="/libs/foundation/global.jsp"%><%

    String xmlPath = properties.get("metaData/xmlPath", String.class);
    if (xmlPath == null || xmlPath.equals("")) {
        xmlPath = I18n.get(slingRequest, "No path selected.");
    }

    String contentPath = properties.get("metaData/contentPath", String.class);
    if (contentPath == null || contentPath.equals("")) {
    	contentPath = I18n.get(slingRequest, "No contentPath selected.");
    }
    
    String imagesPage = properties.get("metaData/imagesPath", String.class);
    if (imagesPage == null || imagesPage.equals("")) {
    	imagesPage = I18n.get(slingRequest, "No imagesPath selected.");
    }
    
    String damPath = properties.get("metaData/damPath", String.class);
    if (damPath == null || damPath.equals("")) {
    	damPath = I18n.get(slingRequest, "No damPath selected.");
    }
    
 	String process = properties.get("metaData/PROCESS", String.class);
    if (process == null || process.equals("")) {
        process = I18n.get(slingRequest, "No process selected.");
    }

%>
<div ext:qtip="<%= xssAPI.encodeForHTMLAttr(xmlPath) %>">
    <div class="process-icon">&nbsp;</div><span class="step-details">[<%= xssAPI.encodeForHTML(process) %>]</span><br/>
    <div class="process-icon">&nbsp;</div><span class="step-details">[<%= xssAPI.encodeForHTML(xmlPath) %>]</span> <br/>
    <div class="process-icon">&nbsp;</div><span class="step-details">[<%= xssAPI.encodeForHTML(contentPath) %>]</span> <br/>
    <div class="process-icon">&nbsp;</div><span class="step-details">[<%= xssAPI.encodeForHTML(damPath) %>]</span> <br/>
    <div class="process-icon">&nbsp;</div><span class="step-details">[<%= xssAPI.encodeForHTML(imagesPage) %>]</span> <br/>
</div>

