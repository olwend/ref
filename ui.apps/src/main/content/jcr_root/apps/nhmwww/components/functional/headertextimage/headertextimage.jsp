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
--%><%
%><%@include file="/libs/foundation/global.jsp"%><%
%><%@page session="false"
          import="com.day.cq.commons.ImageResource,
                  com.day.cq.wcm.api.WCMMode, com.day.cq.wcm.foundation.Placeholder, javax.jcr.*,
                  uk.ac.nhm.nhm_www.core.componentHelpers.HeaderTextImageHelper"%><%
%>
<cq:defineObjects />
<%
	HeaderTextImageHelper helper = new HeaderTextImageHelper(properties, resource, request, xssAPI);
%>
<cq:includeClientLib categories="uk.ac.nhm.headertextimage"/>

<%
	if(helper.isActivated()) {
		String textPosition = "";
		if(helper.getImagePosition().equals("left")) {
			textPosition = "right";
		} else {
			textPosition = "left";
		}
%>
	<div class="GreyBox text <%=textPosition%>-box <%= helper.getComponentType() %><% if(helper.getImageSize().equals("8")) { %> <%= "large-4" + " medium-4"%> <% } else if(helper.getImageSize().equals("4") && helper.getHasImage()) { %> <%= "large-6 medium-6 small-12" %> <% } else { %> <%= "large-12" %> <% } %> columns" data-equalizer-watch>
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
	<%
	  if(helper.getHasImage()) {
	%>
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
<%
	}  
%>


<%
	} else {
%>
<img class="cq-title-placeholder cq-block-lg-placeholder"
	src="/etc/designs/default/0.gif" />
<%
	}
%>
                
                







