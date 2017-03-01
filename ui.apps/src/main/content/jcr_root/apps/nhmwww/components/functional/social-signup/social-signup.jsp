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
  import="uk.ac.nhm.nhm_www.core.componentHelpers.ENewsSignupHelper"%>
<%@page
  import="uk.ac.nhm.nhm_www.core.componentHelpers.DynamicPageHelper"%>
<cq:defineObjects />
<cq:includeClientLib categories="uk.ac.nhm.enews-signup" />

<div class="text parbase section">
<%
  ENewsSignupHelper helper = new ENewsSignupHelper(properties, resource);
  DynamicPageHelper dynamicPageHelper = new DynamicPageHelper(resource, properties, request);
%>
<p><%=helper.getTitle()%></p>
<%
  if (helper.getDescription() != null) {
%>
<p><%=helper.getDescription()%></p>
<%
  }
%>
</div>
<div class="js-enews-signup enews-signup-form-container">
  <form
    action="<%=dynamicPageHelper.getProtocol() + hostPort + pathForSignup%>/jcr:content.newslettersignup.html"
    method="get">
    <div class="row">
      <div class="small-12">
        <p><%=helper.getDataProtection()%></p>
      </div>
    </div>
    <div class="row">
      <div class="small-12 medium-6 large-3 columns" style="padding-right: 10px;">
        <div class="form-field firstname">
          <input type="text" name="firstname" class="item-input" placeholder="First name">
        </div>
      </div>
      <div class="small-12 medium-6 large-3 columns" style="padding-right: 10px;">
        <div class="form-field lastname">
          <input type="text" name="lastname" class="item-input" placeholder="Surname">
        </div>
      </div>
      <div class="small-12 medium-9 large-3 columns" style="padding-right: 10px;">
        <div class="form-field email">
          <input type="text" name="email" class="item-input" placeholder="Email address">
        </div>
      </div>
      <div class="small-12 medium-3 large-3 columns">
        <button class="button__enews-signup" style="width: 100%;">Sign up</button>
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
</div>