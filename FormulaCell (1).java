// Bilaal Ahmad
// Visicalc

import java.util.Scanner;

// helper class
public class FormulaCell extends Cell {

	String formula;
	
	public FormulaCell(String formula) {
		super(0);
		this.formula = formula;
		@SuppressWarnings("unused")
		String compileTest = getContent();
	}
	
	// returns the value in formula cell as a string
	public String getContent() {
		double value = formulaProcessor();
		String valueStr = Double.toString(value);
		if (valueStr.contains(".0")) {
			valueStr = valueStr.substring(0, valueStr.indexOf("."));
		}
		//temp
		//System.out.println("final content of cell: " + valueStr);
		return valueStr;
	}
	
	// takes in formula, returns the answer as double
	public double formulaProcessor () {
		double num1= 0;
		// checks if formula is sum/avg
		if (formula.contains("sum") || formula.contains("avg")) {
			num1 = sumAvgProcessor();
		} else {
			Scanner scanner = new Scanner(formula);
			String token1 = scanner.next();
			//temp
			//System.out.println("first number token is: " + token1);
			num1 = (double)tokenProcessor(token1);
			//temp
			//System.out.println("first number is: " + num1);
		
			while (scanner.hasNext()) {
			
				String token2 = scanner.next();
				String operator = token2;
				//temp
				//System.out.println("operator is: " + operator);
			
				String token3 = scanner.next();
				//temp
				//System.out.println("second number token is: " + token3);
				double num2 = tokenProcessor(token3);
				//temp
				//System.out.println("second number is: " + token3);
			
				num1 = math(num1, operator, num2);	
			
			}	
			scanner.close();
		}
	
		return num1;
	}
		
	
	public double sumAvgProcessor () {
		String[] formulaArr = formula.split(" ");
		String startIndex = formulaArr[1];
		String endIndex = formulaArr[3];
		String[] startIndexArr = {startIndex};
		String[] endIndexArr = {endIndex};
		double sum = 0;
		double cellCounter = 0;
		
		
		if (startIndex.substring(1).equals(endIndex.substring(1))) {
			// horizontal, letter changes
			int row = Integer.parseInt(startIndex.substring(1)) - 1;
			int startColNum = VisiCalc.g.getColIndex(startIndexArr);
			int endColNum = VisiCalc.g.getColIndex(endIndexArr);
			
			for (int i = startColNum; i <= endColNum; i++) {
				if (VisiCalc.g.spreadsheet[row][i] != null) {
					String contentStr = VisiCalc.g.spreadsheet[row][i].getContent();
					int numInCell = Integer.parseInt(contentStr);
					//System.out.println(numInCell);
					sum += numInCell;
					cellCounter++;
				} else {
					// if nothing is in cell, assume value is 0
					sum += 0;
					cellCounter++;
				}
			}
			
		} else {
			// vertical, number changes
			int startNum = Integer.parseInt(startIndex.substring(1));
			int endNum = Integer.parseInt(endIndex.substring(1));
			int col = VisiCalc.g.getColIndex(startIndexArr);
			
			for (int i = startNum; i <= endNum; i++) {
				if (VisiCalc.g.spreadsheet[i - 1][col] != null) {
					String contentStr = VisiCalc.g.spreadsheet[i - 1][col].getContent();
					int numInCell = Integer.parseInt(contentStr);
					sum += numInCell;
					cellCounter++;
				} else {
					// if nothing is in cell, assume value is 0
					sum += 0;
					cellCounter++;
				}
			}	
		}
		
		if (formula.contains("avg")) {
			return sum / cellCounter;
		} else {
			return sum;
		}
	}
	
	// takes in two number and an operator, returns answer
	public double math(double num1, String operator, double num2) {
		double answer = 0.0;
		if (operator.equals("+")) {
			answer = num1 + num2;
		} else if (operator.equals("-")) {
			answer = num1 - num2;
		} else if (operator.equals("*")) {
			answer = num1 * num2;
		} else if (operator.equals("/")) {
			answer = num1 / num2;
		} else {
			System.out.println("error! (perhaps an invalid operator)");
		}
		return answer;
	}

	// takes in a token string (number or cell), returns the double value 
	public int tokenProcessor (String token) {
		String num = "0";
		token = token.toLowerCase();
		// checks if token is an index (if not, token is a number)
		boolean isIndex = false;
		for(int i = 0; i < 14; i++) {
			if (token.contains(Character.toString(VisiCalc.g.alphabet[i]))) {
				isIndex = true;
			}
		}
		
		if (!isIndex) {
			// token is number
			num = token;
		} else { 
			// token is cell
			String[] indexArray = new String[1];
			indexArray[0] = token;
			int row = VisiCalc.g.getRowIndex(indexArray);
			int column = VisiCalc.g.getColIndex(indexArray);
			if (VisiCalc.g.spreadsheet[row][column] == null) {
				// cell is empty
				System.out.print("cell " + token + " is empty! ");
				String originalInput = VisiCalc.lastInput;
				String[] originalInputArr = originalInput.split(" ");
				System.out.println("please clear cell " + originalInputArr[0] + " before continuing");
			} else if (VisiCalc.g.spreadsheet[row][column] instanceof TextCell){ 
				// cell contains text
				System.out.print("cell " + token + " contains text! ");
				String originalInput = VisiCalc.lastInput;
				String[] originalInputArr = originalInput.split(" ");
				System.out.println("please clear cell " + originalInputArr[0] + " before continuing");
			} else {
				num = VisiCalc.g.spreadsheet[row][column].getContent();
			}
		}
		return Integer.parseInt(num);
	}
	
}


