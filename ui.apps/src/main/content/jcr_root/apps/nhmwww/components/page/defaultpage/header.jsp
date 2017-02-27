<%@include file="/apps/nhmwww/components/global.jsp"%>
<header class="global-header cf">
	<div class="global-header-bar global-info-menu">
	    <div class="row cf">
	        <a href="/" class="global-header-logo"><span class="ir">Natural History Museum</span></a>
	        <div class="global-header-info desktop tablet cf">
	            <p class="global-header-info__item tickets">
	               <a href="/visit/getting-here.html"><i class="ico svg-ico" data-svg-src="/etc/designs/nhmwww/img/svg-icons/icon_s_feature_ticket_small.svg" data-svg-title="icon__ticket" data-alt="Admission" data-base-color="#9D9D9D" data-stroke-width="1" data-fallback="/etc/designs/nhmwww/img/icons/nav-ticket.png"></i> Hours and admission</a>
	            </p>
	            <p class="global-header-info__item membership global-header-info__item--last">
	               <a href="/support-us/membership.html"><i class="ico svg-ico" data-svg-src="/etc/designs/nhmwww/img/svg-icons/icon_s_feature_membership_small.svg" data-svg-title="icon__membership" data-alt="Membership" data-base-color="#9D9D9D" data-stroke-width="1" data-fallback="/etc/designs/nhmwww/img/icons/nav-id.png"></i> Become a Member</a>
	            </p>
	        </div>
	        <a href="#" id="mobile-navigation" class="mobile global-menu-trigger">
	            <i class="ico svg-ico ico-menu-open" data-svg-src="/etc/designs/nhmwww/img/svg-icons/icon_l_general_menu.svg" data-svg-title="icon__menu" data-alt="Open Menu" data-stroke-width="10" data-base-color="#A8A8A8" data-fallback="/etc/designs/nhmwww/img/mobile-menu.png"></i>
	            <i class="ico svg-ico ico-menu-close" data-svg-src="/etc/designs/nhmwww/img/svg-icons/icon_l_general_close.svg" data-svg-title="icon__menu_close" data-alt="Close Menu" data-stroke-width="10" data-base-color="#A8A8A8" data-fallback="/etc/designs/nhmwww/img/mobile-menu.png"></i>
	            <i class="ico svg-ico ico-menu-return" data-svg-src="/etc/designs/nhmwww/img/svg-icons/icon_l_general_direction_r.svg" data-svg-title="icon__menu_return" data-alt="Return to Menu" data-stroke-width="10" data-base-color="#A8A8A8" data-fallback="/etc/designs/nhmwww/img/arrows/mobile-return-arrow.png"></i>
	            <span class="ir">Menu</span>
	        </a>
	    </div>
	</div>
	<cq:include path="megamenu" resourceType="nhmwww/components/includes/megamenu"/>
</header>