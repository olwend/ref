<%@include file="/libs/foundation/global.jsp"%>
<%@page session="false"
	import="com.day.cq.wcm.api.WCMMode, javax.jcr.*"%>

<cq:defineObjects />

<%  
    String title = properties.get("jcr:title", "Placeholder Title");
    String secondaryTitle = properties.get("jcr:secondaryTitle", "Placeholder secondary title");   
     %>

	<div id="LD-container" data-title="<%= title %>" data-secondaryTitle="<%= secondaryTitle %>">
	</div>

<cq:includeClientLib categories="nhmwww.ld-interactive-splash"/>
