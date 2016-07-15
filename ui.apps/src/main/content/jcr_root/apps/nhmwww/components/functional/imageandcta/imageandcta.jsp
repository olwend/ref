<%@page session="false"%>
<%@ page import="com.day.cq.commons.Doctype,
    com.day.cq.wcm.api.components.DropTarget,
    com.day.cq.wcm.foundation.Image, com.day.cq.wcm.foundation.Placeholder" %>
<%@include file="/libs/foundation/global.jsp" %>
<%
   final String IMAGE_CLASS = "image--and--cta--image";
   Resource pageRes = currentPage.getContentResource();
   Image image = null;
   if(pageRes != null) {
       if(pageRes.hasChildren()) {
           Resource imageRes = pageRes.getChild("file");
           if(imageRes != null) {
               image = new Image(imageRes);
               if(image != null) {
                   image.setIsInUITouchMode(Placeholder.isAuthoringUIModeTouch(slingRequest));
                   image.addCssClass(IMAGE_CLASS);
                   image.setSelector(".img");
                   image.setDoctype(Doctype.fromRequest(request));
               }
           }
       }
   }
   
   String eventContentPath = currentPage.getPath()+"/jcr:content";

   Node contentNode = resourceResolver.getResource(eventContentPath).adaptTo(Node.class);

   String eventType = contentNode.hasProperty("eventSelect") ? contentNode.getProperty("eventSelect").getString().toLowerCase() : "";
   String fileReference = contentNode.hasProperty("fileReference") ? contentNode.getProperty("fileReference").getString() : "";
   String ctaLink = (eventType != "" && contentNode.hasProperty("ctaLink")) ? contentNode.getProperty("ctaLink").getString() : "";
   String ctaText = (eventType != "" && contentNode.hasProperty("ctaText")) ? contentNode.getProperty("ctaText").getString() : "";
%>    
<c:set var="eventType" value="<%= eventType %>"/>
<c:set var="fileReference" value="<%= fileReference %>"/>
<c:set var="hasUploadedImage" value="<%= image != null %>"/>
<c:set var="hasFileReference" value="<%= !fileReference.isEmpty() %>"/>
<c:set var="hasCTAImage" value="${hasUploadedImage || hasFileReference}"/>
<c:set var="ctaText" value="<%= ctaText %>"/>
<c:if test="${not empty eventType && hasCTAImage}"> 
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
        <c:choose>
            <c:when test="${not empty eventType && hasFileReference}">
                <img class="${IMAGE_CLASS}" src="${fileReference}">
            </c:when>
            <c:when test="${not empty eventType && hasUploadedImage}">
                <% image.draw(out); %>
            </c:when>
            <c:otherwise>
            </c:otherwise>
        </c:choose>
    </div>
</c:if>
<c:if test="${empty eventType}">
    The page has not been set up yet.
</c:if>
<c:if test="${not empty eventType && !hasCTAImage}">
    Please set an Image in the Page Properties.
</c:if>