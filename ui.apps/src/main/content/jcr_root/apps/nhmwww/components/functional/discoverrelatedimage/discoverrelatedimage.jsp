<%@include file="/libs/foundation/global.jsp"%>
<%@page session="false"
        import="com.day.cq.commons.ImageResource,
                com.day.cq.wcm.api.WCMMode, com.day.cq.wcm.foundation.Placeholder, javax.jcr.*,
                uk.ac.nhm.core.componentHelpers.DiscoverRelatedImageHelper"%>
<cq:defineObjects />
<%
	DiscoverRelatedImageHelper helper = new DiscoverRelatedImageHelper(properties, resource, resourceResolver, request, xssAPI);
%>
<% if(helper.isActivated()) { %>
	<% if(helper.getImageLinkURL() != null && !helper.getImageLinkURL().equals("")) { %>
		<a href="<%= helper.getImageLinkURL() %>"<%=helper.getNewWindowHtml()%>>
	<% } %>
	    <img alt='<%= helper.getAlt() %>' data-interchange="
	    	[<%= helper.getPath(DiscoverRelatedImageHelper.ImageInterchangeSize.DEFAULT) %>, (default)],
		    [<%= helper.getPath(DiscoverRelatedImageHelper.ImageInterchangeSize.MEDIUM) %>, (only screen and (max-width: 480px))],
		    [<%= helper.getPath(DiscoverRelatedImageHelper.ImageInterchangeSize.LARGE) %>, (only screen and (min-width: 481px) and (max-width: 640px))],
		    [<%= helper.getPath(DiscoverRelatedImageHelper.ImageInterchangeSize.MEDIUM) %>, (only screen and (min-width: 641px) and (max-width: 1024px))],
		    [<%= helper.getPath(DiscoverRelatedImageHelper.ImageInterchangeSize.SMALL) %>, (only screen and (min-width: 1025px)],">
	<%-- Fallback content for non-JS browsers. --%>
	<noscript>
	    <img src='<%= helper.getPath(DiscoverRelatedImageHelper.ImageInterchangeSize.DEFAULT) %>' alt='<%= helper.getAlt() %>'>
	</noscript>
	<% if(helper.getImageLinkURL() != null && !helper.getImageLinkURL().equals("")) { %>
		</a>
	<% } %>
<% } else {  %>
	<img class="cq-title-placeholder cq-block-lg-placeholder" src="/etc/designs/default/0.gif" />
<% } %>
