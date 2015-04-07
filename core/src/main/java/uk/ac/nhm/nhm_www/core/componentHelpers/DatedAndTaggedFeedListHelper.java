package uk.ac.nhm.nhm_www.core.componentHelpers;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.nhm.nhm_www.core.model.PressReleaseFeedListElement;

public class DatedAndTaggedFeedListHelper extends PressReleaseFeedListHelper {
	
	//Here Be Dragons, ToDo: Implement Tags[] handling here!, Dates are already being handled by the PressReleaseFeedListHelper

    public DatedAndTaggedFeedListHelper(ValueMap properties, PageManager pageManager,
			Page currentPage, HttpServletRequest request,
			ResourceResolver resourceResolver) {
		super(properties, pageManager, currentPage, request, resourceResolver);

	}

	protected static final Logger logger = LoggerFactory.getLogger(DatedAndTaggedFeedListHelper.class);

}
