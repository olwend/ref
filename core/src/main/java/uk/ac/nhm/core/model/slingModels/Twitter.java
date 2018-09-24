package uk.ac.nhm.core.model.slingModels;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.ValueFormatException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Model(adaptables = SlingHttpServletRequest.class)
public class Twitter {
	
	private final static Logger LOG = LoggerFactory.getLogger(QA.class);

	@Inject
	SlingHttpServletRequest request;

	@Inject
	ValueMap properties;

	public static final String FEED_ID_ATTRIBUTE_NAME  	= "widgetid";
	public static final String HEIGHT_ATTRIBUTE_NAME	= "height";
	
	private String feedId = null;
	private String height = null;
	
	@PostConstruct
	protected void init() {
		setVariables(properties);
	}
	
	protected void setVariables(ValueMap properties) {
		if(properties.get(FEED_ID_ATTRIBUTE_NAME) != null) {
			this.setFeedId(properties.get(FEED_ID_ATTRIBUTE_NAME, String.class));
		}
		
		if(properties.get(HEIGHT_ATTRIBUTE_NAME) != null) {
			this.setHeight(properties.get(HEIGHT_ATTRIBUTE_NAME, String.class));
		} else {
			this.setHeight("300");
		}
	}
	
	public boolean isConfigured() throws ValueFormatException, PathNotFoundException, RepositoryException {
		if(this.properties != null
					&& this.properties.get(FEED_ID_ATTRIBUTE_NAME, String.class) != null) {
			return true;
		} else {
			return false;
		}
	}

	public String getFeedId() {
		return feedId;
	}

	public void setFeedId(String feedId) {
		if (feedId.equals("homepage")) {
			this.feedId = "NHM_London";
		}

		else if (feedId.equals("visit-us")) {
			this.feedId = "NHM_Visiting";
		}

		else if (feedId.equals("our-science")) {
			this.feedId = "NHM_Science";
		}

		else if (feedId.equals("museum")) {
			this.feedId = "NHM_London";
		}

		else if (feedId.equals("london")) {
			this.feedId = "NHM_London";
		}

		else if (feedId.equals("visiting")) {
			this.feedId = "NHM_Visiting";
		}

		else if (feedId.equals("events")) {
			this.feedId = "NHM_Venuehire";
		}

		else if (feedId.equals("library")) {
			this.feedId = "NHM_Library";
		}

		else if (feedId.equals("nhm-id")) {
			this.feedId = "NHM_ID";
		}

		else if (feedId.equals("nhm-wpy")) {
			this.feedId = "NHM_WPY";
		}

		else if (feedId.equals("nhm-tring")) {
			this.feedId = "NHM_Tring";
		}

		else if (feedId.equals("nhm-journal-systematic-palaentology")) {
			this.feedId = "JournalSystPal";
		}

		else if (feedId.equals("nhm-filming")) {
			this.feedId = "Filming_at_NHM";
		}

		else if (feedId.equals("nhm-ice-rink")) {
			this.feedId = "NHM_IceRink";
		}

		else if (feedId.equals("nhm-deworm3")) { 
			this.feedId = "NHM_deworm3"; 
		}

		else if (feedId.equals("nhm-brachiopoda")) { 
			this.feedId = "NHM_Brachiopoda"; 
		}

		else if (feedId.equals("nhm-bryozoan")) { 
			this.feedId = "BryozoanNHM"; 
		}

		else if (feedId.equals("nhm-cephalopoda")) { 
			this.feedId = "NHM_Cephalopoda"; 
		}

		else if (feedId.equals("nhm-dino-lab")) { 
			this.feedId = "NMHdinolab"; 
		}

		else if (feedId.equals("nhm-forensics")) { 
			this.feedId = "NHM_Forensics"; 
		}

		else if (feedId.equals("nhm-fossil-crocs")) { 
			this.feedId = "NHM_FossilCrocs"; 
		}

		else if (feedId.equals("nhm-fossil-fish")) { 
			this.feedId = "NHM_FossilFish"; 
		}

		else if (feedId.equals("nhm-meteorites")) { 
			this.feedId = "NHM_Meteorites"; 
		}

		else if (feedId.equals("nhm-micropalaeo")) { 
			this.feedId = "NHM_Micropalaeo"; 
		}

		else if (feedId.equals("nhm-oology")) { 
			this.feedId = "NHM_Oology"; 
		}

		else if (feedId.equals("nhm-palaeobotany")) { 
			this.feedId = "NHMPalaeobotany"; 
		}

		else if (feedId.equals("nhm-pterosauria")) { 
			this.feedId = "NHM_Pterosauria"; 
		}

		else if (feedId.equals("nhm-coleoptera")) { 
			this.feedId = "NHM_Coleoptera"; 
		}

		else if (feedId.equals("nhm-diptera")) { 
			this.feedId = "NHM_Diptera"; 
		}
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

}
