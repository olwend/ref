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

<%	if(helper.isExhibition()){
		if(helper.hasCTAIcon()){
			CTAButtonHelper ctahelper = new CTAButtonHelper(properties, resource, request, xssAPI, cssClassSection.toLowerCase());
			SVGImage svg = ctahelper.getSVGImage(); 
			svgIcon = svg.toHtml(currentDesign.getPath() + "/");
			svgBaseColor = svg.getBaseColour();	
		}
}	%>

<%-- Grand Summary --%>
	<div class="grand-summary--wrapper">
		<%-- Main --%>
			<div class="grand-summary--image-wrapper">
				<% helper.getMobileImage().draw(out); %>
				<% helper.getImage().draw(out); %>
				<div class="row">
					<div class="small-12 medium-9 large-8 columns grand-summary--caption-container">
						<div class="caption-outer-wrapper">
							<div class="caption-inner-wrapper">
								<div class="row caption">
									<% if (!helper.isExhibition()) { %>
										<%-- Normal --%>
											<div class="small-12 medium-9 large-9 columns grand-summary--caption-title--container">
												<h2 class="grand-summary--caption-title">
													<% if ( helper.getTitle() != null ) { %> <h1><%=helper.getTitle()%></h1> <% } %>
												</h2>
											</div>
											<div class="small-12 medium-12 large-12 columns end grand-summary--caption-description--container">
												<p>
													<% if ( helper.getDescription() != null ) { %> <p><%=helper.getDescription()%></p> <% } %>
												</p>
											</div>
										<%-- Normal --%>
									<%} else { %>
										<%-- Exhibition --%>
											<div class="small-10 medium-7 large-7 columns grand-summary--caption-title--container">
												<h2 class="grand-summary--caption-title">
													<% if ( helper.getTitle() != null ) { %> <h1><%=helper.getTitle()%></h1> <% } %>
												</h2>
											</div>
											<div class="small-8 medium-5 large-5 columns end grand-summary--caption-date--container">
												<h3 class="grand-summary--caption-date">
													<% if ( helper.getDate() != null ) { %><h2><%= helper.getDate() %></h2><% }%>
												</h3>
											</div>
										<%-- Exhibition --%>
									<% } %>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		<%-- Main --%>
		<%-- Exhibition --%>
		<%-- Exhibition --%>
	</div>
<%-- Grand Summary --%>


	<% if(helper.isExhibition()) { %>
	
		<%-- Description present in Exhibition Mode --%>
		<% if (helper.getDescription() != null) { %> <p><%=helper.getDescription()%></p>
		 
		 <%-- CTA present in Exhibition Mode --%>
		<div class="<%= helper.getCTASectionOverride() %>">
			<div class="info-tout info-tout__action tickets">
				<a class="arrow--large burgandy" href="<%= helper.getCTALink()%>" <%=helper.getCTALinkNewWindow()%> data-gtm="CTA">
					<%=svgIcon %> 
					<h3 class="paddingTB"><%=helper.getCTATitle() %></h3>
					<i class="ico svg-ico arrowl" data-svg-src="/etc/designs/nhmwww/img/svg-icons/icon_l_general_arrow_r.svg" data-svg-title="icon__arrow" data-alt="<%= helper.getCTAIconClass() %>" data-stroke-width="4" data-base-color="<%= svgBaseColor %>"></i>
				</a>
			</div>
		</div>
		
		<%-- Location and Tickets present in Exhibition Mode --%>
		<div class="foundationwrapper">
			<div>
				<p class="global-header-info__item tickets">
					<i class="ico svg-ico" data-svg-src="/etc/designs/nhmwww/img/svg-icons/icon_s_feature_ticket_small.svg" data-svg-title="icon__ticket" data-alt="Tickets" data-base-color="#9D9D9D" data-stroke-width="1" data-fallback="/etc/designs/nhmwww/img/icons/nav-ticket.png"></i> 
					Entry <b><%= helper.getTicketPrice() %></b>
				</p>
			</div>
			<div>
				<p class="global-header-info__item location">
					<i class="ico svg-ico" data-svg-src="/etc/designs/nhmwww/img/svg-icons/icon_s_feature_location_small.svg" data-svg-title="icon__location" data-alt="Location" data-base-color="#9D9D9D" data-stroke-width="1" data-fallback="/etc/designs/nhmwww/img/icons/icon_s_feature_location_small.png"></i> 
					Location <b><%= helper.getLocation() %></b>
				</p>
			</div>
		</div>

		
	<% } %>
<% %>
