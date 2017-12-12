
<%@include file="/apps/nhmwww/components/global.jsp"%>
<%@page session="false"
	import="uk.ac.nhm.core.componentHelpers.ENewsSignupHelper"
	import="uk.ac.nhm.core.componentHelpers.DynamicPageHelper"%>
<cq:defineObjects />
<cq:includeClientLib categories="uk.ac.nhm.enews-signup" />

<div class="text parbase section">
<%
	ENewsSignupHelper helper = new ENewsSignupHelper(properties, resource);
	DynamicPageHelper dynamicPageHelper = new DynamicPageHelper(resource, properties, request);
%>
<h2><%=helper.getTitle()%></h2>
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
		<div class="form-field firstname">
			<label for="firstname">First name *</label> <input type="text" name="firstname"
				class="item-input">
		</div>
		<div class="form-field lastname">
			<label for="lastname">Surname *</label> <input type="text" name="lastname"
				class="item-input">
		</div>
		<div class="form-field email">
			<label for="email">Email address *</label> <input type="text"
				name="email" class="item-input">
		</div>
		<input type="text" name="question" class="question"> <input
			type="hidden" name="campaign" value="<%=helper.getCampaign()%>">
		<p><%=helper.getDataProtection()%></p>
		<button class="button__enews-signup">Sign up</button>
		<div class="errors"></div>
	</form>
</div>