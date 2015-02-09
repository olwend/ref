<%@page import="uk.ac.nhm.nhm_www.core.componentHelpers.ScientistProfileHelper,
				uk.ac.nhm.nhm_www.core.model.science.WebSite,
				java.util.Set"%>
<%@include file="/apps/nhmwww/components/global.jsp"%> 
<%
	final ScientistProfileHelper helper = new ScientistProfileHelper(resource);
	
	

	if (helper.hasExternalWebSites()) {
		final Set<WebSite> websites = helper.getWebSites();
%>
            
<div class="large-12 medium-12 columns aside-box about-us large4-padding additional-links">  
    <h3>Additional links</h3><!-- get Property here -->
    <ul>
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
<%
	}
%>