package uk.ac.nhm.core.model.slingModels;

import static org.junit.Assert.assertEquals;

import org.apache.sling.testing.mock.osgi.junit.OsgiContext;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

import junitx.util.PrivateAccessor;
import uk.ac.nhm.core.model.slingModels.dinoDirectory.Dinosaur;
import uk.ac.nhm.core.services.dinoDirectory.DinoDirectoryEnvironmentService;

public class DinoDirectoryDinosaurModelTest {

	private Dinosaur dinosaurModel;
	private DinoDirectoryEnvironmentService environmentService;
	
	private String BASE_URL;
	private String host;
	
	@Rule
	public final OsgiContext context = new OsgiContext();

	@Before
	public void setUp() throws Exception {
		environmentService = context.registerInjectActivateService(new DinoDirectoryEnvironmentService());
		BASE_URL = environmentService.getDinoDirectoryUrl();
		
		dinosaurModel = new Dinosaur();
		PrivateAccessor.setField(dinosaurModel, "name", "staging");
		PrivateAccessor.setField(dinosaurModel, "service", environmentService);
		
		host = "staging";
	}
	
	@Test
	public void testEnvironmentService() {
		assertEquals(BASE_URL, "http://staging.nhm.ac.uk/api/dino-directory-api");
	}

	@Test
	public void testImageCredit() throws Exception {
		String jsonWithCredit = "{\"id\":364,\"genus\":\"Achillobator\",\"namePronounciation\":\"a-kil-oh-bah-tor\",\"nameHyphenated\":null,\"nameMeaning\":\"Achilles hero\",\"dentition\":null,\"diet\":null,\"massTo\":null,\"massFrom\":null,\"lengthFromQualifier\":null,\"lengthToQualifier\":null,\"lengthFrom\":5.0,\"lengthTo\":null,\"heightFromQualifier\":null,\"heightToQualifier\":null,\"heightFrom\":null,\"heightTo\":null,\"locomotion\":null,\"myaFromQualifier\":null,\"myaToQualifier\":null,\"myaFrom\":99.0,\"myaTo\":84.0,\"taxTaxon\":{\"taxon\":\"giganteus\",\"authorshipYear\":1999,\"taxonomyCSV\":\"Dinosauria,Saurischia,Theropoda,Neotheropoda,Tetanurae,Avetheropoda,Coelurosauria,Tyrannoraptora,Maniraptoriformes,Maniraptora,Paraves,Eumaniraptora,Dromaeosauridae\",\"taxonId\":1964},\"countries\":[{\"id\":12,\"country\":\"Mongolia\",\"continent\":{\"id\":2,\"continent\":\"Asia\"},\"dinosaurCount\":47}],\"contexts\":[],\"publish\":true,\"isInGallery\":false,\"mediaCollection\":[{\"id\":1708,\"identifier\":\"Achillobator\",\"isDefault\":true,\"title\":null,\"caption\":null,\"mediaContentTypeName\":\"reconstruction\",\"mediaContentTypePath\":\"\\\\reconstruction\",\"copyright\":\"Andrey Atuchin\",\"creditType\":\"Copyright\",\"mediaTypeName\":\"image\",\"mediaTypePath\":\"images\"}],\"period\":{\"id\":6,\"period\":\"Late Cretaceous\",\"myaFrom\":101,\"myaTo\":66,\"dinosaurCount\":139},\"bodyShape\":{\"id\":5,\"bodyShape\":\"Small theropod\",\"description\":\"Small carnivores, herbivores and omnivores that walked on two legs and often had feathers. Birds are part of this group\",\"dinosaurCount\":56},\"textBlockCollection\":[],\"linkCollection\":[],\"dietTypeName\":\"carnivorous\",\"genusNamedBy\":\"Perle, Norell and Clark\",\"genusYear\":1999,\"species\":\"giganteus\"}";
		JSONObject dinosaurJsonWithCredit = new JSONObject(jsonWithCredit);
		
		dinosaurModel.setModelVariables(dinosaurJsonWithCredit, host);

		assertNotNull(dinosaurModel.getImageCredit());
		assertEquals(dinosaurModel.getImageCredit(), "© Andrey Atuchin");
		
		String jsonNoCredit = "{\"id\":139,\"genus\":\"Triceratops\",\"namePronounciation\":\"tri-SERRA-tops\",\"nameHyphenated\":null,\"nameMeaning\":\"three-horned face\",\"dentition\":\"horny beak and shearing teeth\",\"diet\":\"tough palm fronds\",\"massTo\":null,\"massFrom\":5500,\"lengthFromQualifier\":null,\"lengthToQualifier\":null,\"lengthFrom\":9.0,\"lengthTo\":null,\"heightFromQualifier\":null,\"heightToQualifier\":null,\"heightFrom\":null,\"heightTo\":null,\"locomotion\":\"on 4 legs\",\"myaFromQualifier\":null,\"myaToQualifier\":null,\"myaFrom\":68.0,\"myaTo\":66.0,\"taxTaxon\":{\"taxon\":\"horridus\",\"authorshipYear\":1889,\"taxonomyCSV\":\"Dinosauria,Ornithischia,Genasauria,Cerapoda,Marginocephalia,Ceratopsia,Neoceratopsia,Coronosauria,Ceratopsidae,Chasmosaurinae\",\"taxonId\":2269},\"countries\":[{\"id\":17,\"country\":\"USA\",\"continent\":{\"id\":1,\"continent\":\"North America\"},\"dinosaurCount\":80}],\"contexts\":[{\"context\":\"Top 5\"}],\"publish\":true,\"isInGallery\":true,\"mediaCollection\":[{\"id\":1474,\"identifier\":\"triceratops\",\"isDefault\":true,\"title\":null,\"caption\":null,\"mediaContentTypeName\":\"reconstruction\",\"mediaContentTypePath\":\"\\\\reconstruction\",\"copyright\":null,\"creditType\":null,\"mediaTypeName\":\"image\",\"mediaTypePath\":\"images\"}],\"period\":{\"id\":6,\"period\":\"Late Cretaceous\",\"myaFrom\":101,\"myaTo\":66,\"dinosaurCount\":139},\"bodyShape\":{\"id\":2,\"bodyShape\":\"Ceratopsian\",\"description\":\"Herbivores with parrot-like beaks, bony frills and in many cases horns\",\"dinosaurCount\":25},\"textBlockCollection\":[{\"id\":1,\"identifier\":\"top5-intro\",\"title\":null,\"textBlock\":\"<p><i>Triceratops</i> was a plant-eater with specialised teeth for cutting and slicing and a huge stomach for digesting tough plant matter. It would have used its horns for defending itself from predators like <i>Tyrannosaurus</i>.</p>\",\"sortOrder\":null,\"context\":\"Top 5\"},{\"id\":22,\"identifier\":\"detail\",\"title\":null,\"textBlock\":null,\"sortOrder\":null,\"context\":\"detail page\"},{\"id\":2,\"identifier\":\"detail\",\"title\":null,\"textBlock\":\"<p>With its 3 horns, a parrot-like beak and a large frill that could reach nearly 1 metre (3 feet) across, the <i>Triceratops</i> skull is one of the largest and most striking of any land animal.</p>\\r\\n\\r\\n<p>The horns could have been used to fend off attacks from <i>Tyrannosaurus</i>. A partial <i>Triceratops</i> fossil collected in 1997 has a horn that was bitten off, with bite marks that match <i>Tyrannosaurus</i>. The fossil shows that the horn healed after being bitten, so at least some <i>Triceratops</i> survived these encounters.</p>\\r\\n\\r\\n<p>Puncture marks on fossil frills show that male <i>Triceratops</i> also used their horns to fight each other, probably to impress females.</p>\",\"sortOrder\":1,\"context\":\"detail page\"},{\"id\":7,\"identifier\":\"detail\",\"title\":\"Herd instinct\",\"textBlock\":\"<p>Many other horned dinosaurs are known to have lived in herds because of a fossil find of many different individuals at the same location.</p>\\r\\n\\r\\n<p>By moving in herds, prey animals can warn each other of danger and lessen their chances of being singled out by a predator.</p>\\r\\n\\r\\n<p>However, <i>Triceratops</i> was unusual in this respect, as their remains are usually found individually, suggesting they may have spent much of their lives alone.</p>\",\"sortOrder\":2,\"context\":\"detail page\"},{\"id\":8,\"identifier\":\"detail\",\"title\":\"Why the frill?\",\"textBlock\":\"<p>The <i>Triceratops</i> frill might have helped to protect its neck, but some specimens show <i>Tyrannosaurus</i> bite marks puncturing the frill, so it wasn't always enough.</p>\\r\\n\\r\\n<p>The frills could also have been used to attract mates, as a way for members of the same species to recognise each other.</p>\",\"sortOrder\":3,\"context\":\"detail page\"}],\"linkCollection\":[{\"id\":35,\"linkUrl\":\"http://www.nhmshop.co.uk/dinosaurs/view-all-dinosaur-gifts.html?dinosaur=2087\",\"linkText\":\"Buy\",\"linkDescription\":\"<i>Triceratops</i> toys and games\",\"sortOrder\":3}],\"dietTypeName\":\"herbivorous\",\"genusNamedBy\":\"Marsh\",\"genusYear\":1889,\"species\":\"horridus\"}";
		JSONObject dinosaurJsonNoCredit = new JSONObject(jsonNoCredit);
		
		dinosaurModel.setModelVariables(dinosaurJsonNoCredit, host);
		
		assertNull(dinosaurModel.getImageCredit());
	}
	
}
