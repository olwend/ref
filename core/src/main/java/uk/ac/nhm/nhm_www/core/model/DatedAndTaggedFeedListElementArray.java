package uk.ac.nhm.nhm_www.core.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class DatedAndTaggedFeedListElementArray {

    private List<DatedAndTaggedFeedListElement> complexObjectArray;
    
    private Date createdAt;

    private HashMap<String,Integer> indexMap = new LinkedHashMap<String, Integer>();
    
    public DatedAndTaggedFeedListElementArray() {
    	this.complexObjectArray = new ArrayList<DatedAndTaggedFeedListElement>();
    	this.createdAt = new Date();
    }

    public List<DatedAndTaggedFeedListElement> getComplexObjectArray() {
        return complexObjectArray;
    }
    
    public Date getCreatedAt() {
        return createdAt;
    }

	public HashMap<String, Integer> getIndexMap() {
        return indexMap;
    }

    public void addResource(DatedAndTaggedFeedListElement element) {
		getComplexObjectArray().add(element);
		indexMap.put(element.getPath(), complexObjectArray.size() -1);
    }

}