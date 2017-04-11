<%@page import="uk.ac.nhm.nhm_www.core.componentHelpers.DynamicPageHelper"%>
<%@include file="/apps/nhmwww/components/global.jsp"%>
<% DynamicPageHelper helper = new DynamicPageHelper(resource, properties, request); %>
<!-- START OF MEGAMENU -->
            <nav class="global-header-bar global-nav-menu">
                <div class="row">
                    <ul class="nav-list level-1 cf">
                        <li class="sticky-logo tablet desktop">
                            <a href="/">
                              <span class="ir">Natural History Museum</span>
                            </a>
                        </li>
                        <li class="nav-list__item first">
                            <a href="/content/nhmwww/en/home/visit.html">Visit</a>
                        </li>
                        <li class="nav-list__item">
                            <a href="/content/nhmwww/en/home/discover.html">Discover</a>
                        </li>
                        <li class="nav-list__item">
                            <a href="/content/nhmwww/en/home/take-part.html">Take part</a>
                        </li>
                        <li class="nav-list__item">
                            <a href="/content/nhmwww/en/home/support-us.html">Join and support</a>
                        </li>
                        <li class="nav-list__item">
                            <a href="https://www.nhmshop.co.uk/?utm_source=nhm.ac.uk&utm_medium=referral&utm_campaign=general&utm_content=meganav">Shop</a>
                        </li>
                        <li class="nav-list__item">
                            <a href="/content/nhmwww/en/home/schools.html">Schools</a>
                        </li>
                        <li class="nav-list__item">
                            <a href="/content/nhmwww/en/home/our-science.html">Our science</a>
                        </li>
                        <li class="nav-list__item utility last">
                            <a href="#" id="megamenu--search-bar__button" class="nav-icons search">Search</a>
                        </li>
                    </ul>
                </div>
            </nav>
            <div class="megamenu--search-bar">
                <div class="megamenu--search-bar__blocking-overlay"></div>
                <div class="megamenu--search-bar__overlay"></div>
                <div class="megamenu--search-bar__content">
                    <a href="#" id="megamenu--search-bar__close"><i class="ico svg-ico ico-menu-close megamenu--search-bar__close-icon" data-svg-src="/etc/designs/nhmwww/img/svg-icons/icon_l_general_close.svg" data-svg-title="icon__menu_close" data-alt="Close Menu" data-stroke-width="10" data-base-color="#A8A8A8" data-fallback="/etc/designs/nhmwww/img/mobile-menu.png" id="svg-icon__menu_close-4"></i></a>
                        <form action="/content/nhmwww/en/home/search.html" method="get" class="megamenu--search-bar__form">
                            
                                <input type="text" name="q" class="text megamenu--search-bar__input" placeholder="Search">
                                <button class="submit megamenu--search-bar__submit-button">
                                    <i class="ico svg-ico desktop megamenu--search-bar__search-icon" data-svg-src="/etc/designs/nhmwww/img/svg-icons/icon_s_general_search_small.svg" data-svg-title="icon__search" data-alt="Search" data-stroke-width="2" data-base-color="#FFFFFF" data-fallback="/etc/designs/nhmwww/img/icons/search-nav.png" id="svg-icon__search-6"></i>
                                </button>
                            
                        </form>

                    <div class="megamenu--search-bar__popular-search-terms">
                      <h2 class="megamenu--search-bar__heading">Popular search terms:</h2>
                      <ul>
                        <li class="megamenu--search-bar__link"><a href="http://www.nhm.ac.uk/about-us/national-impact/diplodocus-on-tour.html">Dippy on tour</a></li>
                        <li class="megamenu--search-bar__link"><a href="http://www.nhm.ac.uk/about-us/news.html">News</a></li>
                        <li class="megamenu--search-bar__link"><a href="http://www.nhm.ac.uk/search.html?q=blue+whale">Blue whale</a></li>
                        <li class="megamenu--search-bar__link"><a href="http://www.nhm.ac.uk/visit/exhibitions/evening-events.html">Evening events</a></li>
                        <li class="megamenu--search-bar__link"><a href="http://www.nhm.ac.uk/take-part/identify-nature.html">Identify an insect</a></li>
                        <li class="megamenu--search-bar__link"><a href="http://www.nhm.ac.uk/take-part/identify-nature.html">Identify a fossil</a></li>
                        <li class="megamenu--search-bar__link"><a href="http://www.nhm.ac.uk/visit/exhibitions.html">Exhibitions</a></li>
                      </ul>
                    </div>
                </div>
            </div>
<!-- END OF MEGAMENU -->