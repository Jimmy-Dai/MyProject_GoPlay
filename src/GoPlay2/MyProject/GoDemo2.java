package GoPlay2.MyProject;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GoDemo2 {
	// �����߼���С
	public static int boardSize = 15;
	String[][] board = new String[boardSize][boardSize];
	// ͼ
	BufferedImage black;
	BufferedImage white;
	BufferedImage table;
	BufferedImage selected;
	// ����
	JFrame jf = new JFrame("GoPlay");
	GoBoard gb = new GoBoard();
	final int boardWidth = 535;
	final int boardHeigth = 536;
	// ѡ�е�����
	int selectedX = -1;
	int selectedY = -1;
	// ͼ���߼���С����
	final int ratio = boardWidth / boardSize;
	Random rd = new Random();

	// ��������岿��
	public void chessComputer() {
		int xComputer = rd.nextInt(boardSize);
		int yComputer = rd.nextInt(boardSize);
		if (board[xComputer][yComputer].equals("��")) {
			board[xComputer][yComputer] = "��";
		} else {
			chessComputer();
		}
	}

	//// ���Ӽ���λ
	// int winNum = 0;
	//// show win
	// public void win() {
	// Outer: for (int i = 0; i < boardSize; i++) {
	// for (int j = 0; j < boardSize; j++) {
	// if (board[i][j].equals("��")) {
	// for (int k = -3; k < 4; k++) {
	// if (!board[i + k][j].equals(null)) {
	// while (board[i + k][j].equals("��")) {
	// winNum++;
	// }
	// if (winNum == 5) {
	// System.out.println("you win");
	// break Outer;
	// }
	// }
	// }
	// }
	// }
	// }
	// }

	public void init() throws IOException {
		// ����ͼƬ
		black = ImageIO.read(new File("image/black.gif"));
		white = ImageIO.read(new File("image/white.gif"));
		table = ImageIO.read(new File("image/board.jpg"));
		selected = ImageIO.read(new File("image/selected.gif"));
		// ��ʼ�������߼�
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				board[i][j] = "��";
			}
		}
		// ȷ��������С
		gb.setPreferredSize(new Dimension(boardWidth, boardHeigth));
		// �������ƶ�����
		gb.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				selectedX = (e.getX() - 5) / ratio;
				selectedY = (e.getY() - 6) / ratio;
				gb.repaint();
			}
		});
		// ��������� ѡ�п���ʧ
		gb.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if ((selectedX >= 0 && selectedY >= 0) && (selectedX <= boardSize - 1 && selectedY <= boardSize - 1)
						&& board[selectedX][selectedY].equals("��")) {
					board[selectedX][selectedY] = "��";
					chessComputer();
				}
				gb.repaint();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				selectedX = -1;
				selectedY = -1;
				gb.repaint();
			}
		});
		// ȷ�������С
		jf.add(gb);
		jf.pack();
		UiUtil.setFrameCenter(jf);
		jf.setVisible(true);
	}

	public static void main(String[] args) throws IOException {
		GoDemo2 gd2 = new GoDemo2();
		gd2.init();
		// gd2.printBoard();
	}

	class GoBoard extends JPanel {
		@Override
		public void paint(Graphics g) {
			g.drawImage(table, 0, 0, null);

			if ((selectedX >= 0 && selectedY >= 0) && (selectedX <= boardSize - 1 && selectedY <= boardSize - 1)) {
				g.drawImage(selected, selectedX * ratio + 5, selectedY * ratio + 6, null);
			}

			for (int i = 0; i < boardSize; i++) {
				for (int j = 0; j < boardSize; j++) {
					if (board[i][j].equals("��")) {
						g.drawImage(black, i * ratio + 5, j * ratio + 6, null);
					}
					if (board[i][j].equals("��")) {
						g.drawImage(white, i * ratio + 5, j * ratio + 6, null);
					}
				}
			}
		}
	}
}
