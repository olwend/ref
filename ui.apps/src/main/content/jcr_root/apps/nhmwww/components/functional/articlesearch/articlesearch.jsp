<%@page session="false"%>
<%@ page import="uk.ac.nhm.nhm_www.core.componentHelpers.ArticleSearchHelper" %>
<%@ page import="com.day.cq.search.result.SearchResult" %>

<%@ page import="java.util.List" %>
<%@ page import="com.day.cq.search.result.Hit" %>

<%@include file="/libs/foundation/global.jsp" %>

<% ArticleSearchHelper helper = new ArticleSearchHelper(properties, resource, request, resourceResolver); %>

Article search component

<% SearchResult result = helper.getResult();
List<Hit> hits = result.getHits(); %>
<br><br>

<% for(int i=0; i<hits.size(); i++) { 
    Hit hit = hits.get(i);%>

<%= hit.getPath() %><br>
<% } %>