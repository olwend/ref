<%--

  Secondary Nav component.

  This will create a section specific secondary Nav

--%><%@page import="uk.ac.nhm.nhm_www.core.componentHelpers.SecondaryNavHelper"%>
<%@page import="uk.ac.nhm.nhm_www.core.model.ListElement"%>
<%@page import="uk.ac.nhm.nhm_www.core.componentHelpers.ListHelper,
				uk.ac.nhm.nhm_www.core.utils.PageUtils"%>
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
				<div class="parbase headertextimage section">
					<div class="press-room--side-nav">
						<ul class="side-nav">
							<li><a href="<%= helper.getSectionLandingPage().getPath() %>"><%= helper.getSectionLandingPage().getTitle() %> home</a></li>
							<%= helper.getChildrenElements().size() %>
							
							
							<%for (Object navElement: helper.getChildrenElements()) { 
								ListElement processingElement = (ListElement) navElement;
								String currentPageCssClass = "";
								/*if(currentPage.getPath().equals(processingElement.getElementLink())){
									currentPageCssClass = "class=\"current\"";
								}*/
							%>
							
								<li><a <%= //currentPageCssClass %> href="<%= processingElement.getElementLink()%>"><%= processingElement.getTitle() %></a></li>								
							<% } %>
						</ul>
					</div>
				</div>
<!-- END SIDE NAV -->
<% } else { %>
	<p> This component has not been Initialised </p>
<% } %>
