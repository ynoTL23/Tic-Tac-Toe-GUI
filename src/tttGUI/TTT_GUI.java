package tttGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class TTT_GUI extends JFrame {

	private JPanel jpMain;
	Scoreboard sBoard; // a JPanel displaying the score made up of JLabels
	TTTBoard jpBoard; // a JPanel containing the game board made up of JButtons

	private Player currPlayer, player1, player2;

	public TTT_GUI() {
		player1 = new Player("Player 1", "X");
		player2 = new Player("Player 2", "O");
		currPlayer = player1;

		jpMain = new JPanel();
		jpMain.setLayout(new GridLayout(2, 1)); // NORTH, SOUTH, EAST, WEST, CENTER

		sBoard = new Scoreboard(); // init scoreboard
		jpBoard = new TTTBoard(); // init gameboard
		jpMain.add(BorderLayout.NORTH, sBoard); // add scorebaord to jpMain | BorderLayout.NORTH
		jpMain.add(BorderLayout.CENTER, jpBoard); // ad gameboard to jpMain | BorderLayout.CENTER

		add(jpMain);

		setSize(500, 500);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

	}

	private class Scoreboard extends JPanel {
		private JLabel header, p1Score, p2Score;
		
		// make the scoreboard
		public Scoreboard() {
			setLayout(new GridLayout(3, 1));
			
			header = new JLabel("Tic-Tac-Toe", SwingConstants.CENTER);
			header.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 48));
			header.setForeground(Color.WHITE);
			setBackground(new Color(69, 123, 157));
			add(header);
			
			p1Score = new JLabel(player1.getName() + " : [Wins - " + player1.getNumWins() + "] [Losses - " + player1.getNumLosses() + "] [Games - " + player1.getNumGames() + "]", SwingConstants.CENTER);
			p2Score = new JLabel(player2.getName() + " : [Wins - " + player2.getNumWins() + "] [Losses - " + player2.getNumLosses() + "] [Games - " + player2.getNumGames() + "]", SwingConstants.CENTER);

			Font f = new Font(Font.SANS_SERIF, Font.BOLD, 18);
			p1Score.setFont(f);
			p2Score.setFont(f);
			p1Score.setForeground(Color.WHITE);
			p2Score.setForeground(Color.WHITE);
			add(p1Score);
			add(p2Score);
		}
		
	}

	private class TTTBoard extends JPanel implements ActionListener, GameBoard, GamePlayer {

		// set up the board
		private JButton[][] board;
		private final int ROWS = 3;
		private final int COLS = 3;

		public TTTBoard() {
			setLayout(new GridLayout(ROWS, COLS, 1, 1));
			board = new JButton[ROWS][COLS];
			displayBoard();

		}

		// show the board
		@Override
		public void displayBoard() {
			for (int r = 0; r < board.length; r++) {
				for (int c = 0; c < board[r].length; c++) {
					board[r][c] = new JButton();
					Font font = new Font(Font.SANS_SERIF, Font.BOLD, 40);
					board[r][c].setFont(font);
					board[r][c].addActionListener(this);
					board[r][c].setEnabled(true);
					add(board[r][c]);
				}
			}
		}

		// reset board to empty
		@Override
		public void resetBoard() {
			for (int r = 0; r < board.length; r++) {
				for (int c = 0; c < board[r].length; c++) {
					board[r][c].setText("");
					board[r][c].setBackground(null);
					board[r][c].setEnabled(true);
				}
			}
		}

		// listen for actions from user
		@Override
		public void actionPerformed(ActionEvent e) {

			JButton btn = (JButton) e.getSource(); // find which button pressed
			btn.setText(currPlayer.getMarker()); // place and set marker onto button setText()
			if(currPlayer.equals(player1)) {
				btn.setBackground(new Color(230, 57, 70));
				
			} else if(currPlayer.equals(player2)) {
				btn.setBackground(new Color(29, 53, 87));
				
			}
			btn.setEnabled(false); // disable button
			if (isWinner()) { // one of the players won!
				// check if winner
				// if winner, display winner
				// ask to play again
				// clear board
				JOptionPane.showMessageDialog(null, "WINNER: " + currPlayer.getName());
				if(currPlayer.equals(player1)) { // player 1 wins!
					player1.addNumWins();
					player2.addNumLossses();
					updateScores();
					resetBoard();
				} else if(currPlayer.equals(player2)) { // player 2 wins!
					player2.addNumWins();
					player1.addNumLossses();
					updateScores();
					resetBoard();
				}
			} else if (isFull()) { // game ends in draw
				// not winner, check if board full
				// game over, is draw
				// ask play again
				// clear board
				JOptionPane.showMessageDialog(null, "DRAW!");
				player1.addDraw();
				player2.addDraw();
				updateScores();
				resetBoard();
			} else {
				nextTurn();
				// nextTurn() / update display and swap curr player
			}

		}

		
		// update scoreboard
		private void updateScores() {
			sBoard.p1Score.setText(player1.getName() + " : [Wins - " + player1.getNumWins() + "] [Losses - " + player1.getNumLosses() + "] [Games - " + player1.getNumGames() + "]");
			sBoard.p2Score.setText(player2.getName() + " : [Wins - " + player2.getNumWins() + "] [Losses - " + player2.getNumLosses() + "] [Games - " + player2.getNumGames() + "]");
			
		}

		@Override
		public boolean isEmpty() {
			for (int r = 0; r < board.length; r++) {
				for (int c = 0; c < board[r].length; c++) {
					String cell = board[r][c].getText().trim();
					if (cell.isEmpty())
						return true;
				}
			}
			return false;
		}

		@Override
		public boolean isFull() {
			for (int r = 0; r < board.length; r++) {
				for (int c = 0; c < board[r].length; c++) {
					String cell = board[r][c].getText().trim();
					if (cell.isEmpty())
						return false;
				}
			}
			return true;
		}

		@Override
		public boolean isWinner() {
			// check rows
			// check cols
			// check diag
			// check other diag
			if (rowWin() || colWin() || DiagWin() || SecDiagWin()) {
				return true;
			} else {
				return false;
			}
		}

		// row winner checker
		public boolean rowWin() {
			String marker = currPlayer.getMarker();
			for (int r = 0; r < board.length; r++) {
				int count = 0;
				for (int c = 0; c < board[r].length; c++) {
					if (board[r][c].getText().trim().equalsIgnoreCase(marker)) {
						count++;
					} else {
						break;
					}
					if (count == 3)
						return true;
				}
			}
			return false;
		}

		// column winner checker
		public boolean colWin() {
			String marker = currPlayer.getMarker();
			for (int c = 0; c < board.length; c++) {
				int count = 0;
				for (int r = 0; c < board[r].length; r++) {
					if (board[r][c].getText().trim().equalsIgnoreCase(marker)) {
						count++;
					} else {
						break;
					}
					if (count == 3)
						return true;
				}
			}
			return false;
		}

		// diagonal winner checker
		public boolean DiagWin() {
			String marker = currPlayer.getMarker();
			int count = 0;
			int c = 0;
			for (int r = 0; r < board.length; r++) {
				if (board[r][c].getText().trim().equalsIgnoreCase(marker)) {
					count++;
					c++;
				}
			}
			if (count == 3) {
				return true;
			} else {
				return false;
			}
		}

		// reverse diagonal winner checker
		public boolean SecDiagWin() {
			String marker = currPlayer.getMarker();
			int count = 0;
			int c = 2;
			for (int r = 0; r < board.length; r++) {
				if(board[r][c].getText().trim().equalsIgnoreCase(marker)) {
					count++;
					c--;
				}
			}
			if(count == 3) {
				return true;
			} else {
				return false;
			}
		}

		@Override
		public void nextTurn() {
			if (currPlayer.equals(player1)) {
				currPlayer = player2;
			} else {
				currPlayer = player1;
			}

		}

	}

}
