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
		<picture>
			<source media="(max-width: 480px)" 
					srcset="<%= helper.getPath(DiscoverRelatedImageHelper.ImageInterchangeSize.SMALL) %> 320w" />
			<source media="(min-width: 481px) and (max-width: 1024px)"
					srcset="<%= helper.getPath(DiscoverRelatedImageHelper.ImageInterchangeSize.MEDIUM) %> 1024w" />
			<source media="(min-width: 1025px)"
					srcset="<%= helper.getPath(DiscoverRelatedImageHelper.ImageInterchangeSize.SMALL) %> 320w" />
			<img src="<%= helper.getPath(DiscoverRelatedImageHelper.ImageInterchangeSize.DEFAULT) %>" alt="<%= helper.getAlt() %>" />
		</picture>
	
	<% if(helper.getImageLinkURL() != null && !helper.getImageLinkURL().equals("")) { %>
		</a>
	<% } %>
<% } else {  %>
	<img class="cq-title-placeholder cq-block-lg-placeholder" src="/etc/designs/default/0.gif" />
<% } %>
