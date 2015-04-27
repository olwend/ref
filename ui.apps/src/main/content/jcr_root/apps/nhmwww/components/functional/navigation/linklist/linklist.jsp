<%@page import="uk.ac.nhm.nhm_www.core.componentHelpers.LinkListHelper"%>
<%@page session="false"%>
<%@include file="/apps/nhmwww/components/global.jsp"%>
<cq:defineObjects />

<% LinkListHelper helper = new LinkListHelper(properties, pageManager, currentPage, request, resourceResolver); %>
<%-- [Optional] Title & HyperLink --%>
<%if (helper.getComponentTitle() != null) {%><h2><%if (helper.getHyperLink() != null) {%><a href="<%=helper.getHyperLink() %>"<%=helper.getNewwindow()%>><%}%><%=helper.getComponentTitle() %><%if (helper.getHyperLink() != null) {%></a><%}%></h2> <%}%>

<%-- [Optional] Description --%>
<%if (helper.hasDescription()) { %> <div> <%= helper.getDescription() %></div> <% } %>

<% 
	//Fake vars
	String url = "http://www.google.com";
	String linkTitle = "This is a Link ";
	Boolean isNewWindow = true; 
	String windowTarget = "";
	if (isNewWindow == true) {
		windowTarget = "_blank";
	}
	else {
		windowTarget = "_self";
	}

%>

<%-- Link List --%>
<nav class="linklist ">

	<%-- Column --%>
	<ul class="first-column">
		<%-- link,  --%>
		<a href="<%=url %>" data-title="<%=linkTitle %>" target="<%=windowTarget %>">
			<li>
				<h3><%=linkTitle%></h3>
			</li>
		</a>
	</ul>
	
	<%-- [Optional] Column --%>
	<ul class="second-column">
		<a href="<%=url %>" data-title="<%=linkTitle %>" target="<%=windowTarget %>">
			<li>
				<h3><%=linkTitle%></h3>
			</li>
		</a>
	</ul>
	
	<%-- [Optional] Column --%>
	<ul class="third-column">
		<a href="<%=url %>" data-title="<%=linkTitle %>" target="<%=windowTarget %>">
			<li>
				<h3><%=linkTitle%></h3>
			</li>
		</a>
	</ul>
</nav>

























<% %>