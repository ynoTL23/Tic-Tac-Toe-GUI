package tttText;

import java.util.Scanner;

public class TTT implements Gamer {

	private final char[][] grid = new char[3][3];
	public static final char X_SYMBOL = 'X';
	public static final char O_SYMBOL = 'O';
	private char currPlayer = X_SYMBOL;
	public static final char EMPTY = ' '; // populate grid with empty space
	
	public TTT() {
		for(int r = 0; r < grid.length; r++) {
			for(int c = 0; c < grid[r].length; c++) {
				grid[r][c] = EMPTY;
			}
		}
	}
	
	@Override
	public int[] getLocationFromUser() {
		int[] rowAndColIndexes = new int[2]; // stores the position (row and col) of marker
		Scanner kb = new Scanner(System.in);
		
		System.out.print("Enter the row and column for marker: ");
		rowAndColIndexes[0] = kb.nextInt(); // row
		rowAndColIndexes[1] = kb.nextInt(); // col
		if(rowAndColIndexes[0] >= grid.length || rowAndColIndexes[1] >= grid.length) {
			System.out.println("Invalid location");
			getLocationFromUser(); // call again, recursion
		}
		return rowAndColIndexes;
	}
	
	private void placeMarker(int[] indRowCol) {
		int rowIndex = indRowCol[0];
		int colIndex = indRowCol[1];
		if(grid[rowIndex][colIndex] == EMPTY) {
			grid[rowIndex][colIndex] = currPlayer;
		} else {
			System.out.println("SPACE TAKEN : TRY AGAIN");
			getLocationFromUser();
		}
	}
	
	private void changePlayer() {
		if(currPlayer == X_SYMBOL) {
			currPlayer = O_SYMBOL; // change to O player
		} else {
			currPlayer = X_SYMBOL; // change to X player
		}
	}

	public void drawBoard() {
		for (int r = 0; r < grid.length; r++) {
			for (int c = 0; c < grid[r].length-1; c++) {
				System.out.printf("%-1s|", grid[r][c]);
			}
			System.out.println(grid[r][2]);
			System.out.println("-----");
		}
	}
	
	// --- START WINNER CHECKER ---
	
	// check matches per row
	public boolean isCurrPlayerWinnerInRow() {
		for (int row = 0; row < grid.length; row++) {
			int matches = 0;
			for (int col = 0; col < grid[row].length; col++) {
				if (grid[row][col] == currPlayer) {
					matches++;
				}
			} // finished row
			if (matches == 3) {
				return true; // winner in row
			}
		}
		return false;
	}
	
	// check matches per column
	public boolean isCurrPlayerWinnerInCol() {
		for (int col = 0; col < grid.length; col++) {
			int matches = 0;
			for (int row = 0; row < 3; row++) {
				if (grid[row][col] == currPlayer) {
					matches++;
				}
			} // finished row
			if (matches == 3) {
				return true; // winner in col
			}
		}
		return false;
	}
	
	// check matches in diag
	public boolean isCurrPlayerWinnerInMainDiag() {
		int matches = 0;
		for(int i = 0; i < grid.length; i++) {
			if(grid[i][i] == currPlayer) {
				matches++;
			}
		} // finished diag
		if(matches == 3) {
			return true; // winner in diag
		} else {
			return false;
		}
	}
	
	// check matches in other diag
	public boolean isCurrPlayerWinnerInSecondDiag() {
		int matches = 0;
		int row = 2;
		int col = 0;
		while(row > -1 && col < 3) {
			if(grid[row][col] == currPlayer) {
				matches++;
			}
			row--;
			col++;
		} // finished diag
		if(matches == 3) {
			return true; // winner in diag
		} else {
			return false;
		}
	}
	
	public boolean isCurrPlayerWinner() {
		 isCurrPlayerWinnerInRow();
		 isCurrPlayerWinnerInCol();
		 isCurrPlayerWinnerInMainDiag();
		 isCurrPlayerWinnerInSecondDiag();
		 
		 if(isCurrPlayerWinnerInRow() || isCurrPlayerWinnerInCol() || isCurrPlayerWinnerInMainDiag() || isCurrPlayerWinnerInSecondDiag()) {
			 return true;
		 } else {
			 return false;
		 }
		 
	}
	
	// --- END WINNER CHECKER ---
	
	@Override
	public void takeTurn() {
		System.out.println("> Current player: " + currPlayer);
		placeMarker(getLocationFromUser()); // put marker at spot
		System.out.println();
		drawBoard();
	
		if(isCurrPlayerWinner()) {
			System.out.println("WINNER: " + currPlayer);
		} else {		
			isCurrPlayerWinner(); // check if current player won
			changePlayer(); // change current player
			takeTurn(); // call self
		}
	}
	
}
