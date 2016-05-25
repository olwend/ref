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
          import="uk.ac.nhm.nhm_www.core.componentHelpers.HelperBase, com.day.cq.commons.ImageResource,
                  com.day.cq.wcm.api.WCMMode, com.day.cq.wcm.foundation.Placeholder, javax.jcr.*,
                  uk.ac.nhm.nhm_www.core.componentHelpers.TwentyTwentyImageHelper"%><%
%>
<cq:defineObjects />

<%
	TwentyTwentyImageHelper helper = new TwentyTwentyImageHelper(properties, resource, resourceResolver, request, xssAPI);
%>



<p><%= helper.getPath() %></p>
<p><%= helper.getOriginalImagePathBefore() %></p>
<p><%= helper.getOriginalImagePathAfter() %></p>


<div class="twentytwenty-image-slider--container">
	<img src='<%= helper.getOriginalImagePathBefore() %>' />
	<img src='<%= helper.getOriginalImagePathAfter() %>' />
</div>


	<cq:includeClientLib categories="uk.ac.nhm.twentytwenty-image-slider"/>
	<script>
		$(document).ready(function(){
			$(".twentytwenty-image-slider--container").twentytwenty({
				default_offset_pct: 0.3,
			});
		});
	</script>