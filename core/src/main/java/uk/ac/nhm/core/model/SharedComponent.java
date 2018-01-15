package uk.ac.nhm.core.model;

public class SharedComponent implements Comparable<SharedComponent> {
	private String path;
	private String title;
	
	public SharedComponent(final String path, final String title) {
		this.path = path;
		this.title = title;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int compareTo(SharedComponent anotherSharedComponent) {
		return this.title.compareTo(anotherSharedComponent.getTitle());    
	}
	
}
