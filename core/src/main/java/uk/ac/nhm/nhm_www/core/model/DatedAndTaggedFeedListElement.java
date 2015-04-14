package uk.ac.nhm.nhm_www.core.model;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.sling.api.resource.ResourceResolver;

import com.day.cq.tagging.Tag;
import com.day.cq.wcm.api.Page;

public class DatedAndTaggedFeedListElement extends PressReleaseFeedListElement{
	
    protected Tag[] tags;

	public DatedAndTaggedFeedListElement(Page page) {
		super(page);
		this.tags = page.getTags();
	}
	
	public boolean isInitialised() {
		if(this.tags != null && this.title != null && this.elementLink !=null && this.imageResourcePath !=null && this.getPressReleaseDate() != null) {
			return true;
		} else {
			return false;
		}
	}
	
	public Tag[] getTags() {
		return tags;
	}
	
	public boolean hasTags(Tag[] tags){
		boolean found = false;
		List<Tag> pageTags = Arrays.asList(this.tags); 
		Iterator<Tag> filterIterator = Arrays.asList(tags).iterator();
		
		while(!found && filterIterator.hasNext()){
			Tag tagFilter = filterIterator.next();
			found = pageTags.contains(tagFilter); 
		}
		return found;
	}

//	********************************
//	**** May not be used at all ****
//	********************************	
//	public void setTags(Tag[] tags){
//		this.tags = tags;
//	}

}