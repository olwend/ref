<%@page import="uk.ac.nhm.nhm_www.core.componentHelpers.ScientistProfileHelper"%>
<%@include file="/apps/nhmwww/components/global.jsp"%> 
<%
	final ScientistProfileHelper helper = new ScientistProfileHelper(resource);

	final String personalStatement = helper.getStatement();

	if (personalStatement != null) {
%>
<div class="small-12 medium-12 large-12 columns "><!-- get Property here -->
	<h4>Summary</h4>
	<p><%= personalStatement %></p>
</div>
<%
	}
%>