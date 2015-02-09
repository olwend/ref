<%--

  Accordeon component.

  This adds an accordeon to a page

--%>
<%
%><%@include file="/libs/foundation/global.jsp"%>
<%@page session="false"
          import="uk.ac.nhm.nhm_www.core.componentHelpers.AccordeonHelper"%>
<cq:defineObjects />
<%
	AccordeonHelper helper = new AccordeonHelper(properties);
%>
<% if(helper.isInitialised()) { %>
<%  String headingStyleOpen = "";
	String headingStyleClose = "";
	if(helper.getHeadingStyle() != null && !helper.getHeadingStyle().equals("")) {  
		headingStyleOpen = "<" + helper.getHeadingStyle() + ">";
		headingStyleClose= "</" + helper.getHeadingStyle() + ">";
	
	} %> 
<dl class="accordion" data-accordion>
	<dd class="accordion-navigation <% if(helper.isOpen()) { %>active <% } %>">
		<a href="#<%= xssAPI.getValidHref(helper.getPanelId()) %>"><%= headingStyleOpen %><%= xssAPI.encodeForHTML(helper.getPanelTitle()) %><%= headingStyleClose %></a>
		<div id="<%= xssAPI.getValidHref(helper.getPanelId())%>" class="content <% if(helper.isOpen()) { %>active <% } %>">
			<cq:include path="par" resourceType="foundation/components/parsys" />
		</div>
	</dd>
</dl>
<% } else {	%>
	<div>
		<p>This component is not configured properly.</p>
	</div>
<% } %>

