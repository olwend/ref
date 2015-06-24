package uk.ac.nhm.nhm_www.core.model;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public interface TaggedFeedListElementArray {

	public abstract List<TaggedFeedListElement> getComplexObjectArray();

	public abstract Date getCreatedAt();

	public abstract HashMap<String, Integer> getIndexMap();

	public abstract void addResource(TaggedFeedListElement element);

}