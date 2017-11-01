package uk.ac.nhm.core.model.discover;

import java.util.Date;

/**
 * Element representing a Discover Publication Component information to show in a Discover Listing component.
 */
public class ResourceComponent {
    
    private String title;
    
    private String href;
    
    private String text;
    
    private Image image;
    
    private Video video;
    
    private String tag;
    
    private Date created;

    private String path;
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    
    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
	String result = "";
	if(video!=null) {
	    result= "ResourceComponent [path=" + path +", title=" + title + ", href=" + href
			+ ", text=" + text + ", video=" + video.getYouTubeId() + ", tag=" + tag
			+ ", created=" + created + "]";
	}
	if(image!=null){
	    result= "ResourceComponent [path=" + path +",title=" + title + ", href=" + href
			+ ", text=" + text + ", image=" + image.getFileReference() + ", tag=" + tag
			+ ", created=" + created + "]"; 
	}
	
	return result;
	
    }
    
}
