package beans;

public class Session {
	public String cand;
	public String coach;
	public String num;
	public String dates;
	public String form;
	public int rowIndex;
	public int columnIndex;
	
	public Session(int rowIndex, int columnIndex, String coach, String num, String date, String form) {
		this.rowIndex = rowIndex;
		this.columnIndex = columnIndex;
		this.coach = coach;
		this.num = num;
		this.dates = date;
		this.form = form;
	}
	
	public void setCand(String name) {
		this.cand = name;
	}
	
	public String getCand() {
		return this.cand;
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
