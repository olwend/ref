<%@page import="uk.ac.nhm.nhm_www.core.model.science.proactivities.ProfessionalActivity,
				uk.ac.nhm.nhm_www.core.componentHelpers.ScientistProfileHelper,
				uk.ac.nhm.nhm_www.core.model.science.Publication,
				java.util.Set,
				org.apache.commons.lang3.StringUtils"%>
<%@include file="/apps/nhmwww/components/global.jsp"%> 
<%

%>
<div class="row">
	<!-- MAIN ROW -->
	<div class="large-8 medium-8 columns large-left-section our-science">
		<!-- body row -->
		<cq:include script="statement.jsp" />
		<div class="large-12 medium-12 columns ">
			<cq:include script="qualifications.jsp" />
			<cq:include script="employmenthistory.jsp" />
		</div>
	</div>
	<!-- end body row -->
	<div class="large-4 medium-4 columns">
		<!-- aside row -->
		<cq:include script="highlightedpublications.jsp" />
		<cq:include script="externallinks.jsp" />
		<!-- end aside row -->
	</div>
	<!-- / END MAIN ROW-->
</div>
<!-- /END CONTENT WRAPPER -->