<%@page session="false"%>
<%@include file="/libs/foundation/global.jsp" %>
<cq:includeClientLib categories="nhmwww.eventinformationcomponent"/>

<%
   Page eventPage = currentPage;
   String eventContentPath = eventPage.getPath()+"/jcr:content";

   Node contentNode = resourceResolver.getResource(eventContentPath).adaptTo(Node.class);

   String eventTitle = contentNode.hasProperty("jcr:eventTitle") ? contentNode.getProperty("jcr:eventTitle").getString() : "";
   String eventType = contentNode.hasProperty("eventSelect") ? contentNode.getProperty("eventSelect").getString() : "";
   String eventVenue = contentNode.hasProperty("eventVenue") ? contentNode.getProperty("eventVenue").getString() : "";

   Value[] audience = contentNode.hasProperty("cq:tags") ? contentNode.getProperty("cq:tags").getValues() : null;
   String audiences = "";
   
   if (audience != null && audience.length > 0) {
      for (int i = 0; i < audience.length; i++) {
         String[] tokens = audience[i].toString().split("/");
         String firstToken = tokens[0];
         String[] headers = firstToken.split(":");
         if (headers[1].equals("audience")) {
            String lastToken = tokens[tokens.length-1];
            lastToken = lastToken.replace("-", " ");

            if (i == 0) {
               audiences = lastToken;                               
            } 
            else {
               audiences = audiences.concat(", " + lastToken);                            
            }                               
         }
      }
   } 
   
   String adultPrice = contentNode.hasProperty("adultPrice") ? contentNode.getProperty("adultPrice").getString() : "";
   String concessionPrice = contentNode.hasProperty("concessionPrice") ? contentNode.getProperty("concessionPrice").getString() : "";
   String memberPrice = contentNode.hasProperty("memberPrice") ? contentNode.getProperty("memberPrice").getString() : "";
   String familyPrice = contentNode.hasProperty("familyPrice") ? contentNode.getProperty("familyPrice").getString() : "";
   String customPrice = contentNode.hasProperty("customPrice") ? contentNode.getProperty("customPrice").getString() : "";

   String eventSubjects = "";
   String capacity = "";
   String eventDuration = "";
                                          
   if (eventType.toLowerCase().equals("school")) {
       Value[] subject = contentNode.hasProperty("cq:subject") ? contentNode.getProperty("cq:subject").getValues() : null;
       if (subject != null && subject.length > 0) {
          for (int i = 0; i < subject.length; i++) {
             String[] tokens = subject[i].toString().split("/");
             String lastToken = tokens[tokens.length-1];
             lastToken = lastToken.replace("-", " ");

             if (i == 0) {
                eventSubjects = lastToken;                               
             } 
             else {
                eventSubjects = eventSubjects.concat(", " + lastToken);                            
             }
          }
       }
       capacity = contentNode.hasProperty("capacity") ? contentNode.getProperty("capacity").getString() : "";
       eventDuration = contentNode.hasProperty("eventDuration") ? contentNode.getProperty("eventDuration").getString() : "";                                          
   } 

   String speakerDetails = "";
   if (eventType.toLowerCase().equals("science")) {
      speakerDetails = contentNode.hasProperty("speakerDetails") ? contentNode.getProperty("speakerDetails").getString() : "";                                         
   }
   
%>
<c:set var="eventType" value="<%= eventType.toLowerCase() %>"/>
<c:if test="${not empty eventType}">
    <div class="event--information--wrapper ${eventType}">
        <h3>Event information</h3>
        <div class="row">
            <div class="small-2 large-2 columns">
                <img src="/etc/designs/nhmwww/img/icons/info.png"/>
            </div>
            <div class="small-10 large-10 columns">
                <c:if test="${not empty eventType}">
                    <p>Event: <span style="font-weight:bold">${eventType}</span></p> 
                </c:if>

                <c:set var="eventVenue" value="<%= eventVenue %>"/>
                <c:if test="${not empty eventVenue}">
                    <p>Location: <span style="font-weight:bold">${eventVenue}</span></p> 
                </c:if>

                <c:set var="audiences" value="<%= audiences %>"/>
                <c:if test="${not empty audiences}">
                    <p>Who is it for: <span style="font-weight:bold">${audiences}</span></p> 
                </c:if>

                <p class="new--section">Ticket prices:</p>
                <c:set var="adultPrice" value="<%= adultPrice %>"/>
                <c:if test="${not empty adultPrice}">
                    <p>Adult: <span style="font-weight:bold">${adultPrice}</span></p> 
                </c:if>

                <c:set var="concessionPrice" value="<%= concessionPrice %>"/>
                <c:if test="${not empty concessionPrice}">
                    <p>Child and concession: <span style="font-weight:bold">${concessionPrice}</span></p> 
                </c:if> 

                <c:set var="memberPrice" value="<%= memberPrice %>"/>
                <c:if test="${not empty memberPrice}">
                    <p>Members: <span style="font-weight:bold">${memberPrice}</span></p> 
                </c:if> 

                <c:set var="familyPrice" value="<%= familyPrice %>"/>
                <c:if test="${not empty familyPrice}">
                    <p>Family: <span style="font-weight:bold">${familyPrice}</span></p> 
                </c:if> 

                <c:set var="customPrice" value="<%= customPrice %>"/>
                <c:if test="${not empty customPrice}">
                    <p style="font-weight:bold">${customPrice}</p> 
                </c:if>

                <c:set var="eventSubjects" value="<%= eventSubjects %>"/>
                <c:if test="${not empty eventSubjects}">
                    <p class="new--section">Subject: <span style="font-weight:bold">${eventSubjects}</span></p> 
                </c:if> 

                <c:set var="capacity" value="<%= capacity %>"/>
                <c:if test="${not empty capacity}">
                    <p>Capacity: <span style="font-weight:bold">${capacity}</span></p> 
                </c:if> 

                <c:set var="eventDuration" value="<%= eventDuration %>"/>
                <c:if test="${not empty eventDuration}">
                    <p>Event duration: <span style="font-weight:bold">${eventDuration}</span></p> 
                </c:if>

                <c:set var="speakerDetails" value="<%= speakerDetails %>"/>
                <c:if test="${not empty speakerDetails}">
                   <p class="new--section">Speaker details: <span style="font-weight:bold">${speakerDetails}</span></p> 
                </c:if> 
            </div>
        </div>
    </div>
</c:if>
<c:if test="${empty eventType}">
    The page has not been set up yet.
</c:if>