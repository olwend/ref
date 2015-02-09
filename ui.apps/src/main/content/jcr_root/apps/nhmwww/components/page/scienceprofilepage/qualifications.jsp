<%@page import="uk.ac.nhm.nhm_www.core.componentHelpers.ScientistProfileHelper,
				uk.ac.nhm.nhm_www.core.model.science.Qualification,
				java.util.Set"%>
<%@include file="/apps/nhmwww/components/global.jsp"%> 
<%
	final ScientistProfileHelper helper = new ScientistProfileHelper(resource);

	final Set<Qualification> degrees 			   = helper.getDegrees();
	final Set<Qualification> certificates 		   = helper.getCertificates();
	final Set<Qualification> postgraduateTrainings = helper.getPostGraduateTrainings();
	
	if ((degrees == null || degrees.size() == 0) 
			&& (certificates == null || certificates.size() == 0) 
			&& (postgraduateTrainings == null || postgraduateTrainings.size() == 0)) {
		return;
	}
%>
<h2>Qualifications</h2>
<div class="qualifications-list description-list">
<%
	if (degrees != null && degrees.size() > 0) {
%>
	<h3>Degrees</h3>
<%
		for (final Qualification degree:degrees) {
			final int fromYear		 = degree.getFromYear();
			final int year 			 = degree.getToYear();
			final String majorField  = degree.getMajorFieldStudy();
			final String institution = degree.getInstitution();
			final String city 		 = degree.getCity();
			final String country 	 = degree.getCountry();
			
			String qualificationString =  degree.getQualification() +
					((majorField == null) ? "" : ", " + majorField) +
					((institution == null) ? "" : ", " + institution) +
					((city == null) ? "" : ", " + city) +
					((country == null) ? "" : ", " + country);
			
			if (fromYear > 0) {
				qualificationString = qualificationString + ", " + fromYear + " - " + (year > 0 ? year : "ongoing");
			}	
%>
	<p>
		<%= qualificationString %>
	</p>
<%		
		}
	}
	if (certificates != null && certificates.size() > 0) {
%>
	<h3>Certificates</h3>
<%
		for (final Qualification certificate:certificates) {
			final int fromYear 		   = certificate.getFromYear();
			final int year 			   = certificate.getToYear();
			final String institution   = certificate.getInstitution();
			final String qualification = certificate.getQualification();
			final String city 		   = certificate.getCity();
			final String country 	   = certificate.getCountry();
			
			String certificateString = institution +
					// ", " + department + 
					((city == null) ? "" : ", " + city) +
					((country == null) ? "" : ", " + country) +
					", " + qualification;
			
			if (fromYear > 0) {
				certificateString = certificateString + ", " + fromYear + " - " + (year > 0 ? year : "ongoing");
			}	
%>
	<p><%= certificateString %></p>
<%		
		}
	}
	if (postgraduateTrainings != null && postgraduateTrainings.size() > 0) {
%>
	<h3>Post Graduate training</h3>
<%
		for (final Qualification postgraduateTraining:postgraduateTrainings) {
			final int fromYear 		   = postgraduateTraining.getFromYear();
			final int year 			   = postgraduateTraining.getToYear();
			final String institution   = postgraduateTraining.getInstitution();
			final String qualification = postgraduateTraining.getQualification();
			
			final String city 	 = postgraduateTraining.getCity();
			final String country = postgraduateTraining.getCountry();
			
			String postgraduateString = qualification + ", " + institution +
					((city == null) ? "" : ", " + city) +
					((country == null) ? "" : ", " + country);
			
			if (fromYear > 0) {
				postgraduateString = postgraduateString + ", " + fromYear + " - " + (year > 0 ? year : "ongoing");
			}
%>
	<p><%= postgraduateString %></p>
<%		
		}
	}
%>
</div>