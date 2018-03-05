package uk.ac.nhm.core.impl.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import org.apache.sling.testing.mock.osgi.junit.OsgiContext;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import uk.ac.nhm.core.impl.services.dinoDirectory.DinoDirectoryDinosaurSearchServiceImpl;
import uk.ac.nhm.core.services.dinoDirectory.DinoDirectoryEnvironmentService;

public class DinoDirectoryDinosaurSearchServiceImplTest {

	DinoDirectoryDinosaurSearchServiceImpl service;
	DinoDirectoryEnvironmentService environmentService;
	
	private String BASE_URL;
	
	@Rule  
	public final OsgiContext context = new OsgiContext();
	
	@Before
	public void setUp() throws Exception {
		service = context.registerInjectActivateService(new DinoDirectoryDinosaurSearchServiceImpl());
		environmentService = context.registerInjectActivateService(new DinoDirectoryEnvironmentService());
		BASE_URL = environmentService.getDinoDirectoryUrl();
	}
	
	@Test
	public void testEnvironmentService() {
		assertEquals(BASE_URL, "http://staging.nhm.ac.uk/api/dino-directory-api");
	}
	
	@Test
	public void testGetDinosaurList() {
		List<Map<String, String>> dinosaurList = service.getDinosaurList("bodyshape", "large-theropod", BASE_URL);
		assertThat(dinosaurList.size(), greaterThan(0));
		
		Map<String, String> dinosaur = dinosaurList.get(0);
		String dinosaurName = dinosaur.get("genus");
		String dinosaurUrl = "/content/nhmwww/en/home/discover/dino-directory/" + dinosaurName.toLowerCase() + ".html";
		assertEquals(dinosaur.get("url"), dinosaurUrl);
	}
	
	@Test
	public void testGetTitle() {
		String title;
		
		//Body shape
		title = service.getTitle("bodyshape", "large-theropod");
		assertEquals(title, "Large theropod dinosaurs");
		
		//Country
		title = service.getTitle("country", "North%20Africa");
		assertEquals(title, "Dinosaurs in North Africa");
		
		//Initial
		title = service.getTitle("initial", "a");
		assertEquals(title, "Dinosaurs beginning with A");
		
		//Period
		title = service.getTitle("period", "late-jurassic");
		assertEquals(title, "Dinosaurs in the late Jurassic");
	}
	
}
