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
	import="uk.ac.nhm.nhm_www.core.componentHelpers.HelperBase"%>
<%
%>
<cq:defineObjects />
<cq:includeClientLib categories="uk.ac.nhm.enews-signup" />

<%  HelperBase helper = new HelperBase(); %>

<form method="get"
	action="http://www.nhm.ac.uk//jcr:content.newslettersignup.html"
	novalidate="novalidate">
	<div class="form-field firstname">
		<label for="name">Full name</label> <input type="text" name="name"
			class="item-input">
	</div>
	<div class="form-field email">
		<label for="email">Email address</label> <input type="text"
			name="email" class="item-input">
	</div>
	<input type="text" name="question" class="question">
	<button class="submit arrow">Sign up</button>
	<div class="errors"></div>
</form>