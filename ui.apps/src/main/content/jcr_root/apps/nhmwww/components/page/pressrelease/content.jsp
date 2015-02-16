<%@page session="false"
          import="com.day.cq.wcm.api.Page"%>
<%@page import="uk.ac.nhm.nhm_www.core.componentHelpers.PressReleaseHelper" %>
<%@page import="java.text.SimpleDateFormat" %>
<%@include file="/apps/nhmwww/components/global.jsp"%>
<cq:defineObjects />
<%
	PressReleaseHelper helper = new PressReleaseHelper(resource,properties);

%>
<div class="main-section">
<% if ( !helper.getIsComponentInitialised()) {%>
<h1>Page not initialised</h1>
<em>Please set the required page properties.</em>
<% } else { %>
<h2>Publish date: <%=new SimpleDateFormat("dd MMMM yyyy").format(helper.getPublishDate())%></h2> 
	<div class="small-12 large-text-left columns">
			<cq:include path="title" resourceType="nhmwww/components/functional/title"/>
	</div>
	<em><%=helper.getSummary() %></em>
	<%-- <cq:include path="title" resourceType="nhmwww/components/functional/title" />--%>
	<cq:include path="par" resourceType="foundation/components/parsys" />
</div>
<% } %>