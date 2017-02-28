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
                        <li class="nav-list__item utility has-children">
                            <a href="#" class="nav-icons share">
                                <i class="ico svg-ico desktop" data-svg-src="/etc/designs/nhmwww/img/svg-icons/icon_s_general_share_small.svg" data-svg-title="icon__share" data-alt="Share" data-stroke-width="3" data-base-color="#9D9D9D" data-fallback="/etc/designs/nhmwww/img/icons/share-nav.png"></i>
                                <span class="mobile tablet">Share</span>
                            </a>
                            <ul class="nav-list level-2 share">
                                <li class="nav-list__item first cf">
                                    <h4>Share this page</h4>
                                   
                                    <span class='st_email_large' displayText='Email'></span>
                                    <span class='st_facebook_large' displayText='Facebook'></span>
                                    <span class='st_twitter_large' displayText='Tweet'></span>
                                    <!-- <a href="https://plus.google.com/share?url=" onclick="javascript:window.open(this.href + window.location,  '', 'menubar=no,toolbar=no,resizable=yes,scrollbars=yes,height=600,width=600');return false;" class="share-icons">
                                        <i class="ico svg-ico ico-gplus" data-svg-src="/etc/designs/nhmwww/img/svg-icons/icon_l_media_gplus.svg" data-svg-title="icon__gplus" data-fallback="img/icons/mobile-gplus.png" data-base-color="#9D9D9D" data-stroke-width="5" data-alt="Google Plus" /></i>
                                    </a>-->
                                    <span class='st_googleplus_large' displayText='Google+'></span>
                                    <span class='st_pinterest_large' displayText='Pinterest'></span>
                                    
                                </li>
                                <li class="nav-list__item">
                                    <div class="newslettersignup">
                                      <cq:includeClientLib categories="nhm-www.newsletter" />
                                      <h4> Sign up for our emails</h4>
                                      <form action="<%= helper.getProtocol() + hostPort  + pathForSignup %>/jcr:content.newslettersignup.html" method="get">
                                          <div class="form-field firstname">
                                              <label for="name">Full name</label>
                                              <input class="item-input" name="name" type="text" />
                                          </div>
                                          <div class="form-field email">
                                              <label for="email">Email address</label>
                                              <input class="item-input" name="email" type="email" />
                                          </div>
                                          <input class="question" type="text" name="question">
                                          <button class="submit arrow">Sign up</button>
                                          <div class="errors"></div>
                                      </form>
                                      <label>We will use your personal information in accordance with the Data Protection Act 1998. <a href="/my-nhm/privacy-policy/index.html">View our privacy notice</a></label>
                                    </div>
                                </li>
                            </ul>
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