package uk.ac.nhm.nhm_www.core.model.discover;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Group of Discover Publication Component.
 */
public class ResourceComponentArray {

    private List<ResourceComponent> complexObjectArray;
    
    private Date createdAt;

    
    private HashMap<String,Integer> indexMap = new LinkedHashMap<String, Integer>();
    
    public ResourceComponentArray() {
    	this.complexObjectArray = new ArrayList<ResourceComponent>();
    	this.createdAt = new Date();
    }

    public List<ResourceComponent> getComplexObjectArray() {
        return complexObjectArray;
    }
    
    public Date getCreatedAt() {
        return createdAt;
    }

    
	public HashMap<String, Integer> getIndexMap() {
        return indexMap;
    }

    public void addResource(ResourceComponent resource) {
		getComplexObjectArray().add(resource);
		indexMap.put(resource.getPath(), complexObjectArray.size() -1);
    }

}
