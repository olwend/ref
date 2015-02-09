<%--
    Copyright 2014 Adobe Systems Incorporated
  
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
  
        http://www.apache.org/licenses/LICENSE-2.0
  
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
--%>
<%@page session="false" import="com.day.cq.wcm.api.Page, com.day.cq.wcm.api.components.IncludeOptions, com.day.cq.wcm.api.components.ComponentContext"%>
<%
	response.setStatus(404);
%>
<%@include file="/apps/nhmwww/components/global.jsp"%>
<%
	// Remove ourselves from the context stack so we start fresh with the redirect page:
	request.setAttribute(ComponentContext.CONTEXT_ATTR_NAME, null);
	String location = "/content/nhmwww/en/home/about-us/page-not-found";
	// Force the redirect page's context to proxy for us:
	IncludeOptions.getOptions(request, true).forceCurrentPage(
			currentPage);
%><cq:include path="<%=location%>"
	resourceType="<%=resourceResolver.getResource(location).getResourceType()%>" />
	