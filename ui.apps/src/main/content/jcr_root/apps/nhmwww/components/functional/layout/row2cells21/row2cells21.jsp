<%--

  Row 2 Cells 2 col then 1 col component.

  This component add a single row with two cells one of 66% followed by 33%

--%><%
%><%@include file="/libs/foundation/global.jsp"%><%
%><%@page session="false"
			import="uk.ac.nhm.core.componentHelpers.RowHelper"
    		import="com.day.cq.wcm.api.WCMMode"%>
<cq:defineObjects />
<%
	RowHelper helper = new RowHelper(properties);
	boolean isEdit = WCMMode.fromRequest(request) == WCMMode.EDIT;
	boolean isDesign = WCMMode.fromRequest(request) == WCMMode.DESIGN;
%>

<div class="row" <% if(!helper.isEqualizedDisabled() && !isEdit && !isDesign) { %>data-equalizer <% } %>
    <% if(isEdit || isDesign) { %> style="min-height:100px;"<% } %> > <%-- Fix for NHMEC-64: The component cell are overlapping --%>
    <div class="large-8 medium-8 columns" <% if(!helper.isEqualizedDisabled()) { %>data-equalizer-watch<% } %>>
        <cq:include path="par" resourceType="foundation/components/parsys" />
    </div>
    <div class="large-4 medium-4 columns" <% if(!helper.isEqualizedDisabled()) { %>data-equalizer-watch<% } %>>
        <cq:include path="par2" resourceType="foundation/components/parsys" />
    </div>
</div>


