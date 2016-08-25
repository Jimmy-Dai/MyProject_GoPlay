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
	// 棋盘逻辑大小
	public static int boardSize = 15;
	String[][] board = new String[boardSize][boardSize];
	// 图
	BufferedImage black;
	BufferedImage white;
	BufferedImage table;
	BufferedImage selected;
	// 窗体
	JFrame jf = new JFrame("GoPlay");
	GoBoard gb = new GoBoard();
	final int boardWidth = 535;
	final int boardHeigth = 536;
	// 选中的坐标
	int selectedX = -1;
	int selectedY = -1;
	// 图与逻辑大小比例
	final int ratio = boardWidth / boardSize;
	Random rd = new Random();
	// 输赢标记
	boolean blackFlag = false;
	boolean whiteFlag = false;
	//虚拟输赢标记
	boolean blackFlagFake = false;
	//虚拟棋组
	String[][] copyBoard = new String[boardSize][boardSize];

	//计算机下棋追击部分
	public void chessComputer() {
		System.arraycopy(board, 0, copyBoard, 0, board.length);
		Outer:for(int i = 0;i<=boardSize;i++){
			if(i==boardSize){
				chessComputerRandom();
				break;
			}
			for(int j=0;j<boardSize;j++){
				if(copyBoard[i][j].equals("╋")){
					copyBoard[i][j]="●";
					isBlackWin("●");
					copyBoard[i][j]="╋";
					if(blackFlagFake==true){
						board[i][j]="○";
						blackFlagFake=false;
						break Outer;
					}else if(!copyBoard[i][j].equals("╋")){
						continue;
					}
				}
			}
		}
	}
	
	// 计算机下棋随机部分
	public void chessComputerRandom() {
		int xComputer = rd.nextInt(boardSize);
		int yComputer = rd.nextInt(boardSize);
		if (board[xComputer][yComputer].equals("╋")) {
			board[xComputer][yComputer] = "○";
		} else {
			chessComputerRandom();
		}
	}

	// 判断输赢
	public void horizontalIsWin(String chess) {
		int blackNum = 1;
		int whiteNum = 1;
		Outer: for (int i = 0; i < boardSize - 4; i++) {
			for (int j = 0; j < boardSize; j++) {
				if (board[i][j].equals(chess)) {
					for (int l = 1; l < 5; l++) {
						if (board[i + l][j].equals(chess)) {
							if (chess.equals("●")) {
								blackNum++;
							} else if (chess.equals("○")) {
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
							if (chess.equals("●")) {
								blackNum++;
							} else if (chess.equals("○")) {
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
							if (chess.equals("●")) {
								blackNum++;
							} else if (chess.equals("○")) {
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
							if (chess.equals("●")) {
								blackNum++;
							} else if (chess.equals("○")) {
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

	// 虚拟棋组判断输赢
	public void horizontalIsWinBlack(String chess) {
		int blackNum = 1;
		int whiteNum = 1;
		Outer: for (int i = 0; i < boardSize - 4; i++) {
			for (int j = 0; j < boardSize; j++) {
				if (copyBoard[i][j].equals(chess)) {
					for (int l = 1; l < 5; l++) {
						if (copyBoard[i + l][j].equals(chess)) {
							if (chess.equals("●")) {
								blackNum++;
							} else if (chess.equals("○")) {
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
							if (chess.equals("●")) {
								blackNum++;
							} else if (chess.equals("○")) {
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
							if (chess.equals("●")) {
								blackNum++;
							} else if (chess.equals("○")) {
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
							if (chess.equals("●")) {
								blackNum++;
							} else if (chess.equals("○")) {
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
	
	//初始化
	public void init() throws IOException {
		// 载入图片
		black = ImageIO.read(new File("image/black.gif"));
		white = ImageIO.read(new File("image/white.gif"));
		table = ImageIO.read(new File("image/board.jpg"));
		selected = ImageIO.read(new File("image/selected.gif"));
		// 初始化棋盘逻辑
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				board[i][j] = "╋";
			}
		}
		// 确定画布大小
		gb.setPreferredSize(new Dimension(boardWidth, boardHeigth));
		// 添加鼠标移动监听
		gb.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				selectedX = (e.getX() - 5) / ratio;
				selectedY = (e.getY() - 6) / ratio;
				gb.repaint();
			}
		});
		// 添加鼠标点击与出窗监听
		gb.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if ((selectedX >= 0 && selectedY >= 0) && (selectedX <= boardSize - 1 && selectedY <= boardSize - 1)
						&& board[selectedX][selectedY].equals("╋")) {
					board[selectedX][selectedY] = "●";
					isWin("●");
					chessComputer();
					isWin("○");
				}
				gb.repaint();
				if (blackFlag == true) {
					JOptionPane.showMessageDialog(jf, "黑棋赢了\n点击确定开始新游戏");
					jf.dispose();
					GoDemo4 gd = new GoDemo4();
					try {
						gd.init();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				} else if (whiteFlag == true) {
					JOptionPane.showMessageDialog(jf, "白棋赢了\n点击确定开始新游戏");
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
		// 确定窗体大小
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
					if (board[i][j].equals("●")) {
						g.drawImage(black, i * ratio + 5, j * ratio + 6, null);
					}
					if (board[i][j].equals("○")) {
						g.drawImage(white, i * ratio + 5, j * ratio + 6, null);
					}
				}
			}
		}
	}
}
