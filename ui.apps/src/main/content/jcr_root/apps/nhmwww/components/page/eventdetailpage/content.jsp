<%@page session="false"
          import="com.day.cq.wcm.api.Page"%>
<%@include file="/apps/nhmwww/components/global.jsp"%>
<cq:includeClientLib categories="nhmwww.eventdetailpage"/>
<div class="main-section"> 
	<div class="small-12 large-text-left columns">
			<cq:include path="title" resourceType="nhmwww/components/functional/title"/>
	</div>
	<%-- <cq:include path="title" resourceType="nhmwww/components/functional/title" />--%>
	<cq:include path="par" resourceType="foundation/components/parsys" />
</div>
