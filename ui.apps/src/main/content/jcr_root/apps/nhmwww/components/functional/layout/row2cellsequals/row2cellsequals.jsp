<%--

  Row 2cells equal width component.

  This component add a single row with two cells of equal width

--%><%
%><%@include file="/libs/foundation/global.jsp"%><%
%><%@page session="false" %><%
%>
<%@page session="false" 
			import="uk.ac.nhm.nhm_www.core.componentHelpers.RowHelper"%>
<%
%>
<cq:defineObjects />
<%
	RowHelper helper = new RowHelper(properties);
%>
<div class="row" <% if(!helper.isEqualizedDisabled()) { %>data-equalizer <% } %>>
	<div class="large-6 medium-6 columns large6-margin" <% if(!helper.isEqualizedDisabled()) { %>data-equalizer-watch<% } %>>
		<cq:include path="par" resourceType="foundation/components/parsys" />
	</div>
	<div class="large-6 medium-6 columns large6-margin" <% if(!helper.isEqualizedDisabled()) { %>data-equalizer-watch<% } %>>
		<cq:include path="par2" resourceType="foundation/components/parsys" />
	</div>
</div>