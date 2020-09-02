// Bilaal Ahmad
// Visicalc

// helper class
public class DateCell extends Cell {

	private String date;
	
	public DateCell(String date) {
		super(0);
		this.date = date;
	}
	
	// setter, in case of replacement
	public void setDate (String date) {
		this.date = date;
	}
		
	
	// getter
	public String getContent () {
			return date;
	}
}
