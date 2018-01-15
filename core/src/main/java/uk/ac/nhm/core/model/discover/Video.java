package uk.ac.nhm.core.model.discover;

/**
 * Discover YouTube Video configured as Head in a Discover Publication.
 */
public class Video {

    private String youTubeId;

    public Video(String youTubeId){

	this.youTubeId = youTubeId;
    }
    
    public String getYouTubeId() {
        return youTubeId;
    }

    public void setYouTubeId(String youTubeId) {
        this.youTubeId = youTubeId;
    } 
    
}
