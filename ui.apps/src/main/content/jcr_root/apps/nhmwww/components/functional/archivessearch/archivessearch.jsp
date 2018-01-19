<%--
  ADOBE CONFIDENTIAL
  __________________

   Copyright 2012 Adobe Systems Incorporated
   All Rights Reserved.

  NOTICE:  All information contained herein is, and remains
  the property of Adobe Systems Incorporated and its suppliers,
  if any.  The intellectual and technical concepts contained
  herein are proprietary to Adobe Systems Incorporated and its
  suppliers and are protected by trade secret or copyright law.
  Dissemination of this information or reproduction of this material
  is strictly forbidden unless prior written permission is obtained
  from Adobe Systems Incorporated.
--%>
<%@include file="/apps/nhmwww/components/global.jsp"%>
<%@page session="false"
          import="uk.ac.nhm.core.componentHelpers.HelperBase"%><%
%>
<cq:defineObjects />
<cq:includeClientLib categories="uk.ac.nhm.archivessearch"/>

<%  HelperBase helper = new HelperBase(); %>

<div class="primo-search-container">
	<div class="primo-search-title">
		<p>Search our collections</p>
	</div>
	<div class="primo-search">
		<div id="primo-search-bar">
			<form id="PrimoSearchForm" name="PrimoSearchForm" method="get" target="_self" action="http://primo-44nhm.hosted.exlibrisgroup.com/primo-explore/search" enctype="application/x-www-form-urlencoded; charset=utf-8" onsubmit="searchPrimo()">
				<!-- Customizable Parameters -->
				<input type="hidden" name="institution" value="44NHM_V1" >
				<input type="hidden" name="vid" value="44NHM_V1">
				<input type="hidden" name="tab" value="default_tab">
				<input type="hidden" name="search_scope" value="default_scope">
				<input type="hidden" name="mode" value="Basic">
				<!-- Fixed parameters -->
				<input type="hidden" name="displayMode" value="full">
				<input type="hidden" name="bulkSize" value="<RECORDS PER PAGE, e.g. 10>">
				<input type="hidden" name="highlight" value="true">
				<input type="hidden" name="dum" value="true">
				<input type="hidden" name="query" id="primoQuery">
				<input type="hidden" name="displayField" value="all">
				<!-- Enable this if "Expand My Results" is enabled by default in Views Wizard -->
				<input type="hidden" name="pcAvailabiltyMode" value="true">
				<input type="text" id="primoQueryTemp" class="primo-search-style" value="" placeholder="Search our collections">
				<!-- Search Button -->
				<input id="go" title="Search" onclick="searchPrimo()" type="button" value="Search" alt="Search">
			</form>
		</div>
	</div>
</div>