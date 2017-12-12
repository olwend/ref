<%@page session="false"
        import="uk.ac.nhm.core.componentHelpers.SponsorBlockHelper" %>
<%@include file="/apps/nhmwww/components/global.jsp"%>
<%SponsorBlockHelper helper = new SponsorBlockHelper(resource,properties); %>
			<!-- START SPONSORS BLOCK GRID -->
			<div class="sponsors-grid">	
				<ul class="small-block-grid-2 medium-block-grid-4">
				<%if (helper.getSponsor1image() != null && helper.getSponsor1image().length() > 0) { %>
					<li><img src="<%=helper.getSponsor1image() %>" /></li>
				<%}%>
				<%if (helper.getSponsor2image() != null && helper.getSponsor2image().length() > 0) { %>
					<li><img src="<%=helper.getSponsor2image() %>" /></li>
				<%}%>
				<%if (helper.getSponsor3image() != null && helper.getSponsor3image().length() > 0) { %>
					<li><img src="<%=helper.getSponsor3image() %>" /></li>
				<%}%>
				<%if (helper.getSponsor4image() != null && helper.getSponsor4image().length() > 0) { %>
					<li><img src="<%=helper.getSponsor4image() %>" /></li>
				<%}%>
				</ul>
			</div>
			<!-- END SPONSORS BLOCK GRID -->