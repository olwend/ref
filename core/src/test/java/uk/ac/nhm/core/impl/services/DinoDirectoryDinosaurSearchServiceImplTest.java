package uk.ac.nhm.core.impl.services;

import static org.junit.Assert.assertEquals;

import org.apache.sling.testing.mock.osgi.junit.OsgiContext;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import uk.ac.nhm.core.impl.services.dinoDirectory.DinoDirectoryDinosaurSearchServiceImpl;

public class DinoDirectoryDinosaurSearchServiceImplTest {

	DinoDirectoryDinosaurSearchServiceImpl service;
	
	@Rule  
	public final OsgiContext context = new OsgiContext();  
	
	@Before
	public void setUp() throws Exception {
		service = context.registerInjectActivateService(new DinoDirectoryDinosaurSearchServiceImpl());
	}
	
	@Test
	public void testGetTitle() {
		String title;
		
		//Body shape
		title = service.getTitle("bodyshape", "large-theropod");
		assertEquals(title, "Large theropods");
		
		//Country
		title = service.getTitle("country", "North%20Africa");
		assertEquals(title, "North Africa");
		
		//Initial
		title = service.getTitle("initial", "a");
		assertEquals(title, "Dinosaurs beginning with A");
		
		//Period
		title = service.getTitle("period", "late-jurassic");
		assertEquals(title, "Late jurassic period");
	}
	
}
