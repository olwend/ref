<%--

  Row 2 Cells 1col then 2 col component.

  This component add a single row with two cells one of 33% followed by 66%

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
	<div class="large-4 medium-4 columns" <% if(!helper.isEqualizedDisabled()) { %>data-equalizer-watch<% } %>>
		<cq:include path="par" resourceType="foundation/components/parsys" />
	</div>
	<div class="large-8 medium-8 columns" <% if(!helper.isEqualizedDisabled()) { %>data-equalizer-watch<% } %>>
		<cq:include path="par2" resourceType="foundation/components/parsys" />
	</div>
</div>