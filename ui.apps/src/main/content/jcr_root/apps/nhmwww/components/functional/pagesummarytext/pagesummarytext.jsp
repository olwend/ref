<%@page session="false"
        import="uk.ac.nhm.nhm_www.core.componentHelpers.PressReleaseHelper" %>
<%@include file="/apps/nhmwww/components/global.jsp"%>
<cq:defineObjects />
<%
	PressReleaseHelper helper = new PressReleaseHelper(resource,properties);
%>
<span class="intro-text"><%=pageProperties.get("summary", String.class) %></span>