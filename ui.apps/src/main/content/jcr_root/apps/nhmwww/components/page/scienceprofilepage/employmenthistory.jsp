<%@page import="uk.ac.nhm.nhm_www.core.componentHelpers.ScientistProfileHelper,
				uk.ac.nhm.nhm_www.core.model.science.WorkExperience,
				java.util.Set"%>
<%@include file="/apps/nhmwww/components/global.jsp"%> 
<%
	final ScientistProfileHelper helper = new ScientistProfileHelper(resource);

	final Set<WorkExperience> academics = helper.getAcademicHistory();
	final Set<WorkExperience> nonAcademics = helper.getNonAcademicHistory();

	if ((academics != null && academics.size() > 0) || (nonAcademics != null && nonAcademics.size() > 0)) {
%>			
<h3>Employment history</h3>
<div class="employment-list description-list">
<%
		if (academics != null && academics.size() > 0) {
%>
	<h4>Academic</<h4>
<%
			for (final WorkExperience academic:academics) {
				

				if (academic.isValid()) {
					final String position 		 = academic.getPosition();
					final String organisation 	 = academic.getOrganisation();
					final String subOrganisation = academic.getSubOrganisation();
					final String city 			 = academic.getCity();
					final String country 		 = academic.getCountry();
					final int startDate			 = academic.getStartDate();
					final int endDate 			 = academic.getEndDate();
					
					String academicString = ((position != null) ? position : "") +
							((organisation != null) ? ", " + organisation : "") +
							((subOrganisation != null) ? ", " + subOrganisation : "") +
							((city != null) ? ", " + city : "") +
							((country != null) ? ", " + country : "");
					
					if (startDate > 0) {
						academicString = academicString + ", " + startDate + " - " + ((endDate > 0) ? endDate : "ongoing");
					}
%> 
		<p><%= academicString %></p>
<%		
				}

			}
		}
	
		if (nonAcademics != null && nonAcademics.size() > 0) {
%>
	<h4>Non-academic</h4>
<%
			for (final WorkExperience nonAcademic:nonAcademics) {
				if (nonAcademic.isValid()) {
					final String position 		 = nonAcademic.getPosition();
					final String organisation 	 = nonAcademic.getOrganisation();
					final String subOrganisation = nonAcademic.getSubOrganisation();
					final String city 			 = nonAcademic.getCity();
					final String country 		 = nonAcademic.getCountry();
					final int startDate			 = nonAcademic.getStartDate();
					final int endDate 			 = nonAcademic.getEndDate();
					
					String nonAcademicString = ((position != null) ? position : "") +
							((organisation != null) ? ", " + organisation : "") +
							((subOrganisation != null) ? ", " + subOrganisation : "") +
							((city != null) ? ", " + city : "") +
							((country != null) ? ", " + country : "");
					
					if (startDate > 0) {
						nonAcademicString = nonAcademicString + ", " + startDate + " - " + ((endDate > 0) ? endDate : "ongoing");
					}
%>
		<p><%= nonAcademicString %></p>
<%		
				}
			}
		}
%>
</div>
<%
	}
%>