<%@page import="uk.ac.nhm.core.model.science.proactivities.ProfessionalActivity,
				uk.ac.nhm.core.componentHelpers.ScientistProfileHelper,
				uk.ac.nhm.core.model.science.Publication,
				java.util.Set,
				org.apache.commons.lang3.StringUtils"%>
<%@include file="/apps/nhmwww/components/global.jsp"%> 
<%

%>
<%-- <div class="row"> --%>
	<!-- MAIN ROW -->
	<div class="small-12 medium-8 large-8 columns">
		<!-- body row -->
		<div class="science-profiles-detail-page--tabs-content-container">
			<cq:include script="statement.jsp" />
			<cq:include script="qualifications.jsp" />
			<cq:include script="employmenthistory.jsp" />
		</div>
	</div>
	<!-- end body row -->
	<div class="small-12 medium-4 large-4 columns">
		<!-- aside row -->
		<cq:include script="highlightedpublications.jsp" />
		<cq:include script="externallinks.jsp" />
		<!-- end aside row -->
	</div>
	<!-- / END MAIN ROW-->
<%-- </div> --%>
<!-- /END CONTENT WRAPPER -->