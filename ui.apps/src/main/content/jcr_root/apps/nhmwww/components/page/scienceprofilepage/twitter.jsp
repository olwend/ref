<%@page import="uk.ac.nhm.nhm_www.core.componentHelpers.ScientistProfileHelper"%>
<%@include file="/apps/nhmwww/components/global.jsp"%> 
<%
	final ScientistProfileHelper helper = new ScientistProfileHelper(resource);
	
	final String twitterID = helper.getTwitterUserName();

	if (twitterID != null) {
%>
	<div class="large-4 medium-4 columns"><!-- links row -->
		<div class="large-12 medium-12 columns aside-box about-us large4-padding additional-links">  
             <a class="twitter-timeline"
				width="100%"
				height="500"
				href="https://twitter.com/<%= twitterID %>"
				data-widget-id="532208252889464835">
					Tweets by @<%= twitterID %>
			</a>
		</div>
	</div>
<%
	}
%>


