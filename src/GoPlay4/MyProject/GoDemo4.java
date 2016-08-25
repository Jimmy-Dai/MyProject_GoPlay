package GoPlay4.MyProject;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GoDemo4 {
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
	// ��Ӯ���
	boolean blackFlag = false;
	boolean whiteFlag = false;
	//������Ӯ���
	boolean blackFlagFake = false;
	//��������
	String[][] copyBoard = new String[boardSize][boardSize];

	//���������׷������
	public void chessComputer() {
		System.arraycopy(board, 0, copyBoard, 0, board.length);
		Outer:for(int i = 0;i<=boardSize;i++){
			if(i==boardSize){
				chessComputerRandom();
				break;
			}
			for(int j=0;j<boardSize;j++){
				if(copyBoard[i][j].equals("��")){
					copyBoard[i][j]="��";
					isBlackWin("��");
					copyBoard[i][j]="��";
					if(blackFlagFake==true){
						board[i][j]="��";
						blackFlagFake=false;
						break Outer;
					}else if(!copyBoard[i][j].equals("��")){
						continue;
					}
				}
			}
		}
	}
	
	// ����������������
	public void chessComputerRandom() {
		int xComputer = rd.nextInt(boardSize);
		int yComputer = rd.nextInt(boardSize);
		if (board[xComputer][yComputer].equals("��")) {
			board[xComputer][yComputer] = "��";
		} else {
			chessComputerRandom();
		}
	}

	// �ж���Ӯ
	public void horizontalIsWin(String chess) {
		int blackNum = 1;
		int whiteNum = 1;
		Outer: for (int i = 0; i < boardSize - 4; i++) {
			for (int j = 0; j < boardSize; j++) {
				if (board[i][j].equals(chess)) {
					for (int l = 1; l < 5; l++) {
						if (board[i + l][j].equals(chess)) {
							if (chess.equals("��")) {
								blackNum++;
							} else if (chess.equals("��")) {
								whiteNum++;
							}
						} else {
							blackNum = 1;
							whiteNum = 1;
							break;
						}
					}
				}
				if (blackNum == 5) {
					blackFlag = true;
					break Outer;
				} else if (whiteNum == 5) {
					whiteFlag = true;
					break Outer;
				}
			}
		}
	}

	public void verticalIsWin(String chess) {
		int blackNum = 1;
		int whiteNum = 1;
		Outer: for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize - 4; j++) {
				if (board[i][j].equals(chess)) {
					for (int l = 1; l < 5; l++) {
						if (board[i][j + l].equals(chess)) {
							if (chess.equals("��")) {
								blackNum++;
							} else if (chess.equals("��")) {
								whiteNum++;
							}
						} else {
							blackNum = 1;
							whiteNum = 1;
							break;
						}
					}
				}
				if (blackNum == 5) {
					blackFlag = true;
					break Outer;
				} else if (whiteNum == 5) {
					whiteFlag = true;
					break Outer;
				}
			}
		}
	}

	public void leftGradientIsWin(String chess) {
		int blackNum = 1;
		int whiteNum = 1;
		Outer: for (int i = 0; i < boardSize - 4; i++) {
			for (int j = 0; j < boardSize - 4; j++) {
				if (board[i][j].equals(chess)) {
					for (int l = 1; l < 5; l++) {
						if (board[i + l][j + l].equals(chess)) {
							if (chess.equals("��")) {
								blackNum++;
							} else if (chess.equals("��")) {
								whiteNum++;
							}
						} else {
							blackNum = 1;
							whiteNum = 1;
							break;
						}
					}
				}
				if (blackNum == 5) {
					blackFlag = true;
					break Outer;
				} else if (whiteNum == 5) {
					whiteFlag = true;
					break Outer;
				}
			}
		}
	}

	public void rightGradientIsWin(String chess) {
		int blackNum = 1;
		int whiteNum = 1;
		Outer: for (int i = boardSize - 1; i > 3; i--) {
			for (int j = 0; j < boardSize - 4; j++) {
				if (board[i][j].equals(chess)) {
					for (int l = 1; l < 5; l++) {
						if (board[i - l][j + l].equals(chess)) {
							if (chess.equals("��")) {
								blackNum++;
							} else if (chess.equals("��")) {
								whiteNum++;
							}
						} else {
							blackNum = 1;
							whiteNum = 1;
							break;
						}
					}
				}
				if (blackNum == 5) {
					blackFlag = true;
					break Outer;
				} else if (whiteNum == 5) {
					whiteFlag = true;
					break Outer;
				}
			}
		}
	}

	public void isWin(String chess) {
		horizontalIsWin(chess);
		verticalIsWin(chess);
		leftGradientIsWin(chess);
		rightGradientIsWin(chess);
	}

	// ���������ж���Ӯ
	public void horizontalIsWinBlack(String chess) {
		int blackNum = 1;
		int whiteNum = 1;
		Outer: for (int i = 0; i < boardSize - 4; i++) {
			for (int j = 0; j < boardSize; j++) {
				if (copyBoard[i][j].equals(chess)) {
					for (int l = 1; l < 5; l++) {
						if (copyBoard[i + l][j].equals(chess)) {
							if (chess.equals("��")) {
								blackNum++;
							} else if (chess.equals("��")) {
								whiteNum++;
							}
						} else {
							blackNum = 1;
							whiteNum = 1;
							break;
						}
					}
				}
				if (blackNum == 5) {
					blackFlagFake = true;
					break Outer;
				} else if (whiteNum == 5) {
					whiteFlag = true;
					break Outer;
				}
			}
		}
	}

	public void verticalIsWinBlack(String chess) {
		int blackNum = 1;
		int whiteNum = 1;
		Outer: for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize - 4; j++) {
				if (copyBoard[i][j].equals(chess)) {
					for (int l = 1; l < 5; l++) {
						if (copyBoard[i][j + l].equals(chess)) {
							if (chess.equals("��")) {
								blackNum++;
							} else if (chess.equals("��")) {
								whiteNum++;
							}
						} else {
							blackNum = 1;
							whiteNum = 1;
							break;
						}
					}
				}
				if (blackNum == 5) {
					blackFlagFake = true;
					break Outer;
				} else if (whiteNum == 5) {
					whiteFlag = true;
					break Outer;
				}
			}
		}
	}

	public void leftGradientIsWinBlack(String chess) {
		int blackNum = 1;
		int whiteNum = 1;
		Outer: for (int i = 0; i < boardSize - 4; i++) {
			for (int j = 0; j < boardSize - 4; j++) {
				if (copyBoard[i][j].equals(chess)) {
					for (int l = 1; l < 5; l++) {
						if (copyBoard[i + l][j + l].equals(chess)) {
							if (chess.equals("��")) {
								blackNum++;
							} else if (chess.equals("��")) {
								whiteNum++;
							}
						} else {
							blackNum = 1;
							whiteNum = 1;
							break;
						}
					}
				}
				if (blackNum == 5) {
					blackFlagFake = true;
					break Outer;
				} else if (whiteNum == 5) {
					whiteFlag = true;
					break Outer;
				}
			}
		}
	}

	public void rightGradientIsWinBlack(String chess) {
		int blackNum = 1;
		int whiteNum = 1;
		Outer: for (int i = boardSize - 1; i > 3; i--) {
			for (int j = 0; j < boardSize - 4; j++) {
				if (copyBoard[i][j].equals(chess)) {
					for (int l = 1; l < 5; l++) {
						if (copyBoard[i - l][j + l].equals(chess)) {
							if (chess.equals("��")) {
								blackNum++;
							} else if (chess.equals("��")) {
								whiteNum++;
							}
						} else {
							blackNum = 1;
							whiteNum = 1;
							break;
						}
					}
				}
				if (blackNum == 5) {
					blackFlagFake = true;
					break Outer;
				} else if (whiteNum == 5) {
					whiteFlag = true;
					break Outer;
				}
			}
		}
	}

	public void isBlackWin(String chess) {
		horizontalIsWinBlack(chess);
		verticalIsWinBlack(chess);
		leftGradientIsWinBlack(chess);
		rightGradientIsWinBlack(chess);
	}
	
	//��ʼ��
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
		// �����������������
		gb.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if ((selectedX >= 0 && selectedY >= 0) && (selectedX <= boardSize - 1 && selectedY <= boardSize - 1)
						&& board[selectedX][selectedY].equals("��")) {
					board[selectedX][selectedY] = "��";
					isWin("��");
					chessComputer();
					isWin("��");
				}
				gb.repaint();
				if (blackFlag == true) {
					JOptionPane.showMessageDialog(jf, "����Ӯ��\n���ȷ����ʼ����Ϸ");
					jf.dispose();
					GoDemo4 gd = new GoDemo4();
					try {
						gd.init();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				} else if (whiteFlag == true) {
					JOptionPane.showMessageDialog(jf, "����Ӯ��\n���ȷ����ʼ����Ϸ");
					jf.dispose();
					GoDemo4 gd = new GoDemo4();
					try {
						gd.init();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				selectedX = -1;
				selectedY = -1;
				gb.repaint();
			}
		});
		jf.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		// ȷ�������С
		jf.add(gb);
		jf.pack();
		UiUtil.setFrameCenter(jf);
		jf.setResizable(false);
		jf.setVisible(true);
	}

	public static void main(String[] args) throws IOException {
		GoDemo4 gd3 = new GoDemo4();
		gd3.init();
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
