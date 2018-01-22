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
<%@page session="false"
	import="uk.ac.nhm.core.componentHelpers.SocialSignupHelper"%>
<%@page
	import="uk.ac.nhm.core.componentHelpers.DynamicPageHelper"%>

	<%
		SocialSignupHelper helper = new SocialSignupHelper(properties, resource);
		DynamicPageHelper dynamicPageHelper = new DynamicPageHelper(resource, properties, request);
	%>

<cq:defineObjects />
<cq:includeClientLib categories="uk.ac.nhm.social-signup" />

<div class="social-signup--<%=helper.getRow()%>">
	<div class="row social-signup--title-container">
		<div class="small-12 medium-12 large-12 columns">
			<h3 class="social-signup--title"><%=helper.getTitle()%></h3>
		</div>
	</div>

	<div class="social-signup--container js-social-signup">

		<div class="row">
			<div class="small-12 medium-12 large-12 columns social-signup--description">
				<%=helper.getDataProtection()%>
			</div>
		</div>

		<div class="row">
			<form
				id="newsletter_signup"
				action="<%=dynamicPageHelper.getProtocol() + hostPort + pathForSignup%>/jcr:content.newslettersignup.html"
				method="get">
				<div class="small-12 medium-12 large-12 columns social-signup--form-container">
					<div class="social-signup--first-name social-signup--field">
						<div class="form-field firstname">
							<input type="text" name="firstname" class="item-input" placeholder="First name">
						</div>
					</div>
					<div class="social-signup--last-name social-signup--field">
						<div class="form-field lastname">
							<input type="text" name="lastname" class="item-input" placeholder="Surname">
						</div>
					</div>
					<div class="social-signup--email-address social-signup--field">
						<div class="form-field email">
							<input type="text" name="email" class="item-input" placeholder="Email address">
						</div>
					</div>
					<div class="social-signup--button-submit social-signup--field">
						<button class="button__newsletter-signup" type="submit">Sign up</button>
					</div>

					<input type="text" name="question" class="question">
					<input type="hidden" name="source" value="www.nhm.ac.uk">
					<input type="hidden" name="campaign" value="<%=helper.getCampaign()%>">

				</div>
			</form>
		</div>

		<div class="row">
			<div class="small-12 medium-12 large-12 columns social-signup--error-container">
				<div class="errors"></div>
			</div>
		</div>

		<div class="row">
			<div class="small-12 medium-12 large-12 columns social-signup--icon-container">
				<p><%=helper.getDescription()%></p>
				<a href="https://www.facebook.com/naturalhistorymuseum">
					<img src="/etc/designs/nhmwww/img/icons/facebook.png" alt="" class="social-signup--icon" />
				</a>
				<a href="https://twitter.com/NHM_London">
					<img src="/etc/designs/nhmwww/img/icons/twitter.png" alt="" class="social-signup--icon" />
				</a>
				<a href="https://www.youtube.com/user/naturalhistorymuseum">
					<img src="/etc/designs/nhmwww/img/icons/youtube.png" alt="" class="social-signup--icon" />
				</a>
				<a href="https://instagram.com/natural_history_museum">
					<img src="/etc/designs/nhmwww/img/icons/instagram-logo-white.png" alt="" class="social-signup--icon" />
				</a>
				<a href="http://www.pinterest.com/nhmlondon">
					<img src="/etc/designs/nhmwww/img/icons/pinterest.png" alt="" class="social-signup--icon" />
				</a>
				<a href="https://plus.google.com/+NaturalHistoryMuseumLondon/posts">
					<img src="/etc/designs/nhmwww/img/icons/gplus.png" alt="" class="social-signup--icon" />
				</a>
			</div>
		</div>

	</div>

</div>