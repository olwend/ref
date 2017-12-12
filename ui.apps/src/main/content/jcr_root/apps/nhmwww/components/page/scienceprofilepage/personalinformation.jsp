<%@include file="/apps/nhmwww/components/global.jsp"%>
<%@page import="uk.ac.nhm.core.model.science.PhoneNumber,
				uk.ac.nhm.core.model.science.EmailAddress,
				uk.ac.nhm.core.model.science.WebSite,
				uk.ac.nhm.core.componentHelpers.ScientistProfileHelper,
				java.util.List,
				java.util.Set"%> 
<%
	final ScientistProfileHelper helper = new ScientistProfileHelper(resource);
	final boolean displayGroupsAndSpecialisms = helper.displayGroupsAndSpecialismsBox(resource, sling);

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
			<div class="
				<% if (displayGroupsAndSpecialisms) { %>
					small-12 medium-5 large-6
				<% } else { %>
					small-12 medium-5 large-4 
				<% } %>
				columns hti--image-wrapper hti-box__light-grey" data-equalizer-watch>
        		<cq:include script="image.jsp" />
        	</div>
    	</div>
		<div class="
				<% if (displayGroupsAndSpecialisms) { %>
					small-12 medium-7 large-6
				<% } else { %>
					small-12 medium-7 large-8
				<% } %>
				columns hti-box hti-box__light-grey" data-equalizer-watch>
			<div class="hti-box--text-wrapper">
				<h2><%= personalInformationHeader %></h2>
				
				<%-- Department --%>
				<span class="science-profiles-detail-page--personal--label">Department:</span> 
					<%= departmentName %> <br>
					
				<%-- Division --%>
				<span class="science-profiles-detail-page--personal--label">Division:</span> 
					<%= division %> <br>
				
				<%-- Email addresses --%>
				<% final List<EmailAddress> emails = helper.getEmails();
				if (emails != null && !emails.isEmpty()) {
					String otherWork = "";
					int count = 0;
					for(EmailAddress e : emails) {
						if(!(e.getType() == null || e.getType().equals(null))) {
							if(e.getType().equals("work")) {
								if(e.getEmailAddress().contains("@nhm.ac.uk")) {%>
									<span class="science-profiles-detail-page--personal--label">Contact:</span> 
									<a href="/about-us/contact-enquiries/forms/emailform.jsp?recip=
									<%=e.getEmailAddress().replace("@nhm.ac.uk", "")%>
									&business_title=
									<% if (helper.getNickName() != null ) { %><%=helper.getNickName().trim()%>+<%=helper.getLastName()%>
					                <% } else { %><%=helper.getFirstName().trim()%>+<%=helper.getLastName()%>
					                <% } %>
									"> email</a> <br>
								<%}
							}
							else {
								otherWork = otherWork + e.getEmailAddress() + ", ";
								count++;
							}
						}
					}
					if(!otherWork.equals("")) {
						if(count > 1) {%>							
							<span class="science-profiles-detail-page--personal--label">Other emails:</span>
						<%}
						else {%>
							<span class="science-profiles-detail-page--personal--label">Other email:</span>
						<%}%>
						<%=otherWork.replaceAll(", $", "") %>
						<br>
					<%}
				}%>				
				
				<%-- Phones --%><% 
				final List<PhoneNumber> phones = helper.getPhones();
				if (phones != null && !phones.isEmpty()) {
					if (phones.size() == 1) { %>
						<span class="science-profiles-detail-page--personal--label">Phone:</span>
						<%= phones.get(0).getPhone() %>
						<br> <% 
					} else { %>
						<div class="phone-numbers">
                            <div class="phone-label columns">
								<strong>Phones:</strong>
                            </div>
                            <div class="phone-number end columns">
								<% for (final PhoneNumber phone : phones) { %>
									<div>
										<%if(!phone.getLabel().equals("mobile")) {%>
											<strong><%= phone.getLabel() %>:</strong>
											<%= phone.getPhone() %>	
										<%} %>
									</div>
									<% } %>
							</div>
						</div>
 				<% 
					} 
				} %>
				
				<%-- Websites --%><%
				if (sites != null) {
					for (final WebSite site : sites) {
						if (site.isValid() && site.isPersonalInformationWebSite()) {
							final String label = site.getLabel();
							final String link  = site.getLink(); %>
				<a href="<%= link %>" target="_blank"><%= label %></a> <br> <%
						}
					}
				} %>
			</div>
		</div>