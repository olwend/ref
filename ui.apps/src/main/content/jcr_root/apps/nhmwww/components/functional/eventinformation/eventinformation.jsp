<%@page session="false"%>
<%@include file="/libs/foundation/global.jsp" %>
<cq:includeClientLib categories="nhmwww.eventinformationcomponent"/>

<%
   Page eventPage = currentPage;
   String eventContentPath = eventPage.getPath()+"/jcr:content";

   Node contentNode = resourceResolver.getResource(eventContentPath).adaptTo(Node.class);

   String event = (String) properties.get("events", "");
   String eventTitle = contentNode.hasProperty("jcr:eventTitle") ? contentNode.getProperty("jcr:eventTitle").getString() : "";
   String eventType = contentNode.hasProperty("eventSelect") ? contentNode.getProperty("eventSelect").getString() : "";
   String eventVenue = contentNode.hasProperty("eventVenue") ? contentNode.getProperty("eventVenue").getString() : "";

   Value[] subject = contentNode.hasProperty("cq:subject") ? contentNode.getProperty("cq:subject").getValues() : null;
   String eventSubjects = "";
   
   if (subject.length > 0) {
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
   
%>    
    
<div class="event--information--wrapper <%= event %>">
    <%= eventTitle %>
        <%= eventType %>
             <%= eventVenue %>
                     <%= eventSubjects %>
    <h3>Event information</h3>    
</div>