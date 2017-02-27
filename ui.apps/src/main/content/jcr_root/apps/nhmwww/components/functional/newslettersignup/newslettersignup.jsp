<%@page session="false"%>
<%@page import="uk.ac.nhm.nhm_www.core.componentHelpers.NewsletterSignUpHelper"%>
<%@page import="uk.ac.nhm.nhm_www.core.componentHelpers.DynamicPageHelper"%>
<%@include file="/apps/nhmwww/components/global.jsp"%>
<% DynamicPageHelper dynamicPageHelper = new DynamicPageHelper(resource, properties, request); %>
<cq:includeClientLib categories="nhm-www.newsletter" />
<!-- START OF FOOTER -->
<footer class="main-footer">
            <div class="row">
                <div class="small-12 medium-9 large-9 columns footer--frame-wrapper">
                    <div class="footer--frame">
                        <nav class="small-12 medium-12 large-9 columns footer-nav">
                               <ul class="small-6 medium-6 large-6 columns">
                                    <li><a href="/content/nhmwww/en/home/about-us.html">About us</a></li>
                                    <li><a href="/about-us/news.html">News</a></li>
                                    <li><a href="/content/nhmwww/en/home/press-office.html">Press office</a></li>
                                    <li><a href="/content/nhmwww/en/home/business-services.html">Business services</a></li>
									<li><a href="/content/nhmwww/en/home/about-us/careers.html">Careers</a></li>
                                    <li><a href="/content/nhmwww/en/home/about-us/governance.html">Governance</a></li>
                                    <li><a href="/content/nhmwww/en/home/business-services/touring-exhibitions.html">Touring Exhibitions</a></li>
                                </ul>
                                <ul class="small-6 medium-6 large-6 columns">
                                	<li><a href="/about-us/privacy-policy.html">Privacy notice</a></li>
                                	<li><a href="/about-us/website-terms-conditions.html">Terms and conditions</a></li>
                                    <li><a href="/content/nhmwww/en/home/contact-us.html">Contact us</a></li>
                                    <li class="footer--icon-list-title">Follow us</li>
                                    <li class="footer--icon-list-container">
										<ul class="small-block-grid-3 medium-block-grid-3 large-block-grid-3 icon-list">
											<li><a href="https://www.facebook.com/naturalhistorymuseum"><img src="<%= currentDesign.getPath() + "/img/icons/facebook.png"%>" alt="Facebook"></a></li>
	                                        <li><a href="https://twitter.com/NHM_London"><img src="<%= currentDesign.getPath() + "/img/icons/twitter.png"%>" alt="Twitter"></a></li>
	                                        <li><a href="https://www.youtube.com/user/naturalhistorymuseum"><img src="<%= currentDesign.getPath() + "/img/icons/youtube.png"%>" alt="YouTube"></a></li>
	                                        <li><a href="https://instagram.com/natural_history_museum"><img src="<%= currentDesign.getPath() + "/img/icons/instagram.png"%>" alt="Instagram"></a></li>
	                                        <li><a href="http://www.pinterest.com/nhmlondon"><img src="<%= currentDesign.getPath() + "/img/icons/pinterest.png"%>" alt="Pinterest" /></a></li>
	                                        <li><a href="https://plus.google.com/+NaturalHistoryMuseumLondon/posts"><img src="<%= currentDesign.getPath() + "/img/icons/gplus.png"%>" alt="GooglePlus" /></a></li>
	                                   	</ul>
                                    </li>
                                </ul>
                        </nav>
<% 
	final NewsletterSignUpHelper helper = new NewsletterSignUpHelper(properties, pageManager);
	
	if (helper.isComponentInitialised()) {
%>
	<div class="small-12 medium-12 large-3 columns footer-utility newslettersignup">
		<form action="<%= dynamicPageHelper.getProtocol() + hostPort  + pathForSignup %>/jcr:content.newslettersignup.html" method="get">
		  <fieldset>
            <legend><%= helper.getTitle() %></legend>
            	 <label class="item-label newslettersignup--footer-label" for="name">Full name</label>
                 <input class="item-input newslettersignup--footer-input" name="name" id="name" type="text" />
                 <label class="item-label newslettersignup--footer-label" for="email">Email address</label>
                 <input class="item-input newslettersignup--footer-input" name="email" id="email" type="email" />
                 <input type="submit" class="newslettersignup--footer-submit-button" value="Sign up &gt;" />
                 <input class="question" type="text" name="question">
           </fieldset>
           		<div class="errors"></div>
				<div class="footer-nav">
					<%= helper.getDataPolicy() %>
				</div>                             
        </form>
	</div>
	
<%		
	} else if (isOnEditMode || isOnDesignMode) {
%>
	<span class="wcmerror">
		The Newsletter Sign Up Component is not configured properly.
	</span>
<%
	} 
%>
                    </div>
                </div>
                <div class="small-12 medium-3 large-3 columns footer-info" title="Natural History Museum">
                    <p class="footer-info-text">&copy; The Trustees of the Natural History Museum, London</p>
                </div>
            </div>
        </footer>
<!-- END OF FOOTER -->