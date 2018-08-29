package beans;

public class Candidate {
	public int id;
	public String name;
	
	public Candidate(int id, String name ) {
		this.id = id;
		this.name = name;
	}
	
	public String toString() {
		if(this.name != null) {
			return this.name;
		}else {
			return "Unknown";
		}
	}
}
