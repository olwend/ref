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
                        <li class="nav-list__item utility has-children last">
                            <a href="#" class="nav-icons search">
                                <i class="ico svg-ico desktop" data-svg-src="/etc/designs/nhmwww/img/svg-icons/icon_s_general_search_small.svg" data-svg-title="icon__search" data-alt="Search" data-stroke-width="3" data-base-color="#9D9D9D" data-fallback="/etc/designs/nhmwww/img/icons/search-nav.png"></i>
                                <span class="mobile tablet">Search</span>
                            </a>
                            <ul class="nav-list level-2 search">
                                <li class="nav-list__item first">
                                    <form action="/content/nhmwww/en/home/search.html" method="get">
                                        <input type="text" name="q" class="text" />
                                        <button class="submit arrow">Search</button>
                                    </form>
                                </li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </nav>
  
<!-- END OF MEGAMENU -->