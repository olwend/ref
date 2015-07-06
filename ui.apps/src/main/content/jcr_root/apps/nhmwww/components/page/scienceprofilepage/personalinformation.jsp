<%@include file="/apps/nhmwww/components/global.jsp"%>
<%@page import="uk.ac.nhm.nhm_www.core.model.science.PhoneNumber,
				uk.ac.nhm.nhm_www.core.model.science.WebSite,
				uk.ac.nhm.nhm_www.core.componentHelpers.ScientistProfileHelper,
				java.util.List,
				java.util.Set"%> 
<%
	final ScientistProfileHelper helper = new ScientistProfileHelper(resource);

	final String function  		= helper.getFunction(); 
	final String position  		= helper.getPosition(); 
	final String departmentName = helper.getDepartmentName();
	final String division  		= helper.getDivision();
	final String email 	   		= helper.getEmail();
	final Set<WebSite> sites	= helper.getWebSites();
	
	final String personalInformationHeader;
	if (position != null && !position.equals("")) {
		personalInformationHeader = position;
	} else {
		personalInformationHeader = "";
	}
%>
		<div class="hti-wrapper">
			<div class="small-12 medium-5 large-6 columns hti--image-wrapper hti-box__light-grey" data-equalizer-watch>
        		<cq:include script="image.jsp" />
        	</div>
    	</div>
		<div class="small-12 medium-7 large-6 columns hti-box hti-box__light-grey" data-equalizer-watch>
			<div class="hti-box--text-wrapper">
				<h2><%= personalInformationHeader %></h2>
				<span class="science-profiles-detail-page--personal--label">Department:</span>
					<%= departmentName %>
					<br>
				<span class="science-profiles-detail-page--personal--label">Division:</span>
					<%= division %>
					<br>
					<% String emailPartial = email.replaceAll("@(.*)",""); %>
		
				<span class="science-profiles-detail-page--personal--label">Contact:</span> <a
						href="/about-us/contact-enquiries/forms/emailform.jsp?recip=<%=emailPartial%>&business_title=<%=helper.getFirstName()%>+<%=helper.getLastName()%>">
						email</a> <br>
					<%
				final List<PhoneNumber> phones = helper.getPhones();
				if (phones != null && !phones.isEmpty()) {
					if (phones.size() == 1) {
			%>
		
				<span class="science-profiles-detail-page--personal--label">Phone:</span>
				<%= phones.get(0).getPhone() %>
				<br>
				<%
				} else {
			%>
				
				<div class="phone-numbers" data-equalizer>
					<div class="phone-label columns" data-equalizer-watch>
						<strong>Phones:</strong>
					</div>
					<div class="phone-number end columns" data-equalizer-watch>
						<%
						for (final PhoneNumber phone : phones) {
			%>
						<div>
							<strong><%= phone.getLabel() %>:</strong>
							<%= phone.getPhone() %></div>
						<% 			
						}
			%>
					</div>
				</div>
				<%
					}
				}
				
				if (sites != null) {
					for (final WebSite site : sites) {
						if (site.isValid() && site.isPersonalInformationWebSite()) {
							final String label = site.getLabel();
							final String link  = site.getLink();
			%>
				<a href="<%= link %>" target="_blank"><%= label %></a> <br>
				<%
						}
					}
				}
			%>
			</div>
		</div>