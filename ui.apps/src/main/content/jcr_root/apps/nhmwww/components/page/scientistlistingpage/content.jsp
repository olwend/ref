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
					<label for="division">Filter by</label>
					<select id="division">
					    <option value="All" selected="selected">Department and Division</option>
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
					    <option class="collection" value="Research">Research</option>
					    <option class="group" value="Purvis Lab" data-collection="Research" data-group="Purvis Lab">&nbsp;&nbsp;&nbsp;&nbsp;F:Research, G:Purvis Lab</option> <%--Botany / Algae --%>
					    
					    <%-- Collection: Botany --%>
					    <option class="collection" value="Botany">Botany collections</option>
					    <option class="group" value="Algae" data-collection="Botany" data-group="Algae">&nbsp;&nbsp;&nbsp;&nbsp;Algae collections</option>
					    <option class="group" value="Diatoms" data-collection="Botany" data-group="Diatoms">&nbsp;&nbsp;&nbsp;&nbsp;Diatoms collections</option>
					    <option class="group" value="Lichens" data-collection="Botany" data-group="Lichens">&nbsp;&nbsp;&nbsp;&nbsp;Lichens collections</option>
					    <option class="group" value="Bryophytes" data-collection="Botany" data-group="Bryophytes">&nbsp;&nbsp;&nbsp;&nbsp;Bryophytes collections</option>
					    <option class="group" value="Ferns" data-collection="Botany" data-group="Ferns">&nbsp;&nbsp;&nbsp;&nbsp;Ferns collections</option>
					    <option class="group" value="British and Irish Herbarium" data-collection="Botany" data-group="British and Irish Herbarium">&nbsp;&nbsp;&nbsp;&nbsp;British and Irish Herbarium collections</option>
					    <option class="group" value="Historical collections" data-collection="Botany" data-group="Historical collections">&nbsp;&nbsp;&nbsp;&nbsp;Historical collections</option>
					
					    <%-- Collection: Entomology --%>
					    <option class="collection" value="Entomology">Entomology collections</option>
					    <option class="group" value="Hymenoptera" data-collection="Entomology" data-group="Hymenoptera">&nbsp;&nbsp;&nbsp;&nbsp;Hymenoptera collections</option>
					    <option class="group" value="Coleoptera" data-collection="Entomology" data-group="Coleoptera">&nbsp;&nbsp;&nbsp;&nbsp;Coleoptera collections</option>
					    <option class="group" value="Lepidoptera" data-collection="Entomology" data-group="Lepidoptera">&nbsp;&nbsp;&nbsp;&nbsp;Lepidoptera collections</option>
					    <option class="group" value="Siphonaptera" data-collection="Entomology" data-group="Siphonaptera">&nbsp;&nbsp;&nbsp;&nbsp;Siphonaptera collections</option>
					    <option class="group" value="Diptera" data-collection="Entomology" data-group="Diptera">&nbsp;&nbsp;&nbsp;&nbsp;Diptera collections</option>
					    <option class="group" value="Hemiptera" data-collection="Entomology" data-group="Hemiptera">&nbsp;&nbsp;&nbsp;&nbsp;Hemiptera collections</option>
					    <option class="group" value="Phthiraptera, Thysanoptera, and Psocoptera" data-collection="Entomology" data-group="Phthiraptera, Thysanoptera, and Psocoptera">&nbsp;&nbsp;&nbsp;&nbsp;Phthiraptera, Thysanoptera, and Psocoptera collections</option>
					    <option class="group" value="Odonata, Neuroptera and associated collections" data-collection="Entomology" data-group="Odonata, Neuroptera and associated collections">&nbsp;&nbsp;&nbsp;&nbsp;Odonata, Neuroptera and associated collections</option>
					    <option class="group" value="Apterygota" data-collection="Entomology" data-group="Apterygota">&nbsp;&nbsp;&nbsp;&nbsp;Apterygota collections</option>
					    <option class="group" value="Arachnida" data-collection="Entomology" data-group="Arachnida">&nbsp;&nbsp;&nbsp;&nbsp;Arachnida collections</option>
					    <option class="group" value="Myriapoda" data-collection="Entomology" data-group="Myriapoda">&nbsp;&nbsp;&nbsp;&nbsp;Myriapoda collections</option>
					    <option class="group" value="Onychophora" data-collection="Entomology" data-group="Onychophora">&nbsp;&nbsp;&nbsp;&nbsp;Onychophora collections</option>
					    <option class="group" value="Tardigrada" data-collection="Entomology" data-group="Tardigrada">&nbsp;&nbsp;&nbsp;&nbsp;Tardigrada collections</option>
					    <option class="group" value="Historical collections" data-collection="Entomology" data-group="Historical collections">&nbsp;&nbsp;&nbsp;&nbsp;Historical collections</option>
					
					    <%-- Collection: Zoology --%>
					    <option class="collection" value="Zoology">Zoology collections</option>
					    <option class="group" value="Invertebrate" data-collection="Zoology" data-group="Invertebrate">&nbsp;&nbsp;&nbsp;&nbsp;Invertebrate collections</option>
					    <option class="group" value="Vertebrate" data-collection="Zoology" data-group="Vertebrate">&nbsp;&nbsp;&nbsp;&nbsp;Vertebrate collections</option>
					    <option class="group" value="Bird" data-collection="Zoology" data-group="Bird">&nbsp;&nbsp;&nbsp;&nbsp;Bird collections</option>
					    <option class="group" value="Fish" data-collection="Zoology" data-group="Fish">&nbsp;&nbsp;&nbsp;&nbsp;Fish collections</option>
					    <option class="group" value="Amphbians" data-collection="Zoology" data-group="Amphbians">&nbsp;&nbsp;&nbsp;&nbsp;Amphbians collections</option>
					    <option class="group" value="Reptiles" data-collection="Zoology" data-group="Reptiles">&nbsp;&nbsp;&nbsp;&nbsp;Reptiles collections</option>
					    <option class="group" value="Mammals" data-collection="Zoology" data-group="Mammals">&nbsp;&nbsp;&nbsp;&nbsp;Mammals collections</option>
					
					    <%-- Collection: Palaeontology --%>
					    <option class="collection" value="Palaeontology">Palaeontology collections</option>
					    <option class="group" value="Anthropology" data-collection="Palaeontology" data-group="Anthropology">&nbsp;&nbsp;&nbsp;&nbsp;Anthropology collections</option>
					    <option class="group" value="Micropalaeontology" data-collection="Palaeontology" data-group="Micropalaeontology">&nbsp;&nbsp;&nbsp;&nbsp;Micropalaeontology collections</option>
					    <option class="group" value="Fossil invertebrate" data-collection="Palaeontology" data-group="Fossil invertebrate">&nbsp;&nbsp;&nbsp;&nbsp;Fossil invertebrate collections</option>
					    <option class="group" value="Fossil vertebrate" data-collection="Palaeontology" data-group="Fossil vertebrate">&nbsp;&nbsp;&nbsp;&nbsp;Fossil vertebrate collections</option>
					    <option class="group" value="Palaeobotany" data-collection="Palaeontology" data-group="Palaeobotany">&nbsp;&nbsp;&nbsp;&nbsp;Palaeobotany collections</option>
					
					    <%-- Collection: Mineralogy --%>
					    <option class="collection" value="Mineralogy">Mineralogy collections</option>
					    <option class="group" value="Meteorite" data-collection="Mineralogy" data-group="Meteorite">&nbsp;&nbsp;&nbsp;&nbsp;Meteorite collections</option>
					    <option class="group" value="Mineral" data-collection="Mineralogy" data-group="Mineral">&nbsp;&nbsp;&nbsp;&nbsp;Mineral collections</option>
					    <option class="group" value="Gemstone" data-collection="Mineralogy" data-group="Gemstone">&nbsp;&nbsp;&nbsp;&nbsp;Gemstone collections</option>
					    <option class="group" value="Ocean bottom deposit" data-collection="Mineralogy" data-group="Ocean bottom deposit">&nbsp;&nbsp;&nbsp;&nbsp;Ocean bottom deposit collections</option>
					    <option class="group" value="Ores" data-collection="Mineralogy" data-group="Ores">&nbsp;&nbsp;&nbsp;&nbsp;Ores collections</option>
					    <option class="group" value="Petrology" data-collection="Mineralogy" data-group="Petrology">&nbsp;&nbsp;&nbsp;&nbsp;Petrology collections</option>
					
					    <%-- Collection: British --%>
					    <option class="collection" value="British">British collections</option>
					
					    <%-- Collection: Molecular --%>
					    <option class="collection" value="Molecular">Molecular collections</option>
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
					collection="${fn:escapeXml(profile.collection)}"
					group="${fn:escapeXml(profile.collectionGroup)}"
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
							<%-- Debugging purpose --%>
									<br/>Function:<c:out value="${profile.collection}"/>
									<br/>FGroup:<c:out value="${profile.collectionGroup}"/>
								<%-- 
								--%>
							<%-- Debugging purpose --%>
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
							<%-- <c:set var="tempString" value="${fn:escapeXml(profile.specialisms)}" /> &hellip;--%>		
							<c:choose>
								<c:when test="${fn:length(profile.specialisms) > 175}">
									<c:out value="${fn:substring(profile.specialisms, 0, 175)}"/>&hellip;
								</c:when>
								<c:otherwise>
									<c:out value="${profile.specialisms}"/>
								</c:otherwise>							
							</c:choose>
						</p>
					</div>
				</div>
			</div>
		</c:forEach>
	</div>
	<div style="text-align: center;">
		<a href="#" id="show-more">
			Show more
		</a>	
	</div>
</div>
