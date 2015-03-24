<%--

  Image component.

  This is how to add a single responsive image

--%><%
%><%@include file="/libs/foundation/global.jsp"%><%
%><%@page session="false"
          import="com.day.cq.commons.ImageResource,
                  com.day.cq.wcm.api.WCMMode, com.day.cq.wcm.foundation.Placeholder, javax.jcr.*,
                  uk.ac.nhm.nhm_www.core.componentHelpers.Foundation5ImageHelper"%><%
%>
<cq:defineObjects />
<cq:includeClientLib categories="uk.ac.nhm.foundation5image"/>

<%
	Foundation5ImageHelper helper = new Foundation5ImageHelper(properties, resource, resourceResolver, request, xssAPI);
%>
<% if(helper.isActivated())  { %>
	<% if(helper.getImageLinkURL() != null && !helper.getImageLinkURL().equals("")) { %>
		<a href="<%= helper.getImageLinkURL() %>"<%=helper.getNewWindowHtml()%>>
	<% } %>
	<%--<img alt='<%= helper.getAlt() %>' data-interchange="
	    [<%= helper.getPath(Foundation5ImageHelper.ImageInterchangeSize.DEFAULT) %>, (default)], 
	    [<%= helper.getPath(Foundation5ImageHelper.ImageInterchangeSize.SMALL) %>, (small)],
	    [<%= helper.getPath(Foundation5ImageHelper.ImageInterchangeSize.SMALL_RETINA) %>, (small retina)],  
	    [<%= helper.getPath(Foundation5ImageHelper.ImageInterchangeSize.RETINA) %>, (retina)],
	    [<%= helper.getPath(Foundation5ImageHelper.ImageInterchangeSize.MEDIUM) %>, (medium)], 
	    [<%= helper.getPath(Foundation5ImageHelper.ImageInterchangeSize.LARGE) %>, (large)]">--%>
	    
	    <img alt='<%= helper.getAlt() %>' data-interchange="
	    [<%= helper.getPath(Foundation5ImageHelper.ImageInterchangeSize.DEFAULT) %>, (default)], 
	    [<%= helper.getPath(Foundation5ImageHelper.ImageInterchangeSize.SMALL) %>, (only screen and (max-width: 768px))],
	    [<%= helper.getPath(Foundation5ImageHelper.ImageInterchangeSize.MEDIUM) %>, (only screen and (min-width: 768px))],
	    [<%= helper.getPath(Foundation5ImageHelper.ImageInterchangeSize.LARGE) %>, (only screen and (min-width: 1160px))]">
	<%-- Fallback content for non-JS browsers. --%>
	<noscript>
	    <img src='<%= helper.getPath(Foundation5ImageHelper.ImageInterchangeSize.DEFAULT) %>' alt='<%= helper.getAlt() %>'>
	</noscript>
	<% if(helper.getImageLinkURL() != null && !helper.getImageLinkURL().equals("")) { %>
		</a>
	<% } %>
	<% if(helper.getCaption() != null && !helper.getCaption().equals("")) { %>
		<div class="caption-outer-wrapper">
           	<div class="caption-inner-wrapper">
				<div class="caption"><%= helper.getCaption() %></div>
			</div>
		</div>
	<% } %>
<% } else {  %>
	<img class="cq-title-placeholder cq-block-lg-placeholder"
	src="/etc/designs/default/0.gif" />
<% } %>
