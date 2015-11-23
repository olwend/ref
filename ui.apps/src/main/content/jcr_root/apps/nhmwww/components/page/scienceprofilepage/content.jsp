<%@page
	import="uk.ac.nhm.nhm_www.core.componentHelpers.ScientistProfileHelper"%>
<%@include file="/apps/nhmwww/components/global.jsp"%>
<% 
    final ScientistProfileHelper helper = new ScientistProfileHelper(resource);
    final String title = helper.getTitle();
    final boolean displayGroupsAndSpecialisms = helper.displayGroupsAndSpecialismsBox(resource, sling);
    final boolean displayProfessionalActivitiesTab = helper.displayProfessionalActivitiesTab(resource);
    final boolean displayPublicationsTab = helper.displayPublicationsTab(resource);
    final boolean displayProjectsTab = helper.displayProjectsTab(resource);
    final boolean displayTeachingActivitiesTab = helper.displayTeachingActivitiesTab(resource);
    final boolean displayImpactAndOutreach = helper.displayImpactAndOutreachTab(resource);
    
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

					<% if (displayProjectsTab) { %>
	                 	<li class="tab-title"><a href="#panel2">Projects</a></li>
					<% } %>
					
					<% if (displayProfessionalActivitiesTab) { %>
						<li class="tab-title"><a href="#panel3">Professional activities</a></li>
					<% } %>
					
					<% if (displayPublicationsTab) { %>
						<li class="tab-title"><a href="#panel4">Publications</a></li> 
					<% } %>
					
					<% if (displayTeachingActivitiesTab) { %>
						<li class="tab-title"><a href="#panel5">Teaching and students</a></li> 
					<% } %>
					
					<% if (displayImpactAndOutreach) { %>
						<li class="tab-title"><a href="#panel6">Impact and outreach</a></li> 
					<% } %>
				</ul>
				<div class="tabs-content">
					<div class="content active" id="panel1">
						<cq:include script="introduction.jsp" />
					</div>
					<% if (displayProjectsTab) { %>
	                    <div class="content" id="panel2">
	                        <cq:include script="projects.jsp" />
	                    </div>
					<% } %>
					<% if (displayProfessionalActivitiesTab) { %>
						<div class="content" id="panel3">
							<cq:include script="professionalactivities.jsp" />
						</div>
					<% } %>
					<% if (displayPublicationsTab) { %>
						<div class="content" id="panel4">
							<cq:include script="publications.jsp" />
						</div>
					<% } %>
					<% if (displayTeachingActivitiesTab) { %>
						<div class="content" id="panel5">
							<cq:include script="teachingactivities.jsp" />
						</div>
					<% } %>
					<% if (displayImpactAndOutreach) { %>
						<div class="content" id="panel6">
							<cq:include script="impactandoutreach.jsp" />
						</div>
					<% } %>
				</div>
			</div>
			<div class="hide-for-large-up">
				<dl class="accordion" data-accordion>
					<dd class="accordion-navigation">
						<a href="#panel1a">Introduction</a>
						<div id="panel1a" class="content science-profiles-detail-page--accordion-content-container">
							<cq:include script="introduction.jsp" />
						</div>
					</dd>
					<% if (displayProjectsTab) { %>
						<dd class="accordion-navigation">
							<a href="#panel2a">Projects</a>
	                        <div id="panel2a" class="content">
	                            <cq:include script="projects.jsp" />
	                        </div>
						</dd>
					<% } %>
					<% if (displayProfessionalActivitiesTab) { %>
						<dd class="accordion-navigation">
							<a href="#panel3b">Professional activities</a>
							<div id="panel3b" class="content science-profiles-detail-page--accordion-content-container">
								<cq:include script="professionalactivities.jsp" />
							</div>
						</dd>
					<% } %>
					<% if (displayPublicationsTab) { %>
						<dd class="accordion-navigation">
							<a href="#panel4c">Publications</a>
							<div id="panel4c" class="content science-profiles-detail-page--accordion-content-container">
								<cq:include script="publications.jsp" />
							</div>
						</dd>
					<% } %>
					<% if (displayTeachingActivitiesTab) { %>
						<dd class="accordion-navigation">
							<a href="#panel5d">Teaching and students</a>
							<div id="panel5d" class="content science-profiles-detail-page--accordion-content-container">
								<cq:include script="teachingactivities.jsp" />
							</div>
						</dd>
					<% } %>
					<% if (displayImpactAndOutreach) { %>
						<dd class="accordion-navigation">
							<a href="#panel6e">Impact and outreach</a>
							<div id="panel6e" class="content science-profiles-detail-page--accordion-content-container">
								<cq:include script="impactandoutreach.jsp" />
							</div>
						</dd>
					<% } %>
				</dl>
			</div>
		</div>
	</div>
</div>
<cq:include path="par" resourceType="foundation/components/parsys" />