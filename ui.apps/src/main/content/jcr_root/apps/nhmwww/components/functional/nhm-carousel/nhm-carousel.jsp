
<%@page session="false"%>
<%@include file="/apps/nhmwww/components/global.jsp"%>
<%@page import="uk.ac.nhm.core.componentHelpers.*"%>
<%@page import="uk.ac.nhm.core.model.CarouselElement"%>
<%@page import="uk.ac.nhm.core.services.CarouselBuilderService"%>
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

ArrayList<CarouselElement> elements = helper.getElements();  

if(elements.size() == 0 && isOnEditMode) { %>
	<div class="row">
		<h4>Carousel</h4>
		Required fields:
		<ul>
			<li>At least one carousel item</li>
		</ul>
	</div>
<% return; } %>
<% if (isOnEditMode) { %>
	<h4>Carousel</h4>
	Edit the carousel component here
<% } %>
<!--  START OF CAROUSEL -->
<div class="<%= helper.getCarouselType() %>-wrapper">

	<%if (helper.getComponentTitle() != null) {%><h3><%if (helper.getHyperlink() != null) {%><a href="<%=helper.getHyperlink() %>"<%=helper.getNewWindowHtml()%>><%}%><%=helper.getComponentTitle() %><%if (helper.getHyperlink() != null) {%></a><%}%></h3> <%}%>
    <ul class="<%= helper.getCarouselType() %>"
        data-nhm-thumbnails="<%=helper.getHasThumbnails()%>"
    	data-nhm-autoscroll="<%=helper.getHasAutoscroll()%>"
    	data-nhm-autoscroll-duration="<%=(helper.getAutoScrollDuration() * 1000) %>" 
    	data-nhm-grouping="<%=helper.getGrouping()%>"> 
    <%
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
        	<li data-thumb="<%=element.getFilename()%>"  data-gtm="<%=helper.getCarouselRow()%>-minor-carousel-<%=imageIndex%>">
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
        	<li data-thumb="<%=element.getFilename()%>"  data-gtm="<%=helper.getCarouselRow()%>-major-carousel-<%=imageIndex%>">
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