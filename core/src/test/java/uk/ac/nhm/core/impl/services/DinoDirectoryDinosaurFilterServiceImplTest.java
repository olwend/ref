package uk.ac.nhm.core.impl.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertEquals;

import org.hamcrest.text.MatchesPattern;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.sling.testing.mock.osgi.junit.OsgiContext;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import uk.ac.nhm.core.impl.services.dinoDirectory.DinoDirectoryDinosaurFilterServiceImpl;
import uk.ac.nhm.core.services.dinoDirectory.DinoDirectoryEnvironmentService;

public class DinoDirectoryDinosaurFilterServiceImplTest {

	DinoDirectoryDinosaurFilterServiceImpl service;
	DinoDirectoryEnvironmentService environmentService;

	private String BASE_URL;

	@Rule
	public final OsgiContext context = new OsgiContext();

	@Before
	public void setUp() throws Exception {
		service = context.registerInjectActivateService(new DinoDirectoryDinosaurFilterServiceImpl());
		environmentService = context.registerInjectActivateService(new DinoDirectoryEnvironmentService());
		BASE_URL = environmentService.getDinoDirectoryUrl();
	}

	@Test
	public void testEnvironmentService() {
		assertEquals(BASE_URL, "http://staging.nhm.ac.uk/api/dino-directory-api");
	}

	@Test
	public void testGetDinosaurList() {
		List<Map<String, String>> dinosaurList = service.getDinosaurList("body-shapes", "large-theropod", BASE_URL);
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
		title = service.getTitle("body-shapes", "large-theropod");
		assertEquals(title, "Large Theropods");

		title = service.getTitle("body-shapes", "sauropod");
		assertEquals(title, "Sauropods");

		//Country
		title = service.getTitle("countries", "North%20Africa");
		assertEquals(title, "Dinosaurs in North Africa");

		title = service.getTitle("countries", "England");
		assertEquals(title, "Dinosaurs in England");

		title = service.getTitle("countries", "usa");
		assertEquals(title, "Dinosaurs in USA");

		//Initial
		title = service.getTitle("initials", "a");
		assertEquals(title, "Dinosaurs beginning with A");

		//Period
		title = service.getTitle("periods", "late-jurassic");
		assertEquals(title, "Dinosaurs in the late Jurassic");
	}

	@Test
	public void testGetDescription() {
		String description;

		//Body shape
		description = service.getDescription("body-shapes", "large-theropod", BASE_URL);
		assertEquals(description, "Large carnivores that walked on two legs.");

		//Country
		Pattern countryPatternMultiple = Pattern.compile("^\\d+ dinosaurs found in England");
		description = service.getDescription("countries", "England", BASE_URL);
		assertThat(description, MatchesPattern.matchesPattern(countryPatternMultiple));

		Pattern countryPatternSingle = Pattern.compile("^\\d+ dinosaur found in North Africa");
		description = service.getDescription("countries", "North%20Africa", BASE_URL);
		assertThat(description, MatchesPattern.matchesPattern(countryPatternSingle));
		
		//Initial
		Pattern initialPatternMultiple = Pattern.compile("^\\d+ dinosaurs beginning with A");
		description = service.getDescription("initials", "a", BASE_URL);
		assertThat(description, MatchesPattern.matchesPattern(initialPatternMultiple));

		Pattern initialPatternSingle = Pattern.compile("^\\d+ dinosaur beginning with Q");
		description = service.getDescription("initials", "q", BASE_URL);
		assertThat(description, MatchesPattern.matchesPattern(initialPatternSingle));

		//Period
		Pattern periodPattern = Pattern.compile("^\\(\\d+ to \\d+ million years ago\\)<br>\\d+ dinosaurs from the Late Jurassic");
		description = service.getDescription("periods", "late-jurassic", BASE_URL);
		assertThat(description, MatchesPattern.matchesPattern(periodPattern));
	}

}
