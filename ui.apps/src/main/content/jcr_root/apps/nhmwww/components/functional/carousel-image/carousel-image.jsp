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

<%
	Foundation5ImageHelper helper = new Foundation5ImageHelper(properties, resource, resourceResolver, request, xssAPI);
%>
<% if(helper.isActivated())  { %>
<!-- WR-890: if user has selected "Add bottom margin" in CMS dialog box, wrap entire component with class "mb-20" (margin-bottom 20px) -->
	<%if(helper.getAddMarginBottom()) { %><div class="mb-20"> <% } %>
		<% if(helper.getImageLinkURL() != null && !helper.getImageLinkURL().equals("")) { %>
			<a href="<%= helper.getImageLinkURL() %>"<%=helper.getNewWindowHtml()%>>
		<% } %>
		<!-- WR-1020: replace Interchange with Picture -->
		<picture>
			<source media="(max-width: 480px)" 
					srcset="<%= helper.getPath(Foundation5ImageHelper.ImageInterchangeSize.SMALL) %> 768w" />
            <source media="(min-width: 481px) and (max-width: 768px)"
					srcset="<%= helper.getPath(Foundation5ImageHelper.ImageInterchangeSize.MEDIUM) %> 753w" />
            <source media="(min-width: 769px) and (max-width: 1024px)"
					srcset="<%= helper.getPath(Foundation5ImageHelper.ImageInterchangeSize.MEDIUM) %> 1024w" />
            <source media="(min-width: 1025px)"
					srcset="<%= helper.getPath(Foundation5ImageHelper.ImageInterchangeSize.LARGE) %> 100w" />
			<img class="js--carousel-image" src="<%= helper.getPath(Foundation5ImageHelper.ImageInterchangeSize.DEFAULT) %>" alt="<%= helper.getAlt() %>" />
		</picture>
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
