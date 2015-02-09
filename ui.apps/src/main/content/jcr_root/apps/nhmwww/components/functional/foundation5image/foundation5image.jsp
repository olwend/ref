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
	Foundation5ImageHelper helper = new Foundation5ImageHelper(properties, resource, request, xssAPI);
%>
<% if(helper.isActivated())  { %>
	<% if(helper.getImageLinkURL() != null && !helper.getImageLinkURL().equals("")) { %>
		<a href="<%= helper.getImageLinkURL() %>"<%=helper.getNewWindowHtml()%>>
	<% } %>
	<img alt='<%= helper.getAlt() %>' data-interchange="
	    [<%= helper.getPath() + ".img.full.medium." + helper.getExtension() + helper.getSuffix() %>, (default)], 
	    [<%= helper.getPath() + ".img.full.low." + helper.getExtension() + helper.getSuffix() %>, (small)],  
	    [<%= helper.getOrigianlImagePath() %>, (retina)],
	    [<%= helper.getPath() + ".img.full.medium." + helper.getExtension() + helper.getSuffix() %>, (medium)], 
	    [<%= helper.getOrigianlImagePath() %>, (large)]">
	<%-- Fallback content for non-JS browsers. Same img src as the initial, unqualified source element. --%>
	<noscript>
	    <img src='<%= helper.getPath() + ".img.full.medium." + helper.getExtension() + helper.getSuffix() %>' alt='<%= helper.getAlt() %>'>
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
