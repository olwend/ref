package uk.ac.nhm.core.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class DatedAndTaggedFeedListElementArray implements TaggedFeedListElementArray{

    private List<TaggedFeedListElement> complexObjectArray;
    
    private Date createdAt;

    private HashMap<String,Integer> indexMap = new LinkedHashMap<String, Integer>();
    
    public DatedAndTaggedFeedListElementArray() {
    	this.complexObjectArray = new ArrayList<TaggedFeedListElement>();
    	this.createdAt = new Date();
    }

    public List<TaggedFeedListElement> getComplexObjectArray() {
        return complexObjectArray;
    }
    
    public Date getCreatedAt() {
        return createdAt;
    }

	public HashMap<String, Integer> getIndexMap() {
        return indexMap;
    }

    public void addResource(TaggedFeedListElement element) {
		getComplexObjectArray().add((DatedAndTaggedFeedListElement) element);
		indexMap.put(element.getElementLink(), complexObjectArray.size() -1);
    }

}