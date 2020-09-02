// Bilaal Ahmad
// AP CS P4
// Visicalc

// helper class
public class Grid {

	// 2d array of cell objects
	Cell[][] spreadsheet = new Cell[10][7];
	public char[] alphabet = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'a', 'b', 'c', 'd', 'e', 'f', 'g'};
	
	// method for printing grid 
	public void printGrid ( ) {
		// print header
		System.out.print("       |");
		for(int i = 0; i < 7; i++) {
			System.out.print("   " + alphabet[i] + "   |" );
		}
		System.out.println("\n----------------------------------------------------------------");
		// print body	
		for (int r = 0; r < 10; r++)  {
			System.out.printf("  %2d   |", r + 1);	
			for (int c = 0; c < 7; c++) {
				if (spreadsheet[r][c] == null) {
					System.out.print("       |");
				} else {
					
					String content = spreadsheet[r][c].getContent();
					
					// to ensure correct formatting
					if (content.length() <= 7) {
						System.out.printf("%7s|", content);
					}  else {
						String truncatedContent = truncator(content);
						System.out.printf("%7s|", truncatedContent);
					}
				}
			}
			System.out.println("\n----------------------------------------------------------------");	
		}
		System.out.println();
	}

	// returns the integer that the user wants to add, or content passed as formula cell parameter
	public String contentIndentifier(String[] input, String inputStr) {
		String content = "error!";
		if (input.length == 3) {
			content = input[2];
		} else if (inputStr.contains("(")) {
			// if statement accounts for cells with row as 10
			if (input[0].length() == 2) {
				content = inputStr.substring(7, inputStr.length() - 1);
			} else {
				content = inputStr.substring(8, inputStr.length() - 1);
			}
		} else if (input.length == 5){
			content = input[3];
		} else if (input.length > 5) {
			int locationOfQuote = inputStr.indexOf("\"");
			content = inputStr.substring(locationOfQuote + 2, inputStr.length() - 2);
		}
		return content;
	}

	// returns index of column as number
	public int getColIndex(String[] input) {
		String fullIndex = "";
		if (input[0].equalsIgnoreCase("clear")) {
			fullIndex = input[1];
		} else {
			fullIndex = input[0];
		}
		fullIndex = fullIndex.toUpperCase();
		char colLetter = fullIndex.charAt(0);
		return colLetter - 65;
	}

	// returns index of row 
	public int getRowIndex(String[] input) {
		String fullIndex = "";
		if (input[0].equalsIgnoreCase("clear")) {
			fullIndex = input[1];
		} else {
			fullIndex = input[0];
		}
		
		String rowStr = fullIndex.substring(1);
		return Integer.parseInt(rowStr) - 1;
	}
	
	// shortens integers to fit formatting
	public String truncator(String str) {
			String newStr = str.substring(0, 7);
			return newStr;
	}
	
	// clears entire grid or individual cell
	public void clear (String input) {
		String[] inputArr = input.split(" ");
		if (inputArr.length == 1) {
			// clear entire grid
			for (int r = 0; r < 10; r++) {
				for (int c = 0; c < 7; c++) {
					if (spreadsheet[r][c] != null) {
						spreadsheet[r][c] = null;
					}
				}
			}
			System.out.println("grid cleared!");
		} else {
			spreadsheet[getRowIndex(inputArr)][getColIndex(inputArr)] = null;
			System.out.println("cell " + inputArr[1] + " cleared!");
		}
	}
	
	// array that stores inputs
	int inputCounterGrid = 0;
	String[] inputs = new String[70];
	
	// copies user input into an array
	public void inputSaver (String input) {
		inputs[inputCounterGrid] = input;
		inputCounterGrid ++;
	}
	
	
	
}








