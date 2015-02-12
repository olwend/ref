package uk.ac.nhm.nhm_www.core.componentHelpers;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.sling.api.resource.ValueMap;

import uk.ac.nhm.nhm_www.core.model.FeedListElement;
import uk.ac.nhm.nhm_www.core.model.PressReleaseFeedListElement;

public class PressReleaseFeedListHelper extends FeedListHelper {

	List<Object> elements;
	public PressReleaseFeedListHelper(ValueMap properties) {
		super(properties);
		
	}
	@Override
	public List<Object> getChildrenElements() {
		
		
		return elements;
	}

}
