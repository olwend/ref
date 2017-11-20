<%@include file="/libs/foundation/global.jsp"%>
<%@page session="false"
          import="uk.ac.nhm.core.componentHelpers.AccordionHelper"%>
<cq:defineObjects />
<%
	AccordionHelper helper = new AccordionHelper(properties);
%>
<% if(!helper.isInitialised()) { %>
<div class="row">
		<h4>Accordion</h4>
		Required fields:
		<ul>
			<li>Panel title</li>
		</ul>
	</div>
	<%} else { %>
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

<% } %>

