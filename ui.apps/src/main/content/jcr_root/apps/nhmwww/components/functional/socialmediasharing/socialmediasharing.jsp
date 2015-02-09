<%@include file="/apps/nhmwww/components/global.jsp"%>
<%@page import="uk.ac.nhm.nhm_www.core.componentHelpers.*"%>
<cq:defineObjects />
<% SocialMediaSharingHelper helper = new SocialMediaSharingHelper(new HelperFactory(resource)); %>
                        <li class="nav-list__item utility has-children">
                            <a href="#" class="nav-icons share">
                                <i class="ico svg-ico desktop" data-svg-src="img/svg-icons/icon_s_general_share_small.svg" data-svg-title="icon__share" data-alt="Share" data-stroke-width="2" data-base-color="#9D9D9D" data-fallback="img/icons/share-nav.png"></i>
                                <span class="mobile tablet">Share</span>
                            </a>
                            <ul class="nav-list level-2 share">
                                <li class="nav-list__item first cf">
                                    <h4>Share this page</h4>
                                    <a href="#" class="share-icons">
                                        <i class="ico svg-ico ico-email" data-svg-src="img/svg-icons/icon_l_media_email.svg" data-svg-title="icon__email" data-fallback="img/icons/mobile-email.png" data-base-color="#9D9D9D" data-stroke-width="5" data-alt="Email" /></i>
                                    </a>
                                    <a href="http://twitter.com/share?url=http://www.nhm.ac.uk/" class="share-icons">
                                        <i class="ico svg-ico ico-twitter" data-svg-src="img/svg-icons/icon_l_media_twitter.svg" data-svg-title="icon__twitter" data-fallback="img/icons/mobile-twitter.png" data-stroke-width="5" data-base-color="#9D9D9D" data-alt="Twitter" /></i>
                                    </a>
                                    <a href="http://www.facebook.com/sharer.php?u=http://www.nhm.ac.uk/" class="share-icons">
                                        <i class="ico svg-ico ico-facebook" data-svg-src="img/svg-icons/icon_l_media_facebook.svg" data-svg-title="icon__facebook" data-fallback="img/icons/mobile-facebook.png" data-stroke-width="5" data-base-color="#9D9D9D" data-alt="Facebook" /></i>
                                    </a>
                                    <a href="https://plus.google.com/share?url=http://www.nhm.ac.uk/" onclick="javascript:window.open(this.href,
  '', 'menubar=no,toolbar=no,resizable=yes,scrollbars=yes,height=600,width=600');return false;" class="share-icons">
                                        <i class="ico svg-ico ico-gplus" data-svg-src="img/svg-icons/icon_l_media_gplus.svg" data-svg-title="icon__gplus" data-fallback="img/icons/mobile-gplus.png" data-base-color="#9D9D9D" data-stroke-width="5" data-alt="Google Plus" /></i>
                                    </a>
                                    <a href="<%=helper.replaceUrlParameters((String)currentStyle.get("twitter/url"), new String[] {"url"}, new String[] {"http://www.nhm.ac.uk/"}) %>" class="share-icons">
                                        <i class="ico svg-ico ico-pinterest" data-svg-src="img/svg-icons/icon_l_media_pinterest.svg" data-svg-title="icon__pinterest" data-fallback="img/icons/mobile-pinterest.png" data-stroke-width="5" data-base-color="#9D9D9D" data-alt="Pinterest" /></i>
                                    </a>
                                    <a href="#" class="share-icons">
                                        <i class="ico svg-ico ico-instagram" data-svg-src="img/svg-icons/icon_l_media_instagram.svg" data-svg-title="icon__instagram" data-fallback="img/icons/mobile-instagram.png" data-stroke-width="12" data-base-color="#9D9D9D" data-alt="Instagram" /></i>
                                    </a>
                                </li>
                                <li class="nav-list__item">
                                    <a href="#">Bookmark this page</a>
                                </li>
                                <li class="nav-list__item cf">
                                    <h4>Print or save as PDF</h4>
                                    <a href="#" class="share-icons">
                                        <i class="ico svg-ico" data-svg-src="img/svg-icons/icon_l_general_printer.svg" data-svg-title="icon__printer" data-fallback="img/icons/mobile-print.png" data-stroke-width="2" data-alt="Print" /></i>
                                    <a href="#" class="share-icons">
                                        <i class="ico svg-ico" data-svg-src="img/svg-icons/icon_l_general_pdf.svg" data-svg-title="icon__pdf" data-fallback="img/icons/mobile-pdf.png" data-stroke-width="2" data-alt="PDF" /></i>
                                    </a>
                                </li>
                                <li class="nav-list__item">
                                    <h4>E-news</h4>
                                    <form action="#" method="post">
                                        <div class="form-field firstname desktop">
                                            <label>Your first name</label>
                                            <input type="text" class="text" />
                                        </div>
                                        <div class="form-field email">
                                            <label>Email address</label>
                                            <input type="text" class="text" />
                                        </div>
                                        <button class="submit arrow">Sign up</button>
                                    </form>
                                </li>
                            </ul>
                        </li>