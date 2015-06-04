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
					    <option value="All" selected="selected">Collections</option>
					    <%-- Collection: Botany --%>
					    <option class="function" value="Botany">Botany collections</option>
					    <option class="fgroup" value="Algae" data-function="Botany" data-fgroup="Algae">&nbsp;&nbsp;&nbsp;&nbsp;Algae collections</option>
					    <option class="fgroup" value="Diatoms" data-function="Botany" data-fgroup="Diatoms">&nbsp;&nbsp;&nbsp;&nbsp;Diatoms collections</option>
					    <option class="fgroup" value="Lichens" data-function="Botany" data-fgroup="Lichens">&nbsp;&nbsp;&nbsp;&nbsp;Lichens collections</option>
					    <option class="fgroup" value="Bryophytes" data-function="Botany" data-fgroup="Bryophytes">&nbsp;&nbsp;&nbsp;&nbsp;Bryophytes collections</option>
					    <option class="fgroup" value="Ferns" data-function="Botany" data-fgroup="Ferns">&nbsp;&nbsp;&nbsp;&nbsp;Ferns collections</option>
					    <option class="fgroup" value="British and Irish Herbarium" data-function="Botany" data-fgroup="British and Irish Herbarium">&nbsp;&nbsp;&nbsp;&nbsp;British and Irish Herbarium collections</option>
					    <option class="fgroup" value="Historical collections" data-function="Botany" data-fgroup="Historical collections">&nbsp;&nbsp;&nbsp;&nbsp;Historical collections</option>
					
					    <%-- Collection: Entomology --%>
					    <option class="function" value="Entomology">Entomology collections</option>
					    <option class="fgroup" value="Hymenoptera" data-function="Entomology" data-fgroup="Hymenoptera">&nbsp;&nbsp;&nbsp;&nbsp;Hymenoptera collections</option>
					    <option class="fgroup" value="Coleoptera" data-function="Entomology" data-fgroup="Coleoptera">&nbsp;&nbsp;&nbsp;&nbsp;Coleoptera collections</option>
					    <option class="fgroup" value="Lepidoptera" data-function="Entomology" data-fgroup="Lepidoptera">&nbsp;&nbsp;&nbsp;&nbsp;Lepidoptera collections</option>
					    <option class="fgroup" value="Siphonaptera" data-function="Entomology" data-fgroup="Siphonaptera">&nbsp;&nbsp;&nbsp;&nbsp;Siphonaptera collections</option>
					    <option class="fgroup" value="Diptera" data-function="Entomology" data-fgroup="Diptera">&nbsp;&nbsp;&nbsp;&nbsp;Diptera collections</option>
					    <option class="fgroup" value="Hemiptera" data-function="Entomology" data-fgroup="Hemiptera">&nbsp;&nbsp;&nbsp;&nbsp;Hemiptera collections</option>
					    <option class="fgroup" value="Phthiraptera, Thysanoptera, and Psocoptera" data-function="Entomology" data-fgroup="Phthiraptera, Thysanoptera, and Psocoptera">&nbsp;&nbsp;&nbsp;&nbsp;Phthiraptera, Thysanoptera, and Psocoptera collections</option>
					    <option class="fgroup" value="Odonata, Neuroptera and associated collections" data-function="Entomology" data-fgroup="Odonata, Neuroptera and associated collections">&nbsp;&nbsp;&nbsp;&nbsp;Odonata, Neuroptera and associated collections</option>
					    <option class="fgroup" value="Apterygota" data-function="Entomology" data-fgroup="Apterygota">&nbsp;&nbsp;&nbsp;&nbsp;Apterygota collections</option>
					    <option class="fgroup" value="Arachnida" data-function="Entomology" data-fgroup="Arachnida">&nbsp;&nbsp;&nbsp;&nbsp;Arachnida collections</option>
					    <option class="fgroup" value="Myriapoda" data-function="Entomology" data-fgroup="Myriapoda">&nbsp;&nbsp;&nbsp;&nbsp;Myriapoda collections</option>
					    <option class="fgroup" value="Onychophora" data-function="Entomology" data-fgroup="Onychophora">&nbsp;&nbsp;&nbsp;&nbsp;Onychophora collections</option>
					    <option class="fgroup" value="Tardigrada" data-function="Entomology" data-fgroup="Tardigrada">&nbsp;&nbsp;&nbsp;&nbsp;Tardigrada collections</option>
					    <option class="fgroup" value="Historical collections" data-function="Entomology" data-fgroup="Historical collections">&nbsp;&nbsp;&nbsp;&nbsp;Historical collections</option>
					
					    <%-- Collection: Zoology --%>
					    <option class="function" value="Zoology">Zoology collections</option>
					    <option class="fgroup" value="Invertebrate" data-function="Zoology" data-fgroup="Invertebrate">&nbsp;&nbsp;&nbsp;&nbsp;Invertebrate collections</option>
					    <option class="fgroup" value="Vertebrate" data-function="Zoology" data-fgroup="Vertebrate">&nbsp;&nbsp;&nbsp;&nbsp;Vertebrate collections</option>
					    <option class="fgroup" value="Bird" data-function="Zoology" data-fgroup="Bird">&nbsp;&nbsp;&nbsp;&nbsp;Bird collections</option>
					    <option class="fgroup" value="Fish" data-function="Zoology" data-fgroup="Fish">&nbsp;&nbsp;&nbsp;&nbsp;Fish collections</option>
					    <option class="fgroup" value="Amphbians" data-function="Zoology" data-fgroup="Amphbians">&nbsp;&nbsp;&nbsp;&nbsp;Amphbians collections</option>
					    <option class="fgroup" value="Reptiles" data-function="Zoology" data-fgroup="Reptiles">&nbsp;&nbsp;&nbsp;&nbsp;Reptiles collections</option>
					    <option class="fgroup" value="Mammals" data-function="Zoology" data-fgroup="Mammals">&nbsp;&nbsp;&nbsp;&nbsp;Mammals collections</option>
					
					    <%-- Collection: Palaeontology --%>
					    <option class="function" value="Palaeontology">Palaeontology collections</option>
					    <option class="fgroup" value="Anthropology" data-function="Palaeontology" data-fgroup="Anthropology">&nbsp;&nbsp;&nbsp;&nbsp;Anthropology collections</option>
					    <option class="fgroup" value="Micropalaeontology" data-function="Palaeontology" data-fgroup="Micropalaeontology">&nbsp;&nbsp;&nbsp;&nbsp;Micropalaeontology collections</option>
					    <option class="fgroup" value="Fossil invertebrate" data-function="Palaeontology" data-fgroup="Fossil invertebrate">&nbsp;&nbsp;&nbsp;&nbsp;Fossil invertebrate collections</option>
					    <option class="fgroup" value="Fossil vertebrate" data-function="Palaeontology" data-fgroup="Fossil vertebrate">&nbsp;&nbsp;&nbsp;&nbsp;Fossil vertebrate collections</option>
					    <option class="fgroup" value="Palaeobotany" data-function="Palaeontology" data-fgroup="Palaeobotany">&nbsp;&nbsp;&nbsp;&nbsp;Palaeobotany collections</option>
					
					    <%-- Collection: Mineralogy --%>
					    <option class="function" value="Mineralogy">Mineralogy collections</option>
					    <option class="fgroup" value="Meteorite" data-function="Mineralogy" data-fgroup="Meteorite">&nbsp;&nbsp;&nbsp;&nbsp;Meteorite collections</option>
					    <option class="fgroup" value="Mineral" data-function="Mineralogy" data-fgroup="Mineral">&nbsp;&nbsp;&nbsp;&nbsp;Mineral collections</option>
					    <option class="fgroup" value="Gemstone" data-function="Mineralogy" data-fgroup="Gemstone">&nbsp;&nbsp;&nbsp;&nbsp;Gemstone collections</option>
					    <option class="fgroup" value="Ocean bottom deposit" data-function="Mineralogy" data-fgroup="Ocean bottom deposit">&nbsp;&nbsp;&nbsp;&nbsp;Ocean bottom deposit collections</option>
					    <option class="fgroup" value="Ores" data-function="Mineralogy" data-fgroup="Ores">&nbsp;&nbsp;&nbsp;&nbsp;Ores collections</option>
					    <option class="fgroup" value="Petrology" data-function="Mineralogy" data-fgroup="Petrology">&nbsp;&nbsp;&nbsp;&nbsp;Petrology collections</option>
					
					    <%-- Collection: British --%>
					    <option class="function" value="British">British collections</option>
					
					    <%-- Collection: Molecular --%>
					    <option class="function" value="Molecular">Molecular collections</option>
					
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
							<%-- <c:set var="tempString" value="${fn:escapeXml(profile.specialisms)}" /> --%>
							<c:out value="${fn:substring(profile.specialisms, 0, 175)}"/> 
						</p>
					</div>
				</div>
			</div>
		</c:forEach>
	</div>
</div>
