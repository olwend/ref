<%@page import="uk.ac.nhm.nhm_www.core.componentHelpers.DynamicPageHelper"%>
<%@include file="/apps/nhmwww/components/global.jsp"%>
<% DynamicPageHelper helper = new DynamicPageHelper(resource, properties, request); %>

	<div class="global-header--container row cf">
		<div class="global-header--menu">
			<div class="global-header--bar global-header--logo__container row cf">
					<a href="/" class="global-header--logo"><span class="ir">Natural History Museum</span></a>
					<a href="#" id="mobile-navigation" class="mobile global-header--menu__trigger">
						<i class="ico svg-ico ico-menu-open" data-svg-src="/etc/designs/nhmwww/img/svg-icons/icon_l_general_menu.svg" data-svg-title="icon__menu" data-alt="Open Menu" data-stroke-width="1" data-fallback="/etc/designs/nhmwww/img/mobile-menu.png"></i>
						<i class="ico svg-ico ico-menu-close" data-svg-src="/etc/designs/nhmwww/img/svg-icons/icon_l_general_close.svg" data-svg-title="icon__menu_close" data-alt="Close Menu" data-stroke-width="10" data-base-color="#A8A8A8" data-fallback="/etc/designs/nhmwww/img/mobile-menu.png"></i>
						<span class="ir">Menu</span>
					</a>
			</div>
			<nav class="global-header--bar global-header--menu__nav row cf">
					<ul class="global-header--nav-list cf">
						<li class="global-header--nav-list__item link-visit">
							<a class="global-header--nav-list__link" href="/content/nhmwww/en/home/visit.html">Visit</a>
						</li>
						<li class="global-header--nav-list__item link-discover">
							<a class="global-header--nav-list__link" href="/content/nhmwww/en/home/discover.html">Discover</a>
							<div class="global-header--nav-list__item--subnav-link">
								<i class="ico svg-ico ico-menu-open" data-svg-src="/etc/designs/nhmwww/img/svg-icons/icon_l_general_menu.svg" data-svg-title="icon__menu" data-alt="Open Menu" data-stroke-width="1" data-fallback="/etc/designs/nhmwww/img/mobile-menu.png"></i>
							</div>
						</li>
								<li class="global-header--menu__subnav-discover">
									<a href="" class="">Oceans</a>
								</li>
								<li class="global-header--menu__subnav-discover">
									<a href="" class="">Dinosaurs</a>
								</li>
								<li class="global-header--menu__subnav-discover">
									<a href="" class="">News</a>
								</li>
								<li class="global-header--menu__subnav-discover">
									<a href="" class="">Blog</a>
								</li>
						<li class="global-header--nav-list__item link-take-part">
							<a class="global-header--nav-list__link" href="/content/nhmwww/en/home/take-part.html">Take part</a>
						</li>
						<li class="global-header--nav-list__item link-support-us">
							<a class="global-header--nav-list__link" href="/content/nhmwww/en/home/support-us.html">Join and support</a>
						</li>
						<li class="global-header--nav-list__item link-shop">
							<a class="global-header--nav-list__link" href="https://www.nhmshop.co.uk/?utm_source=nhm.ac.uk&utm_medium=referral&utm_campaign=general&utm_content=meganav">Shop</a>
						</li>
						<li class="global-header--nav-list__item link-schools">
							<a class="global-header--nav-list__link" href="/content/nhmwww/en/home/schools.html">Schools</a>
						</li>
						<li class="global-header--nav-list__item link-our-science">
							<a class="global-header--nav-list__link" href="/content/nhmwww/en/home/our-science.html">Our science</a>
						</li>
						<li class="global-header--nav-list__item link-search">
							<a class="global-header--nav-list__link" href="#" id="global-header--search-bar__button" >Search</a>
						</li>
					</ul>
			</nav>
		</div>
		<div class="global-header--info__container">
			<ul class="global-header--info">
				<li class="global-header--info__item">
					<a href="/visit/getting-here.html">
					<i class="ico svg-ico" data-svg-src="/etc/designs/nhmwww/img/svg-icons/icon_s_feature_ticket_small.svg" data-svg-title="icon__ticket" data-alt="Admission" data-base-color="#000000" data-stroke-width="1" data-fallback="/etc/designs/nhmwww/img/icons/nav-ticket.png"></i>
					Hours and admission</a>
				</li>
				<li class="global-header--info__item">
					<a href="/support-us/membership.html">
					<i class="ico svg-ico" data-svg-src="/etc/designs/nhmwww/img/svg-icons/icon_s_feature_membership_small.svg" data-svg-title="icon__membership" data-alt="Membership" data-base-color="#000000" data-stroke-width="1" data-fallback="/etc/designs/nhmwww/img/icons/nav-id.png"></i>
					Become a Member</a>
				</li>
			</ul>
		</div>
	</div>
	<div class="global-header--subnav js-global-header--nav-list__hide">
		<div class="row cf">
			<ul>
				<li><a href="">Oceans</a></li>
				<li><a href="">Dinosaurs</a></li>
				<li><a href="">News</a></li>
				<li><a href="">Blog</a></li>
			</ul>
		</div>
	</div>

</div>
	<div class="global-header--search-bar">
		<div class="global-header--search-bar__blocking-overlay"></div>
		<div class="global-header--search-bar__overlay"></div>
		<div class="global-header--search-bar__content">
			<a href="#" id="global-header--search-bar__close"><i class="ico svg-ico ico-menu-close global-header--search-bar__close-icon" data-svg-src="/etc/designs/nhmwww/img/svg-icons/icon_l_general_close.svg" data-svg-title="icon__menu_close" data-alt="Close Menu" data-stroke-width="10" data-base-color="#A8A8A8" data-fallback="/etc/designs/nhmwww/img/mobile-menu.png" id="svg-icon__menu_close-4"></i></a>
				<form action="https://www.nhm.ac.uk/search.html" method="get" class="global-header--search-bar__form">

						<input type="text" name="q" class="text global-header--search-bar__input" placeholder="Search">
						<button class="submit global-header--search-bar__submit-button">
							<i class="ico svg-ico desktop global-header--search-bar__search-icon" data-svg-src="/etc/designs/nhmwww/img/svg-icons/icon_s_general_search_small.svg" data-svg-title="icon__search" data-alt="Search" data-stroke-width="2" data-base-color="#FFFFFF" data-fallback="/etc/designs/nhmwww/img/icons/search-nav.png" id="svg-icon__search-6"></i>
						</button>

				</form>

			<div class="global-header--search-bar__popular-search-terms">
				<h2 class="global-header--search-bar__heading">Popular search terms:</h2>
				<ul>
				<li class="global-header--search-bar__link"><a href="http://www.nhm.ac.uk/visit/exhibitions.html">Exhibitions</a></li>
				<li class="global-header--search-bar__link"><a href="http://www.nhm.ac.uk/visit/exhibitions/wildlife-photographer-of-the-year-53.html"> Wildlife Photographer of the Year</a></li>
				<li class="global-header--search-bar__link"><a href="http://www.nhm.ac.uk/bluewhale">Hope the blue whale</a></li>
				<li class="global-header--search-bar__link"><a href="http://www.nhm.ac.uk/discover/oceans.html">Oceans</a></li>
				<li class="global-header--search-bar__link"><a href="http://www.nhm.ac.uk/search.html?q=dinosaurs">Dinosaurs</a></li>
				<li class="global-header--search-bar__link"><a href="http://www.nhm.ac.uk/take-part/identify-nature.html">Identify a fossil</a></li>
				</ul>
			</div>
		</div>
	</div>
