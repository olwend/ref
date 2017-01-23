<%--

  Image component.

  This is how to add a single responsive image

--%><%
%><%@include file="/libs/foundation/global.jsp"%><%
%><%@page session="false"
          import="com.day.cq.commons.ImageResource,
                  com.day.cq.wcm.api.WCMMode, com.day.cq.wcm.foundation.Placeholder, javax.jcr.*,
                  uk.ac.nhm.nhm_www.core.componentHelpers.DiscoverRelatedImageHelper"%><%
%>
<cq:defineObjects />
<cq:includeClientLib categories="uk.ac.nhm.foundation5image"/>

<%
	DiscoverRelatedImageHelper helper = new DiscoverRelatedImageHelper(properties, resource, resourceResolver, request, xssAPI);
%>
<% if(helper.isActivated())  { %>
<!-- WR-890: if user has selected "Add bottom margin" in CMS dialog box, wrap entire component with class "mb-20" (margin-bottom 20px) -->
	<%if(helper.getAddMarginBottom()) { %><div class="mb-20"> <% } %>
		<% if(helper.getImageLinkURL() != null && !helper.getImageLinkURL().equals("")) { %>
			<a href="<%= helper.getImageLinkURL() %>"<%=helper.getNewWindowHtml()%>>
		<% } %>
		    <img alt='<%= helper.getAlt() %>' data-interchange="
		    [<%= helper.getPath(DiscoverRelatedImageHelper.ImageInterchangeSize.DEFAULT) %>, (default)], 
		    [<%= helper.getPath(DiscoverRelatedImageHelper.ImageInterchangeSize.SMALL) %>, (only screen and (max-width: 768px))],
		    [<%= helper.getPath(DiscoverRelatedImageHelper.ImageInterchangeSize.MEDIUM) %>, (only screen and (max-width: 768px) and (orientation: landscape))],
		    [<%= helper.getPath(DiscoverRelatedImageHelper.ImageInterchangeSize.MEDIUM) %>, (only screen and (min-width: 768px))],
		    [<%= helper.getPath(DiscoverRelatedImageHelper.ImageInterchangeSize.LARGE) %>, (only screen and (min-width: 1160px))]">
		<%-- Fallback content for non-JS browsers. --%>
		<noscript>
		    <img src='<%= helper.getPath(DiscoverRelatedImageHelper.ImageInterchangeSize.DEFAULT) %>' alt='<%= helper.getAlt() %>'>
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
	<%if(helper.getAddMarginBottom()) { %></div> <% } %>
<% } else {  %>
	<img class="cq-title-placeholder cq-block-lg-placeholder"
	src="/etc/designs/default/0.gif" />
<% } %>
