import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Scanner;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Connect4 extends JFrame {

	static int width = 7;
	static int height = 6;
	static int BOARD_WIDTH = 10 * (width + 1) + 150 * width; // lines are 10 px
																// and the cells
																// are 150x150
	static int BOARD_HEIGHT = 10 * (height + 1) + 150 * height;

	static int[][] board = new int[height][width];
	// static int[][] board = {
	// { 0, 0, 0, 0, 0, 0, 0 },
	// { 0, 0, 0, 0, 0, 0, 0 },
	// { 1, 0, 0, 0, 0, 0, 0 },
	// { 0, 1, 0, 0, 0, 0, 0 },
	// { 0, 0, 1, 0, 0, 0, 0 },
	// { 0, 0, 0, 1, 0, 0, 0 } };

	static boolean turn = true; // true -> red, false -> blue
	static boolean gameOver = false;

	static int currSelectedRow = 3;

	private Connect4() {
		super("Connect Four");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(BOARD_WIDTH, BOARD_HEIGHT);
		JPanel jPanel = new JPanel();
		add(jPanel);
		jPanel.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				if (keyCode == KeyEvent.VK_Q || keyCode == KeyEvent.VK_LEFT) {
					if (currSelectedRow > 0) {
						currSelectedRow--;
					}
				} else if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_RIGHT) {

					if (currSelectedRow < 7) {
						currSelectedRow++;
					}
				} else if (keyCode == KeyEvent.VK_ENTER) {
					try {
						int col = -1;
						for (int i = board.length - 1; i >= 0; i--) {
							if (board[i][currSelectedRow] == 0) {
								col = i;
								break;
							}
						}
						if (turn) {
							board[col][currSelectedRow] = 1;
						} else {
							board[col][currSelectedRow] = 2;
						}

						if (existsWinner(board, (turn) ? 1 : 2, 4)) {
							gameOver = !gameOver;
							System.out.println("Winner");
						}

						turn = !turn;
					} catch (ArrayIndexOutOfBoundsException exp) {
						System.out.println("Invalid move!");
					}
				}
				repaint();
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}
		});
		jPanel.setFocusable(true);
		jPanel.requestFocusInWindow();
		setResizable(false);
		setBackground(new Color(0, 0, 0));
	}

	public static void main(String args[]) {
		Connect4 game = new Connect4();
		game.setVisible(true);
		Scanner in = new Scanner(System.in);
		while (!gameOver) {

		}

	}

	public boolean existsWinner(int[][] board, int turn, int length) {

		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] == turn) {
					for (int n = 1; n < length; n++) {
						if (((j + n < board[i].length) ? board[i][j + n] != turn : true) // horizontal
								&& ((i + n < board.length) ? board[i + n][j] != turn : true) // vertical
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

		return false;

	}

	public void paint(Graphics g) {

		// draw cells
		g.setColor(new Color(169, 169, 169));
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (j == currSelectedRow) {
					if (turn) {
						g.setColor(new Color(255, 153, 153));
					} else {
						g.setColor(new Color(153, 153, 255));
					}
				}
				g.fillRect(10 + 150 * j + 10 * j, 10 + 150 * i + 10 * i, 150, 150);
				g.setColor(new Color(169, 169, 169));
			}
		}

		// draw circles
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] == 0) {
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
