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
				<legend class="directory-search--label">Search by name</legend>
				<div class="small-12 medium-12 large-12 columns">
					<div class="search-second-name">
						<input type="text" id="surnameInput" name="Surname" placeholder='Surname' />
					</div>
				</div>
				<div class="small-12 medium-12 large-12 columns">
					<div class="search-first-name">
						<input type="text" id="firstNameInput" name="First Name" placeholder='First name' />
					</div>
				</div>
			</li>
			<li>
				<div class="small-12 medium-12 large-12 columns">
					<legend class="directory-search--label">Search by keywords or department</legend>
				</div>
				<div class="small-12 medium-12 large-12 columns">
					<div class="search-keywords">
						<input type="text" id="keywordsInput" name="Keywords" placeholder='Your keywords and specialisms' />
					</div>
				</div>
				<div class="small-12 medium-12 large-12 columns">
					<select id="division">
						<option value="All" selected="selected">Department and Division</option>
						<%-- Department: Life Sciences --%>
						<option class="department" id="Life Sciences" value="Life Sciences">Life sciences</option>
						<option class="division" id="Diversity and Informatics" value="Genomics and Microbial diversity" data-department="Life Sciences" data-division="Diversity and Informatics">&nbsp;&nbsp;&nbsp;&nbsp;Diversity and Informatics</option>
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
						

						<%-- Department: Core Research Laboratories --%>
						<option class="department" id="Core Research Laboratories" value="Core Research Laboratories">Core Research Laboratories</option>
						<option class="division" id="Conservation Centre" value="Conservation Centre" data-department="Core Research Laboratories" data-division="Conservation Centre">&nbsp;&nbsp;&nbsp;&nbsp;Conservation Centre</option>
						<option class="division" id="Imaging and Analysis Centre" value="Imaging and Analysis Centre" data-department="Core Research Laboratories" data-division="Imaging and Analysis Centre">&nbsp;&nbsp;&nbsp;&nbsp;Imaging and Analysis Centre</option>
						<option class="division" id="Molecular Biology Laboratories" value="Molecular Biology Laboratories" data-department="Core Research Laboratories" data-division="Molecular Biology Laboratories">&nbsp;&nbsp;&nbsp;&nbsp;Molecular Biology Laboratories</option>
						<option class="division" id="Digitisation Centre" value="Digitisation Centre" data-department="Core Research Laboratories" data-division="Digitisation centre">&nbsp;&nbsp;&nbsp;&nbsp;Digitisation Centre</option>
						
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
				<div class="small-12 medium-12 large-12 columns">
					<select id="collection" class="mb-0">
						<option value="All" selected="selected">Collections</option>
						<%-- Collection: FakeFilter_Collections --%>
						<option class="collection" id="Collections" value="Collections">FakeFilter F:Collections</option>
						<option class="group" id="Curator" value="Curator" data-group="Curator">&nbsp;&nbsp;&nbsp;&nbsp;FakeFilter G:Curator</option> <%-- Botany / Algae --%>
						<option class="group" id="Bird-Vertebrates" value="Bird-Vertebrates" data-group="Vertebrates, Birds">&nbsp;&nbsp;&nbsp;&nbsp;FakeFilter G:Vertebrates, Birds</option> <%-- Vertebrates, Birds --%>
						<option class="group" id="Insects-Hymenoptera" value="Insects-Hymenoptera" data-group="Insects, Hymenoptera">&nbsp;&nbsp;&nbsp;&nbsp;FakeFilter G:Insects, Hymenoptera</option> <%--Insects, Hymenoptera--%>
	
					    <%-- Collection: Botany --%>
					    <option class="collection" id="Botany" value="Botany">Botany collections</option>
					    <option class="group" id="Algae" value="Algae" data-group="Algae">&nbsp;&nbsp;&nbsp;&nbsp;Algae collections</option>
					    <option class="group" id="Diatoms" value="Diatoms" data-group="Diatoms">&nbsp;&nbsp;&nbsp;&nbsp;Diatoms collections</option>
					    <option class="group" id="Lichens" value="Lichens" data-group="Lichens">&nbsp;&nbsp;&nbsp;&nbsp;Lichens collections</option>
					    <option class="group" id="Bryophytes" value="Bryophytes" data-group="Bryophytes">&nbsp;&nbsp;&nbsp;&nbsp;Bryophytes collections</option>
					    <option class="group" id="Ferns" value="Ferns" data-group="Ferns">&nbsp;&nbsp;&nbsp;&nbsp;Ferns collections</option>
					    <option class="group" id="British and Irish Herbarium" value="British and Irish Herbarium" data-group="British and Irish Herbarium">&nbsp;&nbsp;&nbsp;&nbsp;British and Irish Herbarium collections</option>
					    <option class="group" id="Historical collections" value="Historical collections" data-group="Historical collections">&nbsp;&nbsp;&nbsp;&nbsp;Historical collections</option>
					
					    <%-- Collection: Entomology --%>
					    <option class="collection" id="Entomology" value="Entomology">Entomology collections</option>
					    <option class="group" id="Hymenoptera" value="Hymenoptera" data-group="Hymenoptera">&nbsp;&nbsp;&nbsp;&nbsp;Hymenoptera collections</option>
					    <option class="group" id="Coleoptera" value="Coleoptera" data-group="Coleoptera">&nbsp;&nbsp;&nbsp;&nbsp;Coleoptera collections</option>
					    <option class="group" id="Lepidoptera" value="Lepidoptera" data-group="Lepidoptera">&nbsp;&nbsp;&nbsp;&nbsp;Lepidoptera collections</option>
					    <option class="group" id="Siphonaptera" value="Siphonaptera" data-group="Siphonaptera">&nbsp;&nbsp;&nbsp;&nbsp;Siphonaptera collections</option>
					    <option class="group" id="Diptera" value="Diptera" data-group="Diptera">&nbsp;&nbsp;&nbsp;&nbsp;Diptera collections</option>
					    <option class="group" id="Hemiptera" value="Hemiptera" data-group="Hemiptera">&nbsp;&nbsp;&nbsp;&nbsp;Hemiptera collections</option>
					    <option class="group" id="Phthiraptera, Thysanoptera and Psocoptera" value="Phthiraptera, Thysanoptera and Psocoptera" data-group="Phthiraptera, Thysanoptera and Psocoptera">&nbsp;&nbsp;&nbsp;&nbsp;Phthiraptera, Thysanoptera, and Psocoptera collections</option>
					    <option class="group" id="Odonata, Neuroptera and associated collections" value="Odonata, Neuroptera and associated collections" data-group="Odonata, Neuroptera and associated collections">&nbsp;&nbsp;&nbsp;&nbsp;Odonata, Neuroptera and associated collections</option>
					    <option class="group" id="Apterygota" value="Apterygota" data-group="Apterygota">&nbsp;&nbsp;&nbsp;&nbsp;Apterygota collections</option>
					    <option class="group" id="Arachnida" value="Arachnida" data-group="Arachnida">&nbsp;&nbsp;&nbsp;&nbsp;Arachnida collections</option>
					    <option class="group" id="Myriapoda" value="Myriapoda" data-group="Myriapoda">&nbsp;&nbsp;&nbsp;&nbsp;Myriapoda collections</option>
					    <option class="group" id="Onychophora" value="Onychophora" data-group="Onychophora">&nbsp;&nbsp;&nbsp;&nbsp;Onychophora collections</option>
					    <option class="group" id="Tardigrada" value="Tardigrada" data-group="Tardigrada">&nbsp;&nbsp;&nbsp;&nbsp;Tardigrada collections</option>
					    <option class="group" id="Historical collections" value="Historical collections" data-group="Historical collections">&nbsp;&nbsp;&nbsp;&nbsp;Historical collections</option>
					
					    <%-- Collection: Zoology --%>
					    <option class="collection" id="Zoology" value="Zoology">Zoology collections</option>
					    <option class="group" id="Invertebrates" value="Invertebrates" data-group="Invertebrates">&nbsp;&nbsp;&nbsp;&nbsp;Invertebrates collections</option>
					    <option class="group" id="Vertebrates" value="Vertebrates" data-group="Vertebrates">&nbsp;&nbsp;&nbsp;&nbsp;Vertebrates collections</option>
					    <option class="group" id="Birds" value="Birds" data-group="Birds">&nbsp;&nbsp;&nbsp;&nbsp;Birds collections</option>
					    <option class="group" id="Fish" value="Fish" data-group="Fish">&nbsp;&nbsp;&nbsp;&nbsp;Fishes collections</option>
					    <option class="group" id="Amphibians" value="Amphibians" data-group="Amphibians">&nbsp;&nbsp;&nbsp;&nbsp;Amphbians collections</option>
					    <option class="group" id="Reptiles" value="Reptiles" data-group="Reptiles">&nbsp;&nbsp;&nbsp;&nbsp;Reptiles collections</option>
					    <option class="group" id="Mammals" value="Mammals" data-group="Mammals">&nbsp;&nbsp;&nbsp;&nbsp;Mammals collections</option>
					
					    <%-- Collection: Palaeontology --%>
					    <option class="collection" id="Palaeontology" value="Palaeontology">Palaeontology collections</option>
					    <option class="group" id="Anthropology" value="Anthropology" data-group="Anthropology">&nbsp;&nbsp;&nbsp;&nbsp;Anthropology collections</option>
					    <option class="group" id="Micropalaeontology" value="Micropalaeontology" data-group="Micropalaeontology">&nbsp;&nbsp;&nbsp;&nbsp;Micropalaeontology collections</option>
					    <option class="group" id="Fossil invertebrate" value="Fossil invertebrate" data-group="Fossil invertebrate">&nbsp;&nbsp;&nbsp;&nbsp;Fossil invertebrate collections</option>
					    <option class="group" id="Fossil vertebrate" value="Fossil vertebrate" data-group="Fossil vertebrate">&nbsp;&nbsp;&nbsp;&nbsp;Fossil vertebrate collections</option>
					    <option class="group" id="Palaeobotany" value="Palaeobotany" data-group="Palaeobotany">&nbsp;&nbsp;&nbsp;&nbsp;Palaeobotany collections</option>
					
					    <%-- Collection: Mineralogy --%>
					    <option class="collection" id="Mineralogy" value="Mineralogy">Mineralogy collections</option>
					    <option class="group" id="Meteorite" value="Meteorite" data-group="Meteorite">&nbsp;&nbsp;&nbsp;&nbsp;Meteorite collections</option>
					    <option class="group" id="Mineral" value="Mineral" data-group="Mineral">&nbsp;&nbsp;&nbsp;&nbsp;Mineral collections</option>
					    <option class="group" id="Gemstone" value="Gemstone" data-group="Gemstone">&nbsp;&nbsp;&nbsp;&nbsp;Gemstone collections</option>
					    <option class="group" id="Ocean bottom deposit"  value="Ocean bottom deposit" data-group="Ocean bottom deposit">&nbsp;&nbsp;&nbsp;&nbsp;Ocean bottom deposit collections</option>
					    <option class="group" id="Ores" value="Ores" data-group="Ores">&nbsp;&nbsp;&nbsp;&nbsp;Ores collections</option>
					    <optin class="group" id="Petrology" value="Petrology" data-group="Petrology">&nbsp;&nbsp;&nbsp;&nbsp;Petrology collections</option>
					
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
	</div><!--  NEW -->


	<div class="directory-search-results--table">
		<div class="row directory-search-results--row hide-for-small-only">
			<div id="image" class="small-6 medium-2 large-2 columns directory-search-results--row-header">
				Image
			</div>
			<div class="small-6 medium-10 large-10 columns">
				<div id="name" class="small-12 medium-2 large-2 columns directory-search-results--row-header directory-search-results--sort-results-down js--profile-content">
					Name
				</div>
				<div id="job" class="small-12 medium-3 large-3 columns directory-search-results--row-header directory-search-results--sort-results-down js--profile-content">
					Job Title
				</div>
				<div id="departAndDiv" class="small-12 medium-4 large-4 columns directory-search-results--row-header directory-search-results--sort-results-down js--profile-content">
					Department and Division
				</div>
				<div id="specialisms" class="small-12 medium-3 large-3 columns directory-search-results--row-header js--profile-content">
					Specialisms
				</div>
			</div>
		</div>
		

		<div id="peopleList" class="row">
			
			
				<c:forEach var="profile" items="${profileList}">
					<div class="row js--directory-search-results--row">
					<div firstname="${fn:escapeXml(profile.firstName)}"
						secondname="${fn:escapeXml(profile.lastName)}"
						activity="${fn:escapeXml(profile.job)}"
						department="${fn:escapeXml(profile.department)}"
						division="${fn:escapeXml(profile.division)}"
						specialisms="${fn:escapeXml(profile.specialisms)}"
						collection="${fn:escapeXml(profile.collection)}"
						group="${fn:escapeXml(profile.collectionGroup)}"
						style="display:none;">
							<div class="small-4 medium-2 large-2 columns directory-search-results--row-content js--profile-content">
								<% final WCMMode beforeMode = WCMMode.fromRequest(slingRequest);
									WCMMode.PREVIEW.toRequest(slingRequest);%>
								<cq:include path="${profile.imagePath}" resourceType="nhmwww/components/functional/foundation5image" />
									<% beforeMode.toRequest(slingRequest); %>
							</div>
							<div class="small-8 medium-10 large-10 columns">
								<div class="small-12 medium-2 columns directory-search-results--row-content js--profile-content">
									<a href="${fn:escapeXml(profile.url)}" class="directory-search-results--row-content--name"> 
										<c:out value="${profile.lastName}" /> 
										<c:out value=", " /> 
										<c:out value="${profile.firstName}" />
									</a>
								</div>
								<div class="small-12 medium-3 large-3 columns directory-search-results--row-content js--profile-content">
									<span class="directory-search-results--row-content--job-title">
										<c:out value="${profile.job}" /></span>
								</div>
								<div class="small-12 medium-4 large-4 columns directory-search-results--row-content js--profile-content">
									<span class="directory-search-results--row-content--label show-for-small-only">Department:</span><c:out value="${profile.department}"/>
									<br />
									<span class="directory-search-results--row-content--label show-for-small-only">Division:</span><c:out value="${profile.division}" />
								</div>
								<div class="small-12 medium-3 large-3 columns directory-search-results--row-content js--profile-content">
									<c:if test="${not empty profile.specialisms}">
										<span class="directory-search-results--row-content--label show-for-small-only">Specialisms: </span>
									</c:if>	
									<c:choose>
										<c:when test="${fn:length(profile.specialisms) > 120}">
											<c:out value="${fn:substring(profile.specialisms, 0, 120)}"/>&hellip;
										</c:when>
										<c:otherwise>
											<c:out value="${profile.specialisms}"/>
										</c:otherwise>							
									</c:choose>
								</div>
							</div>
						</div>
					</div>
			</c:forEach>
		</div>
	</div>

<!--  NEW -->

	<div class="row more-results">
		<a href="#" id="show-more">
			<h5 class="more-results-text more-results-text__directory-search-results">Show more</h5>
		</a>	
	</div>
</div>
