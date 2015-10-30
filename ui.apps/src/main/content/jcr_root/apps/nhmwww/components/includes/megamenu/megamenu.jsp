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
                        <li class="nav-list__item has-children first visit">
                            <a href="/content/nhmwww/en/home/visit.html">Visit</a>
                            <ul class="nav-list level-2">
                                <div class="dropdown-section">
                                    <li class="nav-list__item mobile touchonly first">
                                        <a href="/content/nhmwww/en/home/visit.html">Visit home</a>
                                    </li>
                                    <li class="nav-list__item ">
                                        <a href="/visit/whats-on/day.html">What's on</a>
                                       
                                    </li>
                                    <li class="nav-list__item">
                                        <a href="/content/nhmwww/en/home/visit/exhibitions.html">Exhibitions and attractions</a>
                                    </li>
                                    <!-- <li class="nav-list__item">
                                        <a href="#">Getting here</a>
                                    </li> -->
                                    <li class="nav-list__item">
                                        <a href="/content/nhmwww/en/home/visit/galleries-and-museum-map.html">Galleries and Museum map</a>
                                    </li>
                                    <li class="nav-list__item">
                                        <a href="/content/nhmwww/en/home/visit/eat-drink-and-shop.html">Eat, drink and shop</a>
                                    </li>
                                    <li class="nav-list__item">
                                        <a href="/content/nhmwww/en/home/visit/facilities-and-access.html">Facilities and access</a>
                                    </li>
                                
                                   <!--  <li class="nav-list__item mobile">
                                        <a href="#">Natural History Museum at Tring</a>
                                    </li> -->
                                </div>
                                <div class="dropdown-section">
                                
                                     <li class="nav-list__item">
                                        <a href="/content/nhmwww/en/home/visit/getting-here.html">Getting here</a>
                                    	<p class="tablet desktop">Our main entrance is on Cromwell Road, just five minutes' walk from South Kensington Tube station.</p>
                                    </li>
                                    <li class="nav-list__item">
                                        <a href="/visit/tring.html">The Museum at Tring</a> 
                                    	<p class="tablet desktop">Visit our Museum in Hertfordshire.</p>
                                    </li>
                                </div>
                                <div class="dropdown-section dropdown-section--thumb">
                                    <li class="nav-list__item">

                                    <a href="/content/nhmwww/en/home/visit/getting-here.html"><img src="https://maps.googleapis.com/maps/api/staticmap?size=360x200&markers=color:red|Natural%20History%20Museum,Cromwell%20Road,%20London" alt="Natural History Museum"></a>
 

                            <!--  <iframe width="360" height="200" frameborder="0" style="border:0" src="https://www.google.com/maps/embed/v1/place?q=Natural%20History%20Museum%2C%20Cromwell%20Road%2C%20London%2C%20United%20Kingdom&key=AIzaSyC09eunz5fyve_HGt3DQGRQzEpQT1A1HbA"></iframe>  -->
                                    </li>
                                </div>
                            </ul>
                        </li>
                        <li class="nav-list__item">
                            <a href="/content/nhmwww/en/home/discover.html">Discover</a>
                        </li>
                        <li class="nav-list__item has-children take-part">
                            <a href="/content/nhmwww/en/home/take-part.html">Take part</a>
                            <ul class="nav-list level-2">
                                <div class="dropdown-section">
                                	<li class="nav-list__item mobile touchonly first">
                                        <a href="/content/nhmwww/en/home/take-part.html">Take part home</a>
                                    </li>
                                    <li class="nav-list__item">
                                        <a href="/content/nhmwww/en/home/take-part/citizen-science.html">Citizen science</a>
                                       <!--  <ul class="nav-list level-3">
                                            <li class="nav-list__item">
                                                <a href="#">Current projects</a>
                                            </li>
                                            <li class="nav-list__item">
                                                <a href="#">Resources &amp; guides</a>
                                             </li>
                                        </ul> -->
                                    </li>
                                    <li class="nav-list__item">
                                        <a href="/content/nhmwww/en/home/take-part/identify-nature.html">Identify nature</a>
                                        <!--  <ul class="nav-list level-3">
                                            <li class="nav-list__item">
                                                <a href="#">Forums</a>
                                            </li>
                                            <li class="nav-list__item">
                                                <a href="#">Seasonal guides</a>
                                             </li>
                                        </ul> -->
                                    </li>
                                    <li class="nav-list__item">
                                        <a href="/content/nhmwww/en/home/take-part/centre-for-uk-biodiversity.html">Centre for UK Biodiversity</a>
                                    </li>
                                    <li class="nav-list__item">
                                        <a href="/content/nhmwww/en/home/take-part/volunteer.html">Volunteer</a>
                                    </li>
                                </div>
                                <div class="dropdown-section">
                                    <li class="nav-list__item">
                                        <a href="/content/nhmwww/en/home/take-part/identification-trainers-for-the-future.html">Training</a>
                                    </li>
                                    
                                    <li class="nav-list__item">
                                        <a href="/content/nhmwww/en/home/take-part/wildlife-photographer-of-the-year-competition.html">Wildlife Photographer<br>of the Year Competition</a>
                                    </li>
                                </div>
                                <div class="dropdown-section dropdown-section--thumb">
                                    <li class="nav-list__item">
	                                    <a href="/content/nhmwww/en/home/take-part.html">
	                                        <div class="mega-thumb">
		                                        <img src="/etc/designs/nhmwww/img/megamenu/cta-header-take-part.jpg" alt="Get involved with nature">
	                                            <div class="mega-thumb-caption take-part">
	                                                   <p>
	                                                      Get involved with nature 
	                                                   </p>
	                                            </div>
	                                        </div>
	                                    </a>
                                    </li>
                                </div>
                            </ul>
                        </li>
                        <li class="nav-list__item has-children support-us">
                            <a href="/content/nhmwww/en/home/support-us.html">Support us</a>
                            <ul class="nav-list level-2">
                                <div class="dropdown-section">
                                	<li class="nav-list__item mobile touchonly first">
                                        <a href="/content/nhmwww/en/home/support-us.html">Support us home</a>
                                    </li>
                                    <li class="nav-list__item">
                                        <a href="/content/nhmwww/en/home/support-us/membership.html">Membership</a>
                                        <ul class="nav-list level-3">
                                            <li>
                                                <a class="nav-button arrow" data-gtm="CTA" href="/content/nhmwww/en/home/support-us/become-a-member.html ">Become a Member</a>
                                            </li>
                                        </ul>
                                    </li>
                                <li class="nav-list__item">
                                        <a href="/content/nhmwww/en/home/support-us/why-support-us.html">Why support us?</a>
                                </li>


                                </div>
                                <div class="dropdown-section">
                                    <li class="nav-list__item">
                                        <a href="/content/nhmwww/en/home/support-us/give.html">Give</a>
                                        <ul class="nav-list level-3">
                                            <li>
                                                <a class="nav-button arrow" data-gtm="CTA" href="https://www.justgiving.com/naturalhistorymuseum/">Donate with JustGiving
                                                 </a>
                                            </li>
                                            <li class="nav-list__item">
                                                <a href="/content/nhmwww/en/home/support-us/patronage.html">Patronage</a>
                                            </li>
                                            <li class="nav-list__item">
                                                <a href="/content/nhmwww/en/home/support-us/corporate-partnerships.html">Corporate partnerships</a>
                                            </li>
                                            <li class="nav-list__item">
                                                <a href="/content/nhmwww/en/home/support-us/legacy-giving.html">Legacy giving</a>
                                            </li>
                                        </ul>
                                    </li>
                                </div>
                               
                                 <div class="dropdown-section dropdown-section--thumb">
                                 	<li class="nav-list__item">
	                                    <a href="/content/nhmwww/en/home/support-us.html">
	                                 		<div class="mega-thumb">
	                                        	<img src="/etc/designs/nhmwww/img/megamenu/cta-header-support-us.jpg" alt="Support our vital work">
                                        		<div class="mega-thumb-caption support-us">
                                                    <p>
                                                       Support our vital work 
                                                    </p>
                                            	</div>
	                                        </div>
	                                   	</a>
                                   	</li>  
                               	</div>
                            </ul>
                        </li>
                        <li class="nav-list__item has-children shop">
                            <a href="http://www.nhmshop.co.uk">Shop</a>
                            <ul class="nav-list level-2">
                                <div class="dropdown-section">
                                	<li class="nav-list__item mobile touchonly first">
                                        <a href="http://www.nhmshop.co.uk">Shop home</a>
                                    </li>
                                    <li class="nav-list__item">
                                        <a href="http://www.nhmshop.co.uk/prints.html?utm_source=nhm.ac.uk&utm_medium=referral&utm_campaign=nhmcomm-prints-wpy&utm_content=meganav-link-1">Wildlife Photographer of the Year 2015 prints</a>
                                    </li>
                                    <li class="nav-list__item">
                                        <a href="http://www.nhmshop.co.uk/dinosaurs.html?utm_source=nhm.ac.uk&utm_medium=referral&utm_campaign=nhmcomm-dinosaurs&utm_content=meganav-link-2">Dinosaur toys</a>
                                       
                                    </li>
                                    <li class="nav-list__item">
                                        <a href="http://www.nhmshop.co.uk/souvenirs.html?utm_source=nhm.ac.uk&utm_medium=referral&utm_campaign=nhmcomm-souvenirs&utm_content=meganav-link-3">Museum souvenirs</a>
                                    </li>
                                    <li class="nav-list__item">
                                        <a href="http://www.nhmshop.co.uk/christmas-gifts.html?utm_source=nhm.ac.uk&utm_medium=referral&utm_campaign=nhmcomm-christmas&utm_content=meganav-link-4">Christmas shop</a>
                                    </li>
                                </div>
                                
                                <div class="dropdown-section">
                                    <p></p>
                                    <p class="nav-callout tablet desktop">
                                        Thousands of gifts inspired by the natural world, with free next-day UK delivery for orders over £50. All sales support the Museum's work.</p>
                                    </div>
                                
                                  <div class="dropdown-section dropdown-section--thumb">
                                  	<li class="nav-list__item">
                                  		<a href="http://www.nhmshop.co.uk/christmas-gifts.html?utm_source=nhm.ac.uk&utm_medium=referral&utm_campaign=nhmcomm-christmas&utm_content=meganav-image-1">
                                  			<div class="mega-thumb">
	                                        	<img src="/etc/designs/nhmwww/img/megamenu/cta-header-shop.jpg" alt="Christmas Boutique">
	                                        	<div class="mega-thumb-caption shop">
                                                    <p>
                                                       Christmas Boutique
                                                    </p>
	                                            </div>
	                                        </div>
	                                   	</a>
                                   	</li>  
                                </div>
                            </ul>
                        </li>
                        <li class="nav-list__item has-children schools">
                            <a href="/content/nhmwww/en/home/schools.html">Schools</a>
                            <ul class="nav-list level-2">
                                <div class="dropdown-section">
                                	<li class="nav-list__item mobile touchonly first">
                                        <a href="/content/nhmwww/en/home/schools.html">Schools home</a>
                                    </li>
                                    <li class="nav-list__item first">
                                        <a href="/content/nhmwww/en/home/schools/things-to-do-schools.html">Things to do</a>
                                    </li>
                                    <li class="nav-list__item first">
                                        <a href="/content/nhmwww/en/home/schools/essential-information-schools.html">Essential information</a>
                                       
                                    </li>
                                     <li class="nav-list__item first">
                                        <a href="/content/nhmwww/en/home/schools/teaching-resources.html">Teaching resources</a>
                                    </li>
                                </div>
                                
                                <div class="dropdown-section">
                                    <p></p>
                                    <p class="nav-callout tablet desktop">
                                        Book a visit for your school on<br />
                                         <span>+44 (0)20 7942 5555</span>.<br />
                                        Lines are open Monday-Friday from 8.30-16.00 during term time and 10.00-13.00 during school holidays.</p>
                                    </div>
                                
                                  <div class="dropdown-section dropdown-section--thumb">
                                  	<li class="nav-list__item">
                                  		<a href="/content/nhmwww/en/home/schools.html">
                                  			<div class="mega-thumb">
	                                        	<img src="/etc/designs/nhmwww/img/megamenu/cta-header-schools.jpg" alt="Plan your school visit">
	                                        	<div class="mega-thumb-caption schools">
                                                    <p>
                                                       Plan your school visit
                                                    </p>
	                                            </div>
	                                        </div>
	                                   	</a>
                                   	</li>  
                                </div>
                            </ul>
                        </li>
                        <li class="nav-list__item has-children">
                            <a href="/content/nhmwww/en/home/our-science.html">Our science</a>
                            <ul class="nav-list level-2 science">
                            	<li class="nav-list__item mobile touchonly first">
                                        <a href="/content/nhmwww/en/home/our-science.html" class="home">
                                        <i class="ico svg-ico" data-svg-src="/etc/designs/nhmwww/img/svg-icons/icon_l_branding_n.svg" data-svg-title="icon__science" data-alt="Section home" data-base-color="#357900" data-hover-color="#357900" data-stroke-width="4" data-fallback="/etc/designs/nhmwww/img/icons/nav-microscope.png"></i>
                                        Our science home</a>
                                    </li>
                                <li class="nav-list__item first">
                                    <a href="/content/nhmwww/en/home/our-science/our-work.html" class="works">
                                        <i class="ico svg-ico" data-svg-src="/etc/designs/nhmwww/img/svg-icons/icon_l_service_science.svg" data-svg-title="icon__science" data-alt="Microscope" data-base-color="#357900" data-hover-color="#357900" data-stroke-width="3" data-hover-state="yes" data-fallback="/etc/designs/nhmwww/img/icons/nav-microscope.png"></i>
                                        Our work
                                    </a>
                                </li>
                                <li class="nav-list__item">
                                    <a href="/our-science/science-news.html" class="news">
                                        <i class="ico svg-ico" data-svg-src="/etc/designs/nhmwww/img/svg-icons/icon_l_media_news.svg" data-svg-title="icon__news" data-alt="Newspaper" data-base-color="#357900" data-hover-color="#357900" data-stroke-width="3" data-hover-state="yes" data-fallback="/etc/designs/nhmwww/img/icons/nav-newspaper.png"></i>
                                        News
                                    </a>
                                </li>
                                <li class="nav-list__item">
                                    <a href="/content/nhmwww/en/home/our-science/science-events.html" class="events">
                                        <i class="ico svg-ico" data-svg-src="/etc/designs/nhmwww/img/svg-icons/icon_l_feature_calendar.svg" data-svg-title="icon__events-calendar" data-alt="Newspaper" data-base-color="#357900" data-hover-color="#357900" data-stroke-width="3" data-hover-state="yes" data-fallback="/etc/designs/nhmwww/img/icons/nav-calendar.png"></i>
                                        Science events
                                    </a>
                                </li>
                                <li class="nav-list__item">
                                    <a href="/content/nhmwww/en/home/our-science/collections.html" class="collections">
                                        <i class="ico svg-ico" data-svg-src="/etc/designs/nhmwww/img/svg-icons/icon_l_service_collection.svg" data-svg-title="icon__collections" data-alt="Collections" data-base-color="#357900" data-hover-color="#357900" data-stroke-width="3" data-hover-state="yes" data-fallback="/etc/designs/nhmwww/img/icons/nav-science.png"></i>
                                        Collections
                                    </a>
                                </li>
                                <li class="nav-list__item">
                                    <a href="/content/nhmwww/en/home/our-science/data.html" class="databases">
                                        <i class="ico svg-ico" data-svg-src="/etc/designs/nhmwww/img/svg-icons/icon_l_service_database.svg" data-svg-title="icon__database" data-alt="Database" data-base-color="#357900" data-hover-color="#357900" data-stroke-width="3" data-hover-state="yes" data-fallback="/etc/designs/nhmwww/img/icons/nav-bugs.png"></i>
                                        Data
                                    </a>
                                </li>
                                <li class="nav-list__item">
                                    <a href="/content/nhmwww/en/home/our-science/departments-and-staff.html" class="staff">
                                        <i class="ico svg-ico" data-svg-src="/etc/designs/nhmwww/img/svg-icons/icon_l_service_staff.svg" data-svg-title="icon_staff" data-alt="Staff" data-base-color="#357900" data-hover-color="#357900" data-stroke-width="3" data-hover-state="yes" data-fallback="/etc/designs/nhmwww/img/icons/nav-member.png"></i>
                                Departments<br>and staff
                                    </a>
                                </li>
                                <li class="nav-list__item">
                                    <a href="/content/nhmwww/en/home/our-science/departments-and-staff/core-research-labs.html" class="facilities">
                                        <i class="ico svg-ico" data-svg-src="/etc/designs/nhmwww/img/svg-icons/icon_l_service_facilities.svg" data-svg-title="icon__facilities" data-alt="Facilities" data-base-color="#357900" data-hover-color="#357900" data-stroke-width="3" data-hover-state="yes" data-fallback="/etc/designs/nhmwww/img/icons/nav-facilities.png"></i>
                                        Core research labs and consulting
                                    </a>
                                </li>
                                <li class="nav-list__item">
                                    <a href="/content/nhmwww/en/home/our-science/courses-and-students.html" class="students">
                                        <i class="ico svg-ico" data-svg-src="/etc/designs/nhmwww/img/svg-icons/icon_l_service_courses.svg" data-svg-title="icon__students" data-alt="Students" data-base-color="#357900" data-hover-color="#357900" data-stroke-width="3" data-hover-state="yes" data-fallback="/etc/designs/nhmwww/img/icons/nav-student.png"></i>
                                        Courses and students
                                    </a>
                                </li>
                            </ul>
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
	                                            <input class="item-input" name="email" type="text" />
	                                        </div>
	                                        <input class="question" type="text" name="question">
	                                        <button class="submit arrow">Sign up</button>
	                                        <div class="errors"></div>
	                                    </form>
	                                    <label>We will use your personal information in accordance with the Data Protection Act 1998. <a href="/my-nhm/privacy-policy/index.html">View our privacy policy.</a></label>
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