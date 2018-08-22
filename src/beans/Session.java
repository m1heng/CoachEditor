package beans;

public class Session {
	public String coach;
	public int num;
	public String dates;
	public String form;
	
	public Session(String coach, int num, String date, String form) {
		this.coach = coach;
		this.num = num;
		this.dates = date;
		this.form = form;
	}
	
	public String getName() {
		if(this.coach != null) {
			return this.coach;
		}else {
			return " ";
		}
	}
	public String getDate() {
		if(this.dates != null) {
			return this.dates;
		}else {
			return " ";
		}
	}
	public String getForm() {
		if(this.form != null) {
			return this.form;
		}else {
			return " ";
		}
	}
}
