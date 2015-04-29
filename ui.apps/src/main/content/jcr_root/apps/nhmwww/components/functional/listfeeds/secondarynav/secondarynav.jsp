<%--

  Secondary Nav component.

  This will create a section specific secondary Nav

--%><%@page import="uk.ac.nhm.nhm_www.core.componentHelpers.SecondaryNavHelper"%>
<%@page import="uk.ac.nhm.nhm_www.core.model.ListElement"%>
<%@page import="uk.ac.nhm.nhm_www.core.componentHelpers.ListHelper,
				uk.ac.nhm.nhm_www.core.utils.*"%>
<%
%>
<%@include file="/apps/nhmwww/components/global.jsp"%> 
<%@page session="false" %>
<cq:defineObjects />

<% 
	SecondaryNavHelper helper = new SecondaryNavHelper(properties, pageManager, currentPage, request, resourceResolver);
%>

<% if(helper.isInitialised()) {%>
<!-- START SIDE NAV -->
				<div class="parbase section">
					<div class="secondary-nav-wrapper">
						<% if(helper.getComponentTitle() != null && !helper.getComponentTitle().equals("")) { %>
							<p><%= helper.getComponentTitle() %></p>
						<% }
						
						String currentPageCssClass = "";
						if(currentPage.getPath().equals(helper.getSectionLandingPage().getPath())){
							currentPageCssClass = " class=\"selected\" ";
						}
						%>
						<ul class="side-nav">
							<li<%= currentPageCssClass %>><a href="<%= LinkUtils.getFormattedLink(helper.getSectionLandingPage().getPath()) %>"><%= helper.getSectionLandingPage().getTitle() %> home</a></li>
							<%for (Object navElement: helper.getChildrenElements()) { 
								ListElement processingElement = (ListElement) navElement;
								currentPageCssClass = "";
								if(currentPage.getPath().equals(processingElement.getElementLink())){
									currentPageCssClass = " class=\"selected\" ";
								}
							%>
							
								<li<%= currentPageCssClass %>><a href="<%= LinkUtils.getFormattedLink(processingElement.getElementLink())%>"><%= processingElement.getTitle() %></a></li>								
							<% } %>
						</ul>
					</div>
				</div>
<!-- END SIDE NAV -->
<% } else { %>
	<p> This component has not been Initialised </p>
<% } %>
