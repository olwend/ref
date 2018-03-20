
<%@include file="/apps/nhmwww/components/global.jsp"%>
<%@page session="false" import="uk.ac.nhm.core.componentHelpers.HelperBase"%><%
%>
<cq:defineObjects />


<%  HelperBase helper = new HelperBase(); %>

		<div id="LD-container">
			<div id="bigsplash-interactive" class="showLayers">
				<img id="mainImg" src="/etc/clientlibs/nhmwww/singlepurpose/ld-interactive-splash-v2/img/LitD.jpg"/>
				<div id="layer1">
					<div class="animal" id="fox">
						<img src="/etc/clientlibs/nhmwww/singlepurpose/ld-interactive-splash-v2/img/fennecfox.png" />
					</div>
					<div class="animal" id="owl">
						<img src="/etc/clientlibs/nhmwww/singlepurpose/ld-interactive-splash-v2/img/owl.png" />
						<audio id="owl-sound">
							<source src="/etc/clientlibs/nhmwww/singlepurpose/ld-interactive-splash-v2/audio/territorialhootrhett.mp3"></source>
							<source src="audio/beep.ogg"></source>
							Your browser isn't invited for super fun audio time.
						</audio>
					</div>
				</div>
				<div id="layer2">
					<div class="animal" id="croc">
						<img src="/etc/clientlibs/nhmwww/singlepurpose/ld-interactive-splash-v2/img/croc.png" />
					</div>
				</div>

				<div id="layer3">
					<div class="animal" id="glow-worms">
						<img src="/etc/clientlibs/nhmwww/singlepurpose/ld-interactive-splash-v2/img/glo-worms.png" />
						<!-- glow balls -->
						<div id="glow-worms-glowballs" class="wrap">
							<div class="c"></div>
							<div class="c"></div>
							<div class="c"></div>
							<div class="c"></div>
							<div class="c"></div>
							<div class="c"></div>
							<div class="c"></div>
							<div class="c"></div>
							<div class="c"></div>
							<div class="c"></div>
						</div>
					</div>

					<div class="animal" id="angler">
						<img src="/etc/clientlibs/nhmwww/singlepurpose/ld-interactive-splash-v2/img/angler.jpg" />
						<div class="glowball" />
					</div>

					<div class="animal" id="jellyfish1">
						<img src="/etc/clientlibs/nhmwww/singlepurpose/ld-interactive-splash-v2/img/jellyfish.png" />
					</div>

					<div class="animal" id="jellyfish2">
						<img src="/etc/clientlibs/nhmwww/singlepurpose/ld-interactive-splash-v2/img/jellyfish2.jpg" />
					</div>
				</div>
			</div>
		</div>
		<div class="bigsplash-text">
			<div class="row cf">
				<div class="bigsplash-text--title-container">
					<h1 class="bigsplash-text--title">Life in the dark</h1>
					<span onclick="torch()">Click to turn the light off, also scroll and hover..</span>
				</div>
			</div>
		</div>
	</div>

        
          

<cq:includeClientLib categories="nhmwww.ld-interactive-splash-v2"/>
