package uk.ac.nhm.nhm_www.core.impl.services;

import javax.jcr.RepositoryException;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.jcr.api.SlingRepository;
import org.osgi.service.component.ComponentContext;

import com.day.cq.search.result.SearchResult;

import uk.ac.nhm.nhm_www.core.services.ArticleSearchService;

@Service(value = ArticleSearchServiceImpl.class)
@Component(label = "Natural History Museum Article Search Service", metatype = true, immediate = true)
public class ArticleSearchServiceImpl implements ArticleSearchService {

	@Reference
	private SlingRepository repository;
	
	@Activate
	protected void activate(final ComponentContext componentContext) throws Exception {
		
	}
	
	@Override
	public SearchResult getArticles() throws RepositoryException, JSONException {
		// TODO Auto-generated method stub
		return null;
	}

}
