<%@page session="false"%>
<%@include file="/apps/nhmwww/components/global.jsp"%>
<%@page import="uk.ac.nhm.core.componentHelpers.GoogleMapsHelper"%>
<cq:defineObjects/>

<style>
    .google-maps {
        position: relative;
        padding-bottom: 75%; // This is the aspect ratio
        height: 0;
        overflow: hidden;
    }
    .google-maps iframe {
        position: absolute;
        top: 0;
        left: 0;
        width: 100% !important;
        height: 100% !important;
    }
</style>
 <%
	GoogleMapsHelper helper = new GoogleMapsHelper(slingRequest);
%>
<div class="google-maps">
<iframe width="600" height="450" frameborder="0" style="border:0"
src="https://www.google.com/maps/embed/v1/place?q=<%=helper.getPlace()%>&key=<%=helper.getKey()%>"></iframe>
</div>