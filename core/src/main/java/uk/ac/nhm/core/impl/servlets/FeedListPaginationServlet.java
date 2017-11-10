package uk.ac.nhm.core.impl.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.api.wrappers.ValueMapDecorator;
import org.apache.sling.commons.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.nhm.core.componentHelpers.DatedAndTaggedFeedListHelper;
import uk.ac.nhm.core.componentHelpers.FeedListHelper;
import uk.ac.nhm.core.componentHelpers.PressReleaseFeedListHelper;
import uk.ac.nhm.core.model.TaggedFeedListElement;
import uk.ac.nhm.core.services.FeedListPaginationService;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageFilter;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.PageManagerFactory;

@Component(label = "Natural History Museum Pagination Servlet", description = "Natural History Museum Pagination Servlet", metatype = true, immediate = true)
@Service(value = Servlet.class)
@Properties({
		@Property(name = "sling.servlet.paths", value = { "/bin/list/pagination" }, propertyPrivate = true),
		@Property(name = "sling.servlet.extensions", value = {"json"}),
		@Property(name = "sling.servlet.methods", value = { "GET" }, propertyPrivate = true),
		@Property(name = "service.description", value = "Return Paginated list"),
		@Property(name = "pageNumber", intValue = 0, description = "Default Start Page"),
		@Property(name = "pageSize", intValue = 8, description = "Default page size"),
		@Property(name = "isMultilevel", value = "false",  description = "Default Multilevel"),
		@Property(name = "tags", value = "",  description = "Default Tags")
		
})
public class FeedListPaginationServlet extends SlingAllMethodsServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Reference
	protected PageManagerFactory pageManagerFactory;
	
	@Reference
	private FeedListPaginationService paginationService;

	private static final Logger LOG = LoggerFactory.getLogger(FeedListPaginationServlet.class);
	
	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException, NumberFormatException {
		LOG.error("Reached the FeedListPaginationServlet");
		String rootPath = request.getParameter("rootPath");
		String tags = request.getParameter("tags");
		Integer pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
		Integer pageSize = Integer.parseInt(request.getParameter("pageSize"));
		Boolean isMultilevel = Boolean.parseBoolean(request.getParameter("isMultilevel"));
		String resourceType = request.getParameter("resourceType");
		
		ResourceResolver resourceResolver = request.getResourceResolver();
		
		PageManager pageManager = pageManagerFactory.getPageManager(resourceResolver);
		
		ValueMap properties = new ValueMapDecorator(new HashMap());
		
		FeedListHelper helper;
		List<Object> objects;
		helper = processRequest(rootPath, request, pageManager, properties, resourceResolver, isMultilevel);
		if(isMultilevel) {
			LOG.error("is multilevel 1");
			//helper = processRequest(rootPath, request, pageManager, properties, resourceResolver);
			List<TaggedFeedListElement> results = paginationService.searchCQ(request, rootPath, tags, resourceType);
			objects = new ArrayList<Object>(results);
			helper.addAllListElements(objects);
			LOG.error("results length: " + results.size());
		} 
		objects = helper.getChildrenElements();
		
		
		JSONObject jsonString = paginationService.getJSON(objects, pageNumber, pageSize, resourceResolver, request);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(jsonString.toString());
	}
	
	private FeedListHelper processRequest(String rootPath, HttpServletRequest request, PageManager pageManager, ValueMap properties, ResourceResolver resourceResolver, boolean isMultilevel){
	
		FeedListHelper helper = null;
		Page rootPage = pageManager.getPage(rootPath);
		
		if(isMultilevel) {
			return new DatedAndTaggedFeedListHelper(properties, pageManager, rootPage, request, resourceResolver);
		}
		
		Iterator<Page> childPages = rootPage.listChildren(new PageFilter(request));
		if(childPages.hasNext()) {
			Page child = childPages.next();
			if (child.getProperties().get("cq:template").equals("/apps/nhmwww/templates/pressreleasepage")) { 
				helper = new PressReleaseFeedListHelper(properties, pageManager, rootPage, request, resourceResolver);
			}
			if (child.getProperties().get("cq:template").equals("/apps/nhmwww/templates/taggedcontentpage")) { 
				helper = new PressReleaseFeedListHelper(properties, pageManager, rootPage, request, resourceResolver);
			}
			if (child.getProperties().get("cq:template").equals("/apps/nhmwww/templates/newscontentpage")) { 
				helper = new DatedAndTaggedFeedListHelper(properties, pageManager, rootPage, request, resourceResolver);
			}
			if (child.getProperties().get("cq:template").equals("/apps/nhmwww/templates/sublandingpage")) { 
				helper = new DatedAndTaggedFeedListHelper(properties, pageManager, rootPage, request, resourceResolver);
			}
			
		} else {
			helper = new FeedListHelper(properties, pageManager, rootPage, request, resourceResolver);
		}
		return helper;
	}
}
