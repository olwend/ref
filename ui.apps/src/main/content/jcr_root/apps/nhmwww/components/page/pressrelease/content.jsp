<%@page session="false"
          import="com.day.cq.wcm.api.Page"%>
<%@page import="uk.ac.nhm.nhm_www.core.componentHelpers.PressReleaseHelper" %>
<%@page import="java.text.SimpleDateFormat" %>
<%@include file="/apps/nhmwww/components/global.jsp"%>
<cq:defineObjects />
<%
	PressReleaseHelper helper = new PressReleaseHelper(resource,properties);

%>
<div class="main-section">
<% if ( !helper.getIsComponentInitialised()) {%>
<h1>Page not initialised</h1>
<em>Please set the required page properties.</em>
<% } else { %>
			<div class="title">
				<div class="row title-bar">
					<div class="small-12 columns">
						<h1>yada yada</h1>
						<p><%=helper.getFormattedPublishDate() %></p>
					</div>
				</div>
			</div>
			
			<div class="par parsys"><div class="rowfullwidth section">
				<div class="row " data-equalizer >
					<div class="large-12 columns">
						<div class="parsys par2"><div class="adaptiveimage parbase foundation5image image section">
							<cq:include path="image" resourceType="nhmwww/components/functional/foundation5image" />
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="section row2cells21">
	<div class="row" data-equalizer >
		<div class="large-8 medium-8 columns" data-equalizer-watch>
		
				<cq:include path="par" resourceType="foundation/components/parsys"/>

	<div>

			
			</div>

			<!-- START SPONSORS BLOCK GRID -->
			<div class="sponsors-grid">	
				<ul class="small-block-grid-2 medium-block-grid-4">
					<li><img src="http://www.nhm.ac.uk/content/dam/nhmwww/visit/Exhibitions/WPY2014/DONG-energy-logo.jpg" /></li>
					<li><img src="http://www.nhm.ac.uk/content/dam/nhmwww/visit/Exhibitions/Coral-reefs-exhibition/catlin-100-percent.jpg" /></li>
					<li><img src="http://www.nhm.ac.uk/content/dam/nhmwww/visit/Exhibitions/WPY2014/DONG-energy-logo.jpg" /></li>
					<li><img src="http://www.nhm.ac.uk/content/dam/nhmwww/visit/Exhibitions/Coral-reefs-exhibition/catlin-100-percent.jpg" /></li>
				</ul>
			</div>
			<!-- END SPONSORS BLOCK GRID -->
		</div>

		<div class="large-4 medium-4 columns" data-equalizer-watch>
			<div class="parsys par2">
				<div class="parbase headertextimage section">
					<link rel="stylesheet" href="http://www.nhm.ac.uk/etc/clientlibs/nhmwww/headertextimage.min.css" type="text/css">
					<script type="text/javascript" src="http://www.nhm.ac.uk/etc/clientlibs/nhmwww/headertextimage.min.js"></script>
					 <!-- START PRESS ROOM CONTACT BOX -->
					 <div class="GreyBox text left-box feature-box press-room--contact-us large-12 columns">
						<h3>Contact us</h3>
						<p><strong>Weekdays:</strong> +44 (0)20 7942 5854</p>
						<p><strong>Evenings and weekends:</strong> +44 (0) 7799 690 151</p>
						<p><strong>Email:</strong> <a href="mailto:">press@nhm.ac.uk</a></p> 
					</div>
					<!-- END PRESS ROOM CONTACT BOX -->
				</div>

				<!-- START SIDE NAV -->
				<div class="parbase headertextimage section">
					<div class="press-room--side-nav">
						<ul class="side-nav">
							<li class="selected"><a href="#">Link 1</a></li>
							<li><a href="#">Link 2</a></li>
							<li><a href="#">Link 3</a></li>
							<li><a href="#">Link 4</a></li>
						</ul>
					</div>
				</div>
				<!-- END SIDE NAV -->

				<div class="parbase headertextimage section">
					<link rel="stylesheet" href="http://www.nhm.ac.uk/etc/clientlibs/nhmwww/headertextimage.min.css" type="text/css">
					<script type="text/javascript" src="http://www.nhm.ac.uk/etc/clientlibs/nhmwww/headertextimage.min.js"></script>
					<div class="GreyBox text left-box aside-box large-12 columns">
						<h3>Press passes</h3>
						<p>Press passes: If you are reviewing and exhibition or event we can put aside a press ticket for you in advance.</p>
						<p>Gallery staff cannot accept generic media passes, staff passes or business cards.</p>
					</div>
				</div>
			</div>


		</div>
	</div>
</div>
<% } %>