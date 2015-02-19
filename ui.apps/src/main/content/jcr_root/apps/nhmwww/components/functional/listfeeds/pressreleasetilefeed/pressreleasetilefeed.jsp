<%--

  List Feed component.

  Will list the items under a specified root

--%><%@page import="uk.ac.nhm.nhm_www.core.model.PressReleaseFeedListElement"%>
<%@page import="uk.ac.nhm.nhm_www.core.componentHelpers.PressReleaseFeedListHelper,
				uk.ac.nhm.nhm_www.core.utils.*"%>
<%
%>
<%@include file="/apps/nhmwww/components/global.jsp"%> 
<%@page session="false" %>
<cq:defineObjects />
<%
%><%
	PressReleaseFeedListHelper helper = new PressReleaseFeedListHelper(properties, pageManager, currentPage, request, resourceResolver);
%>
<% if(helper.isInitialised()) { %>

	<%if (helper.getComponentTitle() != null) {%><h3><%if (helper.getHyperLink() != null) {%><a href="<%=helper.getHyperLink() %>"<%=helper.getNewwindow()%>><%}%><%=helper.getComponentTitle() %><%if (helper.getHyperLink() != null) {%></a><%}%></h3> <%}%>
	<div>
	<!-- START FEED BLOCK GRID -->
	<ul class="small-block-grid-1 medium-block-grid-2 press-room--feed">
		<% for(Object element: helper.getTilesElements()) { 
				PressReleaseFeedListElement prElement = (PressReleaseFeedListElement) element;
		
		%>
				<li>
					<div class="press-room--list-item" data-equalizer>
						<div class="small-12 columns press-room--list-item--content-wrapper">
							<div class="small-12 columns" data-equalizer-watch>
								<a href="<%= LinkUtils.getFormattedLink(prElement.getElementLink()) %> ">
									<cq:include path="<%= prElement.getImagePath() %>" resourceType="nhmwww/components/functional/foundation5image" />
								</a>
								<div class="small-12 columns press-room--list-item--caption"><%= PageUtils.getFormattedPublishDate(prElement.getPressReleaseDate()) %></div>
							</div>
							<div class="small-12 columns">
								<div class="press-room--list-item--content">
									<h4 class="press-room--list-item--title"><a href="<%= LinkUtils.getFormattedLink(prElement.getElementLink()) %>"><%= prElement.getTitle() %></a></h4>
									<p class="press-room--list-item--tagline"><%= prElement.getIntro() %></p>
								</div>
							</div>
						</div>
					</div>
					</li>
					
		<% } %>
		</ul>			
					
	<!-- 			
	
		<li>
			<div class="press-room--list-item" data-equalizer>
				<div class="small-12 columns press-room--list-item--content-wrapper">
					<div class="small-12 columns" data-equalizer-watch>
						<div class="adaptiveimage parbase foundation5image image image_0">
							<a href="http://www.nhm.ac.uk/education/microverse/index.html">
								<img alt='A&#x20;person&#x20;swabbing&#x20;microorganisms&#x20;on&#x20;the&#x20;wall&#x20;of&#x20;the&#x20;Tower&#x20;of&#x20;London&#x20;for&#x20;the&#x20;Microverse&#x20;project' data-interchange="
								[http://www.nhm.ac.uk/content/nhmwww/en/home/take-part/citizen-science/jcr:content/par/rowfullwidth_0/par2/nhm_carousel/image_0.img.full.medium.jpg/1421153794547.jpg, (default)], 
								[http://www.nhm.ac.uk/content/nhmwww/en/home/take-part/citizen-science/jcr:content/par/rowfullwidth_0/par2/nhm_carousel/image_0.img.full.low.jpg/1421153794547.jpg, (small)],  
								[http://www.nhm.ac.uk/content/dam/nhmwww/take-part/Citizenscience/microverse-DSC_0328-374x180.jpg, (retina)],
								[http://www.nhm.ac.uk/content/nhmwww/en/home/take-part/citizen-science/jcr:content/par/rowfullwidth_0/par2/nhm_carousel/image_0.img.full.medium.jpg/1421153794547.jpg, (medium)], 
								[http://www.nhm.ac.uk/content/dam/nhmwww/take-part/Citizenscience/microverse-DSC_0328-374x180.jpg, (large)]">
								<noscript>
									<img src='http://www.nhm.ac.uk/content/nhmwww/en/home/take-part/citizen-science/_jcr_content/par/rowfullwidth_0/par2/nhm_carousel/image_0.img.full.medium.jpg/1421153794547.jpg' alt='A&#x20;person&#x20;swabbing&#x20;microorganisms&#x20;on&#x20;the&#x20;wall&#x20;of&#x20;the&#x20;Tower&#x20;of&#x20;London&#x20;for&#x20;the&#x20;Microverse&#x20;project'>
								</noscript>
							</a>
							<div class="small-12 columns press-room--list-item--caption">February 20, 2014</div>
						</div>
					</div>
					<div class="small-12 columns">
						<div class="press-room--list-item--content">
							<h4 class="press-room--list-item--title"><a href="/education/microverse/index.html">The Microverse</a></h4>
							<p class="press-room--list-item--tagline">Discover microscopic life in urban environments using DNA technologies. For secondary schools.</p>
						</div>
					</div>
				</div>
			</div>
		</li> -->
	
		
		<!-- END FEED BLOCK GRID -->
	</div>
	<% } else { %>
<p> The component has not been initialised</p>
<% } %>	
