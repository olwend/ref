<%@page import="java.util.Arrays,
				java.util.Set,
				uk.ac.nhm.nhm_www.core.services.ScientistsGroupsService,
				uk.ac.nhm.nhm_www.core.componentHelpers.ScientistProfileHelper,
				uk.ac.nhm.nhm_www.core.model.science.Scientist,
				org.apache.commons.lang3.StringUtils"%>
<%@include file="/apps/nhmwww/components/global.jsp"%>
<%
	final ScientistProfileHelper helper = new ScientistProfileHelper(resource);

	final String specialisms 	   = helper.getSpecialisms();
%>
<div class="hti-wrapper">
	<div class="small-12 medium-12 large-12 columns hti-box hti-box__light-grey" data-equalizer-watch>
		<div class="hti-box--text-wrapper">
			<%
		if (helper.hasGroup()) {
			final ScientistsGroupsService groupService = sling.getService(ScientistsGroupsService.class);
			final Set<Scientist> groupScientists = groupService.getGroupScientists(resource);
			final String groupName = groupService.getGroupName(resource);
			if (!groupScientists.isEmpty()) {
	%>
			<h3><%= groupName %></h3>
			<!-- <h2>Group</h2> -->
			<p>
				<%
				boolean isFirst = true;
				String groupOutput = "";
				for (final Scientist scientist : groupScientists) {
					final String path = scientist.getPath();
					if (isFirst) {
						isFirst = false;
					} else {
	
						groupOutput += ",&nbsp;";
					
					}
					
					if (currentPage.getPath().equals(path)) {
						groupOutput += scientist.getName();
	
					} else {
						groupOutput += "<a href=\"" + scientist.getPath()+".html\">"+scientist.getName()+"</a>";
					}
				}
	
	%>
				<%= groupOutput %>
			</p>
			<%
			}
		}
	
		if (specialisms != null) {
	%>
			<h3>Specialisms</h3>
			<p><%= specialisms %></p>
			<%
		}
	%>
		</div>
	</div>
</div>