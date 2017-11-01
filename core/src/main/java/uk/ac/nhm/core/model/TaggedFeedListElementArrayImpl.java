package uk.ac.nhm.core.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class TaggedFeedListElementArrayImpl implements TaggedFeedListElementArray {

    private List<TaggedFeedListElement> complexObjectArray;
    
    private Date createdAt;

    private HashMap<String,Integer> indexMap = new LinkedHashMap<String, Integer>();
    
    public TaggedFeedListElementArrayImpl() {
    	this.complexObjectArray = new ArrayList<TaggedFeedListElement>();
    	this.createdAt = new Date();
    }

    /* (non-Javadoc)
	 * @see uk.ac.nhm.core.model.TaggedFeedListElementArray#getComplexObjectArray()
	 */
    @Override
	public List<TaggedFeedListElement> getComplexObjectArray() {
        return complexObjectArray;
    }
    
    /* (non-Javadoc)
	 * @see uk.ac.nhm.core.model.TaggedFeedListElementArray#getCreatedAt()
	 */
    @Override
	public Date getCreatedAt() {
        return createdAt;
    }

	/* (non-Javadoc)
	 * @see uk.ac.nhm.core.model.TaggedFeedListElementArray#getIndexMap()
	 */
	@Override
	public HashMap<String, Integer> getIndexMap() {
        return indexMap;
    }

    /* (non-Javadoc)
	 * @see uk.ac.nhm.core.model.TaggedFeedListElementArray#addResource(uk.ac.nhm.core.model.TaggedFeedListElementImpl)
	 */
    @Override
	public void addResource(TaggedFeedListElement element) {
		getComplexObjectArray().add(element);
		indexMap.put(element.getElementLink(), complexObjectArray.size() -1);
    }

}