package uk.ac.nhm.nhm_www.core.model.youtube;

import java.util.Date;

public class Video {
	private String id;
	private String thumbnailURL;
	private String title;
	private String description;
	private Date uploadDate;
	
	public Video(final String id, final String thumbnailURL) {
		this.id = id;
		this.thumbnailURL = thumbnailURL;
	}
	
	public Video(final String id, final String thumbnailURL, final String title, final String description, final Date uploadDate) {
		this.id = id;
		this.thumbnailURL = thumbnailURL;
		this.title = title;
		this.description = description;
		this.uploadDate = uploadDate;
	}

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public String getThumbnailURL() {
		return thumbnailURL;
	}

	public void setThumbnailURL(final String thumbnail) {
		this.thumbnailURL = thumbnail;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}
	
	public Date getUploadDate() {
		return this.uploadDate;
	}
	
	public void setUploadDate(final Date uploadDate) {
		this.uploadDate = uploadDate;
	}
	
}
