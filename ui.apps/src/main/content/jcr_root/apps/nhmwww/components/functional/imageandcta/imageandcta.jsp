<%@page session="false"%>
<%@include file="/libs/foundation/global.jsp" %>
<%
   Page eventPage = currentPage;
   String eventContentPath = eventPage.getPath()+"/jcr:content";

   Node contentNode = resourceResolver.getResource(eventContentPath).adaptTo(Node.class);

   String eventType = contentNode.hasProperty("eventSelect") ? contentNode.getProperty("eventSelect").getString() : "";
   String fileReference = contentNode.hasProperty("fileReference") ? contentNode.getProperty("fileReference").getString() : "";
   
   String ctaLink = "";
   if (eventType != ""){
      ctaLink = contentNode.hasProperty("ctaLink") ? contentNode.getProperty("ctaLink").getString() : "";
   }
%>    
<c:set var="eventType" value="<%= eventType.toLowerCase() %>"/>
<c:set var="fileReference" value="<%= fileReference %>"/>
<c:set var="school" value="school"/>  
<c:set var="science" value="science"/>
<c:if test="${not empty eventType && not empty fileReference}"> 
    <div class="image--and--cta--wrapper mt-20" style="background-image: url(${fileReference})">
        <c:set var="ctaLink" value="<%= ctaLink %>"/>
        <c:if test="${not empty ctaLink}">    
            <a class="small-6 columns cta--wrapper ${eventType}" href="${ctaLink}.html">
                 <div class="small-2 large-2 columns">
                    <i class="ico svg-ico ticket--icon" data-svg-src="/etc/designs/nhmwww/img/svg-icons/icon_l_feature_ticket.svg" data-stroke-width="3"></i>
                </div>
                <div class="small-8 large-8 columns">
                    <h3>Book tickets now</h3>
                    <p class="sub--text">Beat the long queues!</p>
                </div>
                <div class="small-2 large-2 columns">
                    <i class="ico svg-ico arrowl arrow--icon" data-svg-src="/etc/designs/nhmwww/img/svg-icons/icon_l_general_arrow_r.svg" data-stroke-width="4"></i>
                </div>
            </a>
        </c:if>
    </div>
</c:if>
<c:if test="${empty eventType}">
    The page has not been set up yet.
</c:if>
<c:if test="${not empty eventType && empty fileReference}">
    Please set an Image in the Page Properties.
</c:if>