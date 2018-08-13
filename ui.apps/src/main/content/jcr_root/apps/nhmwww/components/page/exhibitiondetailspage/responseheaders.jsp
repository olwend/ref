<%@include file="/libs/foundation/global.jsp" %>

<%if(properties.get("hideFromSearch") != null) { %>
    <% response.setHeader("X-Robots-Tag", "noindex"); %>
<%} %>
