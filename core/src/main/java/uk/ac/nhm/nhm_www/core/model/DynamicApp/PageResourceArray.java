package uk.ac.nhm.nhm_www.core.model.DynamicApp;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.day.cq.wcm.api.Page;

public class PageResourceArray {
	private List<Page> complexObjectArray;
	private Date createdAt;

    
    private HashMap<String,Integer> indexMap = new LinkedHashMap<String, Integer>();

    public List<Page> getComplexObjectArray() {
        return complexObjectArray;
    }
    
    public Date getCreatedAt() {
        return createdAt;
    }

    
	public HashMap<String, Integer> getIndexMap() {
        return indexMap;
    }

    public void addResource(Page page) {
		getComplexObjectArray().add(page);
		indexMap.put(page.getPath(), complexObjectArray.size() -1);
    }

}
