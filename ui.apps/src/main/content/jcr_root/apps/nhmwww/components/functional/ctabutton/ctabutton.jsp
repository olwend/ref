<%@include file="/apps/nhmwww/components/global.jsp"%>
<%@page import="uk.ac.nhm.core.componentHelpers.CTAButtonHelper" %>
<%@page import="uk.ac.nhm.core.model.SVGImage" %>
<cq:defineObjects />
<cq:includeClientLib categories="uk.ac.nhm.ctabutton"/>

<% CTAButtonHelper helper = new CTAButtonHelper(properties, resource, request, xssAPI, cssClassSection.toLowerCase()); %>
<% if(!helper.isInitialised())  {%>
	<div class="row">
		<h4>CTA button</h4>
		Required fields:
		<ul>
			<li>CTA label or</li>
			<li>Text</li>
		</ul>
	</div>
<% } else { %>
	<% if (isOnEditMode || isOnDesignMode) {%>
		<div class="row">
	<%}%>
	<% if(helper.getSectionOverride() != null && !helper.getSectionOverride().equals("")) { %>
		<div class="<%= helper.getSectionOverride() %>">
	<% } %>
	<%
	SVGImage svg = helper.getSVGImage();
	if(helper.getIsFullWidthCTA()) {%>
	<div class="large-8 medium-6 columns large-left-section"><!-- text-->
		<% if(helper.getText() != null && !helper.getText().equals("")) { %>
			<h2><%= helper.getText() %></h2>
		<% } %>
		</div><!-- end text-->
		<div class="large-4 medium-6 columns"> <!-- start button-->
			<div class="info-tout info-tout__action tickets <%= helper.getIconClass() %>">
				<a class="arrow--large burgandy" href="<%= helper.getLinkURL() %>"<%=helper.getNewWindowHtml()%> data-gtm="CTA">
					<%=svg.toHtml(currentDesign.getPath()+ "/") %>
					<h3 class="paddingTB"><%=helper.getHeading() %></h3>
					<i class="ico svg-ico arrowl" data-svg-src="/etc/designs/nhmwww/img/svg-icons/icon_l_general_arrow_r.svg" data-svg-title="icon__arrow" data-alt="<%= helper.getIconClass() %>" data-stroke-width="4" data-base-color="<%= svg.getBaseColour()%>"></i>
				</a>
			</div>
		</div><!-- End button-->
	<%} else if (helper.isHasImage()) { %>
		<div class="home-thumb-right">
			<h3><%= helper.getTitle() %></h3>
			<a href="<%= helper.getLinkURL() %>"<%=helper.getNewWindowHtml()%> data-gtm="CTA">
				<img data-interchange="
					[<%= helper.getPath() + ".img.320.medium." + helper.getExtension() + helper.getSuffix() %>, (default)],
					[<%= helper.getPath() + ".img.320.low." + helper.getExtension() + helper.getSuffix() %>, (small)],
					[<%= helper.getPath() + ".img.620.high." + helper.getExtension() + helper.getSuffix() %>, (retina)],
					[<%= helper.getPath() + ".img.480.medium." + helper.getExtension() + helper.getSuffix() %>, (medium)],
					[<%= helper.getPath() + ".img.full.high." + helper.getExtension() + helper.getSuffix() %>, (large)]">
				<%-- Fallback content for non-JS browsers. Same img src as the initial, unqualified source element. --%>
				<noscript>
					<img src='<%= helper.getPath() + ".img.320.low." + helper.getExtension() + helper.getSuffix() %>' alt='<%= helper.getAlt() %>'>
				</noscript>
			</a>
			<% if (helper.getHasText()) {  // Only show the CTA if there is no caption set%>
				<div class="thumb-caption <% if(helper.getSectionOverride() != null && !helper.getSectionOverride().equals("")) { %> <%= helper.getSectionOverride() %> <% } %>">
					<a href="<%= helper.getLinkURL() %>"<%=helper.getNewWindowHtml()%> data-gtm="CTA">
						<p><%= helper.getText() %></p>
					</a>
				</div>
			<% } else {%>
				<div class="thumb-cta info-tout info-tout__action tickets">
					<a class="arrow--large" href="#" data-gtm="CTA">
						<%=svg.toHtml(currentDesign.getPath()+ "/") %>
							<h3 class="paddingTB"><%= helper.getHeading() %></h3>
							<i class="ico svg-ico arrowl" data-svg-src="/etc/designs/nhmwww/img/svg-icons/icon_l_general_arrow_r.svg" data-svg-title="icon__arrow" data-alt="<%= helper.getIconClass() %>" data-stroke-width="4" data-base-color="<%= svg.getBaseColour() %>"></i>
					</a>
				</div>
			<% } %>
		</div>
	<%} else {%>
		<!--  no image -->
		<div class="info-tout info-tout__action tickets">
		<!-- helper.getIconClass() removed from above div and hard coded as "tickets" because CSS is coded to use this class for formatting and using another class will break it; see the SVG above for a similar situation -->
			<a class="arrow--large burgandy" href="<%= helper.getLinkURL() %>"<%=helper.getNewWindowHtml()%> data-gtm="CTA">
				<%=svg.toHtml(currentDesign.getPath()+ "/") %>
				<h3 class="paddingTB"><%=helper.getHeading() %></h3>
				<%-- helper.getText()--%>
				<i class="ico svg-ico arrowl" data-svg-src="/etc/designs/nhmwww/img/svg-icons/icon_l_general_arrow_r.svg" data-svg-title="icon__arrow" data-alt="<%= helper.getIconClass() %>" data-stroke-width="4" data-base-color="<%= svg.getBaseColour() %>"></i>
			</a>
		</div>
	<% } %>
	<% if(helper.getSectionOverride() != null && !helper.getSectionOverride().equals("")) { %>
		</div>
	<% } %>
	<% if (isOnEditMode || isOnDesignMode) {%>
		</div>
	<% } %>
<%} %>