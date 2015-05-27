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

<% 	if(helper.isExhibition()){
		if(helper.hasCTAIcon()){
			CTAButtonHelper ctahelper = new CTAButtonHelper(properties, resource, request, xssAPI, cssClassSection.toLowerCase());
			SVGImage svg = ctahelper.getSVGImage(); 
			//Changing colour from inherit section to hardcoded grey #565656.
			svg.setBaseColour("#565656");
			svgIcon = svg.toHtml(currentDesign.getPath() + "/");
			svgBaseColor = svg.getBaseColour();		
		}
	} %>

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
													<% if ( helper.getTitle() != null ) { %> <%=helper.getTitle()%> <% } %>
												</h2>
											</div>
											<div class="small-12 medium-12 large-12 columns end grand-summary--caption-description--container">
												<p>
													<% if ( helper.getDescription() != null ) { %> <%=helper.getDescription()%> <% } %>
												</p>
											</div>
										<%-- Normal --%> 
									<%} else { %>
										<%-- Exhibition --%>
											<div class="small-10 medium-7 large-7 columns grand-summary--exhibition grand-summary--caption-title--container">
												<h2 class="grand-summary--caption-title">
													<% if ( helper.getTitle() != null ) { %> <%=helper.getTitle()%> <% } %>
												</h2>
											</div>
											<div class="small-8 medium-5 large-5 columns end grand-summary--exhibition grand-summary--caption-date--container">
												<h3 class="grand-summary--caption-date">
													<% if ( helper.getDate() != null ) { %> <%=helper.getDate()%> <% }%>
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
		<% if(helper.isExhibition()) { %>
			<div class="row">
				<div class="small-12 medium-12 large-12 columns">
					<ul class="small-block-grid-1 medium-block-grid-3 large-block-grid-3 columns grand-summary--block-grid">
						<% if (helper.getDescription() != null) { %> <li><%=helper.getDescription()%></li><% } %>
						<%-- CTA --%>
							<li>
								<div class="small-12 medium-12 large-12 columns ctabutton">
									<a href="<%= helper.getCTALink() %>" <%=helper.getCTALinkNewWindow()%> data-gtm="CTA">
										<div class="small-2 medium-2 large-2 columns">
											<%=svgIcon %>
										</div>
										<div class="small-7 medium-9 large-9 columns">
											<h3 class="ctabutton--cta-text"><%=helper.getCTATitle() %></h3>
										</div>
										<div class="small-3 medium-1 large-1 columns">
											<i class="ico svg-ico arrowl" data-svg-src="/etc/designs/nhmwww/img/svg-icons/icon_l_general_arrow_r.svg" 
												data-svg-title="icon__arrow" data-alt="<%= helper.getCTAIconClass() %>" 
												data-stroke-width="4" data-base-color="<%= svgBaseColor %>"></i>
										</div>
									</a>
								</div>
							</li>
						<%-- CTA --%>
						<%-- SideBar --%>
							<li>
								<ul class="info-sidebar">
									<li>
										<i class="ico svg-ico" data-svg-src="/etc/designs/nhmwww/img/svg-icons/icon_l_feature_ticket.svg" 
										data-svg-title="icon__ticket" data-alt="<%= helper.getCTAIconClass() %>" 
										data-stroke-width="4" data-base-color="<%= svgBaseColor %>"></i> 
										Entry 
										<strong>
											<%= helper.getTicketPrice() %>
										</strong>
									</li>
									<li>
										<i class="ico svg-ico" data-svg-src="/etc/designs/nhmwww/img/svg-icons/icon_l_feature_location.svg" 
										data-svg-title="icon__location" data-alt="<%= helper.getCTAIconClass() %>" 
										data-stroke-width="4" data-base-color="<%= svgBaseColor %>"></i>
										Location 
										<strong>
											<%= helper.getLocation() %>
										</strong>
									</li>
								</ul>
							</li>
						<%-- SideBar --%>
					</ul>
				</div>
			</div>
		<% } %>
		<%-- Exhibition --%>
	</div>
<%-- Grand Summary --%>