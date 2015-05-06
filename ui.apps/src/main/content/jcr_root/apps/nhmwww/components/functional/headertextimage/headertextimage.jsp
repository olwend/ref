<%--
  ADOBE CONFIDENTIAL
  __________________

   Copyright 2012 Adobe Systems Incorporated
   All Rights Reserved.

  NOTICE:  All information contained herein is, and remains
  the property of Adobe Systems Incorporated and its suppliers,
  if any.  The intellectual and technical concepts contained
  herein are proprietary to Adobe Systems Incorporated and its
  suppliers and are protected by trade secret or copyright law.
  Dissemination of this information or reproduction of this material
  is strictly forbidden unless prior written permission is obtained
  from Adobe Systems Incorporated.
--%><%@page import="uk.ac.nhm.nhm_www.core.componentHelpers.CTAButtonHelper"%>
<%
%><%@include file="/apps/nhmwww/components/global.jsp"%><%
%><%@page session="false"
          import="com.day.cq.commons.ImageResource,
                  com.day.cq.wcm.api.WCMMode, com.day.cq.wcm.foundation.Placeholder, javax.jcr.*,
                  uk.ac.nhm.nhm_www.core.componentHelpers.HeaderTextImageHelper, 
                  uk.ac.nhm.nhm_www.core.model.SVGImage"%><%
%>
<cq:defineObjects />
<cq:includeClientLib categories="uk.ac.nhm.headertextimage"/>

<% 	HeaderTextImageHelper helper = new HeaderTextImageHelper(properties, resource, request, xssAPI); %>
<%	String svgIcon = ""; %>
<%	String svgBaseColor = ""; %>

<% if(helper.hasCTAIcon()){
	CTAButtonHelper ctahelper = new CTAButtonHelper(properties, resource, request, xssAPI, cssClassSection.toLowerCase());
	SVGImage svg = ctahelper.getSVGImage(); 
	svgIcon = svg.toHtml(currentDesign.getPath() + "/");
	svgBaseColor = svg.getBaseColour();
} %>


<%	if(helper.isActivated()) {	%>
	<div class="<%=helper.getBackgroundColor() %> text <%=helper.getTextPosition() %>-box <%= helper.getComponentType() %><% if(helper.getImageSize().equals("8")) { %> <%= "large-4" + " medium-4"%> <% } else if(helper.getImageSize().equals("4") && helper.hasImage()) { %> <%= "large-6 medium-6 small-12" %> <% } else { %> <%= "large-12" %> <% } %> columns" data-equalizer-watch>
		<h3>
			<% if(helper.getLinkURL() != null && !helper.getLinkURL().equals("")) { %>
				<a href="<%= xssAPI.getValidHref(helper.getLinkURL()) %>"<%=helper.getNewWindowHtml(helper.isLinkNewWindow()) %>>
			<% } %>
					<%= helper.getHeading() %>
			<% if(helper.getLinkURL() != null && !helper.getLinkURL().equals("")) { %>
				</a>
			<% } %>
		</h3>
		<cq:text property="text" escapeXml="true" placeholder="<%= Placeholder.getDefaultPlaceholder(slingRequest, component, null)%>"/>
	</div>
<%	if(helper.hasImage()) {	%>
		<div class="<%=helper.getImagePosition()%>-box <% if(helper.getImageSize().equals("8")) { %> <%= "large-"+helper.getImageSize() + " medium-"+helper.getImageSize() %> <% } else { %> <%= "large-6 medium-6 small-12 tablet desktop" %> <% } %> columns" data-equalizer-watch>
			<% if(helper.getImageLinkURL() != null && !helper.getImageLinkURL().equals("")) { %>
				<a href="<%= helper.getImageLinkURL() %>"<%=helper.getNewWindowHtml(helper.isImageLinkNewWindow()) %>>
			<% } %>
				<img alt='<%= helper.getAlt() %>' data-interchange="
			        [<%= helper.getPath() + ".img.320.medium." + helper.getExtension() + helper.getSuffix() %>, (default)], 
			        [<%= helper.getPath() + ".img.320.low." + helper.getExtension() + helper.getSuffix() %>, (small)], 
			        [<%= helper.getPath() + ".img.620.high." + helper.getExtension() + helper.getSuffix() %>, (retina)],
			        [<%= helper.getPath() + ".img.480.medium." + helper.getExtension() + helper.getSuffix() %>, (medium)], 
			        [<%= helper.getPath() + ".img.full.high." + helper.getExtension() + helper.getSuffix() %>, (large)]">
			    <%-- Fallback content for non-JS browsers. Same img src as the initial, unqualified source element. --%>
				<noscript>
			        <img src='<%= helper.getPath() + ".img.320.low." + helper.getExtension() + helper.getSuffix() %>' alt='<%= helper.getAlt() %>'>
			    </noscript>
		    <% if(helper.getImageLinkURL() != null && !helper.getImageLinkURL().equals("")) { %>
				</a>
			<% } %>
		</div>
	<% } %>
	<% if(helper.hasCTA()){ %>
		<div class="<%= helper.getCTASectionOverride() %>">
			<div class="info-tout info-tout__action tickets">
				<a class="arrow--large burgandy" href="<%= helper.getCTALink()%>" <%=helper.getCTALinkNewWindow()%> data-gtm="CTA">
					<%=svgIcon %> 
					<h3 class="paddingTB"><%=helper.getCTATitle() %></h3>
					<i class="ico svg-ico arrowl" data-svg-src="/etc/designs/nhmwww/img/svg-icons/icon_l_general_arrow_r.svg" data-svg-title="icon__arrow" data-alt="<%= helper.getCTAIconClass() %>" data-stroke-width="4" data-base-color="<%= svgBaseColor %>"></i>
				</a>
			</div>
		</div>
	<% } %>
<% } else { %>
	<img class="cq-title-placeholder cq-block-lg-placeholder" src="/etc/designs/default/0.gif" />
<% } %>

