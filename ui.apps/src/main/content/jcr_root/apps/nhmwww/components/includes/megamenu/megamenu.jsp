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
                            <a href="#" id="search-button" class="nav-icons search">Search</a>
                        </li>
                    </ul>
                </div>
            </nav>
            <div class="search_bar_container">
                <div class="search_bar_overlay"></div>
                <div class="search_bar">
                    <a href="#" id="search-bar-close"><i class="ico svg-ico ico-menu-close search_bar_close_icon" data-svg-src="/etc/designs/nhmwww/img/svg-icons/icon_l_general_close.svg" data-svg-title="icon__menu_close" data-alt="Close Menu" data-stroke-width="10" data-base-color="#A8A8A8" data-fallback="/etc/designs/nhmwww/img/mobile-menu.png" id="svg-icon__menu_close-4"></i></a>
                        <form action="/content/nhmwww/en/home/search.html" method="get" class="search_bar_form">
                            
                                <input type="text" name="q" class="text search_bar_text_input">
                                <button class="submit search_bar_submit_button">
                                    <i class="ico svg-ico desktop search_bar_search_icon" data-svg-src="/etc/designs/nhmwww/img/svg-icons/icon_s_general_search_small.svg" data-svg-title="icon__search" data-alt="Search" data-stroke-width="2" data-base-color="#FFFFFF" data-fallback="/etc/designs/nhmwww/img/icons/search-nav.png" id="svg-icon__search-6"></i>
                                </button>
                            
                        </form>

                    <div class="popular_search_terms">
                      <h2 class="search_bar_header">Popular search terms:</h2>
                      <ul>
                        <li class="search_bar_link"><a href="/content/nhmwww/en/home/search.html?q=Dinosaurs">Dinosaurs</a></li>
                        <li class="search_bar_link"><a href="/content/nhmwww/en/home/search.html?q=Darwin">Darwin</a></li>
                        <li class="search_bar_link"><a href="/content/nhmwww/en/home/search.html?q=Whales">Whales</a></li>
                        <li class="search_bar_link"><a href="/content/nhmwww/en/home/search.html?q=Fossils">Fossils</a></li>
                        <li class="search_bar_link"><a href="/content/nhmwww/en/home/search.html?q=is+the+dodo+really+extinct">The Dodo</a></li>
                        <li class="search_bar_link"><a href="/content/nhmwww/en/home/search.html?q=Skeletons">Skeletons</a></li>
                      </ul>
                    </div>
                </div>
            </div>
<!-- END OF MEGAMENU -->