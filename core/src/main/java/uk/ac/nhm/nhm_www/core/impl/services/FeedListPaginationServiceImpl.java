package uk.ac.nhm.nhm_www.core.impl.services;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageFilter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;

import uk.ac.nhm.nhm_www.core.model.FeedListElement;
import uk.ac.nhm.nhm_www.core.model.PressReleaseFeedListElement;
import uk.ac.nhm.nhm_www.core.services.FeedListPaginationService;

@Component(label = "Natural History Museum Pagination", description = "Natural History Museum Pagination", metatype = true, immediate = true)
@Service(value = FeedListPaginationService.class)
@Properties(value = {
	@Property(name = "service.pid", value = "uk.ac.nhm.nhm_www.core.delivery.service.FeedListPaginationService", propertyPrivate = true),
	@Property(name = "service.description", value = "Natural History Museum Pagination properties", propertyPrivate = true),
	@Property(name = "cacheExpired", intValue = 1, description = "Minutes Cache to expire"),
	@Property(name = "concurrencyLevel" ,intValue = 16, description="Cache ConcurrentHashMap: estimated number of concurrently updating threads. The implementation performs internal sizing to try to accommodate this many threads."),
	@Property(name = "mappedPath", value="", description="Repository path mapped, this path is going to be hide on the final link to the publication")
})
public class FeedListPaginationServiceImpl implements FeedListPaginationService {

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    @Override
    public List<Object> getJCRItems( final HttpServletRequest request, final String path, final Integer pageNumber) throws LoginException {
	final Map<String, Object> param = new HashMap<String, Object>();
	final List<Object> objectsFound = new ArrayList<Object>();
	param.put(ResourceResolverFactory.SUBSERVICE, "searchService");
	final ResourceResolver resolver = this.resourceResolverFactory.getServiceResourceResolver(param);
	final Resource res = resolver.getResource(path);
	final Page rootPage = res.adaptTo(Page.class);
	final Iterator<Page> itrChildren = rootPage.listChildren(new PageFilter(request));
	while (itrChildren.hasNext()) {
	    objectsFound.add(itrChildren.next());
	}


	return objectsFound;
    }

    @Override
    public JSONObject getJSON(final List<Object> objects, final Integer pageNumber, final Integer pageSize) {
	final JSONObject jsonObject = new JSONObject();

	//final int listSize = objects.size();

	//final int numberOfPages = listSize / pageSize;
	final int indexFrom = (pageSize*pageNumber) - (pageSize - 1);
	final int indexTo;
	if (objects.size() - indexFrom < pageSize - 1) {
	    indexTo = indexFrom + (objects.size() - indexFrom) - 1;
	} else {
	    indexTo = indexFrom + pageSize - 1;
	}

	final JSONArray jsonArray = new JSONArray();
	try {
	    final int pages = objects.size() / pageSize + 1;
	    jsonObject.put("pages", pages);
	    for(int i=indexFrom-1; i < indexTo; i++) {
		if (objects.get(i) instanceof PressReleaseFeedListElement) {
		    final PressReleaseFeedListElement listElement = (PressReleaseFeedListElement) objects.get(i);
		    jsonArray.put(addPressReleaseElement(listElement));
		} else {
		    final FeedListElement listElement = (FeedListElement) objects.get(i);
		    jsonArray.put(addElement(listElement));
		}
	    }
	    jsonObject.put("pageJson", jsonArray);
	} catch (final JSONException e) {
	    e.printStackTrace();
	}
	return jsonObject;
    }

    private JSONObject addPressReleaseElement(final PressReleaseFeedListElement listElement) throws JSONException {
	final JSONObject itemToAdd = new JSONObject();
	itemToAdd.put("title", listElement.getTitle());
	itemToAdd.put("intro", listElement.getIntro());
	itemToAdd.put("imagePath", listElement.getImagePath());
	final DateFormat df = new SimpleDateFormat("MMMMM d, yyyy");
	itemToAdd.put("date", df.format(listElement.getPressReleaseDate()));
	itemToAdd.put("path", listElement.getPage().getPath());
	return itemToAdd;
    }

    private JSONObject addElement(final FeedListElement listElement) throws JSONException {
	final JSONObject itemToAdd = new JSONObject();
	itemToAdd.put("title", listElement.getTitle());
	itemToAdd.put("intro", listElement.getIntro());
	itemToAdd.put("imagePath", listElement.getImagePath());
	return itemToAdd;
    }


}
