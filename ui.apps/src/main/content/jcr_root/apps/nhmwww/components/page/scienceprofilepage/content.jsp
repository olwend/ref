<%@page import="uk.ac.nhm.nhm_www.core.componentHelpers.ScientistProfileHelper"%>
<%@include file="/apps/nhmwww/components/global.jsp"%> 
<% 
	final ScientistProfileHelper helper = new ScientistProfileHelper(resource);
	
	final String title 	   = helper.getTitle();
	String firstName = "";
	if(helper.getNickName() != null && !helper.getNickName().equals("")){
		firstName = helper.getNickName();
	} else {
		firstName = helper.getFirstName();
	}
 
	final String lastName  = helper.getLastName(); 
	final String nickName  = helper.getNickName(); 
%>
<div class="main-section"><!-- CONTENT WRAPPER -->
	<div class="title">
	    <div class="row title">
		    <div class="small-12 columns">  
				<h1><%= title %> <%= firstName %> <%= lastName %></h1>
			</div>
		</div>
	</div>
	<div class="row personal-group" data-equalizer>
		<cq:include script="personalinformation.jsp" />
        <cq:include script="groupandspecialisms.jsp" />
    </div>
    <div class="row"><!-- MAIN ROW -->
        <div class="large-8 medium-8 columns large-left-section our-science"><!-- body row -->
			<cq:include script="statement.jsp"/>
            <div class="large-12 medium-12 columns ">
                <cq:include script="qualifications.jsp" />
				<cq:include script="employmenthistory.jsp" />
            </div>
            <cq:include script="professionalactivities.jsp" />
            <cq:include script="publications.jsp" />
        </div><!-- end body row -->
		<div class="large-4 medium-4 columns"><!-- aside row -->     
	        <cq:include script="highlightedpublications.jsp" />
			<cq:include script="externallinks.jsp" />
			<!-- end aside row -->
		</div> <!-- / END MAIN ROW-->
	</div> <!-- /END CONTENT WRAPPER -->
</div>
<cq:include path="par" resourceType="foundation/components/parsys" />