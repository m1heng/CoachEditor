package beans;

import javafx.stage.Stage;

public class Coach {
	public static final int COACHALIGNMENT = 9;
	public static final int COACHWIDTH = 3;
	public int id;
	public String name;
	
	public Coach(int index, String name) {
		this.id = index - Coach.COACHALIGNMENT;
		this.name = name;
	}
	
	public int getIndex() {
		return this.id + Coach.COACHALIGNMENT;
	}
	
	public String toString() {
		if(this.name != null && this.name != "") {
			return this.name;
		}else {
			return "Unknown";
		}
	}
	
}
