import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Scanner;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
//test
public class Connect4 extends JFrame {
	static int width = 7;
	static int height = 6;
	static int BOARD_WIDTH = 10 * (width + 1) + 150 * width; //lines are 10 px and the cells are 150x150
	static int BOARD_HEIGHT = 10 * (height + 1) + 150 * height;
	
	//static int[][] board = new int[height][width];
	static int[][] board = {
			{0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0},
			{1, 0, 0, 0, 0, 0, 0},
			{0, 1, 0, 0, 0, 0, 0},
			{0, 0, 1, 0, 0, 0, 0},
			{0, 0, 0, 1, 0, 0, 0}
	};
	static boolean turn = true; //true -> red, false -> blue
	static boolean gameOver = false;
	
	private Connect4() {
		super("Connect Four");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(BOARD_WIDTH, BOARD_HEIGHT);
		JPanel jPanel = new JPanel();
		add(jPanel);
		setResizable(false);
		setBackground(new Color(0, 0, 0));
	}
	
	public static void main (String args []) {
		Connect4 game = new Connect4();
		game.setVisible(true);
		Scanner in = new Scanner(System.in);
		while(!gameOver) {
			int row = -1;
			int col = -1;
			System.out.println("Please enter a row to play in: ");
			try {
				row = Integer.parseInt(in.nextLine()) - 1;
				try {
					for(int i = board.length - 1; i >= 0; i--) {
						if(board[i][row] == 0) {
							col = i;
							break;
						}
					}
					if(turn) {
						board[col][row] = 1;
					} else {
						board[col][row] = 2;
					}
					
					for(int i = 0; i < board.length; i++) {
						for(int j = 0; j < board[i].length; j++) {
							System.out.print(board[i][j] + " ");
						}
						System.out.println();
					}
					
					game.repaint();
					
					if(game.existsWinner(board, (turn) ? 1 : 2, 4)) {
						gameOver = !gameOver;
						System.out.println("Winner");
						in.close();
					}
					
					turn = !turn;
				} catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("Invalid move!");
				}	
				
			} catch (NumberFormatException e){
				System.out.println("Not a number! Try again!");
			}	
		}
		
		//System.out.println(game.existsWinner(board, 1, 4));
	}
	
	public boolean existsWinner(int[][] board, int turn, int length) {
		
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board[i].length; j++) {
				if(board[i][j] == turn) {
					for(int n = 1; n < length; n++) {
						//Here, I abuse the fact that Java evaluates boolean expressions from left to right, 
						//so ArrayIndexOutOfBoundsException won't ever occur if left is false b/c right side won't
						//be evaluated
						if(((j + n < board[i].length) ? board[i][j + n] != turn : true) //horizontal
								&& ((i + n < board.length) ? board[i + n][j] != turn : true) //vertical
								&& ((j + n < board[i].length && i - n >= 0) ? board[i - n][j + n] != turn : true) 
								&& ((j - n >= 0 && i - n >= 0) ? board[i - n][j - n] != turn : true)) {
							break;
						}
						if (n + 1 == length) {
							return true;
						}
					}
				}
			}
		}
		
//		for(int i = 0; i < board.length; i++) {
//			for(int j = 0; j < board[i].length; j++) {
//				System.out.print(board[i][j] + " ");
//			}
//			System.out.println();
//		}
		
		return false;
		
	}
	
	public void paint(Graphics g) {
		
		//draw cells
		g.setColor(new Color(169, 169, 169));
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board[i].length; j++) {
				g.fillRect(10 + 150 * j + 10 * j, 10 + 150 * i + 10 * i, 150, 150);
			}
		}
		
		//draw circles
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board[i].length; j++) {
				if(board[i][j] == 0) {
					g.setColor(new Color(255, 255, 255));
				} else if (board[i][j] == 1) {
					g.setColor(new Color(255, 0, 0));
				} else {
					g.setColor(new Color(0, 0, 255));
				}
				g.fillOval(10 + 150 * j + 10 * j, 10 + 150 * i + 10 * i, 150, 150);
			}
		}
	}

}
