<%@page session="false"%>
<%@include file="/apps/nhmwww/components/global.jsp"%>
<%@page import="uk.ac.nhm.nhm_www.core.componentHelpers.*"%>
<cq:defineObjects/>
<%
	TextHelper helper = new TextHelper(resource);
%>
<%
if (helper.isComponentInitialised())
{
%>
<%=helper.getText()%>
<%
}
else
{
%>
	<img class="cq-carousel-placeholder cq-block-lg-placeholder" src="/etc/designs/default/0.gif"/>
<%
}
%>
