<%@include file="/libs/foundation/global.jsp" %>

<%if(properties.get("hideFromSearch") != null && properties.get("hideFromSearch").equals("true")) { %>
    <meta name="robots" content="noindex">
<%} %>
