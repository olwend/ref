<%@include file="/libs/foundation/global.jsp" %>

<%if(properties.get("hideFromSearch") != null && properties.get("hideFromSearch").equals("true")) { %>
    <% response.setHeader("X-Robots-Tag", "noindex"); %>
    <% response.setHeader("Dispatcher", "no-cache"); %>
<%} %>
