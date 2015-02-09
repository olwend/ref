<%@page session="false"%>
<%@include file="/apps/nhmwww/components/global.jsp"%>
<%@page import="uk.ac.nhm.nhm_www.core.componentHelpers.*"%>
<cq:defineObjects/>
<cq:includeClientLib />
<%
	HeroHelper helper = new HeroHelper(slingRequest, currentPage, properties);
%>
<!-- START OF HERO -->
        <a href="#" class="close-video">close</a>
            <div class="video-wrapper" <% if(helper.getVideoId() != null) { %> data-nhm-videoid=<%= helper.getVideoId() %> <% } %>>
                <% helper.getMobileImage().draw(out); %>
               	<%if (helper.getVideoId() != null) {%>
               		<a href="#" class="play-video"><% helper.getImage().draw(out); %></a>
               	<% } else { 
               		helper.getImage().draw(out);
               	%>
               		
               	<% } %> 
               	<div class="caption-outer-wrapper">
                	<div class="caption-inner-wrapper">
		               	<div class="caption">
		               		<h1><%=helper.getTitle() %></h1>
		                   	<% if(helper.getSummary() != null) { %>
		                   		<p><%=helper.getSummary() %></p>
		                   	<% } %>
		               		<%if (helper.getVideoId() != null) {%>
		               			<div class="promo-link video mobile"><a class="arrow play-video" href="#"><%= helper.getPromoLinkText() %></a></div>
		               		<% } %>	
		               	</div>
               		</div>	
               	</div>
               	<%if (helper.getVideoId() != null) {%>
               		<div class="youtubeplayer" id="<%=helper.getVideoId()%>"></div>
                <% } %>
            </div>
<!--  END OF HERO -->

