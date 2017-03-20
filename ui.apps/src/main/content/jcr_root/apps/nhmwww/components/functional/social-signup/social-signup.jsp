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
  import="uk.ac.nhm.nhm_www.core.componentHelpers.SocialSignupHelper"%>
<%@page
  import="uk.ac.nhm.nhm_www.core.componentHelpers.DynamicPageHelper"%>
<cq:defineObjects />
<cq:includeClientLib categories="uk.ac.nhm.social-signup" />

<div class="social-signup">
  <div class="text parbase section social-signup--title-wrapper">
    <%
      SocialSignupHelper helper = new SocialSignupHelper(properties, resource);
      DynamicPageHelper dynamicPageHelper = new DynamicPageHelper(resource, properties, request);
    %>
    <div class="row social-signup--title-row">
      <div class="small-12 columns">
        <h2 class="social-signup--title-header"><%=helper.getTitle()%></h2>
      </div>
    </div>
  </div>

  <div class="js-social-signup social-signup--container">
      <div class="row">
        <div class="small-12 social-signup--description">
          <%=helper.getDataProtection()%>
        </div>
      </div>
    <form
      id="newsletter_signup"
      action="<%=dynamicPageHelper.getProtocol() + hostPort + pathForSignup%>/jcr:content.newslettersignup.html"
      method="get">
      <div class="row">
        <div class="small-12 medium-6 large-3 columns social-signup--form__first-name">
          <div class="form-field firstname">
            <input type="text" name="firstname" class="item-input" placeholder="First name">
          </div>
        </div>
        <div class="small-12 medium-6 large-3 columns social-signup--form__last-name">
          <div class="form-field lastname">
            <input type="text" name="lastname" class="item-input" placeholder="Surname">
          </div>
        </div>
        <div class="small-12 medium-9 large-3 columns social-signup--form__email-address">
          <div class="form-field email">
            <input type="text" name="email" class="item-input" placeholder="Email address">
          </div>
        </div>
        <div class="small-12 medium-3 large-3 columns social-signup--form__button-submit">
          <button class="button__newsletter-signup" type="submit">Sign up</button>
        </div>
      </div>
      <input type="text" name="question" class="question"> 
      <input type="hidden" name="campaign" value="<%=helper.getCampaign()%>">
      <div class="row">
        <div class="small-12 columns">
          <div class="errors"></div>
        </div>
      </div>
    </form>
    <div class="row">
      <div class="small-12 social-signup--link-icons">
        <p><%=helper.getDescription()%></p>
        <a href="https://www.facebook.com/naturalhistorymuseum" ><img src="/etc/designs/nhmwww/img/icons/facebook.png" alt="" class="social-signup--img__social" /></a>
        <a href="https://twitter.com/NHM_London" ><img src="/etc/designs/nhmwww/img/icons/twitter.png" alt="" class="social-signup--img__social" /></a>
        <a href="https://www.youtube.com/user/naturalhistorymuseum" ><img src="/etc/designs/nhmwww/img/icons/youtube.png" alt="" class="social-signup--img__social" /></a>
        <a href="https://instagram.com/natural_history_museum" ><img src="/etc/designs/nhmwww/img/icons/instagram-logo-white.png" alt="" class="social-signup--img__social" /></a>
        <a href="http://www.pinterest.com/nhmlondon" ><img src="/etc/designs/nhmwww/img/icons/pinterest.png" alt="" class="social-signup--img__social" /></a>
        <a href="https://plus.google.com/+NaturalHistoryMuseumLondon/posts" ><img src="/etc/designs/nhmwww/img/icons/gplus.png" alt="" class="social-signup--img__social" /></a>
      </div>
    </div>
  </div>
</div>