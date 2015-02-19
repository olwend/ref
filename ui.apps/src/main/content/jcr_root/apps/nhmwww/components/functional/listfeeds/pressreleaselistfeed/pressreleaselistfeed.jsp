<%--

  List Feed component.

  Will list the items under a specified root

--%><%
%><%@include file="/apps/nhmwww/components/global.jsp"%><%
%><%@page session="false" %><%
%><%
    // TODO add you code here
%>
<cq:includeClientLib categories="nhm-www.pressreleaseslist"/>
<div class="pressreleaselistfeed-wrapper" data-rootpath="<%= currentPage.getPath() %>" data-pagesize="6" >
    <!-- START PAGINATION -->
    <div class="pagination-centered">
    </div>
    <!-- END PAGINATION -->
    <div class="press-room--list">
    </div>
</div>