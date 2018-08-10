<%@include file="/apps/nhmwww/components/global.jsp"%>
<%@page session="false"
	import="uk.ac.nhm.core.componentHelpers.HelperBase,
		com.day.cq.commons.ImageResource,
		com.day.cq.wcm.api.WCMMode,
		com.day.cq.wcm.foundation.Placeholder,
		javax.jcr.*,
		uk.ac.nhm.core.componentHelpers.TwentyTwentyImageHelper"%>

<cq:defineObjects />

<%
	TwentyTwentyImageHelper helper = new TwentyTwentyImageHelper(properties, resource, resourceResolver, request, xssAPI);
%>

<% if(helper.isInitialised() == false) { %>
	<div class="row">
		<h4>Image slider</h4>
		Required fields:
		<ul>
			<li>Before</li>
			<li>After</li>
		</ul>
	</div>
<% } else { %>
	<div class="twentytwenty-image-slider--container">
		<img src='<%= helper.getOriginalImagePathBefore() %>' alt='<%=helper.getAltBefore() %>' />
		<img src='<%= helper.getOriginalImagePathAfter() %>' alt='<%=helper.getAltAfter() %>' />
	</div>
	
	<cq:includeClientLib categories="uk.ac.nhm.twentytwenty-image-slider"/>
	
	<script>
		$(document).ready(function(){
			$(".twentytwenty-image-slider--container").twentytwenty({
				default_offset_pct: 0.85,
			});
		});
	</script>
<% } %>
