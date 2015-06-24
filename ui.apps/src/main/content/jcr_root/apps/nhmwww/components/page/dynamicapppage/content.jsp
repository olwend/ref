<%@page session="false"
          import="com.day.cq.wcm.api.Page"%>
<%@include file="/apps/nhmwww/components/global.jsp"%>
<div class="main-section"> 
	<div class="small-12 large-text-left columns">
			<cq:include path="title" resourceType="nhmwww/components/functional/title"/>
	</div>
	<%-- <cq:include path="title" resourceType="nhmwww/components/functional/title" />--%>
	<%--<cq:include path="par" resourceType="foundation/components/parsys" /> --%>
	<div class="row">
		<div class="large-8 medium-8 columns">
			Application AREA
		</div>
		<div class="large-4 medium-4 columns">
			<cq:include path="content-panel" resourceType="foundation/components/parsys" />
		</div>
		
	</div>
</div>