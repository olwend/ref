<%@page import="uk.ac.nhm.nhm_www.core.componentHelpers.DynamicPageHelper"%>
<%@include file="/apps/nhmwww/components/global.jsp"%>
<% DynamicPageHelper helper = new DynamicPageHelper(resource, properties, request); %>
<!-- START OF MEGAMENU -->
            <nav class="global-header-bar global-nav-menu">
                <div class="row">
                    <ul class="nav-list level-1 cf">
                        <li class="nav-list__item link-visit">
                            <a class="nav-list__link" href="/content/nhmwww/en/home/visit.html">Visit</a>
                        </li>
                        <li class="nav-list__item link-discover">
                            <a class="nav-list__link" href="/content/nhmwww/en/home/discover.html">Discover</a>
                        </li>
                        <li class="nav-list__item link-take-part">
                            <a class="nav-list__link" href="/content/nhmwww/en/home/take-part.html">Take part</a>
                        </li>
                        <li class="nav-list__item link-support-us">
                            <a class="nav-list__link" href="/content/nhmwww/en/home/support-us.html">Join and support</a>
                        </li>
                        <li class="nav-list__item link-shop">
                            <a class="nav-list__link" href="https://www.nhmshop.co.uk/?utm_source=nhm.ac.uk&utm_medium=referral&utm_campaign=general&utm_content=meganav">Shop</a>
                        </li>
                        <li class="nav-list__item link-schools">
                            <a class="nav-list__link" href="/content/nhmwww/en/home/schools.html">Schools</a>
                        </li>
                        <li class="nav-list__item link-our-science">
                            <a class="nav-list__link" href="/content/nhmwww/en/home/our-science.html">Our science</a>
                        </li>
                        <li class="nav-list__item link-search">
                            <a class="nav-list__link" href="#" id="megamenu--search-bar__button" >Search</a>
                        </li>
                    </ul>
                    <div class="global-header-info__container">
                      <ul class="global-header-info">
                        <li class="global-header-info__item">
                          <a href="/visit/getting-here.html">
                            <i class="ico svg-ico" data-svg-src="/etc/designs/nhmwww/img/svg-icons/icon_s_feature_ticket_small.svg" data-svg-title="icon__ticket" data-alt="Admission" data-base-color="#000000" data-stroke-width="1" data-fallback="/etc/designs/nhmwww/img/icons/nav-ticket.png"></i>
                            Hours and admission</a>
                        </li>
                        <li class="global-header-info__item">
                          <a href="/support-us/membership.html">
                          <i class="ico svg-ico" data-svg-src="/etc/designs/nhmwww/img/svg-icons/icon_s_feature_membership_small.svg" data-svg-title="icon__membership" data-alt="Membership" data-base-color="#000000" data-stroke-width="1" data-fallback="/etc/designs/nhmwww/img/icons/nav-id.png"></i>
                          Become a Member</a>
                        </li>
                      </ul>
                    </div>
                </div>
            </nav>
            <div class="megamenu--search-bar">
                <div class="megamenu--search-bar__blocking-overlay"></div>
                <div class="megamenu--search-bar__overlay"></div>
                <div class="megamenu--search-bar__content">
                    <a href="#" id="megamenu--search-bar__close"><i class="ico svg-ico ico-menu-close megamenu--search-bar__close-icon" data-svg-src="/etc/designs/nhmwww/img/svg-icons/icon_l_general_close.svg" data-svg-title="icon__menu_close" data-alt="Close Menu" data-stroke-width="10" data-base-color="#A8A8A8" data-fallback="/etc/designs/nhmwww/img/mobile-menu.png" id="svg-icon__menu_close-4"></i></a>
                        <form action="https://www.nhm.ac.uk/search.html" method="get" class="megamenu--search-bar__form">
                            
                                <input type="text" name="q" class="text megamenu--search-bar__input" placeholder="Search">
                                <button class="submit megamenu--search-bar__submit-button">
                                    <i class="ico svg-ico desktop megamenu--search-bar__search-icon" data-svg-src="/etc/designs/nhmwww/img/svg-icons/icon_s_general_search_small.svg" data-svg-title="icon__search" data-alt="Search" data-stroke-width="2" data-base-color="#FFFFFF" data-fallback="/etc/designs/nhmwww/img/icons/search-nav.png" id="svg-icon__search-6"></i>
                                </button>
                            
                        </form>

                    <div class="megamenu--search-bar__popular-search-terms">
                      <h2 class="megamenu--search-bar__heading">Popular search terms:</h2>
                      <ul>
                        <li class="megamenu--search-bar__link"><a href="http://www.nhm.ac.uk/visit/exhibitions.html">Exhibitions</a></li>
                        <li class="megamenu--search-bar__link"><a href="http://www.nhm.ac.uk/visit/exhibitions/wildlife-photographer-of-the-year-53.html"> Wildlife Photographer of the Year</a></li>
                        <li class="megamenu--search-bar__link"><a href="http://www.nhm.ac.uk/bluewhale">Hope the blue whale</a></li>
                        <li class="megamenu--search-bar__link"><a href="http://www.nhm.ac.uk/discover/oceans.html">Oceans</a></li>
                        <li class="megamenu--search-bar__link"><a href="http://www.nhm.ac.uk/search.html?q=dinosaurs">Dinosaurs</a></li>
                        <li class="megamenu--search-bar__link"><a href="http://www.nhm.ac.uk/take-part/identify-nature.html">Identify a fossil</a></li>
                      </ul>
                    </div>
                </div>
            </div>
<!-- END OF MEGAMENU -->