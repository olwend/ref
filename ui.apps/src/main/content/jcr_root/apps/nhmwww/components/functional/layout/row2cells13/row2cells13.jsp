<%--

  Row 2 Cells 1col then 3 col component.

  This component add a single row with two cells one of 25% followed by 75%

--%><%
%><%@include file="/libs/foundation/global.jsp"%><%
%>
<%@page session="false" 
			import="uk.ac.nhm.core.componentHelpers.RowHelper"%>
<%
%>
<cq:defineObjects />
<%
	RowHelper helper = new RowHelper(properties);
%>
<div class="row" <% if(!helper.isEqualizedDisabled()) { %>data-equalizer <% } %>>
	<div class="large-3 medium-3 columns" <% if(!helper.isEqualizedDisabled()) { %>data-equalizer-watch<% } %>>
		<cq:include path="par" resourceType="foundation/components/parsys" />
	</div>
	<div class="large-9 medium-9 columns" <% if(!helper.isEqualizedDisabled()) { %>data-equalizer-watch<% } %>>
		<cq:include path="par2" resourceType="foundation/components/parsys" />
	</div>
</div>