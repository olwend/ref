package uk.ac.nhm.core.model.discover;

/**
 * Discover Image configured as Head in a Discover Publication.
 */
public class Image {

    private String fileReference;

    public Image(String filReference){
    	this.fileReference = filReference;
    }
    
    public String getFileReference() {
        return fileReference;
    }
    public void setFileReference(String fileReference) {
        this.fileReference = fileReference;
    }
    
    
}
