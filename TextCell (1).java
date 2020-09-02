// Bilaal Ahmad
// AP CS P4
// Visicalc

public class TextCell extends Cell {

	private String text;
	
	public TextCell(String text) {
		super(0);
		this.text = text;
	}
	
	// setter, in case of replacement
	public void setText (String text) {
		this.text = text;
	}
		
	
	// getter
	public String getContent () {
			return text;
	}
	
}
