<%--

  List Feed component.

  Will list the items under a specified root

--%><%
%><%@include file="/apps/nhmwww/components/global.jsp"%><%
%><%@page session="false" %><%
%><%
	// TODO add you code here
%>
<cq:includeClientLib categories="nhm-www.pressreleaseslist"/>
<div class="pressreleaselistfeed-wrapper" data-rootpath="<%= currentPage.getPath() %>" data-pagesize="6" >
	<!-- START PAGINATION -->
	<div class="pagination-centered">
	</div>
	<!-- END PAGINATION -->
	<div class="press-room--list">
	</div>
	<!--  START PRESS ROOM LIST ITEM->
	<div class="press-room--list-item" data-equalizer>
		<div class="small-12 columns press-room--list-item--caption">February 20, 2014</div>
		<div class="small-12 columns press-room--list-item--content-wrapper">
			<div class="small-12 medium-6 columns" data-equalizer-watch>
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
				</div>
			</div>
			<div class="small-12 medium-6 columns">
				<div class="press-room--list-item--content" data-equalizer-watch>
					<h4 class="press-room--list-item--title"><a href="/education/microverse/index.html">The Microverse</a></h4>
					<p class="press-room--list-item--tagline">Discover microscopic life in urban environments using DNA technologies. For secondary schools.</p>
				</div>
			</div>
		</div>
	</div>
	< END PRESS ROOM LIST ITEM -->
</div>