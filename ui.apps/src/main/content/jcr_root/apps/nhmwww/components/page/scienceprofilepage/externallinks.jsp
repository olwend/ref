<%@page import="uk.ac.nhm.core.componentHelpers.ScientistProfileHelper,
				uk.ac.nhm.core.model.science.WebSite,
				java.util.Set"%>
<%@include file="/apps/nhmwww/components/global.jsp"%> 
<%
	final ScientistProfileHelper helper = new ScientistProfileHelper(resource);
	
	

	if (helper.hasExternalWebSites()) {
		final Set<WebSite> websites = helper.getWebSites();
%>
<div class="hti-wrapper">    
	<div class="small-12 medium-12 large-12 columns hti-box hti-box__light-grey mb-20 additional-links">  
	    <div class="hti-box--text-wrapper">
		    <h4>Additional links</h4><!-- get Property here -->
		    <ul class="list__no-bullet">
		<%
				for (final WebSite website:websites) {
					if (website.isValid() && !website.isPersonalInformationWebSite() && !WebSite.TWITTER_TYPE.equals(website.getType())) {
		%>
		    	<li><a href="<%= website.getLink() %>"><%= website.getLabel() %></a></li>
		<%
					}
				}
		%>
		   	</ul>
		</div>
	</div>
</div>
	<%
		}
	%>