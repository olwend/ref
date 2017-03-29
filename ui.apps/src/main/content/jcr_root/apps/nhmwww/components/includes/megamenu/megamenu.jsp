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
                            <a href="#" class="nav-icons search" onClick="$('#search_bar_container').show();">Search</a>
                        </li>
                    </ul>
                </div>
            </nav>
            <div id="search_bar_container" style="display: none;">
                <div id="search_bar_overlay" style="position: fixed; top: 0; left: 0; width: 100%; height: 300px; background-color: white; opacity: 0.95; z-index: 99;"></div>
                <div id="search_bar" 
                    style="position: fixed; top: 0; left: 0; width: 100%; padding-top: 50px; z-index: 99; ">
                    <a href="#" onClick="$('#search_bar_container').hide();"><i class="ico svg-ico ico-menu-close" data-svg-src="/etc/designs/nhmwww/img/svg-icons/icon_l_general_close.svg" data-svg-title="icon__menu_close" data-alt="Close Menu" data-stroke-width="10" data-base-color="#A8A8A8" data-fallback="/etc/designs/nhmwww/img/mobile-menu.png" id="svg-icon__menu_close-4" style="stroke-width: 10; position: absolute; top: 0; right: 0; height: 25px; width: 25px; margin-right: 5px; margin-top: 5px;"></i></a>
                        <form action="/content/nhmwww/en/home/search.html" method="get" style="text-align: center; height: 50px; margin-bottom: 50px;">
                            <input type="text" name="q" class="text" style="display: inline; width: 570px; height: 50px; margin: 0;">
                            <button class="submit" style="background-color: black; height: 50px; width: 50px; margin-bottom: 0; margin-left: -5px; padding: 0; top: -1px;">
                                <i class="ico svg-ico desktop" data-svg-src="/etc/designs/nhmwww/img/svg-icons/icon_s_general_search_small.svg" data-svg-title="icon__search" data-alt="Search" data-stroke-width="3" data-base-color="#9D9D9D" data-fallback="/etc/designs/nhmwww/img/icons/search-nav.png" id="svg-icon__search-6" style="stroke-width: 3; height: 43px; width: 43px;"></i>
                            </button>
                        </form>

                    <div id="popular_search_terms" style="text-align: center; margin-bottom: 50px;">
                    <h2 style="padding-bottom: 0; margin-bottom: 20px;">Popular search terms:</h2>
                    <a href="/content/nhmwww/en/home/search.html?q=Dinosaurs" style="padding-top:7px; padding-bottom:7px; background-color: #B5B5B5; 
                    color: black; margin-left: 10px; margin-right: 10px; margin-bottom: 0; font-family: 'Elysio-Light', Helvetica, Arial, sans-serif; font-size: 18px;
                    padding-left: 8px; padding-right: 8px;">Dinosaurs</a>
                    <a href="/content/nhmwww/en/home/search.html?q=Darwin" style="padding-top:7px; padding-bottom:7px; background-color: #B5B5B5; 
                    color: black; margin-left: 10px; margin-right: 10px; margin-bottom: 0; font-family: 'Elysio-Light', Helvetica, Arial, sans-serif; font-size: 18px;
                    padding-left: 8px; padding-right: 8px;">Darwin</a>
                    <a href="/content/nhmwww/en/home/search.html?q=Whales" style="padding-top:7px; padding-bottom:7px; background-color: #B5B5B5; 
                    color: black; margin-left: 10px; margin-right: 10px; margin-bottom: 0; font-family: 'Elysio-Light', Helvetica, Arial, sans-serif; font-size: 18px;
                    padding-left: 8px; padding-right: 8px;">Whales</a>
                    <a href="/content/nhmwww/en/home/search.html?q=Fossils" style="padding-top:7px; padding-bottom:7px; background-color: #B5B5B5; 
                    color: black; margin-left: 10px; margin-right: 10px; margin-bottom: 0; font-family: 'Elysio-Light', Helvetica, Arial, sans-serif; font-size: 18px;
                    padding-left: 8px; padding-right: 8px;">Fossils</a>
                    </div>
                </div>
            </div>
<!-- END OF MEGAMENU -->