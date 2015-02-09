package uk.ac.nhm.nhm_www.core.model.youtube;

import java.util.ArrayList;
import java.util.List;

public class YouTubeVideosPage {
	protected String nextPage;
	protected List<Video> videos;
	
	public YouTubeVideosPage() {
		this.nextPage = null;
		videos = new ArrayList<Video>();
	}
	
	public YouTubeVideosPage(final String nextPage, final List<Video> videos) {
		this.nextPage = nextPage;
		this.videos = videos;
	}

	public String getNextPage() {
		return nextPage;
	}

	public void setNextPage(final String nextPage) {
		this.nextPage = nextPage;
	}

	public List<Video> getVideos() {
		return videos;
	}

	public void setVideos(final List<Video> videos) {
		this.videos = videos;
	}
	
	public void addVideo(final Video video) {
		this.videos.add(video);
	}
	
	public boolean hasNextPage() {
		return this.nextPage != null;
	}
	
}
