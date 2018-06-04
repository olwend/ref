<%--
	ADOBE CONFIDENTIAL
	__________________

	 Copyright 2012 Adobe Systems Incorporated
	 All Rights Reserved.

	NOTICE:  All information contained herein is, and remains
	the property of Adobe Systems Incorporated and its suppliers,
	if any.  The intellectual and technical concepts contained
	herein are proprietary to Adobe Systems Incorporated and its
	suppliers and are protected by trade secret or copyright law.
	Dissemination of this information or reproduction of this material
	is strictly forbidden unless prior written permission is obtained
	from Adobe Systems Incorporated.
--%>
<%@include file="/apps/nhmwww/components/global.jsp"%>
<%@page session="false" import="uk.ac.nhm.core.componentHelpers.SocialSignupHelper"%>
<%@page import="uk.ac.nhm.core.componentHelpers.DynamicPageHelper"%>

<%
	SocialSignupHelper helper = new SocialSignupHelper(properties, resource);
	DynamicPageHelper dynamicPageHelper = new DynamicPageHelper(resource, properties, request);
%>

<cq:defineObjects />
<cq:includeClientLib categories="uk.ac.nhm.social-signup" />

<div class="social-signup--container">

	<div class="social-signup--title-container">
		<h3 class="social-signup--title"><%=helper.getTitle()%></h3>
	</div>

	<div class="js-social-signup social-signup--form-container">

		<div class="social-signup--description">
			<%=helper.getDataProtection()%>
		</div>

		<form id="newsletter_signup" action="<%=dynamicPageHelper.getProtocol() + hostPort + pathForSignup%>/jcr:content.newslettersignup.html" method="get">

			<div class="social-signup--input-container">
				<div class="social-signup--firstname form-field firstname">
					<input type="text" role="textbox" name="firstname" class="item-input" placeholder="First name" aria-label="input first name">
				</div>

				<div class="social-signup--lastname form-field lastname">
					<input type="text" role="textbox" name="lastname" class="item-input" placeholder="Surname" aria-label="input surname">
				</div>

				<div class="social-signup--email form-field email">
					<input type="text" role="textbox" name="email" class="item-input" placeholder="Email address" aria-label="input email">
				</div>

				<div class="social-signup--submit">
					<button class="button__newsletter-signup" role="button" aria-label="input button" type="submit">Sign up</button>
				</div>
			</div>

 			<input type="text" name="question" class="question"> 
			<input type="hidden" name="source" value="www.nhm.ac.uk">
			<input type="hidden" name="campaign" value="<%=helper.getCampaign()%>">

			<div class="social-signup--error-container">
				<div role="alert" class="errors"></div>
			</div>

			<div class="social-signup--link-icons">
				<p><%=helper.getDescription()%></p>
				<a href="https://www.facebook.com/naturalhistorymuseum">
					<img src="/etc/designs/nhmwww/img/icons/facebook.png" class="social-signup--img__social" alt="facebook icon"/>
				</a>
				<a href="https://twitter.com/NHM_London">
					<img src="/etc/designs/nhmwww/img/icons/twitter.png" class="social-signup--img__social" alt="twitter icon" />
				</a>
				<a href="https://www.youtube.com/user/naturalhistorymuseum">
					<img src="/etc/designs/nhmwww/img/icons/youtube.png" class="social-signup--img__social" alt="you tube icon" />
				</a>
				<a href="https://instagram.com/natural_history_museum">
					<img src="/etc/designs/nhmwww/img/icons/instagram-logo-white.png" class="social-signup--img__social "alt="instagram icon" />
				</a>
				<a href="http://www.pinterest.com/nhmlondon">
					<img src="/etc/designs/nhmwww/img/icons/pinterest.png" class="social-signup--img__social" alt="pinterest icon" />
				</a>
				<a href="https://plus.google.com/+NaturalHistoryMuseumLondon/posts">
					<img src="/etc/designs/nhmwww/img/icons/gplus.png" class="social-signup--img__social" alt="google plus icon"/>
				</a>
			</div>

		</form>

	</div>

</div>
