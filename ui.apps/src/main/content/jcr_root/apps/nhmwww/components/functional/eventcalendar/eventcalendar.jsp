<%@page session="false"
        import="com.day.cq.tagging.Tag,com.day.cq.tagging.TagManager,
                java.util.Iterator,
                java.lang.Exception"%>
<%@include file="/libs/foundation/global.jsp" %>

<% // Creates a json object with elements in the form { "tagId" : "tagTitle" }
   // in order to be used by the front end javascript logic (search-query.js) %>   
<script type="application/javascript">
    var tagsNamespace = "nhmwww";
    var tagsJson = [];
    <% 
    try {
        String tagID = "nhmwww:";
        TagManager tagManager = resourceResolver.adaptTo(TagManager.class);
        Tag tag2 = tagManager.resolve(tagID);
        Iterator<Tag> tagIterator = tag2.listChildren();
        while (tagIterator.hasNext()) {
            Tag newTag = tagIterator.next();
            %>
            // Adds the parent category tab    
            tagsJson.push({"title":"<%=newTag.getTitle()%>", "name":"<%=newTag.getTagID()%>"});
            <% 
            Iterator<Tag> tagIteratorSubtags = newTag.listChildren();
            while (tagIteratorSubtags.hasNext()) {
                Tag newSubTag = tagIteratorSubtags.next();
                %>
                // Adds the subtag inside that category    
                tagsJson.push({"title":"<%=newSubTag.getTitle()%>","name":"<%=newSubTag.getTagID()%>"});
                <%            
            }    
        }
    } catch(Exception e) {
        // Do nothing
    }
    %>
</script>     
<cq:includeClientLib categories="nhmwww.eventcalendarcomponent"/>

<!-- START event calendar component wrapper -->
<div class="row event--calendar--wrapper" data-showmore="<%=properties.get("./showmore","NaN")%>">
	<!-- START event calendar component inner wrapper -->
	<div class="small-12 medium-12 large-12 columns">
		<!-- START keyword field wrapper -->
		<div class="small-12 medium-12 large-4 columns event-calendar--keyword-search--wrapper">
			<div class="small-12 medium-12 large-12 columns">
				<legend class="calendar--search--label">Keyword search</legend>
	            <div class="search-keywords small-10 medium-10 large-10 columns">
	                <input type="text" id="keywordsInput" name="Keywords" placeholder='Your keyword'/>
	            </div>
	            <div class="search-icon small-2 medium-2 large-2 columns">
	                <img src="/etc/designs/nhmwww/img/icons/search-icon.png"/>
	            </div>
			</div>
		</div>
		<!-- END keyword field wrapper -->
		<!-- START filters and date pickers wrapper -->
		<div class="small-12 medium-12 large-8 columns">
			<!--  START filters wrapper -->
			<div class="small-12 medium-6 large-6 columns">
				<div class="small-12 medium-12 large-12 columns">
	                <legend class="calendar--search--label left">Filter by</legend>
	                <legend class="reset--filter right">Reset filter</legend>
	            </div>
	            <div class="small-12 medium-12 large-12 columns calendar--select">
	                <% TagManager tagMgr = resourceResolver.adaptTo(TagManager.class); %>
	                <select id="filterOne">
	                    <c:set var="filterOneLabel" value="<%= properties.get("filterOneLabel" , String.class) %>" />
	                    <option value="none" selected="selected">${filterOneLabel}</option>
	                    <c:set var="filterOneOptions" value="<%= properties.get("filterOne", String[].class) %>" />
					    <c:forEach var="filterOneOption" items="${filterOneOptions}">
	                        <c:set var="filterOneOptionSplit" value="${fn:split(filterOneOption, '/')}"/>
	                        <c:set var="filterOneName" value="${fn:replace(filterOneOptionSplit[1], '-', ' ')}" />
	                        <%
	                            String tagName = (String)pageContext.getAttribute("filterOneOption");
	                            Tag tag = tagMgr.resolve(tagName);
	                        %>
	                        <option value="${filterOneOption}"><%=tag.getTitle()%></option> 
	                    </c:forEach>
					</select>
	            </div>
	            <div class="small-12 medium-12 large-12 columns calendar--select">
	                <select id="filterTwo" class="mb-0">
	                    <c:set var="filterTwoLabel" value="<%= properties.get("filterTwoLabel", String.class) %>" />
	                    <option value="none" selected="selected">${filterTwoLabel}</option>
	                    <c:set var="filterTwoOptions" value="<%= properties.get("filterTwo", String[].class) %>" />
					    <c:forEach var="filterTwoOption" items="${filterTwoOptions}">
	                        <c:set var="filterTwoOptionSplit" value="${fn:split(filterTwoOption, '/')}"/>
	                        <c:set var="filterTwoName" value="${fn:replace(filterTwoOptionSplit[1], '-', ' ')}" />
	                        <%
	                            String tagName = (String)pageContext.getAttribute("filterTwoOption");
	                            Tag tag = tagMgr.resolve(tagName);
	                        %>
	                        <option value="${filterTwoOption}"><%=tag.getTitle()%></option> 
	                    </c:forEach>
					</select> 
	            </div>	            
			</div>
			<!-- END filters wrapper -->
			<!-- START date pickers wrapper -->
			<div class="small-12 medium-6 large-6 columns event-calendar--date-pickers--wrapper">
		        <div class="small-12 medium-12 large-12 columns helper">
	                <legend class="calendar--search--label">Date</legend>
	            </div>
	            <div class="small-6 medium-6 large-6 columns pr-10 date--picker">
	                <div id="fromIcon" class="date--wrapper columns">
	                    <div class="small-12 medium-12 large-12 columns">
	                        <legend class="dates--label">From</legend>
	                    </div>
	                    <div class="small-12 medium-12 large-12 columns">
	                        <div class="small-10 medium-10 large-10 columns calendar--text">
	                            <input readonly="true" type="text" placeholder="___ __/__/____ " id="dateFrom" class="dp">
	                        </div>
	                        <div class="small-2 medium-2 large-2 columns calendar--icon">
	                            <img src="/etc/designs/nhmwww/img/icons/calendar-icon.png"/>
	                        </div>
	                    </div>
	                </div>
	            </div>
	            <div class="small-6 medium-6 large-6 columns date--picker">
	                <div id="toIcon" class="date--wrapper columns">
	                    <div class="small-12 medium-12 large-12 columns">
	                        <legend class="dates--label">To</legend>
	                    </div>
	                   <div class="small-12 medium-12 large-12 columns">
	                        <div class="small-10 medium-10 large-10 columns calendar--text">
	                            <input readonly="true" type="text" placeholder="___ __/__/____ " id="dateTo" class="dp"/>
	                        </div>
	                        <div class="small-2 medium-2 large-2 columns calendar--icon">
	                            <img src="/etc/designs/nhmwww/img/icons/calendar-icon.png"/>
	                        </div>
	                    </div>
	                </div>
	            </div>
            	<c:set var="displayExtra" value="<%= properties.get("extraOptions", Boolean.class) %>" />
            	<c:if test="${displayExtra}">
	                <div class="small-4 medium-4 large-4 columns pr-10">
	                    <div id="today" class="small-12 medium-12 large-12 columns date--button">
	                        <input readonly="true" type="button" value="Today" name="submit"/>
	                    </div>
	                </div>
	                <div class="small-4 medium-4 large-4 columns pr-10">
	                    <div id="sevenDays" class="small-12 medium-12 large-12 columns date--button">
	                        <input readonly="true" type="button" value="Next 7 days" name="submit"/>
	                    </div>
	                </div>
	                <div class="small-4 medium-4 large-4 columns">
	                    <div id="thirtyDays" class="small-12 medium-12 large-12 columns date--button">
	                        <input readonly="true" type="button" value="Next 30 days" name="submit"/>
	                    </div>
	                </div>
            	</c:if>
	      	</div>
			<!-- END date pickers wrapper -->
		</div>
		<!-- END filters and date pickers wrapper -->
	</div>
	<!-- END event calendar component inner wrapper -->
</div>
<!-- END event calendar component wrapper -->