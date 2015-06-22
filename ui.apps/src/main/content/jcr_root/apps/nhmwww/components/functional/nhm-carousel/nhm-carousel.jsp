
<%@page session="false"%>
<%@include file="/apps/nhmwww/components/global.jsp"%>
<%@page import="uk.ac.nhm.nhm_www.core.componentHelpers.*"%>
<%@page import="uk.ac.nhm.nhm_www.core.model.CarouselElement"%>
<%@page import="uk.ac.nhm.nhm_www.core.services.CarouselBuilderService"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Collections"%>
<cq:defineObjects/>
<sling:defineObjects/>
<cq:includeClientLib categories="wwwnhm.carousel"/>
<%
	CarouselHelper helper = new CarouselHelper(resource, pageManager, resourceResolver);
	if (isOnEditMode) {
		final CarouselBuilderService carouselBuilder = sling.getService(CarouselBuilderService.class);
		carouselBuilder.createStructure(resource, pageManager, resourceResolver);
	}
	CTAButtonHelper ctaHelper = new CTAButtonHelper(cssClassSection.toLowerCase());

%>
<!--  START OF CAROUSEL -->
<div class="<%= helper.getCarouselType() %>-wrapper">

	<%if (helper.getComponentTitle() != null) {%><h4><%if (helper.getHyperlink() != null) {%><a href="<%=helper.getHyperlink() %>"<%=helper.getNewWindowHtml()%>><%}%><%=helper.getComponentTitle() %><%if (helper.getHyperlink() != null) {%></a><%}%></h4> <%}%>
    <ul class="<%= helper.getCarouselType() %>"
        data-nhm-thumbnails="<%=helper.getHasThumbnails()%>"
    	data-nhm-autoscroll="<%=helper.getHasAutoscroll()%>"
    	data-nhm-autoscroll-duration="<%=(helper.getAutoScrollDuration() * 1000) %>" 
    	data-nhm-grouping="<%=helper.getGrouping()%>"> 
    <%
		ArrayList<CarouselElement> elements = helper.getElements(); 
		//for( int i = 0; i < helper.getElements().size(); i++)
		int imageIndex = 0;
		for(CarouselElement element: elements)
		{
	%>
		<!-- <p><%=element.getFilename()%></p>
		<p><%=element.getHyperlink() %></p>
		<p><%=element.getCaption() %></p> 
		<%= element.getType() %>-->
		<% if(element.getIsVideo()) { %>
		    <li data-thumb="http://img.youtube.com/vi/<%=element.getFilename()%>/1.jpg">
            <div class="video-wrapper" data-nhm-videoid="<%= element.getFilename() %>">
            	<div id="<%= element.getFilename()%>" class="youtubeplayer"></div>
          	</div>
            
		<% } %>
		<% if(element.getIsContentSlide()) { %>
        	<li data-thumb="<%=element.getFilename()%>"  data-gtm="<%=helper.getCarouselRow()%>-carousel-<%=imageIndex%>">
        	<%
        		final String imageResourceName = CarouselBuilderService.IMAGE_NODE_NAME_PREFIX + imageIndex;
        		final Resource imageResource = resource.getChild(imageResourceName);
        		
        		if (imageResource == null) {
        	%>
        	test
            <img src="<%=element.getFilename()%>" alt="<%=element.getAlt()%>">
            <% 
        		} else {
            %>
            <cq:include path="<%= imageResourceName %>" resourceType="<%= CarouselBuilderService.FOUNDATION_5_IMAGE_RESOURCE_TYPE %>"/>
            <%
            		imageIndex++;
        		}
            %>
            <div class="event-slide-content">
	            
	           	<h4><%if (element.getHyperlink() != null && !element.getHyperlink().equals("")) { %><a href="<%=element.getHyperlink()%>"<%=helper.getNewWindowHtml()%> data-gtm="<%=helper.getCarouselRow()%>-carousel-<%=imageIndex%>"><% } %><%=element.getHeading() %><%if (element.getHyperlink() != null && !element.getHyperlink().equals("")) { %></a><% } %></h4>
	            <p><%=element.getCaption()%></p>
            </div>
            
		<% } %>
		<% if(!element.getIsVideo() &&  !element.getIsContentSlide()) { %>
        	<li data-thumb="<%=element.getFilename()%>"  data-gtm="<%=helper.getCarouselRow()%>-carousel-<%=imageIndex%>">
            <%if (element.getHyperlink() != null && !element.getHyperlink().equals("")) { %><a href="<%=element.getHyperlink()%>"<%=helper.getNewWindowHtml()%>><% } %>
            	<%
        		final String imageResourceName = CarouselBuilderService.IMAGE_NODE_NAME_PREFIX + imageIndex;
        		final Resource imageResource = resource.getChild(imageResourceName);
        		
        		if (imageResource == null) {
        		%>
        		<img src="<%=element.getFilename()%>" alt="<%=element.getAlt()%>">
        		<% 
        		} else {
	            %>
	            <cq:include path="<%= imageResourceName %>" resourceType="<%= CarouselBuilderService.FOUNDATION_5_IMAGE_RESOURCE_TYPE %>"/>
	            <%
	            	imageIndex++;
	        	}
	            %>
	            <% if(element.getCaption() != null && !element.getCaption().equals("")) { %>
	            	<div class="caption-outer-wrapper">
	                    <div class="caption-inner-wrapper">
			            	<div class="caption<% if(!helper.hasHeading()) { %> no-heading <%}%>">
			                	<% if(element.getHeading() != null && !element.getHeading().equals("")) {  %>
			                		<h2><%=element.getHeading()%></h2>
			                	<% } %>
			                	<p>
			                	
					                <%=element.getCaption()%>
			        	        </p>
			            	</div>
		            	</div>
	            	</div>
	            <% } %>
            <% } %>
            <%if (element.getHyperlink() != null && !element.getHyperlink().equals("")) { %></a><% } %> 
        </li>
    	
       <% } %>
    </ul>
</div>
<!--  END OF CAROUSEL -->