<%@page session="false"%>
<%@include file="/apps/nhmwww/components/global.jsp"%>
<%@page import="uk.ac.nhm.core.componentHelpers.*"%>
<cq:defineObjects/>
<cq:includeClientLib categories="nhm-www.foundationreflow"/>
<%
TwitterHelper helper = new TwitterHelper(slingRequest);
%>
<% if(!helper.isConfigured()) { %>
	<div class="row">
		<h4>Twitter timeline</h4>
	</div>
<% } else { %>
	<a class="twitter-timeline" data-dnt="true" height="<%=helper.getHeight() %>" data-widget-id="<%=helper.getWidgetId() %>"></a>
	<script>
		!function(d,s,id) {
			var js,
				fjs = d.getElementsByTagName(s)[0],
				p = /^http:/.test(d.location) ? 'http' : 'https';
			
			if(!d.getElementById(id)) { 
				js = d.createElement(s);
				js.id = id;
				js.src = p+"://platform.twitter.com/widgets.js";
				fjs.parentNode.insertBefore(js,fjs);
			}
		} (document,"script","twitter-wjs");
	</script>
<% } %>

