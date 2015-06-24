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
	    <div class="row title-bar">
		    <div class="small-12 columns">
		    	<h1>Staff directory</h1>
		    	
		    </div>
	    </div>
	</div>
	
	
	<div class="row directory-search directory-search__science-profiles">
		<ul class="small-block-grid-1 medium-block-grid-3 directory-search--fields-block-grid">
			<li>
				<legend class="directory-search--label">By name, keywords and specialisms</legend>
				<div class="small-6 columns pr-10">
					<div class="search-second-name">
						<input type="text" id="surnameInput" name="Surname" placeholder='Surname' />
					</div>
				</div>
				<div class="small-6 columns">
					<div class="search-first-name">
						<input type="text" id="firstNameInput" name="First Name" placeholder='First name' />
					</div>
				</div>
				<div class="small-12 columns">
					<div class="search-keywords">
						<input type="text" id="keywordsInput" name="Keywords" placeholder='Specialisms' />
					</div>
				</div>
			</li>
			<li>
				<div class="small-12 medium-12 large-12 columns">
					<legend class="directory-search--label">Filter by</legend>
				</div>
				<div class="small-12 columns">
					<select id="division">
						<option value="All" selected="selected">Department and Division</option>
						<%-- Department: Life Sciences --%>
						<option class="department" id="Life Sciences" value="Life Sciences">Life sciences</option>
						<option class="division" id="Genomics and Microbial diversity" value="Genomics and Microbial diversity" data-department="Life Sciences" data-division="Genomics and Microbial Biodiversity Division">&nbsp;&nbsp;&nbsp;&nbsp;Genomics and Microbial diversity</option>
						<option class="division" id="Plants" value="Plants" data-department="Life Sciences" data-division="Plants Division">&nbsp;&nbsp;&nbsp;&nbsp;Plants</option>
						<option class="division" id="Insects" value="Insects" data-department="Life Sciences" data-division="Insects">&nbsp;&nbsp;&nbsp;&nbsp;Insects</option>
						<option class="division" id="Parasites and vectors" value="Parasites and vectors" data-department="Life Sciences" data-division="Parasites and Vectors Division">&nbsp;&nbsp;&nbsp;&nbsp;Parasites and vectors</option>
						<option class="division" id="Invertebrates" value="Invertebrates" data-department="Life Sciences" data-division="Invertebrates">&nbsp;&nbsp;&nbsp;&nbsp;Invertebrates</option>
						<option class="division" id="Vertebrates" value="Vertebrates" data-department="Life Sciences" data-division="Vertebrates Division">&nbsp;&nbsp;&nbsp;&nbsp;Vertebrates</option>
						<option class="division" id="Angela Marmont Centre" value="Angela Marmont Centre" data-department="Life Sciences" data-division="Angela Marmont Centre">&nbsp;&nbsp;&nbsp;&nbsp;Angela Marmont Centre</option>
					    
						<%-- Department: Earth Sciences --%>
						<option class="department" id="Earth Sciences" value="Earth Sciences">Earth sciences</option>
						<option class="division" id="Mineral and planentary sciences" value="Mineral and planentary sciences" data-department="Earth Sciences" data-division="Mineral and Planetary Sciences Division">&nbsp;&nbsp;&nbsp;&nbsp;Mineral and planentary sciences</option>
						<option class="division" id="Economic and environmental earth sciences" value="Economic and environmental earth sciences" data-department="Earth Sciences" data-division="Economic and Environmental Earth Sciences">&nbsp;&nbsp;&nbsp;&nbsp;Economic and environmental earth sciences</option>
						<option class="division" id="Vertebrate and anthropology palaeobiology" value="Vertebrate and anthropology palaeobiology" data-department="Earth Sciences" data-division="Vertebrates and Anthropology Palaeobiology">&nbsp;&nbsp;&nbsp;&nbsp;Vertebrate and anthropology palaeobiology</option>
						<option class="division" id="Invertebrates and plants" value="Invertebrates and plants" data-department="Earth Sciences" data-division="Invertebrates and Plants Division">&nbsp;&nbsp;&nbsp;&nbsp;Invertebrates and plants</option>
						<option class="division" id="Department Management Team" value="Department Management Team" data-department="Earth Sciences" data-division="Department Management Team">&nbsp;&nbsp;&nbsp;&nbsp;Department Management Team</option>
						
						<%-- Department: Core research laboratories --%>
						<option class="department" id="Science Facilities"value="Science Facilities">Core research laboratories</option>
						<option class="division" id="Conservation Centre" value="Conservation Centre" data-department="Science Facilities" data-division="Conservation Centre">&nbsp;&nbsp;&nbsp;&nbsp;Conservation Centre</option>
						<option class="division" id="Imaging and Analysis Centre" value="Imaging and Analysis Centre" data-department="Science Facilities" data-division="Imaging and Analysis Centre">&nbsp;&nbsp;&nbsp;&nbsp;Imaging and Analysis Centre</option>
						<option class="division" id="Molecular Biology Laboratories" value="Molecular Biology Laboratories" data-department="Science Facilities" data-division="Molecular Biology Laboratories">&nbsp;&nbsp;&nbsp;&nbsp;Molecular Biology Laboratories</option>
						<option class="division" id="Digitisation Centre" value="Digitisation Centre" data-department="Science Facilities" data-division="Digitisation centre">&nbsp;&nbsp;&nbsp;&nbsp;Digitisation Centre</option>
						
						<%-- Department: Library and Archives --%>
						<option class="department" id="Library and Archives"value="Library and Archives">Library and Archives</option>
						<option class="division" id="Digital Services Team" value="Digital Services Team" data-department="Library and Archives" data-division="LA Digital Services Team">&nbsp;&nbsp;&nbsp;&nbsp;Digital Services Team</option>
						<option class="division" id="Library Services Team" value="Library Services Team" data-department="Library and Archives" data-division="LA Library Services Team">&nbsp;&nbsp;&nbsp;&nbsp;Library Services Team </option>
						<option class="division" id="LA IPR" value="LA IPR" data-department="Library and Archives" data-division="LA IPR">&nbsp;&nbsp;&nbsp;&nbsp;LA IPR</option>
						<option class="division" id="LA Modern Collections Team"  value="LA Modern Collections Team" data-department="Library and Archives" data-division="LA Modern Collections Team">&nbsp;&nbsp;&nbsp;&nbsp;LA Modern Collections Team</option>
						<option class="division" id="LA Museum Archives and Records Management" value="LA Museum Archives and Records Management" data-department="Library and Archives" data-division="LA Museum Archives and Records Management">&nbsp;&nbsp;&nbsp;&nbsp;LA Museum Archives and Records Management</option>
						<option class="division" id="LA Special Collections Team" value="LA Special Collections Team" data-department="Library and Archives" data-division="LA Special Collections Team">&nbsp;&nbsp;&nbsp;&nbsp;LA Special Collections Team</option>
						<option class="division" id="LA HOD and Admin" value="LA HOD and Admin" data-department="Library and Archives" data-division="LA HOD and Admin">&nbsp;&nbsp;&nbsp;&nbsp;LA HOD and Admin</option>
					    
					    <option class="department" id="Science Directorate" value="Science Directorate">Science directorate</option>
					</select>
				</div>
				<div class="small-12 columns">
					<select id="collection" class="mb-0">
						<option value="All" selected="selected">Collections</option>
						<%-- Collection: FakeFilter_Research --%>
						<option class="collection" id="Research" value="Research">FakeFilter F:Research</option>
						<option class="group" id="Purvis Lab" value="Purvis Lab" data-collection="Research" data-group="Purvis Lab">&nbsp;&nbsp;&nbsp;&nbsp;FakeFilter F:Research, G:Purvis Lab</option> <%--Botany / Algae --%>
					
						<%-- Collection: FakeFilter_Collections --%> %>
						<option class="collection" id="Collections" value="Collections">FakeFilter F:Collections</option>
						<option class="group" id="Curator" value="Curator" data-collection="Collections" data-group="Curator">&nbsp;&nbsp;&nbsp;&nbsp;FakeFilter F:Collections, G:Curator</option> <%--Botany / Algae --%>
	
					    <%-- Collection: Botany --%>
					    <option class="collection" id="Botany" value="Botany">Botany collections</option>
					    <option class="group" id="Algae" value="Algae" data-collection="Botany" data-group="Algae">&nbsp;&nbsp;&nbsp;&nbsp;Algae collections</option>
					    <option class="group" id="Diatoms" value="Diatoms" data-collection="Botany" data-group="Diatoms">&nbsp;&nbsp;&nbsp;&nbsp;Diatoms collections</option>
					    <option class="group" id="Lichens" value="Lichens" data-collection="Botany" data-group="Lichens">&nbsp;&nbsp;&nbsp;&nbsp;Lichens collections</option>
					    <option class="group" id="Bryophytes" value="Bryophytes" data-collection="Botany" data-group="Bryophytes">&nbsp;&nbsp;&nbsp;&nbsp;Bryophytes collections</option>
					    <option class="group" id="Ferns" value="Ferns" data-collection="Botany" data-group="Ferns">&nbsp;&nbsp;&nbsp;&nbsp;Ferns collections</option>
					    <option class="group" id="British and Irish Herbarium" value="British and Irish Herbarium" data-collection="Botany" data-group="British and Irish Herbarium">&nbsp;&nbsp;&nbsp;&nbsp;British and Irish Herbarium collections</option>
					    <option class="group" id="Historical collections" value="Historical collections" data-collection="Botany" data-group="Historical collections">&nbsp;&nbsp;&nbsp;&nbsp;Historical collections</option>
					
					    <%-- Collection: Entomology --%>
					    <option class="collection" id="Entomology" value="Entomology">Entomology collections</option>
					    <option class="group" id="Hymenoptera" value="Hymenoptera" data-collection="Entomology" data-group="Hymenoptera">&nbsp;&nbsp;&nbsp;&nbsp;Hymenoptera collections</option>
					    <option class="group" id="Coleoptera" value="Coleoptera" data-collection="Entomology" data-group="Coleoptera">&nbsp;&nbsp;&nbsp;&nbsp;Coleoptera collections</option>
					    <option class="group" id="Lepidoptera" value="Lepidoptera" data-collection="Entomology" data-group="Lepidoptera">&nbsp;&nbsp;&nbsp;&nbsp;Lepidoptera collections</option>
					    <option class="group" id="Siphonaptera" value="Siphonaptera" data-collection="Entomology" data-group="Siphonaptera">&nbsp;&nbsp;&nbsp;&nbsp;Siphonaptera collections</option>
					    <option class="group" id="Diptera" value="Diptera" data-collection="Entomology" data-group="Diptera">&nbsp;&nbsp;&nbsp;&nbsp;Diptera collections</option>
					    <option class="group" id="Hemiptera" value="Hemiptera" data-collection="Entomology" data-group="Hemiptera">&nbsp;&nbsp;&nbsp;&nbsp;Hemiptera collections</option>
					    <option class="group" id="Phthiraptera, Thysanoptera, and Psocoptera" value="Phthiraptera, Thysanoptera, and Psocoptera" data-collection="Entomology" data-group="Phthiraptera, Thysanoptera, and Psocoptera">&nbsp;&nbsp;&nbsp;&nbsp;Phthiraptera, Thysanoptera, and Psocoptera collections</option>
					    <option class="group" id="Odonata, Neuroptera and associated collections" value="Odonata, Neuroptera and associated collections" data-collection="Entomology" data-group="Odonata, Neuroptera and associated collections">&nbsp;&nbsp;&nbsp;&nbsp;Odonata, Neuroptera and associated collections</option>
					    <option class="group" id="Apterygota" value="Apterygota" data-collection="Entomology" data-group="Apterygota">&nbsp;&nbsp;&nbsp;&nbsp;Apterygota collections</option>
					    <option class="group" id="Arachnida" value="Arachnida" data-collection="Entomology" data-group="Arachnida">&nbsp;&nbsp;&nbsp;&nbsp;Arachnida collections</option>
					    <option class="group" id="Myriapoda" value="Myriapoda" data-collection="Entomology" data-group="Myriapoda">&nbsp;&nbsp;&nbsp;&nbsp;Myriapoda collections</option>
					    <option class="group" id="Onychophora" value="Onychophora" data-collection="Entomology" data-group="Onychophora">&nbsp;&nbsp;&nbsp;&nbsp;Onychophora collections</option>
					    <option class="group" id="Tardigrada" value="Tardigrada" data-collection="Entomology" data-group="Tardigrada">&nbsp;&nbsp;&nbsp;&nbsp;Tardigrada collections</option>
					    <option class="group" id="Historical collections" value="Historical collections" data-collection="Entomology" data-group="Historical collections">&nbsp;&nbsp;&nbsp;&nbsp;Historical collections</option>
					
					    <%-- Collection: Zoology --%>
					    <option class="collection" id="Zoology" value="Zoology">Zoology collections</option>
					    <option class="group" id="Invertebrate" value="Invertebrate" data-collection="Zoology" data-group="Invertebrate">&nbsp;&nbsp;&nbsp;&nbsp;Invertebrate collections</option>
					    <option class="group" id="Vertebrate" value="Vertebrate" data-collection="Zoology" data-group="Vertebrate">&nbsp;&nbsp;&nbsp;&nbsp;Vertebrate collections</option>
					    <option class="group" id="Bird" value="Bird" data-collection="Zoology" data-group="Bird">&nbsp;&nbsp;&nbsp;&nbsp;Bird collections</option>
					    <option class="group" id="Fish" value="Fish" data-collection="Zoology" data-group="Fish">&nbsp;&nbsp;&nbsp;&nbsp;Fish collections</option>
					    <option class="group" id="Amphbians" value="Amphbians" data-collection="Zoology" data-group="Amphbians">&nbsp;&nbsp;&nbsp;&nbsp;Amphbians collections</option>
					    <option class="group" id="Reptiles" value="Reptiles" data-collection="Zoology" data-group="Reptiles">&nbsp;&nbsp;&nbsp;&nbsp;Reptiles collections</option>
					    <option class="group" id="Mammals" value="Mammals" data-collection="Zoology" data-group="Mammals">&nbsp;&nbsp;&nbsp;&nbsp;Mammals collections</option>
					
					    <%-- Collection: Palaeontology --%>
					    <option class="collection" id="Palaeontology" value="Palaeontology">Palaeontology collections</option>
					    <option class="group" id="Anthropology" value="Anthropology" data-collection="Palaeontology" data-group="Anthropology">&nbsp;&nbsp;&nbsp;&nbsp;Anthropology collections</option>
					    <option class="group" id="Micropalaeontology" value="Micropalaeontology" data-collection="Palaeontology" data-group="Micropalaeontology">&nbsp;&nbsp;&nbsp;&nbsp;Micropalaeontology collections</option>
					    <option class="group" id="Fossil invertebrate" value="Fossil invertebrate" data-collection="Palaeontology" data-group="Fossil invertebrate">&nbsp;&nbsp;&nbsp;&nbsp;Fossil invertebrate collections</option>
					    <option class="group" id="Fossil vertebrate" value="Fossil vertebrate" data-collection="Palaeontology" data-group="Fossil vertebrate">&nbsp;&nbsp;&nbsp;&nbsp;Fossil vertebrate collections</option>
					    <option class="group" id="Palaeobotany" value="Palaeobotany" data-collection="Palaeontology" data-group="Palaeobotany">&nbsp;&nbsp;&nbsp;&nbsp;Palaeobotany collections</option>
					
					    <%-- Collection: Mineralogy --%>
					    <option class="collection" id="Mineralogy" value="Mineralogy">Mineralogy collections</option>
					    <option class="group" id="Meteorite" value="Meteorite" data-collection="Mineralogy" data-group="Meteorite">&nbsp;&nbsp;&nbsp;&nbsp;Meteorite collections</option>
					    <option class="group" id="Mineral" value="Mineral" data-collection="Mineralogy" data-group="Mineral">&nbsp;&nbsp;&nbsp;&nbsp;Mineral collections</option>
					    <option class="group" id="Gemstone" value="Gemstone" data-collection="Mineralogy" data-group="Gemstone">&nbsp;&nbsp;&nbsp;&nbsp;Gemstone collections</option>
					    <option class="group" id="Ocean bottom deposit"  value="Ocean bottom deposit" data-collection="Mineralogy" data-group="Ocean bottom deposit">&nbsp;&nbsp;&nbsp;&nbsp;Ocean bottom deposit collections</option>
					    <option class="group" id="Ores" value="Ores" data-collection="Mineralogy" data-group="Ores">&nbsp;&nbsp;&nbsp;&nbsp;Ores collections</option>
					    <option class="group" id="Petrology" value="Petrology" data-collection="Mineralogy" data-group="Petrology">&nbsp;&nbsp;&nbsp;&nbsp;Petrology collections</option>
					
					    <%-- Collection: British --%>
					    <option class="collection" id="British" value="British">British collections</option>
					
					    <%-- Collection: Molecular --%>
					    <option class="collection" id="Molecular" value="Molecular">Molecular collections</option>
					</select>
				</div>
			</li>
			<li>
				<div class="small-12 medium-12 large-12 columns directory-search-button--container">
					<div class="small-4 columns">
						<i class="ico svg-ico directory-search-button--icon" data-svg-src="/etc/designs/nhmwww/img/svg-icons/icon_l_service_staff.svg" alt="Search Directory" data-alt="Search Directory" data-stroke-width="2" data-fallback="/etc/designs/nhmwww/img/icons/icon_l_service_staff-suport-us.png" data-base-color="#FFFFFF" data-hover-color="#FFFFFF" data-hover-state="no"></i>
					</div>
					<div class="small-5 columns">
						<input id="search" type="button" value="Search Directory" name="submit" class="directory-search-button--text"/>
					</div>
					<div class="small-3 columns">
						<i class="ico svg-ico directory-search-button--arrow" data-svg-src="/etc/designs/nhmwww/img/svg-icons/icon_l_general_arrow_r.svg" alt="Search Directory" data-alt="Search Directory" data-stroke-width="4" data-fallback="/etc/designs/nhmwww/img/icons/icon_l_general_arrow_r-support-us.png" data-base-color="#FFFFFF" data-hover-color="#FFFFFF" data-hover-state="no"></i>
					</div>
				</div>
			</li>
		</ul>	
	</div>
	
	<div class="row profiles_row">
		<div class="large-3 medium-4 columns large-left-section">
			<div id="image" class="large-5 medium-6 columns profiles_table profile-content table_header hide-for-small-only">
				<h5>Image</h5>
			</div>
			<div id="name" class="large-7 medium-6 columns profiles_table profile-content table_header hide-for-small-only">
				<a href="#"><h5>Name </h5> <%-- &and; &or; --%>
			</div>
		</div>
		<div id="job" class="large-2 medium-2 columns large-left-section profiles_table profile-content table_header hide-for-small-only">
			<a href="#"><h5>Job title</h5></a>
		</div>
		<div id="departAndDiv" class="large-3 medium-2 columns large-left-section profiles_table profile-content table_header hide-for-small-only">
			<a href="#"><h5>Department and division</h5></a>
		</div>
		<div id="specialisms" class="large-4 medium-4 columns profiles_table profile-content table_header hide-for-small-only">
			<a href="#"><h5>Specialisms</h5></a>
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
