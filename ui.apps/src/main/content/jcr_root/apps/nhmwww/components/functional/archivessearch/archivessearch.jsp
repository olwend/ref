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
          import="uk.ac.nhm.nhm_www.core.componentHelpers.HelperBase"%><%
%>
<cq:defineObjects />
<cq:includeClientLib categories="uk.ac.nhm.archivesprimo-search"/>

<% 	HelperBase helper = new HelperBase(); %>

<div class="search-container">
	<div class="search">
		<ul class="search-options">
			<li  class="selected" id="tab1"><a href="javascript:void(0);" onclick="javascript:setSearchOptions(1);">Everything</a></li>
			<li  id="tab2"><a href="javascript:void(0);" onclick="javascript:setSearchOptions(2);">Print</a></li>
			<li  id="tab3"><a href="javascript:void(0);" onclick="javascript:setSearchOptions(3);">Online</a></li>
		</ul>
		<div id="search-bar">
			<form id="PrimoSearchForm" name="PrimoSearchForm" method="post" action="http://primo-44nhm.hosted.exlibrisgroup.com/primo_library/libweb/action/search.do?fn=search">
				<input name="vl(freeText0)" class="search-style" id="searchq" accesskey="s" placeholder="Enter search terms &ellipsis;" size="60" type="text">
				<input name="mode" value="Basic" type="hidden">
				<input name="tab" value="default_tab" type="hidden">
				<input name="vid" value="44NHM_V1" type="hidden">
				<input value="Search" name="searchopt" id="searchopt" type="button" onclick="javascript:document.getElementById('PrimoSearchForm').submit();">
			</form>
			<div class="advanced">
				<a href="http://primo-44nhm.hosted.exlibrisgroup.com/primo_library/libweb/action/search.do?mode=Advanced&ct=AdvancedSearch&fn=search&vid=44NHM_V1">Advanced search</a>
			</div>
		</div>
	</div>
</div>