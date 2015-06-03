<%@include file="/apps/nhmwww/components/global.jsp"%> 
<%@page import="com.day.cq.wcm.api.WCMMode,
				java.util.List,
				uk.ac.nhm.nhm_www.core.services.StaffProfileService" %>
<%@ page trimDirectiveWhitespaces="true" %>

<%
	final StaffProfileService profileService = sling.getService(StaffProfileService.class);
	final List<StaffProfileService.StaffProfile> profileList = profileService.getStaffProfile(resource);
	
	pageContext.setAttribute("profileList", profileList);
%>
<div class="main-section"><!-- CONTENT WRAPPER -->
	<div class="title">
	    <div class="row title">
		    <div class="small-12 columns">
		    	<h1>Staff directory</h1>
		    </div>
	    </div>
	</div>
	<div class="search_content"><!-- start content search -->
		<div class="row"><!-- start row --> 
			<div class="large-4 medium-6 columns large-left-section">
				By name, keywords and specialisms
				<div class="search-first-name">
					<input type="text" id="firstNameInput" name="First Name" placeholder='First name' />
				</div>
				<div class="search-second-name">
					<input type="text" id="surnameInput" name="Surname" placeholder='Surname' />
				</div>
				<div class="search-keywords">
					<input type="text" id="keywordsInput" name="Keywords" placeholder='Keywords' />
				</div>
			</div>

			<div class="large-4 medium-6 columns large-left-section">
				<div class="search-depts-divs">
					<h2><label for="division">Department and division</label></h2>
					Filter by
					<select id="division">
					    <option value="All" selected="selected">Deparment and Division</option>
					    <%-- Department: Life Sciences --%>
					    <option class="department" value="Life Sciences">Life sciences</option>
					    <option class="division" value="Genomics and Microbial diversity" data-department="Life Sciences" data-division="Genomics and Microbial Biodiversity Division">&nbsp;&nbsp;&nbsp;&nbsp;Genomics and Microbial diversity</option>
					    <option class="division" value="Plants" data-department="Life Sciences" data-division="Plants Division">&nbsp;&nbsp;&nbsp;&nbsp;Plants</option>
					    <option class="division" value="Insects" data-department="Life Sciences" data-division="Insects">&nbsp;&nbsp;&nbsp;&nbsp;Insects</option>
					    <option class="division" value="Parasites and vectors" data-department="Life Sciences" data-division="Parasites and Vectors Division">&nbsp;&nbsp;&nbsp;&nbsp;Parasites and vectors</option>
					    <option class="division" value="Invertebrates" data-department="Life Sciences" data-division="Invertebrates">&nbsp;&nbsp;&nbsp;&nbsp;Invertebrates</option>
					    <option class="division" value="Vertebrates" data-department="Life Sciences" data-division="Vertebrates Division">&nbsp;&nbsp;&nbsp;&nbsp;Vertebrates</option>
					    <option class="division" value="Angela Marmont Centre" data-department="Life Sciences" data-division="Angela Marmont Centre">&nbsp;&nbsp;&nbsp;&nbsp;Angela Marmont Centre</option>
					    
					    <%-- Department: Earth Sciences --%>
					    <option class="department" value="Earth Sciences">Earth sciences</option>
					    <option class="division" value="Mineral and planentary sciences" data-department="Earth Sciences" data-division="Mineral and Planetary Sciences Division">&nbsp;&nbsp;&nbsp;&nbsp;Mineral and planentary sciences</option>
					    <option class="division" value="Economic and environmental earth sciences" data-department="Earth Sciences" data-division="Economic and Environmental Earth Sciences">&nbsp;&nbsp;&nbsp;&nbsp;Economic and environmental earth sciences</option>
					    <option class="division" value="Vertebrate and anthropology palaeobiology" data-department="Earth Sciences" data-division="Vertebrates and Anthropology Palaeobiology">&nbsp;&nbsp;&nbsp;&nbsp;Vertebrate and anthropology palaeobiology</option>
					    <option class="division" value="Invertebrates and plants" data-department="Earth Sciences" data-division="Invertebrates and Plants Division">&nbsp;&nbsp;&nbsp;&nbsp;Invertebrates and plants</option>
					    <option class="division" value="Department Management Team" data-department="Earth Sciences" data-division="Department Management Team">&nbsp;&nbsp;&nbsp;&nbsp;Department Management Team</option>
					    
					    <%-- Department: Core research laboratories --%>
					    <option class="department" value="Science Facilities">Core research laboratories</option>
					    <option class="division" value="Conservation Centre" data-department="Science Facilities" data-division="Conservation Centre">&nbsp;&nbsp;&nbsp;&nbsp;Conservation Centre</option>
					    <option class="division" value="Imaging and Analysis Centre" data-department="Science Facilities" data-division="Imaging and Analysis Centre">&nbsp;&nbsp;&nbsp;&nbsp;Imaging and Analysis Centre</option>
					    <option class="division" value="Molecular Biology Laboratories" data-department="Science Facilities" data-division="Molecular Biology Laboratories">&nbsp;&nbsp;&nbsp;&nbsp;Molecular Biology Laboratories</option>
					    <option class="division" value="Digitisation Centre" data-department="Science Facilities" data-division="Digitisation centre">&nbsp;&nbsp;&nbsp;&nbsp;Digitisation Centre</option>
					    
					    <%-- Department: Library and Archives --%>
					    <option class="department" value="Library and Archives">Library and Archives</option>
					    <option class="division" value="Digital Services Team" data-department="Library and Archives" data-division="LA Digital Services Team">&nbsp;&nbsp;&nbsp;&nbsp;Digital Services Team</option>
					    <option class="division" value="Library Services Team" data-department="Library and Archives" data-division="LA Library Services Team">&nbsp;&nbsp;&nbsp;&nbsp;Library Services Team </option>
					    <option class="division" value="LA IPR" data-department="Library and Archives" data-division="LA IPR">&nbsp;&nbsp;&nbsp;&nbsp;LA IPR</option>
					    <option class="division" value="LA Modern Collections Team" data-department="Library and Archives" data-division="LA Modern Collections Team">&nbsp;&nbsp;&nbsp;&nbsp;LA Modern Collections Team</option>
					    <option class="division" value="LA Museum Archives and Records Management" data-department="Library and Archives" data-division="LA Museum Archives and Records Management">&nbsp;&nbsp;&nbsp;&nbsp;LA Museum Archives and Records Management</option>
					    <option class="division" value="LA Special Collections Team" data-department="Library and Archives" data-division="LA Special Collections Team">&nbsp;&nbsp;&nbsp;&nbsp;LA Special Collections Team</option>
					    <option class="division" value="LA HOD and Admin" data-department="Library and Archives" data-division="LA HOD and Admin">&nbsp;&nbsp;&nbsp;&nbsp;LA HOD and Admin</option>
					    
					    <option class="department" value="Science Directorate">Science directorate</option>
					</select>
					<select id="collections">
					    <option value="All" selected="selected">Activity Type</option>
					    <%-- Department: Life Sciences --%>
					    <option class="department" value="Life Sciences">Life sciences</option>
					    <option class="division" value="Genomics and Microbial diversity" data-department="Life Sciences" data-division="Genomics and Microbial Biodiversity Division">&nbsp;&nbsp;&nbsp;&nbsp;Genomics and Microbial diversity</option>
					    <option class="division" value="Plants" data-department="Life Sciences" data-division="Plants Division">&nbsp;&nbsp;&nbsp;&nbsp;Plants</option>
					    <option class="division" value="Insects" data-department="Life Sciences" data-division="Insects">&nbsp;&nbsp;&nbsp;&nbsp;Insects</option>
					    <option class="division" value="Parasites and vectors" data-department="Life Sciences" data-division="Parasites and Vectors Division">&nbsp;&nbsp;&nbsp;&nbsp;Parasites and vectors</option>
					    <option class="division" value="Invertebrates" data-department="Life Sciences" data-division="Invertebrates">&nbsp;&nbsp;&nbsp;&nbsp;Invertebrates</option>
					    <option class="division" value="Vertebrates" data-department="Life Sciences" data-division="Vertebrates Division">&nbsp;&nbsp;&nbsp;&nbsp;Vertebrates</option>
					    <option class="division" value="Angela Marmont Centre" data-department="Life Sciences" data-division="Angela Marmont Centre">&nbsp;&nbsp;&nbsp;&nbsp;Angela Marmont Centre</option>
					</select>
				</div>
			</div>
			<div class="large-4 columns">
				<input id="search" type="button" value="Search the staff directory" name="submit" class="arrow"/>
			</div>
		</div><!--end start row -->
	</div><!-- / start content search -->
	
	<div class="row profiles_row">
		<div class="large-3 medium-4 columns large-left-section">
			<div id="image" class="large-5 medium-6 columns profiles_table profile-content table_header hide-for-small-only">
				<h5>Image</h5>
			</div>
			<div id="name" class="large-7 medium-6 columns profiles_table profile-content table_header hide-for-small-only">
				<h5>Name &and;</h5>
			</div>
		</div>
		<div id="job" class="large-2 medium-2 columns large-left-section profiles_table profile-content table_header hide-for-small-only">
			<h5>Job title &and;</h5>
		</div>
		<div id="departAndDiv" class="large-3 medium-2 columns large-left-section profiles_table profile-content table_header hide-for-small-only">
			<h5>Department and division  (&and;)</h5>
		</div>
		<div id="specialisms" class="large-4 medium-4 columns profiles_table profile-content table_header hide-for-small-only">
			<h5>Specialisms &and;</h5>
		</div>
	</div>
	<div id="peopleList" class="row">
		<c:forEach var="profile" items="${profileList}">
			<div class="row profiles_row"  >
				<div firstname="${fn:escapeXml(profile.firstName)}"
					secondname="${fn:escapeXml(profile.lastName)}"
					activity="${fn:escapeXml(profile.job)}"
					department="${fn:escapeXml(profile.department)}"
					division="${fn:escapeXml(profile.division)}"
					specialisms="${fn:escapeXml(profile.specialisms)}"
					style="display:none;">
					
					<div class="large-3 medium-4 columns large-left-section">
						<div class="large-5 medium-6 columns">
<%
		final WCMMode beforeMode = WCMMode.fromRequest(slingRequest);
		WCMMode.PREVIEW.toRequest(slingRequest);
%>
							<cq:include path="${profile.imagePath}" resourceType="nhmwww/components/functional/foundation5image" />
<%
		beforeMode.toRequest(slingRequest);
%>
						</div>
						<div class="large-7 medium-6 columns profiles_table profile-content">
							<p>
								<strong class="show-for-small-only">Name:</strong>
								<a href="${fn:escapeXml(profile.url)}"> 
									<c:out value="${profile.lastName}"/>
									<c:out value=", "/>
									<c:out value="${profile.firstName}"/>
								</a>
							</p>
						</div>
					</div>
					<div class="large-2 medium-2 columns large-left-section profiles_table profile-content ">
						<p>
							<strong class="show-for-small-only">Job:</strong>
							<c:out value="${profile.job}" />
						</p>
					</div>
					<div class="large-3 medium-2 columns large-left-section profiles_table profile-content ">
						<p>
							<strong class="show-for-small-only">Department and Division:</strong>
							<c:out value="${profile.department}"/>
							<br/>
							<c:out value="${profile.division}" />
						</p>
					</div>
					<div class="large-4 medium-4 columns profiles_table profile-content">
						<p>
							<c:if test="${not empty profile.specialisms}">
								<strong class="show-for-small-only">Specialisms:</strong>
							</c:if>
							<c:set var="tempString" value="${fn:escapeXml(profile.specialisms)}" />
							<c:out value="${fn:substring(tempString, 0, 175)} }"/> 
							
							<%-- Truncate with style="text-overflow: ellipsis;" in div OR ${fn:substring(text, 0, 175)} in c:out --%>
						</p>
					</div>
				</div>
			</div>
		</c:forEach>
	</div>
</div>
