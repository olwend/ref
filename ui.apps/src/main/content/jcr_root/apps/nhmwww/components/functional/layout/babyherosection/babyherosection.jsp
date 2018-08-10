<%
%><%@include file="/libs/foundation/global.jsp"%><%
%><%@page session="false" %><%
%>
<%@page session="false" 
			import="uk.ac.nhm.core.componentHelpers.RowHelper"%>
<%
	RowHelper helper = new RowHelper(properties);
	String additionalCss = properties.get("additionalCss", "");
%>
<cq:defineObjects />
<div class="row <%=additionalCss %>" <% if(!helper.isEqualizedDisabled()) { %>data-equalizer <% } %>>
	<div class="large-12 columns">
		<cq:include path="par2" resourceType="foundation/components/parsys" />
	</div>
</div>