<%@page session="false"
	import="org.apache.sling.api.resource.Resource,
            org.apache.sling.api.resource.ResourceUtil,
            org.apache.sling.api.resource.ValueMap,
            org.apache.sling.api.resource.ResourceResolver,
            org.apache.sling.api.resource.ResourceMetadata,
            org.apache.sling.api.wrappers.ValueMapDecorator,
            org.apache.commons.httpclient.HttpClient,
			org.apache.commons.httpclient.methods.GetMethod,
			org.json.JSONArray,
			org.json.JSONObject,
            java.util.List,
            java.util.ArrayList,
            java.util.HashMap,
            java.util.Locale,
            com.adobe.granite.ui.components.ds.DataSource,
            com.adobe.granite.ui.components.ds.EmptyDataSource,
            com.adobe.granite.ui.components.ds.SimpleDataSource,
            com.adobe.granite.ui.components.ds.ValueMapResource,
            com.day.cq.wcm.api.Page,
            com.day.cq.wcm.api.PageManager"%>
<%@taglib prefix="cq" uri="http://www.day.com/taglibs/cq/1.0"%>
<cq:defineObjects />
<%
	String requestUrl = "http://staging.nhm.ac.uk/api/dino-directory-api/initials";
	
	HttpClient httpClient = new HttpClient();
	GetMethod getMethod = new GetMethod(requestUrl);
	
	// set fallback
	request.setAttribute(DataSource.class.getName(), EmptyDataSource.instance());

	ResourceResolver resolver = resource.getResourceResolver();

	//Create an ArrayList to hold data
	List<Resource> initialList = new ArrayList<Resource>();

	try {
		httpClient.executeMethod(getMethod);
		JSONArray initials = new JSONArray(getMethod.getResponseBodyAsString());
		
		for(int i=0; i<initials.length(); i++) {
			ValueMap vm = null;

			//allocate memory to the Map instance
			vm = new ValueMapDecorator(new HashMap<String, Object>());
			
			JSONObject bodyShape = initials.getJSONObject(i);

			vm.put("value", bodyShape.getString("initial"));
			vm.put("text", bodyShape.getString("initial").toUpperCase());
			
			initialList.add(new ValueMapResource(resolver, new ResourceMetadata(), "nt:unstructured", vm));
		}
	} catch (Exception e) {
		e.printStackTrace();
	}

	//Create a DataSource that is used to populate the drop-down control
	DataSource ds = new SimpleDataSource(initialList.iterator());
	request.setAttribute(DataSource.class.getName(), ds);
%>