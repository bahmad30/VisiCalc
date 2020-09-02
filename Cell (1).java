// Bilaal Ahmad
// AP CS P4
// Visicalc

// helper class
public class Cell {
	
	private int value;
	
	// constructor
	public Cell (int value) {
		this.value = value;
	}
	
	// setter, in case of replacement
	public void setValue (int value) {
		this.value = value;
	}
	
	// getter, returns integer as string
	public String getContent () {
		return Integer.toString(value);
	}
	
	// getter, returns integer
	public int getContentInt () {
		return value;
	}
}
