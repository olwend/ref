<%@page session="false"
        import="com.day.cq.tagging.Tag,com.day.cq.tagging.TagManager,
                java.util.Map,
                java.util.HashMap"%>
<%@include file="/libs/foundation/global.jsp" %>

<%!

    
    //function to get the subjects
    String getSubject(Value[] subject, TagManager tagMgr) {
       String result = "";
       if (subject != null) {
          for (int i = 0; i < subject.length; i++) {
                Value value = subject[i];
                Tag tag = tagMgr.resolve(value.toString());
                String subjectTitle = tag.getTitle();
                if (i != 0) subjectTitle = subjectTitle.toLowerCase();
                result += subjectTitle;
                if (i < subject.length-1) result += ", ";
           }
       }
       return result;
    }
     String getTagTitle(Value tagId, TagManager tagMgr) {
        Tag tag = tagMgr.resolve(tagId.toString());
        return  tag.getTitle();
     }
%>
<%
   String eventContentPath = currentPage.getPath() + "/jcr:content";

   Node contentNode = resourceResolver.getResource(eventContentPath).adaptTo(Node.class);

   String eventTitle = contentNode.hasProperty("jcr:eventTitle") ? contentNode.getProperty("jcr:eventTitle").getString() : "";
   String eventType = contentNode.hasProperty("eventSelect") ? contentNode.getProperty("eventSelect").getString() : "";
   String eventVenue = contentNode.hasProperty("eventVenue") ? contentNode.getProperty("eventVenue").getString() : "";
   String ctaLink = (eventType != "" && contentNode.hasProperty("ctaLink")) ? contentNode.getProperty("ctaLink").getString() : "";
   String ctaText = (eventType != "" && contentNode.hasProperty("ctaText")) ? contentNode.getProperty("ctaText").getString() : "";
   
   Value[] tags = contentNode.hasProperty("cq:tags") ? contentNode.getProperty("cq:tags").getValues() : null;
   String events = "";
   String keyStage = "";
   String tagTitles[] = new String[0];
   TagManager tagMgr = resourceResolver.adaptTo(TagManager.class);
   boolean eventTagsFound = false;
   boolean keyStageTagsFound = false;
   if (tags != null) {
      for (int i = 0; i < tags.length; i++) {
         String[] tokens = tags[i].toString().split("/");
         String[] headers = tokens[0].split(":");
         
         if (headers[1].equals("events")) {
            eventTagsFound = true;
            String tagTitle = getTagTitle(tags[i], tagMgr) + ", ";
            if (i != 0) tagTitle = tagTitle.toLowerCase();
            events += tagTitle;
         }
         else if (headers[1].equals("key-stage")) {
            keyStageTagsFound = true;
            String tagTitle = getTagTitle(tags[i], tagMgr) + ", ";
            if (i != 0) tagTitle = tagTitle.toLowerCase();
            keyStage += tagTitle;                               
         }
      }
   }
    
   if (eventTagsFound) {
        events = events.substring(0, events.length() - 2);
   } else { 
        events = eventType; 
   } 

   if (keyStageTagsFound) {
        keyStage = keyStage.substring(0, keyStage.length() - 2);
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
       eventSubjects = getSubject(subject, tagMgr);
       capacity = contentNode.hasProperty("capacity") ? contentNode.getProperty("capacity").getString() : "";
       eventDuration = contentNode.hasProperty("eventDuration") ? contentNode.getProperty("eventDuration").getString() : "";                                          
   } 

   String scienceSubjects = "";
   String speakerDetails = "";
   
   if (eventType.toLowerCase().equals("science")) {
      Value[] scienceSubject = contentNode.hasProperty("cq:subjectScience") ? contentNode.getProperty("cq:subjectScience").getValues() : null;
      scienceSubjects = getSubject(scienceSubject, tagMgr);
      speakerDetails = contentNode.hasProperty("speakerDetails") ? contentNode.getProperty("speakerDetails").getString() : "";                                         
   }
   
%>
<c:set var="eventType" value="<%= eventType.toLowerCase() %>"/>
<c:choose>
    <c:when test="${not empty eventType}">
        <div class="${eventType} hti-wrapper event--information--wrapper">
            <div class="small-12 medium-12 large-12 columns hti-box hti-box__feature-box" data-equalizer-watch>
                <div class="hti-box--text-wrapper">
                    <h3>Event information</h3>
                    <div class="small-12 large-12 columns">
                        <c:set var="events" value="<%= events %>"/>
                        <c:if test="${not empty events}">
                            <p>Event: <strong>${events}</strong></p> 
                        </c:if>

                        <c:set var="eventVenue" value="<%= eventVenue %>"/>
                        <c:if test="${not empty eventVenue}">
                            <p>Location: <strong>${eventVenue}</strong></p> 
                        </c:if>

                        <c:set var="keyStage" value="<%= keyStage %>"/>
                        <c:if test="${not empty keyStage}">
                            <p>Suitable for: <strong>${keyStage}</strong></p> 
                        </c:if>
                        
                        <c:set var="eventSubjects" value="<%= eventSubjects %>"/>
                        <c:if test="${not empty eventSubjects}">
                            <p class="event--information--new-section">Subject: <strong>${eventSubjects}</strong></p> 
                        </c:if> 

                        <c:set var="capacity" value="<%= capacity %>"/>
                        <c:if test="${not empty capacity}">
                            <p>Capacity: <strong>${capacity}</strong></p> 
                        </c:if> 

                        <c:set var="eventDuration" value="<%= eventDuration %>"/>
                        <c:if test="${not empty eventDuration}">
                            <p>Event duration: <strong>${eventDuration}</strong></p> 
                        </c:if>

                        <c:set var="scienceSubjects" value="<%= scienceSubjects %>"/>
                        <c:if test="${not empty scienceSubjects}">
                            <p class="event--information--new-section">Subject: <strong>${scienceSubjects}</strong></p> 
                        </c:if> 
                        
                        <c:set var="speakerDetails" value="<%= speakerDetails %>"/>
                        <c:if test="${not empty speakerDetails}">
                           <p>Speaker details: <strong>${speakerDetails}</strong></p> 
                        </c:if> 
                        
                        <p class="event--information--new-section">Ticket prices:</p>
                        <c:set var="adultPrice" value="<%= adultPrice %>"/>
                        <c:if test="${not empty adultPrice}">
                            <p>Adult: <strong>${adultPrice}</strong></p> 
                        </c:if>

                        <c:set var="concessionPrice" value="<%= concessionPrice %>"/>
                        <c:if test="${not empty concessionPrice}">
                            <p>Child and concession: <strong>${concessionPrice}</strong></p> 
                        </c:if> 

                        <c:set var="memberPrice" value="<%= memberPrice %>"/>
                        <c:if test="${not empty memberPrice}">
                            <p>Members: <strong>${memberPrice}</strong></p> 
                        </c:if> 

                        <c:set var="familyPrice" value="<%= familyPrice %>"/>
                        <c:if test="${not empty familyPrice}">
                            <p>Family: <strong>${familyPrice}</strong></p> 
                        </c:if> 

                        <c:set var="customPrice" value="<%= customPrice %>"/>
                        <c:if test="${not empty customPrice}">
                            <p>${customPrice}</p> 
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
        <c:set var="ctaLink" value="<%= ctaLink %>"/>
        <c:set var="ctaText" value="<%= ctaText %>"/>
        <c:if test="${not empty ctaLink}">
            <div class="${eventType} show-for-small-only">
                <div class="info-tout info-tout__action tickets">
                    <a class="arrow--large burgandy" href="${ctaLink}.html">
                        <i class="ico svg-ico" data-svg-src="/etc/designs/nhmwww/img/svg-icons/icon_l_feature_ticket.svg" data-stroke-width="3"></i>
                        <h3 class="paddingTB">${ctaText}</h3>
                        <i class="ico svg-ico arrowl" data-svg-src="/etc/designs/nhmwww/img/svg-icons/icon_l_general_arrow_r.svg" data-stroke-width="4"></i>
                    </a>
                </div>
            </div>
        </c:if>
    </c:when>
    <c:otherwise>
        The page has not been set up yet.
    </c:otherwise>
</c:choose>