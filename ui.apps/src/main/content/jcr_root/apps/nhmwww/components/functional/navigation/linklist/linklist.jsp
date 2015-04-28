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
	String linkURL = "http://www.google.com";
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

<%= helper.printColumn("firstLinkListItems") %>



Ahead, you can find all the fake columns

<%-- Link List --%>
<nav class="linklist small-block-grid-1 medium-block-grid-2 large-block-grid-3">

	<%-- Column --%>
	<ul class="first-column">
		<%-- link,  --%>
		<li>
			<a href="<%=linkURL %>" data-title="<%=linkTitle %>">
				<h3>First <%=linkTitle%></h3>
			</a>
		</li>
	</ul>
	
	<%-- [Optional] Column --%>
	<ul class="second-column">
		<li>
			<a href="<%=linkURL %>" data-title="<%=linkTitle %>">
				<h3>Second <%=linkTitle%></h3>
			</a>
		</li>
	</ul>
	
	<%-- [Optional] Column --%>
	<ul class="third-column">
		<li>
			<a href="<%=linkURL %>" data-title="<%=linkTitle %>">
				<h3>Third <%=linkTitle%></h3>
			</a>
		</li>
	</ul>
</nav>

























<% %>