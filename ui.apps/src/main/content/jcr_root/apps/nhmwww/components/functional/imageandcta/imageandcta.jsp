<%@page session="false"%>
<%@include file="/libs/foundation/global.jsp" %>
<%
   String eventContentPath = currentPage.getPath()+"/jcr:content";

   Node contentNode = resourceResolver.getResource(eventContentPath).adaptTo(Node.class);

   String eventType = contentNode.hasProperty("eventSelect") ? contentNode.getProperty("eventSelect").getString().toLowerCase() : "";
   String fileReference = contentNode.hasProperty("fileReference") ? contentNode.getProperty("fileReference").getString() : "";
   String ctaLink = (eventType != "" && contentNode.hasProperty("ctaLink")) ? contentNode.getProperty("ctaLink").getString() : "";
   String ctaText = (eventType != "" && contentNode.hasProperty("ctaText")) ? contentNode.getProperty("ctaText").getString() : "";
%>    
<c:set var="eventType" value="<%= eventType%>"/>
<c:set var="fileReference" value="<%= fileReference %>"/>
<c:set var="ctaText" value="<%= ctaText %>"/>
<c:if test="${not empty eventType && not empty fileReference}"> 
    <div class="image--and--cta--wrapper">
        <c:set var="ctaLink" value="<%= ctaLink %>"/>
        <c:if test="${not empty ctaLink}">    
            <a class="small-6 columns cta--wrapper ${eventType} hide-for-small-only" href="${ctaLink}.html">
                 <div class="small-2 large-2 columns">
                    <i class="ico svg-ico ticket--icon" data-svg-src="/etc/designs/nhmwww/img/svg-icons/icon_l_feature_ticket.svg" data-stroke-width="3"></i>
                </div>
                <div class="small-8 large-8 columns">
                    <h3 class="cta--text">${ctaText}</h3>
                </div>
                <div class="small-2 large-2 columns">
                    <i class="ico svg-ico arrowl arrow--icon" data-svg-src="/etc/designs/nhmwww/img/svg-icons/icon_l_general_arrow_r.svg" data-stroke-width="4"></i>
                </div>
            </a>
        </c:if>
        <img class="image--and--cta--image" src="${fileReference}">
    </div>
</c:if>
<c:if test="${empty eventType}">
    The page has not been set up yet.
</c:if>
<c:if test="${not empty eventType && empty fileReference}">
    Please set an Image in the Page Properties.
</c:if>