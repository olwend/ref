<%--

  Row 3 Cells component.

  This component add a single row with three cells one of equal width

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
	<div class="large-4 medium-4 columns large4-margin" <% if(!helper.isEqualize3Col()) { %>data-equalizer-watch<% } %>>
		<cq:include path="par" resourceType="foundation/components/parsys" />
	</div>
	<div class="large-4 medium-4 columns large4-margin" <% if(!helper.isEqualize3Col()) { %>data-equalizer-watch<% } %>>
		<cq:include path="par2" resourceType="foundation/components/parsys" />
	</div>
	<div class="large-4 medium-4 columns large4-margin" <% if(!helper.isEqualize3Col()) { %>data-equalizer-watch<% } %>>
		<cq:include path="par3" resourceType="foundation/components/parsys" />
	</div>
</div>