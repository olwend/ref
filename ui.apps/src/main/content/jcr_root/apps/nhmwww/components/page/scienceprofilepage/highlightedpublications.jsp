<%@page import="uk.ac.nhm.nhm_www.core.componentHelpers.ScientistProfileHelper,
				uk.ac.nhm.nhm_www.core.model.science.Publication,
				java.util.Set,
				org.apache.commons.lang3.StringUtils"%>
<%@include file="/apps/nhmwww/components/global.jsp"%> 
<%
	final ScientistProfileHelper helper = new ScientistProfileHelper(resource);

	final Set<Publication> publications = helper.getPublications();
%>
<%
	if (publications != null) {
		boolean hasFavorites = false;
		int count = 0;
		for (final Publication publication:publications) {
			if (publication.isFavorite()) {
				if (!hasFavorites) {
					hasFavorites = true;
%>
	<div class="large-12 medium-12 columns aside-box about-us large4-padding highlight-publications">  
    	<h3>Highlighted publications</h3>
<%	
				}
%>
		<p><%= publication.getHTMLContent(helper.getLastName() + " " + helper.getInitials(), true) %></p>
<%			
				count++;
				if (count >= 6) {
					break;
				}
			}
		}
		if (hasFavorites) {
%>
	</div>
<%		
	}
}
%>
