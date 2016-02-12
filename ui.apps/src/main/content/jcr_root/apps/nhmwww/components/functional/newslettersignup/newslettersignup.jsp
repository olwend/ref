<%@page session="false"%>
<%@page import="uk.ac.nhm.nhm_www.core.componentHelpers.NewsletterSignUpHelper"%>
<%@page import="uk.ac.nhm.nhm_www.core.componentHelpers.DynamicPageHelper"%>
<%@include file="/apps/nhmwww/components/global.jsp"%>
<% DynamicPageHelper dynamicPageHelper = new DynamicPageHelper(resource, properties, request); %>
<cq:includeClientLib categories="nhm-www.newsletter" />
<!-- START OF FOOTER -->
<footer class="main-footer">
            <div class="row cf">
                <div class="columns large-9 medium-9 framed-wrapper">
                    <div class="framed">
                        <nav class="footer-nav columns large-9">
                            <div class="cf">
                                <ul class="columns large-6 medium-6 small-6">
                                    <li><a href="/content/nhmwww/en/home/about-us.html">About us</a></li>
                                    <li><a href="/about-us/news.html">News</a></li>
                                    <li><a href="/content/nhmwww/en/home/press-office.html">Press office</a></li>
                                    <li><a href="/content/nhmwww/en/home/business-services.html">Business services</a></li>
									<li><a href="/content/nhmwww/en/home/about-us/careers.html">Careers</a></li>
                                    <li><a href="/content/nhmwww/en/home/about-us/governance.html">Governance</a></li>
                                    <li><a href="/content/nhmwww/en/home/business-services/touring-exhibitions.html">Touring Exhibitions</a></li>
                                </ul>
                                <ul class="columns large-6 medium-6 small-6">
                                	<li><a href="/about-us/privacy-policy.html">Privacy policy</a></li>
                                	<li><a href="/about-us/website-terms-conditions.html">Terms and conditions</a></li>
                                    <li><a href="/content/nhmwww/en/home/contact-us.html">Contact us</a></li>
                                    <li>Follow us</li>
                                    <li class="footer--icon-list-container">
										<ul class="small-block-grid-3 icon-list">
											<li><a href="https://www.facebook.com/naturalhistorymuseum"><img src="<%= currentDesign.getPath() + "/img/icons/facebook.png"%>" alt="Facebook"></a></li>
	                                        <li><a href="https://twitter.com/NHM_London"><img src="<%= currentDesign.getPath() + "/img/icons/twitter.png"%>" alt="Twitter"></a></li>
	                                        <li><a href="https://www.youtube.com/user/naturalhistorymuseum"><img src="<%= currentDesign.getPath() + "/img/icons/youtube.png"%>" alt="YouTube"></a></li>
	                                        <li><a href="https://instagram.com/natural_history_museum"><img src="<%= currentDesign.getPath() + "/img/icons/instagram.png"%>" alt="Instagram"></a></li>
	                                        <li><a href="http://www.pinterest.com/nhmlondon"><img src="<%= currentDesign.getPath() + "/img/icons/pinterest.png"%>" alt="Pinterest" /></a></li>
	                                        <li><a href="https://plus.google.com/+NaturalHistoryMuseumLondon/posts"><img src="<%= currentDesign.getPath() + "/img/icons/gplus.png"%>" alt="GooglePlus" /></a></li>
	                                   	</ul>
                                    </li>
                                </ul>
                            </div>
                        </nav>
<% 
	final NewsletterSignUpHelper helper = new NewsletterSignUpHelper(properties, pageManager);
	
	if (helper.isComponentInitialised()) {
%>
	<div class="footer-utility columns large-3 newslettersignup">
		<form action="<%= dynamicPageHelper.getProtocol() + hostPort  + pathForSignup %>/jcr:content.newslettersignup.html" method="get">
		  <fieldset>
            <legend><%= helper.getTitle() %></legend>
            	 <label class="item-label" for="name">Full name</label>
                 <input class="item-input" name="name" id="name" type="text" />
                 <label class="item-label" for="email">Email address</label>
                 <input class="item-input" name="email" id="email" type="email" />
                 <input type="submit" value="Sign up &gt;" />
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
                <div class="footer-info columns large-3 medium-3" title="Natural History Museum">
                    <p>&copy; The Trustees of the Natural History Museum, London </p>
                </div>
            </div>
        </footer>


  <div id="feedback-bar" data-module="feedback-bar">

    <div class="container">

        <div class="row">
            <div class="span11 small-11 columns">
					<p class="mobile">Leave <a href="https://www.surveymonkey.com/r/nhm-site-redesign" target="_blank">feedback</a> about our new website. <a href="/about-us/we-are-redeveloping-our-website.html" target="_blank">More information</a>.</p>
                	<p class="tablet desktop">You are viewing our new website - we would love to hear your <a href="https://www.surveymonkey.com/r/nhm-site-redesign" target="_blank">feedback</a>. Find out about our <a href="/about-us/we-are-redeveloping-our-website.html" target="_blank">website redevelopment project</a>.</p>
            </div>
             <div class="span1 small-1 columns">
                <a class="feedback-close" title="Close feedback bar">
						<i class="ico svg-ico" data-svg-src="/etc/designs/nhmwww/img/svg-icons/icon_l_general_close.svg" data-svg-title="icon__general_close" data-stroke-width="8" data-base-color="#EEEEEE"></i>
					</a>              
            </div>
        </div>

    </div>


</div>
<!-- END OF FOOTER -->