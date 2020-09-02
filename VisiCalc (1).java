// Bilaal Ahmad
// AP CS P4
// Visicalc

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;;

public class VisiCalc {

	public static int inputCounter;
	public static Grid g = new Grid();
	public static String lastInput = "";
	
	public static void main(String[] args) throws FileNotFoundException {
		
		Scanner userInput = new Scanner(System.in);
		System.out.println("Welcome to VisiCalc!\n");
		
		boolean quit = false;
		String input = "";
		
		// user input loop 
		while (!quit) {
			System.out.print("Enter: ");
			input = userInput.nextLine();
			lastInput = input;

			if (input.equalsIgnoreCase("quit")) {
				quit = true;
				System.out.println("\nGoodbye!");
			} else if (input.equalsIgnoreCase("help")) {
				// make help method later
				System.out.println("Display help menu");
			} else if (input.equalsIgnoreCase("print")) {
				System.out.println();
				g.printGrid();
			} else if (input.contains("save")) {
				saver(input);
			} else if (input.contains("load")) {
				fileLoader(input);
			} else if (input.contains("clear")) {
				g.clear(input);
			} else {
				commandInput(input);
				g.inputSaver(input);
				inputCounter++;
			} 
			System.out.println();
		}
		
		userInput.close();
	}

	
	// user input was a command
	public static void commandInput(String input) {
		
		input = input.toLowerCase();
		// splits input into an array
		String[] inputArr = input.split(" ");
		
		if (inputArr.length == 1 && inputArr[0].length() <= 3) {
			// call individual cell if input is one token of length 2
			Cell c = g.spreadsheet[g.getRowIndex(inputArr)][g.getColIndex(inputArr)];
			
			if (c == null) {
				System.out.println("cell " + input + " is empty");
			} else {
				System.out.println(input + " is " + c.getContent());
			}
		} else if (inputArr[2].contentEquals("(")) {
			// formula cell
			// System.out.println("cell to be filled: " + g.getRowIndex(inputArr) + g.getColIndex(inputArr));
			g.spreadsheet[g.getRowIndex(inputArr)][g.getColIndex(inputArr)] = new FormulaCell(g.contentIndentifier(inputArr, input));
			
		} else if (inputArr.length > 3) {
			// put text in cell if input is more than 3 tokens
			g.spreadsheet[g.getRowIndex(inputArr)][g.getColIndex(inputArr)] = new TextCell(g.contentIndentifier(inputArr, input));
			
		} else if ((inputArr.length == 3) && (!isNumber(input) || input.contains("\""))) {
			// incorrect text format error, for cases: a1 = hello, a1 = "hello"
			System.out.println("incorrect text format!");
			
		} else if (input.contains("/")) {
			// put date in cell if input contains "/"
			g.spreadsheet[g.getRowIndex(inputArr)][g.getColIndex(inputArr)] = new DateCell(g.contentIndentifier(inputArr, input));
			
		} else if (input.contains("=") && inputArr.length == 3) {
			// put integer in cell if input is 3 tokens and contains "="
			String intString = g.contentIndentifier(inputArr, input);
			g.spreadsheet[g.getRowIndex(inputArr)][g.getColIndex(inputArr)] = new Cell(Integer.parseInt(intString));
			
		} else {
			// invalid input given
			System.out.println("invalid command!");
		}
	}
	
	// creates new file and fills it with all inputs
	public static void saver (String input) throws FileNotFoundException {
		Scanner s = new Scanner(input);
		String[] arr = input.split(" ");
		String fileName = arr[1];
		s.close();
		
		File file = new File(fileName);
		PrintStream fileWriter = new PrintStream(file);
		
		for (int i = 0; i < inputCounter; i++) {
			fileWriter.println(g.inputs[i]);
		}
		System.out.println("file saved");
		fileWriter.close();
	}
	
	// loads a file
	public static void fileLoader(String input) throws FileNotFoundException {
		String[] arr = input.split(" ");
		String fileName = arr[1];
		File f = new File(fileName);
		
		Scanner fileReader = new Scanner(f);
		while (fileReader.hasNext()) {
			commandInput(fileReader.nextLine());
		}
		
		System.out.println("file loaded");
		fileReader.close();
	}
	
	// checks if a string is a number, not perfect but works for incorrect text format error
	public static boolean isNumber (String s) {
		boolean isNumber = false;
		char[] charArray = s.toCharArray();
		for (char c : charArray) {
			if(Character.getNumericValue(c) < 10) {
				isNumber = true;
			} else {
				isNumber = false;
			}
		}
		return isNumber;
	}
}








