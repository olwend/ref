<%@page session="false"
        import="org.apache.commons.lang3.StringEscapeUtils,
        com.day.cq.commons.Doctype,
        com.day.cq.commons.DiffInfo,
        com.day.cq.commons.DiffService,
        org.apache.sling.api.resource.ResourceUtil,
        uk.ac.nhm.core.componentHelpers.PressReleaseHelper" %>
<%@include file="/apps/nhmwww/components/global.jsp"%>
<div class="main-section"> 
<%
	PressReleaseHelper helper = new PressReleaseHelper(resource,properties);

    // first calculate the correct title - look for our sources if not set in paragraph
    String title = properties.get(NameConstants.PN_TITLE, String.class);
    if (title == null || title.equals("")) {
        title = resourcePage.getPageTitle();
    }
    if (title == null || title.equals("")) {
        title = resourcePage.getTitle();
    }
    if (title == null || title.equals("")) {
        title = resourcePage.getName();
    }

    // escape title
    title = xssAPI.filterHTML(title);
    
    // check if we need to compute a diff
    String diffOutput = null;
    DiffInfo diffInfo = resource.adaptTo(DiffInfo.class);
    if (diffInfo != null) {
        DiffService diffService = sling.getService(DiffService.class);
        ValueMap map = ResourceUtil.getValueMap(diffInfo.getContent());
        String diffText = map.get(NameConstants.PN_TITLE, "");
        // if the paragraph has no own title, we use the current page title(!)
        if (diffText == null || diffText.equals("")) {
            diffText = title;
        } else {
            diffText = xssAPI.filterHTML(diffText);
        }
        diffOutput = diffInfo.getDiffOutput(diffService, title, diffText, false);
        if (title.equals(diffOutput)) {
            diffOutput = null;
        }
    }
    String defType = currentStyle.get("defaultType", "large");
	%>
	<% if (!helper.getIsComponentInitialised()) {%>
	<div class="row " data-equalizer >
	<div class="large-12 columns">
		<h4>Press release page</h4>
		Required fields (editable in page properties):
		<ul>
			<li>Publish date</li>
			<li>Summary</li>
		</ul>
	</div>
	</div>
	<% } else {%>
	<div class="title">
    <div class="row title-bar">
	    <div class="small-12 columns">
	    <%
	    // use image title if type is "small" but not if diff should be displayed
	    if (properties.get("type", defType).equals("small") && diffOutput == null) {
	        String suffix = currentDesign.equals(resourceDesign) ? "" : "/" + currentDesign.getId();
	        // add mod timestamp to avoid client-side caching of updated images
	        long tstamp = properties.get("jcr:lastModified", properties.get("jcr:created", System.currentTimeMillis()));
	        suffix += "/" + tstamp + ".png";
	        String xs = Doctype.isXHTML(request) ? "/" : "";
	        %><img src="<%= xssAPI.getValidHref(resource.getPath()+".title.png"+suffix) %>" alt="<%= xssAPI.encodeForHTMLAttr(title) %>"<%=xs%>><%
	
	    // large title
	    } else if (diffOutput == null) {
	        %><h1><%= title %></h1><%
	
	    // we need to display the diff output
	    } else {
	        // don't escape diff output
	        %><h1><%= diffOutput %></h1><%
	
	    } %><p><%=helper.getFormattedPublishDate() %></p>
	    </div>
    </div>
    </div>
    <div class="row " data-equalizer >
	<div class="large-12 columns">
    <cq:include path="image" resourceType="nhmwww/components/functional/foundation5image" />
    </div></div>
	<cq:include path="par" resourceType="foundation/components/parsys" />
	<%} %>
</div>