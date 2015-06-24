<%--
  Copyright 1997-2008 Day Management AG
  Barfuesserplatz 6, 4001 Basel, Switzerland
  All Rights Reserved.

  This software is the confidential and proprietary information of
  Day Management AG, ("Confidential Information"). You shall not
  disclose such Confidential Information and shall use it only in
  accordance with the terms of the license agreement you entered into
  with Day.

  ==============================================================================

  Global WCM script.

  This script can be used by any other script in order to get the default
  tag libs, sling objects and CQ objects defined.

  the following page context attributes are initialized via the <cq:defineObjects/>
  tag:

    @param slingRequest SlingHttpServletRequest
    @param slingResponse SlingHttpServletResponse
    @param resource the current resource
    @param currentNode the current node
    @param log default logger
    @param sling sling script helper

    @param componentContext component context of this request
    @param editContext edit context of this request
    @param properties properties of the addressed resource (aka "localstruct")
    @param pageManager page manager
    @param currentPage containing page addressed by the request (aka "actpage")
    @param resourcePage containing page of the addressed resource (aka "myPage")
    @param pageProperties properties of the containing page
    @param component current CQ5 component
    @param designer designer
    @param currentDesign design of the addressed resource  (aka "actdesign")
    @param resourceDesign design of the addressed resource (aka "myDesign")
    @param currentStyle style of the addressed resource (aka "actstyle")

  ==============================================================================

--%><%@page session="false" import="javax.jcr.*,
        org.apache.sling.api.resource.Resource,
        org.apache.sling.api.resource.ValueMap,
        com.day.cq.commons.inherit.InheritanceValueMap,
        com.day.cq.commons.Externalizer,
        com.day.cq.wcm.commons.WCMUtils,
        com.day.cq.wcm.api.Page,
        com.day.cq.wcm.api.NameConstants,
        com.day.cq.wcm.api.PageManager,
        com.day.cq.wcm.api.WCMMode,
        com.day.cq.wcm.api.designer.Designer,
        com.day.cq.wcm.api.designer.Design,
        com.day.cq.wcm.api.designer.Style,
        com.day.cq.wcm.api.components.ComponentContext,
        com.day.cq.wcm.api.components.EditContext,
        java.util.Calendar,
        java.util.GregorianCalendar,
        java.text.SimpleDateFormat"
%><%@taglib prefix="sling" uri="http://sling.apache.org/taglibs/sling/1.0" %><%
%><%@taglib prefix="cq" uri="http://www.day.com/taglibs/cq/1.0" %><%
%><%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%
%><%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%
%><%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %><%
%><cq:defineObjects /><%
    // add more initialization code here
    final boolean WCMIsOnEditMode = (WCMMode.fromRequest(slingRequest) == WCMMode.EDIT);
    boolean isOnEditMode = (WCMMode.fromRequest(slingRequest) == WCMMode.EDIT);
	boolean isOnPreviewMode = (WCMMode.fromRequest(slingRequest) == WCMMode.PREVIEW);
	boolean isOnDesignMode = (WCMMode.fromRequest(slingRequest) == WCMMode.DESIGN);
	if (isOnPreviewMode) {isOnEditMode = false;}
	String cssClassSection = "";
	
	if(currentPage != null && currentPage.getDepth() > 5) {
		Page landingPage = currentPage.getAbsoluteParent(4);
		cssClassSection = landingPage.getName();
	} else if(currentPage != null && (currentPage.getDepth() == 5 || currentPage.getDepth() == 4)) {
		cssClassSection = currentPage.getName();
	}
	String templateType = "";
	if(currentPage != null && currentPage.getProperties().get("cq:template", "").equals("/apps/nhmwww/templates/contentpage") ){
		templateType = "content-page";
	}
	
	Calendar calendar = Calendar.getInstance(); 
	SimpleDateFormat formatter= new SimpleDateFormat("yyyy/MM/dd");
	String dateNow = formatter.format(calendar.getTime());
	Calendar christmasEve = new GregorianCalendar();
	christmasEve.set(calendar.get(Calendar.YEAR), 12, 24);
	String christmasEveDate = formatter.format(christmasEve.getTime());
	Calendar christmasDay = new GregorianCalendar();
	christmasDay.set(calendar.get(Calendar.YEAR), 12, 25);
	String christmasDayDate = formatter.format(christmasDay.getTime());
	Calendar boxingDay = new GregorianCalendar();
	boxingDay.set(calendar.get(Calendar.YEAR), 12, 26);
	String boxingDayDate = formatter.format(boxingDay.getTime());
	
	String openText = "";
	if(dateNow.equals(christmasEveDate) || dateNow.equals(christmasDayDate) || dateNow.equals(boxingDayDate) ) {
		openText = "Closed";
	} else {
		openText = "<span class=\"desktop\">Open </span>10.00-17.50";
	}
	
%>

<%
	Externalizer externalizer = resourceResolver.adaptTo(Externalizer.class);
	String myExternalizedUrl = "";
	if(isOnEditMode || isOnPreviewMode || isOnDesignMode) {
		myExternalizedUrl = externalizer.externalLink(resourceResolver, Externalizer.AUTHOR, "/content/hnmwww/en/home") + ".html";	
	} else {
		myExternalizedUrl = externalizer.externalLink(resourceResolver, Externalizer.PUBLISH, "/content/hnmwww/en/home") + ".html";
	}
	String pathForSignup = currentPage.getPath();

	int startIndex = 0;
	if(myExternalizedUrl.startsWith("https")) {
		startIndex = 8;
	} else if(myExternalizedUrl.startsWith("http")){
		startIndex = 7;
	} 
		
	String hostPort = myExternalizedUrl.substring(startIndex, myExternalizedUrl.indexOf("/", startIndex));
	pathForSignup = pathForSignup.substring(pathForSignup.indexOf("/home") +5 , pathForSignup.length());
%>