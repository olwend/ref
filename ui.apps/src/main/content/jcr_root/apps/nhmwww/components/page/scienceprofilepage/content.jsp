<%@page
	import="uk.ac.nhm.nhm_www.core.componentHelpers.ScientistProfileHelper"%>
<%@include file="/apps/nhmwww/components/global.jsp"%>
<% 
    final ScientistProfileHelper helper = new ScientistProfileHelper(resource);
    final String title = helper.getTitle();
    final boolean displayGroupsAndSpecialisms = helper.displayGroupsAndSpecialismsBox(resource)(resource);
    final boolean displayProfessionalActivitiesTab = helper.displayProfessionalActivitiesTab(resource);
    final boolean displayPublicationsTab = helper.displayPublicationsTab(resource);
    String firstName = "";
    if(helper.getNickName() != null && !helper.getNickName().equals("")){
        firstName = helper.getNickName();
    } else {
        firstName = helper.getFirstName();
    }

    final String lastName  = helper.getLastName();
    final String nickName  = helper.getNickName();
%>
<div class="main-section science-profiles-detail-page">
	<!-- CONTENT WRAPPER -->
	<div class="title">
		<div class="row title-bar">
			<div class="small-12 medium-12 large-12 columns">
				<h1><%= title %>
					<%= firstName %>
					<%= lastName %></h1>
			</div>
		</div>
	</div>
	<div class="row personal-group science-profiles-detail-page--personal-group" data-equalizer>
		<div class="
			<% if (displayGroupsAndSpecialisms) { %>
				<%-- Will display box over 8 columns --%>
				small-12 medium-8 large-8 science-profiles-detail-page--personal
			<% } else { %>
				<%-- Will display box over all 12 columns --%>
				small-12 medium-12 large-12 science-profiles-detail-page--personal__single
			<% } %>
			columns">
			<cq:include script="personalinformation.jsp" />
		</div>
		<% if (displayGroupsAndSpecialisms) { %>
			<div class="small-12 medium-4 large-4 columns science-profiles-detail-page--group">
				<cq:include script="groupandspecialisms.jsp" />
			</div>
		<% } %>
	</div>

	<div class="row">
		<div class="small-12 medium-12 large-12 columns">
			<div class="show-for-large-up">
				<ul class="tabs science-profiles-detail-page--tabs-container mt-32" data-tab>
					<li class="tab-title active"><a href="#panel1">Introduction</a></li>
					<% if (displayProfessionalActivitiesTab) { %>
						<li class="tab-title"><a href="#panel2">Professional activities</a></li>
					<% } %>
					<% if (displayPublicationsTab) { %>
						<li class="tab-title"><a href="#panel3">Publications</a></li> 
					<% } %>
				</ul>
				<div class="tabs-content">
					<div class="content active" id="panel1">
						<cq:include script="introduction.jsp" />
					</div>
					<% if (displayProfessionalActivitiesTab) { %>
						<div class="content" id="panel2">
							<cq:include script="professionalactivities.jsp" />
						</div>
					<% } %>
					<% if (displayPublicationsTab) { %>
						<div class="content" id="panel3">
							<cq:include script="publications.jsp" />
						</div>
					<% } %>
				</div>
			</div>
			<div class="hide-for-large-up">
				<dl class="accordion" data-accordion>
					<dd class="accordion-navigation">
						<a href="#panel1a">Introduction</a>
						<div id="panel1a"
							class="content science-profiles-detail-page--accordion-content-container">
							<cq:include script="introduction.jsp" />
						</div>
					</dd>
					<% if (displayProfessionalActivitiesTab) { %>
						<dd class="accordion-navigation">
							<a href="#panel2a">Professional Activities</a>
							<div id="panel2a" class="content">
								<cq:include script="professionalactivities.jsp" />
							</div>
						</dd>
					<% } %>
					<% if (displayPublicationsTab) { %>
						<dd class="accordion-navigation">
							<a href="#panel3b">Publications</a>
							<div id="panel3b" class="content">
								<cq:include script="publications.jsp" />
							</div>
						</dd>
					<% } %>
				</dl>
			</div>
		</div>
	</div>
</div>
<cq:include path="par" resourceType="foundation/components/parsys" />