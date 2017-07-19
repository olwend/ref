package uk.ac.nhm.nhm_www.core.impl.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.jcr.api.SlingRepository;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.commons.JS;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;

import uk.ac.nhm.nhm_www.core.services.ArticleSearchService;

@Service(value = ArticleSearchServiceImpl.class)
@Component(label = "Natural History Museum Article Search Service", metatype = true, immediate = true)
@Path("/articles")
public class ArticleSearchServiceImpl implements ArticleSearchService {

	private final Logger LOG = LoggerFactory.getLogger(ArticleSearchServiceImpl.class);

//	private Session session;
//	private String matches;

//	@Reference
//	private SlingRepository repository;
//
//	@Reference
//	private QueryBuilder builder;
	
//	private List<String> hyperlinks;

//	private String alistair;
	
//	public List<String> getHyperlinks() {
//		return hyperlinks;
//	}

//	@Override
//	public List<String> getArticles(String rootPath, String number, String selector) throws RepositoryException, JSONException {
//
//		this.hyperlinks = new ArrayList<String>();
//
//		try {
//			final Session session = repository.loginService("searchService", null);
//
//			Map<String, String> map = new HashMap<String, String>();
//			map.put("path", rootPath);
//			map.put("type", "cq:Page");
//
//			Query query = builder.createQuery(PredicateGroup.create(map), session);
//		    query.setStart(0);
//		    query.setHitsPerPage(200);
//
//			SearchResult result = query.getResult();
//			LOG.error("alistair test");
//			// iterating over the results
//			for (Hit hit : result.getHits()) {
//				String path = hit.getPath();
//				// Create a result element
//				hyperlinks.add(path);
//			}
//
//		} catch(Exception e) {
//			LOG.error("Error", e);
//		} finally {
//			session.logout();
//			return hyperlinks;
//		}
//		return null;
//	}
//
//	@Override
//	public String getMatches() {
//		return this.matches;
//	}
	
	@GET
	@Path("/all")
	@Produces("application/json")
	@Override
	public Response getAll() throws RepositoryException, JSONException {
		JSONObject object = new JSONObject();
		
		try {
			object.put("test", "value");
		} catch (org.json.JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String s = object.toString();

		return Response.ok(s, MediaType.APPLICATION_JSON).build();
	}
	
	@Override
	public String getArticleString() {
		return "lol";
	}
}
