package assignment_06;

public class Movement {
	private String date;
	private String causal;
	
	public Movement() {}
	
	public Movement(String date, String causal) {
		this.date = date;
		this.causal = causal;
	}
	
	public String getDate() {
		return this.date;
	}
	
	public String getCausal() {
		return this.causal;
	}
}
