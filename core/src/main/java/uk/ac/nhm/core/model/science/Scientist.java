package uk.ac.nhm.core.model.science;

public class Scientist implements Comparable<Scientist> {
	private String name;
	private String path;
	
	public Scientist(String name, String path) {
		super();
		this.name = name;
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public int compareTo(Scientist s) {
		if (s.name == null) {
			return -1;
		}
		
		if (this.name == null) {
			return 1;
		}
		
		return this.name.compareTo(s.name);
	}
	
	
}
