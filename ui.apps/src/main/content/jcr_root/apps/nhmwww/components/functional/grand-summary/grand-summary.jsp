<%@page session="false"%>
<%@include file="/apps/nhmwww/components/global.jsp"%>
<%@page import="uk.ac.nhm.nhm_www.core.model.SVGImage" %>
<%@page import="uk.ac.nhm.nhm_www.core.componentHelpers.GrandSummaryHelper"%>
<%@page import="uk.ac.nhm.nhm_www.core.componentHelpers.CTAButtonHelper"%>
<cq:defineObjects />
<cq:includeClientLib />
<%	GrandSummaryHelper helper = new GrandSummaryHelper(slingRequest, currentPage, properties); %>
<%	String svgIcon = ""; %>
<%	String svgBaseColor = ""; %>

<%	if(helper.hasCTA()){
		if(helper.hasCTAIcon()){
			CTAButtonHelper ctahelper = new CTAButtonHelper(properties, resource, request, xssAPI, cssClassSection.toLowerCase());
			SVGImage svg = ctahelper.getSVGImage(); 
			svgIcon = svg.toHtml(currentDesign.getPath() + "/");
			svgBaseColor = svg.getBaseColour();	
		}
}	%>

	<div class="video-wrapper" >
		<% helper.getMobileImage().draw(out); %>
		<% helper.getImage().draw(out); %>
	
		<div class="caption-outer-wrapper">
			<div class="caption-inner-wrapper">
				<div class="caption">
					<% if (helper.getTitle() != null) { %> <h1><%=helper.getTitle()%></h1> <% } %>
					
					<% if (helper.getDescription() != null) { %> <p><%=helper.getDescription()%></p> <% } %>
				</div>
			</div>
		</div>
	</div>
	<% if(helper.hasCTA()){ %>
		<div class="<%= helper.getCTASectionOverride() %>">
			<div class="info-tout info-tout__action tickets">
				<a class="arrow--large burgandy" href="<%= helper.getCTALink()%>" <%=helper.getCTALinkNewWindow()%> data-gtm="CTA">
					<%=svgIcon %> 
					<h3 class="paddingTB"><%=helper.getCTATitle() %></h3>
					<i class="ico svg-ico arrowl" data-svg-src="/etc/designs/nhmwww/img/svg-icons/icon_l_general_arrow_r.svg" data-svg-title="icon__arrow" data-alt="<%= helper.getCTAIconClass() %>" data-stroke-width="4" data-base-color="<%= svgBaseColor %>"></i>
				</a>
			</div>
		</div>
	<% } %>
<% %>
